package edu.sru.thangiah.nao.diagnostic.gui;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sam on 6/4/2015.
 * Note on sensors/actuators from "doc.aldebaran.com"
 Position/Actuator
 Joint angle (in radian) to reach.

 Exception: for hands, aperture (in percentage); 0 means closed, 1 means opened.

 Content update:

 if Stiffness > 0: the last position requested by a timed command.
 if Stiffness <= 0: the same value as the Position/Sensor.
 Position/Actuator (rad)
 Joint angle (in radian) to reach.

 Content update:

 if Stiffness > 0: the last position requested by a timed command.
 if Stiffness <= 0: the same value as the Position/Sensor.
 Position/Actuator (%)
 Hand aperture (in percentage) to reach; 0 means closed, 1 means opened.

 Content update:

 if Stiffness > 0: the last position requested by a timed command.
 if Stiffness <= 0: the same value as the Position/Sensor.
 Stiffness (%)
 Stiffness of the joint (in percentage); 0.0 means 0% and 1.0 means 100% (full power).

 In the motorboard, this percentage is directly applied to the max current. Setting the Stiffness to 0.5 means that the electric current limitation is reduced to 50%.

 The Stiffness is sent to the motor board every DCM cycle time, so you can decrease/increase the control loop very fast. However, the current limitation may have some delay.

 If the Stiffness is <0, the motor is free (no electromagnetic break), but due to hardware limitation, it�s only possible when the two motors of the same board are <0 (both are then free). If not, it�s still electromagnetic brake, but with a security that disable it when there is some movement, to protect the mechanics.

 Stiffness may be automatically cut in case of problem (calibration, sensors �) seen at the DCM level or at the �c level, in order to protect the robot.

 Position/Sensor (rad)
 Angle of the joint (in radian).

 Content update:

 The sensor used is a Magnetic Rotary Encoder (MRE), used like potentiometer. It�s a 12bits precise value (from 0 to 4095) change in rad.

 Position/Sensor (%)
 Hand aperture (in percentage); 0 means closed, 1 means opened.

 Content update:

 The sensor used is a Magnetic Rotary Encoder (MRE), used like potentiometer. It�s a 12bits precise value (from 0 to 4095) change in percentage (0.0 to 1.0).

 Current (A)
 Every motorboard has a current sensor for each motor that is a shunt resistor. The current is an absolute value in Ampere.

 Every joint has an electric current limitation: if the current reach the �Max� value (�ElectricCurrent/Sensor/Max�) the PWM (return by the control loop) will be decreased a bit until it returns under the maximum value, and it�s increased again after. This is a kind of current control loop around the maximum value. The aim of this limitation is to protect the motor, the electronic board, and the mechanical part of the joint.

 Temperature (�C)
 The motor temperature is a simulated one, using electric current value of the motor. The motor board implements a temperature limitation to protect the motor. The temperature limitation depends on robot version.
 Temperature status
 The status is computed accordingly to the temperature limitation to protecting the motor. A non null value implies an automatic correction of the Stiffness.

 0: means regular temperature
 1: means temperature has reach the max limit, start reducing stiffness.
 2: means the joint is very hot, stiffness reduced over 30%.
 3: means the joint is critically hot, stiffness value is set to 0.
 Error
 The results of a diagnosis (see ALDiagnosis and Temperature Diagnosis). A int with 0 (success) or 1 (error).
 Level of failure severity
 This value defines the consequence of the device failure on the robot behavior. The higher the level is the less the robot will be functional.

 The value could be: NEGLIGIBLE, SERIOUS or CRITICAL (see Diagnosis effect for more details about the robot behavior when a failure is detected).
 */
public class MotionSensors {
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
    private JLabel headYaw;
    private JTextField headYawPositionActuatorTF;
    private JLabel headYawPositionActuatorL;
    private JTextField headYawPositionSensorTF;
    private JLabel headYawPositionSensorL;
    private JTextField headYawElectricCurrentTF;
    private JLabel headYawElectricCurrentL;
    private JTextField headYawTemperatureValueTF;
    private JLabel headYawTemperatureValueL;
    private JTextField headYawHardnessTF;
    private JLabel headYawHardnessL;
    private JTextField headYawTemperatureStatusTF;
    private JLabel headYawTemperatureStatusL;

    private JLabel headPitch;
    private JTextField headPitchPositionActuatorTF;
    private JLabel headPitchPositionActuatorL;
    private JTextField headPitchPositionSensorTF;
    private JLabel headPitchPositionSensorL;
    private JTextField headPitchElectricCurrentTF;
    private JLabel headPitchElectricCurrentL;
    private JTextField headPitchTemperatureValueTF;
    private JLabel headPitchTemperatureValueL;
    private JTextField headPitchHardnessTF;
    private JLabel headPitchHardnessL;
    private JTextField headPitchTemperatureStatusTF;
    private JLabel headPitchTemperatureStatusL;

    private JLabel lElbowYaw;
    private JTextField lElbowYawPositionActuatorTF;
    private JLabel lElbowYawPositionActuatorL;
    private JTextField lElbowYawPositionSensorTF;
    private JLabel lElbowYawPositionSensorL;
    private JTextField lElbowYawElectricCurrentTF;
    private JLabel lElbowYawElectricCurrentL;
    private JTextField lElbowYawTemperatureValueTF;
    private JLabel lElbowYawTemperatureValueL;
    private JTextField lElbowYawHardnessTF;
    private JLabel lElbowYawHardnessL;
    private JTextField lElbowYawTemperatureStatusTF;
    private JLabel lElbowYawTemperatureStatusL;

    private JLabel lElbowRoll;
    private JTextField lElbowRollPositionActuatorTF;
    private JLabel lElbowRollPositionActuatorL;
    private JTextField lElbowRollPositionSensorTF;
    private JLabel lElbowRollPositionSensorL;
    private JTextField lElbowRollElectricCurrentTF;
    private JLabel lElbowRollElectricCurrentL;
    private JTextField lElbowRollTemperatureValueTF;
    private JLabel lElbowRollTemperatureValueL;
    private JTextField lElbowRollHardnessTF;
    private JLabel lElbowRollHardnessL;
    private JTextField lElbowRollTemperatureStatusTF;
    private JLabel lElbowRollTemperatureStatusL;

    private JLabel rElbowYaw;
    private JTextField rElbowYawPositionActuatorTF;
    private JLabel rElbowYawPositionActuatorL;
    private JTextField rElbowYawPositionSensorTF;
    private JLabel rElbowYawPositionSensorL;
    private JTextField rElbowYawElectricCurrentTF;
    private JLabel rElbowYawElectricCurrentL;
    private JTextField rElbowYawTemperatureValueTF;
    private JLabel rElbowYawTemperatureValueL;
    private JTextField rElbowYawHardnessTF;
    private JLabel rElbowYawHardnessL;
    private JTextField rElbowYawTemperatureStatusTF;
    private JLabel rElbowYawTemperatureStatusL;

    private JLabel rElbowRoll;
    private JTextField rElbowRollPositionActuatorTF;
    private JLabel rElbowRollPositionActuatorL;
    private JTextField rElbowRollPositionSensorTF;
    private JLabel rElbowRollPositionSensorL;
    private JTextField rElbowRollElectricCurrentTF;
    private JLabel rElbowRollElectricCurrentL;
    private JTextField rElbowRollTemperatureValueTF;
    private JLabel rElbowRollTemperatureValueL;
    private JTextField rElbowRollHardnessTF;
    private JLabel rElbowRollHardnessL;
    private JTextField rElbowRollTemperatureStatusTF;
    private JLabel rElbowRollTemperatureStatusL;

    private JLabel lHand;
    private JTextField lHandPositionActuatorTF;
    private JLabel lHandPositionActuatorL;
    private JTextField lHandPositionSensorTF;
    private JLabel lHandPositionSensorL;
    private JTextField lHandElectricCurrentTF;
    private JLabel lHandElectricCurrentL;
    private JTextField lHandTemperatureValueTF;
    private JLabel lHandTemperatureValueL;
    private JTextField lHandHardnessTF;
    private JLabel lHandHardnessL;
    private JTextField lHandTemperatureStatusTF;
    private JLabel lHandTemperatureStatusL;

    private JLabel lWristYaw;
    private JTextField lWristYawPositionActuatorTF;
    private JLabel lWristYawPositionActuatorL;
    private JTextField lWristYawPositionSensorTF;
    private JLabel lWristYawPositionSensorL;
    private JTextField lWristYawElectricCurrentTF;
    private JLabel lWristYawElectricCurrentL;
    private JTextField lWristYawTemperatureValueTF;
    private JLabel lWristYawTemperatureValueL;
    private JTextField lWristYawHardnessTF;
    private JLabel lWristYawHardnessL;
    private JTextField lWristYawTemperatureStatusTF;
    private JLabel lWristYawTemperatureStatusL;

    private JLabel rHand;
    private JTextField rHandPositionActuatorTF;
    private JLabel rHandPositionActuatorL;
    private JTextField rHandPositionSensorTF;
    private JLabel rHandPositionSensorL;
    private JTextField rHandElectricCurrentTF;
    private JLabel rHandElectricCurrentL;
    private JTextField rHandTemperatureValueTF;
    private JLabel rHandTemperatureValueL;
    private JTextField rHandHardnessTF;
    private JLabel rHandHardnessL;
    private JTextField rHandTemperatureStatusTF;
    private JLabel rHandTemperatureStatusL;

