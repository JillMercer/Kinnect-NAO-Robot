package edu.sru.thangiah.nao.sensors.sonar;


/**
 * Contains sonar wave settings for the NAO robot
 * 
 * @author Brian Atwell
 *
 */
public class SonarNAOSettings implements SonarSettingsInterface{
	
	private SonarNAODeviceID transmitterID;
	private SonarNAODeviceID receiverID;
	
	public static final SonarNAOSettings DEFAULT = new SonarNAOSettings();
	
	public SonarNAOSettings() {
		transmitterID = SonarNAODeviceID.LEFT;
		receiverID = SonarNAODeviceID.LEFT;
	}

	/**
	 * Sets which transmitters to use for sonar wave
	 * 
	 * Receiver must be set to LEFT or RIGHT before 
	 * setting the transmitter to LEFT or RIGHT
	 * 
	 * @return 0 for success
	 * -1 if transmitterID is null
	 * -3 if passed invalid value
	 * -4 if invalid transmitter and receiver values
	 * 
	 * SetTransmitters does null checking and converting
	 * SetTransmittersAbsolute does value checking
	 */
	public int setTransmitters(int transmitterID){
		
		SonarNAODeviceID temp;
		
		temp = SonarNAODeviceID.LEFT;
		
		temp = temp.getEnumFromInt(transmitterID);
		
		if(temp != null) {
			return this.setTransmittersAbsolute(temp);
		}
		
		return -1;
		
	}
	
	/**
	 * Sets which transmitters to use for sonar wave
	 * 
	 * Receiver must be set to LEFT or RIGHT before 
	 * setting the transmitter to LEFT or RIGHT
	 * 
	 * @return 0 for success
	 * -1 if transmitterID is null
	 * -3 if passed invalid value
	 * -4 if invalid transmitter and receiver values
	 * 
	 * SetTransmitters does null checking and converting
	 * SetTransmittersAbsolute does value checking
	 */
	public int setTransmitters(SonarNAODeviceID transmitterID){
		
		if(transmitterID == null) {
			return -1;
		}
		
		return this.setTransmittersAbsolute(transmitterID);
	}
	
	/**
	 * Sets which receivers to use for sonar wave
	 * 
	 * Transmitter must be set to BOTH before setting
	 * the receiver to BOTH.
	 * 
	 * @return 0 for success
	 * -1 if transmitterID is null
	 * -3 if passed invalid value
	 * -4 if invalid transmitter and receiver values
	 * 
	 * SetReceivers does null checking and converting
	 * SetReceiversAbsolute does value checking
	 */
	public int setReceivers(int receiverID){
		/*
		if(receiverID == null) {
			return -1;
		}
		*/
		SonarNAODeviceID temp;
		
		temp = SonarNAODeviceID.LEFT;
		
		temp = temp.getEnumFromInt(receiverID);
		
		if(temp != null) {
			return this.setTransmittersAbsolute(temp);
		}
		
		return -1;
	}
	
	/**
	 * Sets which receivers to use for sonar wave
	 * 
	 * Transmitter must be set to BOTH before setting
	 * the receiver to BOTH.
	 * 
	 * @return 0 for success
	 * -1 if transmitterID is null
	 * -3 if passed invalid value
	 * -4 if invalid transmitter and receiver values
	 * 
	 * SetReceivers does null checking and converting
	 * SetReceiversAbsolute does value checking
	 */
	public int setReceivers(SonarNAODeviceID receiverID){
		
		if(receiverID == null) {
			return -1;
		}
		
		return this.setReceiversAbsolute(receiverID);
	}
	
	/**
	 * Gets current transmitters being used for sonar wave
	 * 
	 * @return transmitterID
	 */
	public int getTransmitters(){
		return this.transmitterID.intValue();
	}
	
	/**
	 * Gets current receivers being used for sonar wave
	 * 
	 * @return receiverID
	 */
	public int getReceivers(){
		return this.receiverID.intValue();
	}

