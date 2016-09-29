package edu.sru.thangiah.nao.module;

import com.aldebaran.qi.Session;

/**
 * Author: Zachary Kearney Last Edited, 11/5/2015
 * 
 * @author zrk1002
 *
 */

public abstract class Module implements ModuleInterface {

	/**
	 * Abstract model of a Module. Must contain constructor for connect
	 * 
	 * @throws Exception
	 */
	
	public abstract void exit() throws Exception;

	public abstract void reset() throws Exception;

	public Module(Session session) throws Exception {
	}

}