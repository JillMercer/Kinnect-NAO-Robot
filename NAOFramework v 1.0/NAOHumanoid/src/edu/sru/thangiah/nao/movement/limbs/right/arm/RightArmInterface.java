package edu.sru.thangiah.nao.movement.limbs.right.arm;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

public interface RightArmInterface 
{
	/**
	 * Gets the position of all joint values in the right arm and prints them to the console.  The order the values are printed in are as follows: Shoulder Pitch, Shoulder Roll, Elbow Yaw, Elbow Roll, Wrist Yaw.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void getRPosition(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Right Elbow Yaw.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Right Elbow Yaw.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public float getRElbowYaw(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Right Elbow Pitch.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Right Elbow Pitch.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public float getRElbowPitch(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Right Shoulder Pitch.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Right Shoulder Pitch.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public float getRShoulderPitch(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Right Shoulder Roll.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Right Shoulder Roll.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public float getRShoulderRoll(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Gets the value of the Right Wrist Yaw.
	 * @param motion - an instance of the motion class being used.
	 * @return Returns the value of the Right Wrist Yaw.  The value is representative of a percentage of the total range on a scale from -100 to 100.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public float getRWristYaw(ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Right Elbow Yaw.
	 * @param x - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void setRElbowYaw(int x, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Right Elbow Roll.
	 * @param z - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void setRElbowRoll(int z, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Right Shoulder Pitch.
	 * @param y - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void setRShoulderPitch(int y, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Right Shoulder Roll.
	 * @param z - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void setRShoulderRoll(int z, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets the value of the Right Wrist Yaw.
	 * @param x - the percentage of the total range of motion desired based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void setRWristYaw(int x, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets whether the Right Hand is opened or closed.
	 * @param position - accepts either "Open" or "Close"
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void setRHand(String position, ALMotion motion) throws InterruptedException, CallError;
	
	/**
	 * Sets all of the joints in the Right Arm to their default values.
	 * @param motion - an instance of the motion class being used.
	 * @throws InterruptedException
	 * @throws CallError
	 */
	public void setRDefault(ALMotion motion) throws InterruptedException, CallError;
}
