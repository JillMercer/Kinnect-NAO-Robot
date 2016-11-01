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
			try {
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
			float speed = 0.3f;
			String jointName = "RShoulderRoll";
			
			Skeleton skeletons[];
			Skeleton currentSkel;
			Queue<Skeleton> queue = new LinkedList<Skeleton>();
			skeletons=new Skeleton[6];
			int frames = 1;
			int count = 0;
			double angleChange;
			
			motion.setAngles( "RShoulderPitch", Math.toRadians( 0 ), speed );
			for(int i=0;i<skeletons.length;i++)
		    	if(skeletons[i]!=null) 
		    	{
		    		if(skeletons[i].getTimesDrawn()<=10 && skeletons[i].isTracked())
		    			{
		    				currentSkel = skeletons[i]; 
//			    			skeletons[i].draw(gl);
//			    			skeletons[i].increaseTimesDrawn();
			    			if(count % 10 == 0 ) // slows down time drawn
			    			{
			    		//Accesses the individual joints and gets the XYZ coordinates and writes them to a file for the time being
			    		//There are 25 different joints in the skeleton class to access this is listed on j4k.com (skeleton class)
			    				//checks if the queue is less than number of frames wanted only has 2 frames at time
			    				if (queue.size()<frames)
			    					queue.add(skeletons[i]);
			    				else
			    				{
//			    					double content
			    					angleChange = getAngle(queue.element().get3DJointX(Skeleton.ELBOW_RIGHT),
			    							queue.element().get3DJointY(Skeleton.ELBOW_RIGHT),
			    							currentSkel.get3DJointX(Skeleton.ELBOW_RIGHT), 
			    							currentSkel.get3DJointY(Skeleton.ELBOW_RIGHT));
			    					
//					    			System.out.println( content );
			    					queue.remove();
			    					queue.add(currentSkel);
			    				}
			    				
			    			}
			    		count++;
			    			
		    			}
		    }
			
//			motion.setAngles( "LShoulderPitch", Math.toRadians( 0 ), speed );
			Thread.sleep( 1000 );
			
			Thread.sleep( 1000 );
			motion.angleInterpolation( jointName, Math.toRadians( 238 ), 5, false );
			// When done, go back to standing:
			posture.goToPosture("Stand", 1.0f);
		}
		
//		Jill's Function: 
		double getAngle(double x1, double y1, double x2, double y2)
		{ // this gets the two joints and finds the angle between them
			double radians;
			if((x2-x1) == 0)
			{
				radians=0;
			}
			else
				radians = Math.toRadians((Math.atan((y2-y1)/(x2-x1))));
			return radians;
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
}
