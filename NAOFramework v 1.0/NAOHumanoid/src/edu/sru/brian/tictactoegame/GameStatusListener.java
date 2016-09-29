package edu.sru.brian.tictactoegame;

/**
 * File: GameStatusListener
 * @author Brian Atwell
 * Description: Provides a callback for start,
 * end, pause and reset events.
 *
 */
public interface GameStatusListener {
	
	/**
	 * Called when the {@link Game} is started
	 */
	public void onStart();
	
	/**
	 * Called when the {@link Game} is ends and player has won.
	 * marker is the marker that won, rowNum is the index in {@link Game#rows},
	 * isTie determines if it was a tie. If it is a tie rowNum is -1.
	 * @param Markers marker
	 * @param int rowNum
	 * @param boolean isTie
	 */
	public void onEnd(Markers marker, int rowNum, boolean isTie);
	
	/**
	 * Called when the {@link Game} is paused
	 */
	public void onPause();
	
	/**
	 * Called when the {@link Game} is reset
	 * @param Marker startMarker
	 */
	public void onReset(Markers startMarker);

}
