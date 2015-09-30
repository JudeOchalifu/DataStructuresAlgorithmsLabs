import structure5.*;
import java.util.Iterator; 

//A class for nodes in the binary tree.  Each LexiconNode keeps a reference to the letter it holds, a reference to its parent, and a reference to its children held in a vector 
public class LexiconNode implements Comparable<LexiconNode>{

    private char letter; 
    private Vector<LexiconNode> childrenPointers; 
    private LexiconNode parent; 
    private boolean isWord; 

    //automatically set isWord to false and just call helper method to change this boolean when needed
    public LexiconNode(char letter){
	this.letter = letter; 
	isWord = false; 
	childrenPointers = new Vector<LexiconNode>(); 
    }

    //return a vector of this node's children 
    public Vector<LexiconNode> getChildrenPointers(){
	return childrenPointers; 
    }

    //return the letter that this node contains 
    public char getLetter(){
	return this.letter; 
    }
    
    //return the LexiconNode child that contains the given letter.  If this node already exists, return the existing node, else, create a new node 
    public LexiconNode addChild(char letter){
	LexiconNode child = this.getChild(letter); 
	if(child==null){
	    int i = 0; 
	    //step through the childrenPointers vector until the next letter is greater than the given letter we are adding, this ensures that all children in the childrenPointers are in alphabetial order 
	    while(i<childrenPointers.size() && letter > childrenPointers.get(i).getLetter()) i++; 
	    childrenPointers.add(i,new LexiconNode(letter) ); 
	    childrenPointers.get(i).setParent(this); //update parent whenever new node created 
	    return childrenPointers.get(i); 
	}
	return child; 
    }
    
    //get the child of this node with the given letter.  Steps through the childrenPointers vectory 
    public LexiconNode getChild(char letter){
	int i = 0; 
	while(i<childrenPointers.size()){
	    if(childrenPointers.get(i).getLetter() == letter)
		return childrenPointers.get(i); 
	    i++; 
	}
	return null; 
    }
    //get the LexiconNode child with the given letter, remove then return it 
    public LexiconNode removeChild(char letter){
	LexiconNode removed = this.getChild(letter); 
	if(removed!=null){
	    childrenPointers.remove(removed); 
	    removed.setParent(null); 
	}
	return removed; 
    }
    //should be called by the parent of this node 
    public void setParent(LexiconNode parent){
	this.parent = parent; 
    }
    public LexiconNode getParent(){
	return this.parent; 
    }
    public boolean isWord(){
	return this.isWord; 
    }
    public void setIsWord(boolean set){
	this.isWord = set; 
    }
    public Iterator<LexiconNode> iterator(){
	return childrenPointers.iterator(); 
    }

    public int compareTo(LexiconNode o){
	return this.getLetter() - o.getLetter(); 
    }
    public String toString(){
	return Character.toString(letter);
    }
    
}


class Test{

    public static void main(String args[]){

	LexiconTrie test = new LexiconTrie(); 
	test.addWordsFromFile("small2.txt"); 
	test.removeWord("a"); 
	//test.removeWord("are");
	System.out.println(test.containsWord("a")); 
	System.out.println(test.containsPrefix("are")); 
	System.out.println(test.containsWord("as")); 
	System.out.println(test.containsPrefix("ar")); 
    }
}