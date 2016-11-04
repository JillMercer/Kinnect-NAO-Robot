/**
 * 
 */
package edu.sru.thangiah.nao.posture;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;

/**
 * @author Brady Rainey
 *
 */
public class NAOPosture {
	
	private ALMotion motion;
	private ALTextToSpeech speech;
	float speed = 0.3f; // Speed is a percentage. 1 = 100. .3 = 30%
	
	public NAOPosture ( String name, SynchronizedConnectDemo connect ) {
		try {
			this.motion = new ALMotion( connect.getSession( name ));
			this.speech = new ALTextToSpeech( connect.getSession( name ));
			// Wake up the motors and get ready for movements. 
			speech.say( "Inside NAO posture class." );
			motion.wakeUp();
			motion.setBreathEnabled("RArm", false);
			motion.setIdlePostureEnabled("RArm", false);
			motion.setBreathEnabled("LArm", false);
			motion.setIdlePostureEnabled("LArm", false);
			motion.setBreathEnabled("RLeg", false);
			motion.setIdlePostureEnabled("RLeg", false);
			motion.setBreathEnabled("LLeg", false);
			motion.setIdlePostureEnabled("LLeg", false);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Arm Motions:
	public void leftArmOut () {
		try {
			motion.setAngles( "LShoulderPitch", Math.toRadians( 0f ), speed );
			motion.setAngles( "LShoulderRoll", Math.toRadians( 76f ), speed );
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void rightArmOut() {
		try {
			motion.setAngles( "RShoulderPitch", Math.toRadians( 0f ), speed );
			motion.setAngles( "RShoulderRoll", Math.toRadians( -76f ), speed );
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void leftArmUp() {
		try {
			motion.setAngles( "LShoulderPitch", Math.toRadians( -119.5f ), speed );
			motion.setAngles( "LShoulderRoll", Math.toRadians( 0f ), speed );
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void rightArmUp() {
		try {
			motion.setAngles( "RShoulderPitch", Math.toRadians( -119.5f ), speed );
			motion.setAngles( "RShoulderRoll", Math.toRadians( 0f ), speed );
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void leftArmDown() {
		try {
			motion.setAngles( "LShoulderPitch", Math.toRadians( 119.5f ), speed );
			motion.setAngles( "LElbowYaw", Math.toRadians( -90f ), speed );
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void rightArmDown() {
		try {
			motion.setAngles( "RShoulderPitch", Math.toRadians( 119.5f ), speed );
			motion.setAngles( "RElbowYaw", Math.toRadians( 90f ), speed );
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
