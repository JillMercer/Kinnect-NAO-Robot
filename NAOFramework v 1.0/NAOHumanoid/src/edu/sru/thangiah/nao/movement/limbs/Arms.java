package edu.sru.thangiah.nao.movement.limbs;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.movement.limbs.left.arm.LeftArmInterface;
import edu.sru.thangiah.nao.movement.limbs.right.arm.RightArmInterface;

public class Arms implements LeftArmInterface, RightArmInterface
{
	private static final float RSHOULDERPITCH = 2.0857f;
	private static final float RSHOULDERROLLMAX = 0.3142f;
	private static final float RSHOULDERROLLMIN = -1.3265f;
	private static final float RELBOWYAW = 2.0857f;
	private static final float RELBOWROLLMAX = 1.5446f;
	private static final float RELBOWROLLMIN = 0.0349f;
	private static final float RWRISTYAW = 1.8238f;
	private static final float LSHOULDERPITCH = 2.0857f;
	private static final float LSHOULDERROLLMAX = 1.3265f;
	private static final float LSHOULDERROLLMIN = -0.3142f;
	private static final float LELBOWYAW = 2.0857f;
	private static final float LELBOWROLLMAX = -0.0349f;
	private static final float LELBOWROLLMIN = -1.5446f;
	private static final float LWRISTYAW = 1.8238f;
	private final float SPEED = 0.1f;
	
	public void setLDefualt(ALMotion motion) throws InterruptedException, CallError
	{
		motion.setAngles("LShoulderPitch", 0.0f, SPEED);
		motion.setAngles("LShoulderRoll", 0.50615f, SPEED);
		motion.setAngles("LElbowYaw", 0.0f, SPEED);
		motion.setAngles("LElbowRoll", -0.82465f, SPEED);
		motion.setAngles("LWristYaw", 0.0f, SPEED);
		motion.closeHand("LHand");
	}

	public void getLPosition(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Left Shoulder Pitch = " + motion.getAngles("LShoulderPitch", false).get(0));
		System.out.println("Left Shoulder Roll = " + motion.getAngles("LShoulderRoll", false).get(0));
		System.out.println("Left Elbow Yaw = " + motion.getAngles("LElbowYaw", false).get(0));
		System.out.println("Left Elbow Roll = " + motion.getAngles("LElbowRoll", false).get(0));
		System.out.println("Left Wrist Yaw = " + motion.getAngles("LWristYaw", false).get(0));
	}
	
	public float getLElbowYaw(ALMotion motion) throws InterruptedException, CallError
	{
		return motion.getAngles("LElbowYaw", false).get(0);
	}
	
	public float getLElbowPitch(ALMotion motion) throws InterruptedException, CallError
	{
		return motion.getAngles("LElbowPitch", false).get(0);
	}

	public float getLShoulderPitch(ALMotion motion) throws InterruptedException, CallError
	{
		return motion.getAngles("LShoulderPitch", false).get(0);
	}

	public float getLShoulderRoll(ALMotion motion) throws InterruptedException, CallError
	{
		return motion.getAngles("LShoulderRoll", false).get(0);
	}
	
	public float getLWristYaw(ALMotion motion) throws InterruptedException, CallError
	{
		return motion.getAngles("LWristYaw", false).get(0);
	}
	
	public void getRPosition(ALMotion motion) throws InterruptedException, CallError 
	{
		System.out.println("Right Shoulder Pitch = " + motion.getAngles("RShoulderPitch", false).get(0));
		System.out.println("Right Shoulder Roll = " + motion.getAngles("RShoulderRoll", false).get(0));
		System.out.println("Right Elbow Yaw = " + motion.getAngles("RElbowYaw", false).get(0));
		System.out.println("Right Elbow Roll = " + motion.getAngles("RElbowRoll", false).get(0));
		System.out.println("Right Wrist Yaw = " + motion.getAngles("RWristYaw", false).get(0));
	}

	public float getRElbowYaw(ALMotion motion) throws InterruptedException, CallError 
	{
		return motion.getAngles("RElbowYaw", false).get(0);
	}

	public float getRElbowPitch(ALMotion motion) throws InterruptedException, CallError 
	{
		return motion.getAngles("RElbowPitch", false).get(0);
	}

	public float getRShoulderPitch(ALMotion motion) throws InterruptedException, CallError 
	{
		return motion.getAngles("RShoulderPitch", false).get(0);
	}

	public float getRShoulderRoll(ALMotion motion) throws InterruptedException, CallError 
	{
		return motion.getAngles("RShoulderRoll", false).get(0);
	}

	public float getRWristYaw(ALMotion motion) throws InterruptedException, CallError 
	{
		return motion.getAngles("RWristYaw", false).get(0);
	}

	public void setRElbowYaw(int x, ALMotion motion) throws InterruptedException, CallError 
	{
		float elbowYaw = x;
		
		elbowYaw = elbowYaw/100;
		elbowYaw = elbowYaw * RELBOWYAW;
		
		motion.setAngles("RElbowYaw", elbowYaw, SPEED);
		Thread.sleep(500);
	}

	public void setRElbowRoll(int z, ALMotion motion) throws InterruptedException, CallError 
	{
		float elbowRoll = z;
		float midRoll = (Math.abs(RELBOWROLLMIN) + Math.abs(RELBOWROLLMAX))/2;
		
		midRoll = RELBOWROLLMAX - midRoll;
				
		elbowRoll = elbowRoll/100;
				
		elbowRoll = ((Math.abs(midRoll) + RELBOWROLLMAX) * elbowRoll) - Math.abs(midRoll);
			
		motion.setAngles("RElbowRoll", elbowRoll, SPEED);
	}

