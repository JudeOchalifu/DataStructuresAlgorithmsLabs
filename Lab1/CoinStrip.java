import java.util.Scanner;
import java.util.Random; 

/*
Kyle Cheng Lab1 CoinStrip 

This program simulates a simple game in which two players take turns moving coins to the left.  The last player who has moved 
the coin so that no coins can be moved wins the game. 

*/
/*
How might one pick game sizes so that one has a 50% chance of three coins, 25% chance of four coins, 12.5% chance of five coins and so on, would your technique bias your choice of underlying data structure?

You could have an array of let's say 100 length.  50 of its elements would be 3.  25 of its elements would be 4.  12.5 => 12 of its elements would be 5 and so on.  You could create a loop to fill in the spaces of the array with the appropriate amount of coins.  Each iteration in the loop should fill in (2i/100)*arraylength number of elements in your given array.  After, use a random generator random.nextInt(100); to choose an integer from the coin array.  A Vector data structure might be preferable to an array depending on how decimals are treated, since change #n (2n/100) could be rounded up or down one element.  As a result, the total elements in what would have been an array of 100 might change.  


How might one generate games that are not immediate wins?  suppose you wanted to be guaranteed a game with the possiblity of n moves?

Generating games that are not immediate wins can be done by having a random int generator that places each of the coins a random distance > 1 from each other.  By n moves, do you mean max moves or minimum moves to win?  If we are looking at minimum moves to win, each coin that is more than 1 distance away from the closest coin to its left will generate an additional move.  Thus, if the first part of this question is followed, each coin should generator one more move away from a win.    

Suppose the computer could occasionally provide good hints?  What opportunities appear easy to recognize?

The easiest opportunity would be the last choice, to move the only remaining coin the farthest most to the left.  Otherwise, based on the assumption that the opponent will move the left most coin all the way to its maximum left direction, if there are an odd number of coins left, the computer should "delay" and not move a coin the farthest to the left, and wait for the opponent to do so.  Therefore, if each player moves each coin its most to the left, the computer will win.  

How might you write a method, computerPlay, where the computer plays to win?

The logic would be similar to the answer above.  The computer needs to make assumptions about what the opponent will do.  The computer needs to "delay" moving a coin all the way to its left if there are odd moves left until all coins are moved to the left (assuming that each player would move the coin closest to the left all the way to the left).  Most of the computers logic will be focused on restricting the number of moves left to an even number (assuming that the computer just went).  By assuring an even number of turns is left after each of its decisions, the computer guarantees that it is the last player to move (the winner).  Should the human player attempt to "delay" moving a coin in order to ensure that there are even moves left to winning, the computer should either move coins all the way to the left or 1 move from max left direction to change the even/odd balance before the game ends.  

A similar game, Welter's game, allows coins to pass each other, would this modification of the rules change your implementation significantly?  

It would change the logic of any ai greatly, since the odd/even delay tactics mentioned above would be altered.  However, for the program's implementation, the effect if this new rule would simply involve removing the check for coins passing each other.  Instead, the only check would be whether or not the move would place two coins on top of each other.  Overall, this new rule would simplify implementation by reducing the checks needed to validate a player's move.  
 */


public class CoinStrip{

    //Array of the game (coin) pieces 
    int[] gameBoard; //how do you find and replace text?  I wanted to change the name of this variable but the tutorials online for finding/replacing text did not work.  

    //Total amount of spaces 
    int boardSize;


    //Random for placing initial coins 
    Random placer = new Random(); //is it more efficient to declare it local but create a new Random() each time?

    //User view 
    String[] display; 

    //Gameplay variables 
    boolean p1Playing = true; 
    boolean playing = true; 

    //Get the size and # of coins from user inputs then initialize the game 
    public static void main(String args[]){
	
	//Get user input 
	Scanner in = new Scanner(System.in); 
        System.out.print("How large is your gameboard? "); 
	int size = in.nextInt();
	System.out.print("How many coins? "); 
	int coins = in.nextInt(); 

	//Initialize the game based on user input 
	CoinStrip strip = new CoinStrip(coins,size); 
    }

