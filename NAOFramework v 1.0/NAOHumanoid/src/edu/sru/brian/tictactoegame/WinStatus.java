package edu.sru.brian.tictactoegame;

/**
 * File: WinStatus.java
 * @author Brian
 * Description: Represents if the game is won by X, O, tie, or unfinished
 *
 */
public enum WinStatus {

	UNFINISHED, X, O, TIE;
	
	/**
	 * Is unfinished
	 * @return boolean
	 */
	public boolean isUnfinished()
	{
		return this == UNFINISHED;
	}
	
	/**
	 * Is unfinished
	 * @param status
	 * @return boolean
	 */
	public static boolean isUnfinished(WinStatus status)
	{
		return status == UNFINISHED;
	}
}
