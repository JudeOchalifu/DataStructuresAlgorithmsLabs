
import objectdraw.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


public class Display extends WindowController{

    VectorModel model; 
    VectorView view; 
  
    public void begin()
    {
        model = new VectorModel(5,5,50,10,0,0,null); 
        view = new VectorView(model, "status", 1, canvas); 
        model.giveView(view); 
    }
    
    public void onMouseMove(Location point){
        model.update(100,100,point.getX(),point.getY(),0,0);
        
    }
    
  

    }


 