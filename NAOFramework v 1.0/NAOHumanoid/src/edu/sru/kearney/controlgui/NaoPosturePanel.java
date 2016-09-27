package edu.sru.kearney.controlgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;

/**
 * Posture Panel Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public class NaoPosturePanel extends NaoPanel implements NaoPanelInterface {

	/**
	 * Create the panel.
	 */
	JButton btnGoToPosture, btnRest, btnWakeUp;
	JComboBox comboBox;
	private ALMotion motion;
	private ALRobotPosture posture;
	String[] postures = { "Crouch", "LyingBack", "LyingBelly", "Sit", "SitRelax", "Stand", "StandInit", "StandZero" };

	public NaoPosturePanel() {
		setLayout(null);

		comboBox = new JComboBox(postures);
		comboBox.setBounds(10, 11, 109, 20);

		/**
		 * for(int i = 0; i<= postures.length; i++){
		 * comboBox.addItem(postures[i]); }
		 */
		add(comboBox);

		btnGoToPosture = new JButton("Go To Posture");
		btnGoToPosture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gotoPosture();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGoToPosture.setBounds(146, 10, 109, 23);
		add(btnGoToPosture);

		btnRest = new JButton("Rest");
		btnRest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					motion.rest();
				} catch (CallError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnRest.setBounds(146, 40, 109, 23);
		add(btnRest);

		btnWakeUp = new JButton("Wake Up");
		btnWakeUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					motion.wakeUp();
				} catch (CallError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnWakeUp.setBounds(146, 70, 109, 23);
		add(btnWakeUp);
		enableDisable(false);
	}

	void gotoPosture() throws Exception {

		motion.setStiffnesses("Body", 1.0f);
		posture.goToPosture((String) comboBox.getSelectedItem(), 1.0f);

	}

	@Override
	public void run() throws Exception {
		motion = new ALMotion(sess);
		posture = new ALRobotPosture(sess);

	}
}
