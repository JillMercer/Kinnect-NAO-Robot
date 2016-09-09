package edu.sru.thangiah.nao.awareness;

public interface StimulusInterface {

	/** Sets the NAO to respond to sound stimulus.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setSoundStimulus(boolean enable) throws Exception;
	
	/** Sets the NAO to respond to movement stimulus.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setMoveStimulus(boolean enable) throws Exception;
	
	/** Sets the NAO to respond to touch stimulus.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setTouchStimulus(boolean enable) throws Exception;
	
	/** Sets the NAO to respond to people stimulus.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setPeopleStimulus(boolean enable) throws Exception;
	
	/** Sets the NAO to respond to all stimulus.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setAllStimulus(boolean enable) throws Exception;
	
	/** Gets the sound stimulus status.
	 * @return True if enabled, false if not.
	 * @throws Exception
	 */
	public boolean getSoundStimulus() throws Exception;
	
	/** Gets the move stimulus status.
	 * @return True if enabled, false if not.
	 * @throws Exception
	 */
	public boolean getMoveStimulus() throws Exception;
	
	/** Gets the touch stimulus status.
	 * @return True if enabled, false if not.
	 * @throws Exception
	 */
	public boolean getTouchStimulus() throws Exception;
	
	/** Gets the people stimulus status.
	 * @return True if enabled, false if not.
	 * @throws Exception
	 */
	public boolean getPeopleStimulus() throws Exception;
	
	/** Gets the status of all stimulus.
	 * @return True if enabled, false if not.
	 * @throws Exception
	 */
	public boolean getAllStimulus() throws Exception;
	
}
