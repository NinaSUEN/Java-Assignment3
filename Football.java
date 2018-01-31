/**
* Sheffield University Robot Football Controller
* Guy Brown September 2008, amended Siobhan North 2014
* Rewritten for EV3 robots SDN 2016
* 
* SCROLL DOWN TO THE BOTTOM OF THE PROGRAM
* YOU DO NOT NEED TO UNDERSTAND (OR EDIT) ANYTHING APART FROM THE
* MARKED SECTIONS OF THE PROGRAM
*/

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

public class Football extends JFrame implements Runnable, KeyListener, WindowListener, ActionListener {
	

    public static Robot myRobot = new Robot();
	
    public static ColorSensor colorSensor = myRobot.getColorSensor(Sensor.Port.S2);
    public static UltrasonicSensor ultrasonicSensor = myRobot.getUltrasonicSensor(Sensor.Port.S1);

    public static Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
    public static Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
    public static Motor mediumMotor = myRobot.getMediumMotor(Motor.Port.A);
    public static Speaker speaker = myRobot.getSpeaker();

    public static final int NONE_SPEED = 0;
    public static final int MOVING_SPEED = 300;
    public static final int TURNING_SPEED = 200;
    public static final int LOW_SPEED = 50;
    public static final int MOVING_TIME = 1000;
    public static final int TURNING_TIME = 600;

    public static float currentDistance = ultrasonicSensor.getDistance();

  
	//Defining the behaviour of the prgram
	enum Command {STOP, LEFT, RIGHT, FORWARD, REVERSE, PUT, GET};
	private static final int DELAY_MS = 50;
	
	// Make the window, text label and menu
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 200;
	
	private JLabel label = new JLabel("Stop",JLabel.CENTER);
			
