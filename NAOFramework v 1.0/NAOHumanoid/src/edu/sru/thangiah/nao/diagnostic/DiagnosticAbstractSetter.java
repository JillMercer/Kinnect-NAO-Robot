package edu.sru.thangiah.nao.diagnostic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * DiagnosticAbstractSetter
 * 
 * Allows you to set active Diagnosis, passive Diagnosis, and robot ready status.
 * @author Brian Atwell
 */
public abstract class DiagnosticAbstractSetter extends DiagnosticAbstract implements DiagnosticSetterInterface {
	
	/**
	 * passive diagnosis
	 */
	private String passiveDiagnosis;
	/**
	 * active diagnosis
	 */
	private String activeDiagnosis;
	/**
	 * is robot Ready
	 */
	private boolean isRobotReady=false;
	
	/**
	 * Sets a new Active Diagnosis
	 * @param String active diagnosis
	 */
	@Override
	public void setActiveDiagnosis(String activeDiagnosis)
	{
		this.activeDiagnosis = activeDiagnosis;
		onActiveDiagnosis(activeDiagnosis);
	}
	
	/**
	 * Sets new Passive Diagnosis
	 * @param String passive diagnosis
	 */
	@Override
	public void setPassiveDiagnosis(String passiveDiagnosis){
		this.passiveDiagnosis = passiveDiagnosis;
		onPassiveDiagnosis(passiveDiagnosis);
	}
	
	/**
	 * Sets new Robot ready status
	 * #param boolean status
	 */
	@Override
	public void setRobotReady(boolean status){
		isRobotReady = status;
		onRobotReady(isRobotReady);
	}

	/**
	 * Gets the active diagnosis
	 * @return String
	 */
	@Override
	public String getActiveDiagnosis() {
		// TODO Auto-generated method stub
		return activeDiagnosis;
	}

	/**
	 * Gets the passive diagnosis
	 * @return String
	 */
	@Override
	public String getPassiveDiagnosis() {
		return passiveDiagnosis;
	}
	
	/**
	 * Gets the the robot ready status
	 * @return boolean
	 */
	@Override
	public boolean isRobotReady() {
		// TODO Auto-generated method stub
		return isRobotReady;
	}
	
	

}
