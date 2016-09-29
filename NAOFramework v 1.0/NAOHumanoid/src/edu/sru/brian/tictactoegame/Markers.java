package edu.sru.brian.tictactoegame;

/**
 * File: Markers.java
 * Author: Brian Atwell
 * Date: 2/05/2016
 * Modified Date:
 * 
 * Description: This provides constants for the board.
 * X and O are X and O marker/place holder for the board.
 * EMPTY should is the default unused parts of the board.
 * OUTOFBOUNDS simply allows you to return an error when expecting
 * a marker as a return value.
 * 
 */

public enum Markers {
	
	EMPTY, X, O, OUTOFBOUNDS;
	
	public static final String EMPTY_STR = "EMPTY";
	public static final String O_STR = "O";
	public static final String X_STR = "X";
	public static final String OUT_BOUNDS_STR="OUT OF BOUNDS";
	public static final int X_INT=0;
	public static final int O_INT=1;
	
	/**
	 * Is the Marker equal to EMPTY Markers
	 * @param Markers marker
	 * @return boolean
	 */
	public static boolean isEmpty(Markers marker)
	{
		return marker == Markers.EMPTY;
	}
	
	/**
	 * Is the Marker equal to EMPTY Markers
	 * @return boolean
	 */
	public boolean isEmpty()
	{
		return this == Markers.EMPTY;
	}
	
	/**
	 * Get the opposite or opponent marker from marker
	 * @param Makers marker
	 * @return Makers
	 */
	public static Markers getOpponent(Markers marker)
	{
		if(marker==Markers.O)
			return Markers.X;
		else if(marker==Markers.X)
			return Markers.O;
		
		
		return Markers.OUTOFBOUNDS;
	}
	
	/**
	 * If num is even return X Marker.
	 * If num is odd return O Marker.
	 * @param num
	 * @return Markers
	 */
	public static Markers markerEvenOdd(int num)
	{
		Markers marker;
		
		marker = Markers.X;
		
		if(num%2 == 0)
		{
			marker = Markers.X;
		}
		else
		{
			marker = Markers.O;
		}
		return marker;
	}
	
	/**
	 * Are the two markers equal. Return true if they are 
	 * equal, else return false.
	 * @param Markers markOne
	 * @param Markers markTwo
	 * @return boolean
	 */
	public static boolean isEqual(Markers markOne, Markers markTwo)
	{
		return markOne == markTwo;
	}
	
	/**
	 * Get the opposite or opponent marker from marker
	 * @return Makers
	 */
	public Markers getOpponent()
	{
		Markers marker = this;
		if(marker==Markers.O)
			return Markers.X;
		else if(marker==Markers.X)
			return Markers.O;
		
		
		return Markers.OUTOFBOUNDS;
	}
	
	/**
	 * Convert an int to Marker
	 * @param int markerInt
	 * @return Markers
	 */
	public static Markers getMarkerFromInt(int markerInt)
	{
		Markers marker;
		marker = Markers.X;
		
		if(markerInt == X_INT)
		{
			marker = Markers.X;
		}
		if(markerInt == O_INT)
		{
			marker = Markers.O;
		}
		
		return marker;
	}
	
	/**
	 * Convert Marker to int
	 * @return int
	 */
	public int getInt()
	{
		int markerInt;
		
		markerInt = X_INT;
		
		if(this == Markers.X)
		{
			markerInt = X_INT;
		}
		if(this == Markers.O)
		{
			markerInt = O_INT;
		}
		
		return markerInt;
	}
	
	/**
	 * Convert string to Marker
	 * @param String value
	 * @return Markers
	 */
	public static Markers getMarkerFromString(String value)
	{
		Markers marker;
		marker = Markers.X;
		if(value.equals(X_STR))
		{
			marker = Markers.X;
		}
		if(value.equals(O_STR))
		{
			marker = Markers.O;
		}
		if(value.equals(EMPTY_STR))
		{
			marker = Markers.EMPTY;
		}
		if(value.equals(OUT_BOUNDS_STR))
		{
			marker = Markers.OUTOFBOUNDS;
		}
		
		return marker;
	}
	
	public String toString()
	{
		String temp;
		temp = "";
		
		if(this == Markers.X)
		{
			temp = X_STR;
		}
		if(this == Markers.O)
		{
			temp = O_STR;
		}
		if(this == Markers.EMPTY)
		{
			temp = EMPTY_STR;
		}
		if(this == Markers.OUTOFBOUNDS)
		{
			temp = OUT_BOUNDS_STR;
		}
		
		return temp;
	}
}
