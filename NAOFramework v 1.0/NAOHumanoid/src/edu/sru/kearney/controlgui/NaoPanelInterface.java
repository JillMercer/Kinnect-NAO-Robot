package edu.sru.kearney.controlgui;

import com.aldebaran.qi.Session;

/**
 * NaoPanel Interface Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public interface NaoPanelInterface {

	public void enableDisable(boolean enabled);

	public void connect(Session sess) throws Exception;

	public void run() throws Exception;

}
