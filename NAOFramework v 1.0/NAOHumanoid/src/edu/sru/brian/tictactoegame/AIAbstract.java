package edu.sru.brian.tictactoegame;

/**
 * File: AIAbstract.java
 * @author Brian Atwell
 * 
 * Description: This provides an AI abstract handler for the Tic Tac Toe
 * Game 
 *
 */
public abstract class AIAbstract implements AIInterface {
	
	private Game game;
	private Markers myMarker;
	
	/**
	 * AIAbstract constructor, it requires child classes to 
	 * have a constructor that takes a Game object and Markers
	 * object. The Markers object specifies the AI's marker.
	 * @param Game game
	 * @param Markers myMarker
	 */
	public AIAbstract(Game game, Markers myMarker)
	{
		this.game = game;
		this.myMarker = myMarker;
	}
	
	/**
	 * AIAbstract constructor, it requires child classes to 
	 * have a constructor that takes a Markers
	 * object. The Markers object specifies the AI's marker.
	 * @param Game game
	 * @param Markers myMarker
	 */
	public AIAbstract(Markers myMarker)
	{
		this.myMarker = myMarker;
	}
	
	/**
	 * Gets the Game the AI is using.
	 * @return Game
	 */
	public Game getGame()
	{
		return game;
	}
	
	/**
	 * Sets the Game the AI uses.
	 * @param Game game
	 */
	public void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Gets the Marker of the AI
	 * 
	 * @return Markers
	 */
	public Markers getMarker() {
		return this.myMarker;
	}
	
	/**
	 * Sets the Marker of the AI
	 * @param myMarker
	 */
	public void setMarker(Markers myMarker) {
		this.myMarker = myMarker;
	}

}
