package edu.sru.thangiah.nao.connection;

import java.util.ArrayList;
import java.util.HashMap;

import edu.sru.thangiah.nao.connection.Network;

public class RobotList {

	private int numRobot;
	private ArrayList<String> names;
	private ArrayList<String> ips;
	
	public RobotList(){

		names = new ArrayList<String>();
		ips = new ArrayList<String>();
		numRobot = 0;
	}
	
	public void ignore(String ip){
		for(int i = 0; i < ips.size(); i++){
			if(ips.get(i).equals(ip)){
				ips.remove(i);
				names.remove(i);
				numRobot--;
			}
		}
	}
	    
	public void clear() throws Exception{
		
		names.clear();
		ips.clear();
		numRobot = 0;
		
	}
	public void run() throws Exception{
		
		Network network = new Network();
		HashMap<String,String>map = network.searchAll();

		for(HashMap.Entry<String, String> entry : map.entrySet()){
			System.out.println((String)entry.getKey());
			String name = (String)entry.getKey();
			String ip = (String)entry.getValue();
			names.add(name);
			ips.add(ip);
			System.out.println(numRobot + "::" + name + "::" + ip);
			numRobot++;			
			}	
	}
	
	public int getNumRobot(){
		return numRobot;
	}
	
	public ArrayList<String> getAllNames(){
		
		return names;
	}
	
	public ArrayList<String> getAllIp(){

		return ips;
	}
	
	
	public String getName(int index){
			return names.get(index);
	}
	
	public String getIp(int index){
			return ips.get(index);
	}
	
}
