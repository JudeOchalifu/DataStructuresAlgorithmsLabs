import structure5.*;
import java.util.Iterator;


/**
 * A Route keeps track of information about a specific
 * connection from one Airport to another.  A route
 * has a departure and arrival Airport, as well as a
 * distance in miles.
 * <p>
 *
 * Note that flights can be added to a Route, and an
 * Iterator provides access to the flights in order
 * of departure time (earlier flights first).
 */
public class Route {

    /** departure airport */
    protected Airport departs;

    /** arrival airport */
    protected Airport arrives;

    /** list of flights, ordered by departure time */
    protected OrderedList<Flight> flights;

    /** distance in miles between airports */
    protected int distance;

    /**
     * @pre  departs and arrives are non-null Airports.
     */
    public Route(Airport departs, Airport arrives, int distance) {
	this.departs = departs;
	this.arrives = arrives;
	this.distance = distance;
	flights = new OrderedList<Flight>();
    } 

    /**
     * @post returns departure airport.
     */
    public Airport departs() { 
	return departs; 
    }

    /**
     * @post returns arrival airport.
     */
    public Airport arrives() { 
	return arrives; 
    }

    /**
     * @post returns distance between airports.
     */
    public int distance() {
	return distance;
    }

    /**
     * @pre  f is not null.
     * @post adds f to the list of flights between cities.
     */
    public void addFlight(Flight f) {
	flights.add(f);
    }

    /**
     * @post returns an iterator that iterates over flights
     * on this route, in order of departure time.
     */
    public Iterator<Flight> flights() {
	return flights.iterator();
    }

    public String toString() {
	return "Route " + departs + " to " + arrives;
    }
}
