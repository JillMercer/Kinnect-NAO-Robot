package edu.sru.thangiah.nao.connection;

/** Author: Zachary Kearney
Last Edited, 9/24/2015
* @author zrk1002
*
*/

import com.aldebaran.qi.Application;
import com.aldebaran.qi.Session;

import edu.sru.thangiah.nao.module.ModuleInterface;

public interface ConnectInterface {

	public void run() throws Exception;
	
	public void stop() throws Exception;
	
	public void reset() throws Exception;
	
	public void changeIp(String ip, String port) throws Exception;
	
	public Application getApplication();
	
	public Session getSession();
	
	public boolean getStatus();
	
	public String getName() throws Exception;
	
	public int getBatteryCharge() throws Exception;
	
	public void shutdown() throws Exception;
	
	public void reboot() throws Exception;
	

	
	
	
	
	
}
