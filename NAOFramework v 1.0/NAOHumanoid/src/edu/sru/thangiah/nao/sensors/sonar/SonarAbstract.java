/**
 * @File SonarAbstract.java
 * 
 * @Description Provides event handler methods of the form onEventName.
 * Example onSonarChanged. Also provides linked list of event listeners
 * 
 * implements add/remove listener
 * @author Brian Atwell
 * @date 2015
 * @lastModified 10/27/2015
 */
package edu.sru.thangiah.nao.sensors.sonar;

import java.lang.instrument.IllegalClassFormatException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public abstract class SonarAbstract implements SonarInterface {
	
	private List<SonarDetectionListener> listDetectionListeners = new LinkedList<SonarDetectionListener>();
	private List<SonarPeriodicListener> listPeriodicListeners = new LinkedList<SonarPeriodicListener>();
	
	/**
	 * Called by the class that implements SonarAbstract when a 
	 * sonar data has changed
	 * 
	 * @param loc: the location of the Sonar device
	 * @param echoes: the distance to objects in sonar's visual path
	 */
	protected void onSonarChanged(int loc, Float[] echoes) {
		Iterator<SonarPeriodicListener> iter;
		iter = listPeriodicListeners.iterator();
		while(iter.hasNext()) {
			iter.next().sonarChanged(loc, echoes);
		}
	}
	
	/**
	 * Called when objects are detected in the sonar's visual path
	 * 
	 * @param loc: the location of the Sonar device
	 * @param echoes: the distance to objects in sonar's visual path
	 */
	protected void onSonarDetected(int loc, float distance) {
		Iterator<SonarDetectionListener> iter;
		iter = listDetectionListeners.iterator();
		
		while(iter.hasNext()) {
			iter.next().sonarDetected(loc, distance);
		}
		
	}
	
	/**
	 * Called when a object is too close to get a
	 * 
	 * @param loc: the location of the Sonar device
	 * @param echoes: the distance to objects in sonar's visual path
	 */
	protected void onSonarNothingDetected(int loc, float distance) {
		Iterator<SonarDetectionListener> iter;
		iter = listDetectionListeners.iterator();
		
		while(iter.hasNext()) {
			iter.next().sonarNothingDetected(loc, distance);
		}
	}
	
	/**
	 * Adds a SonarDetectionListener that is called
	 * when a sonar detection event occurs.
	 * @param listener
	 */
	@Override
	public void addSonarDetectionListener(SonarDetectionListener listener) {
		listDetectionListeners.add(listener);
		
	}
	
	/**
	 * Adds a SonarPeriodicListener that is called when a sonar wave is
	 * updated.
	 * @param listener
	 */
	@Override
	public void addSonarPeriodicListener(SonarPeriodicListener listener) {
		listPeriodicListeners.add(listener);
	}
	
	/**
	 * Removes a certain Sonar detection listener
	 * @param listener
	 */
	@Override
	public void removeSonarDetectionListener(SonarDetectionListener listener) {
		listDetectionListeners.remove(listener);
	}
	
	/**
	 * Removes a certain Sonar Period Listener 
	 */
	@Override
	public void removeSonarPeriodicListener(SonarPeriodicListener listener) {
		listPeriodicListeners.remove(listener);
	}
	
	/**
	 * Returns the list of Sonar Detection listeners. Each listener has to be 
	 * called when a sonar detection event occurs
	 * 
	 * @return list of detection listeners
	 */
	protected List<SonarDetectionListener> getSonarDetectionListenerList() {
		return listDetectionListeners;
	}
	
	/**
	 * Returns the list of Sonar Periodic listeners. Each Listener has to be 
	 * called when a sonar periodic event occurs
	 * @return
	 */
	protected List<SonarPeriodicListener> getSonarPeriodicListenerList() {
		return listPeriodicListeners;
	}

}
