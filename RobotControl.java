import ShefRobot.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class RobotControl extends JFrame implements Runnable, KeyListener, WindowListener, ActionListener {
	
	public static Robot myRobot = new Robot();
	
	
	
			
		
	public static Motor leftMotor = myRobot.getLargeMotor(Motor.Port.C);
	public static Motor rightMotor = myRobot.getLargeMotor(Motor.Port.A);

	public static ColorSensor sensor = myRobot.getColorSensor(Sensor.Port.S2);
	public static Motor actionMotor = myRobot.getMediumMotor(Motor.Port.B);
	
	
	
	public static enum State{BLUE, RED, WHITE, OFF};

    public static enum AreaEnum {BLACK_LINE, WHITE_AREA, BLUE_AREA, ERROR};
    public static AreaEnum areaCode;
    public static State floorLightState;
	
	
	
	//Defining the behaviour of the program
	enum Command {STOP, LEFT, RIGHT, FORWARD, REVERSE, GRAB, RELEASE, DANCE }; 
	private static final int DELAY_MS=50;
	private static final int DANCE_TIMES=10;
	
	private static final int SPEED=100;
	private static final int SLEEP=1000;
	
	// Make the window, text label and menu
	private static final int FRAME_WIDTH=400;
	private static final int FRAME_HEIGHT=200;
	
	
	private static JLabel label = new JLabel("Stop",JLabel.CENTER);
			
	public RobotControl() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Quit");
		JMenuItem menuItem = new JMenuItem("Really Quit?");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		this.add(label, BorderLayout.CENTER);
		label.setFont(new Font("SansSerif", Font.PLAIN, 48));
		this.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGHT);
		this.setTitle("Sheffield Robot Football Controller");
		this.addKeyListener(this);
		this.addWindowListener(this);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	// Start the program	
	private static Command command = Command.STOP;	
	//private Robot myRobot = new Robot();
	public static void main(String[] args) {
		Thread t = new Thread(new RobotControl());
	//	sensor.setFloorlightState(State.OFF);
		followTheBlackLine();
		t.start();
			
		

	}

	
	// Select the command corresponding to the key pressed	
	public void keyPressed(KeyEvent e) {
		switch ( e.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_UP:
				command = Command.FORWARD;
				break;
			case java.awt.event.KeyEvent.VK_DOWN:
				command = Command.REVERSE;
				break;
			case java.awt.event.KeyEvent.VK_LEFT:
				command = Command.LEFT;
				break;
			case java.awt.event.KeyEvent.VK_RIGHT:
				command = Command.RIGHT;
				break;
			case java.awt.event.KeyEvent.VK_W:		//press W to grab
				command = Command.GRAB;
				break;
			case java.awt.event.KeyEvent.VK_S:		//press S to release
				command = Command.RELEASE;
				break;
			case java.awt.event.KeyEvent.VK_COMMA: //press Comma "," to dance
				command = Command.DANCE;
				break;	
			default:
				command = Command.STOP;
				break;
		}
	}
    //and released
	public void keyReleased(KeyEvent e) {
		command = Command.STOP;
	}
	//ignore everything else
	public void keyTyped(KeyEvent e) {}	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}	
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	
	// handle the quit menu item	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Really Quit?")) {
			System.out.println("Closing Bluetooth");
			myRobot.close();
			System.exit(0);
		}
	}
	//and the window closing
	public void windowClosing(WindowEvent e) {
		System.out.println("Closing Bluetooth");
		myRobot.close();
	}	
	
	
	
	public static void waggleDance(Robot myRobot, Motor leftMotor, Motor rightMotor){ 
		label.setText("Waggle Dance");
		for (int t=1; t<10; t++){
			leftMotor.setSpeed(SPEED/3);
			rightMotor.setSpeed(SPEED/3);
			leftMotor.forward();
			rightMotor.backward();
			rightMotor.forward();
			leftMotor.backward();
		}
	   }
	   
	   /*
	   public void followBlackLine(Robot myRobot, Motor leftMotor, Motor rightMotor, ColorSensor sensor){
	 	 int counter=0;
			while(sensor.getColor().equals(ColorSensor.Color.BLACK)){
				leftMotor.setSpeed(200);//BLACK_LINE_SPEED);
                rightMotor.setSpeed(200);
				leftMotor.forward();
			    rightMotor.forward();
			}
			
			while ((sensor.getColor().equals(ColorSensor.Color.WHITE))){// && (sensor.getColor()!=ColorSensor.Color.BLUE) ) { //the second conditional might not be needed due to the while loop before
				counter++;
				if (counter%2==1){
					rightMotor.setSpeed(200);//BLACK_LINE_SPEED);
			    	rightMotor.forward();
			    	myRobot.sleep(500);
			    	System.out.println(sensor.getColor());
			    }
				else {
					leftMotor.setSpeed(200);//BLACK_LINE_SPEED);
			    	leftMotor.forward();
			    	myRobot.sleep(1000);
			    	System.out.println(sensor.getColor());
			    }
			}
			
	
					
			if (sensor.getColor().equals(ColorSensor.Color.BLUE)){
			 	leftMotor.stop();
			 	rightMotor.stop();
				}
				
	 }*/
	 
	 
	 public static AreaEnum mainArea(){

    	if(sensor.getColor().equals(ColorSensor.Color.BLACK)){
        	areaCode = AreaEnum.BLACK_LINE;
    	}
    	else if(sensor.getColor().equals(ColorSensor.Color.WHITE)){
        	areaCode = AreaEnum.WHITE_AREA;
    	}
    	else if(sensor.getColor().equals(ColorSensor.Color.BLUE)){
    		areaCode = AreaEnum.BLUE_AREA;
    	}
    	else{
            areaCode = AreaEnum.ERROR;
    	}
    	return areaCode;   
    }
	
    
    public static void findTheBlackLine(){
    	//int counter = 0;
    	
    	System.out.println("Finding");

        System.out.println("Trying to find a black line!");
    	
        /*
    	leftMotor.setSpeed(SPEED);
		rightMotor.setSpeed(SPEED);
		leftMotor.forward();
		rightMotor.forward();  */

        //if((System.currentTimeMillis() < 20) || (mainRobot.isMoving() != false)){
        
   //     if(mainArea() != AreaEnum.BLACK_LINE){
        
        while(mainArea() != AreaEnum.BLACK_LINE){

           leftMotor.setSpeed(SPEED);
		   rightMotor.setSpeed(SPEED);
		   
		   leftMotor.backward();
		   rightMotor.forward();
		   
		}
        
            /*if(mainArea() == AreaEnum.WHITE_AREA){
                leftMotor.setSpeed(SPEED);
		        rightMotor.setSpeed(SPEED);
		        leftMotor.backward();
		        rightMotor.forward();
            }
            /*else if(mainArea() == AreaEnum.BLUE_AREA){
                getIntoTheBlueArea();
            }*/
            
            	followTheBlackLine();
            
        }
        
        
        /*
        System.out.println("Finding");
        while (!(sensor.getColor().equals(ColorSensor.Color.BLACK))){// && (sensor.getColor()!=ColorSensor.Color.BLUE) ) { //the second conditional might not be needed due to the while loop before
				counter++;
				System.out.println(counter);
				if (counter%2==1){
					rightMotor.setSpeed(200);//BLACK_LINE_SPEED);
			    	rightMotor.forward();
			    	myRobot.sleep(500);
			    	System.out.println(sensor.getColor());
			    }
			    
				else {
					leftMotor.setSpeed(200);//BLACK_LINE_SPEED);
			    	leftMotor.forward();
			    	myRobot.sleep(1000);
			    	System.out.println(sensor.getColor());
			    }  
		}  */
			    
	    
	   
    
    
    //
    
 //   public void setFloodlightState(State floorLightState){
  //  	this.floorLightState = floorLightState;
   // }
    	
    public static void followTheBlackLine(){
   // 	sensor.setFloorlightState(ColorSensor.FloodlightState.OFF);

		System.out.println("Trying to follow a black line!");
	/*	/leftMotor.setSpeed(SPEED);
		rightMotor.setSpeed(SPEED);
		leftMotor.forward();
		rightMotor.forward();*/

		//if((System.currentTimeMillis() < 20) || (mainRobot.isMoving() != false)){

		while(mainArea() == AreaEnum.BLACK_LINE){
			System.out.println(sensor.getColor());
			leftMotor.setSpeed(SPEED);
			rightMotor.setSpeed(SPEED);
			leftMotor.forward();
		    rightMotor.forward();
		}

		//	if(mainArea() == AreaEnum.BLUE_AREA){
     //            getIntoTheBlueArea();
		//	}
		    if(mainArea() == AreaEnum.BLUE_AREA){
		    	System.out.println(sensor.getColor());
		    	leftMotor.stop();
			    rightMotor.stop();
			 //   this.run();
                 
			}
		//	else { //if(mainArea() == AreaEnum.BLUE_AREA){ 
				//myRobot.initialization();
				//System.out.println("The robot takes it for too long, check for it!");
		//		findTheBlackLine();
		//	}
        
        
  
	}
	
	
    public static void getIntoTheBlueArea(){

    	System.out.println("We have got into the Blue Area, we are looking for a ball now!");

        //myRobot.run();
        leftMotor.stop();
		rightMotor.stop();
   }
    

	//control the robot
	public void run() {	
		
		/*
		Robot myRobot=new Robot();
		
		Motor leftMotor = myRobot.getLargeMotor(Motor.Port.C);
		Motor rightMotor = myRobot.getLargeMotor(Motor.Port.A);

		ColorSensor sensor = myRobot.getColorSensor(Sensor.Port.S2);
		Motor actionMotor = myRobot.getMediumMotor(Motor.Port.B);
		*/
		
	
		
		while (true) {
			switch (command) {
				case STOP:
					label.setText("Stop");
					actionMotor.stop();
					leftMotor.stop();
					rightMotor.stop();
					break;					
				case FORWARD:
					label.setText("Forward");
					leftMotor.setSpeed(SPEED);
					rightMotor.setSpeed(SPEED);
					leftMotor.forward();
					rightMotor.forward();
					break;					
				case REVERSE:
					label.setText("Reverse");
					leftMotor.setSpeed(SPEED);
					rightMotor.setSpeed(SPEED);
					leftMotor.backward();
					rightMotor.backward();
					break;					
				case LEFT:
					label.setText("Left");
					leftMotor.setSpeed(SPEED);
					rightMotor.setSpeed(SPEED);
					leftMotor.backward();
					rightMotor.forward();
					break;
				case RIGHT:
					label.setText("Right");
					leftMotor.setSpeed(SPEED);
					rightMotor.setSpeed(SPEED);
					leftMotor.forward();
					rightMotor.backward();
					break;
				case GRAB:
					label.setText("Pick");
					actionMotor.setSpeed(SPEED);
					actionMotor.forward();
 					break;
				case RELEASE:
					label.setText("Release");
					actionMotor.setSpeed(SPEED);
					actionMotor.backward();				
 					break;
 				case DANCE:
					waggleDance(myRobot, leftMotor, rightMotor);;
 					break;
 		/*		case FOLLOW:
 					label.setText("Follow");
 					followBlackLine(myRobot, leftMotor, rightMotor, sensor);
 					break;*/
			}
			try {
				Thread.sleep(DELAY_MS);
			} catch (InterruptedException e) {};
		}	
	}
	
	

}
