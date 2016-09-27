package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Provides a common framework for Sonar Settings
 * Such as settings for transmitters and receivers
 * @author Brian Atwell
 */
public interface SonarSettingsInterface {
	
	/**
	 * Sets which transmitters to use for sonar
	 * 
	 * @return 0 for success
	 */
	public int setTransmitters(int transmitterID);
	
	/**
	 * Sets which receivers to use for sonar
	 * 
	 * @return 0 for success
	 */
	public int setReceivers(int receiverID);
	
	/**
	 * Gets current transmitters being used for sonar
	 * 
	 * @return transmitterID
	 */
	public int getTransmitters();
	
	/**
	 * Gets current receivers being used for sonar
	 * 
	 * @return receiverID
	 */
	public int getReceivers();
	
	/**
	 * Gets data representation of settings
	 * Used to convert sonar settings to a certain data type
	 * 
	 * For example NAO robot original API requires a integer with each bit representing each attribute.
	 * So the class has to be converted to an int to be passed to the original API.
	 */
	public Integer getData();
	
	/**
	 * Takes @param data and converts it to SonarSetting object
	 * @return true if data is successfully converted to SonarSettings
	 * otherwise false.
	 */
	public boolean importData(Integer data);

}
