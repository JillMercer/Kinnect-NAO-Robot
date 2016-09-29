package edu.sru.brian.tictactoegame;

/**
 * File: TurnListener.java
 * @author Brian Atwell
 * Description: Provides an interface for callbacks
 * to turn events.
 *
 */
public interface TurnListener {
	
	/**
	 * Called by Game class when O's turn is ended.
	 * @param Markers marker
	 * @param int pos
	 */
	public void onOTurnEnd(Markers marker, int pos);
	
	/**
	 * Called by Game class when X's turn is ended.
	 * @param Markers marker
	 * @param int pos
	 */
	public void onXTurnEnd(Markers marker, int pos);
	
	/**
	 * Called by Game class when turn is ended.
	 * @param Markers marker
	 * @param int pos
	 */
	public void onTurnEnd(Markers marker, int pos);
	
	/**
	 * Called by Game class when turn is started.
	 * @param Markers marker
	 * @param int pos
	 */
	public void onTurnStart(Markers marker);

}
