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
import java.util.LinkedList;
import java.util.Queue;

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
import edu.ufl.digitalworlds.j4k.Skeleton;
import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.fistbump.FistBump;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;
import edu.sru.thangiah.nao.kinectviewerapp.KinectViewerApp;
import edu.sru.thangiah.nao.kinectviewerapp.KinectViewerApplet;
import edu.sru.thangiah.nao.kinectviewerapp.ViewerPanel3D;

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
			try {
				
				KinectViewerApp.createMainFrame( "Kinect Viewer Applet" );
		    	KinectViewerApp.app = new KinectViewerApp();
		    	KinectViewerApp.setFrameSize( 730,570,null );
				
				this.memory = new ALMemory( connect.getSession( name ));
				this.touch = new ALTouch( connect.getSession( name ));
				this.motion = new ALMotion( connect.getSession( name ));
				this.posture = new ALRobotPosture( connect.getSession( name ));
				this.speech = new ALTextToSpeech( connect.getSession( name ));
//				Set up robot for beginning:
				float speed = 0.3f;
//				String jointName = "LShoulderPitch";
				// Give power to motors and assume default position:
				motion.wakeUp();
				posture.goToPosture("Stand", 1.0f);
				motion.setBreathEnabled("RArm", false);
				motion.setIdlePostureEnabled("RArm", false);
				motion.setBreathEnabled("LArm", false);
				motion.setIdlePostureEnabled("LArm", false);
				speech.say( "Beginning mirroring." );
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}

		@Override
		public void execute() throws Exception {
			
//			speech.setVoice(  );
			speech.say( "Inside execute." );
			float speed = 0.3f;
			String jointName = "RShoulderRoll";
			
			int frames = 1;
			int count = 0;
			double angleChange;
			
			motion.setAngles( "RShoulderPitch", Math.toRadians( 0 ), speed );
			motion.setAngles( "RShoulderRoll", Math.toRadians( -76 ), speed );
			//---
			while ( count < 500 ) {
			    for(int i = 0; i < ViewerPanel3D.skeletons.length; i++ )
			    	if( ViewerPanel3D.skeletons[i] != null ) 
			    	{
			    		if( ViewerPanel3D.skeletons[i].getTimesDrawn() <= 10 && ViewerPanel3D.skeletons[i].isTracked())
			    		{
			    			ViewerPanel3D.currentSkel = ViewerPanel3D.skeletons[i]; 
				    		//skeletons[i].draw(gl);
			    			ViewerPanel3D.skeletons[i].increaseTimesDrawn();
			    			if ( ViewerPanel3D.queue.size() < frames )
			    				ViewerPanel3D.queue.add( ViewerPanel3D.skeletons[i] );
				    		if(count % 10 == 0 ) // slows down time drawn
				    		{
				    		//Accesses the individual joints and gets the XYZ coordinates and writes them to a file for the time being
				    		//There are 25 different joints in the skeleton class to access this is listed on j4k.com (skeleton class)
				    		//checks if the queue is less than number of frames wanted only has 2 frames at time
		    				angleChange = getAngle( ViewerPanel3D.queue.element(), ViewerPanel3D.currentSkel, Skeleton.HAND_RIGHT );
		    				System.out.println( angleChange );
		    				System.out.println( count );
		    				motion.angleInterpolation( "RElbowRoll", angleChange, 1, false );
		    				ViewerPanel3D.queue.remove();
		    				ViewerPanel3D.queue.add( ViewerPanel3D.currentSkel );
				    		}
				    		count++;
				    	}
				    	//count++;
			    	}
			}
			//---
			
			Thread.sleep( 1000 );
			// When done, go back to standing:
			posture.goToPosture("Stand", 1.0f);
		}

		@Override
		protected void frontTactil() {
			try {
			// TODO Activate Kinect Program
				execute();
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
	double getAngle( Skeleton skel1, Skeleton skel2, int movJoint )
	{ // this gets the two joints and finds the angle between them
		
		double xA = skel1.get3DJointX( movJoint );
		double yA = skel1.get3DJointY( movJoint );
		double zA = skel1.get3DJointZ( movJoint );
		double xB = skel1.get3DJointX( Skeleton.NECK );
		double yB = skel1.get3DJointY( Skeleton.NECK );
		double zB = skel1.get3DJointZ( Skeleton.NECK );
		double xC = skel2.get3DJointX( movJoint );
		double yC = skel2.get3DJointX( movJoint );
		double zC = skel2.get3DJointX( movJoint );
		
		double vx1 = xA - xB;
		double vy1 = yA - yB;
		double vz1 = zA - zB;
		
		double vx2 = xC - xB;
		double vy2 = yC - yB;
		double vz2 = zC - zB;
		
		double v1Mag = Math.sqrt( vx1 * vx1 + vy1 * vy1 + vz1 * vz1 );
		double v2Mag = Math.sqrt( vx2 * vx2 + vy2 * vy2 + vz2 * vz2 );
		
		double vx1Norm = vx1 / v1Mag;
		double vy1Norm = vy1 / v1Mag;
		double vz1Norm = vz1 / v1Mag;
		
		double vx2Norm = vx2 / v2Mag;
		double vy2Norm = vy2 / v2Mag;
		double vz2Norm = vz2 / v2Mag;
		
		double dotProd = vx1Norm * vx2Norm + vy1Norm * vy2Norm + vz1Norm * vz2Norm;
		
		return Math.toRadians( Math.acos( dotProd ));
	}
}
