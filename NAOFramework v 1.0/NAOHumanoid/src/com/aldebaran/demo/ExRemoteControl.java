package com.aldebaran.demo;

import java.util.List;

import com.aldebaran.qimessaging.Application;
import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.al.ALCloseObjectDetection;
import com.aldebaran.qimessaging.helpers.al.ALInfrared;
import com.aldebaran.qimessaging.helpers.al.ALMemory;
import com.aldebaran.qimessaging.helpers.al.ALRedBallDetection;
import com.aldebaran.qimessaging.helpers.al.ALRedBallTracker;
import com.aldebaran.qimessaging.helpers.al.ALTextToSpeech;
import com.aldebaran.qimessaging.helpers.al.ALTracker;


public class ExRemoteControl {
	
	public static Session session;
	public static Application app;
	public static  Future<Void> future;
	public static ALTextToSpeech tts;
	public static ALMemory alMemory;
	public static ALInfrared lirc;
	public static boolean isRedBallDetected =false;
	public static boolean isBumperPessed = false;

	public static void main(String[] args) throws CallError, InterruptedException {
		// TODO Auto-generated method stub
		session = new Session();
		app = new Application();
        future = null;
       
        
        try {
	        future = session.connect("tcp://"+RobotIP.ip+":"+RobotIP.port);

            synchronized (future) {
                future.wait(1000);
            }
            
            if(!session.isConnected()) {
            	System.out.println("FAILED to connect to robot at IP: "+RobotIP.ip+" Port: "+RobotIP.port);
            	System.exit(1);
            }
	        
	        //tts = new ALTextToSpeech(session);
	        alMemory = new ALMemory(session);
	        lirc = new ALInfrared(session);
	        
	        alMemory.subscribeToEvent("InfraRedRemoteKeyReceived",  "onInfraRedRemoteReceived::(m)", new Object() {
	        	 public void onInfraRedRemoteReceived(String valueStr, Object value, String messageStr) throws InterruptedException, CallError 
		            {
	        		 	System.out.println("Data Key changed at valueStr: "+ valueStr+" value: "+value+" messageStr: "+messageStr);
		            }
	        });
	        
	       
	        
            app.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
	
	//public static void 

}
