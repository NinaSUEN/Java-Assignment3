/*
Written by: Bryn Berkeley, Panagiotis Antoniou, Tsz Ching Suen
*/
import ShefRobot.*;

public class Assignment3{
	
	public static Robot myRobot = new Robot();
	
	public static ColorSensor sensor = myRobot.getColorSensor(Sensor.Port.S2);
        public static Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
        public static Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
	
	// @author Panagiotis Antoniou
	public static void waggleDance(){ 
               for (int t=0; t<10; t++){
			leftMotor.setSpeed(150);
			rightMotor.setSpeed(150);
			leftMotor.forward();
			rightMotor.backward();
			myRobot.sleep(t+1*1000);
			rightMotor.forward();
			leftMotor.backward();
			myRobot.sleep(t+1*1000);
		}
	   }

	public static void main(String args[]){
		Robot myRobot = new Robot();
		ColorSensor sensor = myRobot.getColorSensor(Sensor.Port.S2);
		Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
		Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
		leftMotor.setSpeed(500);
		rightMotor.setSpeed(500);
		while ((sensor.getColor().equals(ColorSensor.Color.BLACK)) || (sensor.getColor().equals(ColorSensor.Color.BLACK)){
				while (sensor.getColor().equals(ColorSensor.Color.BLACK)){
					rightMotor.forward();
					leftMotor.forward();
				}
				do{
					leftMotor.setSpeed(300);
					leftMotor.forward();
					myRobot.sleep(1000);
					
				while ((sensor.getColor().equals(ColorSensor.Color.WHITE))
				}
				do{
					rightMotor.setSpeed(300);
					rightMotor.forward();
					myRobot.sleep(2000);
					
				while ((sensor.getColor().equals(ColorSensor.Color.WHITE))
				}
			
			
		}
		private boolean lookForLine(){
		
		}
		
		leftMotor.stop();
		rightMotor.stop();
		
		System.out.println(sensor.getColor());
		System.out.println(sensor.getAmbient());
				       
	        waggleDance();
		myRobot.close();
	}
	
}
