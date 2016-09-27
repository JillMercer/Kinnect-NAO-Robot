package edu.sru.brian.tictactoegame;

import java.util.Date;
import java.util.Random;
/**
 * File: RandomMarker
 * @author Brian Atwell
 * Description: This is an implementation to generate random markers.
 *
 */
public class RandomMarker {
	
	private static Random rand;
	
	static {
		rand = new Random();
		Date curDate = new Date();
		
		rand.setSeed(curDate.getTime());
	}
	
	/**
	 * Get next Marker
	 * @return
	 */
	public static Markers nextMarker()
	{
		
		if(rand.nextBoolean())
		{
			return Markers.O;
		}
		
		return Markers.X;
	}
}
