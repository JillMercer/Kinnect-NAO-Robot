/**
 * 
 */
package edu.sru.thangiah.nao.demo;

import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTouch;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.ufl.digitalworlds.gui.DWApp;
import edu.ufl.digitalworlds.j4k.J4K1;
import edu.ufl.digitalworlds.j4k.J4K2;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.fistbump.FistBump;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;
import edu.sru.thangiah.nao.kinectviewerapp.KinectViewerApp;
import edu.sru.thangiah.nao.kinectviewerapp.KinectViewerApplet;

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
		KinectViewerApplet kinect_window = new KinectViewerApplet();
		kinect_window.init();
		
	}
	
	private class KinectRobot extends DemoRobot {
		
		ALTextToSpeech speech;
		ALMemory memory;
		ALTouch touch;
		ALMotion motion;
		ALRobotPosture posture;

		public KinectRobot(String name, String demoName, SynchronizedConnectDemo connect) throws Exception {
			super(name, demoName, connect);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void safeClose() throws Exception {
		}

		@Override
		protected void init() throws Exception {	
			
		}

		@Override
		public void execute() throws Exception {
			// TODO Auto-generated method stub
			speech.say( "Beginning mirroring." );
			// Give power to motors:
			motion.wakeUp();
			// Close hands:
			motion.closeHand("RHand");
			motion.closeHand("LHand");
			motion.setAngles("RShoulderPitch", 0.0f, 0.5f);
			Thread.sleep(100);
			motion.setAngles("RShoulderPitch", 3.14f, 0.5f);
		}

		@Override
		protected void frontTactil() {
			try {
			// TODO Activate Kinect Program
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
