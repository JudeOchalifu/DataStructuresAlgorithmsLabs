


import structure5.*;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Scanner;
import java.io.*;

/**
 * The main class for FlytrapAirlines. 
 */
//Keeps track of a table (hashmap) of airport names (strings) to the actual Airport code class.  Keeps track of a graph of Airports and their edges (Routes).  
//Extensions 1 and 2
//Extension 1 see processCommands
//Extension 2 separate command that gives a boolean as a parameter to the djikstra's algorithm.  This boolean tells the algorithm to add the inverse number of the distances to "trick" the algorithm into choosing the longest path.  
public class FlytrapAirlines {
	
    /** 
     * The schedule for FTA.  
     * The vertex labels are Airports, and the edge labels are Routes. 
     */
    protected Graph<Airport,Route> schedule;
	
    /** 
     * A table of Airports.  Used for the "airports" command and to
     * convert Strings like "SFO" to Airport objects. 
     */
    protected Hashtable<String,Airport> airports;
	
	
    /******************* Main Command Loop *********************/
	
    /**
     * Reads in commands and processes them until "quit"
     * is seen.  The valid command formats are described
     * by the help command.
     */
    public void processCommands() {
	printHelp();
	Scanner in = new Scanner(System.in);
	while (true) {
	    System.out.print("enter command> ");
	    if (!in.hasNext()) break;
	    String cmd = in.next();
	    if (cmd.equals("airports")) {
		printAirports();
	    } else if (cmd.equals("flights")) {
		Airport src = getAirport(in.next());
		Airport dest = getAirport(in.next());
		if (src == null || dest == null) {
		    System.out.println("Bad " + 
				       (src==null?"departure":"arrival") + 
				       " airport code.");
		} else {
		    printFlights(src, dest);
		}
	    } else if (cmd.equals("distance")) {
		Airport src = getAirport(in.next());
		Airport dest = getAirport(in.next());
		if (src == null || dest == null) {
		    System.out.println("Bad " + 
				       (src==null?"departure":"arrival") + 
				       " airport code.");
		} else {
		    printDistance(src, dest, true);
		}
		//Longest distance extension 
	    } else if (cmd.equals("longest")) {  
		Airport src = getAirport(in.next());
		Airport dest = getAirport(in.next());
		if (src == null || dest == null) {
		    System.out.println("Bad " + 
				       (src==null?"departure":"arrival") + 
				       " airport code.");
		} else {
		    printDistance(src, dest, false);
		}
		
	    } else if (cmd.equals("trip")) {
		Airport src = getAirport(in.next());
		Airport dest = getAirport(in.next());
		int departTime = in.nextInt();
		if (src == null || dest == null) {
		    System.out.println("Bad " + 
				       (src==null?"departure":"arrival") + 
				       " airport code.");
		} else {
		    printTrip(src, dest, departTime);
		} 
		//Extension (a) add/remove airports, add/cancel flights 
		//Add airport
	    } else if (cmd.equals("addA")) {
		String code = in.next();
		String name = in.next(); 
		Airport toAdd = new Airport(code,name); 
		airports.put(name,toAdd); //Airports hashtable key=string value=Airport
		schedule.add(toAdd);
		//Remove airport 
	    } else if (cmd.equals("removeA")) {
		Airport toRemove = getAirport(in.next()); 
		airports.remove(toRemove.name()); 
		schedule.remove(toRemove); 
		//Add Flight 
	    } else if (cmd.equals("addF")){
		System.out.println("flight number?"); 
		int number = in.nextInt(); 
		System.out.println("airport arrive?");
		Airport departs = getAirport(in.next()); 
		System.out.println("airport depart?"); 
		Airport arrives = getAirport(in.next()); 
		System.out.println("depart time"); 
		int departTime = in.nextInt();
		System.out.println("arrive time");
		int arriveTime = in.nextInt();
		System.out.println("flight duration"); 
		int duration = in.nextInt(); 
		Flight newFlight = new Flight(number,departs,arrives,departTime,arriveTime,duration); 
		
		Edge<Airport,Route> edge = schedule.getEdge(departs,arrives); 
		Route theRoute = edge.label(); 
		theRoute.addFlight(newFlight); 
		//Remove Flight 
	    } else if (cmd.equals("removeF")){
		System.out.println("depart time of flight to be removed"); 
		int departTime = in.nextInt(); 
		//departure airport holds the route which would hold the flight
		System.out.println("departure airport"); 
		Airport departs = getAirport(in.next()); 
		System.out.println("arrival airport"); 
		Airport arrives = getAirport(in.next()); 
		Route theRoute = schedule.getEdge(departs,arrives).label(); 
		if(theRoute.remove(new Flight(0,null,null,departTime,0,0))){ //flights compared to by departTime only 
		    System.out.println("removed flight"); 
		}
		else{
		    System.out.println("non existent flight"); 
		}
		
	    } else if (cmd.equals("help")) {
		printHelp();
	    } else if (cmd.equals("quit")) {
		return;
	    } else {
		System.out.println("eh?");
	    }
	}
    }      

	
    /**
     * Helper method to convert an airport code to a Airport object.
     */
    protected Airport getAirport(String s) {
	return airports.get(s.toUpperCase());	
    }

