package com.aldebaran.demo;

import com.aldebaran.qimessaging.Application;
import com.aldebaran.qimessaging.CallError;
import com.aldebaran.qimessaging.Future;
import com.aldebaran.qimessaging.Session;
import com.aldebaran.qimessaging.helpers.al.ALMemory;
import com.aldebaran.qimessaging.helpers.al.ALMotion;
import com.aldebaran.qimessaging.helpers.al.ALSoundLocalization;

import java.util.ArrayList;

/**
 * Created by epinault on 11/05/2014.
 */
public class ExGetAllEvents {

    private static Application application;
    private static boolean isMoving = false;


    private static ALMemory alMemory;
    private static ALSoundLocalization sound;
    private static ALMotion motion;

    public static void main(String[] args) {
        application = new Application();
        Session session = new Session();
        Future<Void> future = null;
        try {
	        future = session.connect("tcp://"+RobotIP.ip+":"+RobotIP.port);

            synchronized (future) {
                future.wait(1000);
            }
            
	        if(session.isConnected()){
	        	System.out.println("Session is Connected!!");
	        }
	        else {
	        	System.out.println("Session is Disconnected!!");
	        }

            alMemory = new ALMemory(session);
            motion = new ALMotion(session);
//            new ALMotion(session).wakeUp();
            
            
            System.out.println("EventList: "+alMemory.getEventList());

            //application.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
