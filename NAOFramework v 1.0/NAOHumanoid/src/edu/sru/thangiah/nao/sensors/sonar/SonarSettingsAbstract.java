package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Provides a common abstract framework for the sonar settings.
 * 
 * @author Brian Atwell
 * @date 8-2015
 *
 */
public abstract class SonarSettingsAbstract implements SonarSettingsInterface {
	
	/**
	 * Sets which transmitters to use for sonar
	 * 
	 * @return 0 for success
	 */
	protected abstract int setTransmittersAbsolute(int transmitterID);
	
	/**
	 * Sets which receivers to use for sonar
	 * 
	 * @return 0 for success
	 */
	protected abstract int setReceiversAbsolute(int receiverID);
}
