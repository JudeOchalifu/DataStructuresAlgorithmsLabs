import objectdraw.*;
import java.awt.*;

public class VectorModel{
    private Location a, b; 
    private double vx,vy;
    private VectorView view; 
    
    public VectorModel(double startX, double startY, double endX, double endY, double newVX, double newVY, VectorView view){
        update(startX,startY,endX,endY,newVX,newVY); 
        this.view = view; 
    }
    public void giveView(VectorView view){
        this.view = view;
        view.update(); 
    }
    public void update(double startX, double startY, double endX, double endY, double newVX, double newVY){
        if(newVX == 0 && newVY == 0)
        {
            a = new Location(startX,startY); 
            b = new Location(endX,endY); 
            if(view!=null)view.update(); 
        }
        else
        {
            vx = newVX;
            vy = newVY;
            if(view!=null)view.update(); 
        }
    }
    //Start Location 
    public Location getA(){
        return a;
    }
    //End Location 
    public Location getB(){
        return b;
    }
    //VX
    public double getVX(){
        if(vx==0){
            return b.getX() - a.getX(); 
        }
        else 
            return vx; 
    }
    //VY
    public double getVY(){
        if(vy==0){
            return b.getY() - a.getY(); 
        }
        else 
            return vy; 
    }
    //angle 
    public double getAngle(){
        return Math.atan2(getVY(),getVX()); 
    }
    //magnitude 
    public double getM(){
        if(this.getVX()!=0||this.getVY()!=0){
            return Math.sqrt(getVX()*getVX()+getVY()*getVY()); 
        }
        else return 0; 
    }
    //Left normal 
    public VectorModel getLN(){

                VectorModel leftNormal = new VectorModel(0,0,0,0,0,0,view); 
        if(vx==0&&vy==0){
            leftNormal.update(a.getX(),a.getY(),a.getX()+getLX(),a.getY()+this.getLY(),0,0); 
        }
        else leftNormal.update(0,0,0,0,this.getVX(),this.getVY()); 
        
        return leftNormal; 
    }
    //Right normal 
    public VectorModel getRN(){
        VectorModel rightNormal = new VectorModel(0,0,0,0,0,0,view); 
        if(vx==0&&vy==0){
            rightNormal.update(a.getX(),a.getY(),a.getX()+getRX(),a.getY()+this.getRY(),0,0); 
        }
        else rightNormal.update(0,0,0,0,this.getVX(),this.getVY()); 
        
        return rightNormal; 
    }
    //Right normal x 
    public double getRX(){
        return -getVY(); 
    }
    //Right normal y
    public double getRY(){
        return getVX(); 
    }
    //Left normal x 
    public double getLX(){
        return getVY(); 
    }
    //Left normal y
    public double getLY(){
        return -getVX(); 
    }
    //Normalized Vector x and y 
    public double getDX(){
        if(getM() != 0) return vx/getM(); 
        else return 0.001; 
    }
    public double getDY(){
        if(getM() != 0) return vy/getM(); 
        else return 0.001; 
    }



}