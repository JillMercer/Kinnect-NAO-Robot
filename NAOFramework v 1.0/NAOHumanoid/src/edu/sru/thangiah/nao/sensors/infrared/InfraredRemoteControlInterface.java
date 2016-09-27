package edu.sru.thangiah.nao.sensors.infrared;

/** A common interface for sending remote control buttons
 * and receiving remote control buttons. 
 * 
 * @author Brian Atwell
 *
 */
public interface InfraredRemoteControlInterface {
	
	/**
	 * Adds a new callback listener for IR Remote controls that is called when
	 * a button is pressed
	 * @param listener
	 */
	public void addInfraredRemoteListener(InfraredLIRCRemoteListener listener);
	
	/**
	 * Remove a new callback listener for IR Remote controls that is called when
	 * a button is pressed
	 * @param listener
	 */
	public void removeInfraredRemoteListener(InfraredLIRCRemoteListener listener);
	
	/**
	 * Send a IR remote control button
	 * @param remoteName
	 * @param remoteButton
	 */
	public void sendRemoteButton(String remoteName, String buttonName);
	
	/**
	 * Send a IR remote Control button with Buttone
	 * @param remoteName
	 * @param buttonName
	 * @param time
	 */
	public void sendRemoteButtonWithTime(String remoteName, String buttonName, Integer pTimeMs);
	
	
	/**
	 * Gets the name of the name of the remote from the last remote name and button
	 * sent.
	 * @return String
	 */
	public String getRemoteNameSent();
	
	/**
	 * Get the name of the button as a String
	 * @return String
	 */
	public String getRemoteButtonNameSent();
	
	/**
	 * Get the LIRC hexa code from the LIRC linux module as a String
	 * LIRC documentation: http://www.lirc.org/html/index.html
	 * 
	 * @return String
	 */
	public String getLIRCCode();
	
	/**
	 * Gets the number of times the code is repeated.
	 * ** Some remotes repeat the code a certain number of times
	 * 
	 * @return Integer
	 */
	public Integer getRepeatsPerFrame();
	
	/**
	 * Gets the name of the button last received
	 * @return
	 */
	public String getRemoteButtonNameReceived();
	
	/**
	 * Get the Name of the remote that was received last
	 * @return String
	 */
	public String getRemoteNameReceived();
	
	/**
	 * Get the Location of IR sensor that received the signal
	 * @return
	 */
	public int getIRLocationReceived();
}
