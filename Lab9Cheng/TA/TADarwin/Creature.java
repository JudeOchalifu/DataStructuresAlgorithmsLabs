
import structure5.*;
import java.util.*;

/**
 * This class represents one creature on the board.
 * Each creature must remember its species, position, direction,
 * and the world in which it is living.
 * <p>
 * In addition, the Creature must remember the next instruction
 * out of its program to execute.
 * <p>
 * The creature is also repsonsible for making itself appear in
 * the WorldMap.  In fact, you should only update the WorldMap from
 * inside the Creature class.  
 *
 * Author: Alex Wheelock
 * Version: 5/5/10
 */

public class Creature {
    
    private Species species;
    private World<Creature> world;
    private Position pos;
    private int dir;
    private int line;
    private boolean graphics;

    /**
     * Create a creature of the given species, with the indicated
     * position and direction.  Note that we also pass in the 
     * world-- remember this world, so that you can check what
     * is in front of the creature and to update the board
     * when the creature moves.
     */
    public Creature(Species species, World<Creature> world, Position pos,
		    int dir, boolean graphics) {
	this.species = species;
	this.world = world;
	this.pos = pos;
	this.dir = dir;
	this.line = 1;
	this.graphics = graphics;
	if (graphics) {
	    drawCreature();
	}
    }

    /**
     * Return the species of the creature.
     */
    public Species species() {
	return species;
    }

    /**
     * Return the current direction of the creature.
     */
    public int direction() {
	return dir;
    }

    /**
     * Return the position of the creature.
     */
    public Position position() {
	return pos;
    }

    /**
     * Execute steps from the Creature's program until 
     * a hop, left, right, or infect instruction is executed.
     */
    public void takeOneTurn() {
	boolean done = false;
	
	while (!done) {
	    //System.out.println("take one turn"); 
	    Instruction instr = species.programStep(line);
	    int opcode = instr.getOpcode();
	    int address = instr.getAddress();

	    line++;
	    //System.out.println(address); 
	    switch (opcode) {
	    case Instruction.HOP:
		hop(); 
		done = true;
		break;
	    case Instruction.LEFT:
		left();
		done = true;
		break;
	    case Instruction.RIGHT:
		right(); 
		done = true;
		break;
	    case Instruction.INFECT:
		infect(address);
		done = true;
		break;
	    case Instruction.IFEMPTY:
		if (ifempty()) {
		    go(address);
		}
		break;
	    case Instruction.IFWALL:
		if (ifwall()) {
		    go(address);
		}
		break;
	    case Instruction.IFSAME:
		if (ifsame()) {
		    go(address);
		}
		break;
	    case Instruction.IFENEMY:
		if (ifenemy()) {
		    go(address);
		}
		break;
	    case Instruction.IFRANDOM:
		if (ifrandom()) {
		    go(address);
		}
		break;
	    case Instruction.GO:
		go(address);
		break;
	    }

	}
    }

    /** 
     * Performs the 'hop' operation. 
     */
    public void hop() {
	if (ifempty()) {
	    Position newPos = pos.getAdjacent(dir);
	    world.set(pos, null);
	    world.set(newPos, this);
	    if (graphics) {
		drawEmpty(pos);
	    }
	    pos = newPos;
	    if (graphics) {
		drawCreature();
	    }
	}
    }
	
    /** 
     * Performs the 'left' operation. 
     */
    public void left() {
	dir = leftFrom(dir);
	if (graphics) {
	    drawCreature();
	}
    }

    /** 
     * Performs the 'right' operation. 
     */
    public void right() {
	dir = rightFrom(dir);
	if (graphics) {
	    drawCreature();
	}
    }

    /** 
     * Performs the 'ifempty' operation. 
     */
    public boolean ifempty() {
	Position adjacent = pos.getAdjacent(dir);
	return world.inRange(adjacent) && world.get(adjacent) == null;
    }

    /** 
     * Performs the 'ifwall' operation. 
     */
    public boolean ifwall() {
	Position adjacent = pos.getAdjacent(dir);
	return !world.inRange(adjacent);
    }

    /** 
     * Performs the 'ifsame' operation. 
     */
    public boolean ifsame() {
	Position adjacent = pos.getAdjacent(dir);
	return world.inRange(adjacent) && world.get(adjacent) != null 
	    && this.species == world.get(adjacent).species;
    }

    /** 
     * Performs the 'ifenemy' operation. 
     */
    public boolean ifenemy() {
	Position adjacent = pos.getAdjacent(dir);
	return world.inRange(adjacent) && world.get(adjacent) != null
	    && this.species != world.get(adjacent).species;
    }

    /** 
     * Performs the 'ifrandom' operation. 
     */
    public boolean ifrandom() {
	return Math.random() < .5;
    }

    /** 
     * Performs the 'go' operation. 
     */
    public void go(int line) {
	this.line = line;
    }

    /** 
     * Performs the 'infect' operation. 
     */
    public void infect(int line) {
	if (ifenemy()) {
	    Position adjacent = pos.getAdjacent(dir);
	    Creature other = world.get(adjacent);
	    other.species = this.species;
	    other.line = line;
	    if (graphics) {
		WorldMap.displaySquare(adjacent,
				       other.species.getName().charAt(0), 
				       other.dir, other.species.getColor());
	    }
	}
    }

    /**
     * Return the compass direction the is 90 degrees left of
     * the one passed in.
     */
    public static int leftFrom(int direction) {
	return (4 + direction - 1) % 4;
    }

    /**
     * Return the compass direction the is 90 degrees right of
     * the one passed in.
     */
    public static int rightFrom(int direction) {
	return (direction + 1) % 4;
    }

    /**
     * Updates a sqaure's graphic to be empty. 
     */
    public void drawEmpty(Position pos) {
	WorldMap.displaySquare(pos, ' ', 0, null);
    }
    
    /**
     * Updates the graphic at the location of this creature. 
     */
    public void drawCreature() {
	WorldMap.displaySquare(pos, species.getName().charAt(0), dir, 
			       species.getColor());
    }
    
}
