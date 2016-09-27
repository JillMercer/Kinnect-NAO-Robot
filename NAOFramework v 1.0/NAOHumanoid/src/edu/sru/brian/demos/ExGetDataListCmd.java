package edu.sru.brian.demos;

import java.util.LinkedList;
import java.util.List;








import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALInfrared;
import com.aldebaran.qi.helper.proxies.ALMemory;

import edu.sru.brian.newapi.demo.RobotConfig;
import edu.sru.thangiah.nao.connection.Connection;

/**
 * @file ExGetDataListCmd
 * 
 * Gets the list of all the data keys from the NAO robot.
 * Useful for getting undocumented data keys and checking for continued support for certain features.
 * Example: certain Infrared data keys do not exist on the latest version of the robot making Infrared sensors unusable.
 * 
 * 
 * @author Brian Atwell
 *
 */
public class ExGetDataListCmd {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection;
		Session session;
		ALMemory memory = null;
		ALInfrared infrared = null;
		
		List<String> keys;
		
		keys = new LinkedList<String>();
		keys.add("IR");
		
		connection = new Connection();
		
		if(!connection.connectToNao(RobotConfig.IP))
		{
			System.out.println("Failed to connect to robot");
			return;
		}
		
		session = connection.getSession();
		
		try {
			memory = new ALMemory(session);
			infrared = new ALInfrared(session);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			System.out.println(memory.getDataList(""));
			//System.out.println((String)memory.getDescriptionList(keys));
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
