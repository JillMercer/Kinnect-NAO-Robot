package edu.sru.thangiah.nao.connection;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

/**
 * @author Justin Cather
 *
 */


// Junk class to act as a stub for calls to addActiveListener.
class NaoBonjour implements ServiceListener{

	@Override
	public void serviceAdded(ServiceEvent arg0) {
	}

	@Override
	public void serviceRemoved(ServiceEvent arg0) {
	}

	@Override
	public void serviceResolved(ServiceEvent arg0) {
	}
	
}

public class Network {
	private static final String BONJOUR_SERVICE_TYPE = "_http._tcp.local.";
	private static final String[] NAOS = {"Aegon", "Ophelia", "Hermoine", "KingLear", "Lady MacBeth", 
			"Octavius", "Hamlet", "Lady Montague", "Hermione", "Portia", "Gandolf", "Bubba"};
	
	public Network() {
	}
	
	/**
	 * Searches the network that the PC is connected to for NAOs. 
	 * @return A hashmap of key-value pairs where
	 * @key is the NAO name.
	 * @value is the NAO IP address.
	 */
	public HashMap<String, String> searchForNAOs() throws IOException{
		HashMap<String, String> namesIPs = new HashMap<String, String>();
		JmDNS bonjourService = JmDNS.create();
		bonjourService.addServiceListener(BONJOUR_SERVICE_TYPE, new NaoBonjour());
		ServiceInfo[] serviceInfos = bonjourService.list(BONJOUR_SERVICE_TYPE);
				
		// Iterate over the array of detected devices.
		for (ServiceInfo info : serviceInfos) {
			String name = info.getName().trim();
			
			// See if the detected device has a name that is in the NAOS array.
			if (Arrays.asList(NAOS).contains(name)) {
				String ip = info.getInet4Addresses()[0].getHostAddress().trim();
				namesIPs.put(name, ip);
			}
		}
			
		// Close session and return the hashmap.
		bonjourService.close();
		return namesIPs;
	}
	
	/**
	public static void main(String[] args) throws IOException{
		Network network = new Network();
		HashMap<String, String> robots = network.searchForNAOs();
		
		for(Entry<String, String> pair : robots.entrySet()){
			System.out.println(pair.getKey() + " " + pair.getValue());
		}
	}
	*/
	
	public HashMap<String, String> searchAll() throws IOException{
		HashMap<String, String> namesIPs = new HashMap<String, String>();
		JmDNS bonjourService = JmDNS.create();
		bonjourService.addServiceListener(BONJOUR_SERVICE_TYPE, new NaoBonjour());
		ServiceInfo[] serviceInfos = bonjourService.list(BONJOUR_SERVICE_TYPE);
				
		for (ServiceInfo info : serviceInfos) {
			String name = info.getName().trim();
			String ip = info.getInet4Addresses()[0].getHostAddress().trim();
			namesIPs.put(name, ip);
		}
			
		// Close session and return the hashmap.
		bonjourService.close();
		return namesIPs;
	}
}
