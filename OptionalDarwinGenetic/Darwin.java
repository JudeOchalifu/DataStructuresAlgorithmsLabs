//import java.util.*;
import structure5.*; 
import java.util.HashMap; 
import java.util.Random; 
/**
 * Author: Alex Wheelock
 */
class Darwin {
    Random random = new Random(); 

    public static void main(String args[]) {
	//Instruction sit = new Instruction("hop",-111); 
	//System.out.println( sit.getAddress() ); 

	//add a timing mechanism 
	Darwin test = new Darwin(); 
	//for(int i = 0; i<10; i++)
	//test.match(new Species("Rover.txt"),new Species("Kyle2.txt")); 
	test.mutationMain(new Species("Rover.txt"),new Species("Flytrap.txt"),10); 
    }
    //n is desired number of mutations per round 
    public void mutationMain(Species toBeat, Species toMutate, int n){
	//queue of mutated programs to test? 
	

	///keep track of mutation success 
	Vector<Association<String,Double>> mutationSuccess = new Vector<Association<String,Double>>(); 
	mutationSuccess.add( new Association<String,Double>("swap",1/3.0) ); 
	mutationSuccess.add( new Association<String,Double>("add",1/3.0) ); 
	mutationSuccess.add( new Association<String,Double>("remove",1/3.0) ); 
       
	//randomly choose among mutations 
        double choice = 0; 
	
	//mutate a clone of the instructions 
	//Vector<Instruction> toMutateInstructions = (Vector<Instruction>)toMutate.getInstructions().clone();
 
	//for n times, create n different mutations and run them against toBeat
	
	Vector<Double> ratios = new Vector<Double>(n); //keep track of win loss ratios
	Vector<Species> mutatedSpecies = new Vector<Species>(n); //keep track mutated species 

	for(int a = 0; a<n; a++){

	    //mutate a clone of the instructions 
	    Vector<Instruction> toMutateInstructions = new Vector<Instruction>(toMutate.getInstructions());//(Vector<Instruction>)toMutate.getInstructions().clone();

	    //choose which mutation 
	    choice = random.nextDouble(); 
	    int i = 0;
	    while(choice>0){
		choice = choice - mutationSuccess.get(i).getValue(); 
		i++;
	    }
	    //need to give instructinos
	    //if( mutationSuccess.get(i-1).getKey().equals("swap") ){
	    //	toMutateInstructions = this.swapInstruction(toMutateInstructions); 
	    //} 
	    //else if( mutationSuccess.get(i-1).getKey().equals("add") ){
		toMutateInstructions = this.addInstruction(toMutateInstructions); 
		//}
		//else { //remove
	    //	toMutateInstructions = this.removeInstruction(toMutateInstructions); 
		// } 
	   
	    //run the mutated species 
	    
	    Species mutated = new Species(toMutateInstructions, "mutant: " + a); 
	    //System.out.println("result" + this.match(toBeat,mutated)); 
	    Double success = this.match(toBeat,mutated); 
	    System.out.println("Success: " + success*100.0); 
	    ratios.add(success); 
	    mutatedSpecies.add(mutated); 
	
	}

	//get most successful creatures, or creatures with win > 20% 
	Vector<Integer> indexes = new Vector<Integer>(); 
	Integer indexMax = 0; 

	for(int b = 1; b<ratios.size(); b++){

	    if( ratios.get(b) > ratios.get(indexMax)){
		indexMax = b; 
	    }
	    if( ratios.get(b) > .20){
		indexes.add(b); 
	    }
	}
	indexes.add(indexMax); 
	
	//continue to mutate the most successful creatures 
	for(int c = 0; c<indexes.size(); c++){
	    this.mutationMain(toBeat,mutatedSpecies.get(c),n); 
	}
    }

    //MUTATOR CODE
    public Vector<Instruction> swapInstruction(Vector<Instruction> instructions){
	return instructions; 
    }

    //evaluate infinite loop helper method 
    //NOTE addresses + 1 vs. array , convert addresses to array index by - 1
    //NEED TO CONSIDER ADDRESS SHIFTS BEFORE SHIFTING ADDRESSES BELOW 
    public boolean isInfinite(Vector<Instruction> instructions, Instruction instruction, int pivot){
	System.out.println("instruction in question: " + instruction); 
	int randomAddress = instruction.getAddress()-1; 
	if(instruction.getOpcode()<4) return false;  
	int otherAddress = instructions.get(randomAddress).getAddress()-1; 
	if(otherAddress>pivot) otherAddress++; 
	while(instructions.get(otherAddress).getOpcode()>4){
	    System.out.println("running loop infinite check " + instructions); 
	    if(otherAddress==randomAddress){
		instructions.remove(instruction); 
		return true; 
	    }
	    otherAddress = instructions.get(otherAddress).getAddress()-1; 
	    if(otherAddress>pivot) otherAddress++; 
	}
	return false; 
    }

