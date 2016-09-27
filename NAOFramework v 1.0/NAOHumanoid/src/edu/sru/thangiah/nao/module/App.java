package edu.sru.thangiah.nao.module;

import edu.sru.thangiah.nao.connection.SynchronizedConnect;

/**
 * Author: Zachary Kearney Last Edited, 11/10/2015
 * 
 * @author zrk1002
 *
 */

public abstract class App {

	public App(SynchronizedConnect connect) throws Exception{
	}

	/**
	 * Abstract model of an Application. Must contain constructor for connect
	 * 
	 * @throws Exception
	 */

	public abstract void run() throws Exception;

	public void stop() throws Exception {

	}

	public void reset() throws Exception {

		stop();
		run();

	}

}