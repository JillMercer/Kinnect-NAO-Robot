package edu.sru.thangiah.nao.movement.balance;

import com.aldebaran.qi.helper.proxies.ALRobotPosture;

import edu.sru.thangiah.nao.connection.Connect;


public class BalanceTest {

	public static void main(String args[]) throws Exception{
		Connect connect = new Connect();
		connect.run();
		Balance balance = new Balance(connect.getSession());
		ALRobotPosture post = new ALRobotPosture(connect.getSession());
		post.goToPosture("StandInit", .8f);
		balance.wbEnable(true);
		balance.goToBalance("LLeg", 2f);
		balance.wbEnable(false);
		balance.exit();
		connect.stop();	
	}
}
