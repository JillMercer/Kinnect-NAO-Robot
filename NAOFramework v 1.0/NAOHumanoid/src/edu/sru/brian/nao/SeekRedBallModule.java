/**
 * Author: Brian Atwell
 * Date Modified: 10-22-2015
 * 
 * SeekRedBallModule will run track the red ball on the nao robot.
 * The robot will walk towards the red ball and when close enough will
 * stop and hold out his hand and ask for the ball. If the red ball moves
 * far enough away it will go after it unless it is too far away then it will
 * sit until it is close again. 
 */

package edu.sru.brian.nao;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTracker;

import edu.sru.brian.demos.GUITestV3.SeekRedBallActionlistener;
import edu.sru.brian.timer.ResetableActionListener;
import edu.sru.brian.timer.ResetableTimer;
import edu.sru.thangiah.nao.module.AppInterface;

public class SeekRedBallModule implements AppInterface {
	private List<Float> rHandList;
	//private List<Float> rShoulderList;
	private List<Float> redBallList;
	private List<Float> initRHandPos=null;
	private int curState=0;
	private float distRdBlBody=0;
	private int waitCount=0;
	private boolean isRunning=false;
	private boolean isStopped=false;
	private List<StopListener> stopList;
	private long touchEvent;
	
	private RedBallTrackerModule redBallMod;
	
	private ALMotion alMotion;
	private ALTracker alTracker;
	private ALTextToSpeech tts;
	private ALRobotPosture alPosture;
	private ALMemory memory;
	
	private Timer myTimer;
	
	private ResetableTimer seekRedBallTimer;
	
	//private final static List<Float> initRArm;
	private final static List<Float> initRArmWait;
	private final static List<Float> rHandRdBl;
	
	private final static float REDBALL_MAX_TOO_FAR_AWAY = 1.8f;
	private final static float REDBALL_MIN_TOO_FAR_AWAY = 1.7f;
	private final static float REDBALL_CLOSE_ENOUGH = 1.6f;
	
	public final static int SEEK_REDBALL_SPEED = 250;
	
	static {
		
		initRArmWait = new ArrayList<Float>();
		
		rHandRdBl = new ArrayList<Float>();
		
		/*
		initRArmWait.add(0.5232583f);
		initRArmWait.add( 0.13293631f);
		initRArmWait.add(1.5742933f);
		initRArmWait.add(0.68059057f);
		initRArmWait.add( 1.5707961f);
		initRArmWait.add(1.0f);
		*/
		
		initRArmWait.add(0.34557524f);
		initRArmWait.add(-0.18541902f);
		initRArmWait.add(1.5707961f);
		initRArmWait.add(0.41038793f);
		initRArmWait.add(1.5707961f);
		initRArmWait.add(1.0f);
		
		rHandRdBl.add(0.46320325f);
		rHandRdBl.add(-0.3230874f);
		rHandRdBl.add(-0.011997998f);
    }
	
	/**
	 * A class that will run red ball seek code. Can be call by a timer or thread.
	 * The runOnce contains different states in a switch statement.
	 *
	 */
	private class SeekRedBallRunner implements ResetableActionListener, Runnable {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			runOnce();
		}

		@Override
		public void run() {
			
		}
		
