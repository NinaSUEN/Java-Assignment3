/**
* Class Assignment3.java
* This is the main class which includes the following the line method and has an object of class EV3football
* that uses its methods for controlling the robot. 
* @author Bryn Berkeley, Suen Tsz Ching, Panagiotis Antoniou, Tiange Xiang
*/

//use the package that includes all the methods of the Robot.
import ShefRobot.*;

public class Assignment3{
	
	//get a reference of EV3football class for our robot
	public static EV3football mainRobot = new EV3football();
	
	//create an enum that represents the area that the robot will be demonstrated
	public static enum AreaEnum {BLACK_LINE, WHITE_AREA, BLUE_AREA, ERROR};
    	public static AreaEnum areaCode;
	
	/**
	* the main method 
	* @author Panagiotis Antoniou, Suen Tsz Ching	
	* @param args not used
	*/
	public static void main(String[] args){ 	
		//set the light emitted by the sensor to OFF
		mainRobot.colorSensor.setFloodlightState(ColorSensor.FloodlightState.OFF);
		//set the mode for Colour Sensor which fits what we need
		mainRobot.colorSensor.setMode(ColorSensor.Mode.COLOR);
		
		//debugging statement to see what colour is the floor area
		System.out.println(mainRobot.colorSensor.getFloodlightState());
		
		//call the follow the black line method
		followTheBlackLine();   
		
		//create new thread to use the methods of controlling the robots in EV3football class
		Thread t = new Thread(new EV3football());
		t.start();
		
		//call the methods to be used in the program for controlling the robot
		EV3football.leftMotor.run(); 
		EV3football.rightMotor.run();
		EV3football.mediumMotor.run();
	}
	
	/**
	* Translates the different colours of the demonstrating area into the enum created 
	* @author Suen Tsz Ching
	* @return the colour of the area as an enum type called AreaEnum
	*/
 
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

	/**
	* Makes the robot move forward
	* @author Bryn Berkeley
	*/
   	public static void keepMovingForward(){
        	mainRobot.leftMotor.setSpeed(mainRobot.MOVING_SPEED);
        	mainRobot.rightMotor.setSpeed(mainRobot.MOVING_SPEED);
        	mainRobot.leftMotor.forward();
        	mainRobot.rightMotor.forward();
    	}	
	/**
	* Makes the robot follow the black line and try to find the black line
	* if the robot loses it
	* @author  Panagiotis Antoniou, Suen Tsz Ching, Bryn Berkeley, Tiange Xiang
	*/
    
   	public static void followTheBlackLine(){
		
		//use a counter because the sensor detects the blue colour instead of white
		//so that after a number of fail times of looking for the black line
		//it will result to the robot being in the blue area.
		
    		int counter=0;
    		while (mainArea() != AreaEnum.ERROR){	
			//if it detects the black line reset the counter and move forward
    			while (mainArea() == AreaEnum.BLACK_LINE){
    				counter=0;
    				System.out.println("Black");
	 			keepMovingForward();
	 		}
		
			while (mainArea() == AreaEnum.BLUE_AREA){
				//if it detects the blue line increment the counter and turn left
				counter++;
				
				//if the sensor senses blue no more than 4 times then look for the black line by turning
				if (counter<=4){
	 	 			System.out.println("Not Blue. Turning left");
	 	 			EV3football.turnLeft();
	 	 			mainRobot.myRobot.sleep(EV3football.SLEEP_TIME+200);
					
	 	 			//if it detects the blue line again turn right
	 	 			if (mainArea() == AreaEnum.BLUE_AREA){
	 	 				System.out.println("Not Blue again. Turning Right");
	 	 				EV3football.turnRight();
	 	 				mainRobot.myRobot.sleep(EV3football.SLEEP_TIME*2);
	 	 			} 
	 	 		}
				//if it detects the blue line more than 4 times then the robot is in the blue area
	 			else {
					System.out.println("Blue Area");
	 	 			getIntoTheBlueArea();
	 	 	 		return;
	 	 		}
			}
		}
		
    	}
  	
	/**
	* A method that decides what happens after the robot is in the Blue Area
	* @author Suen Tsz Ching
	*/
	public static void getIntoTheBlueArea(){
        	System.out.println("We have got into the Blue Area!");
		
		//debugging statements to see what colour is the floor and the sensor light
       		System.out.println("The Sensor light is : " + mainRobot.colorSensor.getFloodlightState());
        	System.out.println("The Floor colour is : " + mainRobot.colorSensor.getColor());
		
		//stop the Robot
       		mainRobot.leftMotor.stop();
        	mainRobot.rightMotor.stop();
   	}    
}

