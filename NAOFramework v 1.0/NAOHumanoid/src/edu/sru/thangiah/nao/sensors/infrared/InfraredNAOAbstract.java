package edu.sru.thangiah.nao.sensors.infrared;

public abstract class InfraredNAOAbstract implements InfraredNAOInterface, InfraredDataInterface, InfraredRemoteControlInterface {
	
	/**
	 * This is called when one byte of data is received.
	 * This will call child event listeners.
	 */
	protected abstract void onOneByteDataReceived(Integer data);
	
	/**
	 * This is called when four bytes of data is received.
	 * This will call child event listeners.
	 */
	protected abstract void onFourByteDataReceived(Object data);
	
	/**
	 * This is called when an IP address data is received.
	 * This will call child event listeners.
	 */
	protected abstract void onIPDataReceived(String data);
	
	/**
	 * This is called when LIRC module receives a Remote key
	 */
	protected abstract void onLIRCRemoteButtonReceived(Object data);
}
