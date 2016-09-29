package edu.sru.thangiah.nao.demo.storytelling.robotandrobot;

import java.util.Random;

public class Dice {

	private static final Random rand = new Random();
	public static int times = 0;
	
	private Dice(){}
	
	/** Rolls the dice. Random generator to max value.
	 * @param max The maximum random number. The number of sides of the dice.
	 * @return A random number between 0 and max.
	 */
	public static int roll(int max)
	{	
		return rand.nextInt(max);	
	}
}
