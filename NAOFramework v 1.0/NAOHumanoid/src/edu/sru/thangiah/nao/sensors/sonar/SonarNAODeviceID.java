package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Contains constants for BOTH, LEFT, or RIGHT.
 * This can be used for the NAO robot to represent
 * which transmitter or receiver to use during a sonar wave.
 * 
 * @author Brian Atwell
 *
 */
public enum SonarNAODeviceID implements SonarEnum {
	
	BOTH(0), LEFT(1), RIGHT(2);
	
	private int intVal;
	
	/**
	 * 
	 * @param intVal
	 */
	SonarNAODeviceID(int intVal) {
		this.intVal = intVal;	
	}
	
	/**
	 * @return integer representation of SonarNAODeviceID object
	 * BOTH  0
	 * LEFT  1
	 * RIGHT 2
	 */
	public int intValue() {
		return intVal;
	}
	
	/**
	 * Loops through all values and returns the SonarNAODeviceID object
	 * that has the integer value.
	 */
	public SonarNAODeviceID getEnumFromInt(int value) {
		for(SonarNAODeviceID dev: SonarNAODeviceID.values())
		{
			if(dev.intValue() == value) {
				return dev;
			}
		}
		return null;
	}

	/**
	 * Gets SonarNAODeviceID form string representation of the enum constant.
	 * Appropriate strings to pass:
	 * BOTH
	 * LEFT
	 * RIGHT
	 */
	@Override
	public SonarNAODeviceID getEnumFromStr(String arg0) {
		// TODO Auto-generated method stub
		return SonarNAODeviceID.valueOf(arg0);
	}
	
	public static void main(String[] args) {
		SonarNAODeviceID test;
		int value;
		test = SonarNAODeviceID.BOTH;
		
		value = SonarNAODeviceID.BOTH.intValue();
	}

	/**
	 * Checks if the enum contains a value specified.
	 * Returns true if there is an enum with that value.
	 * Else it returns false.
	 */
	@Override
	public boolean contains(int value) {
		// TODO Auto-generated method stub
		for(SonarNAODeviceID dev: SonarNAODeviceID.values())
		{
			if(dev.intValue() == value) {
				return true;
			}
		}
		return false;
	}

}
