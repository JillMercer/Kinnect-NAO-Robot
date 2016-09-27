package edu.sru.brian.newapi.demo;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

/**
 * Test the new api
 * 
 * By Aldebaran Github
 * {@link https://github.com/aldebaran}
 *
 */

public class ExNewAPI {
	 public static void main(String[] args) throws Exception {
	        // Create a new application
	        Application application = new Application(args, RobotConfig.getConnectionString());
	        
	        // Start your application
	        application.start();
	        
	        // Create an ALTextToSpeech object and link it to your current session
	        ALTextToSpeech tts = new ALTextToSpeech(application.session());
	        
	        // Make your robot say something
	        tts.say("Hello World!");
	    }
}
