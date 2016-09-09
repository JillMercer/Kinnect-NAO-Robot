package edu.sru.thangiah.nao.demo.old.controlgui;

import javax.swing.JPanel;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;

import edu.sru.thangiah.nao.connection.Connect;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;

public class NaoPosturePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	JButton btnGoToPosture, btnRest, btnWakeUp;
	JComboBox comboBox;
	private ALMotion motion;
	private ALRobotPosture posture;
	private Connect connect;
	String[] postures = {"Crouch",
			"LyingBack",
			"LyingBelly",
			"Sit",
			"SitRelax",
			"Stand",
			"StandInit",
			"StandZero"};
	
	public NaoPosturePanel() {
		setLayout(null);
		
		comboBox = new JComboBox(postures);
		comboBox.setBounds(10, 11, 106, 20);
		
		/**
		for(int i = 0; i<= postures.length; i++){
		comboBox.addItem(postures[i]);
		}
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
		btnGoToPosture.setBounds(146, 10, 131, 23);
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
		btnRest.setBounds(146, 47, 131, 23);
		add(btnRest);
		
		btnWakeUp = new JButton("Wake Up");
		btnWakeUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
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
		btnWakeUp.setBounds(146, 81, 131, 23);
		add(btnWakeUp);
		enableDisable(false);
	}
	
	void gotoPosture() throws Exception{
		
	motion.setStiffnesses("Body", 1.0f);
	posture.goToPosture((String)comboBox.getSelectedItem(), 1.0f);
		
	}

	public void run(Connect connect) throws Exception{
		this.connect = connect;
		motion = new ALMotion(connect.getSession());
		posture = new ALRobotPosture(connect.getSession());
		enableDisable(true);
		}
	
	public void enableDisable(boolean enabled){
		for(Component c : this.getComponents()){
			c.setEnabled(enabled);
		}
	}
	}
