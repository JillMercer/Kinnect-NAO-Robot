package edu.sru.thangiah.nao.movement.limbs.right.leg;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.movement.balance.Balance;

public interface RightLegInterface 
{
	/**
	 * Gets the current value of the Right Hip Roll, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getRHipRoll(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of the Right Hip Pitch, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */	
	public void getRHipPitch(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of the Right Knee Pitch, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */	
	public void getRKneePitch(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of the Right Ankle Pitch, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */	
	public void getRAnklePitch(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of the Right Ankle Roll, based on a -100 to 100 scale.  Prints the value to the console.  The value is a representation of a percentage of the limb's range of motion.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */	
	public void getRAnkleRoll(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Gets the current value of all joints in the right leg, based on a -100 to 100 scale.  Prints the values to the console.  The values are a representation of a percentage of the limb's range of motion.  The order of values are Hip Roll, Hip Pitch, Knee Pitch, Ankle Pitch, Ankle Roll.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getRLegPosition(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Sets the value of the Right Hip Roll.
	 * @param roll - an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */	
	public void setRHipRoll(int rolll, ALMotion motion, Balance balance) throws CallError, InterruptedException;
	
	/**
	 * Sets the value of the Right Hip Pitch.
	 * @param pitch - an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */	
	public void setRHipPitch(int pitch, ALMotion motion, Balance balance) throws CallError, InterruptedException;
	
	/**
	 * Sets the value of the Right Knee Pitch.
	 * @param pitch - an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */	
	public void setRKneePitch(int pitch, ALMotion motion, Balance balance) throws CallError, InterruptedException;
	
	/**
	 * Sets the value of the Right Ankle Pitch.
	 * @param pitch - an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */	
	public void setRAnklePitch(int pitch, ALMotion motion, Balance balance) throws CallError, InterruptedException;
	
	/**
	 * Sets the value of the Right Ankle Roll.
	 * @param roll -  an integer value that represents a percentage of the limb's total range of value on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @param balance - an instance of balance being used.  Balance must always be maintained when moving the legs to ensure fall prevention.
	 * @throws Exception
	 */	
	public void setRAnkleRoll(int roll, ALMotion motion, Balance balance) throws CallError, InterruptedException;
	
	/**
	 * Sets the right leg to the default position.
	 * @param motion - an instance of the motion class being used
	 * @throws CallError
	 * @throws InterruptedException
	 */	
	public void setRLegDefualt(ALMotion motion) throws CallError, InterruptedException;
}
