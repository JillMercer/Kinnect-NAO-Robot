package edu.sru.brian.tictactoegame;

/**
 * File: PlayerAIInterface.java
 * @author Brian Atwell
 * Description: This is an interface for the AI players of Tic Tac Toe.
 *
 */
public interface PlayerAIInterface {
	
	/**
	 * Take this player's turn.
	 */
	public void takeTurn();
	
	/**
	 * reset the player AI.
	 */
	public void reset();
	
	/**
	 * Called to reset the AI. myMaker is the marker of the AI.
	 * Start marker is the marker that makes the first move.
	 * @param Markers myMaker
	 * @param Markers startMarker
	 */
	public void reset(Markers myMarker, Markers startMarker);
	
	/**
	 * Gets the Game of the AI player.
	 * @return Game
	 */
	public Game getGame();
	
	/**
	 * Get the player's Marker.
	 * @return Markers
	 */
	public Markers getMarker();
	
	/**
	 * Get the next move.
	 * @return int
	 */
	public int getNextMove();
	
	/**
	 * get the AIInterface that handles calculating the next move.
	 * @return AIInterface
	 */
	public AIInterface getAI();
	
	/**
	 * Sets the AIInterface that handles calculating the next move.
	 * @param AIInterface ai
	 */
	public void setAI(AIInterface ai);
	
	/*
	public int getOpponentMove();
	public void setOpponentMove(int move);
	*/
}