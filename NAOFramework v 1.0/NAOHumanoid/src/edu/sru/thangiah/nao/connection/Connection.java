package edu.sru.thangiah.nao.connection;

import java.util.concurrent.TimeUnit;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;


// Justin Cather
// NAO Connection class

// Edited by: Brian Atwell
// Date: 10/2015
// Added a static Application object for the new API
public class Connection
{
	private static final String PORT = "9559";
	private Session session;
	private String ip;
	static private Application app;
	
	static {
		String[] locArgs;
		locArgs = new String[1];
		locArgs[0] = new String("");
		app = new Application(locArgs);
	}

	// default constructor
	// ===================
	/*Connection()
	{
		Future<Void> future = null;
		setIp("192.168.0.101");
		session = new Session();
		try 
		{
			future = session.connect("tcp://"+ ip +":"+ PORT);
			synchronized (future) 
			{
				future.wait(1000);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error Connecting.");
			e.printStackTrace();
		}
	}*/

	/*
	 * Check if the IP address is valid
	 * Connect to the IP address
	 * Ping the IP address
	 * Set the value for isConnected
	 * Sam T.
	 */
	// arg constructor for IP address
	// ==============================
	/*Connection(String newIp)
	{
		Future<Void> future = null;
		setIp(newIp);
		session = new Session();
		try 
		{
			future = session.connect("tcp://"+ ip +":"+ PORT);
			synchronized (future) 
			{
				future.wait(1000);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error Connecting.");
			e.printStackTrace();
		}
	}
	*/

	/**
	 *  connect to the robot - default ip address
	 * @return
	 */
	public boolean connectToNao()
	{

		Future<Void> future = null;
		setIp("192.168.0.101");

		session = new Session();

		try 
		{
			future = session.connect("tcp://"+ ip +":"+ PORT);

			synchronized (future) 
			{
				future.wait(1000);
			}
			
			if(session.isConnected())
			{
				System.out.println("Connected to NAO Robot");
			}
			
			return session.isConnected();
		}
		catch(Exception e)
		{
			System.out.println("Error Connecting.");
			e.printStackTrace();
		}
		
		return false;
	}
	


	/**
	 *
	 * @param ipAdd
	 * @return
	 */
	public boolean connectToNao(String ipAdd)
	{
		Future<Void> future = null;
		//
		ip=ipAdd;
		
		setIp(ipAdd);

		session = new Session();

		try 
		{
			future = session.connect("tcp://"+ ip +":"+ PORT);
			synchronized (future) 
			{
				future.wait(1000);
			}
			
			if(session.isConnected())
			{
				System.out.println("Connected to NAO Robot");
			}
			return session.isConnected();
		}
		catch(Exception e)
		{
			System.out.println("Error Connecting.");
			e.printStackTrace();
			return false;
		}
	}
	

	/**
	 * Connect by sending IP address and port
	 * @param ipAdd
	 * @param port
	 * @return
	 */
	public boolean connectToNao(String ipAdd, String port)
	{
		Future<Void> future = null;
		setIp(ipAdd);

		session = new Session();

		try 
		{
			future = session.connect("tcp://"+ ip +":"+ port);
			synchronized (future) 
			{
				future.wait(1000);
			}
			
			if(session.isConnected())
			{
				System.out.println("Connected to NAO Robot");
			}
			return session.isConnected();
		}
		catch(Exception e)
		{
			System.out.println("Error Connecting.");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Disconnect from NAO
	 * @return
	 */
	public boolean disconnectFromNao()
	{
		try 
		{
			session.close();
			return !session.isConnected();
		}
		catch(Exception e)
		{
			System.out.println("Error disconnecting.");
			e.printStackTrace();
			return false;
		}
	}
	

	// Setters and Getters
	// ===================
	public Session getSession() 
	{
		return session;
	}

	public String getIp() 
	{
		return ip;
	}

	public void setIp(String ip) 
	{
		this.ip = ip;
	}

	/*
	 * Return the connection status to the IP address
	 * (non-Javadoc)
	 * @see edu.sru.thangiah.nao.connection.ConnectionInterface#isConnected()
	 * Sam T.
	 */
	public boolean isConnected()
	{
		//check the status of the connection
		return session.isConnected();		
	}
}
