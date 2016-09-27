package edu.sru.thangiah.nao.awareness;

import edu.sru.thangiah.nao.awareness.enums.Appendage;

public interface BreathingInterface {

	/** Sets the breathing mode for the specified limb.
	 * @param name The name of the limb.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setBreathEnabled(Appendage name, boolean enable) throws Exception;

	/** Gets the breathing mode for the specified limb.
	 * @param name The name of the limb.
	 * @return True if breathing is enable for the limb, false if not.
	 * @throws Exception
	 */
	public boolean getBreathEnabled(Appendage name) throws Exception;
	
	/** Sets the idle posture for the specified limb.
	 * @param name The name of the limb.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setIdlePostureEnabled(Appendage name, boolean enable) throws Exception;
	
	/** Gets the idle posture mode for the specified limb.
	 * @param name The name of the limb.
	 * @return True if idle posture is enable for the limb, false if not.
	 * @throws Exception
	 */
	public boolean getIdlePostureEnabled(Appendage name) throws Exception;
	
	/** Returns the breath config as 
	 * @return a float array Array consists of[BreathsPerMin, Amplitude]
	 * @throws Exception
	 */
	public float[] getBreathConfig() throws Exception;
	
	/** Sets the breath config.
	 * @param bpm Beats per minute 5 - 30
	 * @param amp 0 - 1
	 * @throws Exception
	 */
	public void setBreathConfig(float bpm, float amp) throws Exception;

	/** Sets the expressive listening mode for the NAO. When enabled, the NAO
	 * does slight movements to indicate if it is listening.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setExpressiveListening(boolean enable) throws Exception;

	/** Gets if expressive listening is enabled.
	 * @return True if enabled, false if not.
	 * @throws Exception
	 */
	public boolean getExpressiveListening() throws Exception;

	/** Sets the background strategy for the NAO. Background strategy is only in
	 * effect when sitting. When disabled, the robot will never move
	 * automatically. When enabled, the robot will move according to the posture
	 * it is in.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setBackgroundMotion(boolean enable) throws Exception;
	
	/** Gets the state of the background strategy.
	 * @return True if enabled, false if not.
	 * @throws Exception
	 */
	public boolean getBackgroundMotion() throws Exception;

	/** Enables or disables the background strategy, breathing, and expressive listening.
	 * @param enable True to enable, false to disable.
	 * @throws Exception
	 */
	public void setAllBreathing(boolean enable) throws Exception;
	
}
