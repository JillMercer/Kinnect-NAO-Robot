package edu.sru.thangiah.nao.demo;

import java.util.ArrayList;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTouch;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;
import edu.sru.thangiah.nao.demo.posturecustom.PostureCustom;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;
import edu.sru.thangiah.nao.demo.fistbump.FistBump;

/**
 * Author: Brady Rainey - 10/27/2016
 *
 * @author Example of having the NAO robot do a fist bump
 */

//public class is an extension of Demo
public class PostureBoard extends Demo {
	/**
	 * Constructor contains a previously initialized SynchronizedConnectDemo
	 * class.
	 *
	 * @param connection
	 * @throws Exception
	 */
	public PostureBoard( SynchronizedConnectDemo connection ) throws Exception {
		super( connection );
		// Demo Name must be Explicitly declared.
		demoName = "Posture Board";
		// Using an Applications Option Dialog to allow the user to select a
		// robot.
		ApplicationsOptionDialog dialog = new ApplicationsOptionDialog( connection );
		dialog.setVisible( true );
		// After the user selects a robot, the name must be retrieved from the
		// dialog, and the dialog is disposed.
		robotNames.add( dialog.getSelectedName() );
	}

	/**
	 * The run method is primarily used to add new DemoRobots to the robots
	 * ArrayList. The constructor is the robot name, the demo name, and the
	 * connection.
	 *
	 * It is a run method as it is a thread executed inside DemoRobot
	 *
	 * @throws Exception
	 */
	@Override
	protected void run() throws Exception {
		//calls the private class inside the current public class with the robot name
		//on which the program is to be executed, the demo name and and connection
		robots.add(new PostureBoardRobot ( robotNames.get( 0 ), demoName, connection ));
	}

	private class PostureBoardRobot extends DemoRobot {

		PostureCustom customPosture;
		ALMotion motion;

		public PostureBoardRobot( String name, String demoName, SynchronizedConnectDemo connect ) throws Exception {
			super( name, demoName, connect );
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void safeClose() throws Exception {
		}

		@Override
		protected void init() throws Exception {
			customPosture = new PostureCustom();
			customPosture.setDefault( motion );
		}

		@Override
		public void execute() throws Exception {
			// TODO Auto-generated method stub
		}

		@Override
		protected void frontTactil() {

		}

		@Override
		protected void rearTactil() {
			try {
				exit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 *
		 *
		 */
		@Override
		protected void handRightBack() {

		}

		@Override
		protected void handRightRight() {

		}
		@Override
		protected void handRightLeft() {

		}
	}
}

