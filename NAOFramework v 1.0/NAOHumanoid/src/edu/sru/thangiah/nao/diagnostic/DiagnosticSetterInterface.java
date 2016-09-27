package edu.sru.thangiah.nao.diagnostic;

/**
 * DianosticSetterInterface
 * Provides setters for diagnostic
 * @author Brian Atwell
 */
public interface DiagnosticSetterInterface {

	/**
	 * Set active diagnosis
	 * @param diagnosis
	 */
	public void setActiveDiagnosis(String diagnosis);
	
	/**
	 * set passive diagnosis
	 * @param diagnosis
	 */
	public void setPassiveDiagnosis(String diagnosis);
	
	/**
	 * Set robot ready state
	 * @param status
	 */
	public void setRobotReady(boolean status);
}