    private JLabel rWristYaw;
    private JTextField rWristYawPositionActuatorTF;
    private JLabel rWristYawPositionActuatorL;
    private JTextField rWristYawPositionSensorTF;
    private JLabel rWristYawPositionSensorL;
    private JTextField rWristYawElectricCurrentTF;
    private JLabel rWristYawElectricCurrentL;
    private JTextField rWristYawTemperatureValueTF;
    private JLabel rWristYawTemperatureValueL;
    private JTextField rWristYawHardnessTF;
    private JLabel rWristYawHardnessL;
    private JTextField rWristYawTemperatureStatusTF;
    private JLabel rWristYawTemperatureStatusL;

    private JLabel lShoulderPitch;
    private JTextField lShoulderPitchPositionActuatorTF;
    private JLabel lShoulderPitchPositionActuatorL;
    private JTextField lShoulderPitchPositionSensorTF;
    private JLabel lShoulderPitchPositionSensorL;
    private JTextField lShoulderPitchElectricCurrentTF;
    private JLabel lShoulderPitchElectricCurrentL;
    private JTextField lShoulderPitchTemperatureValueTF;
    private JLabel lShoulderPitchTemperatureValueL;
    private JTextField lShoulderPitchHardnessTF;
    private JLabel lShoulderPitchHardnessL;
    private JTextField lShoulderPitchTemperatureStatusTF;
    private JLabel lShoulderPitchTemperatureStatusL;

    private JLabel lShoulderRoll;
    private JTextField lShoulderRollPositionActuatorTF;
    private JLabel lShoulderRollPositionActuatorL;
    private JTextField lShoulderRollPositionSensorTF;
    private JLabel lShoulderRollPositionSensorL;
    private JTextField lShoulderRollElectricCurrentTF;
    private JLabel lShoulderRollElectricCurrentL;
    private JTextField lShoulderRollTemperatureValueTF;
    private JLabel lShoulderRollTemperatureValueL;
    private JTextField lShoulderRollHardnessTF;
    private JLabel lShoulderRollHardnessL;
    private JTextField lShoulderRollTemperatureStatusTF;
    private JLabel lShoulderRollTemperatureStatusL;


    private JLabel rShoulderPitch;
    private JTextField rShoulderPitchPositionActuatorTF;
    private JLabel rShoulderPitchPositionActuatorL;
    private JTextField rShoulderPitchPositionSensorTF;
    private JLabel rShoulderPitchPositionSensorL;
    private JTextField rShoulderPitchElectricCurrentTF;
    private JLabel rShoulderPitchElectricCurrentL;
    private JTextField rShoulderPitchTemperatureValueTF;
    private JLabel rShoulderPitchTemperatureValueL;
    private JTextField rShoulderPitchHardnessTF;
    private JLabel rShoulderPitchHardnessL;
    private JTextField rShoulderPitchTemperatureStatusTF;
    private JLabel rShoulderPitchTemperatureStatusL;

    private JLabel rShoulderRoll;
    private JTextField rShoulderRollPositionActuatorTF;
    private JLabel rShoulderRollPositionActuatorL;
    private JTextField rShoulderRollPositionSensorTF;
    private JLabel rShoulderRollPositionSensorL;
    private JTextField rShoulderRollElectricCurrentTF;
    private JLabel rShoulderRollElectricCurrentL;
    private JTextField rShoulderRollTemperatureValueTF;
    private JLabel rShoulderRollTemperatureValueL;
    private JTextField rShoulderRollHardnessTF;
    private JLabel rShoulderRollHardnessL;
    private JTextField rShoulderRollTemperatureStatusTF;
    private JLabel rShoulderRollTemperatureStatusL;

    private JLabel rHipRoll;
    private JTextField rHipRollPositionActuatorTF;
    private JLabel rHipRollPositionActuatorL;
    private JTextField rHipRollPositionSensorTF;
    private JLabel rHipRollPositionSensorL;
    private JTextField rHipRollElectricCurrentTF;
    private JLabel rHipRollElectricCurrentL;
    private JTextField rHipRollTemperatureValueTF;
    private JLabel rHipRollTemperatureValueL;
    private JTextField rHipRollHardnessTF;
    private JLabel rHipRollHardnessL;
    private JTextField rHipRollTemperatureStatusTF;
    private JLabel rHipRollTemperatureStatusL;

    private JLabel lHipRoll;
    private JTextField lHipRollPositionActuatorTF;
    private JLabel lHipRollPositionActuatorL;
    private JTextField lHipRollPositionSensorTF;
    private JLabel lHipRollPositionSensorL;
    private JTextField lHipRollElectricCurrentTF;
    private JLabel lHipRollElectricCurrentL;
    private JTextField lHipRollTemperatureValueTF;
    private JLabel lHipRollTemperatureValueL;
    private JTextField lHipRollHardnessTF;
    private JLabel lHipRollHardnessL;
    private JTextField lHipRollTemperatureStatusTF;
    private JLabel lHipRollTemperatureStatusL;

    private JLabel lHipYaw;
    private JTextField lHipYawPositionActuatorTF;
    private JLabel lHipYawPositionActuatorL;
    private JTextField lHipYawPositionSensorTF;
    private JLabel lHipYawPositionSensorL;
    private JTextField lHipYawElectricCurrentTF;
    private JLabel lHipYawElectricCurrentL;
    private JTextField lHipYawTemperatureValueTF;
    private JLabel lHipYawTemperatureValueL;
    private JTextField lHipYawHardnessTF;
    private JLabel lHipYawHardnessL;
    private JTextField lHipYawTemperatureStatusTF;
    private JLabel lHipYawTemperatureStatusL;

    private JLabel rHipYaw;
    private JTextField rHipYawPositionActuatorTF;
    private JLabel rHipYawPositionActuatorL;
    private JTextField rHipYawPositionSensorTF;
    private JLabel rHipYawPositionSensorL;
    private JTextField rHipYawElectricCurrentTF;
    private JLabel rHipYawElectricCurrentL;
    private JTextField rHipYawTemperatureValueTF;
    private JLabel rHipYawTemperatureValueL;
    private JTextField rHipYawHardnessTF;
    private JLabel rHipYawHardnessL;
    private JTextField rHipYawTemperatureStatusTF;
    private JLabel rHipYawTemperatureStatusL;

    private JLabel lHipPitch;
    private JTextField lHipPitchPositionActuatorTF;
    private JLabel lHipPitchPositionActuatorL;
    private JTextField lHipPitchPositionSensorTF;
    private JLabel lHipPitchPositionSensorL;
    private JTextField lHipPitchElectricCurrentTF;
    private JLabel lHipPitchElectricCurrentL;
    private JTextField lHipPitchTemperatureValueTF;
    private JLabel lHipPitchTemperatureValueL;
    private JTextField lHipPitchHardnessTF;
    private JLabel lHipPitchHardnessL;
    private JTextField lHipPitchTemperatureStatusTF;
    private JLabel lHipPitchTemperatureStatusL;

    private JLabel rHipPitch;
    private JTextField rHipPitchPositionActuatorTF;
    private JLabel rHipPitchPositionActuatorL;
    private JTextField rHipPitchPositionSensorTF;
    private JLabel rHipPitchPositionSensorL;
    private JTextField rHipPitchElectricCurrentTF;
    private JLabel rHipPitchElectricCurrentL;
    private JTextField rHipPitchTemperatureValueTF;
    private JLabel rHipPitchTemperatureValueL;
    private JTextField rHipPitchHardnessTF;
    private JLabel rHipPitchHardnessL;
    private JTextField rHipPitchTemperatureStatusTF;
    private JLabel rHipPitchTemperatureStatusL;

    private JLabel rKneePitch;
    private JTextField rKneePitchPositionActuatorTF;
    private JLabel rKneePitchPositionActuatorL;
    private JTextField rKneePitchPositionSensorTF;
    private JLabel rKneePitchPositionSensorL;
    private JTextField rKneePitchElectricCurrentTF;
    private JLabel rKneePitchElectricCurrentL;
    private JTextField rKneePitchTemperatureValueTF;
    private JLabel rKneePitchTemperatureValueL;
    private JTextField rKneePitchHardnessTF;
    private JLabel rKneePitchHardnessL;
    private JTextField rKneePitchTemperatureStatusTF;
    private JLabel rKneePitchTemperatureStatusL;

    private JLabel lKneePitch;
    private JTextField lKneePitchPositionActuatorTF;
    private JLabel lKneePitchPositionActuatorL;
    private JTextField lKneePitchPositionSensorTF;
    private JLabel lKneePitchPositionSensorL;
    private JTextField lKneePitchElectricCurrentTF;
    private JLabel lKneePitchElectricCurrentL;
    private JTextField lKneePitchTemperatureValueTF;
    private JLabel lKneePitchTemperatureValueL;
    private JTextField lKneePitchHardnessTF;
    private JLabel lKneePitchHardnessL;
    private JTextField lKneePitchTemperatureStatusTF;
    private JLabel lKneePitchTemperatureStatusL;

    private JLabel rAnklePitch;
    private JTextField rAnklePitchPositionActuatorTF;
    private JLabel rAnklePitchPositionActuatorL;
    private JTextField rAnklePitchPositionSensorTF;
    private JLabel rAnklePitchPositionSensorL;
    private JTextField rAnklePitchElectricCurrentTF;
    private JLabel rAnklePitchElectricCurrentL;
    private JTextField rAnklePitchTemperatureValueTF;
    private JLabel rAnklePitchTemperatureValueL;
    private JTextField rAnklePitchHardnessTF;
    private JLabel rAnklePitchHardnessL;
    private JTextField rAnklePitchTemperatureStatusTF;
    private JLabel rAnklePitchTemperatureStatusL;

