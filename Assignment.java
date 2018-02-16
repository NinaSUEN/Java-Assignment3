/**
* @author Suen Tsz Ching
*/

import ShefRobot.*;
import sheffield.*;

public class Assignment{

    public static EV3football mainRobot = new EV3football();


    public static enum AreaEnum {BLACK_LINE, WHITE_AREA, BLUE_AREA, ERROR};
    public static AreaEnum areaCode;

    public static enum BallStatus {YES, NO, OTHER};
    public static BallStatus ballstatus;
    

    static boolean grabTheBall;


    public static void main(String[] args){ 

        // LCD.drawString("The robot is ready to start, here we go!", 0, 0);
        // Button.waitForAnyPress();

        mainRobot.colorSensor.setFloodlightState(ColorSensor.FloodlightState.OFF);
        mainRobot.colorSensor.setMode(ColorSensor.Mode.COLOR);

        System.out.println(mainRobot.colorSensor.getFloodlightState());

        findTheBlackLine();

        //mainRobot.finishEverything();

        //mainRobot.waggleDance();

        // LCD.clear();
        // LCD.drawString("FINISH!", 0, 0);
        // LCD.refresh();
        // mainRobot.stopMoving();
        // mainRobot.close();
        // System.exit(0);
    }
 
    public static AreaEnum mainArea(){

        if(mainRobot.colorSensor.getColor().equals(ColorSensor.Color.BLACK)){
            areaCode = AreaEnum.BLACK_LINE;
        }
    	else if(mainRobot.colorSensor.getColor().equals(ColorSensor.Color.WHITE)){
            areaCode = AreaEnum.WHITE_AREA;
    	}
    	else if(mainRobot.colorSensor.getColor().equals(ColorSensor.Color.BLUE)){
    		areaCode = AreaEnum.BLUE_AREA;
    	}
    	else{
            areaCode = AreaEnum.ERROR;
    	}
        return areaCode;
    }

    
    

    public static void keepMovingForward(){
        //mainRobot.speaker.playTone(mainRobot.MOVING_TIME, mainRobot.MOVING_SPEED);

        mainRobot.leftMotor.setSpeed(mainRobot.MOVING_SPEED);
        mainRobot.rightMotor.setSpeed(mainRobot.MOVING_SPEED);
        mainRobot.leftMotor.forward();
        mainRobot.rightMotor.forward();
    }
  
    // is not completed
    public static void findTheBlackLine(){

        System.out.println("Finding a black line!");
        keepMovingForward();

        if((mainRobot.leftMotor.isMoving() != false) || (mainRobot.rightMotor.isMoving() != false)){

        while(mainArea() != AreaEnum.BLACK_LINE){

            keepMovingForward();
        }

            if(mainArea() == AreaEnum.WHITE_AREA){
                keepMovingForward();
            }
            else if(mainArea() == AreaEnum.BLUE_AREA){
                //getIntoTheBlueArea();
            }
            else{
                System.out.println("ERROR! check for it!");
            	initialization();
            }
        }
        followTheBlackLine();
    }


	public static void followTheBlackLine(){

        mainRobot.colorSensor.setFloodlightState(ColorSensor.FloodlightState.OFF);

		System.out.println("Following a black line!");
	    keepMovingForward();

	    if((mainRobot.leftMotor.isMoving() != false) || (mainRobot.rightMotor.isMoving() != false)){

		    while(mainArea() == AreaEnum.BLACK_LINE){

			    keepMovingForward();
            }

			    if(mainArea() == AreaEnum.BLUE_AREA){
                    getIntoTheBlueArea();
			    }
			    else if(mainArea() == AreaEnum.WHITE_AREA){
                    findTheBlackLine();
			    }
			    else{
                    System.out.println("ERROR! check for it!");
				    initialization();
			    }
        }
        else{
            System.out.println("Check the motor is installed or not!");
        }
    }

    /*
    * @author Suen Tsz Ching
    * initialize the robot when an error was occurred.
    */
    public static void initialization(){

        System.out.println("Some errors bring the robot here!");
        mainRobot.myRobot.sleep(mainRobot.MOVING_TIME);

        mainRobot.leftMotor.setSpeed(mainRobot.LOW_SPEED);
        mainRobot.rightMotor.setSpeed(mainRobot.LOW_SPEED);
        mainRobot.mediumMotor.setSpeed(mainRobot.LOW_SPEED);

        mainRobot.mediumMotor.stop();
        mainRobot.leftMotor.stop();
        mainRobot.rightMotor.stop();

        System.out.println("The Floor light is : " + mainRobot.colorSensor.getFloodlightState());
        System.out.println("The Floor colour is : " + mainRobot.colorSensor.getColor());
    }


    public static void getIntoTheBlueArea(){

        System.out.println("We have got into the Blue Area, we are looking for a ball now!");

        System.out.println("The Floor light is : " + mainRobot.colorSensor.getFloodlightState());
        System.out.println("The Floor colour is : " + mainRobot.colorSensor.getColor());

        mainRobot.colorSensor.setFloodlightState(ColorSensor.FloodlightState.BLUE);
    }


    public static void keepTurningAround(){

        mainRobot.leftMotor.resetTachoCount();
        mainRobot.rightMotor.resetTachoCount();

        if((mainRobot.leftMotor.isMoving() != false) || (mainRobot.rightMotor.isMoving() != false)){

            //if(){
                while(mainRobot.rightMotor.getTachoCount() <= 89){


                    mainRobot.leftMotor.setSpeed(mainRobot.LOW_SPEED);
                    mainRobot.rightMotor.setSpeed(mainRobot.LOW_SPEED);
                    mainRobot.rightMotor.backward();
                    mainRobot.leftMotor.forward();
                    System.out.println(mainRobot.rightMotor.getTachoCount());
                }
            //}
            //else{
                //while(mainRobot.rightMotor.rotateTo((-180), true)){
                    System.out.println(mainRobot.rightMotor.getTachoCount());
                    mainRobot.leftMotor.setSpeed(mainRobot.LOW_SPEED);
                    mainRobot.rightMotor.setSpeed(mainRobot.LOW_SPEED);
                    mainRobot.rightMotor.forward();
                    mainRobot.leftMotor.backward();
                //}
            //} 
        }
        else{
            System.out.println("Check the motor is installed or not!");
        }
	
 
     //    leftMotor.rotateTo(0);
    	// rightMotor.rotateTo(0);

     //    leftMotor.rotate(180, true);
    	// rightMotor.rotate(-180, true);

    	//leftMotor.forward();
        //rightMotor.backward();
    }

    public static void finishEverything(){

    }

    /*
    * @author Panagiotis Antoniou
    * Calls the robot to have a dancing
    */
    public static void waggleDance(){ 

     for (int t=1; t<10; t++){
         mainRobot.leftMotor.setSpeed(mainRobot.TURNING_SPEED);
         mainRobot.rightMotor.setSpeed(mainRobot.TURNING_SPEED);
         mainRobot.leftMotor.forward();
         mainRobot.rightMotor.backward();
         mainRobot.rightMotor.forward();
         mainRobot.leftMotor.backward();
     }
    }
    
}