    ////////////////////////// help command ///////////////////////////////
		
    /**
     * Handles the "help" command by printing a help message.
     */
    protected void printHelp() {
	System.out.println("\n*** Welcome to Flytrap Airlines ***");
	System.out.println(" Valid commands are:");
	System.out.println("    help");
	System.out.println("    quit");
	System.out.println("    airports");
	System.out.println("    flights DEPART ARRIVE");
	System.out.println("    distance DEPART ARRIVE");
	System.out.println("    trip DEPART ARRIVE TIME");	
	System.out.println("    addA AIRPORT");	
	System.out.println("    removeA AIRPORT");	
	System.out.println("    addF FLIGHT");	
	System.out.println("    removeF FLIGHT");	
	System.out.println();
    }

	
    ////////////////////////// airports command ///////////////////////////////
	
    /**
     * Handles the "airports" command by printing an alphabetic list
     * of airports serviced by FTA.
     */
    protected void printAirports() {
	Iterator<Airport> airportName = this.airports.iterator(); 
	//Create ordered vector so that airports are returned in alphabetical order 
	OrderedVector<String> alphAirport = new OrderedVector<String>(); 

	while(airportName.hasNext()){
	    Airport airport = airportName.next(); 
	    String toAdd = airport.code() + " " + airport.name();
	    alphAirport.add(toAdd); 
	
	}

	Iterator<String> alphIterator = alphAirport.iterator(); 
	while(alphIterator.hasNext()){
	    System.out.println(alphIterator.next()); 
	}
    }

    ////////////////////////// flights command ///////////////////////////////
	
    /**
     * Handles the "flights" command.  
     * @pre  departAirport and arriveAirport are non-null Airports.
     * @post prints all daily flights on that route, ordered from earliest
     *       latest.
     */
    protected void printFlights(Airport departAirport, Airport arriveAirport) {
	Assert.pre(departAirport != null, "bad depart airport");
	Assert.pre(arriveAirport != null, "bad arrived airport");

	Edge<Airport,Route> firstEdge = schedule.getEdge(departAirport,arriveAirport); 
	if(firstEdge == null){
	    System.out.println("no routes from departAirport to arriveAirport"); 
	    return;
	}

	Route route = firstEdge.label(); 
	Iterator<Flight> routeIterator = route.flights();
	//Create ordered vector so that flights are returned earliest to latest 
	OrderedVector<Flight> Ordroutes = new OrderedVector<Flight>(); 

	while(routeIterator.hasNext()){
	    Ordroutes.add(routeIterator.next()); 
	}
	
	Iterator<Flight> OrdIterator = Ordroutes.iterator();
	while(OrdIterator.hasNext()){
	    System.out.println(OrdIterator.next()); 
	}
    }
	

