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


public class ExSetPosition{
	
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
		ExSetPosition ex;
		ex = new ExSetPosition();
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
	        
	        System.out.println("Body Names: "+alMotion.getBodyNames("Body"));
	        
	        //ArrayList<Float> temp;
	        //temp = (ArrayList<Float>) alMotion.getPosition("RArm", 0, true);
	        System.out.println("RArm position: "+alMotion.getPosition("RArm", 1, true));
	        
	        //Float[] fRHand = {new Float(0.11877219), new Float(0.13329124), new Float(0.044202648),
	        ///		new Float(0.2169695), new Float(0.41530588), new Float(0.012792875)};
	        
	        //[0.23642616, -0.10755177, 0.41001263, 1.4295573, 0.020576123, -0.02710073]
	        ArrayList<Float> fRHandAry = new ArrayList<Float>();
	        fRHandAry.add(new Float(0.23239836f));
	        fRHandAry.add(new Float(-0.099657305f));
	        fRHandAry.add(new Float(0.37103915f));
	        
	        fRHandAry.add(new Float(0.14110468f));
	        fRHandAry.add(new Float(0.083243676f));
	        fRHandAry.add(new Float(0.05262031f));
	        System.out.println("RArm ArrayList: "+fRHandAry);
	        alMotion.setPositions("RArm", 1, fRHandAry, 0.4f, 7);
	        alMotion.setAngles("RWristYaw", 0.1186824f, 0.4f);
	        
	        System.out.println("RArm position: "+alMotion.getPosition("RArm", 0, true));
	        
            //app.run();
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
