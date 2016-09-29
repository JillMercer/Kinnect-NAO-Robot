package edu.sru.thangiah.nao.movement.head;

//Written by Mike Parnes for educational purposes.
//CPSC 464

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

public interface HeadInterface
{	
	/**
	 * Gets the current position of the head.  The values are a percentage of the total range of motion based on a -100 to 100 scale.  The values are printed in the following order: Head Yaw, Head Pitch.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getPosition(ALMotion motion) throws CallError, InterruptedException;

	/**
	 * Sets the position of the Head.  The values are a percentage of the total range of motion based on a -100 to 100 scale.
	 * @param yawVal - the desired value of the Head Yaw.
	 * @param pitchVal - the desired value of the head pitch.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void setPosition(int yawVal, int pitchVal, ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Sets the value of the Head Yaw.  The values are a percentage of the total range of motion based on a -100 to 100 scale.
	 * @param yawVal - the desired value of the Head Yaw.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void setHeadYaw(int yawVal, ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Sets the value of the Head Pitch.  The values are a percentage of the total range of motion based on a -100 to 100 scale.
	 * @param pitchVal - the desired value of the Head Pitch.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void setHeadPitch(int pitchVal, ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Checks to ensure the head is in a safe position before it attempts to move horizontally.  This prevents collision of joints, and is necessary to check before attempting to move the head horizontally.
	 * @param pitchMax - the maximum range of the Head Pitch
	 * @param pitchMin - the minimum range of the Head Pitch
	 * @param current - the current value of the Head Pitch
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void checkPitchBounds(float pitchMax, float pitchMin, float current, ALMotion motion) throws CallError, InterruptedException;
}
