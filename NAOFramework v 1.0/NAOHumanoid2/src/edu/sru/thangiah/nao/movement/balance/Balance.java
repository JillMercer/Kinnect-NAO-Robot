/** Author: Zachary Kearney
 Last Edited, 9/24/2015
 * @author zrk1002
 *
 */

package edu.sru.thangiah.nao.movement.balance;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.Module;

public class Balance extends Module implements BalanceInterface{
	
	private boolean isEnabled;
	private ALMotion balance;
	private Connect connect;
	
	/**
	 * Balance constructor sets Wholebody balance to false initially.
	 */
	
	public Balance(Session session) throws Exception{
		
		super(session);
		balance = new ALMotion(connect.getSession());
		isEnabled = false;
		
	}
	

	/**
	 * Enables whole body balance or disables depending on the boolean status.
	 */
	
	public void wbEnable(boolean status) throws Exception{
		balance.wbEnable(status);
		isEnabled = status;
	}
	
	/**
	 * Enables whole body balance with a limb constraint.
	 * Support leg can have value of Legs, LLeg, or RLeg;
	 */
	
	public void wbEnableBalanceConstraint(boolean status, String supportLeg) throws Exception{
		if(supportLeg.equals("Legs")||supportLeg.equals("LLeg")||supportLeg.equals("RLeg")){
			balance.wbEnableBalanceConstraint(status, supportLeg);
			isEnabled = status;
		}
		else{
			throw new Exception("Invalid Argument");
		}
	}
	
	/**
	 * Returns the balance state.
	 */
	
	public boolean getBalanceState(){
		return isEnabled;
	}	
	
	/**
	 * Sets footstate for specified foot.
	 * @param stateName
	 * Fixed - Foot is fixed
	 * Plane - Constrained the foot in the plane
	 * Free - Set the foot free
	 * @param supportLeg
	 * LLeg - Left Leg
	 * RLeg - Right Leg
	 * Legs - Both legs
	 */
	
	public void setState(String stateName, String supportLeg)throws Exception{
		if((stateName.equals("Fixed")||stateName.equals("Plane")||stateName.equals("Free"))&&
				supportLeg.equals("LLeg")||supportLeg.equals("RLeg")||supportLeg.equals("Legs")){
			balance.wbFootState(stateName, supportLeg);
		}
		else{
			throw new Exception("Invalid Argument");
		}
	}
	
	/**
	 * Balances on desired leg for desired duration.
	 * supportLeg - [LLeg,RLeg,Legs]
	 * @param supportLeg
	 * @param duration
	 * @throws Exception
	 */
	public void goToBalance(String supportLeg, Float duration) throws Exception{
		
		if(supportLeg.equals("Legs") || supportLeg.equals("LLeg") || supportLeg.equals("RLeg")){
			balance.wbGoToBalance(supportLeg, duration);
		}
		else throw new Exception("Invalid Argument");
		
	}
	
	/**
	 * Ends the balance application.
	 */
	
	public void exit() throws Exception{
		
		System.out.println("Ending Balance Application");
		balance.waitUntilMoveIsFinished();
		balance.exit();
		
	}



	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub
		
	}	
}
