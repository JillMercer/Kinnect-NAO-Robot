package edu.sru.thangiah.nao.movement.limbs.left.arm;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

public interface LeftArmInterface 
{
	/**
	 * Gets the position of all joint values in the left arm and prints them to the console.  The order the values are printed in are as follows: Shoulder Pitch, Shoulder Roll, Elbow Yaw, Elbow Roll, Wrist Yaw.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void getLPosition(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Left Elbow Yaw.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Left Elbow Yaw.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public float getLElbowYaw(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Left Elbow Pitch.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Left Elbow Pitch.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public float getLElbowPitch(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Left Shoulder Pitch.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Left Shoulder Pitch.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public float getLShoulderPitch(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Left Shoulder Roll.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Left Shoulder Roll.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public float getLShoulderRoll(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Left Wrist Yaw.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Left Wrist Yaw.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public float getLWristYaw(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Left Elbow Yaw.
	 * @param x - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public void setLElbowYaw(int x, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Left Elbow Roll.
	 * @param z - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public void setLElbowRoll(int z, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Left Shoulder Pitch.
	 * @param y - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public void setLShoulderPitch(int y, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Left Shoulder Roll.
	 * @param z - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public void setLShoulderRoll(int z, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Left Wrist Yaw.
	 * @param x - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public void setLWristYaw(int x, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets whether the Left Hand is opened or closed.
	 * @param position - accepts either "Open" or "Close"
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public void setLHand(String position, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets all of the joints in the Left Arm to their default values.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */	
	public void setLDefault(ALMotion motion) throws InterruptedException, CallError;
}
