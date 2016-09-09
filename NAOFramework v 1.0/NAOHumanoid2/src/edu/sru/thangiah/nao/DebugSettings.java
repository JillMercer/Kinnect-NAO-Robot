package edu.sru.thangiah.nao;

public class DebugSettings {
	public static java.io.PrintStream debugStream = System.out;

	private DebugSettings(){} // Singleton.
	
	// debug settings
	public static int debugLevel = 3;
	public static final int OFF = 0;
	public static final int ERROR = 1;
	public static final int WARNING = 2;
	public static final int COMMENT = 3;
	private static final String[] dbg = { "OFF", "ERROR", "WARNING", "COMMENT" };

	public static void printDebug(int level, String msg) {
		if (level <= debugLevel) {
			debugStream.println(dbg[level] + " \"" + msg + "\" " + new java.sql.Timestamp(new java.util.Date().getTime()));
		}
	}
}
