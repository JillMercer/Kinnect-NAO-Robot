package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Implements SonarEnum
 * and provides sonar sensor locations
 * @author Brian Atwell
 */

public enum SonarNAOLocation implements SonarEnum {
	LEFT(0), RIGHT(1);
	
	private int intVal;
	
	/**
	 * Creates a new SonarNAOLocation enum with the specified value of num.
	 * @param num
	 */
	SonarNAOLocation(int num) {
		this.intVal = num;
	}

	/**
	 * @return integer representation of SonarNAODeviceID object
	 * BOTH  0
	 * LEFT  1
	 * RIGHT 2
	 */
	@Override
	public int intValue() {
		// TODO Auto-generated method stub
		return intVal;
	}

	/**
	 * Loops through all values and returns the SonarNAODeviceID object
	 * that has the integer value.
	 */
	@Override
	public SonarNAOLocation getEnumFromInt(int value) {
		for(SonarNAOLocation loc: SonarNAOLocation.values())
		{
			if(loc.intValue() == value) {
				return loc;
			}
		}
		return null;
	}

	/**
	 * Gets SonarNAODeviceID form string representation of the enum constant.
	 * Appropriate strings to pass:
	 * LEFT
	 * RIGHT
	 */
	@Override
	public SonarNAOLocation getEnumFromStr(String enumConstant) {
		// TODO Auto-generated method stub
		return SonarNAOLocation.valueOf(enumConstant);
	}

	/**
	 * Checks if the enum contains a value specified.
	 * Returns true if there is an enum with that value.
	 * Else it returns false.
	 */
	@Override
	public boolean contains(int value) {
		for(SonarNAOLocation loc: SonarNAOLocation.values())
		{
			if(loc.intValue() == value) {
				return true;
			}
		}
		return false;
	}

}
