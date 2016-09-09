package edu.sru.thangiah.nao.demo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.aldebaran.qi.helper.proxies.ALMemory;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;

/**
 * Author: Zachary Kearney Last Edited, 5/5/2016
 * 
 * @author zrk1002 Abstract class for a demo. Provides methods to add robots,
 *         execute, safe close, etc. Demo name, robot name, and any options must
 *         be declared in the constructor. An applications option dialog can be
 *         used to choose robots and options.
 *
 */

public abstract class Demo {

	protected SynchronizedConnectDemo connection;
	protected ALMemory memory;
	private boolean running = true;
	protected ArrayList<DemoRobot> robots;
	protected ArrayList<String> robotNames;
	protected String demoName;
	private static AtomicInteger nextId = new AtomicInteger();
	public final int id;
	private boolean ready = false;

	public Demo(SynchronizedConnectDemo connection) throws Exception {
		this.connection = connection;
		id = nextId.incrementAndGet();
		robots = new ArrayList<DemoRobot>();
		robotNames = new ArrayList<String>();
	}

	/**
	 * Initializes run method and sets demo to ready.
	 * 
	 * @throws Exception
	 */
	public final void start() throws Exception {
		if (robotNames.isEmpty()) {
			running = false;
			return;
		}
		run();
		connection.registerDemo(this);
		ready = true;
	}

	/**
	 * The creation of demo robots is done here. Different demo robots can be
	 * instantiated, but they will all run the same execute methods. DemoRobots
	 * must be added to the robots ArrayList to function properly. SEE
	 * DemoRobot.java
	 * 
	 * @throws Exception
	 */
	protected abstract void run() throws Exception;

	/**
	 * Executes all robot execute methods. Used for synchronization.
	 * 
	 * @throws Exception
	 */
	public final void executeAll() throws Exception {
		for (DemoRobot robot : robots) {
			new RobotThread(robot, ThreadType.EXECUTE).start();
		}
	}

	/**
	 * Exits all robots safely and securely, and finishes the demo.
	 * 
	 * @throws Exception
	 */
	public final void exit() throws Exception {
		for (DemoRobot robot : robots) {
			new RobotThread(robot, ThreadType.EXIT).start();
		}
		for (int i = 0; i < robots.size(); i++) {
			while (!robots.get(i).isFin()) {
				Thread.sleep(100);
			}
		}
		connection.unregisterDemo(id);
		running = false;
	}

	/**
	 * Used for exit and executeAll methods.
	 * 
	 * @author zrk1002
	 *
	 */
	private class RobotThread extends Thread {
		DemoRobot robot = null;
		ThreadType type = null;

		public RobotThread(DemoRobot robot, ThreadType type) {
			this.robot = robot;
			this.type = type;
		}

		@Override
		public void run() {
			switch (type) {
			case EXECUTE:
				try {
					robot.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case EXIT:
				try {
					robot.stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Returns true if demo is running.
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	private enum ThreadType {
		EXECUTE, EXIT
	}

	/**
	 * Returns all the active robot names.
	 * 
	 * @return
	 */
	public final ArrayList<String> getRobotNames() {
		return robotNames;
	}

	/**
	 * Removes a robot from the demo, and ends it if no other robots are
	 * available.
	 * 
	 * @param name
	 */
	public void removeRobot(String name) {
		DemoRobot removeBot;
		for (int i = 0; i < robots.size(); i++) {
			if (robots.get(i).name().equals(name)) {
				removeBot = robots.get(i);
				try {
					removeBot.stop();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				robots.remove(i);
				break;
			}
		}
		if (robots.isEmpty()) {
			try {
				exit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the name of the demo.
	 * 
	 * @return
	 */
	public final String getDemoName() {
		return demoName;
	}

}
