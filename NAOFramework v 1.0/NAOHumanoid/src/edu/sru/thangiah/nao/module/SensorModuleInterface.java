package edu.sru.thangiah.nao.module;

/**
 * Author: Brian Atwell
 * Date Created: 11/17/2015
 * Last Modified: 11/17/2015
 * 
 * This provides enable and disable methods for sensors
 * that need enabled/started before they can be used.
 * These sensors may use more resources (such as battery power,
 * processing, etc.)
 * 
 * @author bsa7332
 *
 */

public interface SensorModuleInterface {
	
	/**
	 * Starts the sensor or resource. The device may use more 
	 * battery power, processing, or other resource.
	 */
	public void enable();
	
	/**
	 * Stops the sensor or resource. By disabling a device it
	 * may save battery power, processing, or other resource.
	 */
	public void disable();
}
