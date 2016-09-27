package edu.sru.thangiah.nao.events;

/** Implemented by the housekeeping class to manage event subscriptions.
 * @author Justin Cather
 *
 */
public interface EventMaintenanceInterface {	
	
	/**
	 * Unsubscribe an event.
	 * @param eventName Name of event to unsubscribe from.
	 * @return True if successful, false if not.
	 */
	public boolean removeEvent(String eventName);	
	
	/**
	 * Add a subscribed event.
	 * @param eventName The name of the event.
	 * @param eventID The ID of the event.
	 * @return True if successful, false if not.
	 */
	public boolean addEvent(String eventName, Long eventID);
	
	/**
	 * Add a subscribed event.
	 * @param eventPair The EventPair to add.
	 * @return True if successful, false if not.
	 */
	public boolean addEvent(EventPair eventPair);
	
	/**
	 * Checks if the event has been subscribed to.
	 * @param eventName The name of the event.
	 * @return True if subscribed to, false if not.
	 */
	public boolean isEventSubscribed(String eventName);

}
