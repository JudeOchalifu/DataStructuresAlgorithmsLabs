import objectdraw.*;
import java.awt.*;

public class VectorView
{
    //Vector values 
    private VectorModel v1; 
    private double vx, vy, startX, startY, endX, endY, rx, ry, lx, ly, dx, dy, angle, m, scaleAmount;
    private String renderMode; 

    //Vectors 
    private Line mainVector, mainVectorVX, mainVectorVY; 
    private Line rn, ln; 

    //Text 
    private Text mainVectorText, statusBox; 
    private Text rnText,lnText,vxText,vyText; 

    private Text rnLabel, lnLabel, vxLabel, vyLabel; 

    //Canvas reference 
    DrawingCanvas canvas; 

    public VectorView(VectorModel vector, String renderMode, double scaleAmount, DrawingCanvas canvas){
        //Find the status box display type type
        if( renderMode.equals("basic")){
            this.renderMode = "basic";
        }
        else if( renderMode.equals("detailed")){
            this.renderMode = "detailed";
        }
        else if( renderMode.equals("status")){
            this.renderMode = "status";
        }
        else this.renderMode = "basic";

        this.v1 = vector; 
        this.scaleAmount = scaleAmount; 

        this.canvas = canvas; 

        addedToStage(); 
    }

    public void addedToStage(){
        mainVector = new Line(0,0,0,0,canvas); 

        //must intialize in case the vector view render mode is changed 
        mainVectorVX = new Line(0,0,0,0,canvas); 
        mainVectorVY = new Line(0,0,0,0,canvas); 
        rn = new Line(0,0,0,0,canvas); 
        ln = new Line(0,0,0,0,canvas); 

        rnLabel = new Text("rn",0,0,canvas);
        lnLabel = new Text("ln",0,0,canvas); 
        vxLabel = new Text("vx",0,0,canvas); 
        vyLabel = new Text("vy",0,0,canvas); 

        statusBox = new Text("",10,10,canvas); 

        
    }

    public void update(){
        //New Vector coordinates 
        startX = v1.getA().getX(); 
        startY = v1.getA().getY(); 
        m = v1.getM(); 

        //Scaling 
        if(scaleAmount != 1){
            double newM = m*scaleAmount; 
            m = newM; 

            endX = startX + v1.getDX()*m; 
            endY = startY + v1.getDY()*m; 
        }
        //Default 
        else{
            endX = v1.getB().getX(); 
            endY = v1.getB().getY(); 
        }

        vx = v1.getVX();
        vy = v1.getVY();
        rx = v1.getRX();
        ry = v1.getRY();
        lx = v1.getLX();
        ly = v1.getLY();
        dx = v1.getDX();
        dy = v1.getDY();
        angle = v1.getAngle();

        if(renderMode == "status"){
            statusBox.setText(
                "START: " + Math.round(startX) + ", " + Math.round(startY) +
                "\n" + "END: " + Math.round(endX) + ", " + Math.round(endY) +
                "\n" + "VX = " + Math.round(vx * 1000) / 1000 +
                "\n" + "VY = " + Math.round(vy * 1000) / 1000 +
                "\n" + "MAGNITUDE = " + Math.round(m * 1000) / 1000 +
                "\n" + "ANGLE = " + Math.round(angle*(180/Math.PI)*1000)/1000 +
                "\n" + "RX = " + Math.round(rx * 1000) / 1000 +
                "\n" + "RY = " + Math.round(ry * 1000) / 1000 +
                "\n" + "LX = " + Math.round(lx * 1000) / 1000 +
                "\n" + "LY = " + Math.round(ly * 1000) / 1000 +
                "\n" + "RN.b = " + Math.round(startX + rx) + ", " +  Math.round(startY + ry) +
                "\n" + "LN.b = " + Math.round(startX + lx) + ", " + Math.round(startY + ly) + 
                "\n" + "DX:" + Math.round(dx*1000)/1000 +
                "\n" + "DY:" + Math.round(dy*1000)/1000); 
        }

        //Draw the main vector 
        mainVector.setEndPoints(startX,startY,endX,endY); 

        //If not basic, draw extra components 
        if(renderMode != "basic"){
            //X component 
            mainVectorVX.setEndPoints(startX,startY,endX,startY); 

            //Y component 
            mainVectorVY.setEndPoints(endX,startY,endX,endY); 

            //Right hand normal 
            rn.setEndPoints(startX,startY,startX+rx,startY+ry); 

            //Left hand normal 
            ln.setEndPoints(startX,startY,startX+lx,startY+ly); 

            //position x component label 
            vxLabel.moveTo(startX+vx/2,startY); 

            //position y component label 
            vyLabel.moveTo(startX+vx+2,startY+vy/2); 

            //position right hand normal text 
            rnLabel.moveTo(startX+rx,startY+ry); 

            //position left hand normal text 
            lnLabel.moveTo(startX+lx,startY+ly); 

            //Hide vx and vy labels if line is too short 
            if(Math.abs(vx)<20||Math.abs(vy)<20){
                vxLabel.hide(); 
                vyLabel.hide(); 
            }
            else {
                vxLabel.show(); 
                vyLabel.show(); 
            }

            //Hide vector normals text if line is too short 
            if(m>20){
                rnLabel.show(); 
                lnLabel.show();
            }
            else {
                rnLabel.hide(); 
                lnLabel.hide(); 
            }
        }

    }
    public void renderMode(String renderMode){
        if( renderMode.equals("basic")){
            this.renderMode = "basic";
        }
        else if( renderMode.equals("detailed")){
            this.renderMode = "detailed";
        }
        else this.renderMode = "basic";
    }

    public String getMode(){
        return renderMode;  
    }

    
}
