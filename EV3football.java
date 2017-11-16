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

public class EV3football extends JFrame implements Runnable, KeyListener, WindowListener, ActionListener {
	
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
	 */

	public void run() {
		//This defines and names the two large motors that turn the wheels
		Motor leftMotor = myRobot.getLargeMotor(Motor.Port.B);
		Motor rightMotor = myRobot.getLargeMotor(Motor.Port.C);
		
       //put your code to define other things here

		while (true) {
			switch (command) {
				case STOP:
					label.setText("Stop");
					
					// put your code for stopping here
					
					break;					
				case FORWARD:
					label.setText("Forward");
					
					// put your code for going forwards here
					
					break;					
				case REVERSE:
					label.setText("Reverse");
					
					// put your code for going backwards here
					
					break;					
				case LEFT:
					label.setText("Left");
					
  					// put your code for turning left here

					break;
				case RIGHT:
					label.setText("Right");
					
    				// put your code for turning right here

					break;
				case KICK:
					label.setText("Kick");
					
    				// put your code for kicking here

 					break;
			}
			try {
				Thread.sleep(DELAY_MS);
			} catch (InterruptedException e) {};
		}	
	}

}