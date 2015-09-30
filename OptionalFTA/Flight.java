import structure5.*;
import java.lang.Comparable;

/**
 * Information about one flight on the airline.
 * A flight has a number, arrival and departure
 * Airports, arrival and departure times, and 
 * a duration.  Flights can be compared based
 * on departure time.
 * <p>
 * 
 * Arrival and departure times are encoded as
 * four digit integers where the first two 
 * digits represent hours and the last two represent
 * minutes.  For example, 100 is 1:00AM, 1300 is 1:00PM,
 * 17:58 is 5:58PM.  If a flight lands after midnight,
 * the hours may be larger than 24.  
 * <p>
 *
 * The duration of a flight is minutes.  
 * Note that because of time zone changes, adding
 * the duration to the departure time will not 
 * always give you the arrival time.  For example,
 * if a flight leaves LA and 8:00AM and takes 6 hours
 * to fly to NY, the duration is 360 minutes, but the
 * arrival time is 5:00PM.
 */
public class Flight implements Comparable<Flight> {
    
    /** flight number */
    protected int number;
    
    /** departure airport */
    protected Airport departs;

    /** arrival airport */
    protected Airport arrives;

    /** departure time (in 24 hour time as 4 digits, ie 1530 for 330pm) */
    protected int departTime;

    /** arrival time (in 24 hour time as 4 digits, ie 1530 for 130pm) */
    protected int arriveTime;

    /** duration of the flight (in minutes) */
    protected int duration;

    public Flight(int number,
		  Airport departs,
		  Airport arrives,
		  int departTime,
		  int arriveTime, 
		  int duration) {
	this.number = number;
	this.departs = departs;
	this.arrives = arrives;
	this.departTime = departTime;
	this.arriveTime = arriveTime;
	this.duration = duration;
    } 

    /** 
     * @post returns the departure time for the flight.
     */
    public int departTime() { 
	return departTime; 
    }

    /** 
     * @post returns the arrival time for the flight.
     */
    public int arriveTime() { 
	return arriveTime; 
    }

    /** 
     * @post returns the departure airport for the flight.
     */
    public Airport departs() { 
	return departs; 
    }

    /** 
     * @post returns the arrival airport for the flight.
     */
    public Airport arrives() { 
	return arrives; 
    }

    /** 
     * @post returns the duration of the flight in minutes.
     */
    public int duration() { 
	return duration; 
    }

    /** 
     * @pre  o is a non-null Flight object.
     * @post returns < 0 if this leaves before o, 
     *               = 0 if they leave at the same time,
     *               > 0 if this leaves after o.
     */
    public int compareTo(Flight o) {
	return departTime() - o.departTime();
    }


    /*
     * Uses the String.format method to format the text.  See the
     * online javadoc for details...
     */
    public String toString() {
	return String.format("FTA %-15d %5s -> %-5s         %4d until %4d    (%d min)",
			     number,
			     departs,
			     arrives,
			     departTime,
			     arriveTime,
			     duration);
    }
}
