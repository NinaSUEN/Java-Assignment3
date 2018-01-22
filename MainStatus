/**
* @author Suen Tsz Ching
*/

// import ColorSensor
import ShefRobot.*;
import ShefRobot.ColorSensor.Color.*;
import sheffield.*;


public class MainStatus{

    public static final ColorSensor.Color WHITE;
    public static final ColorSensor.Color BLACK;
    public static final ColorSensor.Color BLUE;
    public static final ColorSensor.Color NONE;

    public static enum Color {BLACK, WHITE, BLUE, NONE};
    public static enum BallStatus {YES, NO, OTHER};


    public Color colour;
    public BallStatus ballstatus;

    public MainStatus(Color colour, BallStatus ballstatus){
   	    super();
   	    this.colour = colour;
   	    this.ballstatus = ballstatus;  
    }

    public void setColour(Color colour){
    	this.colour = colour;
    }

    public static Color getColour(){
     	return Colour;
    }


    public void setBallstatus(BallStatus ballstatus){
   	    this.ballstatus = ballstatus;
    }

    public static ballstatus getBallstatus(){
   	    return ballstatus;
    }

    public static String toString(){
    	return "Our Robot's information : " + "\nColour : " + colour + ", Status : " + ballstatus;
    }

}



