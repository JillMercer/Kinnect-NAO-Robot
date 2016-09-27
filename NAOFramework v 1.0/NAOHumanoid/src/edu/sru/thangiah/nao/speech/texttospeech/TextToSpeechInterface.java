package edu.sru.thangiah.nao.speech.texttospeech;

/** Author: Zachary Kearney
Last Edited, 11/5/2015
* @author zrk1002
*
*/

import java.util.ArrayList;

import edu.sru.thangiah.nao.module.ModuleInterface;

public interface TextToSpeechInterface{
	
	public void setVolume(float val) throws Exception;
	
	public void setPitch(float val) throws Exception;
	
	public void setVoice(String voice) throws Exception;
	
	public void setDLevel(float val) throws Exception;
	
	public void setDVoice(float val) throws Exception;
	
	public void setDTime(float val) throws Exception;
	
	public void setSpeed(float val) throws Exception;
	
	public void say(String text) throws Exception;

	public ArrayList getAvailableVoices() throws Exception;
	
	public float getVolume() throws Exception;
	
	public void stopAllSpeech() throws Exception;
	
	public void setBodyLanguageMode(BodyLanguage mode) throws Exception;
	
	public void animatedSay(String text) throws Exception;
	
	public void animatedSay(String text, String animation) throws Exception;
	
	public BodyLanguage getBodyLanguageMode() throws Exception;
}