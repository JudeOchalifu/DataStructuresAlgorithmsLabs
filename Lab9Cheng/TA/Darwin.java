import java.util.*;

/**
 * Author: Alex Wheelock
 */
class Darwin {

    public static void main(String args[]) {

	int simulations = 1000;
	int turns = 1000;
	boolean graphics = false;
	int pause = 10;
	int dimensions = 15;
	int numCreatures = 10;
	boolean simulationsChanged = false;

	int argsIndex = 0;
	while (args[argsIndex].charAt(0) == '-') {
	    if (args[argsIndex].equals("-g")) {
		graphics = true;
		if (!simulationsChanged) {
		    simulations = 10;
		}
		simulations = 10;
	    } else if (args[argsIndex].equals("-p")) {
		pause = Integer.parseInt(args[argsIndex + 1]);
		argsIndex++;
	    } else if (args[argsIndex].equals("-s")) {
		simulations = Integer.parseInt(args[argsIndex + 1]);
		argsIndex++;
		simulationsChanged = true;
	    } else if (args[argsIndex].equals("-d")) {
		dimensions = Integer.parseInt(args[argsIndex + 1]);
		argsIndex++;
	    } else if (args[argsIndex].equals("-n")) {
		numCreatures = Integer.parseInt(args[argsIndex + 1]);
		argsIndex++;
	    }
	    argsIndex++;
	}

	if (graphics) {
	    WorldMap.createWorldMap(dimensions, dimensions);
	}

	Vector<Species> allSpecies = new Vector<Species>();
	HashMap<Species, Integer> scores = new HashMap<Species, Integer>();
	
	for (; argsIndex < args.length; argsIndex++) {
	    Species s = new Species(args[argsIndex]);
	    allSpecies.add(s);
	    scores.put(s, 0);
	}

	for (int i = 0; i < simulations; i++) {
	    
	    if (simulations < 10 || i % (simulations / 10) == 0) {
		printScores(allSpecies, scores);
		System.out.println();
	    }

	    World<Creature> w = new World<Creature>(dimensions, dimensions);
	    
	    Vector<Creature> creatures = placeCreatures(allSpecies, w,  
							numCreatures, graphics);

	    for (int j = 0; j < turns; j++) {
		simulate(creatures);
		if (graphics) {
		    WorldMap.pause(pause);
		}
	    }

	    for (Species s : allSpecies) {
		scores.put(s, scores.get(s) + countSpecies(s, creatures));
	    }
	    
	    if (graphics) {
		WorldMap.pause(500);
		resetWorldMap();
	    }
	}

	printScores(allSpecies, scores);
    
    }

    /**
     * Populates the board with creatures and returns those creatures.
     */
    private static Vector<Creature> placeCreatures(Vector<Species> allSpecies,
						   World<Creature> w,
						   int numCreatures,
						   boolean graphics) {
	Vector<Creature> creatures = new Vector<Creature>();
	
	for (Species s : allSpecies) {
	    for (int i = 0; i < numCreatures; i++) {		
		boolean done = false;		
		while (!done) {
		    
		    int x = (int) (Math.random() * w.width());
		    int y = (int) (Math.random() * w.height());
		    Position pos = new Position(x, y);
		    
		    if (w.get(pos) == null) {
			int dir = (int) (Math.random() * 4);
			Creature c = new Creature(s, w, pos, dir, graphics);
			w.set(pos, c);

			int index = (int) (Math.random() * creatures.size());
			creatures.add(index, c);

			done = true;
		    }

		}

	    }
	}
    
	return creatures;
    }
    
    /**
     * Has each creature take one turn. 
     */
    private static void simulate(Vector<Creature> creatures) {
	for (Creature c : creatures) {
	    c.takeOneTurn();
	}
    }

    /**
     * Counts the number of creatures of a particular species. 
     */
    private static int countSpecies(Species s, Vector<Creature> creatures) {
	int count = 0; 

	for (Creature c : creatures) {
	    if (c.species() == s) {
		count++;
	    }
	}

	return count;
    }

    public static void printScores(Vector<Species>  allSpecies, 
				    HashMap<Species, Integer> scores) {
	Vector<Species> species = new Vector<Species>(allSpecies);
	Vector<Integer> values = new Vector<Integer>();
	for (Species s : species) {
	    values.add(scores.get(s));
	}
	while (species.size() > 0) {
	    int max = -1;
	    int maxIndex = -1;
	    for (int i = 0; i < values.size(); i++) {
		if (values.get(i) > max) {
		    max = values.get(i);
		    maxIndex = i;
		}
	    }
	    System.out.println(species.get(maxIndex) + ": "
			       + values.get(maxIndex));
	    species.remove(maxIndex);
	    values.remove(maxIndex);
	}		
    }

    public static void resetWorldMap() {
	for (int i = 0; i < 15; i++) {
	    for (int j = 0; j < 15; j++) {
		WorldMap.displaySquare(new Position(j, i), ' ', 0, null);
	    }
	}
    }
		
}
