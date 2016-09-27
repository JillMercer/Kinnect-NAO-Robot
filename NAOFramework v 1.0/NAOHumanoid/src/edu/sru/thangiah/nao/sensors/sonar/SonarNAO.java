package edu.sru.thangiah.nao.sensors.sonar;

/**
 * @File SonarNAO.java
 * Implements SonarInterface for the NAO robot
 * 
 * @author Brian Atwell
 * @date 2015
 * @lastModified 10/27/2015
 */
import java.lang.instrument.IllegalClassFormatException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSonar;

import edu.sru.thangiah.nao.connection.Connection;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.system.HouseKeeping;
import edu.sru.thangiah.random.RandomGenerator;
import edu.sru.thangiah.random.RandomGenerator.CASEENUM;

public class SonarNAO extends SonarAbstract {
	
	private ALSonar sonar;
	private ALMemory memory;
	private String subscriberName=null;
	private SonarSettingsInterface settings;
	
	private HouseKeeping maid;
	
	static private List<SonarNAO> listSonar = new ArrayList<SonarNAO>();
	private List<SonarDetectionNAOListener> listDetectionNAO = new LinkedList<SonarDetectionNAOListener>();
	
	
	/**
	 * Creates a new SonarNAO object from a Connection object
	 * @param connection
	 */
	public SonarNAO(Session session) {
		if(session == null){
			throw new NullPointerException("SonarNAO constructor parameter Session cannot be null!");
		}
		try {
			sonar = new ALSonar(session);
			memory = new ALMemory(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(subscriberName == null) {
			// Generate Random name
			int i;
			this.subscriberName = RandomGenerator.genAlphaNumericString(CASEENUM.BOTH, 8);
			for(i=0; containsSubscriberName(subscriberName) && i < 3; i++) {
				this.subscriberName = RandomGenerator.genAlphaNumericString(CASEENUM.BOTH, 8);
			}
			
			if(subscriberName == null) {
				//If we reach this error something is wrong with Generate Random Name
				throw new NullPointerException("Could not generate a unique random name after 3 tries!");
			}
		}
		
		this.settings = SonarNAOSettings.DEFAULT;
		
		listSonar.add(this);
		
		try {
			maid = new HouseKeeping(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Creates a new SonarNAO object from a Connection and a string
	 * @param connection
	 * @param subscriberName
	 */
	public SonarNAO(Session session, String subscriberName) {
		this(session);
		this.subscriberName=subscriberName;
		
	}
	
	/**
	 * Turns on the Sonar hardware to monitor sonar waves
	 */
	public void enable() {
		try {
			EventPair event = new EventPair();
			sonar.subscribe(subscriberName);
			
			event.eventName = "SonarLeftDetected";
			event.eventID=memory.subscribeToEvent(event.eventName, "onSonarLeftDetected::(f)", this);
			maid.addEvent(event);
			
			event = new EventPair();
			event.eventName = "SonarRightDetected";
			event.eventID=memory.subscribeToEvent(event.eventName, "onSonarRightDetected::(f)", this);
			maid.addEvent(event);
			
			event = new EventPair();
			event.eventName = "SonarLeftNothingDetected";
			event.eventID=memory.subscribeToEvent(event.eventName, "onSonarLeftNothingDetected::(f)", this);
			maid.addEvent(event);
			
			event = new EventPair();
			event.eventName = "SonarRightNothingDetected";
			event.eventID=memory.subscribeToEvent(event.eventName, "onSonarRightNothingDetected::(f)", this);
			maid.addEvent(event);
			
			event = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Turns off the Sonar hardware
	 */
	public void disable() {
		try {
			maid.removeAllEvents();
			sonar.unsubscribe(subscriberName);
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets an array of echoes from 0 to 10 echoes
	 * from the left sensor. Which will be from 0 to 10 elements
	 * Must use SonarNAOLocation
	 * @return Float array
	 */
	@Override
	public Float[] getSonarEchoes(int loc) throws IllegalClassFormatException {
		// TODO Auto-generated method stub

		SonarNAOLocation newLoc;
		newLoc = SonarNAOLocation.LEFT;
		newLoc = newLoc.getEnumFromInt(loc);
		return getSonarEchoes(newLoc);
	}
	
	/**
	 * Gets an array of echoes from 0 to 10 echoes
	 * from the left sensor. Which will be from 0 to 10 elements
	 * Must use SonarNAOLocation
	 * @return Float array
	 */
	public Float[] getSonarEchoes(SonarNAOLocation loc) {
		// TODO Auto-generated method stub
		List<Float> echoes;
		String base;
		Float curEcho;
		
		base = "";
		
		echoes = new LinkedList<Float>();
		
		if(loc.equals(SonarNAOLocation.LEFT)) {
			base = "Device/SubDeviceList/US/Left/Sensor/Value";
		}
		else if(loc.equals(SonarNAOLocation.RIGHT)) {
			base = "Device/SubDeviceList/US/Right/Sensor/Value";
		}
		
		try {
			int i;
			
			// Get echo
			curEcho = (Float) memory.getData(base);
			// if echo is in range and we are in loop range
			for(i = 1; curEcho > 0 && curEcho < 5 && i < 10; i++) {
				echoes.add(curEcho);
				//Get next echo
				curEcho = (Float) memory.getData(base+i);
			}
			if(i == 9 && curEcho > 0 && curEcho < 5) {
				echoes.add(curEcho);
			}
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return echoes.toArray(new Float[echoes.size()]);
	}

	/* (non-Javadoc)
	 * @see edu.sru.thangiah.nao.sonar.SonarInterface#setSonarWaveSettings(edu.sru.thangiah.nao.sonar.SonarSettingsInterface)
	 */
	@Override
	public boolean setSonarWaveSettings(SonarSettingsInterface settings) {
		// TODO Auto-generated method stub
		this.settings = settings;
		return true;
	}

	/* (non-Javadoc)
	 * @see edu.sru.thangiah.nao.sonar.SonarInterface#sendWave()
	 */
	@Override
	public void sendWave() {
		try {
			memory.insertData("Device/SubDeviceList/US/Actuator/Value", this.settings.getData());
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see edu.sru.thangiah.nao.sonar.SonarInterface#sendWavesPeriodically()
	 */
	@Override
	public void sendWavesPeriodically() {
		// TODO Auto-generated method stub
		Integer deviceStatus;
		int data;
		data = ((Integer)this.settings.getData()).intValue();
		data =  data ^ 64;
		
		try {
			deviceStatus = ((Float) memory.getData("Device/SubDeviceList/US/Actuator/Value")).intValue();
			if((deviceStatus & 64) != 64) {
				memory.insertData("Device/SubDeviceList/US/Actuator/Value", data);
				memory.subscribeToEvent("ALMemory/KeyTypeChanged", "onMemoryKeyTypeChanged::(S)", this);
			}
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see edu.sru.thangiah.nao.sonar.SonarInterface#stopWaves()
	 */
	@Override
	public void stopWaves() {
		// TODO Auto-generated method stub
		Integer deviceStatus;
		int data;
		data = ((Integer)this.settings.getData()).intValue();
		try {
			deviceStatus = ((Float) memory.getData("Device/SubDeviceList/US/Actuator/Value")).intValue();
			if((deviceStatus & 64) == 64) {
				memory.insertData("Device/SubDeviceList/US/Actuator/Value", data);
				memory.unsubscribeToEvent("ALMemory/KeyTypeChanged", "onMemoryKeyTypeChanged::(S)");
			}
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks all current instances if 
	 * @param name is equal to subscriber names of each object.
	 * @return boolean true if one instance subscriber name is 
	 * equal to name parameter.
	 * Returns false if no subscriber name of each object is 
	 * different from name parameter.
	 */
	protected boolean containsSubscriberName(String name) {
		
		for(SonarNAO sonar : listSonar) {
			if(sonar.getSubscriberName().equals(name)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Gets the subscriber name
	 * @return String
	 */
	public String getSubscriberName() {
		return subscriberName;
	}
	
	/**
	 * Adds a SonarDetectionListener that is called
	 * when a sonar detection event occurs.
	 * @param listener
	 */
	public void addSonarDetectionNAOListener(SonarDetectionNAOListener listener) {
		listDetectionNAO.add(listener);
	}
	
	/**
	 * Removes a certain Sonar detection listener
	 * @param listener
	 */
	public void removeSonarDetectionNAOListener(SonarDetectionNAOListener listener) {
		listDetectionNAO.remove(listener);
	}
	
	/**
	 * Get NAO Sonar Detection 
	 * @return List<SonarDetectionNAOListener
	 */
	protected List<SonarDetectionNAOListener> getSonarDetectionNAO() {
		return listDetectionNAO;
	}
	
	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is something in front of NAO (left side)
	 * at less than 0.5m. This means that NAO can’t go
	 * forward and has to stop and turn right to avoid
	 * the obstacle.
	 */
	public void onSonarLeftDetected(Float distance){
		Iterator<SonarDetectionNAOListener> iterNAO;
		iterNAO = listDetectionNAO.iterator();
		while(iterNAO.hasNext()) {
			iterNAO.next().sonarLeftDetected(distance);
		}
		
		onSonarDetected(SonarNAOLocation.LEFT.intValue(), distance);
	}
	
	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is something in front of NAO (right side)
	 * at less than 0.5m. This means that NAO can’t go
	 * forward and has to stop and turn left to avoid the obstacle.
	 */
	public void onSonarRightDetected(Float distance){
		Iterator<SonarDetectionNAOListener> iterNAO;
		iterNAO = listDetectionNAO.iterator();
		while(iterNAO.hasNext()) {
			iterNAO.next().sonarRightDetected(distance);
		}
		
		onSonarDetected(SonarNAOLocation.RIGHT.intValue(), distance);
	}
	
	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is nothing in front of NAO nor on his left side,
	 * this means NAO can go forward or turn left. An obstacle
	 * is present on the right side at less than 0.5m but it is
	 * not a problem if NAO goes forward.
	 */
	public void onSonarLeftNothingDetected(Float distance){
		Iterator<SonarDetectionNAOListener> iterNAO;
		iterNAO = listDetectionNAO.iterator();
		while(iterNAO.hasNext()) {
			iterNAO.next().sonarLeftNothingDetected(distance);
		}
		
		onSonarDetected(SonarNAOLocation.LEFT.intValue(), distance);
	}
	
	/**
	 * @link http://doc.aldebaran.com/2-1/naoqi/sensors/alsonar.html#alsonar
	 * There is nothing in front of NAO nor on his right side,
	 * this means NAO can go forward or turn right. An obstacle
	 * is present on the left side at less than 0.5m but it is
	 * not a problem if NAO goes forward.
	 */
	public void onSonarRightNothingDetected(Float distance){
		Iterator<SonarDetectionNAOListener> iterNAO;
		iterNAO = listDetectionNAO.iterator();
		while(iterNAO.hasNext()) {
			iterNAO.next().sonarRightNothingDetected(distance);
		}
		
		onSonarDetected(SonarNAOLocation.RIGHT.intValue(), distance);
	}
	
	
	/**
	 * onMemoryKeyTypeChanged(string keyName)
	 * is called when a memory key is changed on the NAO robot
	 * @param keyName
	 */
	private void onMemoryKeyTypeChanged(String keyName) {
		if(keyName.equals("Device/SubDeviceList/US/Left/Sensor/Value")) {
			onSonarChanged(SonarNAOLocation.LEFT.intValue(),getSonarEchoes(SonarNAOLocation.LEFT));
		}
		if(keyName.equals("Device/SubDeviceList/US/Right/Sensor/Value")) {
			onSonarChanged(SonarNAOLocation.RIGHT.intValue(),getSonarEchoes(SonarNAOLocation.RIGHT));
		}
	}

}
