/**
* Sheffield University Robot Football Controller
* Guy Brown September 2008, amended Siobhan North 2014
* Rewritten for EV3 robots SDN 2016
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

public class EV3football extends JFrame implements Runnable, KeyListener, WindowListener, ActionListener {
	
	//create an object of Robot type
	public static Robot myRobot = new Robot();
	
	//create some motor objects of Motor type, Sensor type and Speaker type 
    	public static ColorSensor colorSensor = myRobot.getColorSensor(Sensor.Port.S2);
    	public static Motor leftMotor = myRobot.getLargeMotor(Motor.Port.C);
    	public static Motor rightMotor = myRobot.getLargeMotor(Motor.Port.A);
    	public static Motor mediumMotor = myRobot.getMediumMotor(Motor.Port.B);
    	public static Speaker speaker = myRobot.getSpeaker();
	
	//static constants to be used 
    	public static final int NONE_SPEED = 0;
    	public static final int MOVING_SPEED = 200;
    	public static final int TURNING_SPEED = 60;
    	public static final int LOW_SPEED = 50;
    	public static final int MOVING_TIME = 1000;
    	public static final int TURNING_TIME = 600;
    	public static final int DANCE_TIMES = 10;
    	public static final int SLEEP_TIME = 500;


    	//Defining the behaviour of the program
	enum Command {STOP, LEFT, RIGHT, FORWARD, REVERSE, RAISE, LOWER, DANCE};
	private static final int DELAY_MS = 50;
	
	// Make the window, text label and menu
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 200;
	
	private JLabel label = new JLabel("Stop",JLabel.CENTER);
			
	public EV3football() {
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
			case java.awt.event.KeyEvent.VK_W:
				command = Command.RAISE;
				break;
			case java.awt.event.KeyEvent.VK_S:  
				command = Command.LOWER;
				break;	
			case java.awt.event.KeyEvent.VK_SPACE:  
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
	
	//handle the quit menu item	
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

    	/**
    	*Stops both wheels, the robot will stop moving when this method was called. 
	*@author Bryn Berkeley 
    	*/
    	public static void stopMoving(){
    		leftMotor.stop();
    		rightMotor.stop();
    		mediumMotor.stop();
   	}

   	/**
    	* Calls the robot to move forward with moving speed
	* @author Suen Tsz Ching
    	*/	 
    	public static void forwardMoving(){
    		leftMotor.setSpeed(MOVING_SPEED);
    		rightMotor.setSpeed(MOVING_SPEED);
    		leftMotor.forward();
    		rightMotor.forward();
    	}
 

   	/**
	* Calls the robot to move reverse with moving speed
    	* @author Panagiotis Antoniou
    	*/
    	public static void reverseMoving(){
    		leftMotor.setSpeed(MOVING_SPEED);
    		rightMotor.setSpeed(MOVING_SPEED);
    		leftMotor.backward();
    		rightMotor.backward();
   	}

   	/**
	* Calls the robot to turn left with turning speed
    	* @author Bryn Berkeley
    	*/
    	public static void turnLeft(){
   	    	leftMotor.setSpeed(TURNING_SPEED);
   	    	rightMotor.setSpeed(TURNING_SPEED);
   	    	leftMotor.backward();
   	    	rightMotor.forward();
    	}

   	/**
	* Calls the robot to turn right with turning speed
    	* @author Bryn Berkeley 
    	*/
    	public static void turnRight(){
    		leftMotor.setSpeed(TURNING_SPEED);
    		rightMotor.setSpeed(TURNING_SPEED);
    		leftMotor.forward();
    		rightMotor.backward();
   	}


   	/**
	* Calls the medium motor to raise up the trap to make the robot release the ball.
    	* @author Panagiotis Antoniou
    	*/
    	public static void raise(){
        	mediumMotor.setSpeed(MOVING_SPEED);
        	mediumMotor.forward();
    	}


       	/**
	* Calls the medium motor to lower down the trap to trap the ball.
    	* @author Suen Tsz Ching
    	*/
    	public static void lower(){
        	mediumMotor.setSpeed(MOVING_SPEED);
        	mediumMotor.backward();
    	}
    	
	/**
	* Makes the robot dance 
    	* @author Panagiotis Antoniou
    	*/
   	public static void waggleDance(){
		for (int t=0; t<DANCE_TIMES; t++){
			leftMotor.setSpeed(MOVING_SPEED);
			rightMotor.setSpeed(MOVING_SPEED);
			leftMotor.forward();
			rightMotor.backward();
			myRobot.sleep(SLEEP_TIME*2);
			rightMotor.forward();
			leftMotor.backward();
			myRobot.sleep(SLEEP_TIME*2);
		}
	}
	
	/**
	* Makes the robot dance 
    	* @author Bryn Berkeley, Suen Tsz Ching, Panagiotis Antoniou, Tiange Xiang
    	*/
	public void run() {
        	while (true) {
			switch (command) {
				case STOP:
					label.setText("Stop");
					stopMoving();					
					break;					
				case FORWARD:
					label.setText("Forward");				
					forwardMoving();					
					break;					
				case REVERSE:
					label.setText("Reverse");
					reverseMoving();
					break;					
				case LEFT:
					label.setText("Left");
  					turnLeft();
					break;
				case RIGHT:
					label.setText("Right");
    					turnRight();
					break;
				case RAISE:
					label.setText("Raise");
    					raise();
 					break;
 				case LOWER:
 				    	label.setText("Lower");
                    		    	lower();
 				    	break;
 				case DANCE:
 				    	label.setText("Dance");
                    		    	waggleDance();
 				    	break;
			}
			try {
				Thread.sleep(DELAY_MS);
			} catch (InterruptedException e) {};
		}	
	}

}
