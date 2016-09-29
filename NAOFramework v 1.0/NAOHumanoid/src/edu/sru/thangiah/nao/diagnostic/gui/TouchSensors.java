package edu.sru.thangiah.nao.diagnostic.gui;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class TouchSensors {
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
    
    	private JLabel headTouch;
	private JLabel headFrontTouchL;
	private JTextField headFrontTouchTF;
	private JLabel headRearTouchL;
	private JTextField headRearTouchTF;
	private JLabel headMiddleTouchL;
	private JTextField headMiddleTouchTF;
	private JLabel leftHandTouch;
	private JLabel leftHandBackTouchL;
	private JTextField leftHandBackTouchTF;
	private JLabel leftHandLeftTouchL;
	private JTextField leftHandLeftTouchTF;
	private JLabel leftHandRightTouchL;
	private JTextField leftHandRightTouchTF;
	private JLabel rightHandTouch;
	private JLabel rightHandBackTouchL;
	private JTextField rightHandBackTouchTF;
	private JLabel rightHandLeftTouchL;
	private JTextField rightHandLeftTouchTF;
	private JLabel rightHandRightTouchL;
	private JTextField rightHandRightTouchTF;



    public TouchSensors(){
        contentPane = new JPanel();
        sensorTextFields = new HashMap<>();
        sensors = new JPanel();
        sensors.setLayout(new GridLayout(20,1));

        headTouch = new JLabel("Head Touch");
	sensors.add(headTouch);
	sensors.add(new JSeparator());
	headFrontTouchL = new JLabel("Head Front Touch");
	headFrontTouchTF = new JTextField();
	sensors.add(headFrontTouchL);
	sensors.add(headFrontTouchTF);
	sensorTextFields.put("Head Front Touch", headFrontTouchTF);
	headRearTouchL = new JLabel("Head Rear Touch");
	headRearTouchTF = new JTextField();
	sensors.add(headRearTouchL);
	sensors.add(headRearTouchTF);
	sensorTextFields.put("Head Rear Touch", headRearTouchTF);
	headMiddleTouchL = new JLabel("Head Middle Touch");
	headMiddleTouchTF = new JTextField();
	sensors.add(headMiddleTouchL);
	sensors.add(headMiddleTouchTF);
	sensorTextFields.put("Head Middle Touch", headMiddleTouchTF);
	sensors.add(new JSeparator());
	sensors.add(new JSeparator());
	leftHandTouch = new JLabel("Left Hand Touch");
	sensors.add(leftHandTouch);
	sensors.add(new JSeparator());
	leftHandBackTouchL = new JLabel("Left Hand Back Touch");
	leftHandBackTouchTF = new JTextField();
	sensors.add(leftHandBackTouchL);
	sensors.add(leftHandBackTouchTF);
	sensorTextFields.put("Left Hand Back Touch", leftHandBackTouchTF);
	leftHandLeftTouchL = new JLabel("Left Hand Left Touch");
	leftHandLeftTouchTF = new JTextField();
	sensors.add(leftHandLeftTouchL);
	sensors.add(leftHandLeftTouchTF);
	sensorTextFields.put("Left Hand Left Touch", leftHandLeftTouchTF);
	leftHandRightTouchL = new JLabel("Left Hand Right Touch");
	leftHandRightTouchTF = new JTextField();
	sensors.add(leftHandRightTouchL);
	sensors.add(leftHandRightTouchTF);
	sensorTextFields.put("Left Hand Right Touch", leftHandRightTouchTF);
	sensors.add(new JSeparator());
	sensors.add(new JSeparator());
	rightHandTouch = new JLabel("Right Hand Touch");
	sensors.add(rightHandTouch);
	sensors.add(new JSeparator());
	rightHandBackTouchL = new JLabel("Right Hand Back Touch");
	rightHandBackTouchTF = new JTextField();
	sensors.add(rightHandBackTouchL);
	sensors.add(rightHandBackTouchTF);
	sensorTextFields.put("Right Hand Back Touch", rightHandBackTouchTF);
	rightHandLeftTouchL = new JLabel("Right Hand Left Touch");
	rightHandLeftTouchTF = new JTextField();
	sensors.add(rightHandLeftTouchL);
	sensors.add(rightHandLeftTouchTF);
	sensorTextFields.put("Right Hand Left Touch", rightHandLeftTouchTF);
	rightHandRightTouchL = new JLabel("Right Hand Right Touch");
	rightHandRightTouchTF = new JTextField();
	sensors.add(rightHandRightTouchL);
	sensors.add(rightHandRightTouchTF);
	sensorTextFields.put("Right Hand Right Touch", rightHandRightTouchTF);
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
    public boolean setSensorValue(String sensorTouchSensors, String value){
        if(sensorTextFields.containsKey(sensorTouchSensors)) {
            sensorTextFields.get(sensorTouchSensors).setEditable(true);
            sensorTextFields.get(sensorTouchSensors).setText(value);
            sensorTextFields.get(sensorTouchSensors).setEditable(false);
            return true;
        }
        else return false;
    }


    //what should actually be passed to the main GUI
    public JScrollPane getScrollPane(){ return scrollPane;}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TouchSensors();
            }
        });
    }
}
