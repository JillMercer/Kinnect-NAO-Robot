package edu.sru.thangiah.nao.speech.texttospeech;

/** Allows configuration of the NAO pitch, speed and volume.
 * Use methods say and animatedSay to cause the NAO to speak.
* @author Zachary Kearney & Justin Cather
*
*/

import java.util.ArrayList;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALAnimatedSpeech;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.Module;

public class TextToSpeech extends Module implements TextToSpeechInterface {

	private ALTextToSpeech tts;
	private ALAnimatedSpeech atts;
	private int speed;
	private int pitch;

	public TextToSpeech(Session session) throws Exception {
		super(session);
		speed = 100;
		pitch = 100;
		
		tts = new ALTextToSpeech(session);
		atts = new ALAnimatedSpeech(session);
	}


	/**
	 * Resets to default parameters.
	 */
	public void reset() throws Exception {
		setVolume(1);
		setSpeed(1);
		setPitch(1);
		setDVoice(0);
		setDLevel(0);
		setDTime(0);
	}

	/**
	 * Exits the module.
	 */
	public void exit() throws Exception {
		tts.stopAll();
	}

	/**
	 * Adjusts volume of speech.
	 * 
	 * @param val The new volume value. Range is 0 - 1.75.
	 * @throws Exception
	 */
	public void setVolume(float val) throws Exception {

		if (val <= 0f) {
			val = 0f;
		} else if (val >= 1.75f) {
			val = 1.75f;
		}
		tts.setVolume(val);
	}

	/**
	 * Adjusts pitch of speech.
	 * 
	 * @param val The new pitch value. Range is .5 - 2.
	 * @throws Exception
	 */
	public void setPitch(float val) throws Exception {

		if (val <= .5f) {
			val = .5f;
		} else if (val >= 2f) {
			val = 2f;
		}
		pitch = (int) val * 100;

	}

	/**
	 * Adjusts voice of speech;
	 * 
	 * @param voice The voice to use.
	 * @throws Exception
	 */
	public void setVoice(String voice) throws Exception {

		tts.setVoice(voice);
	}

	/**
	 * Adjusts double voice level of speech 
	 * 
	 * @param val Range is 0-4.
	 * @throws Exception
	 */
	public void setDLevel(float val) throws Exception {

		if (val <= 0f) {
			val = 0f;
		} else if (val >= 4f) {
			val = 4f;
		}
		tts.setParameter("doubleVoiceLevel", val);
	}

	/**
	 * Adjusts ratio between double voice and original voice.
	 * 
	 * @param val Range is 1-4; Any value less than 1 disables effect.
	 * @throws Exception
	 */
	public void setDVoice(float val) throws Exception {

		if (val < 1f) {
			val = 0f;
		} else if (val >= 4f) {
			val = 4f;
		}
		tts.setParameter("doubleVoice", val);
	}

	/**
	 * Adjusts timeshift of double voice.
	 * 
	 * @param val The value for double voice. Range is 0-.5.
	 * @throws Exception
	 */
	public void setDTime(float val) throws Exception {

		if (val <= 0f) {
			val = 0f;
		} else if (val >= .5f) {
			val = .5f;
		}
		tts.setParameter("doubleVoiceTimeShift", val);
	}

	/**
	 * Adjusts speed of voice. 
	 * 
	 * @param val The value for speed. Range is .5-4.
	 * @throws Exception
	 */
	public void setSpeed(float val) throws Exception {

		if (val <= .5f) {
			val = .5f;
		} else if (val >= 4f) {
			val = 4f;
		}
		speed = (int) val * 100;

	}

	/**
	 * Calls tts module to speak;
	 * 
	 * @param text The speech to speak.
	 * @throws Exception
	 */
	public void say(String text) throws Exception {

		String say = ("\\rspd=" + speed + "\\\\vct=" + pitch + "\\");
		tts.say(say + text);

	}

	/**
	 * Returns an ArrayList of all voices available on the robot;
	 */
	public ArrayList getAvailableVoices() throws Exception {
		return (ArrayList) tts.getAvailableVoices();
	}

	/**
	 * Returns the current volume;
	 */
	public float getVolume() throws Exception {
		return tts.getVolume();
	}

	/**
	 * Stops all running speech;
	 * 
	 * @throws Exception
	 */
	public void stopAllSpeech() throws Exception {
		tts.stopAll();
	}

	/**
	 * Returns the current body language mode for Animated Speech.
	 * 
	 * @return Values are disabled, random, contextual.
	 * @throws Exception
	 */
	public BodyLanguage getBodyLanguageMode() throws Exception {
		return BodyLanguage.valueOf(atts.getBodyLanguageModeToStr());
	}

	/**
	 * Sets the current body language mode for Animated Speech; Values are
	 * Disabled - No body language Random - Random body language Contextual -
	 * Uses words like you, me, and hi to determine the animation
	 * 
	 * @param mode The body mode to use.
	 * @throws Exception
	 */
	public void setBodyLanguageMode(BodyLanguage mode) throws Exception {
		if (mode != null) {
			atts.setBodyLanguageModeFromStr(mode.name());
		} 
		else {
			throw new IllegalArgumentException("Mode cannot be null.");
		}
	}

	/**
	 * Says the input with the current body language mode.
	 * 
	 * @param text The string the NAO will speak.
	 * @throws Exception
	 */
	public void animatedSay(String text) throws Exception {
		String say = ("\\rspd=" + speed + "\\\\vct=" + pitch + "\\");
		atts.say(say + text);
	}
	
	/** NAO says a given string and performs an animation.
	 * USE Gestures.java for animations.
	 * @param text The string the NAO will speak.
	 * @param animation The animation the NAO will perform.
	 */
	public void animatedSay(String text, String animation) throws Exception{
		String say = ("\\rspd=" + speed + "\\\\vct=" + pitch + "\\");
		atts.say(say + "^start(" + animation + ")" + text + "^wait(" + animation + ")");
	}
}