		public void runOnce() {
			// TODO Auto-generated method stub
			float distRdBlHand;
			int distRdBlHandInt;
			boolean isTargetLost;
			
			if(isStopped && isRunning)
			{
				isStopped=false;
				onStop();
				return;
			}
			
			try {
				rHandList = alMotion.getPosition("RHand", 0, true);
				redBallList=alTracker.getTargetPosition(0);
				isTargetLost = alTracker.isTargetLost();
				
				
				if(redBallList != null && !redBallList.isEmpty() && !isTargetLost)
				{
					
					distRdBlBody = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), 0.0f,0.0f,0.0f);
					
					//System.out.println("Made it to curState:"+curState);
					
					switch(curState) 
					{
					// State 0
					case 0:
						
						// State 1 Condition
						if(distRdBlBody <= REDBALL_CLOSE_ENOUGH)
						{
							
							redBallMod.setMode("Move");
							
							curState=1;
							waitCount=0;
						}
						break;
					// State 1
					case 1:
						
						// State 2 Condition
						if(distRdBlBody <= .4f)
						{
							initRHandPos = alMotion.getAngles("RArm", true);
							
							redBallMod.setMode("Head");
							
							curState=2;
							waitCount=0;
						}
						// State 0 condition
						else if(distRdBlBody > REDBALL_MAX_TOO_FAR_AWAY)
						{
							
							redBallMod.setMode("Head");
							
							tts.say("The red ball is too far away!");
							
							curState=0;
							waitCount=0;
						}
						break;
					// State 2
					case 2:
						++waitCount;
						
						if(waitCount == 3)
						{
							curState = 3;
							waitCount = 0;
							alMotion.setBreathEnabled("RArm", false);
							//alMotion.setIdlePostureEnabled("RArm", false);
							//alMotion.setStiffnesses("RArm", 1.0f);
							alMotion.setStiffnesses("RWristYaw", 1.0f);
							alMotion.setStiffnesses("RElbowYaw", 1.0f);
							
							
							// Make sure we are not using too much battery power before
							// moving arm to reach for the red ball
							// Go to standing position, which uses less power
							alPosture.goToPosture("Stand", 0.35f);
							Thread.sleep(100);
							
							alMotion.setAngles("RArm", initRArmWait, 0.4f);
							Thread.sleep(250);
							alMotion.setStiffnesses("LArm", 1.0f);
							
							tts.say("Give me the red ball please");
						}
						
						// State 1 condition
						if(distRdBlBody >= .6f && distRdBlBody < REDBALL_MIN_TOO_FAR_AWAY)
						{
							//alMotion.setIdlePostureEnabled("RArm", false);
							alMotion.setAngles("RArm", initRHandPos, 0.25f);
							//alMotion.setStiffnesses("RArm", 0.0f);
							alMotion.setBreathEnabled("RArm", true);
							redBallMod.setMode("Move");
							
							tts.say("I want the red ball");
							curState=1;
						}
						// State 0 condition
						else if(distRdBlBody > REDBALL_MAX_TOO_FAR_AWAY)
						{
							
							redBallMod.setMode("Head");
							
							//alMotion.setIdlePostureEnabled("RArm", false);
							alMotion.setAngles("RArm", initRHandPos, 0.25f);
							//alMotion.setStiffnesses("RArm", 0.0f);
							alMotion.setBreathEnabled("RArm", true);
							
							tts.say("The red ball is too far away!");
							
							curState=0;
							waitCount=0;
						}
						break;
					// State 3
					case 3:
						
						
						distRdBlHand = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rHandRdBl.get(0), rHandRdBl.get(1), rHandRdBl.get(2));
						
						
						//if(distRdBlHandInt <= 10){
						if(distRdBlHand <= 0.1) {
							curState=4;
							//textArea.append("Red Ball in Range!\n\r");
							tts.say("Red ball is in my hand");
						}
						
						// State 1 Condition
						if(distRdBlBody >= .7f && distRdBlBody < REDBALL_MIN_TOO_FAR_AWAY)
						{
							//alMotion.setIdlePostureEnabled("RArm", false);
							alMotion.setAngles("RArm", initRHandPos, 0.25f);
							//alMotion.setStiffnesses("RArm", 0.0f);
							alMotion.setBreathEnabled("RArm", true);
							redBallMod.setMode("Move");
							
							tts.say("I want the red ball");
							curState=1;
						}
						// State 0 condition
						else if(distRdBlBody > REDBALL_MAX_TOO_FAR_AWAY)
						{
							
							redBallMod.setMode("Head");
							
							//alMotion.setIdlePostureEnabled("RArm", false);
							alMotion.setAngles("RArm", initRHandPos, 0.25f);
							//alMotion.setStiffnesses("RArm", 0.0f);
							alMotion.setBreathEnabled("RArm", true);
							
							tts.say("The red ball is too far away!");
							
							curState=0;
							waitCount=0;
						}
						break;
						
					// State 4 
					case 4:
						//distRdBlHand = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rHandList.get(0), rHandList.get(1), rHandList.get(2));
						distRdBlHand = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rHandRdBl.get(0), rHandRdBl.get(1), rHandRdBl.get(2));
						
						//dataTextField.setText(""+distRdBlHand);
						
						if(distRdBlHand > 0.2f && distRdBlBody < .7f){
							curState=3;
							tts.say("Red Ball is not in my hand");
						}
						
