import java.util.Scanner; 

//A program that creates random text based on the probabilities that certain characters follow 
//certain character combinations 
/*
3.2
- add(v) adds an Object v to the end of the vector while add(i,v) adds an Object v at index i of the vector, 
and moves all other elements forward in the array (for the vector).

3.3
- set(i,v) will override element i of the array with Object v and return the previous element 
that existed at i of the array. (array means the array for the vector) 

- add(i,v) will not override the element i and will instead move all of the existing array 
items to the right and increase the size by 1

3.4
- remove(i) removes the element at index i of the array for the vector  remove(v) loops 
through the vector's array until a match is found between v and a given object in the vector. 
The given object is then removed from the vectory's array. 

3.6
- In hangman, we do not know how long the randomly generated secret word will be.  Vectors gives us 
the ability to create words of varying length without having to worry about explicitly keeping 
track of the word's length (just use size methdo) or having to create an array with the proper length


 */
public class WordGen{
    //length of random gen text 
    private static final int OUTPUT_LENGTH = 500; 

    public static void main(String args[]){
	WordGen test = new WordGen(Integer.parseInt(args[0])); 
    }
    
    public WordGen(int k){
	//Read the input text
	Scanner in = new Scanner(System.in); 
	StringBuffer textBuffer = new StringBuffer(); 
	while(in.hasNextLine()){
	    String line = in.nextLine(); 
	    textBuffer.append(line); 
	    textBuffer.append("\n");
	}
	String input = textBuffer.toString(); 

	//Create a table to store all the character combinations and their 
	//associate frequency lists 
	Table table = new Table(); 

	//Read through the input text 
	int index = 0; 
	while(index != input.length()-1-k){
	    table.add( input.substring(index,index+1+k) ); 
	    index ++; 
	}

	//print out frequency list matches 
	//System.out.println(table.toString()); 

	//Print out randomly generated text 
	String output = input.substring(0,k+1); 
	int start = output.length(); 
	//for the output length, randomly return that characters that follow a given character combination
	for(int i = 0; i<OUTPUT_LENGTH; i++){
	    output = output + table.pickNext( output.substring( start-k, start) ); 
	    start ++;		       
	}
	//print out randomly generated 
	System.out.println(output); 
    }
}