    ////////////////////////// distance command ///////////////////////////////

	
    /**
     * Handles the "distance" command.  This uses the dijkstra method
     * to compute shortest paths
     * @pre  departAirport and arriveAirport are non-null Airports.
     * @post prints the shortest path distance for a trip from the departAirport
     *       to the arriveAirport.
     */
    protected void printDistance(Airport departAirport, 
				 Airport arriveAirport, boolean findShortest) {
	Assert.pre(departAirport != null, "bad depart airport");
	Assert.pre(arriveAirport != null, "bad arrived airport");
		
	Map<Airport,ComparableAssociation<Integer,Edge<Airport,Route>>> m = 
	    dijkstra(schedule, departAirport, findShortest); 
	ComparableAssociation<Integer,Edge<Airport,Route>> v = 
	    m.get(arriveAirport);
	if (m.get(arriveAirport) == null) {
	    System.out.println("Not possible.");
	} else {
	    if(findShortest){
	    System.out.println("Total Distance is " + v.getKey() + " miles.");
	    }
	    printShortestPath(m, arriveAirport, findShortest); 
	}
    }

	


    /**
     * An implementation of dijkstra's algorithm to compute route
     * distances.  You should not modify this method.
     * @pre g is a schedule graph and start is a non-null Airport
     * @post returns a map from Airport to (distance, previous-edge) 
     *       Associations.
     */
    protected Map<Airport,ComparableAssociation<Integer,Edge<Airport,Route>>>
	dijkstra(Graph<Airport,Route> g, Airport start, boolean findShortest) {

	// keep a priority queue of distances from source
	PriorityQueue<ComparableAssociation<Integer,Edge<Airport,Route>>> q = 
	    new SkewHeap<ComparableAssociation<Integer,Edge<Airport,Route>>>();

	// map from node to shortest-path info
	Map<Airport,ComparableAssociation<Integer,Edge<Airport,Route>>> result = 
	    new Table<Airport,ComparableAssociation<Integer,Edge<Airport,Route>>>();

	// airport that we are currently visiting.  Begin with start airport.
	Airport current = start;
		
	// result is a (total-distance,previous-edge) pair.  We create
	// a "fake" one that leads to the start airport.
	ComparableAssociation<Integer,Edge<Airport,Route>> possiblePath =
	    new ComparableAssociation<Integer,Edge<Airport,Route>>(0, null);
		
	// as long as we are visiting a valid city
	while (current != null) {
	    if (!result.containsKey(current)) {
		// visit node current -- record shortest path to current
		result.put(current,possiblePath);
		// distToCurrent is shortest distance to current node
		int distToCurrent = possiblePath.getKey();
				
		// compute and consider distance to each neighbor
		Iterator<Airport> currentNeighbors = g.neighbors(current);
		while (currentNeighbors.hasNext()) {
		    // get edge to neighbor
		    Edge<Airport,Route> neighborEdge = g.getEdge(current,currentNeighbors.next());
		    // construct (distance,edge) pair for possible result
		    Route routeFromCurrentToNeighbor = neighborEdge.label();
		    int distanceToNeighbor = distToCurrent + routeFromCurrentToNeighbor.distance();
		    //if we are finding shortest, add regular distance, else, if we are finding longest distance, simply add 1/distanceToNeighbor to make longest distances "smallest" to the priority queue
		    if(findShortest){
			possiblePath = new ComparableAssociation<Integer,
			    Edge<Airport,Route>>(distanceToNeighbor, neighborEdge);
		    }
		    else{
			possiblePath = new ComparableAssociation<Integer,
			    Edge<Airport,Route>>(1/distanceToNeighbor, neighborEdge);
		    }
		    q.add(possiblePath);	// add to priority queue
		}
	    }
	    // set up for next iteration by getting the closest 
	    // (possibly unvisited) vertex, 
	    if (!q.isEmpty()) {
		possiblePath = q.remove();
		current = possiblePath.getValue().there();
	    } else {
		// no new vertex (algorithm stops)
		current = null;
	    }
	}
	return result;
    }
		
    /**
     * @pre  distances is a map from Airport to (distance, previous-edge) 
     *       Associations.
     * @pre  destination is the end of the route we are printing.  
     * @post Prints out the route distances from the source to destination (by 
     *       following the previous edges back to the source.
     */
    protected void 
	printShortestPath(Map<Airport,ComparableAssociation<Integer,Edge<Airport,Route>>> distances,
			  Airport destination, boolean findShortest) {
	//given map distances which maps distances from source Airport to all airports 
	ComparableAssociation<Integer,Edge<Airport,Route>> routeTo = distances.get(destination); 
	while(routeTo.getValue()!=null){
	    System.out.println(routeTo.getValue().label().toString() + " " + routeTo.getValue().label().distance() + " miles"); 
	    routeTo = distances.get(routeTo.getValue().here()); 
	}
  
    }

	

