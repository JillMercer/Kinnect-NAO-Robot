package edu.sru.thangiah.nao.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.aldebaran.qi.Session;

public class SynchronizedConnect {

	protected HashMap<String, Robot> robotMap;
	protected boolean isRunning = false;

	public SynchronizedConnect(ArrayList<String> robotIps) {
		robotMap = new HashMap<String, Robot>();
		for (String str : robotIps) {
			Robot add = new Robot(str);
			robotMap.put(add.name(), add);
		}
		System.out.println(robotMap.toString());
		new BlockThread().start();
	}

	public SynchronizedConnect(String ip) {
		robotMap = new HashMap<String, Robot>();
		Robot add = new Robot(ip);
		robotMap.put(add.name(), new Robot(ip));
		new BlockThread().start();
	}

	public ArrayList<Session> getAllSessions() {
		ArrayList<Session> returnVal = new ArrayList<Session>();
		Iterator it = robotMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			returnVal.add(((Robot) pair.getValue()).session());
		}
		return returnVal;
	}

	public ArrayList<String> getAllNames() {
		ArrayList<String> returnVal = new ArrayList<String>();
		Iterator it = robotMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			returnVal.add(((Robot) pair.getValue()).name());
		}
		return returnVal;
	}

	public ArrayList<String> getAllIps() {
		ArrayList<String> returnVal = new ArrayList<String>();
		Iterator it = robotMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			returnVal.add(((Robot) pair.getValue()).ip());
		}
		return returnVal;
	}

	public boolean stopAll() {
		Iterator it = robotMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			((Robot) pair.getValue()).stop();
		}
		isRunning = false;
		return true;
	}

	public boolean stop(String name) {
		robotMap.get(name).stop();
		robotMap.remove(name);
		if (robotMap.isEmpty()) {
			isRunning = false;
		}
		return true;
	}

	public boolean shutdownAll() {
		Iterator it = robotMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Thread t = new Thread() {
				@Override
				public void run() {
					((Robot) pair.getValue()).shutdown();
				}
			};
			t.start();
		}
		robotMap.clear();
		isRunning = false;
		return true;
	}

	public boolean shutdown(String name) {
		Thread t = new Thread() {
			@Override
			public void run() {
				robotMap.get(name).shutdown();
			}
		};
		t.start();

		robotMap.remove(name);
		if (robotMap.isEmpty()) {
			isRunning = false;
		}
		return true;
	}

	public boolean rebootAll() {
		Iterator it = robotMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Thread t = new Thread() {
				@Override
				public void run() {
					((Robot) pair.getValue()).reboot();
				}
			};
			t.start();
		}
		robotMap.clear();
		isRunning = false;
		return true;
	}

	public boolean reboot(String name) {
		Thread t = new Thread() {
			@Override
			public void run() {
				robotMap.get(name).reboot();
			}
		};
		t.start();
		robotMap.remove(name);
		if (robotMap.isEmpty()) {
			isRunning = false;
		}
		return true;
	}

	public boolean addRobot(String ip) {
		if (getAllIps().contains(ip)) {
			return false;
		}
		Robot add = new Robot(ip);
		robotMap.put(add.name(), add);
		if (!isRunning) {
			new BlockThread().start();
		}
		return true;
	}

	public int numSessions() {
		return robotMap.size();
	}

	public int batteryCharge(String name) {
		return robotMap.get(name).batteryCharge();
	}

	public Session getSession(String name) {
		return robotMap.get(name).session();
	}

	public ArrayList<Robot> getAllRobots() {
		ArrayList<Robot> returnVal = new ArrayList<Robot>();
		Iterator it = robotMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			returnVal.add(((Robot) pair.getValue()));
		}
		return returnVal;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public Robot getRobot(String name) {
		return robotMap.get(name);
	}

	private class BlockThread extends Thread {
		@Override
		public void run() {
			isRunning = true;
			while (isRunning) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
