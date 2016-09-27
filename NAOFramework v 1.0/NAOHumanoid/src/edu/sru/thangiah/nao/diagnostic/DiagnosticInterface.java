package edu.sru.thangiah.nao.diagnostic;

/**
 * Diagnostic Interface
 * 
 * @author Brian Atwell
 */
public interface DiagnosticInterface {
	
	/**
	 * gets the active diagnosis
	 * 
	 * @return String
	 */
	public String getActiveDiagnosis();
	
	/**
	 * gets the passive diagnosis
	 * 
	 * @return String
	 */
	public String getPassiveDiagnosis();
	
	/**
	 * returns true if the robot is ready
	 * 
	 * @return boolean
	 */
	public boolean isRobotReady();
}
