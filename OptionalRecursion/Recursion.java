
import structure5.*; 
//import java.util.*; 

public class Recursion{


    /* The very long method for longest subsequence, iteratively 
    //Methods for finding longest substring 
    public String longest(String A, String B){
	//this.longestHelper(A,B,"",0); 
	    this.helper(A,B); 
	    return null; 
    }
    public String helper(String A, String B){
	for(int i = 0; i<A.length(); i++){
	    for(int j = 0; j<B.length(); j++){
		if(A.charAt(i)==B.charAt(j)){
		    System.out.println( walker( A.substring(i), B.substring(j), 1, 1) ); 
		}
	    }
	}
	return null;
    }
    public String walker(String A, String B, int iA, int iB){
	if(Math.min(A.length(),B.length())>Math.max(iA,iB)){
	    while(iB<B.length()){
		if(A.charAt(iA)==B.charAt(iB)){
		    return this.walker(A,B,++iA,iB); 
		}
		++iB; 
	    }
	} 
	return A.substring(0,iA); 
    }
//end methods for finding longest substring 
*/

    //Recursive longest subsequence method 
    public String longest(String A, String B, String C){

	if(A.length()==0 || B.length()==0){
		return C; 
	}

	//call on different combinations of string A and B 
	String x = longest(A.substring(1), B.substring(1), C); 
	String y = longest( A, B.substring(1), C);
	String z = longest( A.substring(1), B, C); 
	String u = ""; 
	
	if(A.charAt(0)==B.charAt(0)){
	    String D = C + A.charAt(0); 
	    u = longest( A.substring(1) , B.substring(1) , D); 
	}

	//is this the most efficient way? 
	int max = ( Math.max( Math.max(u.length(),z.length()), Math.max(x.length(),y.length()) ) );
	if(x.length()==max) return x; 
	else if(y.length()==max) return y; 
	else if (z.length()==max) return z; 
	else return u; 

    }

    //Recursive ChainExists method
    public boolean chainExists(Vector<Domino> dominos, int start, int finish){
	return search(dominos,start,finish,0); 
    }
    public boolean search(Vector<Domino> dominos, int search, int target, int index){
	if(index==dominos.size()) return false;
	else{ //doubles for every call 
	    //check if other domino first side is search 
	    if(dominos.get(index).firstSide()==search){
		if(dominos.get(index).secondSide()==target){
		    return true; 
		}
		Vector<Domino> dominosClone = (Vector<Domino>)dominos.clone(); 
		dominosClone.remove(dominos.get(index)); 
		return( search(dominos,search,target,++index) || search(dominosClone,dominos.get(index).secondSide(),target,0)); 
	    }
	    //else check if other domino second side is search 
	    else if(dominos.get(index).secondSide()==search){
		if(dominos.get(index).firstSide()==target){
		    return true; 
		}
		Vector<Domino> dominosClone = (Vector<Domino>)dominos.clone(); 
		dominosClone.remove(dominos.get(index)); 
		return( search(dominosClone,dominos.get(index).firstSide(),target,0) || search(dominos,search,target,++index)); 
	    }
	    
	    return search(dominos,search,target,++index); 
	    
	    

	}
    }


    public static void main(String args[]){
	Recursion test = new Recursion(); 
	//Longest Subsequence 
	//System.out.println(test.longest("recursion","c-u-r-s-e","")); 

	//Working CellPhone Mind Reader
	//Lexicon lexicon = new Lexicon("lexicon.dat");
	//ListCompletions completions = new ListCompletions("72547",lexicon); 

	//Chain Exists Dominos
	Vector<Domino> dominos = new Vector<Domino>(5); 
	dominos.add(new Domino(6,2)); 
	dominos.add(new Domino(4,3));
	dominos.add(new Domino(2,5)); 
	dominos.add(new Domino(5,6)); 
	dominos.add(new Domino(1,5)); //populate dominos 
	System.out.println(test.chainExists(dominos,6,1)); 
	
    }

    
    
}

//Domino Class 
class Domino {
    protected int side1, side2;
    public Domino(int side1, int side2) {
	this.side1 = side1;
	this.side2 = side2;
    }
    public int firstSide() { return side1; }
    public int secondSide() { return side2; }
}

class ListCompletions{
    Lexicon lex; 
    public ListCompletions(String digitSequence, Lexicon lex){
	this.lex = lex; 
	helper(digitSequence, "", lex, 0); 
    }
    public void helper(String digitSequence, String soFar, Lexicon lex, int index){
	if(index<digitSequence.length()){
	    int key = digitSequence.charAt(index)-48; 
	    Vector<Character> chars = intToChar(key);
	    //System.out.println("char:" + key);
	    for(int i = 0; i<chars.size(); i++){
		if(lex.containsPrefix(soFar + chars.get(i))){
		    helper(digitSequence,soFar + chars.get(i),lex,index+1); 
		}
	    }
	}
	else{
	    helperII(soFar,lex); 
	}
    }
    public void helperII(String soFar,Lexicon lex){
	//System.out.println(soFar); 
	try{
	if(lex.contains(soFar)) System.out.println(soFar); 
	}
	catch( StringIndexOutOfBoundsException nfe ){
	    System.out.println(soFar); 
	}
	if(lex.containsPrefix(soFar)){
	    for(int i = 97; i<123; i++){
		char c = (char)i;
		helperII(soFar+c,lex); 
	    }
	}
    }
    public Vector<Character> intToChar(Integer n){
	Vector<Character> chars = new Vector<Character>(3); 
	if(n==2){
	    chars.add('a');
	    chars.add('b');
	    chars.add('c');
	}
	else if(n==3){
	    chars.add('d');
	    chars.add('e');
	    chars.add('f');
	}
	else if(n==4){
	    chars.add('g');
	    chars.add('h');
	    chars.add('i');
	}
	else if(n==5){
	    chars.add('j');
	    chars.add('k');
	    chars.add('l');
	}
	else if(n==6){
	    chars.add('m');
	    chars.add('n');
	    chars.add('o');
	}
	else if(n==7){
	    chars.add('p');
	    chars.add('q');
	    chars.add('r');
	    chars.add('s');
	}
	else if(n==8){
	    chars.add('t');
	    chars.add('u');
	    chars.add('v');
	}
	else{
	    chars.add('w');
	    chars.add('x');
	    chars.add('y');
	    chars.add('z');

	}
	return chars; 
    }



}