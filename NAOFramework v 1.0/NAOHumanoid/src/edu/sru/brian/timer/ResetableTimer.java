package edu.sru.brian.timer;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

/**
 * Adds the reset method to Timer and applies it to the ResetableActionListener
 * @author Brian
 *
 */
public class ResetableTimer extends Timer {
	
	List<ResetableActionListener> resetListeners;

	public ResetableTimer(int delay, ResetableActionListener listener) {
		super(delay, listener);
		// TODO Auto-generated constructor stub
		
		resetListeners = new ArrayList<ResetableActionListener>();
		
		resetListeners.add(listener);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}
	
	/**
	 * Add ResetableActionListener
	 * @param act
	 */
	public void addResetableActionListener(ResetableActionListener act) {
		resetListeners.add(act);
	}
	
	/**
	 * Resets all ResetableActionListeners
	 */
	public void reset() {
		Iterator<ResetableActionListener> iter;
		
		iter = resetListeners.iterator();
		
		while(iter.hasNext()) 
		{
			iter.next().reset();
		}
	}
	
	/**
	 * Remove ResetableActionListener
	 * @param act
	 */
	public void removeResetableActionListener(ResetableActionListener act) {
		resetListeners.remove(act);
	}
	

}
