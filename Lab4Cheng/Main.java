
import structure5.*;
import java.util.Scanner;
import java.util.InputMismatchException; 

//A class that uses all the other classes to sort students based on names, vowels, addresses, and area codes 
public class Main{
    //Reads the text file of student info
    private FileReader test = new FileReader(); 

    //Vector to hold students or frequencies that students have a specific address or home phone number 
    private MyVector<Student> students;
    private MyVector<Association<String,Integer>> addressFrequency = new MyVector<Association<String,Integer>>();
    private MyVector<Association<Integer,Integer>> numberFrequency = new MyVector<Association<Integer,Integer>>(); 

    //Sort student data based on certain comparators and print out the results 
    public Main(){
	
	//update vectors 
	students = test.getStudents(); 
	addressFrequency = AddressComparator.updateAddresses(students); 	
	numberFrequency = AreaCodeComparator.updateNumbers(students); 
	
	//sort students based on one's name in alphabetical order 
	students.insertionSort(new NameComparator()); 
	System.out.println("First name in an address book: " + students.get(0).getName() ); 
	System.out.println();

	//sort students based on number of vowels that appear 
	students.insertionSort(new VowelComparator()); 
	System.out.println("Most vowels in full name: " + students.get(0).getName() ); 
	System.out.println(); 

	//sort students based on their common addresses 
	addressFrequency.insertionSort(new AddressComparator()); 
	System.out.println("Most common address is: " + addressFrequency.get(0).getKey()); 
	for(int j = 0; j<students.size(); j++){
	    if(students.get(j).getAddress().equals(addressFrequency.get(0).getKey())){
		System.out.println(students.get(j).getName()); 
	    }
	}

	System.out.println(); 
	
	//sort students based on their home phone numbers 
	numberFrequency.insertionSort(new AreaCodeComparator()); 
	System.out.println("Ten most common area codes"); 
	for(int k = 0; k<10; k++){
	    System.out.println(numberFrequency.get(k).getKey() + " " + numberFrequency.get(k).getValue()); 
	}

	
    }

    //call the program 
    public static void main(String args[]){
	Main program = new Main(); 

    }

}

// a class that reads in the text file from the computer 
class FileReader{

    //a vector of students to be filled based on what is read 
    private MyVector<Student> students = new MyVector<Student>(); 
    //reads in info
    private Scanner in = new Scanner(System.in); 
    private StringBuffer textBuffer = new StringBuffer(); 

    public FileReader(){

	//Read in the text file and extract the information for 
	//the student class 
	while(in.hasNextLine()){

	    //Read in info for the student object 
	    String name = in.nextLine();
	    String address = in.nextLine();

	    //Exception handling for reading nextLong() 
	    long campusPhone; 
	    try{ 
		campusPhone = in.nextLong();
	    }
	    catch (InputMismatchException e){
		campusPhone = -1; 
	    }

	    long boxNumber; 
	    try{ 
		boxNumber = in.nextLong();
	    }
	    catch (InputMismatchException e){
		boxNumber = -1; 
	    }

	    long phoneNumber; 
	    try{ 
		phoneNumber = in.nextLong();
	    }
	    catch (InputMismatchException e){
		phoneNumber = -1; 
	    }

	    
	    //Catch exception issues with faulty phone numbers, box numbers, etc. 
	    if(campusPhone < 1000000000){
		campusPhone = -1; 
	    }
	    if(boxNumber > 9999 || boxNumber < 1000){
		boxNumber = -1; 
	    }
	    if(phoneNumber < 1000000000){
		phoneNumber = -1; 
	    }

	    //create a new student object with this new information 
	    Student student = new Student(name, address, campusPhone, boxNumber,
					  phoneNumber); 
	    students.add(student); 

	    //skip the spaces in between each student 
	    in.nextLine();
	    in.nextLine();
	}
	//test that desych has not occured for reading lines 
	//System.out.println( students.get(1500).getName() );
	//System.out.println( students.get(1500).getHomePhone() ); 


    }
    
    //Return the filled students vector 
    public MyVector<Student> getStudents(){
	return students; 
    }

}    

