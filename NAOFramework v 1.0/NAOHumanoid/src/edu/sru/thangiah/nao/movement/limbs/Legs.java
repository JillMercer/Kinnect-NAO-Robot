package edu.sru.thangiah.nao.movement.limbs;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.movement.balance.Balance;
import edu.sru.thangiah.nao.movement.limbs.left.leg.LeftLegInterface;
import edu.sru.thangiah.nao.movement.limbs.right.leg.RightLegInterface;

public class Legs implements LeftLegInterface, RightLegInterface 
{
	private static final float SPEED = 0.1f;
	private static final float LHIPROLLMAX = 0.790477f;
	private static final float LHIPROLLMIN = -0.379472f;
	private static final float LHIPPITCHMAX = 0.484090f;
	private static final float LHIPPITCHMIN = -1.535889f;
	private static final float LKNEEPITCHMAX = 2.112528f;
	private static final float LKNEEPITCHMIN = -0.092346f;
	private static final float LANKLEPITCHMAX = 0.922747f;
	private static final float LANKLEPITCHMIN = -1.189516f;
	private static final float RHIPROLLMAX = 0.790477f;
	private static final float RHIPROLLMIN = -0.379472f;
	private static final float RHIPPITCHMAX = 0.484090f;
	private static final float RHIPPITCHMIN = -1.535889f;
	private static final float RKNEEPITCHMAX = 2.112528f;
	private static final float RKNEEPITCHMIN = -0.092346f;
	private static final float RANKLEPITCHMAX = 0.922747f;
	private static final float RANKLEPITCHMIN = -1.189516f;
	
	
	public void getRHipRoll(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Right Hip Roll = " + motion.getAngles("RHipRoll", false).get(0));		
	}

	
	public void getRHipPitch(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Right Hip Pitch = " + motion.getAngles("RHipPitch", false).get(0));		
	}
	
	
	public void getRKneePitch(ALMotion motion) throws CallError, InterruptedException 
	{
		System.out.println("Right Knee Pitch = " + motion.getAngles("RKneePitch", false).get(0));		
	}

	
	public void getRAnklePitch(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Right Ankle Pitch = " + motion.getAngles("RAnklePitch", false).get(0));		
	}
	
	public void getRAnkleRoll(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Right Ankle Roll = " + motion.getAngles("RAnkleRoll", false).get(0));		
	}

	public void setRHipRoll(int roll, ALMotion motion, Balance balance) throws CallError, InterruptedException
	{
		float hipRoll = roll;
		float midRoll = (Math.abs(RHIPROLLMIN) + Math.abs(RHIPROLLMAX))/2;
		
		hipRoll = hipRoll/100;
		
		midRoll = RHIPROLLMAX - midRoll;
		
		hipRoll = ((Math.abs(midRoll) + RHIPROLLMAX) * hipRoll) - Math.abs(midRoll);
		
		motion.setAngles("RHipRoll", hipRoll, SPEED);
	}
	
	public void setRHipPitch(int pitch, ALMotion motion, Balance balance) throws CallError, InterruptedException
	{
		float hipPitch = pitch;
		float midRoll = (Math.abs(RHIPPITCHMIN) + Math.abs(RHIPPITCHMAX))/2;
		
		hipPitch = hipPitch/100;
		
		midRoll = RHIPPITCHMAX - midRoll;
		
		hipPitch = ((Math.abs(midRoll) + RHIPPITCHMAX) * hipPitch) - Math.abs(midRoll);
		
		motion.setAngles("RHipPitch", hipPitch, SPEED);
	}

	public void setRKneePitch(int pitch, ALMotion motion, Balance balance) throws CallError, InterruptedException
	{
		float kneePitch = pitch;
		float midRoll = (Math.abs(RKNEEPITCHMIN) + Math.abs(RKNEEPITCHMAX))/2;
		
		kneePitch = kneePitch/100;
		
		midRoll = RKNEEPITCHMAX - midRoll;
		
		kneePitch = ((Math.abs(midRoll) + RKNEEPITCHMAX) * kneePitch) - Math.abs(midRoll);
		
		motion.setAngles("RKneePitch", kneePitch, SPEED);		
	}

