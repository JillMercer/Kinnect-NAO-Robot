package edu.sru.thangiah.nao.gui;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;

public class NAONavigationPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public NAONavigationPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		
		JButton btnNAO = new JButton("NAO");
		btnNAO.setBounds(10, 11, 98, 23);
		add(btnNAO);

	}

}
