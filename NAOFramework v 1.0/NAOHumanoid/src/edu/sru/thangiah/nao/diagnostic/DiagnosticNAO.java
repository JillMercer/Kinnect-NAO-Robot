package edu.sru.thangiah.nao.diagnostic;
import java.util.LinkedList;
import java.util.List;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALDiagnosis;
import com.aldebaran.qi.helper.proxies.ALMemory;


/**
 * NAODiagnostic
 * implements Diagnostic for the NAO robot using 
 * ALDiagnostic events.
 * @author Brian Atwell
 */

public class DiagnosticNAO extends DiagnosticAbstract {
	
	/**
	 * Original NAO framework classes
	 */
	private ALDiagnosis alDiagnosis;
	private ALMemory memory;
	
	private String passiveDiagnosis;
	private String activeDiagnosis;
	private boolean isRobotReady=false;

	public DiagnosticNAO(Session session) {
			
		try {
			alDiagnosis = new ALDiagnosis(session);
			memory = new ALMemory(session);
			memory.subscribeToEvent("ActiveDiagnosisErrorChanged", "setActiveDiagnosis::(s)", this);
			memory.subscribeToEvent("PassiveDiagnosisErrorChanged", "setPassiveDiagnosis::(s)", this);
			memory.subscribeToEvent("ALDiagnosis/RobotIsReady", "setRobotReady::()", this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets an active diagnosis
	 */
	protected void setActiveDiagnosis(String activeDiagnosis)
	{
		this.activeDiagnosis = activeDiagnosis;
		onActiveDiagnosis(activeDiagnosis);
	}
	
	/**
	 * Sets an passive diagnosis
	 */
	protected void setPassiveDiagnosis(String passiveDiagnosis){
		this.passiveDiagnosis = passiveDiagnosis;
		onPassiveDiagnosis(passiveDiagnosis);
	}
	
	/**
	 * Sets robot state
	 */
	protected void setRobotReady(boolean status){
		isRobotReady = status;
		onRobotReady(isRobotReady);
	}

	/**
	 * Gets active Diagnosis
	 * @return String
	 */
	@Override
	public String getActiveDiagnosis() {
		// TODO Auto-generated method stub
		return this.activeDiagnosis;
	}

	/**
	 * Gets passive diagnosis
	 */
	@Override
	public String getPassiveDiagnosis() {
		// TODO Auto-generated method stub
		return this.passiveDiagnosis;
	}

	/**
	 * Gets robot ready state
	 */
	@Override
	public boolean isRobotReady() {
		// TODO Auto-generated method stub
		return this.isRobotReady;
	}
	
}
