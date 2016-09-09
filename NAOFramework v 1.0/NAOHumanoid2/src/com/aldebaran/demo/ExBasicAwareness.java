package com.aldebaran.demo;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.Connect;

/**
 * Created by epinault on 25/09/2014.
 */
//Only on real robot
public class ExBasicAwareness extends AldeberanDemo{

	public static void main(String[] args) throws Exception {
		Connect connect = new Connect("192.168.0.101");
		connect.run();
		Session session = connect.getSession();
		application = connect.getApplication();

			alMemory = new ALMemory(session);
			awareness = new ALBasicAwareness(session);
			tts = new ALTextToSpeech(session);
			motion = new ALMotion(session);

			tts.say("hello");
			motion.wakeUp();
			awareness.setEngagementMode("SemiEngaged");
			awareness.setTrackingMode("Head");
			awareness.setStimulusDetectionEnabled("Sound", true);
			awareness.setStimulusDetectionEnabled("Movement", true);
			awareness.setStimulusDetectionEnabled("People", true);
			awareness.setStimulusDetectionEnabled("Touch", true);

			alMemory.subscribeToEvent("RearTactilTouched" , "onBackTouch::(f)", new Object() {
				public void onBackTouch(Float touch) throws InterruptedException, CallError {
					if(touch == 1.0) {
						System.out.println("touch");
						awareness.stopAwareness();
					}
				}
			});
			alMemory.subscribeToEvent("FrontTactilTouched" , "onFrontTouch::(f)", new Object() {
				public void onFrontTouch(Float touch) throws InterruptedException, CallError {
					if(touch == 1.0) {
						System.out.println("touch");
						awareness.startAwareness();
					}
				}
			});
			alMemory.subscribeToEvent("ALBasicAwareness/HumanTracked", "onHumanTracked::(i)", new Object() {
				public void onHumanTracked(Integer humanID) throws InterruptedException, CallError {
					if (humanID >= 0) {
						tts.say("I see " + humanID);
					}
				}
			});
			application.run();
	}


}
