package edu.sru.thangiah.nao.connection;

import java.util.ArrayList;
import java.util.HashMap;

import edu.sru.thangiah.nao.demo.Demo;

public class SynchronizedConnectDemo extends SynchronizedConnect {

	private HashMap<Integer, Demo> registeredDemos;
	private ArrayList<String> activeRobots;

	public SynchronizedConnectDemo(ArrayList<String> robotIps) {
		super(robotIps);
		activeRobots = new ArrayList<String>();
		registeredDemos = new HashMap<Integer, Demo>();
		// TODO Auto-generated constructor stub
	}

	public SynchronizedConnectDemo(String ip) {
		super(ip);
		activeRobots = new ArrayList<String>();
		registeredDemos = new HashMap<Integer, Demo>();
	}

	public void stopDemo(int id) {
		Demo d = registeredDemos.get(id);
		unregisterDemo(id);
		try {
			d.exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getAllInactiveNames() {
		ArrayList<String> returnVal = getAllNames();
		for (String str : activeRobots) {
			returnVal.remove(str);
		}
		return returnVal;
	}

	public void registerDemo(Demo d) throws Exception {
		for (String str : d.getRobotNames()) {
			if (robotMap.get(str).demoRunning()) {
				throw new Exception("ERR: DEMO ALREADY RUNNING");
			}
		}
		registeredDemos.put(d.id, d);
		for (String str : d.getRobotNames()) {
			robotMap.get(str).runDemo(d);
			activeRobots.add(str);
		}
	}

	public void stopDemo(String robotName) {
		registeredDemos.get(robotMap.get(robotName).demoId()).removeRobot(robotName);
		activeRobots.remove(robotName);
		robotMap.get(robotName).stopDemo();
	}

	public boolean stopAllDemos() {
		for (String name : activeRobots) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						stopDemo(name);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
		return true;
	}

	public void unregisterDemo(int id) {
		if (registeredDemos.containsKey(id)) {
			ArrayList<String> names = registeredDemos.get(id).getRobotNames();
			for (String str : names) {
				activeRobots.remove(str);
				robotMap.get(str).stopDemo();
			}
			registeredDemos.remove(id);
		}
	}

}
