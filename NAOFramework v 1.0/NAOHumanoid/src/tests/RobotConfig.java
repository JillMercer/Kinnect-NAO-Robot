package tests;

public class RobotConfig {
	
	public static String IP = "192.168.0.101";
	public static String PORT = "9559";
	
	public static String getConnectionString(){
		return "tcp://" + IP + ":" + PORT;
	}

}
