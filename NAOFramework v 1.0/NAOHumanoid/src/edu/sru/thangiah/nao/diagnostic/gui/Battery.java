package edu.sru.thangiah.nao.diagnostic.gui;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Battery {
    private JFrame testFrame;  //frame to test look of Panel
    private JPanel contentPane; //main panel to be accessed by external GUI
    private JScrollPane scrollPane;
    private JPanel sensors; //Scroll Pane that houses all motion sensors
    //stores all JTextFields for Motion Sensors
    private HashMap<String, JTextField> sensorTextFields; //for easy access to any of the JTextFields

    //Note on Naming:  Camel case, name of joint followed by name of sensor, followed by
    //TF for text field or L for label
    //Here lie the hundreds of declarations needed to list out all the sensors in the GUI;
    //Definitely need a JScrollPane for this mess, and some separators to keep things neet
    
    	private JLabel battery;
	private JLabel batteryCurrentL;
	private JTextField batteryCurrentTF;
	private JLabel batteryChargeL;
	private JTextField batteryChargeTF;
	private JLabel batteryTemperatureL;
	private JTextField batteryTemperatureTF;




    public Battery(){
        contentPane = new JPanel();
        sensorTextFields = new HashMap<>();
        sensors = new JPanel();
        sensors.setLayout(new GridLayout(8,1));

        	battery = new JLabel("Battery");
	sensors.add(battery);
	sensors.add(new JSeparator());
	batteryCurrentL = new JLabel("Battery Current");
	batteryCurrentTF = new JTextField();
	sensors.add(batteryCurrentL);
	sensors.add(batteryCurrentTF);
	sensorTextFields.put("Battery Current", batteryCurrentTF);
	batteryChargeL = new JLabel("Battery Charge");
	batteryChargeTF = new JTextField();
	sensors.add(batteryChargeL);
	sensors.add(batteryChargeTF);
	sensorTextFields.put("Battery Charge", batteryChargeTF);
	batteryTemperatureL = new JLabel("Battery Temperature");
	batteryTemperatureTF = new JTextField();
	sensors.add(batteryTemperatureL);
	sensors.add(batteryTemperatureTF);
	sensorTextFields.put("Battery Temperature", batteryTemperatureTF);
	sensors.add(new JSeparator());
	sensors.add(new JSeparator());



        for(Map.Entry<String,JTextField> entry : sensorTextFields.entrySet()){
            entry.getValue().setEditable(false);
        }
        //contentPane.setLayout(new FlowLayout());
        //testFrame = new JFrame("Test Motion Sensors");
        //testFrame.setContentPane(contentPane);
        //testFrame.setSize(500,500);
        scrollPane = new JScrollPane(sensors);
        scrollPane.setPreferredSize(new Dimension(400,500));
        //contentPane.add(scrollPane);
        //testFrame.setVisible(true);
    }

    //Method to set any Sensor Value based on its name
    public boolean setSensorValue(String sensorBattery, String value){
        if(sensorTextFields.containsKey(sensorBattery)) {
            sensorTextFields.get(sensorBattery).setEditable(true);
            sensorTextFields.get(sensorBattery).setText(value);
            sensorTextFields.get(sensorBattery).setEditable(false);
            return true;
        }
        else return false;
    }


    //what should actually be passed to the main GUI
    public JScrollPane getScrollPane(){ return scrollPane;}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Battery();
            }
        });
    }
}
