package edu.sru.thangiah.nao.diagnostic;

/**
 * DiagnosticListener
 * 
 * Receives callback to methods
 * 
 * @author Brian Atwell
 */
public interface DiagnosticListener {
	
	/**
	 * Called when a new diagnosis is made
	 * @param String activeDiagnosis
	 */
	public void ActiveDiagnosisErrorChanged(String activeDiagnosis);
	
	/**
	 * Called when a new diagnosis is made
	 * @param String passiveDiagnosis
	 */
	public void PassiveDiagnosisErrorChanged(String passiveDiagnosis);
	
	/**
	 * Called when the robot ready status is changed
	 * @param boolean status
	 */
	public void RobotIsReady(boolean status);

}
