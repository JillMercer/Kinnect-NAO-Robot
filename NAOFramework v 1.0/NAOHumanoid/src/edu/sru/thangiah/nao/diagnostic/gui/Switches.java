package edu.sru.thangiah.nao.diagnostic.gui;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Switches {
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
    
    	private JLabel chestBoardSwitches;
	private JLabel chestBoardButtonL;
	private JTextField chestBoardButtonTF;

	private JLabel leftFootSwitches;
	private JLabel leftFootRightBumperL;
	private JTextField leftFootRightBumperTF;
	private JLabel leftFootLeftBumperL;
	private JTextField leftFootLeftBumperTF;

	private JLabel rightFootSwitches;
	private JLabel rightFootRightBumperL;
	private JTextField rightFootRightBumperTF;
	private JLabel rightFootLeftBumperL;
	private JTextField rightFootLeftBumperTF;




    public Switches(){
        contentPane = new JPanel();
        sensorTextFields = new HashMap<>();
        sensors = new JPanel();
        sensors.setLayout(new GridLayout(20,1));

        	chestBoardSwitches = new JLabel("Chest Board Switches");
	sensors.add(chestBoardSwitches);
	sensors.add(new JSeparator());
	chestBoardButtonL = new JLabel("Chest Board Button");
	chestBoardButtonTF = new JTextField();
	sensors.add(chestBoardButtonL);
	sensors.add(chestBoardButtonTF);
	sensorTextFields.put("Chest Board Button", chestBoardButtonTF);
	sensors.add(new JSeparator());
	sensors.add(new JSeparator());

	leftFootSwitches = new JLabel("Left Foot Switches");
	sensors.add(leftFootSwitches);
	sensors.add(new JSeparator());
	leftFootRightBumperL = new JLabel("Left Foot Right Bumper");
	leftFootRightBumperTF = new JTextField();
	sensors.add(leftFootRightBumperL);
	sensors.add(leftFootRightBumperTF);
	sensorTextFields.put("Left Foot Right Bumper", leftFootRightBumperTF);
	leftFootLeftBumperL = new JLabel("Left Foot Left Bumper");
	leftFootLeftBumperTF = new JTextField();
	sensors.add(leftFootLeftBumperL);
	sensors.add(leftFootLeftBumperTF);
	sensorTextFields.put("Left Foot Left Bumper", leftFootLeftBumperTF);
	sensors.add(new JSeparator());
	sensors.add(new JSeparator());

	rightFootSwitches = new JLabel("Right Foot Switches");
	sensors.add(rightFootSwitches);
	sensors.add(new JSeparator());
	rightFootRightBumperL = new JLabel("Right Foot Right Bumper");
	rightFootRightBumperTF = new JTextField();
	sensors.add(rightFootRightBumperL);
	sensors.add(rightFootRightBumperTF);
	sensorTextFields.put("Right Foot Right Bumper", rightFootRightBumperTF);
	rightFootLeftBumperL = new JLabel("Right Foot Left Bumper");
	rightFootLeftBumperTF = new JTextField();
	sensors.add(rightFootLeftBumperL);
	sensors.add(rightFootLeftBumperTF);
	sensorTextFields.put("Right Foot Left Bumper", rightFootLeftBumperTF);
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
    public boolean setSensorValue(String sensorSwitches, String value){
        if(sensorTextFields.containsKey(sensorSwitches)) {
            sensorTextFields.get(sensorSwitches).setEditable(true);
            sensorTextFields.get(sensorSwitches).setText(value);
            sensorTextFields.get(sensorSwitches).setEditable(false);
            return true;
        }
        else return false;
    }


    //what should actually be passed to the main GUI
    public JScrollPane getScrollPane(){ return scrollPane;}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Switches();
            }
        });
    }
}
