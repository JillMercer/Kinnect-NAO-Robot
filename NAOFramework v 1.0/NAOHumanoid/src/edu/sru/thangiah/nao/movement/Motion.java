package edu.sru.thangiah.nao.movement;

import com.aldebaran.qi.Session;

/** Author: Zachary Kearney
Last Edited, 11/5/2015
* @author zrk1002
*
*/

import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.Module;

public class Motion extends Module implements MotionInterface{

	private ALMotion motion;
	
	public Motion(Session session) throws Exception {
		
		super(session);		
	}

	@Override
	public void wakeUp() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rest() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStiffness(String[] names, float[] stiffnesses) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getStiffness(String[] names) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void openRightHand(boolean enabled) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openLeftHand(boolean enabled) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToward(float x, float y, float z) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveTo(float x, float y, float z) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waitForMoveStop() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
