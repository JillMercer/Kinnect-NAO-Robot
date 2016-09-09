/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sru.thangiah.nao.diagnostic.gui;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.Module;

/**
 *
 * @author sam
 */
public class DiagnosticGUI extends Module{
    private JFrame window;
    private JTabbedPane sensors;
    private JPanel content;
    private Updater updater;
    private MotionSensors motionSensors = new MotionSensors();
    private Battery battery = new Battery();
    private LEDActuators led = new LEDActuators();
    private TouchSensors touch = new TouchSensors();
    private Sonars sonar = new Sonars();
    private Switches switches = new Switches();
    private InertialSensors inertial = new InertialSensors();
    private ALMemory memory;
    
    @Override
    public void exit() throws Exception{
        memory.exit();
    }
    
    @Override
    public void reset() throws Exception{
        
    }
    
    @SuppressWarnings("deprecation")
	public DiagnosticGUI(Session session) throws Exception{
    	super(session);
    	String path = System.getProperty("java.library.path");
    	System.out.println(path);
    	updater = new Updater();
        memory = new ALMemory(session);
        window = new JFrame("SRU NAO GUI");
        content = new JPanel(new FlowLayout());
        window.setContentPane(content);
        window.setSize(500,500);
        
        sensors = new JTabbedPane();
        sensors.add("LED Actuators", led.getScrollPane());
        sensors.add("Inertial Sensors", inertial.getScrollPane());
        sensors.add("Touch Sensors",touch.getScrollPane());
        sensors.add("Switches",switches.getScrollPane());
        sensors.add("MotionSensors",motionSensors.getScrollPane());
        sensors.add("Sonars",sonar.getScrollPane());
        sensors.add("Battery",battery.getScrollPane());
        content.add(sensors);
       
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
      	updater.start();
    }
    
    private class Updater extends Thread{
    	public void run(){
    		while(true){
    			updateAllMotion();
    			updateBattery();
    			updateTouch();
    			updateLEDs();
    			updateSonars();
    			updateSwitches();
    			updateInertialSensors();
    			try{
    				sleep(500);
    			} catch(Exception e){}
    		}
    	}
    }
    
    public void updateAllMotion(){
    	updateMotionGroup("Head Yaw");
    	updateMotionGroup("Head Pitch");
    	updateMotionGroup("R Shoulder Roll");
    	updateMotionGroup("R Shoulder Pitch");
    	updateMotionGroup("L Shoulder Roll");
    	updateMotionGroup("L Shoulder Pitch");
    	updateMotionGroup("R Elbow Yaw");
    	updateMotionGroup("L Elbow Yaw");
    	updateMotionGroup("R Elbow Roll");
    	updateMotionGroup("L Elbow Roll");
    	updateMotionGroup("R Wrist Yaw");
    	updateMotionGroup("L Wrist Yaw");
    	updateMotionGroup("R Hand");
    	updateMotionGroup("L Hand");
    	updateMotionGroup("R Hip Pitch");
    	updateMotionGroup("L Hip Pitch");
    	updateMotionGroup("R Hip Roll");
    	updateMotionGroup("L Hip Roll");
    	updateMotionGroup("R Knee Pitch");
    	updateMotionGroup("L Knee Pitch");
    	updateMotionGroup("R Ankle Pitch");
    	updateMotionGroup("L Ankle Pitch");
    	updateMotionGroup("R Ankle Roll");
    	updateMotionGroup("L Ankle Roll");
    }
    
    public void updateSonars(){
    	updateSonarSensor("Sonar Actuator Value",
    			"US/Actuator/Value");
    	updateSonarSensor("Sonar Sensor Value",
    			"US/Sensor/Value");
    	updateSonarSensor("Sonar Right Sensor Value",
    			"US/Right/Sensor/Value");
    	updateSonarSensor("Sonar Left Sensor Value",
    			"US/Left/Sensor/Value");
    	for(int i = 1; i < 10; i++){
    		updateSonarSensor("Sonar Right Sensor Value" + i,
        			"US/Right/Sensor/Value" + i);
        	updateSonarSensor("Sonar Left Sensor Value" + i,
        			"US/Left/Sensor/Value" + i);
    	}
    }
    
