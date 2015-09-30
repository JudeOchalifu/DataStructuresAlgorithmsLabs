
import structure5.*; 
import java.util.Random; 

//A class to store the a word and its frequency.  Works with table class to store the frequency in which 
//certain words follow a given character combination.  
public class FrequencyList{

    //Stores the main frequency list 
    private Vector<Association<String,Integer>> frequencyList; 

    //Count of total times that frequency list is updated 
    private int total; 

    //Constructor 
    public FrequencyList(){

	frequencyList = new Vector<Association<String,Integer>>(); 

	
    }

    //Update the frequency that a given character appears after this string 
    //pre: input is a character
    //post: the frequency lists have updated the new character 
    public void add(String character){

	//Make sure that only single words are inputed 
	Assert.pre(character.length()==1,"Must input character"); 

	character = character.toLowerCase(); 

	//check if frequencyList contains character 
	//if we update an existing character frequency, signal to create new frequency
	for(int i = 0; i<frequencyList.size(); i++){
	    if(frequencyList.get(i).getKey().equals(character)){
		frequencyList.get(i).setValue( frequencyList.get(i).getValue()+1 );
		total ++; 
		return; 
	    }
	}
	frequencyList.add( new Association<String,Integer>(character,1)); 
	total ++; 
    }

    //Randomly choose a character to follow the given character combination based on previous 
    //character frequencies 
    public String pickNext(){

	//Choose a random number less than total then iterate through the frequency list until the 
	//random number is less than 0 

	Random random = new Random(); 
	int i = 0; 
	int result = random.nextInt(total) - frequencyList.get(i).getValue();  

	while(result>0){
	    i++; 
	    result -= frequencyList.get(i).getValue(); 
	    
	}

	return frequencyList.get(i).getKey(); 
	
	//Old method involving random vector element choosing 
	/*
	Vector<String> totalList = new Vector<String>(); 
	for(int i = 0; i<total; i++){
	    for(int j = 0; j<frequencyList.get(i).getValue(); j++){
		totalList.add(frequencyList.get(i).getKey()); 
	    }
	}
	Random random = new Random(); 
	System.out.println(totalList.size()); 
	return totalList.get( random.nextInt(totalList.size()) ); 
	*/
	
    }

    //Output the given frequencies that certain characters follow this given character combination 
    public String toString(){
	String result = ""; 
	for(int i = 0; i<frequencyList.size(); i++){
	    result = result + frequencyList.get(i).getKey() + "=" + Integer.toString(frequencyList.get(i).getValue()) + "\n"; 
	}
	return result; 
	
    }

}