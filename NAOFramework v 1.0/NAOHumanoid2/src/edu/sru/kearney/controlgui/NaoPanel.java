package edu.sru.kearney.controlgui;

import java.awt.Component;

import javax.swing.JPanel;

import com.aldebaran.qi.Session;

/**
 * Abstract model for an jpanel in the nao control gui. Each NAO Panel will
 * extend off of this NaoPanel. The extended panels connect by passing the
 * current session in the connect method. Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public abstract class NaoPanel extends JPanel implements NaoPanelInterface {

	protected Session sess;

	/**
	 * Enable or disables all components for the panel
	 */

	public void enableDisable(boolean enabled) {
		for (Component c : this.getComponents()) {
			c.setEnabled(enabled);
		}
	}

	/**
	 * Passes the session to the panel, and forces user to call run method
	 */
	public void connect(Session sess) throws Exception {
		this.sess = sess;
		run();
	}

}
