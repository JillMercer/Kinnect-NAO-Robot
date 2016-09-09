package edu.sru.thangiah.demo.redball;

import com.aldebaran.qi.Session;

/*
 * Provides common methods for modules for NAO
 */
public interface CommonModuleInterface {
	
	/*
	 * Initializes the Module
	 */
	public void initialize(Session session);
	
	/*
	 * Runs the Module
	 */
	//public void run();
	
	/*
	 * Stops the Module
	 */
	public void stop();
	
	/*
	 * Start the Module
	 */
	public void start();
	
	/*
	 * Uninitializes the Module
	 */
	public void uninitialize();
}
