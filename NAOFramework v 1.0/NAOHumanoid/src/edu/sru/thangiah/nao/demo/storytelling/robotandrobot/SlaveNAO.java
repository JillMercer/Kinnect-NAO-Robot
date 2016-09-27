package edu.sru.thangiah.nao.demo.storytelling.robotandrobot;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALLeds;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.DebugSettings;

public class SlaveNAO {

	public static String PORT = "9559";
	
	private ALLeds leds;
	private ALRobotPosture posture;
	private ALMotion motion;
	private ALTextToSpeech tts;
	private Session session;
	private String naoName;
	private boolean isHandRaised;
	private boolean isActive;
	
	private static String[] phrases = {"I choose option ", "How about option ", "Lets go with option "};
	
	public SlaveNAO(String name, String ip)
	{
		try
		{
			naoName = name;
			isHandRaised = false;
			isActive = false;
			
			session = new Session("tcp://" + ip + ":" + PORT);
			tts = new ALTextToSpeech(session);
			motion = new ALMotion(session);
			posture = new ALRobotPosture(session);
			leds = new ALLeds(session);
		}
		catch(Exception e)
		{
			DebugSettings.printDebug(DebugSettings.ERROR, "Error from " + naoName + " " + e.getMessage());
		}
	}
	
	/** Gets the name of this slave Nao.
	 * @return The name of the slave.
	 */
	public String getName()
	{
		return naoName;
	}
	
	/** Gets whether the Nao is active.
	 * @return True if Nao is active, false if not.
	 */
	public boolean isActive()
	{
		return isActive;
	}
	
	/** Gets whether this slave Nao has its hand raised.
	 * @return True if hand raised, false if not.
	 */
	public boolean isHandRaised()
	{
		return isHandRaised;
	}
	
	/**
	 * Tell the Nao to raise its hand.
	 */
	public void raiseHand()
	{
		try {	
			leds.async().rasta(10.0f);
			Thread.sleep(500);
			motion.setBreathEnabled("All", false);
			Thread.sleep(500);
			motion.setStiffnesses("RArm", 1.0f);
			Thread.sleep(500);
			motion.setAngles("RShoulderPitch", -1.0f, 0.5f);
			Thread.sleep(500);
			motion.setAngles("RWristYaw", -1.0f, 0.5f);
			Thread.sleep(500);
			motion.openHand("RHand");
			Thread.sleep(500);	
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		isHandRaised = true;
		DebugSettings.printDebug(DebugSettings.COMMENT, naoName + " raised hand.");
	}
	
	/**
	 * Tells the Nao to lower its hand.
	 */
	public void lowerHand()
	{
		try {
			motion.closeHand("RHand");
			Thread.sleep(500);
			posture.goToPosture("Stand", 0.8f);
			Thread.sleep(500);
			motion.setBreathEnabled("All", true);
			motion.wakeUp();
			leds.reset("FaceLeds");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		isHandRaised = false;
		DebugSettings.printDebug(DebugSettings.COMMENT, naoName + " lowered hand.");
	}
	
	/** Have the Slave Nao choose an option.
	 * @param max The highest numbered option.
	 * @return The random number that the slave chose.
	 */
	public int pickOption(int max)
	{	
		try 
		{
			int choose = Dice.roll(max);
			choose++; // so that options line up properly. i.e. it doesn't say option 0.
			String phrase = phrases[Dice.roll(phrases.length)] + choose + " please.";
			
			leds.fadeRGB("FaceLeds", "green", 0.0f);
			tts.say(phrase);
			leds.reset("FaceLeds");
			
			DebugSettings.printDebug(DebugSettings.COMMENT, "From " + naoName + ": " + phrase);
			choose--;
			return choose;
		} 
		catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, "Error from " + naoName + " " + e.getMessage());
			return -1;
		}
	}
}
