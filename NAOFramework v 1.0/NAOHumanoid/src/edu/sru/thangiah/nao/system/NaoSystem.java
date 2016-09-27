package edu.sru.thangiah.nao.system;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALBattery;
import com.aldebaran.qi.helper.proxies.ALSystem;

import edu.sru.thangiah.nao.module.Module;

public class NaoSystem extends Module{

	private ALSystem system;
	private ALBattery battery;
	
	public NaoSystem(Session session) throws Exception {
		super(session);
		system = new ALSystem(session);
		battery = new ALBattery(session);
		// TODO Auto-generated constructor stub
	}

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
	
	@Override
	public void exit() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
