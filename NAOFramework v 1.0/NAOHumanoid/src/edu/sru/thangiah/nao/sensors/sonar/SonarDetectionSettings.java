package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Provides a common interface for Sonar Detection Settings
 * Such as: 
 * 			* minimum object detection distance
 * 			* Whether each location is blocked
 * 			* distance of the object at each location
 * 
 * @author Brian Atwell
 *
 */
public interface SonarDetectionSettings {
	
	/**
	 * Gets the minimum distance an object can be to the robot.
	 * @param loc
	 * @return
	 */
	public float getMinimumDistance(int loc);
	
	/**
	 * Gets the maximum distance an object can be to the robot.
	 * @param loc
	 * @return
	 */
	public float getMaximumDistance(int loc);
	
	/**
	 * Gets the recommended minimum distance an object can be to the robot.
	 * @param loc
	 * @return
	 */
	public float getRecommendedMinimumDistance(int loc);
	
	/**
	 * Gets the recommended maximum distance an object can be to the robot.
	 * @param loc
	 * @return
	 */
	public float getRecommendedMaximumDistance(int loc);

}
