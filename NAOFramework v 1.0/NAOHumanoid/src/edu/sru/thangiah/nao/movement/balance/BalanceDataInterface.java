package edu.sru.thangiah.nao.movement.balance;

public interface BalanceDataInterface{
	
	public boolean getBothFootContact() throws Exception;
	public boolean getLeftFootContact() throws Exception;
	public boolean getRightFootContact() throws Exception;
	public float getRightFootWeight() throws Exception;
	public float getLeftFootWeight() throws Exception;
	public float getGyroXValue() throws Exception;
	public float getGyroYValue() throws Exception;
	public float getAccXValue() throws Exception;
	public float getAccYValue() throws Exception;
	public float getAccZValue() throws Exception;
	public float getAngleXValue() throws Exception;
	public float getAngleYValue() throws Exception;
	
}
