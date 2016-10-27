package edu.sru.thangiah.nao.demo.posturecustom;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

/**
 * Author: Brady Rainey Last Edited, 10/27/2016
 * 
 * @author Custom postures for NAO robot. 
 *
 *
 */
public class PostureCustom {
	
	ALTextToSpeech tts;
	ALMotion motion;
	ALRobotPosture posture;
	boolean moving = false;
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
	
	public void setDefault( ALMotion motion ) {
		try {
			setLDefault( motion );
			setRDefault( motion );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CallError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setLDefault(ALMotion motion) throws InterruptedException, CallError
	{
		motion.setAngles("LShoulderPitch", 0.0f, SPEED);
		motion.setAngles("LShoulderRoll", 0.50615f, SPEED);
		motion.setAngles("LElbowYaw", 0.0f, SPEED);
		motion.setAngles("LElbowRoll", -0.82465f, SPEED);
		motion.setAngles("LWristYaw", 0.0f, SPEED);
		motion.closeHand("LHand");
	}
	
	public void setRDefault(ALMotion motion) throws InterruptedException, CallError 
	{
		motion.setAngles("RShoulderPitch", 0.0f, SPEED);
		motion.setAngles("RShoulderRoll", 0.50615f, SPEED);
		motion.setAngles("RElbowYaw", 0.0f, SPEED);
		motion.setAngles("RElbowRoll", -0.82465f, SPEED);
		motion.setAngles("RWristYaw", 0.0f, SPEED);
		motion.closeHand("RHand");
	}
	
	public void extendLeftArm () {
		if ( !moving ) {
			moving = true;
			try {
				
			} finally {
				
			}
		}
	}
	
}