	public void setRAnklePitch(int pitch, ALMotion motion, Balance balance) throws CallError, InterruptedException
	{
		float anklePitch = pitch;
		float midRoll = (Math.abs(RANKLEPITCHMIN) + Math.abs(RANKLEPITCHMAX))/2;
		
		anklePitch = anklePitch/100;
		
		midRoll = RANKLEPITCHMAX - midRoll;
		
		anklePitch = ((Math.abs(midRoll) + RANKLEPITCHMAX) * anklePitch) - Math.abs(midRoll);
		
		motion.setAngles("RAnklePitch", anklePitch, SPEED);
	}

	
	public void setRAnkleRoll(int roll, ALMotion motion, Balance balance) throws CallError, InterruptedException
	{
		// TODO Auto-generated method stub
		
	}

	
	public void setRLegDefualt(ALMotion motion) throws CallError, InterruptedException
	{
		motion.setAngles("RHipRoll", -0.11038116f, SPEED);
		motion.setAngles("RHipPitch", 0.18404147f, SPEED);
		motion.setAngles("RKneePitch", -0.073591396f, SPEED);
		motion.setAngles("RAnklePitch", 0.06293426f, SPEED);
		motion.setAngles("RAnkleRoll", 0.10126777f, SPEED);				
	}

	
	public void getLHipRoll(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Left Hip Roll = " + motion.getAngles("LHipRoll", false).get(0));
		
	}

	
	public void getLHipPitch(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Left Hip Pitch = " + motion.getAngles("LHipPitch", false).get(0));
		
	}

	
	public void getLKneePitch(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Left Knee Pitch = " + motion.getAngles("LKneePitch", false).get(0));
		
	}

	
	public void getLAnklePitch(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Left Ankle Pitch = " + motion.getAngles("LAnklePitch", false).get(0));
		
	}

	
	public void getLAnkleRoll(ALMotion motion) throws CallError, InterruptedException
	{
		System.out.println("Left Ankle Roll = " + motion.getAngles("LAnkleRoll", false).get(0));
		
	}

	
	public void setLHipRoll(int roll, ALMotion motion, Balance balance) throws Exception
	{
		float hipRoll = roll;
		float midRoll = (Math.abs(LHIPROLLMIN) + Math.abs(LHIPROLLMAX))/2;
		
		//balance.wbEnableBalanceConstraint(true, "RLeg");
		
		hipRoll = hipRoll/100;
		
		midRoll = LHIPROLLMAX - midRoll;
		
		hipRoll = ((Math.abs(midRoll) + LHIPROLLMAX) * hipRoll) - Math.abs(midRoll);
		
		motion.setAngles("LHipRoll", hipRoll, SPEED);	
	}

	
	public void setLHipPitch(int pitch, ALMotion motion, Balance balance) throws CallError, InterruptedException
	{
		float hipPitch = pitch;
		float midRoll = (Math.abs(LHIPPITCHMIN) + Math.abs(LHIPPITCHMAX))/2;
		
		hipPitch = hipPitch/100;
		
		midRoll = LHIPPITCHMAX - midRoll;
		
		hipPitch = ((Math.abs(midRoll) + LHIPPITCHMAX) * hipPitch) - Math.abs(midRoll);
		
		motion.setAngles("LHipPitch", hipPitch, SPEED);
	}

	
	public void setLKneePitch(int pitch, ALMotion motion, Balance balance) throws Exception
	{
		float kneePitch = pitch;
		float midRoll = (Math.abs(LKNEEPITCHMIN) + Math.abs(LKNEEPITCHMAX))/2;
		
		balance.wbEnableBalanceConstraint(true, "RLeg");
		
		kneePitch = kneePitch/100;
		
		midRoll = LKNEEPITCHMAX - midRoll;
		
		kneePitch = ((Math.abs(midRoll) + LKNEEPITCHMAX) * kneePitch) - Math.abs(midRoll);
		
		motion.setAngles("LKneePitch", kneePitch, SPEED);	
	}

	
	public void setLAnklePitch(int pitch, ALMotion motion, Balance balance) throws CallError, InterruptedException
	{
		float anklePitch = pitch;
		float midRoll = (Math.abs(LANKLEPITCHMIN) + Math.abs(LANKLEPITCHMAX))/2;
		
		anklePitch = anklePitch/100;
		
		midRoll = LANKLEPITCHMAX - midRoll;
		
		anklePitch = ((Math.abs(midRoll) + LANKLEPITCHMAX) * anklePitch) - Math.abs(midRoll);
		
		motion.setAngles("LAnklePitch", anklePitch, SPEED);
	}

	
	public void setLAnkleRoll(int roll, ALMotion motion, Balance balance) throws CallError, InterruptedException
	{
		
	}
	
	public void setLLegDefualt(ALMotion motion) throws CallError, InterruptedException
	{
		motion.setAngles("LHipRoll", 0.059890933f, SPEED);
		motion.setAngles("LHipPitch", 0.2102006f, SPEED);
		motion.setAngles("LKneePitch", -0.09054797f, SPEED);
		motion.setAngles("LAnklePitch", 0.056715403f, SPEED);
		motion.setAngles("LAnkleRoll", -0.07054056f, SPEED);		
	}

	public void getRLegPosition(ALMotion motion) throws CallError, InterruptedException
	{
		getRHipRoll(motion);
		getRHipPitch(motion);
		getRKneePitch(motion);
		getRAnklePitch(motion);
		getRAnkleRoll(motion);
	}

	public void getLLegPosition(ALMotion motion) throws CallError, InterruptedException 
	{
		getLHipRoll(motion);
		getLHipPitch(motion);
		getLKneePitch(motion);
		getLAnklePitch(motion);
		getLAnkleRoll(motion);		
	}

}
