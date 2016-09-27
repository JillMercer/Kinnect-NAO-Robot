package edu.sru.thangiah.nao.diagnostic.gui;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class InertialSensors {
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
    
    	private JLabel gyroscope;
	private JLabel gyroscopeXL;
	private JTextField gyroscopeXTF;
	private JLabel gyroscopeYL;
	private JTextField gyroscopeYTF;
	private JLabel gyroscopeZL;
	private JTextField gyroscopeZTF;

	private JLabel angel;
	private JLabel angleXL;
	private JTextField angleXTF;
	private JLabel angleYL;
	private JTextField angleYTF;
	private JLabel angleZL;
	private JTextField angleZTF;

	private JLabel accelerometer;
	private JLabel accelerometerXL;
	private JTextField accelerometerXTF;
	private JLabel accelerometerYL;
	private JTextField accelerometerYTF;
	private JLabel accelerometerZL;
	private JTextField accelerometerZTF;




    public InertialSensors(){
        contentPane = new JPanel();
        sensorTextFields = new HashMap<>();
        sensors = new JPanel();
        sensors.setLayout(new GridLayout(20,1));

        gyroscope = new JLabel("Gyroscope");
	sensors.add(gyroscope);
	sensors.add(new JSeparator());
	gyroscopeXL = new JLabel("Gyroscope X");
	gyroscopeXTF = new JTextField();
	sensors.add(gyroscopeXL);
	sensors.add(gyroscopeXTF);
	sensorTextFields.put("Gyroscope X", gyroscopeXTF);
	gyroscopeYL = new JLabel("Gyroscope Y");
	gyroscopeYTF = new JTextField();
	sensors.add(gyroscopeYL);
	sensors.add(gyroscopeYTF);
	sensorTextFields.put("Gyroscope Y", gyroscopeYTF);
	gyroscopeZL = new JLabel("Gyroscope Z");
	gyroscopeZTF = new JTextField();
	sensors.add(gyroscopeZL);
	sensors.add(gyroscopeZTF);
	sensorTextFields.put("Gyroscope Z", gyroscopeZTF);
	sensors.add(new JSeparator());
	sensors.add(new JSeparator());

	angel = new JLabel("Angel");
	sensors.add(angel);
	sensors.add(new JSeparator());
	angleXL = new JLabel("Angle X");
	angleXTF = new JTextField();
	sensors.add(angleXL);
	sensors.add(angleXTF);
	sensorTextFields.put("Angle X", angleXTF);
	angleYL = new JLabel("Angle Y");
	angleYTF = new JTextField();
	sensors.add(angleYL);
	sensors.add(angleYTF);
	sensorTextFields.put("Angle Y", angleYTF);
	angleZL = new JLabel("Angle Z");
	angleZTF = new JTextField();
	sensors.add(angleZL);
	sensors.add(angleZTF);
	sensorTextFields.put("Angle Z", angleZTF);
	sensors.add(new JSeparator());
	sensors.add(new JSeparator());

	accelerometer = new JLabel("Accelerometer");
	sensors.add(accelerometer);
	sensors.add(new JSeparator());
	accelerometerXL = new JLabel("Accelerometer X");
	accelerometerXTF = new JTextField();
	sensors.add(accelerometerXL);
	sensors.add(accelerometerXTF);
	sensorTextFields.put("Accelerometer X", accelerometerXTF);
	accelerometerYL = new JLabel("Accelerometer Y");
	accelerometerYTF = new JTextField();
	sensors.add(accelerometerYL);
	sensors.add(accelerometerYTF);
	sensorTextFields.put("Accelerometer Y", accelerometerYTF);
	accelerometerZL = new JLabel("Accelerometer Z");
	accelerometerZTF = new JTextField();
	sensors.add(accelerometerZL);
	sensors.add(accelerometerZTF);
	sensorTextFields.put("Accelerometer Z", accelerometerZTF);
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
    public boolean setSensorValue(String sensorInertialSensors, String value){
        if(sensorTextFields.containsKey(sensorInertialSensors)){
            sensorTextFields.get(sensorInertialSensors).setEditable(true);
            sensorTextFields.get(sensorInertialSensors).setText(value);
            sensorTextFields.get(sensorInertialSensors).setEditable(false);
            return true;
        }
        else return false;
    }


    //what should actually be passed to the main GUI
    public JScrollPane getScrollPane(){ return scrollPane;}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InertialSensors();
            }
        });
    }
}
