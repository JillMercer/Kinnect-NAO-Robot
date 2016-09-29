package edu.sru.thangiah.nao.movement.head;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

public class Head implements HeadInterface
{
	protected static float yawMin = -2.0857f;
	protected static float yawMax = 2.0857f;
	protected static float pitchMin = -0.6720f;
	protected static float pitchMax = 0.5149f;
	protected static final float SPEED = 0.1f;
		
	public void getPosition(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Head Yaw = " + (motion.getAngles("HeadYaw", false).get(0)));
		System.out.println("Head Pitch = " + (motion.getAngles("HeadPitch", false).get(0)));
	}
	
	
	public void setPosition(int yawVal, int pitchVal, ALMotion motion) throws CallError, InterruptedException
	{
		setHeadYaw(yawVal, motion);
		setHeadPitch(pitchVal, motion);
	}
	
	public void setHeadYaw(int yawVal, ALMotion motion) throws CallError, InterruptedException
	{	
		float newYaw = 0.0f;
		
		motion.setStiffnesses("Head", 1.0f);
		checkPitchBounds(pitchMin, pitchMax, motion.getAngles("HeadPitch", false).get(0), motion);
		
		newYaw = (yawVal/100) * yawMax;
		
		while(motion.getAngles("HeadYaw", false).get(0) != newYaw)
		{
			motion.setAngles("HeadYaw", newYaw, SPEED);
			Thread.sleep(500);
			System.out.println("Trying.");
		}
		
		System.out.println("Head moved.");
		motion.setStiffnesses("HeadYaw", 0.0f);
	}
	
	public void setHeadPitch(int pitchVal, ALMotion motion) throws CallError, InterruptedException
	{
		float newPitch;
		float midVal;
	
		midVal = Math.abs(pitchMin) + pitchMax;
		midVal = midVal/2;
		midVal = pitchMin + midVal;
		newPitch = pitchVal/100;					
		newPitch = ((Math.abs(midVal) + pitchMax) * pitchVal) + midVal;
		
		while(motion.getAngles("HeadPitch", false).get(0) != newPitch)
		{
			motion.setAngles("HeadPitch", newPitch, SPEED);
			Thread.sleep(500);
		}
	}
	
	public void checkPitchBounds(float pitchMax, float pitchMin, float current, ALMotion motion) throws InterruptedException, CallError
	{					
		if(current < pitchMin)
		{
			while(motion.getAngles("HeadPitch", false).get(0) != pitchMin)
			{
				motion.setAngles("HeadPitch", pitchMin, SPEED);
				Thread.sleep(3000);
			}
		}
		else if (current > pitchMax)
		{
			while(motion.getAngles("HeadPitch", false).get(0) != pitchMax)
			{
				motion.setAngles("HeadPitch", pitchMax, SPEED);
				Thread.sleep(3000);
			}
		}
	}
}
