package edu.sru.brian.timer;

import java.awt.event.ActionListener;

/**
 * Resetable Action Listener
 * Provides a reset method.
 * 
 * @author Brian Atwell
 *
 */
public interface ResetableActionListener extends ActionListener {
	
	/**
	 * reset Action Listener
	 */
	public void reset();

}
