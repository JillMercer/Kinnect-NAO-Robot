/**
 * @File SonarDetectionNAOAdapter.java
 * 
 * Provides Sonar Events which can be implemented through a child class.
 * 
 * @author Brian Atwell
 * @date 2015
 * @lastModified 10/27/2015
 */

package edu.sru.thangiah.nao.sensors.sonar;

public abstract class SonarDetectionNAOAdapter implements
		SonarDetectionNAOListener {

	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is something in front of NAO (left side)
	 * at less than 0.5m. This means that NAO can’t go
	 * forward and has to stop and turn right to avoid
	 * the obstacle.
	 */
	@Override
	public void sonarLeftDetected(float distance) {
		// TODO Auto-generated method stub

	}

	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is something in front of NAO (right side)
	 * at less than 0.5m. This means that NAO can’t go
	 * forward and has to stop and turn left to avoid the obstacle.
	 */
	@Override
	public void sonarRightDetected(float distance) {
		// TODO Auto-generated method stub

	}

	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is nothing in front of NAO nor on his left side,
	 * this means NAO can go forward or turn left. An obstacle
	 * is present on the right side at less than 0.5m but it is
	 * not a problem if NAO goes forward.
	 */
	@Override
	public void sonarLeftNothingDetected(float distance) {
		// TODO Auto-generated method stub

	}

	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is nothing in front of NAO nor on his right side,
	 * this means NAO can go forward or turn right. An obstacle
	 * is present on the left side at less than 0.5m but it is
	 * not a problem if NAO goes forward.
	 */
	@Override
	public void sonarRightNothingDetected(float distance) {
		// TODO Auto-generated method stub

	}

}
