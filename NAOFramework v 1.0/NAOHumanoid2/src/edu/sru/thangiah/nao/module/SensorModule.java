package edu.sru.thangiah.nao.module;

import com.aldebaran.qi.Session;

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
 * 
 * @author bsa7332
 *
 */

public abstract class SensorModule extends Module implements SensorModuleInterface, ModuleInterface{
	
	public SensorModule(Session session) throws Exception {
		super(session);
		// TODO Auto-generated constructor stub
	}

	public void exit() throws Exception{
		disable();
	}
}
