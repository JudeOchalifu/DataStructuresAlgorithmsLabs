import objectdraw.*;
import java.awt.*;

public class VectorMath
{
    public VectorMath(){

    }

    public double dotProduct(VectorModel v1, VectorModel v2){
        return v1.getVX()*v2.getDX() + v1.getVY() * v2.getDY(); 
    }

    public double dotProduct2(VectorModel v1, VectorModel v2){
        return v1.getVX()*v2.getVX() + v2.getVY()*v2.getVY(); 
    }

    public double perpProduct(VectorModel v1, VectorModel v2){
        double perpProduct = v1.getLX() * v2.getDX() + v1.getLY() * v2.getDY();
        if(perpProduct!=0)return perpProduct; 
        else return 1; 
    }

    //ratio between perpProducts of v1 and v2, helps find intersection point 
    public double ratio(VectorModel v1, VectorModel v2){
        if(v1.getDX()==v2.getDX()&&v1.getDY()==v2.getDY() || v1.getDX()==-v2.getDX()&&v1.getDY()==-v2.getDY()){
            return 1; 
        }
        if(v1.getM()==0 || v2.getM()==0){
            return 1;     
        }
        VectorModel v3 = new VectorModel(0,0,0,0,0,0,null); 
        v3.update(v1.getA().getX(),v1.getA().getY(),v2.getA().getX(),v2.getA().getY(),0,0); 

        double ratio = this.perpProduct(v3,v2)/this.perpProduct(v1,v2); 
        return ratio;
    }

    //distance between two points 
    public double distance(double aX, double aY, double bX, double bY){
        VectorModel v1 = new VectorModel(0,0,0,0,0,0,null);
        v1.update(aX,aY,bX,bY,0,0); 
        return v1.getM(); 
    }

    //collision force vector (vector that object has passed the collision point)
    //for vector of v1 onto v2
    public VectorModel collisionForce(VectorModel v1, VectorModel v2){
        double t = this.ratio(v1,v2); 
        double intersectionX = v1.getA().getX() + v1.getVX() * t; 
        double intersectionY = v1.getA().getY() + v1.getVY() * t; 

        //Calculate force of impact 
        double collisionForceVX = v1.getA().getX() + v1.getVX() - intersectionX; 
        double collisionForceVY = v1.getA().getY() + v1.getVY() - intersectionY; 

        //Create collision force vector to return 
        VectorModel collisionVector = new VectorModel(0,0,0,0,collisionForceVX,collisionForceVY,null); 

        return collisionVector; 
    }

    //calculate projection of v1 onto v2 
    public VectorModel project(VectorModel v1, VectorModel v2){
        double dp1 = this.dotProduct(v1,v2); 

        double vx = dp1 * v2.getDX(); 
        double vy = dp1 * v2.getDY(); 

        //if vectors have start and end points, return vector with start and end points 
        double aX, aY, bX, bY = 0; 

        //vector projection to return 
        VectorModel projectionVector = new VectorModel(0,0,0,0,0,0,null); 

        if(v2.getA().getX()!=0 && v2.getA().getY()!=0){
            aX = v2.getA().getX(); 
            aY = v2.getA().getY(); 
            bX = v2.getA().getX()+vx; 
            bY = v2.getA().getY()+vy; 
            projectionVector.update(aX,aY,bX,bY,0,0); 
        }
        else{
            projectionVector.update(0,0,0,0,vx,vy); 
        }
        return projectionVector; 
    }

    //Calculate a bounce vector
    public VectorModel bounce (VectorModel v1, VectorModel v2)
    {
        //Find the projection onto v2
        VectorModel p1= this.project(v1, v2);

        //Find the projection onto v2's normal
        VectorModel p2= this.project(v1, v2.getLN());

        //Calculate the bounce vector by adding up the projections
        //and reversing the projection onto the normal
        double bounceVx = p1.getVX() + (p2.getVX() * -1);
        double bounceVy = p1.getVY() + (p2.getVY() * -1);

        //Create a bounce VectorModel to return to the caller
        VectorModel bounceVector = new VectorModel(0,0,0,0, bounceVx, bounceVy, null);

        return bounceVector;
    }

    public VectorModel bounce2(VectorModel v1, VectorModel v2){
        //Find the projection onto v2
        VectorModel p1 = this.project(v1, v2);

        //Find the projection onto v2's normal
        VectorModel p2 = this.project(v1, v2.getLN());

        //Calculate the bounce vector by adding up the projections
        //and reversing the projection onto the normal
        double bounceVx = p1.getVX() + p2.getVX();
        double bounceVy = p1.getVY() + p2.getVY();

        //Create a bounce VectorModel to return to the caller
        VectorModel bounceVector = new VectorModel(0,0,0,0, bounceVx, bounceVy, null);

        return bounceVector;
    }

    public double findIntersection(VectorModel v1, VectorModel v2)
    {
        //Find out if the vectors are paralell
        if ((v1.getDX() == v2.getDX() && v1.getDY() == v2.getDY()) 
        || (v1.getDX() == -v2.getDX() && v1.getDY() == -v2.getDY())) 
        {
            return 1;
        } 
        else 
        {
            //Create two new vectors between the 
            //start points of vectors 1 and 2
            VectorModel v3 = new VectorModel(v1.getA().getX(), v1.getA().getY(), v2.getA().getX(), v2.getA().getY(), 0, 0, null);
            VectorModel v4 = new VectorModel(v2.getA().getX(), v2.getA().getY(), v1.getA().getX(), v1.getA().getY(), 0, 0, null);

            double t1 = this.perpProduct(v3, v2) / this.perpProduct(v1, v2);
            double t2 = this.perpProduct(v4, v1) / this.perpProduct(v2, v1);

            if (t1 > 0 && t1 <= 1 && t2 > 0 && t2 <= 1) 
            {
                return t1;
            } 
            else 
            {
                return 1;
            }
        }
    }

    //Use this method to create bounce and friction for the verlet object.
    //It takes four arguments: 
    //1. The verlet model
    //2. The vector that you want to bounce it against
    //3. The bounce multiplier, for the amount of "bounciness"
    //4. The friction multiplier, for the amount of surface friction between objects
    public void bounceOnPlane (AVerletModel verletModel, VectorModel plane, double bounce, double friction)
    {
        VectorModel v1 = new VectorModel (verletModel.xPos, verletModel.yPos, verletModel.xPos + verletModel.vx, verletModel.yPos + verletModel.vy, 0, 0, null);

        //Find the projection vectors
        VectorModel p1 = this.project(v1, plane);
        VectorModel p2 = this.project(v1, plane.getLN());

        //Calculate the bounce vector
        double bounceVX = p2.getVX() * -1;
        double bounceVY = p2.getVY() * -1;

        //Calculate the friction vector
        double frictionVX = p1.getVX();
        double frictionVY = p1.getVY();

        verletModel.setVX = (bounceVX * bounce) + (frictionVX * friction);
        verletModel.setVY = (bounceVY * bounce) + (frictionVY * friction);	                                  
    }
}


