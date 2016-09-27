package edu.sru.brian.tictactoegame;

/**
 * File: BoardModelInterface.java
 * @author Brian Atwell
 * @date 2/06/2016
 * Modified Date:
 * 
 * Description: This is general interface for tic tac toe board model.
 * It provides setters and getters for modifying the board.
 * 
 */
public interface BoardModelInterface extends BoardModelGetInterface {
	
	/**
	 * Sets a marker at 1D position.
	 * @param pos
	 */
	public void setMarker1DPos(int pos);
	
	/**
	 * Sets a marker at 2D position.
	 * @param pos
	 */
	public void setMarker2DPos(int x, int y);
}