	/**
	 * Sets which transmitters to use for sonar wave
	 * 
	 * transmitterID must be SonarNAODeviceID
	 * 
	 * @return 0 for success
	 * -1 if transmitterID is null
	 * -2 if 
	 * -3 if passed invalid value
	 * -4 if invalid transmitter and receiver values
	 */
	/*
	@Override
	public int setTransmitters(int transmitterID) {
		// TODO Auto-generated method stub
		if(transmitterID == null) {
			return -1;
		}
		if(transmitterID.getClass() != SonarNAODeviceID.class)
		{
			return -2;
		}
		return setTransmitters((SonarNAODeviceID)transmitterID);
	}

	/**
	 * Sets which receivers to use for sonar wave
	 * 
	 *  receiverID must be SonarNAODeviceID
	 * 
	 * @return 0 for success
	 * -1 if transmitterID is null
	 * -2 if 
	 * -3 if passed invalid value
	 * -4 if invalid transmitter and receiver values
	 */
	/*
	@Override
	public int setReceivers(int receiverID) {
		// TODO Auto-generated method stub
		if(receiverID == null) {
			return -1;
		}
		if(receiverID.getClass() != SonarNAODeviceID.class)
		{
			return -1;
		}
		return setReceivers((SonarNAODeviceID)receiverID);
	}

	/**
	 * Gets data representation of settings
	 * Used to convert sonar settings to a certain data type
	 * 
	 * NAO needs an integer to be passed to the robot's API
	 */
	@Override
	public Integer getData() {
		// TODO Auto-generated method stub
		int data;
		data = 0;
		if(this.receiverID == null || this.transmitterID == null) {
			return null;
		}
		switch(this.receiverID) {
		case BOTH:
			if(this.transmitterID == SonarNAODeviceID.BOTH) {
				data = data | 4;
				return new Integer(data);
			}
			break;
		case RIGHT:
			data = data | 1;
			break;
		default:
			break;
		}
		switch(this.transmitterID) {
		case BOTH:
			data = data | 8;
			break;
		case RIGHT:
			data = data | 2;
			break;
		default:
			break;
		}
		return new Integer(data);
	}

	@Override
	public boolean importData(Integer data) {
		// TODO Auto-generated method stub
		int tempData;
		
		if(data == null)
		{
			return false;
		}
		
		tempData = data.intValue();
		
		
		// Check for other options that determine
		// if both transmitters and receivers are being used.
		if((tempData & 4) == 4)
		{
			this.receiverID = SonarNAODeviceID.BOTH;
			this.transmitterID = SonarNAODeviceID.BOTH;
			return true;
		}
		
		// Receiver check
		if((tempData & 1) == 1)
		{
			//Right receiver is being used
			this.receiverID = SonarNAODeviceID.RIGHT;
		}
		else
		{
			//Left receiver is being used
			this.receiverID = SonarNAODeviceID.LEFT;
		}
		
		// Check for other options that determine
		// if both transmitters are being used.
		if((tempData & 8) == 8)
		{
			this.transmitterID = SonarNAODeviceID.BOTH;
			return true;
		}
		
		// Transmitter check
		if((tempData & 2) == 2)
		{
			//Right transmitter is being used
			this.transmitterID = SonarNAODeviceID.RIGHT;
		}
		else
		{
			//Left transmitter is being used
			this.transmitterID = SonarNAODeviceID.LEFT;
		}
		
		return true;
	}

	/**
	 * Sets which transmitters to use for sonar wave
	 * 
	 * transmitterID must be SonarNAODeviceID
	 * 
	 * @return 0 for success
	 * -1 if transmitterID is null
	 * -2 if 
	 * -3 if passed invalid value
	 * -4 if invalid transmitter and receiver values
	 * 
	 * Provides a separation of parameter null checking and converting from
	 * parameter value checking.
	 * 
	 * SetTransmittersAbsolute does value checking
	 * SetTransmitters does null checking and converting
	 * @param transmitterID
	 * @return
	 */
	protected int setTransmittersAbsolute(SonarNAODeviceID transmitterID) {
		// TODO Auto-generated method stub
		switch(transmitterID) {
		case BOTH:
			break;
		case RIGHT:
		case LEFT:
			if(this.receiverID == null || this.receiverID == SonarNAODeviceID.BOTH) {
				// Receiver must be set to LEFT or RIGHT before
				// changing transmitter to LEFT or RIGHT.
				return -4;
			}
			break;
		default:
			return -3;
		}
		
		this.transmitterID = transmitterID;
		return 0;
	}
	
	/**
	 * Sets which receivers to use for sonar wave
	 * 
	 * Transmitter must be set to BOTH before setting
	 * the receiver to BOTH.
	 * 
	 * @return 0 for success
	 * -1 if transmitterID is null
	 * -3 if passed invalid value
	 * -4 if invalid transmitter and receiver values
	 * 
	 * Provides a separation of parameter null checking and converting from
	 * parameter value checking.
	 * 
	 * SetReceiversAbsolute does value checking
	 * SetReceivers does null checking and converting
	 * @param receiverID
	 * @return
	 */
	protected int setReceiversAbsolute(SonarNAODeviceID receiverID) {
		// TODO Auto-generated method stub
		switch(receiverID) {
		case BOTH:
			if(this.transmitterID == null || this.transmitterID != SonarNAODeviceID.BOTH) {
				//Transmitter must be set to BOTH before receiver
				return -4;
			}
			break;
		case RIGHT:
		case LEFT:
			break;
		default:
			return -3;
		}
		
		this.receiverID = receiverID;
		return 0;
	}
	
}
