package edu.sru.thangiah.nao.sensors.infrared;

/**
 * Infrared Remote Listener common interface
 * 
 * Author Brian Atwell
 */
public interface InfraredLIRCRemoteListener {
	
	/**
	 * Called when a Infrared Remote key is received
	 */
	public void onLIRCRemoteKeyReceived(Object data);
}
