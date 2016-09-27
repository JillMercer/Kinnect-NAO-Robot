package edu.sru.thangiah.nao.awareness;

import edu.sru.thangiah.nao.awareness.enums.EngagementModes;
import edu.sru.thangiah.nao.awareness.enums.TrackingModes;

public interface EngagementInterface {

	/**Sets the engagement mode of the NAO.
	 * 
	 * Unengaged - Enabled by default. When the robot is engaged with a user, it
	 * can be distracted by any stimulus, and engage with another person.
	 * 
	 * FullyEngaged - As soon as the robot is engaged with a person, it stops
	 * listening to stimuli and stays engaged with the same person. If it loses
	 * the engaged person, it will listen to stimuli again and may engage with a
	 * different person.
	 * 
	 * SemiEngaged - When the robot is engaged with a person, it keeps listening
	 * to the stimuli, and if it gets a stimulus, it will look in its direction,
	 * but it will always go back to the person it is engaged with. If it loses
	 * the person, it will listen to stimuli again and may engage with a
	 * different person.
	 * @param mode The mode to use.
	 * @throws Exception
	 */
	public void setEngagementMode(EngagementModes mode) throws Exception;
	
	/** Gets the current engagement mode.
	 * @return The current engagement mode.
	 * @throws Exception
	 */
	public EngagementModes getEngagementMode() throws Exception;
	
	/**
	 * Sets the tracking mode of the NAO. 
	 * Head - Tracking uses only the head
	 * BodyRotation - Tracking uses the head and the rotation of the body.
	 * WholeBody - Tracking uses the whole body, but doesn't make it rotate.
	 * MoveContextually - Tracking uses the head and autonomously performs small
	 * moves such as approaching the tracked person, stepping backward,
	 * rotating, etc...
	 * @param mode The tracking mode to use.
	 * @throws Exception
	 */
	public void setTrackingMode(TrackingModes mode) throws Exception;
	
	/** Gets the current tracking mode.
	 * @return The current tracking mode or null if error occurs.
	 * @throws Exception
	 */
	public TrackingModes getTrackingMode() throws Exception;
	
}
