package edu.sru.thangiah.nao.awareness;

import edu.sru.thangiah.nao.awareness.enums.LifeState;
import edu.sru.thangiah.nao.module.ModuleInterface;

/** Author: Zachary Kearney
Last Edited, 9/25/2015
* @author zrk1002
*
*/

public interface AwarenessInterface{
	
	/** Set the basic robot awareness.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setAwareness(boolean enable) throws Exception;

	/** Gets the state of the current basic robot awareness.
	 * @return True if enabled, false if not.
	 * @throws Exception
	 */
	public boolean getAwareness() throws Exception;	
	
	/**
	 * Sets the autonomous life state for the robot States can either be
	 * solitary, interactive, disabled, or safeguard. See
	 * http://doc.aldebaran.com/2-1/naoqi/core/autonomouslife_advanced.html# for more details.
	 * @param state The life state to use.
	 * @throws Exception
	 */
	public void setLifeState(LifeState state) throws Exception;
	
	/** Gets the current life state.
	 * @return The current life state. Null if error occurred.
	 * @throws Exception
	 */
	public LifeState getLifeState() throws Exception;
	
}
