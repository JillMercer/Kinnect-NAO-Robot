package com.aldebaran.demo;
/*
 * 
 * Andrew Rindt
 * 
 */
import com.aldebaran.demo.RobotIP;
import com.aldebaran.qimessaging.Application;
import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.al.ALMemory;
import com.aldebaran.qimessaging.helpers.al.ALMotion;
import com.aldebaran.qimessaging.helpers.al.ALTextToSpeech;

import java.lang.Object;

public class TurnTest
{
	private static Application application;
    private static ALMemory alMemory;
    private static ALMotion motion;
    private static ALTextToSpeech tts;
    
    public static void main(String[] args) throws InterruptedException, CallError
	{
		application = new Application();
		Session session = new Session();
		Future<Void> future = null;
		
        try 
        {
	        future = session.connect("tcp://"+RobotIP.ip+":"+RobotIP.port);

            synchronized (future)
            {
                future.wait(1000);
            }
            
            alMemory = new ALMemory(session);
            motion = new ALMotion(session);
            tts = new ALTextToSpeech(session);
            
            alMemory.subscribeToEvent("FrontTactilTouched", "onEnd::(f)", new Object() 
            {
	            public void onEnd(Float touch) throws InterruptedException, CallError 
	            {
		            if (touch == 1.0 && !motion.moveIsActive()) 
		            {
		            	tts.say("Turning right.");
		            	motion.moveToward(0f, 0f, 0.5f);
		            }
	            }
            });
            
            alMemory.subscribeToEvent("MiddleTactilTouched", "onEnd::(f)", new Object() 
            {
	            public void onEnd(Float touch) throws InterruptedException, CallError 
	            {
		            if (touch == 1.0 && motion.moveIsActive()) 
		            {
		            	tts.say("Stopping.");
		            	motion.moveToward(0f, 0f, 0f);
		            }
	            }
            });
            
            alMemory.subscribeToEvent("RearTactilTouched", "onEnd::(f)", new Object() 
            {
	            public void onEnd(Float touch) throws InterruptedException, CallError 
	            {
		            if (touch == 1.0 && !motion.moveIsActive()) 
		            {
		            	tts.say("Turning left.");
		            	motion.moveToward(0f, 0f, -0.5f);
		            }
	            }
            });
            
            application.run();
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        finally
        {
        	session.close();
        }
	}
}