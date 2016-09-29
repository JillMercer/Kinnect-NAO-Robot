package edu.sru.thangiah.nao.connection;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALBattery;
import com.aldebaran.qi.helper.proxies.ALSystem;

/**Connect class for nao Robot. Only one instance may run at a time. Connect
* to ALModules by instantiating the object with Connect.getSession() passed
* as constructor. EX- Module mod = new Module(connection.getSession());
* Running the stop method will end all running applications and close the
* connection completely. Recommended to rest the robot before running the
* stop feature.
Last Edited, 9/24/2015
* @author Zachary Kearney
*/

public class Connect implements ConnectInterface {

	private Application application;
	private Session session;
	private boolean isRunning = false;
	private String IPConnect;
	private ALSystem system;
	private ALBattery battery;
	private String ip = "";


	/**
	* Default Constructor to connect to RobotConfig IP;
	*/
	public Connect() {

		IPConnect = RobotConfig.getConnectionString();

	}

	/**
	 * Constructor to connect to a certain IP and default 9559 Port;
	 * @param ip The IP to connect with.
	 */
	public Connect(String ip) {

		IPConnect = ("tcp://" + ip + ":9559");
		this.ip = ip;

	}

	/**
	 * Constructor to connect to a certain IP and Port;
	 * @param ip The IP to connect with.
	 * @param port The port to connect with.
	 */

	public Connect(String ip, String port) {

		IPConnect = ("tcp://" + ip + ":" + port);
		this.ip = ip;

	}

	/**
	 * Runs the connect module, creating a new application and session and
	 * allowing other modules to function;
	 */
	public void run() throws Exception {

		if (!isRunning) {
			String[] args = { "" };
			System.out.println("Connecting to " + IPConnect);
			application = new Application(args, IPConnect);
			application.start();
			System.out.println("Robot is connected");
			session = application.session();
			system = new ALSystem(session);
			battery = new ALBattery(session);
			isRunning = true;

		} else
			throw new Exception("Connection is running");

	}

	/**
	 * Returns status of Connect module;
	 * @return
	 */
	public boolean getStatus() {
		return isRunning;
	}

	/**
	 * Returns current session;
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Returns current application;
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * Stops the connect module and disconnects from the robot;
	 */
	public void stop() throws Exception {

		if (isRunning) {
			application.stop();
			isRunning = false;
			session.close();
		} else
			throw new Exception("Robot is not connected");

	}

	/**
	 * Shuts down the NAO robot and destroys connection;
	 * 
	 * @throws Exception
	 */
	public void shutdown() throws Exception {

		if (isRunning) {
			system.shutdown();
			isRunning = false;
		} else
			throw new Exception("Robot is not connected");

	}

	/**
	 * Reboots the NAO robot, also destroys connection;
	 * 
	 * @throws Exception
	 */
	public void reboot() throws Exception {
		if (isRunning) {
			system.reboot();
			isRunning = false;
		} else
			throw new Exception("Robot is not connected");
	}

	/**
	 * Changes the robots IP and Port;
	 * 
	 * @param ip
	 * @param port
	 * @throws Exception
	 */
	public void changeIp(String ip, String port) throws Exception {
		if (!isRunning) {
			IPConnect = ("tcp://" + ip + ":" + port);
		} else
			throw new Exception("Cannot change ip when robot is connected");
	}

	/**
	 * Returns current connected robots name;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getName() throws Exception {
		return system.robotName();
	}

	/**
	 * Returns the current battery level of the connected robot;
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getBatteryCharge() throws Exception {
		return battery.getBatteryCharge();
	}

	public void reset() throws Exception {

		this.stop();
		this.run();

	}
	
	public String getIp(){
		return ip;
	}

}