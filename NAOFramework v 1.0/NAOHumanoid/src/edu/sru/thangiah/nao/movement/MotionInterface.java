package edu.sru.thangiah.nao.movement;

public interface MotionInterface{
	
	public void wakeUp() throws Exception;
	
	public void rest() throws Exception;
	
	public void setStiffness(String[] names, float[] stiffnesses) throws Exception;
	
	public float getStiffness(String[] names) throws Exception;
	
	public void openRightHand(boolean enabled) throws Exception;
	
	public void openLeftHand(boolean enabled) throws Exception;
	
	public void moveToward(float x, float y, float z) throws Exception;
	
	public void moveTo(float x, float y, float z) throws Exception;
	
	public void waitForMoveStop() throws Exception;
}
