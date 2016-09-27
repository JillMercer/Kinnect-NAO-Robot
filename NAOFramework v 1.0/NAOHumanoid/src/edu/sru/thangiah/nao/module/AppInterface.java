package edu.sru.thangiah.nao.module;

import edu.sru.thangiah.nao.connection.Connect;

/**
 * Author: Brian Atwell Last Edited, 11/17/2015
 * 
 * Based off of App abstract class by Zach Zearney
 * 
 * @author Brian Atwell
 *
 */

public interface AppInterface {


	/**
	 * Abstract model of an Application. Must contain constructor for connect
	 * 
	 * @throws Exception
	 */

	public void run() throws Exception;

	public void stop() throws Exception;

	public void reset() throws Exception;

}