    //Use input parameters to intialize the gameboard.  Start a while loop that runs while the game has not finished 
    //Pre: coin and size are type int 
    //Post: start the coinStrip game until no moves exist 
    public CoinStrip(int coins, int size){

	//Initialize coin array and board display, etc. 
	gameBoard = new int[coins]; 
	boardSize = size;	
	display = new String[boardSize]; 

	//Randomly fill "coins" Spots On Gameboard 

	//For the total number of coins 
	for(int i = 0; i<coins; i++){
	    //Assign position to each coin 
	    gameBoard[i] = placer.nextInt(boardSize); 
	    //Check that new position has not been assigned already 
	    check(i);  
	} 

	//Show the initial gameboard 
	System.out.println("=================================================================" + "\n"); 

	System.out.println(this.toString()); 
	System.out.println("\n" + "=================================================================" + "\n"); 

	//While coins can still be moved 
	while(playing){

	    //Scanner to read user input 
	    Scanner in = new Scanner(System.in);

	    //Display who's playing 
	    if(p1Playing){ System.out.println("\n" + "Player 1:"); }
	    else{ System.out.println("\n" + "Player 2:"); }

	    //Instructions 
	    System.out.println("\n" + "Choose coin, move coin : cn ns"); 
	    
	    //Extract user input 
	    int coin = in.nextInt(); 
	    int move = Math.abs(in.nextInt()); 

	    //Check coin 
	    if( (coin>=0 && coin < gameBoard.length)){

		//Check move 
		int oldPos = gameBoard[coin]; 
		int newPos = gameBoard[coin]-move; 

		//Check that newPos is not out of bounds and that coin does 
		//not pass another coin 
		if(newPos>=0){
		    for(int i = 0; i<gameBoard.length; i++){
			if(i != coin){
			    if(gameBoard[i]>=newPos && gameBoard[i]<oldPos){
				System.out.println("invalid move"); 
				oldPos = -1; 
				break;
			    }
			}	
		    }
		    //If all inputs are correct, move the coin, check for win, and change player 
		    if(oldPos!=-1){
		    gameBoard[coin] = gameBoard[coin]-move; 
		    p1Playing = !p1Playing; 
		    }
		}
		else System.out.println("move out of bounds"); 
	    }
	    else System.out.println("move a valid coin"); 

	    //display results

	    System.out.println("\n" + "=================================================================" + "\n"); 

	    System.out.println(this.toString()); 
 
	    System.out.println("\n" + "=================================================================" + "\n");
 
	    checkWin(); //check win is here, because it relies on display updating the string array first 
	}

    }

    //Pre: game initialized 
    //Post: do nothing, or if game won, print the winner and end the game 
    public void checkWin(){

	boolean won = true; 

	//gameBoard.length is the length of an array of game (coin) pieces 
	for(int i = 0; i < gameBoard.length; i++){
	    if(display[i].equals("_|")){
		won = false; 
		break; 
	    }
	}

	if(won){
	    playing = false; 
	    System.out.println( "\n" + "GAME OVER" ); 
	    if(!p1Playing){
		System.out.println("player 1 wins"); 
	    }
	    else{
		System.out.println("player 2 wins"); 
	    }
	    
	}
    }
    //Pre: input is an integer 
    //Post: boolean, check if two coins have same position 
    public void check(int i){
	for(int j = 0; j<i; j++){
	    if(gameBoard[i] == gameBoard[j]){
		gameBoard[i] = placer.nextInt(boardSize);
		//recheck that new position does not overlap again 
		check(i); 
	    }
	 }
    }

    //toString return string 
    //Post: return a string representation of the game board 
    public String toString(){

	String result = ""; 

	//initialize all spaces on board with default graphic 
	for(int i = 0; i<boardSize; i++){
	    display[i] = "_|"; 
	}

	//initialize graphic for filled spaces on board 
	for(int i = 0; i<gameBoard.length; i++){
	    display[ gameBoard[i] ] = Integer.toString(i)+"|";
	    }

	//get all elements of display 
	for(int i = 0; i<display.length; i++){
	    result = result + display[i]; 
	}

	return result; 

    }


}