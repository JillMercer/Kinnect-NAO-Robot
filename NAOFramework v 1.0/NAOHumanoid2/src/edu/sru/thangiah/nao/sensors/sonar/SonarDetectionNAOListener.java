package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Sonar Detection Listener
 * Provides callbacks for sonar detection events
 * @author Brian Atwell
 * @date 2015
 * @lastModified 10/27/2015
 */
public interface SonarDetectionNAOListener {
	
	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is something in front of NAO (left side)
	 * at less than 0.5m. This means that NAO can’t go
	 * forward and has to stop and turn right to avoid
	 * the obstacle.
	 */
	public void sonarLeftDetected(float distance);
	
	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is something in front of NAO (right side)
	 * at less than 0.5m. This means that NAO can’t go
	 * forward and has to stop and turn left to avoid the obstacle.
	 */
	public void sonarRightDetected(float distance);
	
	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is nothing in front of NAO nor on his left side,
	 * this means NAO can go forward or turn left. An obstacle
	 * is present on the right side at less than 0.5m but it is
	 * not a problem if NAO goes forward.
	 */
	public void sonarLeftNothingDetected(float distance);
	
	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is nothing in front of NAO nor on his right side,
	 * this means NAO can go forward or turn right. An obstacle
	 * is present on the left side at less than 0.5m but it is
	 * not a problem if NAO goes forward.
	 */
	public void sonarRightNothingDetected(float distance);

}
