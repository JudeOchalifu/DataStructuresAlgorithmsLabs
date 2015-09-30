
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
 *
 * Author: Alex Wheelock
 * Version: 5/5/10
 */

public class World<E> { 

    private Matrix<E> grid;

    /**
     * This function creates a new world consisting of width columns 
     * and height rows, each of which is numbered beginning at 0. 
     * A newly created world contains no objects. 
     */
    public World(int w, int h) {
	grid = new Matrix<E>(h, w);
    }

    /**
     * Returns the height of the world.
     */
    public int height() {
	return grid.height();
    }

    /**
     * Returns the width of the world.
     */
    public int width() {
	return grid.width();
    }

    /**
     * Returns whether pos is in the world or not.
     * @pre  pos is a non-null position.
     * @post returns true if pos is an (x,y) location in 
     *       the bounds of the board.
     */
    boolean inRange(Position pos) {
	return (pos.getX() < grid.width()) && (pos.getY() < grid.height()) 
	    && (pos.getX() >= 0) && (pos.getY() >= 0);
    }

    /**
     * Set a position on the board to contain c.
     * @pre  pos is a non-null position on the board.
     */
    public void set(Position pos, E c) {
	grid.set(pos.getY(), pos.getX(), c);
    }

    /**
     * Return the contents of a position on the board.
     * @pre  pos is a non-null position on the board.
     */
    public E get(Position pos) {
	return grid.get(pos.getY(), pos.getX());
    }


    public static void main(String s[]) {
	
	World<Integer> w = new World<Integer>(5, 10);
	Position p1 = new Position(2, 3);
	Position p2 = new Position(10, 1);
	Position p3 = new Position(0, -1);

	System.out.println(w.width() + ", " + w.height());
	System.out.println(w.inRange(p1));
	System.out.println(w.inRange(p2));
	System.out.println(w.inRange(p3));
	System.out.println(w.get(p1));
	w.set(p1, 3);
	System.out.println(w.get(p1));

    }
	
}
