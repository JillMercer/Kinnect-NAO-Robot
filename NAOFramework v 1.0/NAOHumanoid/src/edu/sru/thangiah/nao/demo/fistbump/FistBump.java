package edu.sru.thangiah.nao.demo.fistbump;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTouch;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;

public class FistBump{

	// Any utilized modules are declared here. 
	//They must be initialized in the init() method.
	private ALMemory memory;
	private ALTouch touch;
	private ALMotion motion;
	private ALRobotPosture posture;
	private ALTextToSpeech speech;
	private EventPair handTouch;


	/**
	 * DemoRobot calls on the constructor
	 * 
	 * @param name
	 * @param appName
	 * @param connect
	 * @throws Exception
	 */
	public FistBump(String name, SynchronizedConnectDemo connect)   {
		try {
			this.memory = new ALMemory(connect.getSession(name));
			this.touch = new ALTouch(connect.getSession(name));
			this.motion = new ALMotion(connect.getSession(name));
			this.posture = new ALRobotPosture(connect.getSession(name));
			this.speech = new ALTextToSpeech(connect.getSession(name));
		} catch (Exception e) {
			e.printStackTrace();
		}     

	}

	/*
	 * Raise arm for the bump
	 */
	public void raiseArmforBump (){
		try {
			System.out.println("FrontTop touched: Raise Arm");
			motion.setBreathEnabled("RArm", false);
			motion.setIdlePostureEnabled("RArm", false);
			motion.setStiffnesses("RArm", 1.0f);
			motion.setAngles("RShoulderPitch", 0.0f, 0.5f);
			motion.setAngles("RWristYaw", 0, 0.5f);
			motion.closeHand("RHand");
			Thread.sleep(50);
			speech.say("Pound it");							
		}
		catch (Exception ex) 
		{
			System.out.println("Error:Raise arm for the bump");
			ex.printStackTrace();
		}
	}

	/*
	 * Hand was bumped
	 */
	public void handBumped() {		
		/* Hand "bumped" */
		try {
			System.out.println("Hand touched: Lower Arm");
			speech.say("Nice");
			Thread.sleep(50);
			motion.setAngles("RShoulderRoll", 1.3f, 0.2f);
			motion.setAngles("RElbowRoll", -1.5f, 0.2f);
			Thread.sleep(50);
			posture.goToPosture("Stand", 1.0f);
			motion.setBreathEnabled("RArm", true);
			motion.setIdlePostureEnabled("RArm", true);
		} 
		catch (Exception ex) 
		{
			System.out.println("Error:Hand bumped");
			ex.printStackTrace();
		}
	}

}


