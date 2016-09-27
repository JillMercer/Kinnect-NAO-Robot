package com.aldebaran.demo;

import java.util.List;
import com.aldebaran.qimessaging.Application;
import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.al.ALCloseObjectDetection;
import com.aldebaran.qimessaging.helpers.al.ALMemory;
import com.aldebaran.qimessaging.helpers.al.ALRedBallDetection;
import com.aldebaran.qimessaging.helpers.al.ALRedBallTracker;
import com.aldebaran.qimessaging.helpers.al.ALTextToSpeech;

class MyALRedBallTracker extends ALRedBallTracker {

	public MyALRedBallTracker(Session session) {
		super(session);
		// TODO Auto-generated constructor stub
	}
	
	public void _onBallDetected(java.lang.String param1, java.lang.Object param2, java.lang.String param3) {
		System.out.println("Red ball detected!");
		System.out.println("param1: "+param1);
		System.out.println("param2: "+(ALRedBallDetection)param2);
		System.out.println("param3: "+param3);
		try {
			System.out.println("I spy with my little eye a red ball at "+this.getPosition());
		} catch (CallError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


public class ExRedBallDetectFail {
	
	public static Session session;
	public static Application app;
	public static  Future<Void> future;
	public static ALTextToSpeech tts;
	public static ALRedBallTracker redBallTrack;
	public static ALRedBallDetection redBallDetect;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		session = new Session();
		app = new Application();
        future = null;
        
        //ALRedBallDetection redBallDet;
        ALMemory alMemory;
        
        try {
	        future = session.connect("tcp://"+RobotIP.ip+":"+RobotIP.port);

            synchronized (future) {
                future.wait(1000);
            }
	        
	        tts = new ALTextToSpeech(session);
	        redBallTrack = new ALRedBallTracker(session);
	        alMemory = new ALMemory(session);
	        
	        redBallTrack = new MyALRedBallTracker(session);
	        
	        if(null == redBallTrack) {
	        	System.out.println("redBallTrack is null!");
	        }
	        
	        alMemory.subscribeToEvent("LeftBumperPressed" , "onEnd::(f)", new Object() {
	            public void onEnd(Float touch) throws InterruptedException, CallError {
		            if(touch == 1.0) {
			            tts.say("Application is stopping");
			            app.stop();
		            }
	            }
            });
	        
	        /*
	        alMemory.subscribeToEvent("redBallDetected", "onBallDetected::(p1,p2,p3)", new Object() 
            {
	            public void onBallDetected(java.lang.String param1, java.lang.Object param2, java.lang.String param3) throws InterruptedException, CallError 
	            {
	            	System.out.println("Red ball detected!");
	        		System.out.println("param1: "+param1);
	        		System.out.println("param2: "+(ALRedBallDetection)param2);
	        		System.out.println("param3: "+param3);
	        		try {
						System.out.println("I spy with my little eye a red ball at "+redBallTrack.getPosition());
					} catch (CallError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
            });
            */
	        
	        
	        //redBallDet.subscribeToEvent("", "", callback);
	        redBallTrack.startTracker();
	        //redBallTrack.subscriber("demo");
	        //alMemory.subscribeToEvent(event, signature, callback);
	        
            //app.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
