/**
* @author Suen Tsz Ching
*/

import ShefRobot.*;
//import sheffield.*;

public class Assignment3{

    public static EV3football mainRobot = new EV3football();


    public static enum AreaEnum {BLACK_LINE, WHITE_AREA, BLUE_AREA, ERROR};
    public static AreaEnum areaCode;


    public static void main(String[] args){ 

        mainRobot.colorSensor.setFloodlightState(ColorSensor.FloodlightState.OFF);
        mainRobot.colorSensor.setMode(ColorSensor.Mode.COLOR);

        System.out.println(mainRobot.colorSensor.getFloodlightState());

        followTheBlackLine();
        
        Thread t = new Thread(new EV3football());
		t.start();

        EV3football.leftMotor.run(); 
        EV3football.rightMotor.run();
        EV3football.mediumMotor.run();
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
        mainRobot.leftMotor.setSpeed(mainRobot.MOVING_SPEED);
        mainRobot.rightMotor.setSpeed(mainRobot.MOVING_SPEED);
        mainRobot.leftMotor.forward();
        mainRobot.rightMotor.forward();
    }
    
    public static void followTheBlackLine(){
    int counter=0;
    while (mainArea() != AreaEnum.ERROR){	
    	while (mainArea() == AreaEnum.BLACK_LINE){
    		counter=0;
    		System.out.println("Black");
	 		keepMovingForward();
	 	}
		
		while (mainArea() == AreaEnum.BLUE_AREA){
			counter++;
			if (counter<=3){
	 	 	 System.out.println("White1");
	 	 	 EV3football.turnLeft();
	 	 	 mainRobot.myRobot.sleep(700);
	 	 	 
	 	 	 if (mainArea() == AreaEnum.BLUE_AREA){
	 	 	 System.out.println("White2");
	 	 	 EV3football.turnRight();
	 	 	 mainRobot.myRobot.sleep(1000);

	 	 	 }
	 	 	 
	 	 	}
	 	 	else {System.out.println("Blue");
	 	 	 getIntoTheBlueArea();
	 	 	 return;
	 	 	}
		}
	}
		
}
  


    public static void getIntoTheBlueArea(){

        System.out.println("We have got into the Blue Area, we are looking for a ball now!");

        System.out.println("The Floor light is : " + mainRobot.colorSensor.getFloodlightState());
        System.out.println("The Floor colour is : " + mainRobot.colorSensor.getColor());

        mainRobot.colorSensor.setFloodlightState(ColorSensor.FloodlightState.BLUE);
        mainRobot.leftMotor.stop();
        mainRobot.rightMotor.stop();
    }

    
}

