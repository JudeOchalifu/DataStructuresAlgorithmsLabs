
import structure5.*;

/*
A class that represents a single game tree node of a larger tree of possible moves.  Each node is in essence, a possible move on the board in addition to the possible moves at that given position in the board
 */

class GameTreeNode{
    public static int count = 0; //static keyword only initializes once 
    public Vector<GameTreeNode> children = new Vector<GameTreeNode>(); 
    //private GameTreeNode parent; 
    private boolean blackWin; 
    private boolean isLeaf; 
    private char color; 
    private HexBoard board; 

    //constructor for node game tree
    public GameTreeNode(HexBoard board, char color){
	this.board = board; 
	this.color = color; 
	//create children, if possible moves still exist 
	if( !board.win( HexBoard.opponent(color)) ){
	    Vector<HexMove> possibleMoves = board.moves(color); 
	    for(int i = 0; i<possibleMoves.size(); i++){
		//System.out.println(count); 
		count ++; 
		children.add( new GameTreeNode( new HexBoard(board,possibleMoves.get(i)),
						HexBoard.opponent(color)));
	    }
	}
	//Need to evaluate if the given node is a win for either side 
	else if(HexBoard.opponent(color)=='o'){ //White win 
	    blackWin = false; 
	    isLeaf = true; 
	} 
	else{ //Black win 
	    blackWin = true; 
	    isLeaf = true; 
	}
	
    }
    public Vector<GameTreeNode> getChildren(){
	return this.children;
    }
    public GameTreeNode getMoveChild(HexMove theMove){
	Vector<HexMove> possibleMoves = board.moves(color); 
	int index = possibleMoves.indexOf(theMove); 
	return children.get(index); 
    }

    public HexBoard getBoard(){
	return this.board; 
    }


    //Checks the win chance of each of its children (evaluates win chance of each possible move) and chooses the node with the best chance of winning 
    public GameTreeNode getBestMove(boolean isBlack){
	double bestWin = 0;
	GameTreeNode bestChoice = null; 
	for(int i = 0; i<children.size(); i++){
	    double winChance = children.get(i).getWinChance(isBlack);
	    if(winChance>bestWin){
		bestWin = winChance; 
		bestChoice = children.get(i); 
	    }
	}
	return bestChoice; 
    }

    //returns win % of a given node for a given color.  Evalutes win chance of a given node.  Recursive 
    protected double getWinChance(boolean isBlack){
	if(isLeaf){
	    if(isBlack){ //if won increase wins 
		if(blackWin) return 1;
		else return 0; 
	    }
	    else{
		if(blackWin) return 0; 
		else return 1; 
	    }
	}
	double wins = 0; 
	for(int i = 0; i<children.size(); i++){
	    wins += children.get(i).getWinChance(isBlack); 
	}
	return wins/(double)children.size(); 
    }

}
