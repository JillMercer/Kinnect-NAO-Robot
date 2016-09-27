package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Listener that provides callbacks
 * for Sonar Periodic waves 
 * @author Brian Atwell
 */
public interface SonarPeriodicListener {

	/**
	 * Callback for the when sonar values change
	 * 	
	 * @param right contains an array from 0 to 10 elements
	 * from the right sonar sensor
	 */
	public void sonarChanged(int loc, Float[] echoes);
}
