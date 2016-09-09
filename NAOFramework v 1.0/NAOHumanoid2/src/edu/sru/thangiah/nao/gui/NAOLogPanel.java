package edu.sru.thangiah.nao.gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class NAOLogPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public NAOLogPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 13, 438, 51);
		add(textArea);

	}

}
