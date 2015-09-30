import structure5.*;
import java.lang.Comparable;

/**
 * A simple class to store the name and code
 * for an airport.  This class supports equals,
 * compare, and hashCode so that Airports may be 
 * use as keys in a table and sorted.
 */
public class Airport implements Comparable<Airport> {
    /** three letter airport code */
    protected String code;

    /** full name of airport */
    protected String name;

    public Airport(String code, String name) {
	this.code = code;
	this.name = name;
    }

    /**
     * @post return the airport code.
     */
    public String code() { 
	return code; 
    } 

    /**
     * @post return the airport name.
     */
    String name() { 
	return name; 
    }

    /** 
     * @pre  o is a non-null Airport object.
     * @post returns < 0 if code is lexically before o.code
     *               = 0 if code is equal to o.code
     *               > 0 if code is lexically after o.code.
     */
    public int compareTo(Airport o) {
	return code.compareTo(o.code());
    }

    /** 
     * @pre  o is a non-null Airport object.
     * @post returns true if codes are equal, and false otherwise.
     */
    public boolean equals(Object o) { 
	return o instanceof Airport && code.equals(((Airport)o).code());
    }

    /**
     * @post returns a hashcode based on the airport code.
     */
    public int hashCode() { 
	return code.hashCode();
    }

    public String toString() { 
	return code; 
    }

}
