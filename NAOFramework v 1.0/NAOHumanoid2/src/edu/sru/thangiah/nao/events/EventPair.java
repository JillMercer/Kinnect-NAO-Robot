package edu.sru.thangiah.nao.events;

/** Stores the event name and event ID of a subscribed event.
 * @author Justin Cather
 *
 */
public class EventPair {
	public String eventName;
	public long eventID;
	
	public EventPair(){
		
	}
	
	public EventPair(String eventName){
		this.eventName = eventName;
	}
	
	public EventPair(String eventName, long eventID){
		this.eventName = eventName;
		this.eventID = eventID;
	}
}
