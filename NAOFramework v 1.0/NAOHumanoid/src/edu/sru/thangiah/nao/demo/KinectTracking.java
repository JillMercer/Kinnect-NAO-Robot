/**
 * 
 */
package edu.sru.thangiah.nao.demo;

import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.ufl.digitalworlds.j4k.Skeleton;
import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;
import edu.sru.thangiah.nao.kinectviewerapp.KinectViewerApp;
import edu.sru.thangiah.nao.kinectviewerapp.ViewerPanel3D;

/**
 * @author Brady Rainey
 * @author Jill Mercer
 * @author Julia Walsh
 *
 */
public class KinectTracking extends Demo {
	
	public KinectTracking( SynchronizedConnectDemo connection ) throws Exception {
		super( connection );
		// Demo Name must be Explicitly declared.
		demoName = "Kinect Tracking";
		// Using an Applications Option Dialog to allow the user to select a
		// robot.
		ApplicationsOptionDialog dialog = new ApplicationsOptionDialog( connection );
		dialog.setVisible( true );
		// After the user selects a robot, the name must be retrieved from the
		// dialog, and the dialog is disposed.
		robotNames.add( dialog.getSelectedName());
	}
	
	@Override
	protected void run() throws Exception {
		robots.add( new KinectRobot( robotNames.get( 0 ), demoName, connection ));
	}
	
	private class KinectRobot extends DemoRobot {
		
		ALTextToSpeech speech;
		ALMotion motion;
		ALRobotPosture posture;

		public KinectRobot( String name, String demoName, SynchronizedConnectDemo connect ) throws Exception {
			super( name, demoName, connect );
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
		    	KinectViewerApp.setFrameSize( 730, 570, null );
		    	
				this.posture = new ALRobotPosture( connect.getSession( name ));
				this.speech = new ALTextToSpeech( connect.getSession( name ));
				this.motion = new ALMotion( connect.getSession( name ));

				
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}

		@Override
		public void execute() throws Exception {
			
			float speed = 0.5f;
			motion.wakeUp();
			motion.setBreathEnabled("RArm", false);
			motion.setIdlePostureEnabled("RArm", false);
			motion.setBreathEnabled("LArm", false);
			motion.setIdlePostureEnabled("LArm", false);
			speech.say( "I'm ready." );
			posture.goToPosture( "Stand", 1.0f );
			for ( int i = 0; i < 500; i++ ) {
				double leftShoulderPitch = Math.atan(( ViewerPanel3D.currentSkel.get3DJointY( Skeleton.ELBOW_LEFT ) - 
						ViewerPanel3D.currentSkel.get3DJointY( Skeleton.SHOULDER_LEFT )) / 
						( ViewerPanel3D.currentSkel.get3DJointX( Skeleton.ELBOW_LEFT ) - 
						ViewerPanel3D.currentSkel.get3DJointX( Skeleton.SHOULDER_LEFT )));
				motion.setAngles( "LShoulderPitch", leftShoulderPitch, speed );
				
				double rightShoulderPitch = Math.atan(( ViewerPanel3D.currentSkel.get3DJointY( Skeleton.ELBOW_RIGHT ) - 
						ViewerPanel3D.currentSkel.get3DJointY( Skeleton.SHOULDER_RIGHT )) / 
						( ViewerPanel3D.currentSkel.get3DJointX( Skeleton.ELBOW_RIGHT ) - 
						ViewerPanel3D.currentSkel.get3DJointX( Skeleton.SHOULDER_RIGHT )));
				motion.setAngles( "RShoulderPitch", -rightShoulderPitch, speed );
				
				// double right
				// double left
			}
			
			speech.say( "Done!" );
			posture.goToPosture( "Stand", 1.0f );
		}

		@Override
		protected void frontTactil() {
			try {
			// TODO Activate Kinect Program
				execute();
			} catch ( Exception e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void rearTactil() {
			try {
				exit();
			} catch ( Exception e ) {
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
