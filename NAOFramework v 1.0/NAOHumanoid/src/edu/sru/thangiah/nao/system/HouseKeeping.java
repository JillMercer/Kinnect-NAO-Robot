package edu.sru.thangiah.nao.system;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALAutonomousLife;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALSystem;

import edu.sru.thangiah.nao.DebugSettings;
import edu.sru.thangiah.nao.awareness.Awareness;
import edu.sru.thangiah.nao.awareness.enums.Appendage;
import edu.sru.thangiah.nao.awareness.enums.EngagementModes;
import edu.sru.thangiah.nao.awareness.enums.LifeState;
import edu.sru.thangiah.nao.awareness.enums.TrackingModes;
import edu.sru.thangiah.nao.events.EventMaintenanceInterface;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.module.Module;
import edu.sru.thangiah.nao.speech.texttospeech.TextToSpeech;

/** Class to maintain the NAO by managing events the NAO is subscribed to
 * and allowing the restoration of the NAO to a default state by calling reset().
 * @author Justin Cather
 *
 */
public class HouseKeeping extends Module implements EventMaintenanceInterface {	
	private Session session;
	ALMemory mem;
	private Hashtable<String, Long> events;

	public HouseKeeping(Session session) throws Exception{	
		super(session);
		try {		
			this.session = session;
			mem =  new ALMemory(session);
			events = new Hashtable<String,Long>();
		} 
		catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
		}	
	}
	
	/**
	 * Resets the NAO to a default state. The NAO will go to a standing posture, 
	 * setting breath and life to on and unsubscribing to any events.
	 */
	public void reset(){
		try {
			ALRobotPosture posture = new ALRobotPosture(this.session);
			TextToSpeech tts =  new TextToSpeech(this.session);
			Awareness aware = new Awareness(this.session);
			
			aware.setAwareness(true);
			aware.setAllStimulus(true);
			aware.setEngagementMode(EngagementModes.SemiEngaged);
			aware.setTrackingMode(TrackingModes.Head);
			aware.setAllBreathing(true);
			aware.setIdlePostureEnabled(Appendage.Body, true);
			aware.setLifeState(LifeState.solitary);
			
			posture.goToPosture("Stand", 0.5f);		
			tts.reset();			
			
			this.removeAllEvents();
			tts.exit();
			aware.exit();
			
		} 
		catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
		}
	}
	
	/**
	 * Physically reboots the NAO. Stops all running applications and 
	 * processes. The NAO will go down for shutdown and then restart.
	 */
	public void hardReset(){
		try {
			ALSystem system = new ALSystem(session);
			system.reboot();
			
		} catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
		}
	}
	
	/**
	 * Remove and unsubscribe to all events.
	 */
	public void removeAllEvents(){
		Enumeration<String> iterator = events.keys();
		
		while (iterator.hasMoreElements()){
			removeEvent(iterator.nextElement());
		}
	}
	
	/**
	 * Gets an iterator to iterator through the added events.
	 * @return An iterator of event names.
	 */
	public Iterator<String> getEvents(){
		Enumeration<String> enumeration = events.keys();
		Iterator<String> iterator = new Iterator<String>() {

			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public String next() {
				return enumeration.nextElement();
			}
		};
		
		return iterator;
	}

	@Override
	public boolean removeEvent(String eventName) {
		boolean isGood = false;
		
		if (isEventSubscribed(eventName)){
			long event = events.get(eventName);
				
			try {
				mem.unsubscribeToEvent(event);
				events.remove(eventName);
				isGood = true;
			} 
			catch (Exception ex) {
				isGood = false;
			}
		}
		
		return isGood;
	}

	@Override
	public boolean addEvent(EventPair eventPair){
		boolean isGood = false;
		
		if(!isEventSubscribed(eventPair.eventName)){
			events.put(eventPair.eventName, eventPair.eventID);
			isGood = true;
		}
		
		return isGood;
	}
	
	@Override
	public boolean addEvent(String eventName, Long eventID) {
		boolean isGood = false;
		
		if(!isEventSubscribed(eventName)){
			events.put(eventName, eventID);
			isGood = true;
		}
		
		return isGood;
	}

	@Override
	public boolean isEventSubscribed(String eventName) {
		if (events.containsKey(eventName)){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void exit() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
