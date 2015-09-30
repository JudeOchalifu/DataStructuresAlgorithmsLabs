
/*
The main class for running the game 
 */
public class HexaPawn{

    public static void main(String args[]){
	HexBoard board = new HexBoard(Integer.parseInt(args[0]),Integer.parseInt(args[1])); 
	Player Player1 = getPlayer(args[2],HexBoard.WHITE);
	Player Player2 = getPlayer(args[3],HexBoard.BLACK); 

	Player1.play(new GameTreeNode(board,HexBoard.WHITE),Player2); 
    }

    //Creates a player based on input 
     public static Player getPlayer(String player,char side){
	if(player.equals("human")){
	    return new HumanPlayer(side); 
	}
	else if(player.equals("random")){
	    return new RandomPlayer(side); 
	}
	else{
	    return new CompPlayer(side); 
	}
    }
	

	
}