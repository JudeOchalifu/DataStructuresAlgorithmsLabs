
import structure5.*;
import java.util.Comparator;

//A class that stores objects of type E 
public class MyVector<E> extends Vector<E> {
    //static MyVector<String> test; 

    public MyVector(){
	super(); 
    }

    //insertion sort sorts data based ont he values that a comparator c returns 
    public void insertionSort(Comparator<E> c){
	int n = size(); 
	int numSorted = 1; 
	int index; 
	while(numSorted<n){
	    E temp = get(numSorted); 
	    for(index = numSorted; index>0; index--){
		if( c.compare(temp,get(index-1))<0){
		    set(index,get(index-1)); 
		}
		else{
		    break; 
		}	
	    }
	    set(index,temp); 
	    numSorted ++; 
	}
    }

}

//COMPARATORS 

//Compare which name comes first alphabetically 
class NameComparator implements Comparator<Student>{
    //pre, inputs are Student
    //post:
    // <0 a alphabetically before b 
    // >0 a alphabetically after b 
    public int compare(Student studentA, Student studentB){
	String nameA = studentA.getName();
	String nameB = studentB.getName(); 
	int index = 0; 
	int result = 0; 
	int min = Math.min(nameA.length(),nameB.length()); 
	while(index<min && result==0){
	    result = (int)nameA.charAt(index)-(int)nameB.charAt(index); 
	    index++; 
	}
	return result; 
    }
}

//Compare which names have the most vowels 
class VowelComparator implements Comparator<Student>{
    //pre, inputs are Student 
    //post: 
    // <0 nameA more vowels than nameB
    // >0 nameA less vowels than nameB
    public int compare(Student nameA, Student nameB){
	return readVowels(nameB) - readVowels(nameA); 
    }
    //Helper method that counts the amount of vowels in a name 
    protected int readVowels(Student aName){
	String name = aName.getName(); 
	name = name.toLowerCase(); 
	int result = 0; 
	for(int i = 0; i<name.length(); i++){
	    String A = Character.toString(name.charAt(i)); 
	    if(A.equals("a") || A.equals("e") || A.equals("i") || A.equals("o") || A.equals("u")){
		result ++; 
	    }
	}
	return result; 
    }
}

//Return a MyVector of Assocations linking a given address with its frequency 
//Compare the frequencies of two Assocations 
class AddressComparator implements Comparator<Association<String,Integer>>{

    //Create the MyVector of Addresses and their frequencies, adding a new association if the given address has not appeared 
    public static MyVector<Association<String,Integer>> updateAddresses(MyVector<Student> students){

	MyVector<Association<String,Integer>> addressFrequency = new MyVector<Association<String,Integer>>(); 

	for(int i = 0; i<students.size(); i++){

	    String address = students.get(i).getAddress(); 
	    if(!address.equals("UNKNOWN")){
	    
		boolean updated = false; 

		//Check if the given address has already appeared, and if it has, update the frequency, else create a new assocation for the new address 
		for(int j = 0; j<addressFrequency.size(); j++){
		    if(addressFrequency.get(j).getKey().equals(address)){
			addressFrequency.get(j).setValue( addressFrequency.get(j).getValue()+1 ); 
			updated = true; 
			break;
		    }
		}
		if(!updated){
		    addressFrequency.add( new Association<String,Integer>(address,1) );
		}
	    }

	}
	return addressFrequency; 

    }
    //Pre: comparing two Assocation
    //Post: 
    // <0 Assocation A more frequent than Assocation B
    // >0 Association A less frequent than Association B 
    public int compare(Association<String,Integer> A, Association<String,Integer> B){
	return B.getValue() - A.getValue(); 	
    }
}

//Sort a MyVector based on frequencies of AreaCodes, same as top class but with different search parameters ie. Area Code 
//Compare the frequencies of Area Code Associations 
class AreaCodeComparator implements Comparator<Association<Integer,Integer>>{

    //Same as the above updateAddresses, but sorts/creates a vector based on area code frequencies 
    public static MyVector<Association<Integer,Integer>> updateNumbers(MyVector<Student> students){
	MyVector<Association<Integer,Integer>> numberFrequency = new MyVector<Association<Integer,Integer>>(); 
	for(int i = 0; i<students.size(); i++){

	    if(students.get(i).getHomePhone() != -1){
		Integer areaCode = (int)(students.get(i).getHomePhone()/Math.pow(10,7)); 
	    
	    
		boolean updated = false; 

		for(int j = 0; j<numberFrequency.size(); j++){
		    if(numberFrequency.get(j).getKey().equals(areaCode)){
			numberFrequency.get(j).setValue( numberFrequency.get(j).getValue()+1 ); 
			updated = true; 
			break;
		    }
		}
		if(!updated){
		    numberFrequency.add( new Association<Integer,Integer>(areaCode,1) );
		}
	    }
	}

    
	return numberFrequency; 
    }


    //Pre: comparing two Assocation 
    //Post: 
    // <0 Assocation A more frequent than Assocation B
    // >0 Association A less frequent than Association B 
 public int compare(Association<Integer,Integer> A, Association<Integer,Integer> B){
	return B.getValue() - A.getValue(); 	
 }
}


