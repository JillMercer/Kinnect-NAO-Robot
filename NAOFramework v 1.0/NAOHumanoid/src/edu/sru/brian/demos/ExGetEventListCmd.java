package edu.sru.brian.demos;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;

import edu.sru.thangiah.nao.connection.Connection;

/**
 * Gets all the possible Events.
 * Some Events are not documented on the NAO website.
 * This dumps all events to the console. 
 * 
 * @author Brian Atwell
 *
 */
public class ExGetEventListCmd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection;
		Session session;
		ALMemory memory;
		
		connection = new Connection();
		
		if(!connection.connectToNao("192.168.1.146"))
		{
			System.out.println("Failed to connect to robot");
			return;
		}
		
		session = connection.getSession();
		
		try {
			memory = new ALMemory(session);
			
			//Get event list
			System.out.println(memory.getEventList());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
