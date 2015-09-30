
import structure5.*;
import java.io.*;

/**
 * This class represents one Darwin instruction.  Instructions
 * contain two parts: an opcode and an address.  For instructions
 * that do not perform jumps (hop, left, right, infect), the address
 * part is not used.
 * 
 * The fill instruction set is the following:<p>
 * <pre>
 *   hop
 *   left
 *   right
 *   infect
 *   ifempty
 *   ifwall
 *   ifsame
 *   ifenemy
 *   ifrandom
 *   go
 * </pre>
 * The following instructions require a target address to jump to:
 *   <pre>ifempty, ifwall, ifsame, ifenemy, ifrandom, go</pre>
 *
 */
public class Instruction {

    /** opcode for the hop instruction */
    public static final int HOP =      1;
    /** opcode for the left instruction */
    public static final int LEFT =     2;
    /** opcode for the right instruction */
    public static final int RIGHT =    3;
    /** opcode for the infect instruction */
    public static final int INFECT =   4;
    /** opcode for the ifempty instruction */
    public static final int IFEMPTY =  5;
    /** opcode for the ifwall instruction */
    public static final int IFWALL =   6;
    /** opcode for the ifsame instruction */
    public static final int IFSAME =   7;
    /** opcode for the ifenemy instruction */
    public static final int IFENEMY =  8;
    /** opcode for the ifrandom instruction */
    public static final int IFRANDOM = 9;
    /** opcode for the go instruction */
    public static final int GO =       10;

    protected int opcode;  /** the opcode */
    protected int address; /** the address */

    /**
     * Creates a new instruction.  address is the target of 
     * the operation, if one is needed.  Otherwise it is not used.
     * @pre 0 <= opcode <= GO.
     */
    public Instruction(String opcode, int address) {
	this(Instruction.toInt(opcode),address); 
    }
    public Instruction(int opcode, int address) {
	Assert.pre(0 < opcode && opcode <= GO, "Bad opcode");
	this.opcode = opcode;
	this.address = address;
    }

    /**
     * Returns the opcode
     * @post returns the opcode
     */
    public int getOpcode() {
	return opcode;
    }

    /**
     * Returns the addrss
     * @post returns the address
     */
    public void setAddress(int address) {
	this.address = address;
    }
    public int getAddress() {
	return address;
    }
    
    /**
     * Returns a printable representation of an Instruction
     */
    public String toString() {
	switch (opcode) {
	case HOP:      return "hop";
	case LEFT:     return "left";
	case RIGHT:    return "right";
	case INFECT:   return "infect " + address;
	case IFEMPTY:  return "ifempty " + address;
	case IFWALL:   return "ifwall " + address;
	case IFSAME:   return "ifsame " + address;
	case IFENEMY:  return "ifenemy " + address;
	case IFRANDOM: return "ifrandom " + address;
	case GO:       return "go " + address;
	default:
	    return "BAD INSTRUCTION: " + opcode + " " + address;
	}
    }

    static int toInt(String opcode) {
	if(opcode.equals("hop")) return HOP; 
	else if(opcode.equals("left")) return LEFT; 
	else if(opcode.equals("right")) return RIGHT;
	else if(opcode.equals("infect")) return INFECT; 
	else if(opcode.equals("ifempty")) return IFEMPTY; 
	else if(opcode.equals("ifwall")) return IFWALL; 
	else if(opcode.equals("ifsame")) return IFSAME; 
	else if(opcode.equals("ifenemy")) return IFENEMY;
	else if(opcode.equals("ifrandom")) return IFRANDOM;
	else if(opcode.equals("go")) return GO;
	else Assert.condition(false, "wrong input"); 
	return -1;
    }
}

