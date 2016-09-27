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
import edu.sru.thangiah.nao.sensors.tactile.Tactile;

/**
 * @author Justin Cather
 * 3/29/2016
 */
public class WalkWithMe extends Application
{
	private ALMemory memory;
    private ALTouch alTouch;
    private ALMotion motion;
    private ALRobotPosture posture;
    private Tactile tactile;
    private ALTextToSpeech tts;
    private volatile Boolean isActive;
	
	
	/** Constructor
	 * @param args
	 * @param defaultUrl robot IP
	 */
	public WalkWithMe(String[] args, String defaultUrl) {
		super(args, "tcp://" + defaultUrl + ":9559");
		
		try {
			this.start();
			this.memory = new ALMemory(this.session());
			this.alTouch = new ALTouch(this.session());
			this.motion = new ALMotion(this.session());
			this.posture = new ALRobotPosture(this.session());
			this.tactile = new Tactile(this.session());
			this.tts = new ALTextToSpeech(this.session());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.isActive = false;	
	}
	
	/**
	 * Start the walk events.
	 */
	public void walkStart(){
		// Events
		EventPair frontTactile = new EventPair();
		frontTactile.eventName = NaoEvents.FrontTactilTouched;
		EventPair handTouch = new EventPair();
		handTouch.eventName = NaoEvents.HandRightBackTouched;
		EventPair middleTactile = new EventPair();
		middleTactile.eventName = NaoEvents.MiddleTactilTouched;
		
		try {
			// Front Tactile Event
			frontTactile.eventID = this.memory.subscribeToEvent(frontTactile.eventName, value -> {
				if (((Float) value) > 0.0 && !isActive) {
					// Disable this stuff.
					motion.setBreathEnabled("RArm", false);
					motion.setIdlePostureEnabled("RArm", false);
					Thread.sleep(500);
					
					// Set positions.
					motion.setStiffnesses("RArm", 1.0f);
					Thread.sleep(500);
					motion.setAngles("RShoulderPitch", 0.0f, 0.5f);
					Thread.sleep(500);
					motion.setAngles("RWristYaw", 0.0f, 0.5f);
					Thread.sleep(500);
					motion.openHand("RHand");
					Thread.sleep(500);
					
					// Speak
					tts.say("Grab my hand!");
				}
			});
			
			// Middle Tactile Event
			middleTactile.eventID = this.memory.subscribeToEvent(middleTactile.eventName, value -> {
				if (((Float) value) > 0.00 && !isActive) {
					System.out.println("_________WalkWithMe is Stopping__________");
                    this.memory.unsubscribeToEvent(frontTactile.eventID);
                    this.memory.unsubscribeToEvent(middleTactile.eventID);
                    this.memory.unsubscribeToEvent(handTouch.eventID);
                    this.stop();
				}
			});
			
			// Hand Grab Event
			handTouch.eventID = this.memory.subscribeToEvent(handTouch.eventName, value -> {
				synchronized (this.isActive) {
					if (((Float) value) > 0.00 && !isActive) {
						this.isActive = true;
						
						// Close hand and start walking.               
						motion.setStiffnesses("RArm", 0.0f);
						motion.closeHand("RHand");
						motion.moveInit();
						motion.moveToward(0.5f, 0.0f, 0.0f);

						try {
							// infinite loop while hand sensor is touched. 
							while (tactile.isRightHandBack()){
							    System.out.println("Move status: " + motion.moveIsActive());
								Thread.sleep(500);
							}
						} catch (Exception ex) {}									
						
						// Open hand and stop the move.
						motion.openHand("RHand");
						motion.stopMove();
						motion.waitUntilMoveIsFinished();
									
						// Re-enable breathe and idle.
						motion.setBreathEnabled("RArm", true);
						motion.setIdlePostureEnabled("RArm", true);
						posture.goToPosture("Stand", 0.6f); //added this
						System.out.println("Walk OVER...");
						
						this.isActive = false;				
					}
				}
			});
			
			System.out.println("WalkWithMe is active now.");
			this.run();
				
		} catch (Exception ex) {
			System.out.println("_________WalkWithMe is Error__________");
            try {
				this.memory.unsubscribeToEvent(frontTactile.eventID);
	            this.memory.unsubscribeToEvent(middleTactile.eventID);
	            this.memory.unsubscribeToEvent(handTouch.eventID);
	            this.stop();
			} catch (CallError | InterruptedException e) {
				e.printStackTrace();
			}         
		}
	}
	
	public static void main(String[] args){
		WalkWithMe wwm = new WalkWithMe(args, "192.168.0.109");
		wwm.walkStart();
	}
}
