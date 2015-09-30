import structure5.*;

// A class that uses Readers and interprets the results and adds them to a stack.  If the result is recognized, the interpreter class will call the given method attached to the result name like add or sub. This functionality models the postscript language and the reverse polish notation approach to mathematics calculations  
public class Interpreter{

    //stack and table , stack holds all user inputs and table holds all user defined functions (procedures) 
    private DoublyLinkedList<Token> stack = new DoublyLinkedList<Token>(); 
    private SymbolTable table = new SymbolTable(); 

    public static void main(String[] args){
	//Create a new interpreter to read in output from the reader 
	Interpreter interpreter = new Interpreter(); 
	interpreter.interpret(new Reader()); 
    }

    public Interpreter(){
	
    }

    //Interprets the output of a given reader 
    public void interpret(Reader r){
	Token t; 
	//reads in output of reader and adds results to list or calls the method attached to the result 
	while(r.hasNext()){
	    t = r.next(); 
	    if(t.isSymbol()&&t.getSymbol().equals("quit")){break;}
	    //process token here determine what its type is 
	    String result = ""; 
	    switch (t.kind())
		{
		case 1:
		    result = "number";
		    stack.addLast(t); 
		    break;
		case 2:				
		    result = "bool";
		    stack.addLast(t); 
		    break;
		case 3:
		    result = "symbol";
		    check(t); 
		    break;
		 // t is a procedure, automatically checks for { and } 
		case 4:
		    result = "{ ";
		    stack.addLast( t ); 
		    break; 
		default:
		    break;
		}
	
	}
    }
    //check to see which procedure should be called for the given Token 
    public void check(Token t){
	//redundant check but necessary for checks called from procedureReader.next()
	if(t.isNumber() || t.isBoolean() || t.isProcedure()){
	    stack.addLast(t); 
	    return; 
	}
	//see if a given method exists for the input 
	if(t.getSymbol().equals("add")) add(); 
	else if(t.getSymbol().equals("sub")) sub(); 
	else if(t.getSymbol().equals("mul")) mul(); 
	else if(t.getSymbol().equals("div")) div(); 
	else if(t.getSymbol().equals("dup")) dup(); 
	else if(t.getSymbol().equals("exch")) exch();
	else if(t.getSymbol().equals("ne")) ne();
	else if(t.getSymbol().equals("pstack")) pstack(); 
	else if(t.getSymbol().equals("pop")) pop(); 
	else if(t.getSymbol().contains("def")) def();
	else if(t.getSymbol().equals("eq")) eq(); 
	else if(t.getSymbol().equals("if")) IF(); 
	else if(t.getSymbol().equals("ptable")) ptable(); 
	else if(table.contains(t.getSymbol())){
	    //if the table entry is a number 
	    //based on the bailey.jar book examples, assuming we are not allowed to define something like /pi            dup def instead must use /pi { dup } def for any procedure that contains methods 
	    if( table.get(t.getSymbol()).isNumber() ){
		stack.addLast(new Token(table.get(t.getSymbol()).getNumber())); 
	    }
	    //else the table entry is a list of tokens and numbers.  Read all elements from the procedure and calls the correct methods 
	    else{ 
		Reader procedureReader = new Reader(table.get(t.getSymbol()).getProcedure()); 
		while(procedureReader.hasNext()){
		    //while the reader still has input from the procedure (ie a list of tokens) interpret each token 
		    this.check(procedureReader.next()); 
		}
		
	    }
	}


	//for adding function names: /pi 
	else if(t.isSymbol() && t.getSymbol().contains("/")) stack.addLast(t); 
	//else the user has typed in invalid input 
	else Assert.condition(false, "must give valid input");  
			    
			    
    }
    public void add(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" ); 
	Token a = stack.removeLast(); 
	Token b = stack.removeLast(); 
	Assert.condition(a.isNumber()&&b.isNumber(),"Must use numbers"); 
	stack.addLast(new Token(a.getNumber()+b.getNumber())); 	    
    }

    public void sub(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" );
	Token a = stack.removeLast(); 
	Token b = stack.removeLast(); 
	Assert.condition(a.isNumber()&&b.isNumber(),"Must use numbers"); 
	stack.addLast(new Token(b.getNumber()-a.getNumber())); 
    }

    public void mul(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" );
	Token a = stack.removeLast(); 
	Token b = stack.removeLast(); 
	Assert.condition(a.isNumber()&&b.isNumber(),"Must use numbers"); 
	stack.addLast(new Token(a.getNumber()*b.getNumber())); 
    }

    public void div(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" );
	Token a = stack.removeLast(); 
	Token b = stack.removeLast(); 
	Assert.condition(a.isNumber()&&b.isNumber(),"Must use numbers"); 
	Assert.condition(b.getNumber()!=0,"Cannot divide by 0"); 
	stack.addLast(new Token(a.getNumber()/b.getNumber())); 
    }
    //duplicate , can duplicate tokens that are not numbers 
    public void dup(){
	stack.addLast(stack.getLast()); 
	
    }
    //swap elements in the stack.  
    public void exch(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" );
	Token a = stack.removeLast(); 
	Token b = stack.removeLast(); 
	stack.addLast(a); 
	stack.addLast(b); 
    }
    //test if equal return boolean token to stack 
    public void eq(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" );
	Token a = stack.removeLast(); 
	Token b = stack.removeLast();
	Assert.condition(a.isNumber()&&b.isNumber() || a.isBoolean()&&b.isBoolean() || a.isProcedure()&&b.isProcedure() || a.isSymbol()&&b.isSymbol(),"Must compare same types of object"); 
	stack.addLast( new Token( a.equals(b) ) ); 
    }
    //test if not equal 
    public void ne(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" );
	Token a = stack.removeLast(); 
	Token b = stack.removeLast(); 	
	Assert.condition(a.isNumber()&&b.isNumber() || a.isBoolean()&&b.isBoolean() || a.isProcedure()&&b.isProcedure() || a.isSymbol()&&b.isSymbol(),"Must compare same types of object"); 
	stack.addLast( new Token( !a.equals(b) ) ); 	
    }
    //define methods and numbers related to symbol def and add to table of symbols 
    public void def(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" );
	Token value = stack.removeLast();  
	Token symbol = stack.removeLast(); 

	Assert.condition(symbol.isSymbol(), "must give your procedure a valid name (a string)"); 
	Assert.condition(symbol.getSymbol().contains("/"), "must follow procedure rules: /"); 

	table.add(symbol.getSymbol().substring(1),value); 

	
    }
    //removes last element in the stack 
    public void pop(){
	stack.removeLast(); 
    }
    //print out the entire stack 
    public void pstack(){
	for(int i = stack.size()-1; i>=0; i--){
	    System.out.println(stack.get(i)); 
	}
    }
    // inputs: boolean token if, run token if boolean is true 
    public void IF(){
	Assert.condition( stack.size() >= 2, "must have two elements in stack" );
	Token token = stack.removeLast(); 
	Token bool = stack.removeLast(); 
	Assert.condition(bool.isBoolean(),"must include boolean"); 
	if(bool.getBoolean()){
	    Reader tokenReader = new Reader(token); 
	    interpret(tokenReader); 
	}
	
    }
    //print out a table of user defined functions: their names and what they do 
    public void ptable(){
	System.out.println( table.toString() ); 
    }
}