    ////////////////////////// trip command ///////////////////////////////
	
	
    /**
     * Handles the "trip" command.  This uses the dijkstraEarliestArrival method
     * to compute shortest paths based on arrival time.
     * @pre  departAirport and arriveAirport are non-null Airports, and 
     *       earliestDepartTime is a valid time between 0 and 2359.
     * @pre  departAirport and arriveAirport are non-null Airports.
     * @post prints the itinerary from the departAirport
     *       to the arriveAirport that arrives at the earliest time.
     */
    protected void printTrip(Airport departAirport, 
			     Airport arriveAirport, 
			     int earliestDepartTime) {
	Assert.pre(departAirport != null, "bad depart airport");
	Assert.pre(arriveAirport != null, "bad arrived airport");
		
	Map<Airport,ComparableAssociation<Integer,Flight>> m = 
	    dijkstraEarliestArrival(schedule, departAirport, earliestDepartTime); 
	if (m == null || m.get(arriveAirport) == null) {
	    System.out.println("Not possible.");
	} else {
	    printItinerary(m, arriveAirport); 
	}
    }
	
	
	
    /**
     * An implementation of Dijkstra's algorithm to compute earliest-arriving
     * itineraries.  You should modify this method to enqueue new possible
     * itineraries into the prioirity queue.
     * @pre g is a scheule graph and start is a non-null Airport
     * @post returns a map from Airport to (arrival-time, previous-flight) 
     *       Associations.
     */
    protected Map<Airport,ComparableAssociation<Integer,Flight>> 
	dijkstraEarliestArrival(Graph<Airport,Route> g, Airport start, 
				int time)
    {

	//Integer should store the current time 

	//keep a priority queue to distance from source 
	PriorityQueue<ComparableAssociation<Integer,Flight>> pq = 
	    new SkewHeap<ComparableAssociation<Integer,Flight>>();

	//the map to return 
	Map<Airport,ComparableAssociation<Integer,Flight>> result = 
	    new Table<Airport,ComparableAssociation<Integer,Flight>>(); 

	//the start airport 
	Airport current = start; 

	//result is an airport, time-flight pair.  We create a fake one for the start airport 
	ComparableAssociation<Integer,Flight> possiblePath = new ComparableAssociation<Integer,Flight>(0,null);

	while(current!=null){
	    //if airport destination not already in result map add its neighbors to result amp 
	    if(!result.containsKey(current)){
		result.put(current,possiblePath);
		int currentTime = time; 
		if(possiblePath.getValue()!=null) 
		    currentTime = possiblePath.getValue().arriveTime(); 

		//Look at all neighbors and get the flights of these neighbors 
		Iterator<Airport> neighbors = g.neighbors(current);
		while(neighbors.hasNext()){
		    //get connections to neighbor 
		    Airport neighbor = neighbors.next();
		    Edge<Airport,Route> connection = g.getEdge(current,neighbor); 
		    Iterator<Flight> flights = connection.label().flights(); 
		    //iterate through all of the airport's flights. We know that if we add an already existent flight, our check if(!result.containsKey) above will prevent multiple entries while the skewHeap will always mean we get the shortest possible path beforehand 
		    //Skew heap ensures we are always looking at the shortest possible path at any given time, thus the if(!result... check above.
		    while(flights.hasNext()){
			Flight flight = flights.next();
			if( flight.departTime() > currentTime && differenceInMinutes(flight.departTime(),currentTime)>=30  ){
			    pq.add( new ComparableAssociation<Integer,Flight>( flight.arriveTime(), flight)); 
			}
		    }
		}
		
	    }
	    if(!pq.isEmpty()){
		possiblePath = pq.remove(); 
		current = possiblePath.getValue().arrives(); 
	    }
	    else{
		current = null; 
	    }
	}
    
	return result; 
    }
	
    //finds difference in minutes between two times in 24 hours format 
    protected int differenceInMinutes(int time1, int time2){
	return Math.abs( convertToMinutes(time1) - convertToMinutes(time2) ); 
    }

