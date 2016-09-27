package edu.sru.thangiah.nao.movement.limbs.left.leg;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.movement.balance.Balance;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

public interface LeftLegInterface 
{
	/**
	 * Gets the current value of the Left Hip Roll, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getLHipRoll(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of the Left Hip Pitch, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getLHipPitch(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of the Left Knee Pitch, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getLKneePitch(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of the Left Ankle Pitch, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getLAnklePitch(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of the Left Ankle Roll, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getLAnkleRoll(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of all joints in the left leg, based on a -100 to 100 scale.  Prints the values to the console.  The values are a representation of a percentage of the limb's range of motion.  The order of values are Hip Roll, Hip Pitch, Knee Pitch, Ankle Pitch, Ankle Roll.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getLLegPosition(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Sets the value of the Left Hip Roll.
	 * @param roll - an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */
	public void setLHipRoll(int roll, ALMotion motion, Balance balance) throws Exception;
	
	/**
	 * Sets the value of the Left Hip Pitch.
	 * @param pitch - an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */
	public void setLHipPitch(int pitch, ALMotion motion, Balance balance) throws Exception;
	
	/**
	 * Sets the value of the Left Knee Pitch.
	 * @param pitch - an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */
	public void setLKneePitch(int pitch, ALMotion motion, Balance balance) throws Exception;
	
	/**
	 * Sets the value of the Left Ankle Pitch.
	 * @param pitch - an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */
	public void setLAnklePitch(int pitch, ALMotion motion, Balance balance) throws Exception;
	
	/**
	 * Sets the value of the Left Ankle Roll.
	 * @param roll -  an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */
	public void setLAnkleRoll(int roll, ALMotion motion, Balance balance) throws Exception;
	
	/**
	 * Sets the left leg to the default position.
	 * @param motion - an instance of the motion class being used
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void setLLegDefualt(ALMotion motion) throws CallError, InterruptedException;
}
