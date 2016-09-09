package edu.sru.brian.newapi.demo;

import com.aldebaran.demo.RobotIP;
import com.aldebaran.qi.Application;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

/**
 * Test new api with multiple sessions
 * 
 * @author Brian Atwell
 *
 */

public class HelloWorldSeparateSession {
	 public static void main(String[] args) throws Exception {
	        // Create a new application
	        Application application = new Application(args);
	        
	        Session session;
	        
	        session = new Session();
	        
	        // Start your application
	        //application.start();
	        
	        Future<Void> future = null;
	        try {
		        future = session.connect("tcp://"+RobotIP.ip+":"+RobotIP.port);

	            synchronized (future) {
	                future.wait(1000);
	            }
	            
	            if(!session.isConnected())
	            {
	            	System.out.println("Failed to connect!!");
	            }
	            else
	            {
	            	System.out.println("Connected Successfully!!!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	        // Create an ALTextToSpeech object and link it to your current session
	        ALTextToSpeech tts = new ALTextToSpeech(session);
	        
	        // Make your robot say something
	        tts.say("Hello World!");
	        
	        //application.run();
	    }
}