    protected int convertToMinutes(int time1){
	String A = Integer.toString(time1); 
	if(A.length()==4){
	    return Integer.parseInt(A.substring(0,2))*60 + Integer.parseInt(A.substring(2,4)); 
	}
	else{
	    return Integer.parseInt(A.substring(0,1))*60 + Integer.parseInt(A.substring(1,3)); 
	}
	
    }
    /**
     * @pre  earliestArrivals is a map from Airport to 
     *       (arrivalTime, previous-flight) Associations.
     * @pre  destination is the end of the route we are printing.  
     * @post Prints out the flights from the source to destination (by 
     *       following the previous flights back to the source.
     */
    protected void 
	printItinerary(Map<Airport,ComparableAssociation<Integer,Flight>> earliestArrivals, 
		       Airport destination) {

	// Map earliestArrivals is a map of shortest time "distances" from a given source airport 
	
	while(true){
	    Flight incomingFlight = earliestArrivals.get(destination).getValue(); 
	    if(incomingFlight!=null){
		System.out.println(incomingFlight); 
		destination = earliestArrivals.get(destination).getValue().departs(); 
	    }
	    else return; 
	}
    }
	
	
    /******************* Constructors and Main *********************/
	
	
    /**
     * @pre  prefix is "small" or "large".
     * @post creates a new FlytrapAirlines object with the given data set.
     */
    public FlytrapAirlines(String prefix) {
	schedule = new GraphListDirected<Airport,Route>();
	airports = new Hashtable<String,Airport>();
	loadFiles(prefix);
    }
	
    /**
     * Reads in the airports.txt, dists.txt. and flights.txt 
     * files, prepended with the given prefix.  You should
     * call this method only with prefix equals to "large" or "small".
     * <p>
     * You should not modify this code.
     */
    protected void loadFiles(String prefix) {
	readAirports(prefix + "-airports.txt");
	readDistances(prefix + "-dists.txt");
	readFlights(prefix + "-flights.txt");
    }
	
    /**
     * Helper method to read in airport data file.
     * Adds the airports to the graph and to the airports map
     */
    protected void readAirports(String fileName) {
	Scanner in = new Scanner(new FileStream(fileName));
	while (in.hasNext()) {
	    String code = in.next().toUpperCase();
	    String name = in.nextLine().trim();  // removing leading/trailing white space
	    Airport a = new Airport(code,name);
	    airports.put(code, a);
	    schedule.add(a);
	}
    }
	
    /**
     * Helper method to read in the distances between airports
     * serviced by flights.  Adds Route edges to the graph.
     */
    protected void readDistances(String fileName) {
	Scanner in = new Scanner(new FileStream(fileName));
	while (in.hasNext()) {
	    Airport here = getAirport(in.next());
	    Airport there = getAirport(in.next());
	    int dist = in.nextInt();
	    schedule.addEdge(here, there, new Route(here, there, dist));
	    schedule.addEdge(there, here, new Route(there, here, dist));
	}
    }
	
    /**
     * Helper method to read in flight info.
     * Adds Flights to the Route object in the graph.
     */
    protected void readFlights(String fileName) {
	Scanner in = new Scanner(new FileStream(fileName));
	while (in.hasNext()) {
	    int code = in.nextInt();
	    Airport here = getAirport(in.next());
	    Airport there = getAirport(in.next());
	    Assert.condition(here != null, "bad airport: " + here);
	    Assert.condition(there != null, "bad airport: " + there);
	    int start = in.nextInt();
	    int end = in.nextInt();
	    int time = in.nextInt();
	    Edge<Airport,Route> e = schedule.getEdge(here, there);
	    Assert.condition(e != null, "bad route (edge)");
	    Route r = e.label();
	    Assert.condition(r != null, "bad route");
	    Flight f = new Flight(code, here, there, start, end, time);
	    r.addFlight(f);
	}
    }
	
    public static void main(String s[]) {

	if (s.length == 0) {
	    System.out.println("You must supply a data set (large or small).");
	    System.out.println("Example:  java FlytrapAirlines small");
	} else {
	    FlytrapAirlines p = new FlytrapAirlines(s[0]);
	    p.processCommands();
	}
    }
}
