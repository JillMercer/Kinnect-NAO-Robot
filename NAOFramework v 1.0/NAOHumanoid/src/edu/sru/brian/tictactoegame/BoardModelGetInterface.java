package edu.sru.brian.tictactoegame;

/**
 * File: BoardModelGetInterface.java
 * @author Brian Atwell
 * @date 2/06/2016
 * Modified Date:
 * 
 * Description: This is getter interface for tic tac toe board model.
 * It provides getters for modifying the board.
 * 
 */

public interface BoardModelGetInterface {
	
	/**
	 * Gets the marker at the 1D position given.
	 * @param pos
	 * @return
	 */
	public Markers getMarker1DPos(int pos);
	
	/**
	 * Gets the marker at the 2D position given.
	 * @param x
	 * @param y
	 * @return
	 */
	public Markers getMarker2DPos(int x, int y);
}
