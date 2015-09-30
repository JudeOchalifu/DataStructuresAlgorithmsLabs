
import structure5.*;
import java.util.Random;
import java.io.*;

/**
 * This class controls the simulation.  The design is entirely up to
 * you.  You should include a main method that takes the array of
 * species file names passed in and populates a world with species of
 * each type.  
 * <p>
 * Be sure to call the WorldMap.pause() method every time
 * through the main simulation loop or else the simulation will be too fast. 
 * For example:
 * <pre>
 *   public void simulate() {
 *       for (int rounds = 0; rounds < numRounds; rounds++) {
 *         giveEachCreatureOneTurn();
 *         pause(100);
 *       }
 *   }
 * </pre>
 */
class Darwin {

    /**
     * The array passed into main will include the arguments that
     * appeared on the command line.  For example, running "java
     * Darwin Hop.txt Rover.txt" will call the main method with s
     * being an array of two strings: "Hop.txt" and "Rover.txt".
     */
    private static final int PAUSE = 50; 
    private static final int TOTAL_TURNS = 1000; 
    private static final int BOARD_SIZE = 30; 
    private static final int NUM_CREATURES = 20; 
    private Species A, B; 
    private World<Creature> world; 
    private Vector<Creature> creatures = new Vector<Creature>(NUM_CREATURES); 

    public static void main(String s[]) {
	//initialize species types 
	//pre: the input species are the correct types 
	Species A = new Species(s[0]);
	Species B = new Species(s[1]);
	//initialize the game 
	Darwin simulator = new Darwin(A,B);
    }
    //takes in two species and creates NUM_CREATURES/2 of each and places them randomly on the board and then start the simulation 
    public Darwin(Species A, Species B){
	this.A = A; 
	this.B = B; 
	world = new World<Creature>(BOARD_SIZE,BOARD_SIZE); 

	Random placer = new Random(); 

	//Place creatures randomly on board 
	for(int i = 0; i<NUM_CREATURES; i++){
	    //make sure the creature's position is vacant, else choose a new one 
	    Position pos = new Position( placer.nextInt(BOARD_SIZE),placer.nextInt(BOARD_SIZE) ); 
	    while( world.get(pos) != null ){
		pos = new Position( placer.nextInt(BOARD_SIZE),placer.nextInt(BOARD_SIZE) ); 
	    }
	    if(i<NUM_CREATURES/2) creatures.add( new Creature(A,this.world,pos,placer.nextInt(4)) ); 
	    else creatures.add( new Creature(B,this.world,pos,placer.nextInt(4)) ); 
	}

	this.simulate(); 
    }
    //run the simulation 
    public void simulate() {
	//for TOTAL_TURNS, have each creature take a turn 
	for(int i = 0; i<TOTAL_TURNS; i++){
	    for(int j = 0; j<NUM_CREATURES; j++){
		creatures.get(j).takeOneTurn(); 
	    }
	    WorldMap.pause(PAUSE);
	}

    }
}
