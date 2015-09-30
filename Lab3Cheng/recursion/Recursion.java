/*
 * Recursion.java
 *
 * Kyle Cheng 
 *
 * Starter code for the recursion lab.
 *
 */
import structure5.*;

public class Recursion {

    //static methods just helper methods that can be called from main 


    /*****  1  ***************************************************/

    /*
     * Return number of cannoballs in pyramid with the given height.
     n = height 
     O(n) 
     */
    public static int countCannonballs(int height) {
	if(height != 1){
	    return countCannonballs(height-1) + height*height; 
	}
	else{ //height == 1
	    return 1; 
	}
    }


    /*****  2  ***************************************************/

    /*
     * Return true if str is a palindrome.
     n = str.length
     O(n) 
     */
    public static boolean isPalindrome(String str) {
	if(str.length() <= 1){
	    return true; 
	}
	else{
	    return isPalindrome(str.substring(1,str.length()-1)) && (str.charAt(0) == str.charAt(str.length()-1)); 
	}
    }

    /*****  3  ***************************************************/

    /*
     * Return true if str is a string of matched parens,
     * brackets, and braces.
     n = str.length 
     O(n^2) assuming that we find the bracket at the end of the string after every linear search 
     */
    public static boolean isBalanced(String str) {

	if(str.length()!=0){
	    int index = str.indexOf("()"); 
	    if(index==-1) index = str.indexOf("{}"); 
	    if(index==-1) index = str.indexOf("[]"); 

	    if(index!=-1) return isBalanced( str.substring(0,index) + str.substring(index+2)); 
	    else return false; 
	}
	return true; 

    }

    /*****  4  ***************************************************/

    /*
     * Print all substrings of str.  (Order does not matter.)
     n = str.length
     O(2^n)
     */
    public static void printSubstrings(String str) {

	substringHelper(str,""); 
    }
    public static void substringHelper(String str, String soFar){
	if(str.equals("")) System.out.println(soFar); 
	else{
	    substringHelper( str.substring(1), str.charAt(0) + soFar );
	substringHelper( str.substring(1), soFar );
	}
	
    }
    /*****  5  ***************************************************/

    /*
     * Print number in binary
     n = number 
     O( log base 2 of n )
     */ 
    public static void printInBinary(int number) {	
	if(number>=1){	    
	    printInBinary( (number-(number%2))/2 ); 
	    System.out.print( number%2 ); 
	}
	else System.out.print( number ); 
    }

    /*****  6  ***************************************************/

    /*
     * Return whether a subset of the numbers in nums add up to sum,
     * and print them out.
     n = setOfNums.length
     O(2^n)
     */
    public static boolean printSubSetSum(int setOfNums[], int target) {
        return printSubSetSumHelper(setOfNums, target, 0);
    }
    protected static boolean printSubSetSumHelper(int set[],int target,int index) {
	if (set.length == index){
	    return target == 0; 
	    
	}
	if ( printSubSetSumHelper(set, target - set[index], index + 1) ){
		System.out.println( set[index]);
		return true; 
        }
	return printSubSetSumHelper(set, target, index + 1); 	
    }

    /*
     * Return the number of different ways elements in nums can be
     * added together to equal sum.
     n = setOfNums.length
     O(2^n)
     */
    public static int countSubSetSumSolutions(int nums[],int target) {
	return countSubSetSumSolutionsHelper(nums, target, 0);
    }
    protected static int countSubSetSumSolutionsHelper(int set[],int target,int index) {
        if (set.length == index) {
	    if(target==0)
		return 1; 
	    return 0; 
        } else {
            return countSubSetSumSolutionsHelper(set, target - set[index], index + 1) +
                countSubSetSumSolutionsHelper(set, target, index + 1);
        }
    }



    /*****  7  ***************************************************/
    
    public static void listCompletions(String digitSequence, 
				       Lexicon lex) {
    
    }


    /**************************************************************/
    
    /*
     * Add testing code to main to demonstrate that each of your
     * recursive methods works properly.
     */
    public static void main(String args[]) {
	
	
	// test code for problem 1
	System.out.println("count cannonballs 3 and 10"); 
	System.out.println(countCannonballs(3));
	System.out.println(countCannonballs(10));

	// test code for problem 2 
	System.out.println("isPalindrome for hih , dfjsdlfjskldfjwe , hallah"); 
	System.out.println( isPalindrome("hih")); 
	System.out.println( isPalindrome("dfjsdlfjskldfjwe")); 
	System.out.println( isPalindrome("hallah"));
	

	// test code for problem 3
	System.out.println("isBalanced for {}({})[()] and [[))[]({}) and {(})"); 
	System.out.println(isBalanced("{}({})[()]")); 
	System.out.println(isBalanced("[[))[]({})"));
	System.out.println(isBalanced("{(})")); 

	// test code for problem 4 
	System.out.println("substrings for ABC"); 
	printSubstrings("ABC"); 

	// test code for problem 5 
	System.out.println("Binary for 35");
	printInBinary(35); 
	System.out.println(""); 

	// test code for problem 6 
	System.out.println("a subset of  -1,3,6,5,1,2,4 to sum 4"); 
	int a1[] = new int[] { -1,3,6,5,1,2,4 };
	System.out.println( printSubSetSum(a1,4) ); 

	// test code for problem 7 
	System.out.println("number of subsets for -1,3,6,5,1,2,4"); 
	System.out.println(countSubSetSumSolutions(a1, 4)); 
	
    }
}
