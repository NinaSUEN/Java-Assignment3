/**
* @author Suen Tsz Ching
*/

import ShefRobot.*;
import sheffield.*;

public class Assignment3{

    static EV3football mainRobot = new EV3football();

    public static Robot myRobot = new Robot();
	
    public static ColorSensor colorSensor = myRobot.getColorSensor(Sensor.Port.S2);
    public static UltrasonicSensor ultrasonicSensor = myRobot.getUltrasonicSensor(Sensor.Port.S1);

    public static Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
    public static Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
    public static Motor mediumMotor = myRobot.getMediumMotor(Motor.Port.A);
    public static Speaker speaker = myRobot.getSpeaker();


    public static final ColorSensor.Color BLUE;
    public static final ColorSensor.Color WHITE;
    public static final ColorSensor.Color BLACK;
    public static final ColorSensor.Color NONE;

    public static enum AreaEnum {BLACK_LINE, WHITE_AREA, BLUE_AREA, ERROR};
    public static AreaEnum areaCode;

    static boolean grabTheBall;
    static int currentDistance = ultrasonicSensor.getDistance();


    public static void main(String[] args){ 

        LCD.drawString("The robot is ready to start, here we go!", 0, 0);
        Button.waitForAnyPress();

        mainRobot.findTheBlackLine();

        //mainRobot.finishEverything();

        //mainRobot.waggleDance();

        LCD.clear();
        LCD.drawString("FINISH!", 0, 0);
        LCD.refresh();
        mainRobot.stopMoving();
        mainRobot.close();
        System.exit(0);
    }
 
    public static AreaEnum mainArea(){

        if(colorSensor.getColor().equals(ColorSensor.Color.BLACK)){
            areaCode = AreaEnum.BLACK_LINE;
        }
    	  else if(colorSensor.getColor().equals(ColorSensor.Color.WHITE)){
            areaCode = AreaEnum.WHITE_AREA;
    	  }
    	  else if(colorSensor.getColor().equals(ColorSensor.Color.BLUE)){
    		    areaCode = AreaEnum.BLUE_AREA;
    	  }
    	  else{
            areaCode = AreaEnum.ERROR;
    	  }
    }
    
  
    public static void findTheBlackLine(){

        System.out.println("Trying to find a black line!");
        mainRobot.forwardMoving();

        if((System.currentTimeMillis() > 20) || (mainRobot.isMoving() != false)){
        while(mainArea() != AreaEnum.BLACK_LINE){

            mainRobot.forwardMoving();

            if(mainArea() == AreaEnum.WHITE_AREA){
                mainRobot.forwardMoving();
            }
            else if(mainArea() == AreaEnum.BLUE_AREA){
                mainRobot.getIntoTheBlueArea();
            }
            else{
            	mainRobot.initialization();
            }
        }
        followTheBlackLine();
	    }
	    else{
        	System.out.println("The robot takes it for too long, check for it!");
        	mainRobot.initialization();
        }
    }


	  public static void followTheBlackLine(){

		    System.out.println("Trying to follow a black line!");
		    mainRobot.forwardMoving();

		    if((System.currentTimeMillis() > 20) || (mainRobot.isMoving() != false)){

		    while(mainArea() == AreaEnum.BLACK_LINE){

			      mainRobot.forwardMoving();

			      if(mainArea() == AreaEnum.BLUE_AREA){
                 mainRobot.getIntoTheBlueArea();
			      }
			      else if(mainArea() == AreaEnum.WHITE_AREA){
                mainRobot.findTheBlackLine();
			      }
			      else{
				        mainRobot.initialization();
			      }
        }
        }
        else{
            System.out.println("The robot takes it for too long, check for it!");
            mainRobot.initialization();
        }
    }


    public static void getIntoTheBlueArea(){

        System.out.println("We have got into the Blue Area, we are looking for a ball now!");

        mainRobot.run();

        if(grabTheBall != false){
            MainStatus success = new MainStatus(BLUE, YES);
            System.out.println(success.toString());
            mainRobot.turningAround();
            mainRobot.findTheBlackLine();
        }
        else if(grabTheBall == false){
            MainStatus fail = new MainStatus(BLUE, NO);
            System.out.println(fail.toString());
            mainRobot.initialization();
        }
        else{
            MainStatus unknown = new MainStatus(BLUE, OTHER);
            System.out.println(unknown.toString());
            mainRobot.initialization();
        }
    }


    public static boolean grabTheBall(int currentDistance){

        while((currentDistance > 6) || ultrasonicSensor.disable()){

        mainRobot.forwardMoving();

            if((currentDistance < 15) && (currentDistance < 0)){
        		    System.out.println(currentDistance);
        		    MainStatus.BallStatus.YES;
        		    grabTheBall = true;
            }
            else if(ultrasonicSensor.getDistance() >= 15){
                System.out.println(ultrasonicSensor.getDistance());
        	      MainStatus.BallStatus.NO;
        	      grabTheBall = false;
            }
            else{
                System.out.println(ultrasonicSensor.getDistance());
        	      MainStatus.BallStatus.OTHER;
        	      grabTheBall = false;
        	      //return 255;
            }
            return grabTheBall;
        }
    }


    public static void turningAround(){
        speaker.playTone(TURNING_TIME, TURNING_SPEED);

        leftMotor.setPower(HIGH_POWER);
        rightMotor.setPower(HIGH_POWER);		
        leftMotor.setSpeed(TURNING_SPEED);
        rightMotor.setSpeed(NONE_SPEED);
 
        leftMotor.rotateTo(0);
    	  rightMotor.rotateTo(0);

        leftMotor.rotate(180, true);
    	  rightMotor.rotate(-180, true);

    	  //leftMotor.forward();
    	  //rightMotor.backward();
    }

    public static void finishEverything(){

    }

    
    public static ColorSensor.Color getColor(){

    	//getColor = colorSensor.getColor();
   	    
   	    if(mainRobot.getColor.equals(BLACK)){
            return MainStatus.Color.BLACK;
   	    }
   	    else if(mainRobot.getColor.equals(WHITE)){
     		    return MainStatus.Color.BLACK;
   	    }
   	    else if(mainRobot.getColor.equals(BLUE)){
            return MainStatus.Color.BLUE;
   	    }
   	    else if(mainRobot.getColor.equals(NONE)){
            return MainStatus.Color.NONE;
   	    }
   	    else{
            return MinStatus.Color;
        }

    }

}
