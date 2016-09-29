package edu.sru.brian.tictactoegame;

import java.util.ArrayList;
import java.util.List;
/**
 * File: PlayerAIAbstract.java
 * @author Brian Atwell
 * Description: This provides a abstraction of the player. You can pass in an AIInterface.
 *
 */
public abstract class PlayerAIAbstract implements PlayerAIInterface {
	
	private int nextMove =0;
	
	private Markers myMarker=Markers.EMPTY;
	
	private Game game=new Game();
	private AIInterface ai;
	
	/**
	 * recieves the event that a turn has ended in the Game class.
	 * @param Markers marker
	 * @param int pos
	 * @return int
	 */
	public int onUserTurn(Markers marker, int pos) 
	{
		
		if(Markers.O != marker && Markers.X != marker)
		{
			return -2;
		}
		
		System.out.println("Made it to X Turn End!");
		
		//game.takeTurn(marker, pos);
		
		return 0;
	}

	/**
	 * Called to reset the AI. myMaker is the marker of the AI.
	 * Start marker is the marker that makes the first move.
	 * @param Markers myMaker
	 * @param Markers startMarker
	 */
	@Override
	public void reset(Markers myMarker, Markers startMarker) {
		// TODO Auto-generated method stub
		this.myMarker = myMarker;
		getAI().reset();
	}

	/**
	 * Called to reset the AI.
	 */
	@Override
	public void reset()
	{
		this.myMarker=RandomMarker.nextMarker();
		game.reset();
	}

	/**
	 * Gets the Game of the AI player.
	 * @return Game
	 */
	@Override
	public Game getGame() {
		// TODO Auto-generated method stub
		return game;
	}

	/**
	 * Sets the Game of the AI player.
	 */
	protected void setGame(Game game) {
		// TODO Auto-generated method stub
		this.game = game;
	}

	/**
	 * Get the player's Marker.
	 * @return Markers
	 */
	@Override
	public Markers getMarker() {
		// TODO Auto-generated method stub
		return myMarker;
	}

	/**
	 * Get the player's Marker.
	 * @return Markers
	 */
	protected void setMarker(Markers marker) {
		// TODO Auto-generated method stub
		this.myMarker = marker;
	}

	/**
	 * Get the next move.
	 * @return int
	 */
	@Override
	public int getNextMove() {
		// TODO Auto-generated method stub
		return nextMove;
	}

	/**
	 * Set the next move.
	 * @param int move
	 */
	protected void setNextMove(int move) {
		// TODO Auto-generated method stub
		this.nextMove = move;
	}
	
	/**
	 * get the AIInterface that handles calculating the next move.
	 * @return AIInterface
	 */
	public AIInterface getAI() {
		return this.ai;
	}
	
	/**
	 * Sets the AIInterface that handles calculating the next move.
	 * @param AIInterface ai
	 */
	public void setAI(AIInterface ai) {
		this.ai = ai;
	}
	

}
