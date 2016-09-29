package com.aldebaran.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.aldebaran.qimessaging.Application;
import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.al.ALBasicAwareness;
import com.aldebaran.qimessaging.helpers.al.ALCloseObjectDetection;
import com.aldebaran.qimessaging.helpers.al.ALMemory;
import com.aldebaran.qimessaging.helpers.al.ALMotion;
import com.aldebaran.qimessaging.helpers.al.ALRedBallDetection;
import com.aldebaran.qimessaging.helpers.al.ALRedBallTracker;
import com.aldebaran.qimessaging.helpers.al.ALRobotPosture;
import com.aldebaran.qimessaging.helpers.al.ALTextToSpeech;
import com.aldebaran.qimessaging.helpers.al.ALTracker;


public class ExRedBallDetectionTest{
	
	public static Session session;
	public static Application app;
	public static  Future<Void> future;
	public static ALTextToSpeech tts;
	public static ALRedBallDetection redBallDetect;
	public static ALMemory alMemory;
	public static ALTracker alTracker;
	public static ALBasicAwareness alBasicAwareness;
	public static ALRobotPosture alPosture;
	public static ALMotion	alMotion;
	public static boolean isRedBallDetected =false;
	public static boolean isBumperPessed = false;
	public static boolean isRunning =true;
	
	public static void main(String[] args) throws CallError, InterruptedException {
		ExRedBallDetectionTest ex;
		ex = new ExRedBallDetectionTest();
		ex.runNAO();
	}

	public void runNAO() {
		// TODO Auto-generated method stub
		session = new Session();
		app = new Application();
        future = null;
        
        //ALRedBallDetection redBallDet;
        
        try {
	        future = session.connect("tcp://"+RobotIP.ip+":"+RobotIP.port);

            synchronized (future) {
                future.wait(1000);
            }
            
            if(!session.isConnected()) {
            	System.out.println("FAILED to connect to robot at IP: "+RobotIP.ip+" Port: "+RobotIP.port);
            	System.exit(1);
            }
	        
	        tts = new ALTextToSpeech(session);
	        alMemory = new ALMemory(session);
	        alTracker = new ALTracker(session);
	        alPosture = new ALRobotPosture(session);
	        alMotion = new ALMotion(session);
	        alBasicAwareness = new ALBasicAwareness(session);
	        
	        Runtime.getRuntime().addShutdownHook(new Thread() {
	        	   @Override
	        	   public void run() {
	        		   uninitialize();
	        	   }
	        	  });
	       
	        alMemory.subscribeToEvent("redBallDetected", "onBallDetected::(m)", new Object() {
	        	 public void onBallDetected(Object param) throws InterruptedException, CallError 
		            {
	        		 		if(!isRedBallDetected)
	        		 		{
	        		 			isRedBallDetected=true;
	        		 			//tts.say("I spy with my little eye a red ball");
	        		 			System.out.println("I spy with my little eye a red ball at "+alTracker.getTargetPosition());
	 							System.out.println("red ball param: "+param);
	 							System.exit(0);
	        		 		}
	        		 		
	        		 		System.out.println("I spy with my little eye a red ball at "+param);
	        		 		
	        		 		
	        		 		if(isRedBallDetected) {
								isRedBallDetected=false;
								tts.say("red ball went bye bye");
								System.out.println("red ball went bye bye");
							}
							

		            }
	        });
	        
	        alMemory.subscribeToEvent("ALTracker/TargetLost", "onTargetLost::(m)", new Object() {
	        	 public void onTargetLost(Object param) throws InterruptedException, CallError 
		            {
	        		 		System.out.println("Target Lost!!");

		            }
	        });
	                
	        
	        alMemory.subscribeToEvent("LeftBumperPressed" , "onEnd::(f)", new Object() {
	            public void onEnd(Float touch) throws InterruptedException, CallError {
	            	if(!isBumperPessed) {
	            		isBumperPessed=true;
		            	uninitialize();
			            isBumperPessed=false;
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
	        
	        // Wake up robot
	        alMotion.wakeUp();
	        
	        //Go to posture stand
	        alPosture.goToPosture("Stand", 0.8f);
	        
	        alBasicAwareness.stopAwareness();
	        
	        
	        // choregraphe example [-self.distanceX, self.distanceY, self.angleWz,
            //self.thresholdX, self.thresholdY, self.thresholdWz]
	        Float[] relPos = {new Float(-0.3f),new Float(0),new Float(0),
	        		new Float(0.1f),new Float(0.1f),new Float(0.1f)};
	        ArrayList<Float> relPosAry = new ArrayList<Float>(Arrays.asList(relPos));
	        
	        // Initialize movement Used for Move and WholeBody
	        alMotion.moveInit();
	        alTracker.setRelativePosition(relPosAry);
	        alTracker.registerTarget("RedBall", 0.10f);
	        alTracker.setMode("Move");
	        alTracker.track("RedBall");
	       
	        
            app.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	public void uninitialize() {
		try {
			if(isRunning) {
				isRunning = false;
				System.out.println("uninitialize called");
				alTracker.stopTracker();
		    	alTracker.unregisterAllTargets();
		    	alPosture.goToPosture("Stand", 0.8f);
		        alBasicAwareness.startAwareness();
		        alMotion.wakeUp();
		        tts.say("Application is stopping");
		        app.stop();
			}
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
