package edu.sru.thangiah.nao.movement.balance;

import edu.sru.thangiah.nao.module.ModuleInterface;

/** Author: Zachary Kearney
Last Edited, 9/24/2015
* @author zrk1002
*
*/

public interface BalanceInterface extends ModuleInterface{
	
	
	public void wbEnable(boolean status) throws Exception;

	public void wbEnableBalanceConstraint(boolean status, String supportLeg) throws Exception;
	
	public boolean getBalanceState();
	
	public void setState(String stateName, String supportLeg)throws Exception;
	
	
}
