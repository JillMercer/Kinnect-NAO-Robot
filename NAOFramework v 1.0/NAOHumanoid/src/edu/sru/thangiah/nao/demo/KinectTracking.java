/**
 * 
 */
package edu.sru.thangiah.nao.demo;

import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.fistbump.FistBump;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;

/**
 * @author Brady Rainey
 * @author Jill Mercer
 * @author Julia Walsh
 *
 */
public class KinectTracking extends Demo {
	
	public KinectTracking(SynchronizedConnectDemo connection) throws Exception {
		super(connection);
		// Demo Name must be Explicitly declared.
		demoName = "Kinect Tracking";
		// Using an Applications Option Dialog to allow the user to select a
		// robot.
		ApplicationsOptionDialog dialog = new ApplicationsOptionDialog(connection);
		dialog.setVisible(true);
		// After the user selects a robot, the name must be retrieved from the
		// dialog, and the dialog is disposed.
		robotNames.add(dialog.getSelectedName());
	}
	
	@Override
	protected void run() throws Exception {
		robots.add(new KinectRobot(robotNames.get(0), demoName, connection));
	}
	
	private class KinectRobot extends DemoRobot {

		FistBump fistBump;
		
		public KinectRobot(String name, String demoName, SynchronizedConnectDemo connect) throws Exception {
			super(name, demoName, connect);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void safeClose() throws Exception {
		}

		@Override
		protected void init() throws Exception {	
			fistBump = new FistBump(name, connect);
		}

		@Override
		public void execute() throws Exception {
			// TODO Auto-generated method stub
		}

		@Override
		protected void frontTactil() {
			try {
				fistBump.raiseArmforBump();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		 * If any of the sensors on the right hand of the robot is
		 * triggered, say nice and lower the right arm
		 */
		@Override
		protected void handRightBack() {
			fistBump.handBumped();
		}
		
		@Override
		protected void handRightRight() {
			fistBump.handBumped();
		}
		@Override
		protected void handRightLeft() {
			fistBump.handBumped();
		}

	}
}
