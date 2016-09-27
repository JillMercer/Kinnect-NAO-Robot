package edu.sru.brian.newapi.demo;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

/**
 * ReactToEvents
 * 
 * {@link https://github.com/aldebaran}
 *
 */
public class ReactToEvents {
	Application app;
    ALMemory memory;
    ALTextToSpeech tts;
    long frontTactilSubscriptionId;
    
    public ReactToEvents(Application app){
    	this.app = app;
    }

    public void run(Session session) throws Exception {   	
    	
        memory = new ALMemory(session);
        tts = new ALTextToSpeech(session);
        frontTactilSubscriptionId = 0;

        // Subscribe to FrontTactilTouched event,
        // create an EventCallback expecting a Float.
        frontTactilSubscriptionId = memory.subscribeToEvent(
                "FrontTactilTouched", new EventCallback<Float>() {
                    @Override
                    public void onEvent(Float arg0)
                            throws InterruptedException, CallError {
                        // 1 means the sensor has been pressed
                        if (arg0 > 0) {
                            tts.say("ouch!");
                        }
                    }
                });
        // Subscribe to RearTactilTouched event,
        // create an EventCallback expecting a Float.
        memory.subscribeToEvent("RearTactilTouched",
                new EventCallback<Float>() {
                    @Override
                    public void onEvent(Float arg0)
                            throws InterruptedException, CallError {
                        if (arg0 > 0) {
                            if (frontTactilSubscriptionId > 0) {
                                tts.say("I'll no longer say ouch");
                                // Unsubscribing from FrontTactilTouched event
                                memory.unsubscribeToEvent(frontTactilSubscriptionId);
                                app.stop();
                            }
                        }
                    }
                });
    }
    
    public static void main(String[] args) throws Exception {
        // Create a new application
        Application application = new Application(args, RobotConfig.getConnectionString());
        
        // Start your application
        application.start();
        System.out.println("Successfully connected to the robot");
        
        // Subscribe to selected ALMemory events
        ReactToEvents reactor = new ReactToEvents(application);
        reactor.run(application.session());
        System.out.println("Subscribed to FrontTactilTouched and RearTactilTouched.");
        
        // Run your application
        application.run();
        System.out.println("End of Program.");

    }
}
