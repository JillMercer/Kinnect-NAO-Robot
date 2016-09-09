package edu.sru.thangiah.nao.movement.limbs;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

public class Hip implements HipInterface 
{
	private static final float HIPMIN = -1.145303f;
	private static final float HIPMAX = 0.740810f;
	private static final float SPEED = 0.1f;
	
	@Override
	public void getHipVal(ALMotion motion) throws CallError, InterruptedException
	{
		motion.getAngles("LHipYawPitch", false).get(0);
	}
	
	@Override
	public void setHipVal(int hipVal, ALMotion motion) throws CallError, InterruptedException
	{
		float hip = hipVal;
		float midVal = 0.0f;
		
		hip = hip/100;
		midVal = (Math.abs(HIPMIN) + Math.abs(HIPMAX))/2;
		
		midVal = HIPMAX - Math.abs(midVal);
		
		hip = ((Math.abs(midVal) + HIPMAX) * hip) - Math.abs(midVal);
			
		motion.setAngles("LHipYawPitch", hip, SPEED);
	}
	
	public void setHipDefault(ALMotion motion) throws CallError, InterruptedException
	{
		motion.setAngles("LHipYawPitch", -0.14518839, SPEED);
	}
}
