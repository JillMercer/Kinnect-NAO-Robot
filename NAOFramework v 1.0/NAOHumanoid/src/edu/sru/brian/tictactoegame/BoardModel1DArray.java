package edu.sru.brian.tictactoegame;

/**
 * File: BoardModel.java
 * Author: Brian Atwell
 * Date: 2/06/2016
 * Modified Date:
 * 
 * Description: This is a model for the tic tac toe board.
 * It provides getters and setters for the board.
 * It implements the board as a 1 dimensional array.
 * 
 */
public class BoardModel1DArray {
	
	private Markers markerArray[];
	private boolean isEmpty=true;
	private int numMarked;
	
	/**
	 * Default constructor
	 */
	public BoardModel1DArray() {
		markerArray = new Markers[9];
		reset();
	}
	
	/**
	 * Gets the marker at the position of pos.
	 * pos is a 1 dimensional representation of the board.
	 * pos range is 0 through 8. A sample of the board is below: 
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 * @param pos
	 * @return Markers
	 */
	public Markers getMarker1DPos(int pos)
	{
		if(pos < 0 || pos >= markerArray.length)
		{
			return Markers.OUTOFBOUNDS;
		}
		
		return markerArray[pos];
		
	}
	
	/**
	 * Sets the marker at the position of pos.
	 * pos is a 1 dimensional representation of the board.
	 * pos range is 0 through 8. A sample of the board is below: 
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 * @param pos
	 */
	public void setMarker1DPos(Markers marker, int pos)
	{
		if(pos >= 0 && pos < markerArray.length
			&& markerArray[pos] == Markers.EMPTY)
		{
			markerArray[pos] = marker;
			isEmpty=false;
			numMarked++;
		}
	}
	
	/**
	 * Gets the marker at the position (x,y).
	 * (x,y) is a 2 dimensional representation of the board.
	 * x and y each have a range of 0 to 2. A sample of the board is below: 
	 * (0,0) (1,0) (2,0)
	 * (0,1) (1,1) (2,1)
	 * (0,2) (1,2) (2,2)
	 * @param int x
	 * @param int y
	 * @return Markers
	 */
	public Markers getMarker2DPos(int x, int y)
	{
		int pos = 0;
		if(x < 0 || x >= 3 ||
				y < 0 || y >= 3)
		{
			return Markers.OUTOFBOUNDS;
		}
		
		pos = x + (3*y);
		
		return markerArray[pos];
	}
	
	/**
	 * Sets the marker at the position (x,y).
	 * (x,y) is a 2 dimensional representation of the board.
	 * x and y each have a range of 0 to 2. A sample of the board is below: 
	 * (0,0) (1,0) (2,0)
	 * (0,1) (1,1) (2,1)
	 * (0,2) (1,2) (2,2)
	 * @param int x
	 * @param int y
	 * @return
	 */
	public void setMarker2DPos(Markers marker, int x, int y)
	{
		int pos = 0;
		if(x >= 0 && x < 3 &&
				y >= 0 && y < 3 &&
				markerArray[pos] == Markers.EMPTY)
		{
			pos = x + (3*y);
			
			markerArray[pos] = marker;
			isEmpty=false;
			numMarked++;
		}
	}
	
	/**
	 * Resets the Board to a clean state
	 */
	public void reset()
	{
		for(int i=0; i < markerArray.length; i++)
		{
			markerArray[i]=Markers.EMPTY;
		}
		isEmpty=true;
		numMarked=0;
	}
	
	/**
	 * Removes the marker at the position (x,y).
	 * (x,y) is a 2 dimensional representation of the board.
	 * This is useful for MIN MAX tree generation. It allows
	 * you to clear certain positions without clearing the whole boar.
	 * x and y each have a range of 0 to 2. A sample of the board is below: 
	 * (0,0) (1,0) (2,0)
	 * (0,1) (1,1) (2,1)
	 * (0,2) (1,2) (2,2)
	 * @param int x
	 * @param int y
	 * @return
	 */
	public void removeMarker2DPos(int x, int y)
	{
		int pos = 0;
		if(x >= 0 && x < 3 &&
				y >= 0 && y < 3 &&
				markerArray[pos] != Markers.EMPTY && numMarked > 0)
		{
			pos = x + (3*y);
			markerArray[pos] = Markers.EMPTY;
			numMarked--;
			
			if(numMarked == 0)
			{
				isEmpty=true;
			}
		}
	}
	
	/**
	 * Removes the marker at the position of pos.
	 * pos is a 1 dimensional representation of the board.
	 * This is useful for MIN MAX tree generation. It allows
	 * you to clear certain positions without clearing the whole boar.
	 * pos range is 0 through 8. A sample of the board is below: 
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 * @param pos
	 */
	public void removeMarker1DPos(int pos)
	{
		if(pos >= 0 && pos < markerArray.length &&
				markerArray[pos] != Markers.EMPTY && numMarked > 0)
		{
			markerArray[pos] = Markers.EMPTY;
			numMarked--;
			
			if(numMarked == 0)
			{
				isEmpty=true;
			}
		}
	}
	
	/** Checks if the board is empty
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return isEmpty;
	}
	
	/**
	 * Get the number of markers on the board.
	 * @return int numMarkers
	 */
	public int getMarkedCount() {
		return numMarked;
	}

}
