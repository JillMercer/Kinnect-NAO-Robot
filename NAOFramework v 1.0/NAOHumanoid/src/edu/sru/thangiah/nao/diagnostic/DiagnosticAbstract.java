package edu.sru.thangiah.nao.diagnostic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * DiagnosticAbstract
 * 
 * Provides getters to active diagnosis, passive diagnosis, and robot ready status.
 * @author Brian Atwell
 */
public abstract class DiagnosticAbstract implements DiagnosticInterface {
	
	/**
	 * List of diagnosticListeners
	 */
	private List<DiagnosticListener> listenerList = new LinkedList<DiagnosticListener>();
	
	/**
	 * Called when an active diagnostic is changed.
	 * @param String diagnosis
	 */
	protected void onActiveDiagnosis(String diagnosis){
		Iterator<DiagnosticListener> iter;
		
		DiagnosticListener diaList;
		
		iter = listenerList.iterator();
		
		// Loop through iterators
		while(iter.hasNext()) {
			diaList = iter.next();
			diaList.ActiveDiagnosisErrorChanged(diagnosis);
		}
	}
	
	/**
	 * Called when a passive diagnostic is changed.
	 * @param String diagnosis
	 */
	protected void onPassiveDiagnosis(String diagnosis){
		Iterator<DiagnosticListener> iter;
		
		DiagnosticListener diaList;
		
		iter = listenerList.iterator();
		
		// Loop through iterators
		while(iter.hasNext()) {
			diaList = iter.next();
			diaList.PassiveDiagnosisErrorChanged(diagnosis);
		}
	}
	
	/**
	 * Called when robot ready status is changed.
	 * @param boolean status
	 */
	protected void onRobotReady(boolean status) {
		Iterator<DiagnosticListener> iter;
		
		DiagnosticListener diaList;
		
		iter = listenerList.iterator();
		
		// Loop through iterators
		while(iter.hasNext()) {
			diaList = iter.next();
			diaList.RobotIsReady(status);
		}
		
	}

	/**
	 * Add a listener to the diagnostic
	 * A listener receives calls when an event happens
	 * @param DiagnosticListener
	 */
	public void addDiagnosticListener(DiagnosticListener listener) {
		// TODO Auto-generated method stub
		listenerList.add(listener);
	}
	
	/**
	 * Remove a listener to the diagnostic
	 * A listener receives calls when an event happens
	 * @param DiagnosticListener
	 */
	public void removeDiagnosticListener(DiagnosticListener listener) {
		listenerList.remove(listener);
	}

}
