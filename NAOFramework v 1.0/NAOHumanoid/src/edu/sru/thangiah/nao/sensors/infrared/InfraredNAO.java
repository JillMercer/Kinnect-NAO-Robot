package edu.sru.thangiah.nao.sensors.infrared;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.RawApplication;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALInfrared;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.brian.newapi.demo.RobotConfig;
import edu.sru.thangiah.nao.connection.Connection;

public class InfraredNAO extends InfraredNAOAbstract implements InfraredNAOInterface {
	
	private ALInfrared infrared;
	private ALMemory memory;
	private List<InfraredLIRCRemoteListener> remoteListenerList;
	private List<InfraredDataListener> dataListenerList;
	
	public InfraredNAO(Session session)
	{
		try {
			memory = new ALMemory(session);
			infrared = new ALInfrared(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		remoteListenerList = new LinkedList<InfraredLIRCRemoteListener>();
		dataListenerList = new LinkedList<InfraredDataListener>();
	}

	@Override
	public void addInfraredRemoteListener(InfraredLIRCRemoteListener listener) {	
		remoteListenerList.add(listener);
	}

	@Override
	public void removeInfraredRemoteListener(InfraredLIRCRemoteListener listener) {
		remoteListenerList.remove(listener);

	}

	@Override
	public void addDataReceiveListener(InfraredDataListener listener) {
		dataListenerList.add(listener);
	}

	@Override
	public void removeDataReceiveListener(InfraredDataListener listener) {
		dataListenerList.remove(listener);

	}

	@Override
	public void send8Bit(Integer octet) {
		// TODO Auto-generated method stub
		try {
			infrared.send8(octet);
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void send32Bit(String dataIR) {
		// TODO Auto-generated method stub
		try {
			infrared.send32(dataIR);
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void send32Bit(Integer octet1, Integer octet2, Integer octet3, Integer octet4) {
		// TODO Auto-generated method stub
		try {
			infrared.send32(octet1, octet2, octet3, octet4);
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendIPAddress(String ipData) {
		// TODO Auto-generated method stub
		try {
			infrared.sendIpAddress(ipData);
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendRemoteButton(String remoteName, String buttonName) {
		// TODO Auto-generated method stub
		try {
			infrared.sendRemoteKey(remoteName, buttonName);
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendRemoteButtonWithTime(String remoteName, String buttonName, Integer pTimeMs) {
		// TODO Auto-generated method stub
		try {
			infrared.sendRemoteKeyWithTime(remoteName, buttonName, pTimeMs);
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void enable() {
		try {
			memory.subscribeToEvent("InfraRedRemoteKeyReceived", "onLIRCRemoteButtonReceived::(m)", this);
			memory.subscribeToEvent("InfraRedIpAdressReceived", "onIPDataReceived::(s)", this);
			memory.subscribeToEvent("InfraRedOneByteReceived", "onOneByteDataReceived::(i)", this);
			memory.subscribeToEvent("InfraRedFourBytesReceived", "onFourByteDataReceived::(m)", this);
			
			// This function blocks forever as far as I know. lol.
			//infrared.initReception(-1);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		try {
			memory.unsubscribeToEvent("InfraRedRemoteKeyReceived", "onLIRCRemoteButtonReceived::(m)");
			//memory.unsubscribeToEvent("InfraRedIpAdressReceived", "onIPDataReceived::(s)");
			//memory.unsubscribeToEvent("InfraRedOneByteReceived", "onOneByteDataReceived::(i)");
			//memory.unsubscribeToEvent("InfraRedFourBytesReceived", "onFourByteDataReceived::(m)");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onOneByteDataReceived(Integer data) {
		System.out.println("infrared: One Byte Received: "+data);
		
		Iterator<InfraredDataListener> iter;
		iter = dataListenerList.iterator();
		InfraredDataListener listener;
		
		while(iter.hasNext())
		{
			listener = iter.next();
			
			listener.onOneByteDataReceived(data);
		}
		
	}

	@Override
	protected void onFourByteDataReceived(Object data) {
		System.out.println("infrared: Four Byte Received: "+data);
		
		Integer dataAry[];
		int lData;
		Iterator<InfraredDataListener> iter;
		iter = dataListenerList.iterator();
		InfraredDataListener listener;
		
		dataAry = (Integer[]) data;
		
		lData =dataAry[0];
		lData+=dataAry[1]<<8;
		lData+=dataAry[2]<<16;
		lData+=dataAry[3]<<24;
		
		
		while(iter.hasNext())
		{
			listener = iter.next();
			
			listener.onFourByteDataReceived(lData);
		}
		
	}

	@Override
	protected void onIPDataReceived(String data) {
		System.out.println("infrared: IP data Received: "+data);
		
		Iterator<InfraredDataListener> iter;
		iter = dataListenerList.iterator();
		InfraredDataListener listener;
		
		while(iter.hasNext())
		{
			listener = iter.next();
			
			listener.onIPDataReceived(data);
		}
	}

	@Override
	protected void onLIRCRemoteButtonReceived(Object data) {
		System.out.println("infrared: IR Remote Control button Received: "+data);
		
		Iterator<InfraredLIRCRemoteListener> iter;
		iter = remoteListenerList.iterator();
		InfraredLIRCRemoteListener listener;
		
		while(iter.hasNext())
		{
			listener = iter.next();
			
			listener.onLIRCRemoteKeyReceived(data);
		}
	}

	@Override
	public String getIPAddressSent() {
		String ipData;
		ipData = "";
		
		try {
			ipData = (String) memory.getData("Device/SubDeviceList/IR/LIRC/Data/IP/Actuator/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipData;
	}

	@Override
	public Integer get8BitSent() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt8/Byte/Actuator/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Integer getFirstByteOf32BitSent() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte1/Actuator/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Integer getSecondByteOf32BitSent() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte2/Actuator/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Integer getThirdByteOf32BitSent() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte3/Actuator/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Integer getFourthByteOf32iBitSent() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte4/Actuator/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public String getIPAddressReceived() {
		String ipData;
		ipData = "";
		
		try {
			ipData = (String) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt8/Byte/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipData;
	}

	@Override
	public Integer get8BitReceived() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt8/Byte/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Integer getFirstByteOf32bitReceived() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte1/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Integer getSecondByteOf32bitReceived() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte2/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Integer getThirdByteOf32bitReceived() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte3/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Integer getFourthByteOf32bitReceived() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte4/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public String getRemoteNameSent() {
		String ipData;
		ipData = "";
		
		try {
			ipData = (String) memory.getData("Device/SubDeviceList/IR/LIRC/Remote/Remote/Actuator/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipData;
	}

	@Override
	public String getRemoteButtonNameSent() {
		String ipData;
		ipData = "";
		
		try {
			ipData = (String) memory.getData("Device/SubDeviceList/IR/LIRC/Remote/Key/Actuator/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipData;
	}

	@Override
	public String getLIRCCode() {
		String ipData;
		ipData = "";
		
		try {
			ipData = (String) memory.getData("Device/SubDeviceList/IR/LIRC/Remote/LircCode/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipData;
	}

	@Override
	public Integer getRepeatsPerFrame() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Remote/Repeat/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public String getRemoteButtonNameReceived() {
		String ipData;
		ipData = "";
		
		try {
			ipData = (String) memory.getData("Device/SubDeviceList/IR/LIRC/Remote/Key/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipData;
	}

	@Override
	public String getRemoteNameReceived() {
		String ipData;
		ipData = "";
		
		try {
			ipData = (String) memory.getData("Device/SubDeviceList/IR/LIRC/Remote/Remote/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipData;
	}

	@Override
	public int getIRLocationReceived() {
		Integer data;
		data = 0;
		
		try {
			data = (Integer) memory.getData("Device/SubDeviceList/IR/LIRC/Data/uInt32/Byte4/Sensor/Value/");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return data;
		return data.intValue();
	}
	
	
	public void onFrontTactileTouched(Float val) {
		System.out.println("Front Tactile Touched!! with value: "+ val);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Application app = new Application(args);
		//RawApplication application = new RawApplication(args);
		
		Connection connection = new Connection();
		
		connection.connectToNao("192.168.0.108");
		
		//app.start();
		
		Session session;
		session = connection.getSession();
		//session = app.session();
		
		if(!session.isConnected()){
			System.out.println("Not Connected!!!!");
		}
		
		
		//InfraredNAO naoIR = new InfraredNAO(connection.getSession());
		InfraredNAO naoIR = new InfraredNAO(session);
		
		
		try {
			naoIR.memory.subscribeToEvent("FrontTactilTouched", "onFrontTactileTouched::(f)", naoIR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		naoIR.enable();
		
		
		// Create an ALTextToSpeech object and link it to your current session
        ALTextToSpeech tts = null;
		try {
			tts = new ALTextToSpeech(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Make your robot say something
        try {
			tts.say("Hello World!");
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("HelloWorld!!");
		
		naoIR.disable();
		//app.run();
	}

}
