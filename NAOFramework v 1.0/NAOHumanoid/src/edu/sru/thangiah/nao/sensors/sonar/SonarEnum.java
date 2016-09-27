package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Provides interface for enumerators to inherit from.
 * This allows for child classes of SonarSettingsInterface to define
 * their own enum.
 * @author Brian Atwell
 */
public interface SonarEnum {

	/**
	 * @return integer representation of enum
	 */
	public int intValue();
	
	/**
	 * Get enumerator from @param value
	 * @return enumerator
	 * 
	 * **Must be cast to the appropriate enum
	 */
	public SonarEnum getEnumFromInt(int value);
	
	public String toString();
	
	/**
	 * Gets enumerator from String representation of constant from enum.
	 * @param enumConstant
	 * @return enumerator
	 * 
	 * **Must be cast to the appropriate enum
	 */
	public SonarEnum getEnumFromStr(String enumConstant);
	
	/**
	 * Checks if the enum contains a value that has the same integer
	 * value as @param value. If it does it returns true.
	 * @return
	 */
	public boolean contains(int value);
}
