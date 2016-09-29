package edu.sru.brian.tictactoegame;

/**
 * File: TurnAdaptor.java
 * @author Brian Atwell
 * Description: Turn adaptor implementation of the turn listener.
 *
 */
public class TurnAdaptor implements TurnListener {

	/**
	 * Called by Game class when O's turn is ended.
	 * @param Markers marker
	 * @param int pos
	 */
	@Override
	public void onOTurnEnd(Markers marker, int pos) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called by Game class when X's turn is ended.
	 * @param Markers marker
	 * @param int pos
	 */
	@Override
	public void onXTurnEnd(Markers marker, int pos) {
		// TODO Auto-generated method stub

	}

	/**
	 * Called by Game class when turn is ended.
	 * @param Markers marker
	 * @param int pos
	 */
	@Override
	public void onTurnEnd(Markers marker, int pos) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Called by Game class when turn is started.
	 * @param Markers marker
	 * @param int pos
	 */
	@Override
	public void onTurnStart(Markers marker) {
		
	}

}
