package edu.sru.thangiah.nao.sensors.sonar;

/**
 * Provides constants for the NAO sonar detection
 * 
 * @author Brian Atwell
 *
 */
public class SonarNAODetectionConstants{
	
	// Under the minimum distance in (m - meters) is simply knows it is there or not.
	
	public static final float NAOV5MIN = 0.01f;
	// NAO robot V5 distance over the maximum is an estimation
	public static final float NAOV5MAX = 5f;
	
	public static final float NAOV5RECMIN = 0.2f;
	public static final float NAOV5RECMAX = 0.8f;
	
	public static final float NAOV4MIN = 0.25f;
	public static final float NAOV4MAX = 2.55f;

}
