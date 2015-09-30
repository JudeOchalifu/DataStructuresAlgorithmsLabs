
import structure5.*; 
import java.util.Random; 
import java.io.PrintWriter; 
import java.io.FileNotFoundException;

/*
Kyle Cheng

Graph creates vectors of varying sizes filled with integers and has static methos that can sort these methods.  The plot method is used to time how long different sorting methods take to sort the data.  The time it takes to sort data for a given vector size is then written to a text file that can be read by gnuplot.  

There were no starter files for this extra lab, the ones listed on the website have code for Student, and PhoneBook which appear to be part of the real lab.  I tried to guess what you wanted in the lab.  

 */

public class Graph {

    public void Graph(){

    }

    //Creates vectors of integers and populates them.  Keeps track of time taken per each sort 
    //xRange is number of vectors to sort
    //yRange is range of random values to populate the vector
    //fileName is name of file that we print out to 
    //boolean quickSort determines which sort will be called 
    public void plot(int xRange, int yRange, String fileName, boolean quickSort){
	//PrintWriter to write to txt file 
	try{
	PrintWriter writer = new PrintWriter(fileName + ".txt"); 
	
	//For xRange, create vectors 
	for(int i = 1; i<=xRange; i++){
	   
	    Vector<Integer> toSort = this.generateIntVector(i,yRange);  

	    
	    //test time taken here 
	    long startTime = System.currentTimeMillis(); 
	    if(!quickSort)
		this.selectionSort(toSort,toSort.size()); 
	    else 
		this.quickSort(toSort,toSort.size()); 
	    long endTime = System.currentTimeMillis(); 
	    long duration = endTime - startTime; 

	    //write to file here 
	    writer.println(i + " " + duration); 

	    //allow user to monitor progress 
	    System.out.println(xRange - i); 

	}

	writer.close(); 
	
	}catch(FileNotFoundException fnfe){
	    System.out.println(fnfe.getMessage()); 
	}

    }

    //Generates vector or "size" with random values to range 
    public Vector<Integer> generateIntVector(int size, int range){
	Random random = new Random(); 
	Vector<Integer> theVector = new Vector<Integer>(size); 
	for(int i = 0; i<size; i++){
	    theVector.add(random.nextInt(range+1)); 
	}
	return theVector; 
    }

    public static void selectionSort(Vector<Integer> vector, int n)
    {
        int numUnsorted = n;
        int index;      // general index
        int max;        // index of largest value
        while (numUnsorted > 0){
            // determine maximum value in array
            max = 0;
            for (index = 1; index < numUnsorted; index++)
            {
                if (vector.get(max) < vector.get(index)) max = index;
            }
            swap(vector,max,numUnsorted-1);
            numUnsorted--;
	}
    }
    public static void swap(Vector<Integer> vector, int i, int j){
	int temp;
	temp = vector.get(i);
        vector.set(i,vector.get(j)); 
	vector.set(j,temp); 
    }

    public static void quickSort(Vector<Integer> vector, int n)
    {
        quickSortRecursive(vector,0,n-1);
    }
    private static void quickSortRecursive(Vector<Integer> vector,int left,int right)

    {
        int pivot;   // the final location of the leftmost value
        if (left >= right) return;
        pivot = partition(vector,left,right);    
        quickSortRecursive(vector,left,pivot-1); 
        quickSortRecursive(vector,pivot+1,right);
    }
    private static int partition(Vector<Integer> vector, int left, int right)
    {
        while (true)
        {
	    //System.out.println(left); 
            // move right "pointer" toward left
            while (left < right && vector.get(left) < vector.get(right)) right--;
            if (left < right) swap(vector,left++,right);
            else return left;
            // move left pointer toward right
            while (left < right && vector.get(left) < vector.get(right)) left++;
            if (left < right) swap(vector,left,right--);
            else return right;
	} 
    }

    public static void main(String args[]){
	Graph test = new Graph();
	//Sort 8000 vectors starting with size 1 to 8000 
	//the random values placed in the vectors can range from 0 to 3000
	test.plot(8000,3000,"selectionSort",false);  
	test.plot(8000,3000,"quickSort",true); 
    }
    //I ran this program, and while selectionSort took around 5 to 10 minutes, when I ran quickSort it finished it
    //about 25 seconds...  I thought my sorting was glitched but when I told the computer to print out the sorted vector, all the vectors were sorted!  but is it really that much faster?!...
}
