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
		
		// Depending on the arm, these may be positive or negative. Negate as needed. 
		final int MAXSHOULDERROLLANGLE = 76;
		final int MAXSHOULDERPITCHANGLE = 119; // Negative and positive on both arms. 
		final int MAXELBOWANGLE = 88;
		
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
			// Set up
			float speed = 0.2f;
			motion.wakeUp();
			motion.setBreathEnabled("RArm", false);
			motion.setIdlePostureEnabled("RArm", false);
			motion.setBreathEnabled("LArm", false);
			motion.setIdlePostureEnabled("LArm", false);
			motion.setBreathEnabled("LLeg", false);
			motion.setIdlePostureEnabled("LLeg", false);
			motion.setBreathEnabled("RLeg", false);
			motion.setIdlePostureEnabled("RLeg", false);
			speech.say( "I'm ready." );
			posture.goToPosture( "Stand", 1.0f );
			int cycles = 400;
			// Runs for "cycles" cycles. 
			for ( int i = 0; i < cycles; i++ ) {
				
				// Left Shoulder Pitch 
				double leftShoulderPitch = Math.atan(( ViewerPanel3D.currentSkel.get3DJointY( Skeleton.ELBOW_LEFT ) - 
						ViewerPanel3D.currentSkel.get3DJointY( Skeleton.SHOULDER_LEFT )) / 
						( ViewerPanel3D.currentSkel.get3DJointX( Skeleton.ELBOW_LEFT ) - 
						ViewerPanel3D.currentSkel.get3DJointX( Skeleton.SHOULDER_LEFT )));
				motion.setAngles( "LShoulderPitch", leftShoulderPitch, speed );
				
				// Right Shoulder Pitch 
				double rightShoulderPitch = Math.atan(( ViewerPanel3D.currentSkel.get3DJointY( Skeleton.ELBOW_RIGHT ) - 
						ViewerPanel3D.currentSkel.get3DJointY( Skeleton.SHOULDER_RIGHT )) / 
						( ViewerPanel3D.currentSkel.get3DJointX( Skeleton.ELBOW_RIGHT ) - 
						ViewerPanel3D.currentSkel.get3DJointX( Skeleton.SHOULDER_RIGHT )));
				motion.setAngles( "RShoulderPitch", -rightShoulderPitch, speed );
				
				// Elbow Rolls
				double leftElbowRoll = getVectorAngle( ViewerPanel3D.currentSkel, Skeleton.SHOULDER_LEFT, Skeleton.ELBOW_LEFT, Skeleton.WRIST_LEFT );
				double rightElbowRoll = getVectorAngle( ViewerPanel3D.currentSkel, Skeleton.SHOULDER_RIGHT, Skeleton.ELBOW_RIGHT, Skeleton.WRIST_RIGHT );
				motion.setAngles( "LElbowRoll", leftElbowRoll - Math.toRadians( 180 ), speed );
				motion.setAngles( "RElbowRoll", -( rightElbowRoll - Math.toRadians( 180 )), speed );
				
				// Shoulder Rolls
				double leftShoulderRoll = getVectorAngle( ViewerPanel3D.currentSkel, Skeleton.ELBOW_LEFT, Skeleton.SHOULDER_LEFT, Skeleton.SPINE_SHOULDER );
				double rightShoulderRoll = getVectorAngle( ViewerPanel3D.currentSkel, Skeleton.ELBOW_RIGHT, Skeleton.SHOULDER_RIGHT, Skeleton.SPINE_SHOULDER );
				motion.setAngles( "LShoulderRoll", leftShoulderRoll - Math.toRadians( 70 ), speed );
				motion.setAngles( "RShoulderRoll", -( rightShoulderRoll - Math.toRadians( 70 )), speed ); //48
				
			}
			
			speech.say( "Done!" );
			posture.goToPosture( "Stand", 1.0f );
		}

		@Override
		protected void frontTactil() {
			try {
			// Activate Kinect Program
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
		
	}
	public double getVectorAngle( Skeleton current, int joint1, int joint2, int joint3 ) {
		double angle;
		 
		double aX = current.get3DJointX( joint1 );
		double aY = current.get3DJointY( joint1 );
		double aZ = current.get3DJointZ( joint1 );
		double bX = current.get3DJointX( joint2 );
		double bY = current.get3DJointY( joint2 );
		double bZ = current.get3DJointZ( joint2 );
		double cX = current.get3DJointX( joint3 );
		double cY = current.get3DJointY( joint3 );
		double cZ = current.get3DJointZ( joint3 );
		
		double baX = aX - bX;
		double baY = aY - bY;
		double baZ = aZ - bZ;
		
		double bcX = cX - bX;
		double bcY = cY - bY;
		double bcZ = cZ - bZ;
		
		// Calculate the lengths of the vectors.
		double mag1 = Math.sqrt(( baX * baX ) + ( baY * baY ) + ( baZ * baZ ));
		double mag2 = Math.sqrt(( bcX * bcX ) + ( bcY * bcY ) + ( bcZ * bcZ ));
		
		// Find the dot product.
		double dotProd = ( baX * bcX ) + ( baY * bcY ) + ( baZ * bcZ );
		
		angle = Math.acos( dotProd / ( mag1 * mag2 ));
		
		return angle;
	}
}
