package edu.sru.thangiah.nao.sensors.sonar;

/**
 * @File SonarDetectionListener.java
 * 
 * Provides callbacks for SonarDetected and SonarNothingDetected events
 * each callback is call when an object is detected (sonarDetected) or
 * an object is not detected (sonarNothingDetected) for a particular location (SonarEnumLocation).
 * 
 * To find out if other objects are detected other than the one refered to by SonarEnumLocation loc,
 * use SonarDetectionSettings. SonarDetectionSettings also provides the minimium distance that will
 * trigger those events.
 * 
 * @author Brian Atwell
 * @date 2015
 * @lastModified 10/27/2015
 *
 */
public interface SonarDetectionListener {
	
	/**
	 * Called by SonarNAO when an object is detected less than 0.5 meters away.
	 * The @param loc is the ID of the Sonar device. For NAO robot use SonarNAOLocation enum.
	 * The distance is in meters.
	 * 
	 * @param loc
	 * @param distance
	 */
	public void sonarDetected(int loc, float distance);
	
	/**
	 * Called by SonarNAO when no objects are in front of it.
	 * The @param loc is the ID of the Sonar device. For NAO robot use SonarNAOLocation enum.
	 * The distance is in meters.
	 * 
	 * @param loc
	 * @param distance
	 */
	public void sonarNothingDetected(int loc, float distance);
	
	

}
