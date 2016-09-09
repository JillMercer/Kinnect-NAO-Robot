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


public class ExGetAngle{
	
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
		ExGetAngle ex;
		ex = new ExGetAngle();
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
	        

	        System.out.println("RArm Angle: "+alMotion.getAngles("RArm", false));
	       
	        
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