    private JLabel lAnklePitch;
    private JTextField lAnklePitchPositionActuatorTF;
    private JLabel lAnklePitchPositionActuatorL;
    private JTextField lAnklePitchPositionSensorTF;
    private JLabel lAnklePitchPositionSensorL;
    private JTextField lAnklePitchElectricCurrentTF;
    private JLabel lAnklePitchElectricCurrentL;
    private JTextField lAnklePitchTemperatureValueTF;
    private JLabel lAnklePitchTemperatureValueL;
    private JTextField lAnklePitchHardnessTF;
    private JLabel lAnklePitchHardnessL;
    private JTextField lAnklePitchTemperatureStatusTF;
    private JLabel lAnklePitchTemperatureStatusL;

    private JLabel rAnkleRoll;
    private JTextField rAnkleRollPositionActuatorTF;
    private JLabel rAnkleRollPositionActuatorL;
    private JTextField rAnkleRollPositionSensorTF;
    private JLabel rAnkleRollPositionSensorL;
    private JTextField rAnkleRollElectricCurrentTF;
    private JLabel rAnkleRollElectricCurrentL;
    private JTextField rAnkleRollTemperatureValueTF;
    private JLabel rAnkleRollTemperatureValueL;
    private JTextField rAnkleRollHardnessTF;
    private JLabel rAnkleRollHardnessL;
    private JTextField rAnkleRollTemperatureStatusTF;
    private JLabel rAnkleRollTemperatureStatusL;

    private JLabel lAnkleRoll;
    private JTextField lAnkleRollPositionActuatorTF;
    private JLabel lAnkleRollPositionActuatorL;
    private JTextField lAnkleRollPositionSensorTF;
    private JLabel lAnkleRollPositionSensorL;
    private JTextField lAnkleRollElectricCurrentTF;
    private JLabel lAnkleRollElectricCurrentL;
    private JTextField lAnkleRollTemperatureValueTF;
    private JLabel lAnkleRollTemperatureValueL;
    private JTextField lAnkleRollHardnessTF;
    private JLabel lAnkleRollHardnessL;
    private JTextField lAnkleRollTemperatureStatusTF;
    private JLabel lAnkleRollTemperatureStatusL;


