package edu.sru.brian.net;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.Iterator;

/**
 * Class to hold Host data.
 * Such as IPv4 addresses, IPv6 addresses, Name, type, and port.
 * 
 * @author Brian Atwell
 *
 */
/**
 * @author bsa7332
 *
 */
public class HostData {

	private String type;
	private String name;
	private int port;
	private Inet4Address[] ipv4;
	private Inet6Address[] ipv6;
	
	/**
	 * Create a new HostData
	 * with (service type, host name, port, IP address version 4, IP address version 6)
	 * @param type 
	 * @param name
	 * @param port
	 * @param IPv4
	 * @param IPv6
	 */
	public HostData(String type, String name, int port, Inet4Address[] ipv4, Inet6Address[] ipv6)
	{
		this.type = type;
		this.name = name;
		this.port = port;
		this.ipv4 = ipv4;
		this.ipv6 = ipv6;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public Inet4Address[] getIPv4Addresses() {
		return ipv4;
	}
	
	public String getFirstIPv4Address() {
		if(ipv4.length > 0)
		{
			return ipv4[0].getHostAddress();
		}
		
		
		return null;
	}
	
	public Inet6Address[] getIPv6Addresses() {
		return ipv6;
	}
	
	public String getFirstIPv6Address() {
		
		if(ipv6.length > 0)
		{
			return ipv6[0].getHostAddress();
		}
		
		return null;
	}
	
	/**
	 * Get HostData by host name
	 * @param iterator
	 * @param name
	 * @return
	 */
	public static HostData findByName(Iterator<HostData> iterator, String name)
	{
		HostData hostData = null;
		HostData curData = null;
		
		while(iterator.hasNext() && hostData == null)
		{
			curData = iterator.next();
			
			if(name.equals(curData.getName()))
			{
				hostData = curData;
			}
		}
		
		return hostData;
	}
	
	/**
	 * Get the HostData by IP version 4
	 * @param iterator
	 * @param ip
	 * @return
	 */
	public static HostData findByIPv4(Iterator<HostData> iterator, String ip)
	{
		HostData hostData = null;
		HostData curData = null;
		Inet4Address ipAddresses[];
		
		while(iterator.hasNext() && hostData == null)
		{
			curData = iterator.next();
			ipAddresses = curData.getIPv4Addresses();
			
			for(int i=0; i<ipAddresses.length; i++)
			{
				if(ip.equals(ipAddresses[i].getHostAddress()))
				{
					hostData = curData;
				}
				
			}
		}
		
		return hostData;
	}
	
	/**
	 * Get the HostData by IP version 6
	 * @param iterator
	 * @param ip
	 * @return
	 */
	public static HostData findByIPv6(Iterator<HostData> iterator, String ip)
	{
		HostData hostData = null;
		HostData curData = null;
		Inet6Address ipAddresses[];
		
		while(iterator.hasNext() && hostData == null)
		{
			curData = iterator.next();
			ipAddresses = curData.getIPv6Addresses();
			
			for(int i=0; i<ipAddresses.length; i++)
			{
				if(ip.equals(ipAddresses[i].getHostAddress()))
				{
					hostData = curData;
				}
				
			}
		}
		
		return hostData;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		
		sb.append("[HostData [Name: ");
		sb.append(this.name);
		sb.append("] [Type: ");
		sb.append(this.type);
		sb.append("] [Port: ");
		sb.append(this.port);
		sb.append("] [IPv4: ");
		
		for(int i=0; i<ipv4.length;i++)
		{
			sb.append(" ["+ipv4[i].getHostAddress()+"]");
		}
		
		//sb.append(ipv4);
		sb.append("] [IPv6: ");
		
		for(int i=0; i<ipv6.length;i++)
		{
			sb.append(" ["+ipv6[i].getHostAddress()+"]");
		}
		
		//sb.append(ipv6);
		sb.append("]]");
		
		return sb.toString();
	}
	
	
}
