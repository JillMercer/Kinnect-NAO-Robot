package edu.sru.thangiah.nao.sensors.sonar;

import java.lang.instrument.IllegalClassFormatException;

/**
 * Common interface for Sonar
 * @author Brian Atwell
 */
public interface SonarInterface {
	
	/**
	 * Adds a SonarDetectionListener that is called
	 * when a sonar detection event occurs.
	 * @param listener
	 */
	public void addSonarDetectionListener(SonarDetectionListener listener);
	
	/**
	 * Adds a SonarPeriodicListener that is called when a sonar wave is
	 * updated.
	 * @param listener
	 */
	public void addSonarPeriodicListener(SonarPeriodicListener listener);
	
	/**
	 * Removes a certain Sonar detection listener
	 * @param listener
	 */
	public void removeSonarDetectionListener(SonarDetectionListener listener);
	
	/**
	 * Removes a certain Sonar Period Listener 
	 */
	public void removeSonarPeriodicListener(SonarPeriodicListener listener);
	
	/**
	 * Turns on the Sonar hardware to monitor sonar waves
	 */
	public void enable();
	
	/**
	 * Turns off the Sonar hardware
	 */
	public void disable();
	
	/**
	 * Gets an array of echoes from 0 to 10 echoes
	 * from the left sensor. Which will be from 0 to 10 elements
	 * Must use appropriate enum
	 * @return Float array
	 * @throws IllegalClassFormatException 
	 */
	public Float[] getSonarEchoes(int loc) throws IllegalClassFormatException;
	
	/**
	 * Sets sonar wave settings
	 */
	public boolean setSonarWaveSettings(SonarSettingsInterface settings);
	
	/**
	 * Sends a single wave
	 */
	public void sendWave();
	
	/**
	 * Sends waves periodically
	 */
	public void sendWavesPeriodically();
	
	/**
	 * Stops periodic waves
	 */
	public void stopWaves();

}
