
import structure5.*;

/**
 * This module includes the functions necessary to keep track of the
 * creatures in a two-dimensional world. In order for the design to be
 * general, the interface adopts the following design: <p>
 * 1. The contents have generic type E.  <p>
 * 2. The dimensions of the world array are specified by the client. <p>
 * There are many ways to implement this structure.  HINT: 
 * look at the structure.Matrix class.  You should need to add no more than
 * about ten lines of code to this file.
 */

public class World<E> { 
    //a gameboard of objects E
    private Object[][] gameBoard; 

   

    /**
     * This function creates a new world consisting of width columns 
     * and height rows, each of which is numbered beginning at 0. 
     * A newly created world contains no objects. 
     */
    public World(int row, int column) {
	gameBoard = new Object[column][row]; 
	WorldMap.createWorldMap(column,row); 
    }

    /**
     * Returns the height of the world.
     */
    public int height() {
	return gameBoard[0].length; 
    }

    /**
     * Returns the width of the world.
     */
    public int width() {
	return gameBoard.length;
    }

    /**
     * Returns whether pos is in the world or not.
     * @pre  pos is a non-null position.
     * @post returns true if pos is an (x,y) location in 
     *       the bounds of the board.
     */
    boolean inRange(Position pos) {
	return ( pos.getX()<this.width() && pos.getY()<this.height() && pos.getX()>=0 && pos.getY()>=0 );
    }

    /**
     * Set a position on the board to contain c.
     * @pre  pos is a non-null position on the board.
     */
    public void set(Position pos, E c) {
	if( this.inRange(pos) ){
	    gameBoard[pos.getX()][pos.getY()] = c; 
	}
    }

    /**
     * Return the contents of a position on the board.
     * @pre  pos is a non-null position on the board.
     */
    public E get(Position pos) {
	if ( this.inRange(pos) && gameBoard[pos.getX()][pos.getY()]!=null){
	    return (E)gameBoard[pos.getX()][pos.getY()]; //compiler gives me a warning here... how come?
	}
	return null; 
    }

    //test code to demonstrate the world works correctly, deactivate by default 
    public static void main(String s[]) {
	/*
	World<Integer> test = new World(10,10); 
	System.out.println(test.height()); 
	test.set( new Position(4,4) , 5 ); 
	System.out.println( test.get( new Position(4,4) )); 
	*/

    }
	
}
