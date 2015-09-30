import structure5.*;
import java.util.Iterator; 

//An iterator that iterates over a generic vector of variable type E.  For each iteration, the iterator returns a vector containing a different subset of the vector's elements.  The goal is to have all possible combinations of a given vector be returned by the get() method once hasNext() returns false.  We get all possible subsets of a given Vector by using a binary representation of the vector where 0 means do not add and 1 means add.  the size of this binary representation at its max must be equal to the vector size as each "bit" in this binary representation indicates whether or not we should add a given vector element to the returned vector.  We incremenet this binary representation by 1 each time.  
public class SubsetIterator<E> extends AbstractIterator<Vector<E>>{
    //Keep track of the current binary representation of the vector indexes we will use 
    private long currentSubset; 
    //Vector we are iterating over
    private Vector<E> vector;

    //Initialize the vector we are using and reset the increment counter 
    public SubsetIterator( Vector<E> vector ){
	this.vector = vector; 
	this.reset(); 

    }
    public void reset(){
	currentSubset = 0; 
    }
    //the max increment counter or subset we are looking at must be less than or equal to 2^vectorsize-1 as each element in the vector can be 0 or 1 so each new element would be *2 to all possible subset combinations.  We do vector.size()-1 since if vector size is 1, we should only have two possibilities, 0 and 1 for all of our possible subsets.  
    public boolean hasNext(){
	return (currentSubset<=Math.pow(2,vector.size())-1) ;
    }
    //return a vector that holds a possible subset of the parent vector 
    public Vector<E> get(){
	Vector<E> subset = new Vector<E>(); 
	//the maximum possible bits of currentSubset must be < vector.size() as each bit of the current subset represents a yes or no to add to the subset vector 
	for(int i = 0; i<vector.size(); i++){
	    // 1L << i multiplies 1 by 2^i.  This operation means that (1L << i) is our "mask" for accessing each bit of the currentSubset.  If the given bit is 1 we add the given vector index i to the subset vector to be returned.  
	    if( (currentSubset & (1L << i)) != 0 ){
		subset.add(vector.get(i)); 
	    }
	}
	return subset;
    }
    //While there exists another possible subset, return a vector with a subset and increment the currentSubset index or return null if we have returned all possible subsets 
    public Vector<E> next(){
	if(this.hasNext()){
	Vector<E> value = this.get(); 
	currentSubset ++; 
	return value; 
	}
	else return null; 
    }

    

}

//A class that uses the above subset iterator class to find the best tower height of a sqrt 1 to sqrt n set of height blocks if we were to attempt to create two towers with even height from our given blocks.  
class TwoTowers{

    public static void main(String[] args){
	/* //Prints 256 subsets test 
	Vector<Integer> numbers = new Vector<Integer>(); 
	for(int i = 0; i<8; i++){
	    numbers.add(i); 
	}
	SubsetIterator numberTest = new SubsetIterator(numbers); 
	while( numberTest.hasNext() ){
	    System.out.println( numberTest.next().toString() ); 
	}
	*/
	//get user input which determines how many blocks we wish to stack 
	int n = Integer.parseInt(args[0]); 
	//initialize a vector of block heights 
	Vector<Double> blocks = new Vector<Double>(); 
	for(int i = 1; i<=n; i++){
	    blocks.add( Math.sqrt(i) ); 
	}
	//target value or the ideal height of our tower
	double target = sum(blocks)/2.0; 

	//intialize the iterator 
	SubsetIterator blockTest = new SubsetIterator(blocks); 
	Vector<Double> bestSubset = blockTest.get();
	double bestSum; 

	//initialize the first bestSum vector and its best sum.  We need to make sure that our first best sum is less than target as all possible towers cannot exceed the target value.  
	if( sum(bestSubset) <= target)
	    bestSum = target - sum(bestSubset); 
	else 
	    bestSum = sum(blocks); 
	
	//iterate through all of the possible subsets that would make the second tower and update the bestSum and the blocks that would lead to bestSum if we find a better subset for our second tower.  
	while(blockTest.hasNext() ){
	    Vector<Double> currentVector = blockTest.next(); 
	    if( sum(currentVector) <= target && target - sum(currentVector) < bestSum){
		bestSubset = currentVector; 
		bestSum = target - sum(currentVector);
	    } 
	}
	//Give output 
	System.out.println( "Best Subset: " + bestSubset.toString() + " Best Height: " + sum(bestSubset) ); 
	System.out.println("Total Height: " + target*2); 
	System.out.println("Target Height: " + target); 
	System.out.println("Error: " + (target-sum(bestSubset))); 

    }
    
    //Helper method to sum all of the elements of a vector of doubles 
    public static double sum(Vector<Double> vector){
	double sum = 0; 
	for(int i = 0; i<vector.size(); i++){
	    sum += vector.get(i); 
	}
	return sum; 
    }

}