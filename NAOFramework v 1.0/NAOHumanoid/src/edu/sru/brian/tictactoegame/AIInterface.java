package edu.sru.brian.tictactoegame;

/**
 * File: AIInterface.java
 * @author Brian
 * 
 * Description: Provides an interface for Tic Tac Toe AI. Classes can
 * 			implement new AI implementations.
 *
 */
public interface AIInterface {
	
	/**
	 * Called when it is the AI players turn. Pass in the Game of Tic Tac Toe,
	 * The Opponent's marker and the opponent's move as a 1 Dimensional position
	 * of the board.
	 * @param game
	 * @param oppMark
	 * @param oppMove
	 * @return
	 */
	public int calculateNextMove(Game game, Markers oppMark, int oppMove);
	
	/**
	 * Called when the game class is reset.
	 */
	public void reset();

}
