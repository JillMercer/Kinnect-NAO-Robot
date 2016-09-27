package com.aldebaran.demo;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import com.aldebaran.qimessaging.helpers.al.ALTracker;


public class ExRunProgramOnRedBall {
	
	public Session session;
	public Application app;
	public Future<Void> future;
	public ALTextToSpeech tts;
	public ALMemory alMemory;
	public ALTracker alTracker;
	public boolean isRedBallDetected =false;
	public boolean isBumperPessed = false;

	public static void main(String[] args) throws CallError, InterruptedException, IOException {
		ExRunProgramOnRedBall mainapp = new ExRunProgramOnRedBall();
		mainapp.runMainProgram();

	}
	
	public void runMainProgram() {
		while(true) {
			runProgramOnRedBall();
			try {
				startProgram();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void uninitializeNAO() throws CallError, InterruptedException {
		alTracker.stopTracker();
		app.stop();
		alTracker=null;
		tts=null;
		alMemory=null;
		session=null;
		future=null;
	}
	
	public void runProgramOnRedBall() {
		// TODO Auto-generated method stub

		session = new Session();
		app = new Application();
		future = null;
		isRedBallDetected=false;
		
		System.out.println("Attempting to create a new session with the robot.");

		try {
			future = session.connect("tcp://" + RobotIP.ip + ":" + RobotIP.port);

			synchronized (future) {
				future.wait(1000);
			}
			
			if(!session.isConnected()) {
				System.out.println("FAILED to connect to Robot!!!");
				System.exit(1);
			}

			tts = new ALTextToSpeech(session);
			alMemory = new ALMemory(session);
			alTracker = new ALTracker(session);

			alMemory.subscribeToEvent("redBallDetected", "onBallDetected::(m)",
					new Object() {
						public void onBallDetected(Object param) throws InterruptedException, CallError {
							if (!isRedBallDetected) {
								isRedBallDetected = true;
								// tts.say("I spy with my little eye a red ball");
								System.out.println("I spy with my little eye a red ball at "+ alTracker.getTargetPosition());
								uninitializeNAO();
							}

						}
					});

			alTracker.addTarget("RedBall", 0.10f);
			alTracker.setMode("Head");
			alTracker.track("RedBall");

			app.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startProgram() throws IOException, InterruptedException {
		List<String> command = new ArrayList<String>();
	    command.add("java");
	    command.add("-cp");
	    command.add("lib\\jnaoqi-windows32-2.1.0.19.jar;bin\\Test.jar");
	    command.add("com.aldebaran.demo.ExDanceImediately");

	    ProcessBuilder builder = new ProcessBuilder(command);
	    //Map<String, String> environ = builder.environment();
	    builder.directory(new File("F:\\College\\2015\\Spring2015\\CPSC374\\Data Structures 2015  Workspace\\Nao Robot Examples"));

	    System.out.println("Directory : " + System.getenv("temp") );
	    final Process process = builder.start();
	    InputStream is = process.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String line;
	    while ((line = br.readLine()) != null) {
	      System.out.println(line);
	    }
		System.out.println("External Program Done");
	}
	
	

}
