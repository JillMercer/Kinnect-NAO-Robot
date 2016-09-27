package edu.sru.thangiah.nao.movement.limbs;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

public interface HipInterface 
{
	/**
	 * Gets the value of the Hips.  The value is a percentage of the total range of motion based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void getHipVal(ALMotion motion) throws CallError, InterruptedException;
	
	/**
	 * Sets the position of the Hips.
	 * @param hipVal - a percentage of the total range of motion based on a -100 to 100 scale.
	 * @param motion - an instance of the motion class being used.
	 * @throws CallError
	 * @throws InterruptedException
	 */
	public void setHipVal(int hipVal, ALMotion motion) throws CallError, InterruptedException;
}
