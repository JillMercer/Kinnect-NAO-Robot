package edu.sru.thangiah.nao.connection;

/** Author: Zachary Kearney
Last Edited, 9/24/2015
* @author zrk1002
*
*/

public class RobotConfig {
		
		public static String IP = "192.168.0.107";
		public static String PORT = "9559";
		
		public static String getConnectionString(){
			return "tcp://" + IP + ":" + PORT;
		}

	}
