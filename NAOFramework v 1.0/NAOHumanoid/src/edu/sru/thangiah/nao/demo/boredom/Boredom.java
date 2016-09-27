package edu.sru.thangiah.nao.demo.boredom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.App;

import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;

public class Boredom
{
	public static ALRobotPosture posture;
	public static ALTextToSpeech tts;
	public static float touch = 0.0f;
	public static Connect connection;
	private static ALMemory memory;
	private static ALSpeechRecognition recog;
	private static volatile boolean boredom;
	private static String[] words = {"Face", "Arm", "Hip", "Leg"};
	private static ArrayList<String> playWords = new ArrayList<String>(Arrays.asList(words));
	private static final float SPEED = 0.5f;
	private static boolean running;
	private static Pose pose = new Pose();
	
	public Boredom(Connect connect) throws Exception
	{
		connection = connect;
		boredom = true;
	}
		
	private static void determineAction() throws Exception
	{
		posture.goToPosture("Stand", SPEED);
		Thread.sleep(200);
		
		tts.say("What would you like to do?");
		
		recog.pause(false);
		
	}
	
	private void isCurious() throws Exception
	{
		recog.pause(true);
		tts.say("Would you like to do something else?");
		Thread.sleep(200);
		playWords.remove(words);
		
		for(int i = 0; i < playWords.size(); i++)
		{
			System.out.println(playWords.get(i));
		}
		
		playWords.add("Yes");
		playWords.add("No");
		
		for(int i = 0; i < playWords.size(); i++)
		{
			System.out.println(playWords.get(i));
		}
		
		recog.setVocabulary(playWords, false);
		recog.pause(false);
	}
	public void onWordRecognized(Object heard) throws Exception 
	{
        String word = (String) ((List<Object>)heard).get(0);
        System.out.println("Word "+heard);

        switch(word)
		{
			case "Face":
				recog.pause(true);
				//Put constructor somewhere else, make visible here, then change running to true
				InterestHead head = new InterestHead(connection.getSession());
				head.setRunning(true);
				running = head.isRunning();
				while(running)
				{
					System.out.println("Head is " + running);
					running = head.isRunning();
				}
				isCurious();
				break;
			
			case "Arm":
				recog.pause(true);
				InterestArm arm = new InterestArm(connection.getSession());
				arm.setRunning(true);
				running = arm.isRunning();
				while(running)
				{
					System.out.println("Arm is " +  running);
					running = arm.isRunning();
				}
				isCurious();
				break;
			
			case "Hip":
				recog.pause(true);
				InterestHip hip = new InterestHip(connection.getSession());
				hip.setRunning(true);
				running = hip.isRunning();
				while(running)
				{
					System.out.println("Hip is " + hip.isRunning());
					running = hip.isRunning();
				}
				isCurious();
				break;
				
			case "Leg":
				recog.pause(true);
				InterestLeg leg = new InterestLeg(connection.getSession());
				leg.setRunning(true);
				running = leg.isRunning();
				while(running)
				{
					System.out.println("Leg is " + leg.isRunning());
					running = leg.isRunning();
				}
				isCurious();
				break;
				
			case "Yes":
				recog.pause(true);
				for(int i = 0; i < words.length; i++)
				{
					playWords.add(words[i]);
				}
				recog.setVocabulary(playWords, false);
				determineAction();
				break;
			
			case "No":
				recog.pause(true);
				boredom = true;
				synchronized (pose)
				{
					pose.notify();
				}
				break;		

			default:
				boredom = true;
				break;
		}
    }
	
	private synchronized void onMiddleTouch(Float touch) throws Exception
	{	
		boredom = false;
		
		if(touch == 1.0f)
		{
			try
			{
				pose.wait();
				System.out.println("Pose is interrupted");
			}
			catch(Exception e)
			{
				pose.interrupt();
			}
			
			boredom = false;
			
			determineAction();
		}
	}
	
	public void run() throws Exception
	{
		connection.run();
	    
		posture = new ALRobotPosture(connection.getSession());
		memory = new ALMemory(connection.getSession());
		tts = new ALTextToSpeech(connection.getSession());
		recog = new ALSpeechRecognition(connection.getSession());
		recog.setVocabulary(playWords, false);
		
		recog.subscribe("Boredom1");
		recog.pause(true);
		
		memory.subscribeToEvent("MiddleTactilTouched", "onMiddleTouch::(f)", this);
		memory.subscribeToEvent("WordRecognized", "onWordRecognized::(m)", this);
		
		posture.goToPosture("Stand", SPEED);
		pose.start();
	}
		
	public static void main(String[] args) throws Exception
	{
		Connect c = new Connect("192.168.0.100");
		Boredom bored = new Boredom(c);
		bored.run();
	}
}

class Pose extends Thread
{
	private static final float SPEED = 0.5f;
	private static String instructions = new String("Touch the middle sensor on my head to do something fun!");
	
	public void run()
	{
		try 
		{
			bored(Boredom.connection.getSession(), Boredom.posture, Boredom.tts);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private void bored(Session session, ALRobotPosture posture, ALTextToSpeech tts) throws Exception
	{
		while(!isInterrupted())
		{
			try
			{
				Thread.sleep(10000);
				goToPose(posture, tts);
			}
			catch(InterruptedException e)
			{
				synchronized(this)
				{
					this.wait();
				}
			}
		}
	}
	
	private static void goToPose(ALRobotPosture posture, ALTextToSpeech tts) throws Exception
	{		
		Random rand = new Random();
		int num = (rand.nextInt(3));
		
		switch(num)
		{
			case 0: posture.goToPosture("Stand", SPEED);
					Thread.sleep(200);
					tts.say(sayRandom());
					break;
			
			case 1: posture.goToPosture("Sit", SPEED);
					Thread.sleep(200);
					tts.say(sayRandom());
					break;
					
			case 2: posture.goToPosture("SitRelax", SPEED);
					Thread.sleep(200);
					tts.say(sayRandom());
					break;
			
			default: posture.goToPosture("Stand", SPEED);
					break;
		}
	}
	
	private static String sayRandom()
	{
		String output = new String("");
		
		Random rand = new Random();
		int num = (rand.nextInt(3));
		
		switch(num)
		{
			case 0: output = "I'm bored!  " + instructions;
					break;
					
			case 1: output = "Let's do something fun!  " + instructions;
					break;
					
			case 2: output = "Would you like to play?  " + instructions;
					break;
					
			default:
					output = "I'm bored!  " + instructions;
					break;
		}
		
		return output;
	}
}