	public Football() {
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
	private Command command = Command.STOP;	
	//private Robot myRobot = new Robot();	

	public static void main(String[] args) {
		Thread t = new Thread(new Football());
		t.start();

        leftMotor.run(); 
        rightMotor.run();
	mediumMotor.run();
	
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
			case java.awt.event.KeyEvent.VK_SPACE:
				command = Command.GET;
				break;
			case java.awt.event.KeyEvent.VK_C:  //press C "c" to dance
				command = Command.PUT;
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


	/*
	 * THIS IS THE ONLY PART OF THE PROGRAM THAT YOU NEED TO EDIT
	 */


    /*
    * @author Suen Tsz Ching
    * Stops both wheels, the robot will stop moving when this method was called.
    */
    public static void stopMoving(){
//     	leftMotor.setSpeed(NONE_SPEED);
//     	rightMotor.setSpeed(NONE_SPEED);
    	leftMotor.stop();
    	rightMotor.stop();
    }

   /*
    * @author Suen Tsz Ching
    * Calls the robot to move forward with moving speed
    */	 
    public static void forwardMoving(){
//     	speaker.playTone(MOVING_TIME, MOVING_SPEED);

    	leftMotor.setSpeed(MOVING_SPEED);
    	rightMotor.setSpeed(MOVING_SPEED);
    	leftMotor.forward();
    	rightMotor.forward();
//     	leftMotor.stop();
//    	rightMotor.stop();
    }
 

   /*
    * @author Suen Tsz Ching
    * Calls the robot to move reverse with moving speed
    */
    public static void reverseMoving(){
// 	speaker.playTone(MOVING_TIME, MOVING_SPEED);
	
    	leftMotor.setSpeed(MOVING_SPEED);
    	rightMotor.setSpeed(MOVING_SPEED);
    	leftMotor.backward();
    	rightMotor.backward();
//     	leftMotor.stop();
//    	rightMotor.stop();
    }

   /*
    * @author Suen Tsz Ching
    * Calls the robot to turn left with turning speed
    */
    public static void turnLeft(){
//     	speaker.playTone(TURNING_TIME, TURNING_SPEED);

   	leftMotor.setSpeed(NONE_SPEED);
   	rightMotor.setSpeed(TURNING_SPEED);
   	leftMotor.backward();
   	rightMotor.forward();

//    	leftMotor.stop();
//      rightMotor.stop();
    }

   /*
    * @author Suen Tsz Ching
    * Calls the robot to turn right with turning speed
    */
    public static void turnRight(){
// 	speaker.playTone(TURNING_TIME, TURNING_SPEED);
		
    	leftMotor.setSpeed(TURNING_SPEED);
    	rightMotor.setSpeed(NONE_SPEED);
    	leftMotor.forward();
    	rightMotor.backward();

//     	leftMotor.stop();
//    	rightMotor.stop();
    }


   /*
    * @author Suen Tsz Ching
    * @param integer ratation degree 
    * Calls the medium motor to move if the ratation degree matched the condition, makes the robot to grab a ball.
    */
    public static void getBall(){
        //speaker.playTone(TURNING_TIME, TURNING_SPEED);

        // if(rotationDegrees <= 180 && rotationDegrees > 0){
        // 	mediumMotor.rotate((180+rotationDegrees), true);
        // 	myRobot.sleep(TURNING_TIME);
        // 	System.out.println(mediumMotor.getTachoCount());
        // }
        // else if(rotationDegrees > 180 && rotationDegrees <= 360){
        // 	mediumMotor.rotate((180-rotationDegrees), true);
        // 	myRobot.sleep(TURNING_TIME);
        // 	System.out.println(mediumMotor.getTachoCount());
        // }
        // else{
        //     System.out.println("The rotation degree is out of required range!");
        //     mediumMotor.rotateTo(0);
        //     mediumMotor.resetTachoCount();
        //     initialization();
        // }
	    
	mediumMotor.setSpeed(LOW_SPEED);
	mediumMotor.forward();
    }


     /*
    * @author Suen Tsz Ching
    * @param integer ratation degree 
    * Calls the medium motor to move if the ratation degree matched the condition, makes the robot to grab a ball.
    */
    public static void putBall(){
        //speaker.playTone(TURNING_TIME, TURNING_SPEED);

        // if(rotationDegrees <= 180 && rotationDegrees > 0){
        // 	mediumMotor.rotate((180+rotationDegrees), true);
        // 	myRobot.sleep(TURNING_TIME);
        // 	System.out.println(mediumMotor.getTachoCount());
        // }
        // else if(rotationDegrees > 180 && rotationDegrees <= 360){
        // 	mediumMotor.rotate((180-rotationDegrees), true);
        // 	myRobot.sleep(TURNING_TIME);
        // 	System.out.println(mediumMotor.getTachoCount());
        // }
        // else{
        //     System.out.println("The rotation degree is out of required range!");
        //     mediumMotor.rotateTo(0);
        //     mediumMotor.resetTachoCount();
        //     initialization();
        // }
	    
	mediumMotor.setSpeed(LOW_SPEED);
	mediumMotor.backward();
    }

	// Control the robot
	public void run() {

       //put your code to define other things here

        while (true) {
			switch (command) {
				case STOP:
					label.setText("Stop");
					System.out.println("Moving Forward...");
					stopMoving();					
					break;					
				case FORWARD:
					label.setText("Forward");				
					System.out.println("Moving Forward...");
					forwardMoving();					
					break;					
				case REVERSE:
					label.setText("Reverse");
					System.out.println("Moving Reverse...");
					reverseMoving();
					break;					
				case LEFT:
					label.setText("Left");
					System.out.println("Turning around...");
  					turnLeft();
  					System.out.println("Turning to LEFT!");
					break;
				case RIGHT:
					label.setText("Right");
					System.out.println("Turning around...");
    				turnRight();
    				        System.out.println("Turning to RIGHT!");
					break;
				case GET:
					label.setText("Get");
					System.out.println("Loading the Motor...");
    				        getBall();
 					break;
 				case PUT:
 				        label.setText("Put");
 				        System.out.println("The robot is going to dance now!");
                                        putBall();
 				        break;
			}
			try {
				Thread.sleep(DELAY_MS);
			} catch (InterruptedException e) {};
		}	
	}

}
