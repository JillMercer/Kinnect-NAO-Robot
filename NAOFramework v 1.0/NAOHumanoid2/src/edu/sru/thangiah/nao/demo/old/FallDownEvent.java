package edu.sru.thangiah.nao.demo.old;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;

public class FallDownEvent
{
	private ALMemory memory;
	private ALRobotPosture posture;
	private ALTextToSpeech tts;
	private ALMotion motion;
	private EventPair onFall;
	private EventPair onMiddleTouch;
	private Application app;
	
	
	public FallDownEvent(Application app) throws Exception {
		this.app = app;
		this.onFall = new EventPair();
		this.onFall.eventName = NaoEvents.robotHasFallen;
		this.onMiddleTouch = new EventPair();
		this.onMiddleTouch.eventName = NaoEvents.MiddleTactilTouched;
		
		this.app.start();
		
		this.tts = new ALTextToSpeech(app.session());
		this.memory = new ALMemory(app.session());
		this.posture = new ALRobotPosture(app.session());
		this.motion = new ALMotion(app.session());
		this.motion.setFallManagerEnabled(true);
		
		this.onFall.eventID = this.memory.subscribeToEvent(this.onFall.eventName, (value) -> {
			System.out.println("Robot fell down.");
			this.tts.say("Uh oh. Looks like I fell down.");
			this.tts.async().say("I will attempt to stand up now.");
			this.motion.wakeUp();	
		});
		
		this.onMiddleTouch.eventID = this.memory.subscribeToEvent(this.onMiddleTouch.eventName, (value) -> {
			System.out.println("Exiting...");
			this.memory.unsubscribeToEvent(this.onFall.eventID);
			this.memory.unsubscribeToEvent(this.onMiddleTouch.eventID);
			this.app.stop();
		});
		
		this.app.run();
	}
	
	public static void main(String[] args) {		
		try {
			Application a = new Application(args, "tcp://192.168.0.103:9559");
			FallDownEvent fde = new FallDownEvent(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
