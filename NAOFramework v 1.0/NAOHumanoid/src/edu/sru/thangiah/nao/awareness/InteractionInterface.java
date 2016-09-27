package edu.sru.thangiah.nao.awareness;

import edu.sru.thangiah.nao.module.Module;
import edu.sru.thangiah.nao.module.ModuleInterface;

public interface InteractionInterface {
		
	public boolean getStatus() throws Exception;
	
	public void setSpeechInteraction(boolean enable) throws Exception;
	
	public boolean getSpeechInteraction() throws Exception;
	
	public void runSpeech() throws Exception;
	
	public void setFollow(boolean enable) throws Exception;
	
	public boolean getFollow() throws Exception;
	
	public void follow() throws Exception;
	
	public void setPostureChange(boolean enable) throws Exception;
	
	public boolean getPostureChange() throws Exception;
	
	public void postureChange() throws Exception;
	
	

}
