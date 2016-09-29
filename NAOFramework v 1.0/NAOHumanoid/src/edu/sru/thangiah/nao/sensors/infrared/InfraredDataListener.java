package edu.sru.thangiah.nao.sensors.infrared;

public interface InfraredDataListener {
	
	public void onOneByteDataReceived(Integer data);
	
	public void onFourByteDataReceived(Integer data);
	
	public void onIPDataReceived(String data);

}