    public Vector<Instruction> addInstruction(Vector<Instruction> instructions){
	//PROBLEM: MODIFYING THE SAME INSTRUCTIONS, SO INCREMENTING SAME BASE INSTRUCTIONS AGAIN AND AGAIN 
	System.out.println("adding"); 

	int pivot; 
	Instruction randomInstruction; 
	int randomAddress; 

	do{
	    pivot = random.nextInt(instructions.size()); 

	    System.out.println("choosing new point"); 
	    int randomOpcode = random.nextInt(Instruction.GO)+1; 

	    //Only generate random addresses for valid instructions 
	    randomAddress = random.nextInt(instructions.size())+1;
	    System.out.println(randomAddress); 
	    if(randomOpcode>3){
		while(randomAddress==pivot+1){
		    randomAddress = random.nextInt(instructions.size()+1);
		}
	    }
	    else randomAddress = 1; 
	    randomInstruction = new Instruction( randomOpcode, randomAddress ); 

	    //add random instruction to random location 
	    instructions.add(pivot,randomInstruction); 


	}while(isInfinite(instructions,randomInstruction,pivot)); //prevent infinite address reference loops 
	

	//maintain all previous connections 
	for(int i = 0; i<instructions.size(); i++){
		//if address was to line later than pivot, update that line's new location 
	    if(instructions.get(i).getAddress()>=pivot && i!=pivot){
		Instruction copied = new Instruction( instructions.get(i) ); 
		copied.setAddress( copied.getAddress()+1 ); 
		instructions.set(i,copied); 
	    }
	}


	//1 to 10 
	//ifEnemy = 8
	//infect = 4 
	//Insruction(int opcode, int address); 
	System.out.println("returning"); 
	System.out.println("instruction: " + randomInstruction + " pivot" + pivot + " randomA" + randomAddress); 
	System.out.println(instructions); 
	return instructions; 
    }
    public Vector<Instruction> removeInstruction(Vector<Instruction> instructions){
	return instructions; 
    }
    //change address method? 
    
    
    //Tests species B against species A, returns win loss ratio B/A 
    public double match(Species A, Species B){ 
	int simulations = 200; //number of games is simulations + 1 
	int turns = 400; //num turns per round 
	int dimensions = 15; //world size
	int numCreatures = 10;

	//scores
	HashMap<Species, Integer> scores = new HashMap<Species, Integer>();	

	//mutant tester species  
	Vector<Species> allSpecies = new Vector<Species>(2);

	//Add species 
	allSpecies.add(A); scores.put(A,0); 
	allSpecies.add(B); scores.put(B,0); 

	System.out.println( "a: " + A);
	System.out.println( "b: " + B); 

	//Run game for 'simulations' times 
	for (int i = 0; i < simulations; i++) {	   
	    //System.out.println(simulations-i); 
	    World<Creature> w = new World<Creature>(dimensions, dimensions);	    
	    Vector<Creature> creatures = placeCreatures(allSpecies, w,  
							numCreatures, false);
	    //System.out.println("START SIM");
	    for (int j = 0; j < turns; j++) {
		simulate(creatures);
		//System.out.println("END SIM" + j + " " + turns); 
	    }
	    for (Species s : allSpecies) {
		scores.put(s, scores.get(s) + countSpecies(s, creatures));
	    }
	}

	System.out.println("FINISHED many SIMULATION");

	//Print final scores 
	return printScores(allSpecies, scores);    
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

    //return win loss ratio 
    public double printScores(Vector<Species>  allSpecies, 
				    HashMap<Species, Integer> scores) {
	/*
	int j = 0; 
	//print out results
	
	for (Species s : allSpecies) {
	    System.out.println(allSpecies.get(j) + " " + scores.get(s)); 
	    j++; 
	}	
	//assume second creature is rover/'title champion' 
	//System.out.println( "challenger/champion win loss ratio: " + scores.get(allSpecies.get(1))/(double)scores.get(allSpecies.get(0)) ); 
	*/
	//return final score 
	return scores.get(allSpecies.get(1))/(double)scores.get(allSpecies.get(0)); 
    }

    public static void resetWorldMap() {
	for (int i = 0; i < 15; i++) {
	    for (int j = 0; j < 15; j++) {
		WorldMap.displaySquare(new Position(j, i), ' ', 0, null);
	    }
	}
    }
		
}