    public void updateMotionGroup(String groupName){
    	updateMotionSensor(groupName + " Position Actuator",
    					
    					"Device/SubDeviceList/"
    					+groupName.replaceAll(" ","")
    					+"/Position/Actuator/Value");
    	updateMotionSensor(groupName + " Position Sensor",
				
				"Device/SubDeviceList/"
				+groupName.replaceAll(" ","")
				+"/Position/Sensor/Value");
    	updateMotionSensor(groupName + " Electric Current",
				
				"Device/SubDeviceList/"
				+groupName.replaceAll(" ","")
				+"/ElectricCurrent/Sensor/Value");
    	updateMotionSensor(groupName + " Temperature Value",
				
				"Device/SubDeviceList/"
				+groupName.replaceAll(" ","")
				+"/Temperature/Sensor/Value");
    	updateMotionSensor(groupName + " Hardness",
				
				"Device/SubDeviceList/"
				+groupName.replaceAll(" ","")
				+"/Hardness/Actuator/Value");
    	updateMotionSensor(groupName + " Temperature Status",
				
				"Device/SubDeviceList/"
				+groupName.replaceAll(" ","")
				+"/Temperature/Sensor/Status");
    }
    public void updateBattery(){
    	updateBatterySensor( "Battery Current",
    			"Device/SubDeviceList/Battery/Current/Sensor/Value");
    	updateBatterySensor( "Battery Charge",
    			"Device/SubDeviceList/Battery/Charge/Sensor/Value");
    	updateBatterySensor( "Battery Temperature",
    			"Device/SubDeviceList/Battery/Temperature/Sensor/Value");
    }
    public void updateTouch(){
    	updateTouchSensor("Head Front Touch", 
    			"Head/Touch/Front/Sensor/Value");
    	updateTouchSensor("Head Rear Touch", 
    			"Head/Touch/Rear/Sensor/Value");
    	updateTouchSensor("Head Middle Touch", 
    			"Head/Touch/Middle/Sensor/Value");
    	updateTouchSensor("Left Hand Back Touch", 
    			"LHand/Touch/Back/Sensor/Value");
    	updateTouchSensor("Left Hand Left Touch", 
    			"LHand/Touch/Left/Sensor/Value");
    	updateTouchSensor("Left Hand Right Touch", 
    			"LHand/Touch/Right/Sensor/Value");
    	updateTouchSensor("Right Hand Back Touch", 
    			"RHand/Touch/Back/Sensor/Value");
    	updateTouchSensor("Right Hand Left Touch", 
    			"RHand/Touch/Left/Sensor/Value");
    	updateTouchSensor("Right Hand Right Touch", 
    			"RHand/Touch/Right/Sensor/Value");
    }
    public void updateLEDs(){
    	updateLEDSensor("Face Red Left 0",
    			"Face/Led/Red/Left/0Deg/Actuator/Value");
    	updateLEDSensor("Face Red Left 45",
    			"Face/Led/Red/Left/45Deg/Actuator/Value");
    	updateLEDSensor("Face Red Left 90",
    			"Face/Led/Red/Left/90Deg/Actuator/Value");
    	updateLEDSensor("Face Red Left 135",
    			"Face/Led/Red/Left/135Deg/Actuator/Value");
    	updateLEDSensor("Face Red Left 180",
    			"Face/Led/Red/Left/180Deg/Actuator/Value");
    	updateLEDSensor("Face Red Left 225",
    			"Face/Led/Red/Left/225Deg/Actuator/Value");
    	updateLEDSensor("Face Red Left 270",
    			"Face/Led/Red/Left/270Deg/Actuator/Value");
    	updateLEDSensor("Face Red Left 315",
    			"Face/Led/Red/Left/315Deg/Actuator/Value");
    	
    	updateLEDSensor("Face Red Right 0",
    			"Face/Led/Red/Right/0Deg/Actuator/Value");
    	updateLEDSensor("Face Red Right 45",
    			"Face/Led/Red/Right/45Deg/Actuator/Value");
    	updateLEDSensor("Face Red Right 90",
    			"Face/Led/Red/Right/90Deg/Actuator/Value");
    	updateLEDSensor("Face Red Right 135",
    			"Face/Led/Red/Right/135Deg/Actuator/Value");
    	updateLEDSensor("Face Red Right 180",
    			"Face/Led/Red/Right/180Deg/Actuator/Value");
    	updateLEDSensor("Face Red Right 225",
    			"Face/Led/Red/Right/225Deg/Actuator/Value");
    	updateLEDSensor("Face Red Right 270",
    			"Face/Led/Red/Right/270Deg/Actuator/Value");
    	updateLEDSensor("Face Red Right 315",
    			"Face/Led/Red/Right/315Deg/Actuator/Value");
    	
    	updateLEDSensor("Face Green Left 0",
    			"Face/Led/Green/Left/0Deg/Actuator/Value");
    	updateLEDSensor("Face Green Left 45",
    			"Face/Led/Green/Left/45Deg/Actuator/Value");
    	updateLEDSensor("Face Green Left 90",
    			"Face/Led/Green/Left/90Deg/Actuator/Value");
    	updateLEDSensor("Face Green Left 135",
    			"Face/Led/Green/Left/135Deg/Actuator/Value");
    	updateLEDSensor("Face Green Left 180",
    			"Face/Led/Green/Left/180Deg/Actuator/Value");
    	updateLEDSensor("Face Green Left 225",
    			"Face/Led/Green/Left/225Deg/Actuator/Value");
    	updateLEDSensor("Face Green Left 270",
    			"Face/Led/Green/Left/270Deg/Actuator/Value");
    	updateLEDSensor("Face Green Left 315",
    			"Face/Led/Green/Left/315Deg/Actuator/Value");
    	
    	updateLEDSensor("Face Blue Left 0",
    			"Face/Led/Blue/Left/0Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Left 45",
    			"Face/Led/Blue/Left/45Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Left 90",
    			"Face/Led/Blue/Left/90Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Left 135",
    			"Face/Led/Blue/Left/135Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Left 180",
    			"Face/Led/Blue/Left/180Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Left 225",
    			"Face/Led/Blue/Left/225Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Left 270",
    			"Face/Led/Blue/Left/270Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Left 315",
    			"Face/Led/Blue/Left/315Deg/Actuator/Value");
    	
    	updateLEDSensor("Face Blue Right 0",
    			"Face/Led/Blue/Right/0Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Right 45",
    			"Face/Led/Blue/Right/45Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Right 90",
    			"Face/Led/Blue/Right/90Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Right 135",
    			"Face/Led/Blue/Right/135Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Right 180",
    			"Face/Led/Blue/Right/180Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Right 225",
    			"Face/Led/Blue/Right/225Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Right 270",
    			"Face/Led/Blue/Right/270Deg/Actuator/Value");
    	updateLEDSensor("Face Blue Right 315",
    			"Face/Led/Blue/Right/315Deg/Actuator/Value");
    	
    	updateLEDSensor("Face Green Right 0",
    			"Face/Led/Green/Right/0Deg/Actuator/Value");
    	updateLEDSensor("Face Green Right 45",
    			"Face/Led/Green/Right/45Deg/Actuator/Value");
    	updateLEDSensor("Face Green Right 90",
    			"Face/Led/Green/Right/90Deg/Actuator/Value");
    	updateLEDSensor("Face Green Right 135",
    			"Face/Led/Green/Right/135Deg/Actuator/Value");
    	updateLEDSensor("Face Green Right 180",
    			"Face/Led/Green/Right/180Deg/Actuator/Value");
    	updateLEDSensor("Face Green Right 225",
    			"Face/Led/Green/Right/225Deg/Actuator/Value");
    	updateLEDSensor("Face Green Right 270",
    			"Face/Led/Green/Right/270Deg/Actuator/Value");
    	updateLEDSensor("Face Green Right 315",
    			"Face/Led/Green/Right/315Deg/Actuator/Value");
    	
    	updateLEDSensor("Right Ear Led 0",
    			"Ears/Led/Right/0Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 36",
    			"Ears/Led/Right/36Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 72",
    			"Ears/Led/Right/72Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 108",
    			"Ears/Led/Right/108Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 144",
    			"Ears/Led/Right/144Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 180",
    			"Ears/Led/Right/180Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 216",
    			"Ears/Led/Right/216Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 252",
    			"Ears/Led/Right/252Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 288",
    			"Ears/Led/Right/288Deg/Actuator/Value");
    	updateLEDSensor("Right Ear Led 324",
    			"Ears/Led/Right/324Deg/Actuator/Value");
    	
    	updateLEDSensor("Left Ear Led 0",
    			"Ears/Led/Left/0Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 36",
    			"Ears/Led/Left/36Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 72",
    			"Ears/Led/Left/72Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 108",
    			"Ears/Led/Left/108Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 144",
    			"Ears/Led/Left/144Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 180",
    			"Ears/Led/Left/180Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 216",
    			"Ears/Led/Left/216Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 252",
    			"Ears/Led/Left/252Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 288",
    			"Ears/Led/Left/288Deg/Actuator/Value");
    	updateLEDSensor("Left Ear Led 324",
    			"Ears/Led/Left/324Deg/Actuator/Value");
    }
    public void updateSwitches(){
    	updateSwitchSensor("Chest Board Button",
    			"ChestBoard/Button/Sensor/Value");
    	updateSwitchSensor("Left Foot Right Bumper",
    			"LFoot/Bumper/Right/Sensor/Value");
    	updateSwitchSensor("Left Foot Left Bumper",
    			"LFoot/Bumper/Left/Sensor/Value");
    	updateSwitchSensor("Right Foot Right Bumper",
    			"RFoot/Bumper/Right/Sensor/Value");
    	updateSwitchSensor("Right Foot Left Bumper",
    			"RFoot/Bumper/Left/Sensor/Value");
    }
    public void updateInertialSensors(){
    	updateInertialSensor("Gyroscope X",
    			"GyroscopeX/Sensor/Value");
    	updateInertialSensor("Gyroscope Y",
    			"GyroscopeY/Sensor/Value");
    	updateInertialSensor("Gyroscope Z",
    			"GyroscopeZ/Sensor/Value");
    	updateInertialSensor("Angle X",
    			"AngleX/Sensor/Value");
    	updateInertialSensor("Angle Y",
    			"AngleY/Sensor/Value");
    	updateInertialSensor("Angle Z",
    			"AngleZ/Sensor/Value");
    	updateInertialSensor("Angle X",
    			"AngleX/Sensor/Value");
    	updateInertialSensor("Accelerometer X",
    			"AccelerometerX/Sensor/Value");
    	updateInertialSensor("Accelerometer Y",
    			"AccelerometerY/Sensor/Value");
    	updateInertialSensor("Accelerometer Z",
    			"AccelerometerZ/Sensor/Value");
    }
    
