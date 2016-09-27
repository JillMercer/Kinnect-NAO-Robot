package edu.sru.thangiah.nao.diagnostic.gui;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Sonars {
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
    
    	private JLabel sonars;
	private JLabel sonarActuatorValueL;
	private JTextField sonarActuatorValueTF;
	private JLabel sonarSensorValueL;
	private JTextField sonarSensorValueTF;
	private JLabel sonarLeftSensorValueL;
	private JTextField sonarLeftSensorValueTF;
	private JLabel sonarRightSensorValueL;
	private JTextField sonarRightSensorValueTF;
	private JLabel sonarLeftSensorValue1L;
	private JTextField sonarLeftSensorValue1TF;
	private JLabel sonarLeftSensorValue2L;
	private JTextField sonarLeftSensorValue2TF;
	private JLabel sonarLeftSensorValue3L;
	private JTextField sonarLeftSensorValue3TF;
	private JLabel sonarLeftSensorValue4L;
	private JTextField sonarLeftSensorValue4TF;
	private JLabel sonarLeftSensorValue5L;
	private JTextField sonarLeftSensorValue5TF;
	private JLabel sonarLeftSensorValue6L;
	private JTextField sonarLeftSensorValue6TF;
	private JLabel sonarLeftSensorValue7L;
	private JTextField sonarLeftSensorValue7TF;
	private JLabel sonarLeftSensorValue8L;
	private JTextField sonarLeftSensorValue8TF;
	private JLabel sonarLeftSensorValue9L;
	private JTextField sonarLeftSensorValue9TF;
	private JLabel sonarRightSensorValue1L;
	private JTextField sonarRightSensorValue1TF;
	private JLabel sonarRightSensorValue2L;
	private JTextField sonarRightSensorValue2TF;
	private JLabel sonarRightSensorValue3L;
	private JTextField sonarRightSensorValue3TF;
	private JLabel sonarRightSensorValue4L;
	private JTextField sonarRightSensorValue4TF;
	private JLabel sonarRightSensorValue5L;
	private JTextField sonarRightSensorValue5TF;
	private JLabel sonarRightSensorValue6L;
	private JTextField sonarRightSensorValue6TF;
	private JLabel sonarRightSensorValue7L;
	private JTextField sonarRightSensorValue7TF;
	private JLabel sonarRightSensorValue8L;
	private JTextField sonarRightSensorValue8TF;
	private JLabel sonarRightSensorValue9L;
	private JTextField sonarRightSensorValue9TF;




    public Sonars(){
        contentPane = new JPanel();
        sensorTextFields = new HashMap<>();
        sensors = new JPanel();
        sensors.setLayout(new GridLayout(40,1));

        	sonars = new JLabel("Sonars");
	sensors.add(sonars);
	sensors.add(new JSeparator());
	sonarActuatorValueL = new JLabel("Sonar Actuator Value");
	sonarActuatorValueTF = new JTextField();
	sensors.add(sonarActuatorValueL);
	sensors.add(sonarActuatorValueTF);
	sensorTextFields.put("Sonar Actuator Value", sonarActuatorValueTF);
	sonarSensorValueL = new JLabel("Sonar Sensor Value");
	sonarSensorValueTF = new JTextField();
	sensors.add(sonarSensorValueL);
	sensors.add(sonarSensorValueTF);
	sensorTextFields.put("Sonar Sensor Value", sonarSensorValueTF);
	sonarLeftSensorValueL = new JLabel("Sonar Left Sensor Value");
	sonarLeftSensorValueTF = new JTextField();
	sensors.add(sonarLeftSensorValueL);
	sensors.add(sonarLeftSensorValueTF);
	sensorTextFields.put("Sonar Left Sensor Value", sonarLeftSensorValueTF);
	sonarRightSensorValueL = new JLabel("Sonar Right Sensor Value");
	sonarRightSensorValueTF = new JTextField();
	sensors.add(sonarRightSensorValueL);
	sensors.add(sonarRightSensorValueTF);
	sensorTextFields.put("Sonar Right Sensor Value", sonarRightSensorValueTF);
	sonarLeftSensorValue1L = new JLabel("Sonar Left Sensor Value1");
	sonarLeftSensorValue1TF = new JTextField();
	sensors.add(sonarLeftSensorValue1L);
	sensors.add(sonarLeftSensorValue1TF);
	sensorTextFields.put("Sonar Left Sensor Value1", sonarLeftSensorValue1TF);
	sonarLeftSensorValue2L = new JLabel("Sonar Left Sensor Value2");
	sonarLeftSensorValue2TF = new JTextField();
	sensors.add(sonarLeftSensorValue2L);
	sensors.add(sonarLeftSensorValue2TF);
	sensorTextFields.put("Sonar Left Sensor Value2", sonarLeftSensorValue2TF);
	sonarLeftSensorValue3L = new JLabel("Sonar Left Sensor Value3");
	sonarLeftSensorValue3TF = new JTextField();
	sensors.add(sonarLeftSensorValue3L);
	sensors.add(sonarLeftSensorValue3TF);
	sensorTextFields.put("Sonar Left Sensor Value3", sonarLeftSensorValue3TF);
	sonarLeftSensorValue4L = new JLabel("Sonar Left Sensor Value4");
	sonarLeftSensorValue4TF = new JTextField();
	sensors.add(sonarLeftSensorValue4L);
	sensors.add(sonarLeftSensorValue4TF);
	sensorTextFields.put("Sonar Left Sensor Value4", sonarLeftSensorValue4TF);
	sonarLeftSensorValue5L = new JLabel("Sonar Left Sensor Value5");
	sonarLeftSensorValue5TF = new JTextField();
	sensors.add(sonarLeftSensorValue5L);
	sensors.add(sonarLeftSensorValue5TF);
	sensorTextFields.put("Sonar Left Sensor Value5", sonarLeftSensorValue5TF);
	sonarLeftSensorValue6L = new JLabel("Sonar Left Sensor Value6");
	sonarLeftSensorValue6TF = new JTextField();
	sensors.add(sonarLeftSensorValue6L);
	sensors.add(sonarLeftSensorValue6TF);
	sensorTextFields.put("Sonar Left Sensor Value6", sonarLeftSensorValue6TF);
	sonarLeftSensorValue7L = new JLabel("Sonar Left Sensor Value7");
	sonarLeftSensorValue7TF = new JTextField();
	sensors.add(sonarLeftSensorValue7L);
	sensors.add(sonarLeftSensorValue7TF);
	sensorTextFields.put("Sonar Left Sensor Value7", sonarLeftSensorValue7TF);
	sonarLeftSensorValue8L = new JLabel("Sonar Left Sensor Value8");
	sonarLeftSensorValue8TF = new JTextField();
	sensors.add(sonarLeftSensorValue8L);
	sensors.add(sonarLeftSensorValue8TF);
	sensorTextFields.put("Sonar Left Sensor Value8", sonarLeftSensorValue8TF);
	sonarLeftSensorValue9L = new JLabel("Sonar Left Sensor Value9");
	sonarLeftSensorValue9TF = new JTextField();
	sensors.add(sonarLeftSensorValue9L);
	sensors.add(sonarLeftSensorValue9TF);
	sensorTextFields.put("Sonar Left Sensor Value9", sonarLeftSensorValue9TF);
	sonarRightSensorValue1L = new JLabel("Sonar Right Sensor Value1");
	sonarRightSensorValue1TF = new JTextField();
	sensors.add(sonarRightSensorValue1L);
	sensors.add(sonarRightSensorValue1TF);
	sensorTextFields.put("Sonar Right Sensor Value1", sonarRightSensorValue1TF);
	sonarRightSensorValue2L = new JLabel("Sonar Right Sensor Value2");
	sonarRightSensorValue2TF = new JTextField();
	sensors.add(sonarRightSensorValue2L);
	sensors.add(sonarRightSensorValue2TF);
	sensorTextFields.put("Sonar Right Sensor Value2", sonarRightSensorValue2TF);
	sonarRightSensorValue3L = new JLabel("Sonar Right Sensor Value3");
	sonarRightSensorValue3TF = new JTextField();
	sensors.add(sonarRightSensorValue3L);
	sensors.add(sonarRightSensorValue3TF);
	sensorTextFields.put("Sonar Right Sensor Value3", sonarRightSensorValue3TF);
	sonarRightSensorValue4L = new JLabel("Sonar Right Sensor Value4");
	sonarRightSensorValue4TF = new JTextField();
	sensors.add(sonarRightSensorValue4L);
	sensors.add(sonarRightSensorValue4TF);
	sensorTextFields.put("Sonar Right Sensor Value4", sonarRightSensorValue4TF);
	sonarRightSensorValue5L = new JLabel("Sonar Right Sensor Value5");
	sonarRightSensorValue5TF = new JTextField();
	sensors.add(sonarRightSensorValue5L);
	sensors.add(sonarRightSensorValue5TF);
	sensorTextFields.put("Sonar Right Sensor Value5", sonarRightSensorValue5TF);
	sonarRightSensorValue6L = new JLabel("Sonar Right Sensor Value6");
	sonarRightSensorValue6TF = new JTextField();
	sensors.add(sonarRightSensorValue6L);
	sensors.add(sonarRightSensorValue6TF);
	sensorTextFields.put("Sonar Right Sensor Value6", sonarRightSensorValue6TF);
	sonarRightSensorValue7L = new JLabel("Sonar Right Sensor Value7");
	sonarRightSensorValue7TF = new JTextField();
	sensors.add(sonarRightSensorValue7L);
	sensors.add(sonarRightSensorValue7TF);
	sensorTextFields.put("Sonar Right Sensor Value7", sonarRightSensorValue7TF);
	sonarRightSensorValue8L = new JLabel("Sonar Right Sensor Value8");
	sonarRightSensorValue8TF = new JTextField();
	sensors.add(sonarRightSensorValue8L);
	sensors.add(sonarRightSensorValue8TF);
	sensorTextFields.put("Sonar Right Sensor Value8", sonarRightSensorValue8TF);
	sonarRightSensorValue9L = new JLabel("Sonar Right Sensor Value9");
	sonarRightSensorValue9TF = new JTextField();
	sensors.add(sonarRightSensorValue9L);
	sensors.add(sonarRightSensorValue9TF);
	sensorTextFields.put("Sonar Right Sensor Value9", sonarRightSensorValue9TF);
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
    public boolean setSensorValue(String sensorSonars, String value){
        if(sensorTextFields.containsKey(sensorSonars)) {
            sensorTextFields.get(sensorSonars).setEditable(true);
            sensorTextFields.get(sensorSonars).setText(value);
            sensorTextFields.get(sensorSonars).setEditable(false);
            return true;
        }
        else return false;
    }


    //what should actually be passed to the main GUI
    public JScrollPane getScrollPane(){ return scrollPane;}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Sonars();
            }
        });
    }
}
