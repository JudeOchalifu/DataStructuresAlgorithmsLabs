// An interface for a player of hexapawn.
// (c) 2000, 2001 duane a. bailey
import structure5.*; 
import java.util.Scanner; 
import java.util.Iterator; 
import java.util.Random; 

/*
This collection of classes holds the different player types that can
be deployed
 */


public interface Player
{
    // make sure your constructor accepts a char (HexBoard.WHITE or
    // HexBoard.BLACK) to play with.  This should be remembered.

    public void play(GameTreeNode node, Player opponent);
    // pre: node is a non-null game tree node
    //      opponent is the player to play after this player
    // post: game is played from this node on; winning player is returned
}

//An abstract class for general methods that all types of players should have 
abstract class BasePlayer implements Player{
    protected char side; 
    public String printPlayer(){
	if(side=='o') return "WHITE"; 
	else return "BLACK"; 
    }
    protected boolean checkWin(HexBoard board){
	if( board.win('o') ){
	    System.out.println("White wins!"); 
	    return true;
	}
	else if( board.win('*') ){ 
	    System.out.println("Black wins!"); 
	    return true; 
	}
	return false; 
    }
}

class HumanPlayer extends BasePlayer{


    //char side; 

    public HumanPlayer(char side){
	this.side = side; 
    }

    public void play(GameTreeNode node, Player opponent){
	System.out.println(node.getBoard().toString());
	if(!this.checkWin(node.getBoard())){      
	Scanner in = new Scanner(System.in); //would need to create scanner each time to prevent it from running at the wrong time in case two human players go against each other 
	Vector<HexMove> moves = node.getBoard().moves(side);

	//Print out moves available 
	Iterator i = moves.iterator();
	int j = 0; 
	while(i.hasNext()){
	    System.out.println(j+". "+i.next());
	    j++; 
	}
	//read move from keyboard 
	int yourMove = in.nextInt(); 


	//hand off to opponent 
	opponent.play(node.getMoveChild( moves.elementAt(yourMove) ) , this); 
	    

	}
	
    }
}

class RandomPlayer extends BasePlayer{

    //char side;

    public RandomPlayer(char side){
	this.side = side; 
    }

    public void play(GameTreeNode node, Player opponent){
	System.out.println(node.getBoard().toString()); 
	if(!this.checkWin(node.getBoard())){ 
	Random random = new Random(); 
	Vector<HexMove> moves = node.getBoard().moves(side); 
	int randomMove = random.nextInt(moves.size()); 
	System.out.println("Random " + moves.elementAt(randomMove)); 
	opponent.play(node.getMoveChild( moves.elementAt(randomMove)),this); 
	}
    }
}

class CompPlayer extends BasePlayer{
    
    //char side; 

    public CompPlayer(char side){
	this.side = side; 
    }
    //note hexmoves index = child index for GameTreeNode s 
    public void play(GameTreeNode node, Player opponent){
	System.out.println(node.getBoard().toString());
	if(!this.checkWin(node.getBoard())){
	    GameTreeNode bestNode = node.getBestMove(side=='*'); 
	    int index = node.getChildren().indexOf(bestNode);  
	    //AM I DOING THIS EFFICIENTLY?  since GameTreeNode.getBestMode only returns another GameTreeNode, I have to get the GameTreeNode's children, get the index, then get its board's moves, then match that index to the board's hex moves vector index (since they match) to get the hexMove that was done.  
	    System.out.println("Comp " + node.getBoard().moves(this.side).get(index)); 
	    opponent.play(node.getBestMove(side=='*'),this); 
	}
    }

}
 
