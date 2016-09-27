package edu.sru.thangiah.nao.vision.tracking;

/** Author: Zachary Kearney
Last Edited, 11/5/2015
* @author zrk1002
*
*/

import java.util.ArrayList;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALTracker;

import edu.sru.thangiah.nao.awareness.Awareness;
import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.Module;

public class Tracking extends Module implements TrackingInterface{
	
	private boolean isRunning;
	private ALTracker tracking;
	private ALMotion motion;
	private Awareness awareness;
	private String target;
	private float parameter;
	private String mode;
	
	public Tracking(Session session) throws Exception{
		
		super(session);
		isRunning = true;
		mode = "Head";
		
		tracking = new ALTracker(session);
		motion = new ALMotion(session);
		isRunning = true;
		awareness = new Awareness(session);
		awareness.setAllBreathing(false);
		motion.wakeUp();
		setMode(mode);		
	}
	
	
	public void exit() throws Exception{
		
		tracking.stopTracker();
		tracking.unregisterAllTargets();
		tracking.exit();
		motion.waitUntilMoveIsFinished();
		motion.exit();
		awareness.exit();
		isRunning = false;
	}
	
	public void reset() throws Exception{
		
		awareness.reset();
		
	}
	
	/**
	 * Returns the status of the tracking module;
	 */
	
	public boolean getStatus() throws Exception{
		
		return isRunning;
		
	}
	
	/**
	 * Sets the current Tracking Mode;
	 */
	
	public void setMode(String Mode) throws Exception {
		if(Mode.equals("Head")||Mode.equals("Move")||Mode.equals("WholeBody")){
			mode = Mode;
		}
		else throw new Exception("Invalid Argument");
	}
	
	/**
	 * Returns the tracking mode;
	 */
	
	public String getMode() throws Exception {
		return mode;
	}
	
	/**
	 * List of target Names and Parameters
	 * RedBall - Diameter of Ball (Float meter)
	 * Face - Width of Face (Float meter)
	 * LandMark - [size,[LandMarkId,...]]
	 * LandMarks - [[size,[LandMarkId,..]],[size,[LandMarkId,...]]]
	 * People - [peopleId, ...]
	 * Sound - [Distance, Confidence]
	 * @param connect
	 * @param target
	 * @param parameter
	 * @throws Exception
	 */
	
	public void startTracking(String target, float parameter) throws Exception {
		
		tracking.unregisterAllTargets();
		tracking.registerTarget(target, parameter);
		
		motion.wakeUp();
		Thread.sleep(250);
		motion.setStiffnesses("Body", 1f);
		Thread.sleep(250);
		motion.moveInit();
		Thread.sleep(500);
		tracking.track(target);
		
	}
	
	/**
	 * Stops the tracker;
	 */
	
	public void stopTracking() throws Exception {
		
		tracking.stopTracker();
		
	}
	
	/**
	 * Points at current target;
	 * Effector can be --
	 * RArm, LArm, Arms;
	 * @param effector
	 * @throws Exception
	 */
	
	public void pointAt(String effector) throws Exception {
		
		tracking.pointAt(effector, tracking.getTargetPosition(), 0, 1.0f);
		
	}
	
	/**
	 * Looks at current target;
	 * @throws Exception
	 */
	
	public void lookAt() throws Exception{
		
		tracking.lookAt(getTargetPosition(), 0, 1.0f, true);
	}

	/**
	 * Returns ArrayList of targetPosition <X,Y,Z>
	 * @return
	 * @throws Exception
	 */
	
	public ArrayList<Float> getTargetPosition() throws Exception{
		
		return (ArrayList<Float>)tracking.getTargetPosition();
	}
	
	/**
	 * Returns true if robot is currently tracking;
	 * @return
	 * @throws Exception
	 */
	
	public boolean isTracking() throws Exception{
		return tracking.isActive();
	}
	
	/**
	 * Returns true if the target is lost;
	 * @return
	 * @throws Exception
	 */
	
	public boolean isTargetLost() throws Exception{
		return tracking.isTargetLost();
	}
	
	
}
