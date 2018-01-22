/**
* Sheffield University Robot Football Controller
* Guy Brown September 2008, amended Siobhan North 2014
* Rewritten for EV3 robots SDN 2016
* 
* SCROLL DOWN TO THE BOTTOM OF THE PROGRAM
* YOU DO NOT NEED TO UNDERSTAND (OR EDIT) ANYTHING APART FROM THE
* MARKED SECTIONS OF THE PROGRAM
*
* @author Suen Tsz Ching
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


    public static Robot myRobot = new Robot();
	
    //public static ColorSensor colorSensor = myRobot.getColorSensor(Sensor.Port.S2);
    //public static UltrasonicSensor ultrasonicSensor = myRobot.getUltrasonicSensor(Sensor.Port.S1);

    public static Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
    public static Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
    public static Motor mediumMotor = myRobot.getMediumMotor(Motor.Port.A);
    public static Speaker speaker = myRobot.getSpeaker();

    static final int HIGH_POWER = 200;
    static final int LOW_POWER = 100; 
    static final int NONE_SPEED = 0;
    static final int MOVING_SPEED = 600;
    static final int TURNING_SPEED = 300;
    static final int LOW_SPEED = 200;
    static final int MOVING_TIME = 1000;
    static final int TURNING_TIME = 600;
    //static final int ROTATIONS = 90;

	
    //Defining the behaviour of the prgram
    enum Command {STOP, LEFT, RIGHT, FORWARD, REVERSE, KICK };
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
    private Robot myRobot = new Robot();
	
    public static void main(String[] args) {
	Thread t = new Thread(new EV3football());
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
		case java.awt.event.KeyEvent.VK_SPACE:
			command = Command.KICK;
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
     * @author Suen Tsz Ching
    */
    public static void stopMoving(){
    	leftMotor.setSpeed(NONE_SPEED);
    	rightMotor.setSpeed(NONE_SPEED);
    	leftMotor.stop();
    	rightMotor.stop();
    }

    public static void forwardMoving(){
    	speaker.playTone(MOVING_TIME, MOVING_SPEED);

    	leftMotor.setPower(HIGH_POWER);
    	rightMotor.setPower(HIGH_POWER);
    	leftMotor.setSpeed(MOVING_SPEED);
    	rightMotor.setSpeed(MOVING_SPEED);
    	leftMotor.forward();
    	rightMotor.forward();
    	leftMotor.flt(true);
    	rightMotor.flt(true);
    }

    public static void reverseMoving(){
		speaker.playTone(MOVING_TIME, MOVING_SPEED);

    	leftMotor.setPower(HIGH_POWER);
    	rightMotor.setPower(HIGH_POWER);		
    	leftMotor.setSpeed(MOVING_SPEED);
    	rightMotor.setSpeed(MOVING_SPEED);
    	leftMotor.backward();
    	rightMotor.backward();
    	leftMotor.flt(true);
    	rightMotor.flt(true);
    }

    public static void turnLeft(){
    	speaker.playTone(TURNING_TIME, TURNING_SPEED);

    	leftMotor.setPower(LOW_POWER);
    	rightMotor.setPower(LOW_POWER);	
   		leftMotor.setSpeed(NONE_SPEED);
   	    rightMotor.setSpeed(TURNING_SPEED);
   		leftMotor.backward();
   		rightMotor.forward();
   		leftMotor.flt(true);
    	rightMotor.flt(true);

   		myRobot.sleep(TURNING_TIME);
   	    rightMotor.stop();
    }

    public static void turnRight(){
		speaker.playTone(TURNING_TIME, TURNING_SPEED);

    	leftMotor.setPower(LOW_POWER);
    	rightMotor.setPower(LOW_POWER);		
    	leftMotor.setSpeed(TURNING_SPEED);
    	rightMotor.setSpeed(NONE_SPEED);
    	leftMotor.forward();
    	rightMotor.backward();
    	leftMotor.flt(true);
    	rightMotor.flt(true);
 
    	myRobot.sleep(TURNING_TIME);
    	leftMotor.stop();
    }

    public static void kickBall(int rotationDegrees){
        speaker.playTone(TURNING_TIME, TURNING_SPEED);
        leftMotor.setPower(HIGH_POWER);
    	rightMotor.setPower(HIGH_POWER);
    	mediumMotor.setPower(HIGH_POWER);

        mediumMotor.setSpeed(TURNING_SPEED);
        //mediumMotor.rotateTo(0);

        if(rotationDegrees <= 180 && rotationDegrees > 0){
        	mediumMotor.rotate((180+rotationDegrees), true);
        	myRobot.sleep(TURNING_TIME);
        	System.out.println(mediumMotor.getTachoCount());
        }
        else if(rotationDegrees > 180 && rotationDegrees <= 360){
        	mediumMotor.rotate((180-rotationDegrees), true);
        	myRobot.sleep(TURNING_TIME);
        	System.out.println(mediumMotor.getTachoCount());
        }
        else{
            System.out.println("The rotation degree is out of required range!");
            mediumMotor.rotateTo(0);
            mediumMotor.resetTachoCount();
            //initialization();
        }
        leftMotor.flt(true);
    	rightMotor.flt(true);
    }

    public static void initialization(){
        Delay.msDelay(3000);
    	myRobot.sleep(MOVING_TIME);

    	leftMotor.setPower(LOW_POWER);
    	rightMotor.setPower(LOW_POWER);
        mediumMotor.setPower(LOW_POWER);

    	leftMotor.setSpeed(LOW_SPEED);
    	rightMotor.setSpeed(LOW_SPEED);
    	mediumMotor.setSpeed(LOW_SPEED);

    	System.out.println("There are some errors!");
    }


    // @author Suen Tsz Ching
    public void run() {
	// This defines and names the two large motors that turn the wheels
	// Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
	// Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);

	// Motor mediumMotor = myRobot.getMediumMotor(Motor.Port.A);
        // Speaker speaker = myRobot.getSpeaker();

		
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
				case KICK:
					label.setText("Kick");
					System.out.println("Loading the Motor...");
    				kickBall();

 					break;
			}
			try {
				Thread.sleep(DELAY_MS);
			} catch (InterruptedException e) {};
		}	
	}


}
