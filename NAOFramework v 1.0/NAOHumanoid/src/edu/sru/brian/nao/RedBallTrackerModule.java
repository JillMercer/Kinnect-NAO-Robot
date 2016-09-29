/**
 * RedBallTrackerModule
 * 
 * Author: Brian Atwell
 * 
 * This will run the basic red ball tracker.
 */

package edu.sru.brian.nao;

import java.util.ArrayList;
import java.util.Arrays;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRedBallDetection;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTracker;

import edu.sru.thangiah.nao.module.AppInterface;

/*
 * Basic red ball tracker module
 * That runs a basic red ball demo
 * 
 * @author Brian Atwell
 */
public class RedBallTrackerModule implements AppInterface {
	
	private Session session;
	private ALTextToSpeech tts;
	private ALMemory alMemory;
	private ALTracker alTracker;
	private ALRobotPosture alPosture;
	private ALMotion	alMotion;
	private ALBasicAwareness alBasicAwareness;
	
	private float diameter;
	private String mode;
	
	public RedBallTrackerModule(Session session) {
		diameter = 0.10f;
		mode = "Move";
		initialize(session);
	}
	
	/**
	 * Initialize red ball NAO robot
	 */
	public void initialize(Session session) {
		if( session == null) {
			System.out.println("Null pointer!!");
			return;
		}
		this.session = session;
		if(!session.isConnected()) {
			System.out.println("Null pointer!!");
			return;
		}
		try {
			this.tts = new ALTextToSpeech(session);
			this.alMemory = new ALMemory(session);
			this.alTracker = new ALTracker(session);
			this.alMotion = new ALMotion(session);
			this.alPosture = new ALRobotPosture(session);
			this.alBasicAwareness = new ALBasicAwareness(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void exit() {
		stop();
		
	}

	/**
	 * Start red ball tracker module
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
        try {
        	
        	// Wake up robot
			alMotion.wakeUp();
			
			//Go to posture stand
	        alPosture.goToPosture("Stand", 0.8f);
	        
	        alBasicAwareness.stopAwareness();
	        
	        
	        // choregraphe example [-self.distanceX, self.distanceY, self.angleWz,
	        //self.thresholdX, self.thresholdY, self.thresholdWz]
	        Float[] relPos = {new Float(-0.3f),new Float(0),new Float(0),
	        		new Float(0.1f),new Float(0.1f),new Float(0.1f)};
	        ArrayList<Float> relPosAry = new ArrayList<Float>(Arrays.asList(relPos));
	        
	        // Initialize movement Used for Move and WholeBody
	        alMotion.moveInit();
	        alTracker.setRelativePosition(relPosAry);
	        alTracker.registerTarget("RedBall", diameter);
	        alTracker.setMode(this.mode);
	        alTracker.track("RedBall");
		} catch (CallError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		try {
			alTracker.stopTracker();
	    	alTracker.unregisterAllTargets();
	    	alPosture.goToPosture("Stand", 0.8f);
	        alBasicAwareness.startAwareness();
	        alMotion.wakeUp();
		} catch (CallError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the mode of the tracker
	 * Available parameters for @param mode:
	 * "Head"
	 * "WholeBody"
	 * "Move"
	 * 
	 * If it is a wrong value it will not set the mode.
	 * 
	 * 
	 */
	public void setMode(String mode) {
		if(alTracker != null) {
			try {
				switch(mode) {
				case "Head":
				case "WholeBody":
				case "Move":
					this.mode = mode;
					alTracker.setMode(mode);
					break;
				default:
					return;
				}
			} catch (CallError | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Set the diameter of the red ball you are using.
	 * @param diameter
	 */
	public void setDiameter(float diameter) {
		if(alTracker != null) {
			try {
				this.diameter = diameter;
				alTracker.registerTarget("RedBall", diameter);
			} catch (CallError | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the mode of the tracker
	 * Available @return strings:
	 * "Head"
	 * "WholeBody"
	 * "Move"
	 * 
	 * 
	 */
	public String getMode() {
		return this.mode;
	}
	
	/**
	 * Get the red ball diameter
	 */
	public float getDiameter() {
		return this.diameter;
	}

	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	*/

}
