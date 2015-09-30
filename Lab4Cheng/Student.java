
//A class for storing information about Williams students 
public class Student{
    //info of each student 
    private String name;
    private String address; 
    private long campusPhone; 
    private long boxNumber; 
    private long homePhone; 

    public Student(String name, String address, long campusPhone, long boxNumber, long homePhone){
	this.name = name; 
	this.address = address; 
	this.campusPhone = campusPhone;
	this.boxNumber = boxNumber; 
	this.homePhone = homePhone; 
    }
    //Get methods for all information about a student 
    public String getName(){
	return name; 
    }
    public String getAddress(){
	return address; 
    }
    public long getCampusPhone(){
	return campusPhone;
    }
    public long getBoxNumber(){
	return boxNumber; 
    }
    public long getHomePhone(){
	return homePhone; 
    }

    public String toString(){
	return getName() + "\n" + getAddress() + "\n" + getCampusPhone() + "\n" + getBoxNumber() + "\n" + getHomePhone() + "\n"; 
    }

}