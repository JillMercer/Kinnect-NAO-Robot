package edu.sru.thangiah.nao.demo.old;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTouch;

import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;

/**
 * @author Justin Cather
 * 3/29/2016
 */
public class FistBump extends Application {

	private ALMemory memory;
    private ALTouch touch;
    private ALMotion motion;
    private ALRobotPosture posture;
    private ALTextToSpeech speech;
	
	/**
	 * @param args empty
	 * @param defaultUrl robot IP
	 */
	public FistBump(String[] args, String defaultUrl) {
		super(args, "tcp://" + defaultUrl + ":9559");
		
		try {
			this.start();
			this.memory = new ALMemory(this.session());
			this.touch = new ALTouch(this.session());
	        this.motion = new ALMotion(this.session());
	        this.posture = new ALRobotPosture(this.session());
	        this.speech = new ALTextToSpeech(this.session());
		} catch (Exception e) {
			e.printStackTrace();
		}     
	}
	
	public void startEvents() {
		// Events
		EventPair frontTactile = new EventPair();
		frontTactile.eventName = NaoEvents.FrontTactilTouched;
		EventPair handTouch = new EventPair();
		handTouch.eventName = NaoEvents.HandRightLeftTouched;
		EventPair middleTactile = new EventPair();
		middleTactile.eventName = NaoEvents.MiddleTactilTouched;
			
		try {
			/* Touch the Front button. Raise ARM. */
			frontTactile.eventID = this.memory.subscribeToEvent(frontTactile.eventName, value -> {
				if (((Float) value) > 0.0) {
					System.out.println("FrontTop touched: Raise Arm");
					motion.setBreathEnabled("RArm", false);
					motion.setIdlePostureEnabled("RArm", false);
					motion.setStiffnesses("RArm", 1.0f);
					motion.setAngles("RShoulderPitch", 0.0f, 0.5f);
					motion.setAngles("RWristYaw", 0, 0.5f);
					motion.closeHand("RHand");

					Thread.sleep(500);
					speech.say("Pound it");
				}
			});
			
			/* Hand "bumped" */
			handTouch.eventID = this.memory.subscribeToEvent(handTouch.eventName, value -> {
				if (((Float) value) > 0.0) {
					System.out.println("Hand touched: Lower Arm");
					speech.say("Nice");
					Thread.sleep(1000);
					motion.setAngles("RShoulderRoll", 1.3f, 0.2f);
					motion.setAngles("RElbowRoll", -1.5f, 0.2f);
					Thread.sleep(100);
					posture.goToPosture("Stand", 1.0f);
					motion.setBreathEnabled("RArm", true);
					motion.setIdlePostureEnabled("RArm", true);
				}

			});
			
			/* Stop the program */
			middleTactile.eventID = this.memory.subscribeToEvent(middleTactile.eventName, value -> {
				if (((Float) value) > 0.00) {
					System.out.println("_________FistBump is Stopping__________");
					memory.unsubscribeToEvent(frontTactile.eventID);
					memory.unsubscribeToEvent(handTouch.eventID);
					memory.unsubscribeToEvent(middleTactile.eventID);
					this.stop();
				}
			});

			System.out.println("FistBump is running");
			this.run();			
			
		} catch (Exception ex) {
			System.out.println("_________FistBump is Error__________");
			try {
				memory.unsubscribeToEvent(frontTactile.eventID);
				memory.unsubscribeToEvent(handTouch.eventID);
				memory.unsubscribeToEvent(middleTactile.eventID);
				this.stop();
			} catch (InterruptedException | CallError e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void main(String[] args){
		FistBump fb = new FistBump(args, "192.168.0.109");
		fb.startEvents();
	}
}
