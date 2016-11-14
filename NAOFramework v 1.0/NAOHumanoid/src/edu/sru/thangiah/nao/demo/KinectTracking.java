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
		Skeleton lastSkeleton;

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
			
			float speed = 0.2f;
			motion.wakeUp();
			motion.setBreathEnabled("RArm", false);
			motion.setIdlePostureEnabled("RArm", false);
			motion.setBreathEnabled("LArm", false);
			motion.setIdlePostureEnabled("LArm", false);
			speech.say( "I'm ready." );
			posture.goToPosture( "Stand", 1.0f );
			lastSkeleton = ViewerPanel3D.currentSkel; 
			for ( int i = 0; i < 400; i++ ) {
				
				// Fake Movements Begin
				motion.setAngles( "LShoulderRoll", 0, speed );
				motion.setAngles( "RShoulderRoll", 0, speed );
				// Fake Movements End
				
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
				
// WORKING CODE ABOVE THIS LINE
// TEST CODE BELOW THIS LINE
				
				double leftElbowRoll = getVectorAngle( ViewerPanel3D.currentSkel, Skeleton.SHOULDER_LEFT, Skeleton.ELBOW_LEFT, Skeleton.WRIST_LEFT );
				
				double rightElbowRoll = getVectorAngle( ViewerPanel3D.currentSkel, Skeleton.SHOULDER_RIGHT, Skeleton.ELBOW_RIGHT, Skeleton.WRIST_RIGHT );
				
//				motion.setAngles( "LElbowRoll", Math.toRadians(-88), speed );
				motion.setAngles( "LElbowRoll", leftElbowRoll - Math.toRadians( 180 ), speed );
//				motion.setAngles( "RElbowRoll", Math.toRadians(88), speed );
				motion.setAngles( "RElbowRoll", -( rightElbowRoll - Math.toRadians( 180 )), speed );
				
				lastSkeleton = ViewerPanel3D.currentSkel; 
				System.out.println( "Value to robot (left): " + Math.toDegrees( leftElbowRoll ));
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
	public double getVectorAngle(Skeleton current, int joint1, int joint2, int joint3)
	{	//may not need all three points, maybe only 2?
		double angle;
		 
		double aX = current.get3DJointX(joint1);
		double aY = current.get3DJointY(joint1);
		double aZ = current.get3DJointZ(joint1);
		double bX = current.get3DJointX(joint2);
		double bY = current.get3DJointY(joint2);
		double bZ = current.get3DJointZ(joint2);
		double cX = current.get3DJointX(joint3);
		double cY = current.get3DJointY(joint3);
		double cZ = current.get3DJointZ(joint3);
		
		double baX = aX - bX;
		double baY = aY - bY;
		double baZ = aZ - bZ;
		
		double bcX = cX - bX;
		double bcY = cY - bY;
		double bcZ = cZ - bZ;
		
		//calculate the lengths of the vectors
		double mag1 = Math.sqrt(( baX * baX ) + ( baY * baY ) + ( baZ * baZ ));
		double mag2 = Math.sqrt(( bcX * bcX ) + ( bcY * bcY ) + ( bcZ * bcZ ));
		
		//find the dot product
		double dotProd = ( baX * bcX ) + ( baY * bcY ) + ( baZ * bcZ );
		
		//plug into acos formula
//		double test = dotProd/(mag1*mag2*mag3);
		angle = Math.acos( dotProd / ( mag1 * mag2 ));
		
//		System.out.println( "Function angle: " + angle );
		
		return angle;
	}
}
