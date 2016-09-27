package edu.sru.thangiah.nao.sensors.infrared;

/**
 * A common interface for interacting with infrared to sent data
 * and receive data
 * 
 * @author Brian Atwell
 */
public interface InfraredDataInterface {
	
	/**
	 * Adds a new callback listener for receive data that is called when
	 * a byte, 4 bytes, or ip is received.
	 * @param listener
	 */
	public void addDataReceiveListener(InfraredDataListener listener);
	
	/**
	 * Removes a new callback listener for receive data that is called when
	 * a byte, 4 bytes, or ip is received.
	 * @param listener
	 */	
	public void removeDataReceiveListener(InfraredDataListener listener);
	
	/**
	 * Send a 8 bit byte of data through infrared
	 * @param octet
	 */
	public void send8Bit(Integer octet);
	
	/**
	 * Send a 32 bit, four bytes of data through infrared as a string
	 * @param dataIR
	 */
	public void send32Bit(String dataIR);
	
	/**
	 * Send a 32 bit, four bytes of data through infrared as four separate Integers
	 * @param octet1
	 * @param octet2
	 * @param octet3
	 * @param octet4
	 */
	public void send32Bit(Integer octet1, Integer octet2, Integer octet3, Integer octet4);
	
	/**
	 * Send an IP address data through infrared as a string
	 * @param ipData
	 */
	public void sendIPAddress(String ipData);
	
	/**
	 * Get the string that was sent
	 * @return String
	 */
	public String getIPAddressSent();
	
	/**
	 * Get the 8 bit byte sent
	 * @return Integer
	 */
	public Integer get8BitSent();
	
	/**
	 * Get the first byte of the 32 bit four bytes
	 * as an Integer
	 * @return Integer
	 */
	public Integer getFirstByteOf32BitSent();
	
	/**
	 * Get the second byte of the 32 bit four bytes
	 * as an Integer
	 * @return Integer
	 */
	public Integer getSecondByteOf32BitSent();
	
	/**
	 * Get the third byte of the 32 bit four bytes
	 * as an Integer
	 * @return Integer
	 */
	public Integer getThirdByteOf32BitSent();
	
	/**
	 * Get the fourth byte of the 32 bit four bytes
	 * as an Integer
	 * @return Integer
	 */
	public Integer getFourthByteOf32iBitSent();
	
	
	/**
	 * Get the IP address received
	 * @return String
	 */
	public String getIPAddressReceived();
	
	/**
	 * Get the 8 bit byte last received
	 * @return Integer
	 */
	public Integer get8BitReceived();
	
	/**
	 * Get the first byte of the four byte (32 bit) value
	 * @return Integer
	 */
	public Integer getFirstByteOf32bitReceived();
	
	/**
	 * Get the Second byte of the four byte (32 bit) value
	 * @return Integer
	 */
	public Integer getSecondByteOf32bitReceived();
	
	/**
	 * Get the third byte of the four byte (32 bit) value
	 * @return Integer
	 */
	public Integer getThirdByteOf32bitReceived();
	
	/**
	 * Get the fourth byte of the four byte (32 bit) value
	 * @return Integer
	 */
	public Integer getFourthByteOf32bitReceived();
}
