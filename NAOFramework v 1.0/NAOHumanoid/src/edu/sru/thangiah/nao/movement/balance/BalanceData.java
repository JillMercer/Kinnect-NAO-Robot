package edu.sru.thangiah.nao.movement.balance;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.Module;

public class BalanceData extends Module implements BalanceDataInterface {

	private ALMemory balanceData;
	/**
	 * Connects to the nao and enables the ALMemory module to retrieve balance data.
	 */
	
	public BalanceData(Session session) throws Exception{
		
		super(session);
		balanceData = new ALMemory(session);		
	}
	
	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void exit() throws Exception {
		// TODO Auto-generated method stub
		balanceData.exit();
	}

	/**
	 * Returns true if left foot has contact with the ground.
	 */
	
	public boolean getLeftFootContact() throws Exception{
		if((float)balanceData.getData("leftFootContact") == 1.0){
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the right foot has contact with the ground.
	 */
	
	public boolean getRightFootContact() throws Exception{
		if((float)balanceData.getData("rightFootContact") == 1.0){
			return true;
		}
		return false;
	}

	/**
	 * Returns total weight in kg displaced on the right foot. (Not very accurate, useful when comparing both feet.)
	 */
	
	public float getRightFootWeight() throws Exception{
		return (float)balanceData.getData("rightFootTotalWeight");
	}

	/**
	 * Returns total weight in kg displaced on the left foot. (Not very accurate, useful when comparing both feet.)
	 */
	
	public float getLeftFootWeight() throws Exception{
		return (float)balanceData.getData("rightFootTotalWeight");
	}

	/**
	 * Returns true if both feet are maintaining contact with the ground.
	 */
	
	public boolean getBothFootContact() throws Exception {
		if((float)balanceData.getData("footContact") == 1.0){
			return true;
		}
		return false;
	}

	/**
	 * Returns the direct rotation speed values in rad/s from the inertial sensor in the center of the body
	 * The x value is directly infront of the nao.
	 * The y value is horizontal.
	 * The z value is vertical. (Z values are not yet available for the gyroscope.)
	 */
	
	public float getGyroXValue() throws Exception {
		return (float)balanceData.getData("Device/SubDeviceList/InertialSensor/GyroscopeX/Sensor/Value");
	}


	public float getGyroYValue() throws Exception {
		return (float)balanceData.getData("Device/SubDeviceList/InertialSensor/GyroscopeY/Sensor/Value");
	}

	/**
	 * Returns the 3-axis acceleration values in M/S^2 from the inertial sensor in the center of the body.
	 * The x value is directly infront of the nao.
	 * The y value is horizontal.
	 * The z value is vertical.
	 */

	public float getAccXValue() throws Exception {
		return (float)balanceData.getData("Device/SubDeviceList/InertialSensor/AccelerometerX/Sensor/Value");
	}


	public float getAccYValue() throws Exception {
		return (float)balanceData.getData("Device/SubDeviceList/InertialSensor/AccelerometerY/Sensor/Value");
	}


	public float getAccZValue() throws Exception {
		return (float)balanceData.getData("Device/SubDeviceList/InertialSensor/AccelerometerZ/Sensor/Value");
	}

	/**
	 * The inertial board computes 3 angles of the robot body with both the gyro and accelero data.
	 * All angles are in radians, the z angle is not available yet.
	 * The x value is directly infront of the nao.
	 * The y value is horizontal.
	 * The z value is vertical.
	 */

	public float getAngleXValue() throws Exception {
		return (float)balanceData.getData("Device/SubDeviceList/InertialSensor/AngleX/Sensor/Value");
	}


	public float getAngleYValue() throws Exception {
		return (float)balanceData.getData("Device/SubDeviceList/InertialSensor/AngleY/Sensor/Value");
	}	
}
