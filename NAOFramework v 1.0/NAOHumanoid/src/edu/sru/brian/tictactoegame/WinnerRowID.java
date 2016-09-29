package edu.sru.brian.tictactoegame;

/**
 * File: WinnerRowID
 * @author Brian
 * Description: Stores an integer for row ID of {@link Game#rows}
 * and Markers marker for GameChecker multiple winning rows.
 *
 */
public class WinnerRowID {

	private int rowID;
	private Markers marker;
	
	public WinnerRowID(Markers marker, int rowID)
	{
		this.marker = marker;
		this.rowID = rowID;
	}
	
	/**
	 * Get Row ID {@link Game#rows} index
	 * @return int
	 */
	public int getRowID() {
		return rowID;
	}
	
	/**
	 * Set Row ID {@link Game#rows} index
	 * @param rowID
	 */
	void setRowID(int rowID) {
		this.rowID = rowID;
	}
	
	/**
	 * Get marker
	 * {@link Markers}
	 * @return Markers
	 */
	public Markers getMarker() {
		return marker;
	}
	
	/**
	 * Set marker
	 * {@link Markers}
	 * @return
	 */
	void setMarker(Markers marker) {
		this.marker = marker;
	}
}
