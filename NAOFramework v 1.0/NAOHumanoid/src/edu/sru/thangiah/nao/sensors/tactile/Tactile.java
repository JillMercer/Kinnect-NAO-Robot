package edu.sru.thangiah.nao.sensors.tactile;

import com.aldebaran.qi.*;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.Module;
import tests.RobotConfig;

/**
 * @author Justin Cather
 */

public class Tactile extends Module implements TactileInterface{
		
	private ALMemory memory;
	
	/**
	 * Constructor for TouchSensors.
	 * @param session The session to use.
	 * @throws Exception
	 */
	public Tactile(Session session) throws Exception{
		super(session);
		this.memory = new ALMemory(session);
	}
	
	@Override
	public void exit() throws Exception {
		this.memory.exit();
		
	}


	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * Gets the most recently touched tactile sensor on the robot.
	 * @return The most recently touched sensor.
	 * @throws Exception 
	 */
	@Override
	public String getMostRecentTouch() throws Exception{
		return memory.getData("TouchChanged").toString();
	}
	
	/**
	 * Checks if right foot bumper is currently being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isRightFootBumper() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("RightBumperPressed");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if left foot bumper is currently being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isLeftFootBumper() throws Exception{
		int value = -1;
		
		try {
			value = (int)memory.getData("LeftBumperPressed");
		} catch (Exception e) {
			value = -1;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the bottom of the left hand is currently being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isLeftHandBottom() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("HandLeftLeftTouched");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the top of the left hand is currently being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isLeftHandTop() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("HandLeftRightTouched");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the back of the left hand is currently being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isLeftHandBack() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("HandLeftBackTouched");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the top of the right hand is currently being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isRightHandTop() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("HandRightLeftTouched");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the bottom of the right hand is currently being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isRightHandBottom() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("HandRightRightTouched");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the back of the right hand is currently being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isRightHandBack() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("HandRightBackTouched");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the rear sensor on the top of the robot's head is being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isRearTactile() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("RearTactilTouched");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the middle sensor on the top of the robot's head is being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isMiddleTactile() throws Exception{
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("MiddleTactilTouched");
		} catch (Exception e) {
			value  = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if the front sensor on the top of the robot's head is being touched.
	 * @return True if touched, false if not.
	 * @throws Exception 
	 */
	@Override
	public boolean isFrontTactile() throws Exception{	
		float value = -1.0f;
		
		try {
			value = (float)memory.getData("FrontTactilTouched");
		} catch (Exception e) {
			value = -1.0f;
		}
		
		if (value > 0)
			return true;
		else
			return false;
	}
		
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws CallError
	 * @throws Exception
	 */
	public static void main(String[] args) throws InterruptedException, CallError, Exception{
		boolean isTouched = false;
		String touchedSensor = "";
		Connect connect = new Connect();
		connect.run();
        Tactile sensors = new Tactile(connect.getSession());
        ALTextToSpeech tts = new ALTextToSpeech(connect.getSession());
        
        tts.say("Touch One of my tactile sensors...");
        
        // Wait for touch.
        System.out.println("Waiting for touch....");
        try {
			while (!isTouched) {
				isTouched = sensors.isLeftHandBottom();
				if (isTouched){
					touchedSensor = "Left Hand Bottom";
					break;
				}
					      	
				isTouched = sensors.isLeftHandTop();
				if (isTouched){
					touchedSensor = "Left Hand Top";
					break;
				}
				
				isTouched = sensors.isLeftHandBack();
				if (isTouched){
					touchedSensor = "Left Hand Back";
					break;
				}
				
				isTouched = sensors.isRightHandBottom();
				if (isTouched){
					touchedSensor = "Right Hand Bottom";
					break;
				}
					    	
				isTouched = sensors.isRightHandTop();
				if (isTouched){
					touchedSensor = "Right Hand Top";
					break;
				}
				
				isTouched = sensors.isRightHandBack();
				if (isTouched){
					touchedSensor = "Right Hand Back";
					break;
				}
				
				isTouched = sensors.isFrontTactile();
				if (isTouched){
					touchedSensor = "Front Tactile";
					break;
				}
				
				isTouched = sensors.isRearTactile();
				if (isTouched){
					touchedSensor = "Rear Tactile";
					break;
				}
				
				isTouched = sensors.isMiddleTactile();
				if (isTouched){
					touchedSensor = "Middle Tactile";
					break;
				}
				
				isTouched = sensors.isLeftFootBumper();
				if (isTouched){
					touchedSensor = "Left Foot Bumper";
					break;
				}
				
				isTouched = sensors.isRightFootBumper();
				if (isTouched){
					touchedSensor = "Right Foot Bumper";
					break;
				}
			}
		} catch (Exception e) {}       
        System.out.println("TOUCH DETECTED! " + touchedSensor);        
        System.out.println("\n Most recently touched sensor... " + sensors.getMostRecentTouch());
        
        tts.say("That is my " + touchedSensor);
	}
}
