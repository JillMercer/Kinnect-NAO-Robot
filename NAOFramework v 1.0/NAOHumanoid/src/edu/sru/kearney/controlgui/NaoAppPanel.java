package edu.sru.kearney.controlgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import edu.sru.thangiah.nao.demo.old.DemoEnum;

/**
 * Application Panel Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public class NaoAppPanel extends NaoPanel implements NaoPanelInterface {

	/**
	 * Create the panel.
	 */
	public NaoAppPanel() {
		setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(DemoEnum.values()));
		comboBox.setBounds(10, 11, 229, 20);
		add(comboBox);

		JButton btnNewButton = new JButton("Run Application");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					NaoAppDialog app = new NaoAppDialog(sess, (DemoEnum) comboBox.getSelectedItem());
					app.setModal(true);
					app.setVisible(true);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(270, 10, 109, 23);
		add(btnNewButton);

	}

	/**
	 * Method currently void. Needs future implementation with an application
	 * interface.
	 */
	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub

	}
}