	public void setRShoulderPitch(int y, ALMotion motion) throws InterruptedException, CallError 
	{
		float shoulderPitch = y;
		
		shoulderPitch = shoulderPitch/100;
		shoulderPitch = shoulderPitch * RSHOULDERPITCH;
		
		motion.setAngles("RShoulderPitch", shoulderPitch, SPEED);
		Thread.sleep(500);
	}

	public void setRShoulderRoll(int z, ALMotion motion) throws InterruptedException, CallError 
	{
		float shoulderRoll = z;
		float midRoll = (Math.abs(RSHOULDERROLLMIN) + Math.abs(RSHOULDERROLLMAX))/2;
		
		midRoll = RSHOULDERROLLMAX - midRoll;
				
		shoulderRoll = shoulderRoll/100;
				
		shoulderRoll = ((Math.abs(midRoll) + RSHOULDERROLLMAX) * shoulderRoll) - Math.abs(midRoll);
			
		motion.setAngles("RShoulderRoll", shoulderRoll, SPEED);
	}

	public void setRWristYaw(int x, ALMotion motion) throws InterruptedException, CallError 
	{
		float wristYaw = x;
		
		wristYaw = wristYaw/100;
		wristYaw = wristYaw * RWRISTYAW;
		
		motion.setAngles("RWristYaw", wristYaw, SPEED);
		Thread.sleep(500);
	}

	public void setRHand(String position, ALMotion motion) throws InterruptedException, CallError 
	{
		if(position.equals("Open"))
		{
			motion.openHand("RHand");
		}
		else if(position.equals("Close"))
		{
			motion.closeHand("RHand");
		}
		else
		{
			System.out.println("Input not valid.  Please enter either 'Open' or 'Close'.");
		}
	}

	public void setRDefault(ALMotion motion) throws InterruptedException, CallError 
	{
		motion.setAngles("RShoulderRoll", -0.17714359f, SPEED);
		motion.setAngles("RElbowRoll", 0.5672885f, SPEED);
		setRShoulderPitch(0, motion);
		setRElbowYaw(0, motion);
		setRWristYaw(0, motion);
		setRHand("Close", motion);
	}

	public void setLElbowYaw(int x, ALMotion motion) throws InterruptedException, CallError 
	{
		float elbowYaw = x;
		
		elbowYaw = elbowYaw/100;
		elbowYaw = elbowYaw * LELBOWYAW;
		
		motion.setAngles("LElbowYaw", elbowYaw, SPEED);
		Thread.sleep(500);
	}

	public void setLElbowRoll(int z, ALMotion motion) throws InterruptedException, CallError 
	{
		float elbowRoll = z;
		float midRoll = (Math.abs(LELBOWROLLMIN) + Math.abs(LELBOWROLLMAX))/2;
		
		midRoll = LELBOWROLLMAX - midRoll;
				
		elbowRoll = elbowRoll/100;
				
		elbowRoll = ((Math.abs(midRoll) + LELBOWROLLMAX) * elbowRoll) - Math.abs(midRoll);
			
		motion.setAngles("LElbowRoll", elbowRoll, SPEED);
	}

	public void setLShoulderPitch(int y, ALMotion motion) throws InterruptedException, CallError 
	{
		float shoulderPitch = y;
		
		shoulderPitch = shoulderPitch/100;
		shoulderPitch = shoulderPitch * LSHOULDERPITCH;
		
		motion.setAngles("LShoulderPitch", shoulderPitch, SPEED);
		Thread.sleep(500);
	}

	public void setLShoulderRoll(int z, ALMotion motion) throws InterruptedException, CallError 
	{
		float shoulderRoll = z;
		float midRoll = (Math.abs(LSHOULDERROLLMIN) + Math.abs(LSHOULDERROLLMAX))/2;
		
		midRoll = LSHOULDERROLLMAX - midRoll;
				
		shoulderRoll = shoulderRoll/100;
				
		shoulderRoll = ((Math.abs(midRoll) + LSHOULDERROLLMAX) * shoulderRoll) - Math.abs(midRoll);
			
		motion.setAngles("LShoulderRoll", shoulderRoll, SPEED);
		
	}

	public void setLWristYaw(int x, ALMotion motion) throws InterruptedException, CallError 
	{
		float wristYaw = x;
		
		wristYaw = wristYaw/100;
		wristYaw = wristYaw * LWRISTYAW;
			
		motion.setAngles("LWristYaw", wristYaw, SPEED);
		Thread.sleep(500);
	}
	
	public void setLHand(String position, ALMotion motion) throws InterruptedException, CallError 
	{
		if(position.equals("Open"))
		{
			motion.openHand("LHand");
		}
		else if(position.equals("Close"))
		{
			motion.closeHand("LHand");
		}
		else
		{
			System.out.println("Input not valid.  Please enter either 'Open' or 'Close'.");
		}
	}

	public void setLDefault(ALMotion motion) throws InterruptedException, CallError 
	{
		motion.setAngles("LShoulderRoll", 0.50615f, SPEED);
		motion.setAngles("LElbowRoll", -0.82465f, SPEED);
		setLShoulderPitch(0, motion);
		setLElbowYaw(0, motion);
		setLWristYaw(0, motion);
		setLHand("Close", motion);
	}
}