    public void updateInertialSensor(String textField, String motionSensor){
    	try{
    		
    		inertial.setSensorValue(textField,memory.getData("Device/SubDeviceList/InertialSensor/"+motionSensor).toString());
    		
    	}catch(Exception e){}
    }
    public void updateTouchSensor(String textField, String motionSensor){
    	try{
    		
    		touch.setSensorValue(textField,memory.getData("Device/SubDeviceList/"+motionSensor).toString());
    		
    	}catch(Exception e){}
    }
    public void updateSonarSensor(String textField, String motionSensor){
    	try{
    		
    		sonar.setSensorValue(textField,memory.getData("Device/SubDeviceList/"+motionSensor).toString());
    		
    	}catch(Exception e){}
    }
    public void updateSwitchSensor(String textField, String motionSensor){
    	try{
    		
    		switches.setSensorValue(textField,memory.getData("Device/SubDeviceList/"+motionSensor).toString());
    		
    	}catch(Exception e){}
    }
    //update an individual sensor
    public void updateMotionSensor(String textField, String motionSensor){
    	try{
    		if(textField.contains("Position")){
    			motionSensors.setSensorValue(textField,new Double(Double.valueOf(memory.getData(motionSensor).toString()) * 57.2957795).toString());
    		}
    		else{
    			motionSensors.setSensorValue(textField,memory.getData(motionSensor).toString());
    		}
    	}catch(Exception e){}
    }
    public void updateBatterySensor(String textField, String motionSensor){
    	try{
    		
    			battery.setSensorValue(textField,memory.getData(motionSensor).toString());
    		
    	}catch(Exception e){}
    }
    public void updateLEDSensor(String textField, String motionSensor){
    	try{
    		
    		led.setSensorValue(textField,memory.getData("Device/SubDeviceList/"+motionSensor).toString());
    		
    	}catch(Exception e){}
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Connect c = new Connect("192.168.0.105");
            	try {
					c.run();
					new DiagnosticGUI(c.getSession());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
    
}
