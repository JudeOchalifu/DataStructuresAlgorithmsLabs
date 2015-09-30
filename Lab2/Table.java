
import structure5.*; 
import java.util.Random; 

//A class that stores the frequency list of several character combination 
public class Table{
    
    private Vector<Association<String,FrequencyList>> table; 
    
    public Table(){
	table = new Vector<Association<String,FrequencyList>>(); 

    }

    //Update the frequency in which certain words follow a character combination.  If the character combination
    //has not been stores in the table vector, add it.  

    //pre: input is k+1 in length 
    //post: the frequency list for the k character combination has been updated or created 
    public void add(String character){

	character = character.toLowerCase(); 
	String key = character.substring(0,character.length()-1); 
	String next = character.substring(character.length()-1); 

	//if the given character combination exists, update its frequency list 
	for(int i = 0; i<table.size(); i++){
	    if(table.get(i).getKey().equals(key)){
		table.get(i).getValue().add(next); 
		return; 
	    }
	}
	//else, create a new character combination 
	FrequencyList frequencyList = new FrequencyList();
	frequencyList.add(next); 
	table.add( new Association(key,frequencyList) );
    }
    
    //from a string, find its frequencylist and generate its most probably next character
    public String pickNext(String str){
	for(int i = 0; i<table.size(); i++){
	    if( table.get(i).getKey().equals(str) ){
		return table.get(i).getValue().pickNext(); 

	    }
	}

	//if the string key is not found, you cannot default to choosing a random character combination as 
	//doing so would create a self perpetuating cycle of unknwon character combinations being created again 
	//and again.  Instead, return a random table KEY that has known frequencies to get the 
	//pickNext method on track again. 
	Random random = new Random();	
	return table.get( random.nextInt(table.size()) ).getKey(); 

    }

    //Call the toString of the frequencylist of every character combination in table 
    public String toString(){
	String result = ""; 
	for(int i = 0; i<table.size(); i++){
	    result = result + table.get(i).getKey() + " 's Frequency List: " + "\n" + table.get(i).getValue().toString() + "\n"; 
	}
	return result; 
    }
}