						if(distRdBlBody >= .7f && distRdBlBody < REDBALL_MIN_TOO_FAR_AWAY)
						{
							alMotion.setAngles("RArm", initRHandPos, 0.25f);
							alMotion.setBreathEnabled("RArm", true);
							redBallMod.setMode("Move");
							
							tts.say("I want the red ball");
							curState=1;
						}
						// State 0 condition
						else if(distRdBlBody > REDBALL_MAX_TOO_FAR_AWAY)
						{
							
							redBallMod.setMode("Head");
							
							alMotion.setAngles("RArm", initRHandPos, 0.25f);
							alMotion.setBreathEnabled("RArm", true);
							
							tts.say("The red ball is too far away!");
							
							curState=0;
							waitCount=0;
						}
						break;
					}
				}
				else
				{
					// Lost track of the red ball
					//System.out.println("Reached lost state!");
					if(curState != 0)
					{
						redBallMod.setMode("Head");
						
						tts.say("I do not see the red ball!");
						
						curState=0;
						waitCount=0;
					}
				}
				
			} catch (CallError | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public void setTimer(Timer newTimer) {
			myTimer = newTimer;
		}
		
		public void reset() {
			curState=0;
		}
		
	}
	
	public SeekRedBallModule(Session session)
	{
		stopList = new ArrayList<StopListener>();
		SeekRedBallRunner seekActionListener;
		seekActionListener = new SeekRedBallRunner();
		
		seekRedBallTimer = new ResetableTimer(SEEK_REDBALL_SPEED, seekActionListener);
		
		seekActionListener.setTimer(seekRedBallTimer);
		
		seekRedBallTimer.setCoalesce(true);
		
		initialize(session);
	}
	
	/**
	 * Set the timer of our class
	 * @param newTimer
	 */
	public void setTimer(Timer newTimer) {
		myTimer = newTimer;
	}
	
	/**
	 * Setup the reset our module
	 * reset the current state
	 */
	public void reset() {
		curState=0;
	}

	/**
	 * Initialize Red ball module
	 */
	public void initialize(Session session) {
		// TODO Auto-generated method stub
		redBallMod = new RedBallTrackerModule(session);
		
		try {
			alMotion = new ALMotion(session);
			alTracker = new ALTracker(session);
			tts = new ALTextToSpeech(session);
			alPosture = new ALRobotPosture(session);
			memory = new ALMemory(session);
			touchEvent=memory.subscribeToEvent("TouchChanged",
			        "onTouched::(m)", this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	*/

	/**
	 * Stop the module
	 */
	public void stop() {
		// TODO Auto-generated method stub
		if(isRunning)
		{
			isRunning=false;
			/*
			try {
				memory.unsubscribeToEvent(touchEvent);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CallError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			stopTimed();
		}
	}
	
	/**
	 * Stop timed version
	 */
	private void stopTimed()
	{
		seekRedBallTimer.stop();
		redBallMod.stop();
	}

	/**
	 * Start the module
	 */
	public void run() {
		// TODO Auto-generated method stub
		if(!isRunning)
		{
			isRunning=true;
			redBallMod.setMode("Head");
			startTimed();
		}
	}
	
	/**
	 * Start timer call
	 */
	private void startTimed() {
		redBallMod.run();
		
		seekRedBallTimer.reset();
		seekRedBallTimer.setInitialDelay(SEEK_REDBALL_SPEED);
		seekRedBallTimer.setCoalesce(true);
		seekRedBallTimer.start();
	}


	/**
	 * Uninitialize the module
	 */
	public void exit() {
		// TODO Auto-generated method stub
		try {
			memory.unsubscribeToEvent(touchEvent);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CallError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		redBallMod.exit();
	}
	
	/**
	 * Get the redballtrackermodule
	 * @return
	 */
	public RedBallTrackerModule getRedBallTracker() {
		return redBallMod;
	}
	
	/**
	 * Get the redballtrackermodule
	 * @param redballMod
	 */
	public void setRedBallTracker(RedBallTrackerModule redballMod) {
		this.redBallMod = redballMod;		
	}
	
	/**
	 * If running return true, else return false.
	 * @return
	 */
	public boolean isRunning() {
		return isRunning;
	}
	
	public void onTouched(Object obj)
	{
		if(isRunning)
		{
			isStopped=true;
			System.out.println("Touched!");
		}
	}
	
	private void onStop()
	{
		try {
			tts.say("I am done playing now.");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Stopped");
		stop();
		
		for(StopListener l:stopList)
		{
			l.onStop();
		}
	}
	
	public void addStopListener(StopListener listener)
	{
		stopList.add(listener);
	}
	
	public void removeStopListener(StopListener listener)
	{
		stopList.remove(listener);
	}
}
