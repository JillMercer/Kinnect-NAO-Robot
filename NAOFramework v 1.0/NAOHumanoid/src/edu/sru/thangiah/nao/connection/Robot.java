package edu.sru.thangiah.nao.connection;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALBattery;
import com.aldebaran.qi.helper.proxies.ALSystem;

import edu.sru.thangiah.nao.demo.Demo;

/** Author: Zachary Kearney
Last Edited, 5/5/2016
* @author zrk1002
* Class to hold a robot, contains methods for session connection, shutdown, reboot, etc.
*
*/
public class Robot {
	
	private String ip, name;
	private final String CONNECTIP;
	private Session session;
	private ALSystem system;
	private ALBattery battery;
	private final static String PORT = ":9559";
	private final static String TCP = "tcp://";
	private boolean isRunning;
	private String demoName = "";
	private int demoId = 0;
	private boolean demoRunning = false;
	
	public Robot(String ip){	
		this.ip = ip;
		CONNECTIP = (TCP + ip + PORT);
		Runtime.getRuntime().addShutdownHook(new SafeClose());
		isRunning = true;
		start();
	}
	
	private void start(){
		try {
			session = new Session(CONNECTIP);
			system = new ALSystem(session);
			battery = new ALBattery(session);
			name = system.robotName();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String ip(){
		return ip;
	}
	
	public String name(){
		return name;
	}
	
	public Session session(){
		return session;
	}
	
	public int batteryCharge(){
		int bat = 0;
		if(isRunning){
		try {
			bat = battery.getBatteryCharge();
		} catch (CallError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		}
		return bat;
	}
	
	public boolean stop(){
		session.close();
		isRunning = false;
		return true;
	}
	
	public boolean shutdown(){
		try {
			system.shutdown();
			isRunning = false;
			return true;
		} catch (CallError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean reboot(){
			try {
				system.reboot();
				isRunning = false;
				return true;
			} catch (CallError | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	public boolean isRunning(){
		return isRunning;
	}
	
	public class SafeClose extends Thread{
		public void run(){
			session.close();
			isRunning = false;
		}
	}
	
	public boolean runDemo(Demo d){
		if(demoRunning){
			return false;
		}
		else{
		demoRunning = true;
		demoName = d.getDemoName();
		demoId = d.id;
		return true;
		}
	}
	
	public int demoId(){
		return demoId;
	}
	
	public boolean demoRunning(){
		return demoRunning;
	}
	
	public String demoName(){
		return demoName;
	}
	
	public void stopDemo(){
		demoRunning = false;
		demoName = "";
		demoId = 0;
	}
	
	public String toString(){
		return name;
	}
	
	
	
}