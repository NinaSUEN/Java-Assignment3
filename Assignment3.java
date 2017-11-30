/*
Written by: Bryn Berkeley, Panagiotis Antoniou, Tsz Ching Suen
*/
import ShefRobot.*;

public class Assignment3{

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
		myRobot.close();
	}
	
}