    public MotionSensors(){
        contentPane = new JPanel();
        sensorTextFields = new HashMap<>();
        sensors = new JPanel();
        sensors.setLayout(new GridLayout(300,1));

        headYaw = new JLabel("Head Yaw: ");
        sensors.add(headYaw);
        sensors.add(new JSeparator());
        headYawPositionActuatorTF = new JTextField();
        sensorTextFields.put("Head Yaw Position Actuator", headYawPositionActuatorTF);
        headYawPositionActuatorL = new JLabel("Head Yaw Position Actuator");
        sensors.add(headYawPositionActuatorL);
        sensors.add(headYawPositionActuatorTF);
        headYawPositionSensorTF = new JTextField();
        sensorTextFields.put("Head Yaw Position Sensor", headYawPositionSensorTF);
        headYawPositionSensorL = new JLabel("Head Yaw Position Sensor");
        sensors.add(headYawPositionSensorL);
        sensors.add(headYawPositionSensorTF);
        headYawElectricCurrentTF = new JTextField();
        sensorTextFields.put("Head Yaw Electric Current", headYawElectricCurrentTF);
        headYawElectricCurrentL = new JLabel("Head Yaw Electric Current");
        sensors.add(headYawElectricCurrentL);
        sensors.add(headYawElectricCurrentTF);
        headYawTemperatureValueTF = new JTextField();
        sensorTextFields.put("Head Yaw Temperature Value", headYawTemperatureValueTF);
        headYawTemperatureValueL = new JLabel("Head Yaw Temperature Value");
        sensors.add(headYawTemperatureValueL);
        sensors.add(headYawTemperatureValueTF);
        headYawHardnessTF = new JTextField();
        sensorTextFields.put("Head Yaw Hardness", headYawHardnessTF);
        headYawHardnessL = new JLabel("Head Yaw Hardness");
        sensors.add(headYawHardnessL);
        sensors.add(headYawHardnessTF);
        headYawTemperatureStatusTF = new JTextField();
        sensorTextFields.put("Head Yaw Temperature Status", headYawTemperatureStatusTF);
        headYawTemperatureStatusL = new JLabel("Head Yaw Temperature Status");
        sensors.add(headYawTemperatureStatusL);
        sensors.add(headYawTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        headPitch = new JLabel("Head Pitch: ");
        sensors.add(headPitch);
        sensors.add(new JSeparator());
        headPitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("Head Pitch Position Actuator", headPitchPositionActuatorTF);
        headPitchPositionActuatorL = new JLabel("Head Pitch Position Actuator");
        sensors.add(headPitchPositionActuatorL);
        sensors.add(headPitchPositionActuatorTF);
        headPitchPositionSensorTF = new JTextField();
        sensorTextFields.put("Head Pitch Position Sensor", headPitchPositionSensorTF);
        headPitchPositionSensorL = new JLabel("Head Pitch Position Sensor");
        sensors.add(headPitchPositionSensorL);
        sensors.add(headPitchPositionSensorTF);
        headPitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("Head Pitch Electric Current", headPitchElectricCurrentTF);
        headPitchElectricCurrentL = new JLabel("Head Pitch Electric Current");
        sensors.add(headPitchElectricCurrentL);
        sensors.add(headPitchElectricCurrentTF);
        headPitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("Head Pitch Temperature Value", headPitchTemperatureValueTF);
        headPitchTemperatureValueL = new JLabel("Head Pitch Temperature Value");
        sensors.add(headPitchTemperatureValueL);
        sensors.add(headPitchTemperatureValueTF);
        headPitchHardnessTF = new JTextField();
        sensorTextFields.put("Head Pitch Hardness", headPitchHardnessTF);
        headPitchHardnessL = new JLabel("Head Pitch Hardness");
        sensors.add(headPitchHardnessL);
        sensors.add(headPitchHardnessTF);
        headPitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("Head Pitch Temperature Status", headPitchTemperatureStatusTF);
        headPitchTemperatureStatusL = new JLabel("Head Pitch Temperature Status");
        sensors.add(headPitchTemperatureStatusL);
        sensors.add(headPitchTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lElbowYaw = new JLabel("L Elbow Yaw: ");
        sensors.add(lElbowYaw);
        sensors.add(new JSeparator());
        lElbowYawPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Elbow Yaw Position Actuator", lElbowYawPositionActuatorTF);
        lElbowYawPositionActuatorL = new JLabel("L Elbow Yaw Position Actuator");
        sensors.add(lElbowYawPositionActuatorL);
        sensors.add(lElbowYawPositionActuatorTF);
        lElbowYawPositionSensorTF = new JTextField();
        sensorTextFields.put("L Elbow Yaw Position Sensor", lElbowYawPositionSensorTF);
        lElbowYawPositionSensorL = new JLabel("L Elbow Yaw Position Sensor");
        sensors.add(lElbowYawPositionSensorL);
        sensors.add(lElbowYawPositionSensorTF);
        lElbowYawElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Elbow Yaw Electric Current", lElbowYawElectricCurrentTF);
        lElbowYawElectricCurrentL = new JLabel("L Elbow Yaw Electric Current");
        sensors.add(lElbowYawElectricCurrentL);
        sensors.add(lElbowYawElectricCurrentTF);
        lElbowYawTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Elbow Yaw Temperature Value", lElbowYawTemperatureValueTF);
        lElbowYawTemperatureValueL = new JLabel("L Elbow Yaw Temperature Value");
        sensors.add(lElbowYawTemperatureValueL);
        sensors.add(lElbowYawTemperatureValueTF);
        lElbowYawHardnessTF = new JTextField();
        sensorTextFields.put("L Elbow Yaw Hardness", lElbowYawHardnessTF);
        lElbowYawHardnessL = new JLabel("L Elbow Yaw Hardness");
        sensors.add(lElbowYawHardnessL);
        sensors.add(lElbowYawHardnessTF);
        lElbowYawTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Elbow Yaw Temperature Status", lElbowYawTemperatureStatusTF);
        lElbowYawTemperatureStatusL = new JLabel("L Elbow Yaw Temperature Status");
        sensors.add(lElbowYawTemperatureStatusL);
        sensors.add(lElbowYawTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lElbowRoll = new JLabel("L Elbow Roll: ");
        sensors.add(lElbowRoll);
        sensors.add(new JSeparator());
        lElbowRollPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Elbow Roll Position Actuator", lElbowRollPositionActuatorTF);
        lElbowRollPositionActuatorL = new JLabel("L Elbow Roll Position Actuator");
        sensors.add(lElbowRollPositionActuatorL);
        sensors.add(lElbowRollPositionActuatorTF);
        lElbowRollPositionSensorTF = new JTextField();
        sensorTextFields.put("L Elbow Roll Position Sensor", lElbowRollPositionSensorTF);
        lElbowRollPositionSensorL = new JLabel("L Elbow Roll Position Sensor");
        sensors.add(lElbowRollPositionSensorL);
        sensors.add(lElbowRollPositionSensorTF);
        lElbowRollElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Elbow Roll Electric Current", lElbowRollElectricCurrentTF);
        lElbowRollElectricCurrentL = new JLabel("L Elbow Roll Electric Current");
        sensors.add(lElbowRollElectricCurrentL);
        sensors.add(lElbowRollElectricCurrentTF);
        lElbowRollTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Elbow Roll Temperature Value", lElbowRollTemperatureValueTF);
        lElbowRollTemperatureValueL = new JLabel("L Elbow Roll Temperature Value");
        sensors.add(lElbowRollTemperatureValueL);
        sensors.add(lElbowRollTemperatureValueTF);
        lElbowRollHardnessTF = new JTextField();
        sensorTextFields.put("L Elbow Roll Hardness", lElbowRollHardnessTF);
        lElbowRollHardnessL = new JLabel("L Elbow Roll Hardness");
        sensors.add(lElbowRollHardnessL);
        sensors.add(lElbowRollHardnessTF);
        lElbowRollTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Elbow Roll Temperature Status", lElbowRollTemperatureStatusTF);
        lElbowRollTemperatureStatusL = new JLabel("L Elbow Roll Temperature Status");
        sensors.add(lElbowRollTemperatureStatusL);
        sensors.add(lElbowRollTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rElbowYaw = new JLabel("R Elbow Yaw: ");
        sensors.add(rElbowYaw);
        sensors.add(new JSeparator());
        rElbowYawPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Elbow Yaw Position Actuator", rElbowYawPositionActuatorTF);
        rElbowYawPositionActuatorL = new JLabel("R Elbow Yaw Position Actuator");
        sensors.add(rElbowYawPositionActuatorL);
        sensors.add(rElbowYawPositionActuatorTF);
        rElbowYawPositionSensorTF = new JTextField();
        sensorTextFields.put("R Elbow Yaw Position Sensor", rElbowYawPositionSensorTF);
        rElbowYawPositionSensorL = new JLabel("R Elbow Yaw Position Sensor");
        sensors.add(rElbowYawPositionSensorL);
        sensors.add(rElbowYawPositionSensorTF);
        rElbowYawElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Elbow Yaw Electric Current", rElbowYawElectricCurrentTF);
        rElbowYawElectricCurrentL = new JLabel("R Elbow Yaw Electric Current");
        sensors.add(rElbowYawElectricCurrentL);
        sensors.add(rElbowYawElectricCurrentTF);
        rElbowYawTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Elbow Yaw Temperature Value", rElbowYawTemperatureValueTF);
        rElbowYawTemperatureValueL = new JLabel("R Elbow Yaw Temperature Value");
        sensors.add(rElbowYawTemperatureValueL);
        sensors.add(rElbowYawTemperatureValueTF);
        rElbowYawHardnessTF = new JTextField();
        sensorTextFields.put("R Elbow Yaw Hardness", rElbowYawHardnessTF);
        rElbowYawHardnessL = new JLabel("R Elbow Yaw Hardness");
        sensors.add(rElbowYawHardnessL);
        sensors.add(rElbowYawHardnessTF);
        rElbowYawTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Elbow Yaw Temperature Status", rElbowYawTemperatureStatusTF);
        rElbowYawTemperatureStatusL = new JLabel("R Elbow Yaw Temperature Status");
        sensors.add(rElbowYawTemperatureStatusL);
        sensors.add(rElbowYawTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rElbowRoll = new JLabel("R Elbow Roll: ");
        sensors.add(rElbowRoll);
        sensors.add(new JSeparator());
        rElbowRollPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Elbow Roll Position Actuator", rElbowRollPositionActuatorTF);
        rElbowRollPositionActuatorL = new JLabel("R Elbow Roll Position Actuator");
        sensors.add(rElbowRollPositionActuatorL);
        sensors.add(rElbowRollPositionActuatorTF);
        rElbowRollPositionSensorTF = new JTextField();
        sensorTextFields.put("R Elbow Roll Position Sensor", rElbowRollPositionSensorTF);
        rElbowRollPositionSensorL = new JLabel("R Elbow Roll Position Sensor");
        sensors.add(rElbowRollPositionSensorL);
        sensors.add(rElbowRollPositionSensorTF);
        rElbowRollElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Elbow Roll Electric Current", rElbowRollElectricCurrentTF);
        rElbowRollElectricCurrentL = new JLabel("R Elbow Roll Electric Current");
        sensors.add(rElbowRollElectricCurrentL);
        sensors.add(rElbowRollElectricCurrentTF);
        rElbowRollTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Elbow Roll Temperature Value", rElbowRollTemperatureValueTF);
        rElbowRollTemperatureValueL = new JLabel("R Elbow Roll Temperature Value");
        sensors.add(rElbowRollTemperatureValueL);
        sensors.add(rElbowRollTemperatureValueTF);
        rElbowRollHardnessTF = new JTextField();
        sensorTextFields.put("R Elbow Roll Hardness", rElbowRollHardnessTF);
        rElbowRollHardnessL = new JLabel("R Elbow Roll Hardness");
        sensors.add(rElbowRollHardnessL);
        sensors.add(rElbowRollHardnessTF);
        rElbowRollTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Elbow Roll Temperature Status", rElbowRollTemperatureStatusTF);
        rElbowRollTemperatureStatusL = new JLabel("R Elbow Roll Temperature Status");
        sensors.add(rElbowRollTemperatureStatusL);
        sensors.add(rElbowRollTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lHand = new JLabel("L Hand: ");
        sensors.add(lHand);
        sensors.add(new JSeparator());
        lHandPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Hand Position Actuator", lHandPositionActuatorTF);
        lHandPositionActuatorL = new JLabel("L Hand Position Actuator");
        sensors.add(lHandPositionActuatorL);
        sensors.add(lHandPositionActuatorTF);
        lHandPositionSensorTF = new JTextField();
        sensorTextFields.put("L Hand Position Sensor", lHandPositionSensorTF);
        lHandPositionSensorL = new JLabel("L Hand Position Sensor");
        sensors.add(lHandPositionSensorL);
        sensors.add(lHandPositionSensorTF);
        lHandElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Hand Electric Current", lHandElectricCurrentTF);
        lHandElectricCurrentL = new JLabel("L Hand Electric Current");
        sensors.add(lHandElectricCurrentL);
        sensors.add(lHandElectricCurrentTF);
        lHandTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Hand Temperature Value", lHandTemperatureValueTF);
        lHandTemperatureValueL = new JLabel("L Hand Temperature Value");
        sensors.add(lHandTemperatureValueL);
        sensors.add(lHandTemperatureValueTF);
        lHandHardnessTF = new JTextField();
        sensorTextFields.put("L Hand Hardness", lHandHardnessTF);
        lHandHardnessL = new JLabel("L Hand Hardness");
        sensors.add(lHandHardnessL);
        sensors.add(lHandHardnessTF);
        lHandTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Hand Temperature Status", lHandTemperatureStatusTF);
        lHandTemperatureStatusL = new JLabel("L Hand Temperature Status");
        sensors.add(lHandTemperatureStatusL);
        sensors.add(lHandTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lWristYaw = new JLabel("L Wrist Yaw: ");
        sensors.add(lWristYaw);
        sensors.add(new JSeparator());
        lWristYawPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Wrist Yaw Position Actuator", lWristYawPositionActuatorTF);
        lWristYawPositionActuatorL = new JLabel("L Wrist Yaw Position Actuator");
        sensors.add(lWristYawPositionActuatorL);
        sensors.add(lWristYawPositionActuatorTF);
        lWristYawPositionSensorTF = new JTextField();
        sensorTextFields.put("L Wrist Yaw Position Sensor", lWristYawPositionSensorTF);
        lWristYawPositionSensorL = new JLabel("L Wrist Yaw Position Sensor");
        sensors.add(lWristYawPositionSensorL);
        sensors.add(lWristYawPositionSensorTF);
        lWristYawElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Wrist Yaw Electric Current", lWristYawElectricCurrentTF);
        lWristYawElectricCurrentL = new JLabel("L Wrist Yaw Electric Current");
        sensors.add(lWristYawElectricCurrentL);
        sensors.add(lWristYawElectricCurrentTF);
        lWristYawTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Wrist Yaw Temperature Value", lWristYawTemperatureValueTF);
        lWristYawTemperatureValueL = new JLabel("L Wrist Yaw Temperature Value");
        sensors.add(lWristYawTemperatureValueL);
        sensors.add(lWristYawTemperatureValueTF);
        lWristYawHardnessTF = new JTextField();
        sensorTextFields.put("L Wrist Yaw Hardness", lWristYawHardnessTF);
        lWristYawHardnessL = new JLabel("L Wrist Yaw Hardness");
        sensors.add(lWristYawHardnessL);
        sensors.add(lWristYawHardnessTF);
        lWristYawTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Wrist Yaw Temperature Status", lWristYawTemperatureStatusTF);
        lWristYawTemperatureStatusL = new JLabel("L Wrist Yaw Temperature Status");
        sensors.add(lWristYawTemperatureStatusL);
        sensors.add(lWristYawTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rHand = new JLabel("R Hand: ");
        sensors.add(rHand);
        sensors.add(new JSeparator());
        rHandPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Hand Position Actuator", rHandPositionActuatorTF);
        rHandPositionActuatorL = new JLabel("R Hand Position Actuator");
        sensors.add(rHandPositionActuatorL);
        sensors.add(rHandPositionActuatorTF);
        rHandPositionSensorTF = new JTextField();
        sensorTextFields.put("R Hand Position Sensor", rHandPositionSensorTF);
        rHandPositionSensorL = new JLabel("R Hand Position Sensor");
        sensors.add(rHandPositionSensorL);
        sensors.add(rHandPositionSensorTF);
        rHandElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Hand Electric Current", rHandElectricCurrentTF);
        rHandElectricCurrentL = new JLabel("R Hand Electric Current");
        sensors.add(rHandElectricCurrentL);
        sensors.add(rHandElectricCurrentTF);
        rHandTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Hand Temperature Value", rHandTemperatureValueTF);
        rHandTemperatureValueL = new JLabel("R Hand Temperature Value");
        sensors.add(rHandTemperatureValueL);
        sensors.add(rHandTemperatureValueTF);
        rHandHardnessTF = new JTextField();
        sensorTextFields.put("R Hand Hardness", rHandHardnessTF);
        rHandHardnessL = new JLabel("R Hand Hardness");
        sensors.add(rHandHardnessL);
        sensors.add(rHandHardnessTF);
        rHandTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Hand Temperature Status", rHandTemperatureStatusTF);
        rHandTemperatureStatusL = new JLabel("R Hand Temperature Status");
        sensors.add(rHandTemperatureStatusL);
        sensors.add(rHandTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rWristYaw = new JLabel("R Wrist Yaw: ");
        sensors.add(rWristYaw);
        sensors.add(new JSeparator());
        rWristYawPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Wrist Yaw Position Actuator", rWristYawPositionActuatorTF);
        rWristYawPositionActuatorL = new JLabel("R Wrist Yaw Position Actuator");
        sensors.add(rWristYawPositionActuatorL);
        sensors.add(rWristYawPositionActuatorTF);
        rWristYawPositionSensorTF = new JTextField();
        sensorTextFields.put("R Wrist Yaw Position Sensor", rWristYawPositionSensorTF);
        rWristYawPositionSensorL = new JLabel("R Wrist Yaw Position Sensor");
        sensors.add(rWristYawPositionSensorL);
        sensors.add(rWristYawPositionSensorTF);
        rWristYawElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Wrist Yaw Electric Current", rWristYawElectricCurrentTF);
        rWristYawElectricCurrentL = new JLabel("R Wrist Yaw Electric Current");
        sensors.add(rWristYawElectricCurrentL);
        sensors.add(rWristYawElectricCurrentTF);
        rWristYawTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Wrist Yaw Temperature Value", rWristYawTemperatureValueTF);
        rWristYawTemperatureValueL = new JLabel("R Wrist Yaw Temperature Value");
        sensors.add(rWristYawTemperatureValueL);
        sensors.add(rWristYawTemperatureValueTF);
        rWristYawHardnessTF = new JTextField();
        sensorTextFields.put("R Wrist Yaw Hardness", rWristYawHardnessTF);
        rWristYawHardnessL = new JLabel("R Wrist Yaw Hardness");
        sensors.add(rWristYawHardnessL);
        sensors.add(rWristYawHardnessTF);
        rWristYawTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Wrist Yaw Temperature Status", rWristYawTemperatureStatusTF);
        rWristYawTemperatureStatusL = new JLabel("R Wrist Yaw Temperature Status");
        sensors.add(rWristYawTemperatureStatusL);
        sensors.add(rWristYawTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lShoulderPitch = new JLabel("L Shoulder Pitch: ");
        sensors.add(lShoulderPitch);
        sensors.add(new JSeparator());
        lShoulderPitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Shoulder Pitch Position Actuator", lShoulderPitchPositionActuatorTF);
        lShoulderPitchPositionActuatorL = new JLabel("L Shoulder Pitch Position Actuator");
        sensors.add(lShoulderPitchPositionActuatorL);
        sensors.add(lShoulderPitchPositionActuatorTF);
        lShoulderPitchPositionSensorTF = new JTextField();
        sensorTextFields.put("L Shoulder Pitch Position Sensor", lShoulderPitchPositionSensorTF);
        lShoulderPitchPositionSensorL = new JLabel("L Shoulder Pitch Position Sensor");
        sensors.add(lShoulderPitchPositionSensorL);
        sensors.add(lShoulderPitchPositionSensorTF);
        lShoulderPitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Shoulder Pitch Electric Current", lShoulderPitchElectricCurrentTF);
        lShoulderPitchElectricCurrentL = new JLabel("L Shoulder Pitch Electric Current");
        sensors.add(lShoulderPitchElectricCurrentL);
        sensors.add(lShoulderPitchElectricCurrentTF);
        lShoulderPitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Shoulder Pitch Temperature Value", lShoulderPitchTemperatureValueTF);
        lShoulderPitchTemperatureValueL = new JLabel("L Shoulder Pitch Temperature Value");
        sensors.add(lShoulderPitchTemperatureValueL);
        sensors.add(lShoulderPitchTemperatureValueTF);
        lShoulderPitchHardnessTF = new JTextField();
        sensorTextFields.put("L Shoulder Pitch Hardness", lShoulderPitchHardnessTF);
        lShoulderPitchHardnessL = new JLabel("L Shoulder Pitch Hardness");
        sensors.add(lShoulderPitchHardnessL);
        sensors.add(lShoulderPitchHardnessTF);
        lShoulderPitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Shoulder Pitch Temperature Status", lShoulderPitchTemperatureStatusTF);
        lShoulderPitchTemperatureStatusL = new JLabel("L Shoulder Pitch Temperature Status");
        sensors.add(lShoulderPitchTemperatureStatusL);
        sensors.add(lShoulderPitchTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lShoulderRoll = new JLabel("L Shoulder Roll: ");
        sensors.add(lShoulderRoll);
        sensors.add(new JSeparator());
        lShoulderRollPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Shoulder Roll Position Actuator", lShoulderRollPositionActuatorTF);
        lShoulderRollPositionActuatorL = new JLabel("L Shoulder Roll Position Actuator");
        sensors.add(lShoulderRollPositionActuatorL);
        sensors.add(lShoulderRollPositionActuatorTF);
        lShoulderRollPositionSensorTF = new JTextField();
        sensorTextFields.put("L Shoulder Roll Position Sensor", lShoulderRollPositionSensorTF);
        lShoulderRollPositionSensorL = new JLabel("L Shoulder Roll Position Sensor");
        sensors.add(lShoulderRollPositionSensorL);
        sensors.add(lShoulderRollPositionSensorTF);
        lShoulderRollElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Shoulder Roll Electric Current", lShoulderRollElectricCurrentTF);
        lShoulderRollElectricCurrentL = new JLabel("L Shoulder Roll Electric Current");
        sensors.add(lShoulderRollElectricCurrentL);
        sensors.add(lShoulderRollElectricCurrentTF);
        lShoulderRollTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Shoulder Roll Temperature Value", lShoulderRollTemperatureValueTF);
        lShoulderRollTemperatureValueL = new JLabel("L Shoulder Roll Temperature Value");
        sensors.add(lShoulderRollTemperatureValueL);
        sensors.add(lShoulderRollTemperatureValueTF);
        lShoulderRollHardnessTF = new JTextField();
        sensorTextFields.put("L Shoulder Roll Hardness", lShoulderRollHardnessTF);
        lShoulderRollHardnessL = new JLabel("L Shoulder Roll Hardness");
        sensors.add(lShoulderRollHardnessL);
        sensors.add(lShoulderRollHardnessTF);
        lShoulderRollTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Shoulder Roll Temperature Status", lShoulderRollTemperatureStatusTF);
        lShoulderRollTemperatureStatusL = new JLabel("L Shoulder Roll Temperature Status");
        sensors.add(lShoulderRollTemperatureStatusL);
        sensors.add(lShoulderRollTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rShoulderPitch = new JLabel("R Shoulder Pitch: ");
        sensors.add(rShoulderPitch);
        sensors.add(new JSeparator());
        rShoulderPitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Shoulder Pitch Position Actuator", rShoulderPitchPositionActuatorTF);
        rShoulderPitchPositionActuatorL = new JLabel("R Shoulder Pitch Position Actuator");
        sensors.add(rShoulderPitchPositionActuatorL);
        sensors.add(rShoulderPitchPositionActuatorTF);
        rShoulderPitchPositionSensorTF = new JTextField();
        sensorTextFields.put("R Shoulder Pitch Position Sensor", rShoulderPitchPositionSensorTF);
        rShoulderPitchPositionSensorL = new JLabel("R Shoulder Pitch Position Sensor");
        sensors.add(rShoulderPitchPositionSensorL);
        sensors.add(rShoulderPitchPositionSensorTF);
        rShoulderPitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Shoulder Pitch Electric Current", rShoulderPitchElectricCurrentTF);
        rShoulderPitchElectricCurrentL = new JLabel("R Shoulder Pitch Electric Current");
        sensors.add(rShoulderPitchElectricCurrentL);
        sensors.add(rShoulderPitchElectricCurrentTF);
        rShoulderPitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Shoulder Pitch Temperature Value", rShoulderPitchTemperatureValueTF);
        rShoulderPitchTemperatureValueL = new JLabel("R Shoulder Pitch Temperature Value");
        sensors.add(rShoulderPitchTemperatureValueL);
        sensors.add(rShoulderPitchTemperatureValueTF);
        rShoulderPitchHardnessTF = new JTextField();
        sensorTextFields.put("R Shoulder Pitch Hardness", rShoulderPitchHardnessTF);
        rShoulderPitchHardnessL = new JLabel("R Shoulder Pitch Hardness");
        sensors.add(rShoulderPitchHardnessL);
        sensors.add(rShoulderPitchHardnessTF);
        rShoulderPitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Shoulder Pitch Temperature Status", rShoulderPitchTemperatureStatusTF);
        rShoulderPitchTemperatureStatusL = new JLabel("R Shoulder Pitch Temperature Status");
        sensors.add(rShoulderPitchTemperatureStatusL);
        sensors.add(rShoulderPitchTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rShoulderRoll = new JLabel("R Shoulder Roll: ");
        sensors.add(rShoulderRoll);
        sensors.add(new JSeparator());
        rShoulderRollPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Shoulder Roll Position Actuator", rShoulderRollPositionActuatorTF);
        rShoulderRollPositionActuatorL = new JLabel("R Shoulder Roll Position Actuator");
        sensors.add(rShoulderRollPositionActuatorL);
        sensors.add(rShoulderRollPositionActuatorTF);
        rShoulderRollPositionSensorTF = new JTextField();
        sensorTextFields.put("R Shoulder Roll Position Sensor", rShoulderRollPositionSensorTF);
        rShoulderRollPositionSensorL = new JLabel("R Shoulder Roll Position Sensor");
        sensors.add(rShoulderRollPositionSensorL);
        sensors.add(rShoulderRollPositionSensorTF);
        rShoulderRollElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Shoulder Roll Electric Current", rShoulderRollElectricCurrentTF);
        rShoulderRollElectricCurrentL = new JLabel("R Shoulder Roll Electric Current");
        sensors.add(rShoulderRollElectricCurrentL);
        sensors.add(rShoulderRollElectricCurrentTF);
        rShoulderRollTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Shoulder Roll Temperature Value", rShoulderRollTemperatureValueTF);
        rShoulderRollTemperatureValueL = new JLabel("R Shoulder Roll Temperature Value");
        sensors.add(rShoulderRollTemperatureValueL);
        sensors.add(rShoulderRollTemperatureValueTF);
        rShoulderRollHardnessTF = new JTextField();
        sensorTextFields.put("R Shoulder Roll Hardness", rShoulderRollHardnessTF);
        rShoulderRollHardnessL = new JLabel("R Shoulder Roll Hardness");
        sensors.add(rShoulderRollHardnessL);
        sensors.add(rShoulderRollHardnessTF);
        rShoulderRollTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Shoulder Roll Temperature Status", rShoulderRollTemperatureStatusTF);
        rShoulderRollTemperatureStatusL = new JLabel("R Shoulder Roll Temperature Status");
        sensors.add(rShoulderRollTemperatureStatusL);
        sensors.add(rShoulderRollTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lHipRoll = new JLabel("L Hip Roll: ");
        sensors.add(lHipRoll);
        sensors.add(new JSeparator());
        lHipRollPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Hip Roll Position Actuator", lHipRollPositionActuatorTF);
        lHipRollPositionActuatorL = new JLabel("L Hip Roll Position Actuator");
        sensors.add(lHipRollPositionActuatorL);
        sensors.add(lHipRollPositionActuatorTF);
        lHipRollPositionSensorTF = new JTextField();
        sensorTextFields.put("L Hip Roll Position Sensor", lHipRollPositionSensorTF);
        lHipRollPositionSensorL = new JLabel("L Hip Roll Position Sensor");
        sensors.add(lHipRollPositionSensorL);
        sensors.add(lHipRollPositionSensorTF);
        lHipRollElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Hip Roll Electric Current", lHipRollElectricCurrentTF);
        lHipRollElectricCurrentL = new JLabel("L Hip Roll Electric Current");
        sensors.add(lHipRollElectricCurrentL);
        sensors.add(lHipRollElectricCurrentTF);
        lHipRollTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Hip Roll Temperature Value", lHipRollTemperatureValueTF);
        lHipRollTemperatureValueL = new JLabel("L Hip Roll Temperature Value");
        sensors.add(lHipRollTemperatureValueL);
        sensors.add(lHipRollTemperatureValueTF);
        lHipRollHardnessTF = new JTextField();
        sensorTextFields.put("L Hip Roll Hardness", lHipRollHardnessTF);
        lHipRollHardnessL = new JLabel("L Hip Roll Hardness");
        sensors.add(lHipRollHardnessL);
        sensors.add(lHipRollHardnessTF);
        lHipRollTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Hip Roll Temperature Status", lHipRollTemperatureStatusTF);
        lHipRollTemperatureStatusL = new JLabel("L Hip Roll Temperature Status");
        sensors.add(lHipRollTemperatureStatusL);
        sensors.add(lHipRollTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lHipPitch = new JLabel("L Hip Pitch: ");
        sensors.add(lHipPitch);
        sensors.add(new JSeparator());
        lHipPitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Hip Pitch Position Actuator", lHipPitchPositionActuatorTF);
        lHipPitchPositionActuatorL = new JLabel("L Hip Pitch Position Actuator");
        sensors.add(lHipPitchPositionActuatorL);
        sensors.add(lHipPitchPositionActuatorTF);
        lHipPitchPositionSensorTF = new JTextField();
        sensorTextFields.put("L Hip Pitch Position Sensor", lHipPitchPositionSensorTF);
        lHipPitchPositionSensorL = new JLabel("L Hip Pitch Position Sensor");
        sensors.add(lHipPitchPositionSensorL);
        sensors.add(lHipPitchPositionSensorTF);
        lHipPitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Hip Pitch Electric Current", lHipPitchElectricCurrentTF);
        lHipPitchElectricCurrentL = new JLabel("L Hip Pitch Electric Current");
        sensors.add(lHipPitchElectricCurrentL);
        sensors.add(lHipPitchElectricCurrentTF);
        lHipPitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Hip Pitch Temperature Value", lHipPitchTemperatureValueTF);
        lHipPitchTemperatureValueL = new JLabel("L Hip Pitch Temperature Value");
        sensors.add(lHipPitchTemperatureValueL);
        sensors.add(lHipPitchTemperatureValueTF);
        lHipPitchHardnessTF = new JTextField();
        sensorTextFields.put("L Hip Pitch Hardness", lHipPitchHardnessTF);
        lHipPitchHardnessL = new JLabel("L Hip Pitch Hardness");
        sensors.add(lHipPitchHardnessL);
        sensors.add(lHipPitchHardnessTF);
        lHipPitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Hip Pitch Temperature Status", lHipPitchTemperatureStatusTF);
        lHipPitchTemperatureStatusL = new JLabel("L Hip Pitch Temperature Status");
        sensors.add(lHipPitchTemperatureStatusL);
        sensors.add(lHipPitchTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());


        lHipYaw = new JLabel("L Hip Yaw: ");
        sensors.add(lHipYaw);
        sensors.add(new JSeparator());
        lHipYawPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Hip Yaw Position Actuator", lHipYawPositionActuatorTF);
        lHipYawPositionActuatorL = new JLabel("L Hip Yaw Position Actuator");
        sensors.add(lHipYawPositionActuatorL);
        sensors.add(lHipYawPositionActuatorTF);
        lHipYawPositionSensorTF = new JTextField();
        sensorTextFields.put("L Hip Yaw Position Sensor", lHipYawPositionSensorTF);
        lHipYawPositionSensorL = new JLabel("L Hip Yaw Position Sensor");
        sensors.add(lHipYawPositionSensorL);
        sensors.add(lHipYawPositionSensorTF);
        lHipYawElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Hip Yaw Electric Current", lHipYawElectricCurrentTF);
        lHipYawElectricCurrentL = new JLabel("L Hip Yaw Electric Current");
        sensors.add(lHipYawElectricCurrentL);
        sensors.add(lHipYawElectricCurrentTF);
        lHipYawTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Hip Yaw Temperature Value", lHipYawTemperatureValueTF);
        lHipYawTemperatureValueL = new JLabel("L Hip Yaw Temperature Value");
        sensors.add(lHipYawTemperatureValueL);
        sensors.add(lHipYawTemperatureValueTF);
        lHipYawHardnessTF = new JTextField();
        sensorTextFields.put("L Hip Yaw Hardness", lHipYawHardnessTF);
        lHipYawHardnessL = new JLabel("L Hip Yaw Hardness");
        sensors.add(lHipYawHardnessL);
        sensors.add(lHipYawHardnessTF);
        lHipYawTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Hip Yaw Temperature Status", lHipYawTemperatureStatusTF);
        lHipYawTemperatureStatusL = new JLabel("L Hip Yaw Temperature Status");
        sensors.add(lHipYawTemperatureStatusL);
        sensors.add(lHipYawTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rHipRoll = new JLabel("R Hip Roll: ");
        sensors.add(rHipRoll);
        sensors.add(new JSeparator());
        rHipRollPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Hip Roll Position Actuator", rHipRollPositionActuatorTF);
        rHipRollPositionActuatorL = new JLabel("R Hip Roll Position Actuator");
        sensors.add(rHipRollPositionActuatorL);
        sensors.add(rHipRollPositionActuatorTF);
        rHipRollPositionSensorTF = new JTextField();
        sensorTextFields.put("R Hip Roll Position Sensor", rHipRollPositionSensorTF);
        rHipRollPositionSensorL = new JLabel("R Hip Roll Position Sensor");
        sensors.add(rHipRollPositionSensorL);
        sensors.add(rHipRollPositionSensorTF);
        rHipRollElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Hip Roll Electric Current", rHipRollElectricCurrentTF);
        rHipRollElectricCurrentL = new JLabel("R Hip Roll Electric Current");
        sensors.add(rHipRollElectricCurrentL);
        sensors.add(rHipRollElectricCurrentTF);
        rHipRollTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Hip Roll Temperature Value", rHipRollTemperatureValueTF);
        rHipRollTemperatureValueL = new JLabel("R Hip Roll Temperature Value");
        sensors.add(rHipRollTemperatureValueL);
        sensors.add(rHipRollTemperatureValueTF);
        rHipRollHardnessTF = new JTextField();
        sensorTextFields.put("R Hip Roll Hardness", rHipRollHardnessTF);
        rHipRollHardnessL = new JLabel("R Hip Roll Hardness");
        sensors.add(rHipRollHardnessL);
        sensors.add(rHipRollHardnessTF);
        rHipRollTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Hip Roll Temperature Status", rHipRollTemperatureStatusTF);
        rHipRollTemperatureStatusL = new JLabel("R Hip Roll Temperature Status");
        sensors.add(rHipRollTemperatureStatusL);
        sensors.add(rHipRollTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rHipPitch = new JLabel("R Hip Pitch: ");
        sensors.add(rHipPitch);
        sensors.add(new JSeparator());
        rHipPitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Hip Pitch Position Actuator", rHipPitchPositionActuatorTF);
        rHipPitchPositionActuatorL = new JLabel("R Hip Pitch Position Actuator");
        sensors.add(rHipPitchPositionActuatorL);
        sensors.add(rHipPitchPositionActuatorTF);
        rHipPitchPositionSensorTF = new JTextField();
        sensorTextFields.put("R Hip Pitch Position Sensor", rHipPitchPositionSensorTF);
        rHipPitchPositionSensorL = new JLabel("R Hip Pitch Position Sensor");
        sensors.add(rHipPitchPositionSensorL);
        sensors.add(rHipPitchPositionSensorTF);
        rHipPitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Hip Pitch Electric Current", rHipPitchElectricCurrentTF);
        rHipPitchElectricCurrentL = new JLabel("R Hip Pitch Electric Current");
        sensors.add(rHipPitchElectricCurrentL);
        sensors.add(rHipPitchElectricCurrentTF);
        rHipPitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Hip Pitch Temperature Value", rHipPitchTemperatureValueTF);
        rHipPitchTemperatureValueL = new JLabel("R Hip Pitch Temperature Value");
        sensors.add(rHipPitchTemperatureValueL);
        sensors.add(rHipPitchTemperatureValueTF);
        rHipPitchHardnessTF = new JTextField();
        sensorTextFields.put("R Hip Pitch Hardness", rHipPitchHardnessTF);
        rHipPitchHardnessL = new JLabel("R Hip Pitch Hardness");
        sensors.add(rHipPitchHardnessL);
        sensors.add(rHipPitchHardnessTF);
        rHipPitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Hip Pitch Temperature Status", rHipPitchTemperatureStatusTF);
        rHipPitchTemperatureStatusL = new JLabel("R Hip Pitch Temperature Status");
        sensors.add(rHipPitchTemperatureStatusL);
        sensors.add(rHipPitchTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());


        rHipYaw = new JLabel("R Hip Yaw: ");
        sensors.add(rHipYaw);
        sensors.add(new JSeparator());
        rHipYawPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Hip Yaw Position Actuator", rHipYawPositionActuatorTF);
        rHipYawPositionActuatorL = new JLabel("R Hip Yaw Position Actuator");
        sensors.add(rHipYawPositionActuatorL);
        sensors.add(rHipYawPositionActuatorTF);
        rHipYawPositionSensorTF = new JTextField();
        sensorTextFields.put("R Hip Yaw Position Sensor", rHipYawPositionSensorTF);
        rHipYawPositionSensorL = new JLabel("R Hip Yaw Position Sensor");
        sensors.add(rHipYawPositionSensorL);
        sensors.add(rHipYawPositionSensorTF);
        rHipYawElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Hip Yaw Electric Current", rHipYawElectricCurrentTF);
        rHipYawElectricCurrentL = new JLabel("R Hip Yaw Electric Current");
        sensors.add(rHipYawElectricCurrentL);
        sensors.add(rHipYawElectricCurrentTF);
        rHipYawTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Hip Yaw Temperature Value", rHipYawTemperatureValueTF);
        rHipYawTemperatureValueL = new JLabel("R Hip Yaw Temperature Value");
        sensors.add(rHipYawTemperatureValueL);
        sensors.add(rHipYawTemperatureValueTF);
        rHipYawHardnessTF = new JTextField();
        sensorTextFields.put("R Hip Yaw Hardness", rHipYawHardnessTF);
        rHipYawHardnessL = new JLabel("R Hip Yaw Hardness");
        sensors.add(rHipYawHardnessL);
        sensors.add(rHipYawHardnessTF);
        rHipYawTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Hip Yaw Temperature Status", rHipYawTemperatureStatusTF);
        rHipYawTemperatureStatusL = new JLabel("R Hip Yaw Temperature Status");
        sensors.add(rHipYawTemperatureStatusL);
        sensors.add(rHipYawTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rKneePitch = new JLabel("R Knee Pitch: ");
        sensors.add(rKneePitch);
        sensors.add(new JSeparator());
        rKneePitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Knee Pitch Position Actuator", rKneePitchPositionActuatorTF);
        rKneePitchPositionActuatorL = new JLabel("R Knee Pitch Position Actuator");
        sensors.add(rKneePitchPositionActuatorL);
        sensors.add(rKneePitchPositionActuatorTF);
        rKneePitchPositionSensorTF = new JTextField();
        sensorTextFields.put("R Knee Pitch Position Sensor", rKneePitchPositionSensorTF);
        rKneePitchPositionSensorL = new JLabel("R Knee Pitch Position Sensor");
        sensors.add(rKneePitchPositionSensorL);
        sensors.add(rKneePitchPositionSensorTF);
        rKneePitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Knee Pitch Electric Current", rKneePitchElectricCurrentTF);
        rKneePitchElectricCurrentL = new JLabel("R Knee Pitch Electric Current");
        sensors.add(rKneePitchElectricCurrentL);
        sensors.add(rKneePitchElectricCurrentTF);
        rKneePitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Knee Pitch Temperature Value", rKneePitchTemperatureValueTF);
        rKneePitchTemperatureValueL = new JLabel("R Knee Pitch Temperature Value");
        sensors.add(rKneePitchTemperatureValueL);
        sensors.add(rKneePitchTemperatureValueTF);
        rKneePitchHardnessTF = new JTextField();
        sensorTextFields.put("R Knee Pitch Hardness", rKneePitchHardnessTF);
        rKneePitchHardnessL = new JLabel("R Knee Pitch Hardness");
        sensors.add(rKneePitchHardnessL);
        sensors.add(rKneePitchHardnessTF);
        rKneePitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Knee Pitch Temperature Status", rKneePitchTemperatureStatusTF);
        rKneePitchTemperatureStatusL = new JLabel("R Knee Pitch Temperature Status");
        sensors.add(rKneePitchTemperatureStatusL);
        sensors.add(rKneePitchTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lKneePitch = new JLabel("L Knee Pitch: ");
        sensors.add(lKneePitch);
        sensors.add(new JSeparator());
        lKneePitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Knee Pitch Position Actuator", lKneePitchPositionActuatorTF);
        lKneePitchPositionActuatorL = new JLabel("L Knee Pitch Position Actuator");
        sensors.add(lKneePitchPositionActuatorL);
        sensors.add(lKneePitchPositionActuatorTF);
        lKneePitchPositionSensorTF = new JTextField();
        sensorTextFields.put("L Knee Pitch Position Sensor", lKneePitchPositionSensorTF);
        lKneePitchPositionSensorL = new JLabel("L Knee Pitch Position Sensor");
        sensors.add(lKneePitchPositionSensorL);
        sensors.add(lKneePitchPositionSensorTF);
        lKneePitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Knee Pitch Electric Current", lKneePitchElectricCurrentTF);
        lKneePitchElectricCurrentL = new JLabel("L Knee Pitch Electric Current");
        sensors.add(lKneePitchElectricCurrentL);
        sensors.add(lKneePitchElectricCurrentTF);
        lKneePitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Knee Pitch Temperature Value", lKneePitchTemperatureValueTF);
        lKneePitchTemperatureValueL = new JLabel("L Knee Pitch Temperature Value");
        sensors.add(lKneePitchTemperatureValueL);
        sensors.add(lKneePitchTemperatureValueTF);
        lKneePitchHardnessTF = new JTextField();
        sensorTextFields.put("L Knee Pitch Hardness", lKneePitchHardnessTF);
        lKneePitchHardnessL = new JLabel("L Knee Pitch Hardness");
        sensors.add(lKneePitchHardnessL);
        sensors.add(lKneePitchHardnessTF);
        lKneePitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Knee Pitch Temperature Status", lKneePitchTemperatureStatusTF);
        lKneePitchTemperatureStatusL = new JLabel("L Knee Pitch Temperature Status");
        sensors.add(lKneePitchTemperatureStatusL);
        sensors.add(lKneePitchTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());


        lAnkleRoll = new JLabel("L Ankle Roll: ");
        sensors.add(lAnkleRoll);
        sensors.add(new JSeparator());
        lAnkleRollPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Ankle Roll Position Actuator", lAnkleRollPositionActuatorTF);
        lAnkleRollPositionActuatorL = new JLabel("L Ankle Roll Position Actuator");
        sensors.add(lAnkleRollPositionActuatorL);
        sensors.add(lAnkleRollPositionActuatorTF);
        lAnkleRollPositionSensorTF = new JTextField();
        sensorTextFields.put("L Ankle Roll Position Sensor", lAnkleRollPositionSensorTF);
        lAnkleRollPositionSensorL = new JLabel("L Ankle Roll Position Sensor");
        sensors.add(lAnkleRollPositionSensorL);
        sensors.add(lAnkleRollPositionSensorTF);
        lAnkleRollElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Ankle Roll Electric Current", lAnkleRollElectricCurrentTF);
        lAnkleRollElectricCurrentL = new JLabel("L Ankle Roll Electric Current");
        sensors.add(lAnkleRollElectricCurrentL);
        sensors.add(lAnkleRollElectricCurrentTF);
        lAnkleRollTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Ankle Roll Temperature Value", lAnkleRollTemperatureValueTF);
        lAnkleRollTemperatureValueL = new JLabel("L Ankle Roll Temperature Value");
        sensors.add(lAnkleRollTemperatureValueL);
        sensors.add(lAnkleRollTemperatureValueTF);
        lAnkleRollHardnessTF = new JTextField();
        sensorTextFields.put("L Ankle Roll Hardness", lAnkleRollHardnessTF);
        lAnkleRollHardnessL = new JLabel("L Ankle Roll Hardness");
        sensors.add(lAnkleRollHardnessL);
        sensors.add(lAnkleRollHardnessTF);
        lAnkleRollTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Ankle Roll Temperature Status", lAnkleRollTemperatureStatusTF);
        lAnkleRollTemperatureStatusL = new JLabel("L Ankle Roll Temperature Status");
        sensors.add(lAnkleRollTemperatureStatusL);
        sensors.add(lAnkleRollTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        lAnklePitch = new JLabel("L Ankle Pitch: ");
        sensors.add(lAnklePitch);
        sensors.add(new JSeparator());
        lAnklePitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("L Ankle Pitch Position Actuator", lAnklePitchPositionActuatorTF);
        lAnklePitchPositionActuatorL = new JLabel("L Ankle Pitch Position Actuator");
        sensors.add(lAnklePitchPositionActuatorL);
        sensors.add(lAnklePitchPositionActuatorTF);
        lAnklePitchPositionSensorTF = new JTextField();
        sensorTextFields.put("L Ankle Pitch Position Sensor", lAnklePitchPositionSensorTF);
        lAnklePitchPositionSensorL = new JLabel("L Ankle Pitch Position Sensor");
        sensors.add(lAnklePitchPositionSensorL);
        sensors.add(lAnklePitchPositionSensorTF);
        lAnklePitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("L Ankle Pitch Electric Current", lAnklePitchElectricCurrentTF);
        lAnklePitchElectricCurrentL = new JLabel("L Ankle Pitch Electric Current");
        sensors.add(lAnklePitchElectricCurrentL);
        sensors.add(lAnklePitchElectricCurrentTF);
        lAnklePitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("L Ankle Pitch Temperature Value", lAnklePitchTemperatureValueTF);
        lAnklePitchTemperatureValueL = new JLabel("L Ankle Pitch Temperature Value");
        sensors.add(lAnklePitchTemperatureValueL);
        sensors.add(lAnklePitchTemperatureValueTF);
        lAnklePitchHardnessTF = new JTextField();
        sensorTextFields.put("L Ankle Pitch Hardness", lAnklePitchHardnessTF);
        lAnklePitchHardnessL = new JLabel("L Ankle Pitch Hardness");
        sensors.add(lAnklePitchHardnessL);
        sensors.add(lAnklePitchHardnessTF);
        lAnklePitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("L Ankle Pitch Temperature Status", lAnklePitchTemperatureStatusTF);
        lAnklePitchTemperatureStatusL = new JLabel("L Ankle Pitch Temperature Status");
        sensors.add(lAnklePitchTemperatureStatusL);
        sensors.add(lAnklePitchTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rAnkleRoll = new JLabel("R Ankle Roll: ");
        sensors.add(rAnkleRoll);
        sensors.add(new JSeparator());
        rAnkleRollPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Ankle Roll Position Actuator", rAnkleRollPositionActuatorTF);
        rAnkleRollPositionActuatorL = new JLabel("R Ankle Roll Position Actuator");
        sensors.add(rAnkleRollPositionActuatorL);
        sensors.add(rAnkleRollPositionActuatorTF);
        rAnkleRollPositionSensorTF = new JTextField();
        sensorTextFields.put("R Ankle Roll Position Sensor", rAnkleRollPositionSensorTF);
        rAnkleRollPositionSensorL = new JLabel("R Ankle Roll Position Sensor");
        sensors.add(rAnkleRollPositionSensorL);
        sensors.add(rAnkleRollPositionSensorTF);
        rAnkleRollElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Ankle Roll Electric Current", rAnkleRollElectricCurrentTF);
        rAnkleRollElectricCurrentL = new JLabel("R Ankle Roll Electric Current");
        sensors.add(rAnkleRollElectricCurrentL);
        sensors.add(rAnkleRollElectricCurrentTF);
        rAnkleRollTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Ankle Roll Temperature Value", rAnkleRollTemperatureValueTF);
        rAnkleRollTemperatureValueL = new JLabel("R Ankle Roll Temperature Value");
        sensors.add(rAnkleRollTemperatureValueL);
        sensors.add(rAnkleRollTemperatureValueTF);
        rAnkleRollHardnessTF = new JTextField();
        sensorTextFields.put("R Ankle Roll Hardness", rAnkleRollHardnessTF);
        rAnkleRollHardnessL = new JLabel("R Ankle Roll Hardness");
        sensors.add(rAnkleRollHardnessL);
        sensors.add(rAnkleRollHardnessTF);
        rAnkleRollTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Ankle Roll Temperature Status", rAnkleRollTemperatureStatusTF);
        rAnkleRollTemperatureStatusL = new JLabel("R Ankle Roll Temperature Status");
        sensors.add(rAnkleRollTemperatureStatusL);
        sensors.add(rAnkleRollTemperatureStatusTF);
        sensors.add(new JSeparator());
        sensors.add(new JSeparator());

        rAnklePitch = new JLabel("R Ankle Pitch: ");
        sensors.add(rAnklePitch);
        sensors.add(new JSeparator());
        rAnklePitchPositionActuatorTF = new JTextField();
        sensorTextFields.put("R Ankle Pitch Position Actuator", rAnklePitchPositionActuatorTF);
        rAnklePitchPositionActuatorL = new JLabel("R Ankle Pitch Position Actuator");
        sensors.add(rAnklePitchPositionActuatorL);
        sensors.add(rAnklePitchPositionActuatorTF);
        rAnklePitchPositionSensorTF = new JTextField();
        sensorTextFields.put("R Ankle Pitch Position Sensor", rAnklePitchPositionSensorTF);
        rAnklePitchPositionSensorL = new JLabel("R Ankle Pitch Position Sensor");
        sensors.add(rAnklePitchPositionSensorL);
        sensors.add(rAnklePitchPositionSensorTF);
        rAnklePitchElectricCurrentTF = new JTextField();
        sensorTextFields.put("R Ankle Pitch Electric Current", rAnklePitchElectricCurrentTF);
        rAnklePitchElectricCurrentL = new JLabel("R Ankle Pitch Electric Current");
        sensors.add(rAnklePitchElectricCurrentL);
        sensors.add(rAnklePitchElectricCurrentTF);
        rAnklePitchTemperatureValueTF = new JTextField();
        sensorTextFields.put("R Ankle Pitch Temperature Value", rAnklePitchTemperatureValueTF);
        rAnklePitchTemperatureValueL = new JLabel("R Ankle Pitch Temperature Value");
        sensors.add(rAnklePitchTemperatureValueL);
        sensors.add(rAnklePitchTemperatureValueTF);
        rAnklePitchHardnessTF = new JTextField();
        sensorTextFields.put("R Ankle Pitch Hardness", rAnklePitchHardnessTF);
        rAnklePitchHardnessL = new JLabel("R Ankle Pitch Hardness");
        sensors.add(rAnklePitchHardnessL);
        sensors.add(rAnklePitchHardnessTF);
        rAnklePitchTemperatureStatusTF = new JTextField();
        sensorTextFields.put("R Ankle Pitch Temperature Status", rAnklePitchTemperatureStatusTF);
        rAnklePitchTemperatureStatusL = new JLabel("R Ankle Pitch Temperature Status");
        sensors.add(rAnklePitchTemperatureStatusL);
        sensors.add(rAnklePitchTemperatureStatusTF);
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
    public boolean setSensorValue(String sensorName, String value){
        if(sensorTextFields.containsKey(sensorName)) {
            sensorTextFields.get(sensorName).setEditable(true);
            sensorTextFields.get(sensorName).setText(value);
            sensorTextFields.get(sensorName).setEditable(false);
            return true;
        }
        else return false;
    }


    //what should actually be passed to the main GUI
    public JScrollPane getScrollPane(){ return scrollPane;}

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MotionSensors();
            }
        });
    }
}
