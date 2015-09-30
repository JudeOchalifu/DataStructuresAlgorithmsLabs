import structure5.*;
import java.util.Random; 

/*
 * A class to store one non-terminal and its productions.
 * You should not need to modify this class.

like:
<verb> {
    sigh <adverb>  <= this is a production                            ;
    portend like <object>                      ;
    die <adverb>                               ;
} 

 */
public class Definition {
    // Each production in this vector is a Vector of Strings.
    protected Vector<Vector<String>> productions;

    /**
     * Create a new definition for a non-terminal
     */
    public Definition() {
	productions = new Vector<Vector<String>>();
    }

    /**
     * pre:  prod is a non-null vector of Strings  <p>
     * post: prod is added as a production for the definition.
     */
    public void add(Vector<String> prod) {
	productions.add(prod);
    }

    /**
     * post: return the number of productions 
     */
    public int size() {
	return productions.size();
    }

    /**
     * pre:  0 <= index < size()
     * post: return the production at given index
     */
    public Vector<String> get(int index) {
	return productions.get(index);
    }

    public Vector<String> getRandom(){
	Random random = new Random(); 
	return this.get(random.nextInt(this.size()));
    }

    /**
     * Print out the productions for this definition.
     */
    public String toString() {
	String s = "";
	for (int i = 0; i < productions.size(); i++) {
	    s += "      ";
	    Vector<String> prodVec = productions.get(i);
	    for (int j = 0; j < prodVec.size(); j++) {
		s += prodVec.get(j) + " ";
	    }
	    s += "\n";
	}
	return s;
    }
}
