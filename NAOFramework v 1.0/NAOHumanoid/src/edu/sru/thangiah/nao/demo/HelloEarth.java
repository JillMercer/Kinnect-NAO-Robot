package edu.sru.thangiah.nao.demo;

import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;

/**
 * Author: Sam Thangiah - 8/10/2016
 * 
 * @author Example of having the NAO robot utter "Hello Earthlings" 
 */

//public class is an extension of Demo
public class HelloEarth extends Demo {
	/**
	 * Constructor contains a previously initialized SynchronizedConnectDemo
	 * class.
	 * 
	 * @param connection
	 * @throws Exception
	 */
	public HelloEarth(SynchronizedConnectDemo connection) throws Exception {
		super(connection);
		// Demo Name must be Explicitly declared.
		demoName = "Hello Earthlings";
		// Using an Applications Option Dialog to allow the user to select a
		// robot.
		ApplicationsOptionDialog dialog = new ApplicationsOptionDialog(connection);
		dialog.setVisible(true);
		// After the user selects a robot, the name must be retrieved from the
		// dialog, and the dialog is disposed.
		robotNames.add(dialog.getSelectedName());
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
		robots.add(new HelloEarthlings(robotNames.get(0), demoName, connection));
	}

	//private class is an extension of DemoRobot
	private class HelloEarthlings extends DemoRobot {

		// Any utilized modules are declared here. They must be initialized in
		// the init() method.
		private ALTextToSpeech speech;

		/**
		 * Constructor automatically holds the robots name, the appname, and the
		 * connect class. The demo robot also subscribes to ALMemory, as well as
		 * the FrontTactil, RearTactil, and MiddleTactil. ALMemory is saved as
		 * the variable memory.
		 * 
		 * calls on the constructor for DemoRobot
		 * 
		 * @param name
		 * @param appName
		 * @param connect
		 * @throws Exception
		 */
		public HelloEarthlings(String name, String appName, SynchronizedConnectDemo connect) throws Exception {
			super(name, appName, connect);

		}

		/**
		 * Safe close is used to stop any current motion, put the robot in a
		 * safe position, unsubscribe to any events, and prepare the robot for
		 * another demo or shutdown. In this particular instance, there is no
		 * motion or subscriptions, so this method can be left blank.
		 */
		@Override
		protected void safeClose() throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		protected void init() throws Exception {
			speech = new ALTextToSpeech(connect.getSession(name));
		}

		/**
		 * Execute method. Can be used to execute all robots in synchronization.
		 */
		@Override
		public void execute() throws Exception {
			speech.say("Hello Earthlings!");
		}

		/**
		 * By default, the front tactil, rear tactil, and middle tactil are
		 * already subscribed to. These can be accessed by using the methods
		 * frontTactil() middleTactil() rearTactil()
		 */

		/**
		 * Using the front tactil to run the execute method.
		 */
		@Override
		public void frontTactil() {
			try {
				executeAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * Using the rear tactil to run the exit method.
		 */
		@Override
		public void rearTactil() {
			try {
				exit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

