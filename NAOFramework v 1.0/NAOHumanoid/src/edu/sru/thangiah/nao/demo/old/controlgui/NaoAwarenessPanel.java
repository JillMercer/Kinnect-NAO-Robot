package edu.sru.thangiah.nao.demo.old.controlgui;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import com.aldebaran.qi.Session;

import edu.sru.thangiah.nao.awareness.Awareness;
import edu.sru.thangiah.nao.awareness.enums.Appendage;
import edu.sru.thangiah.nao.awareness.enums.EngagementModes;
import edu.sru.thangiah.nao.awareness.enums.TrackingModes;
import edu.sru.thangiah.nao.connection.Connect;

import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NaoAwarenessPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private Connect connect;
	private Awareness awareness;
	private JRadioButton rdbtnUnengaged;
	private JRadioButton rdbtnSemiEngaged;
	private JRadioButton rdbtnFullyEngaged;
	private JRadioButton rdbtnHead;
	private JRadioButton rdbtnBodyRotation;
	private JRadioButton rdbtnWholeBody;
	private JRadioButton rdbtnMoveContextually;
	private JCheckBox chckbxTouch;
	private JCheckBox chckbxSound;
	private JCheckBox chckbxMovement;
	private JCheckBox chckbxPeople;
	private JButton btnBreath;
	private JButton btnEnableAwarenessComponents;
	private JButton btnUpdateAwareness;
	private ButtonGroup engage;
	private ButtonGroup tracking;
	private JTextPane txtpnTrackingMode;
	private JTextPane txtpnEngagementMode;
	private JTextPane txtpnStimulus;
	private boolean running;
	
	public void setState(boolean state){
		
		btnBreath.setEnabled(state);
		btnUpdateAwareness.setEnabled(state);
		rdbtnUnengaged.setEnabled(state);
		rdbtnSemiEngaged.setEnabled(state);
		rdbtnFullyEngaged.setEnabled(state);
		rdbtnHead.setEnabled(state);
		rdbtnBodyRotation.setEnabled(state);
		rdbtnWholeBody.setEnabled(state);
		rdbtnMoveContextually.setEnabled(state);
		chckbxTouch.setEnabled(state);
		chckbxSound.setEnabled(state);
		chckbxMovement.setEnabled(state);
		chckbxPeople.setEnabled(state);
		
	}
	
	public NaoAwarenessPanel() {
		
		setLayout(null);
		running = false;
		btnEnableAwarenessComponents = new JButton("Enable Awareness");
		btnEnableAwarenessComponents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					enableDisable();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		btnEnableAwarenessComponents.setBounds(10, 11, 197, 23);
		add(btnEnableAwarenessComponents);
		
		btnBreath = new JButton("Disable Breathing");
		btnBreath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					setBreath();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnBreath.setBounds(10, 45, 197, 23);
		add(btnBreath);
		
		rdbtnUnengaged = new JRadioButton("Unengaged");
		rdbtnUnengaged.setBounds(10, 101, 98, 23);
		add(rdbtnUnengaged);
		
		rdbtnFullyEngaged = new JRadioButton("Fully Engaged");
		rdbtnFullyEngaged.setBounds(10, 154, 98, 23);
		add(rdbtnFullyEngaged);
		
		rdbtnSemiEngaged = new JRadioButton("Semi Engaged");
		rdbtnSemiEngaged.setBounds(10, 127, 98, 23);
		add(rdbtnSemiEngaged);
		
		rdbtnHead = new JRadioButton("Head");
		rdbtnHead.setBounds(115, 101, 109, 23);
		add(rdbtnHead);
		
		rdbtnBodyRotation = new JRadioButton("Body Rotation");
		rdbtnBodyRotation.setBounds(115, 128, 109, 23);
		add(rdbtnBodyRotation);
		
		rdbtnWholeBody = new JRadioButton("Whole Body");
		rdbtnWholeBody.setBounds(115, 154, 109, 23);
		add(rdbtnWholeBody);
		
		rdbtnMoveContextually = new JRadioButton("Contextually");
		rdbtnMoveContextually.setBounds(115, 180, 87, 23);
		add(rdbtnMoveContextually);
		
		engage = new ButtonGroup();
		engage.add(rdbtnUnengaged);
		engage.add(rdbtnFullyEngaged);
		engage.add(rdbtnSemiEngaged);
		
		tracking = new ButtonGroup();
		tracking.add(rdbtnBodyRotation);
		tracking.add(rdbtnHead);
		tracking.add(rdbtnMoveContextually);
		tracking.add(rdbtnWholeBody);
		
		chckbxSound = new JCheckBox("Sound");
		chckbxSound.setBounds(220, 101, 61, 23);
		add(chckbxSound);
		
		chckbxMovement = new JCheckBox("Movement");
		chckbxMovement.setBounds(220, 127, 76, 23);
		add(chckbxMovement);
		
		chckbxTouch = new JCheckBox("Touch");
		chckbxTouch.setBounds(220, 154, 61, 23);
		add(chckbxTouch);
		
		chckbxPeople = new JCheckBox("People");
		chckbxPeople.setBounds(220, 180, 68, 23);
		add(chckbxPeople);
		
		txtpnTrackingMode = new JTextPane();
		txtpnTrackingMode.setBackground(SystemColor.menu);
		txtpnTrackingMode.setText("Tracking Mode");
		txtpnTrackingMode.setBounds(10, 79, 100, 20);
		add(txtpnTrackingMode);
		
		txtpnEngagementMode = new JTextPane();
		txtpnEngagementMode.setText("Engagement Mode");
		txtpnEngagementMode.setBackground(SystemColor.menu);
		txtpnEngagementMode.setBounds(115, 79, 100, 20);
		add(txtpnEngagementMode);
		
		txtpnStimulus = new JTextPane();
		txtpnStimulus.setText("Stimulus");
		txtpnStimulus.setBackground(SystemColor.menu);
		txtpnStimulus.setBounds(220, 79, 48, 20);
		add(txtpnStimulus);
		
		btnEnableAwarenessComponents.setEnabled(false);
		btnBreath.setEnabled(false);
		rdbtnUnengaged.setEnabled(false);
		rdbtnSemiEngaged.setEnabled(false);
		rdbtnFullyEngaged.setEnabled(false);
		rdbtnHead.setEnabled(false);
		rdbtnBodyRotation.setEnabled(false);
		rdbtnWholeBody.setEnabled(false);
		rdbtnMoveContextually.setEnabled(false);
		chckbxTouch.setEnabled(false);
		chckbxSound.setEnabled(false);
		chckbxMovement.setEnabled(false);
		chckbxPeople.setEnabled(false);
		txtpnStimulus.setEnabled(false);
		txtpnEngagementMode.setEnabled(false);
		txtpnTrackingMode.setEnabled(false);
		
		btnUpdateAwareness = new JButton("Update Awareness");
		btnUpdateAwareness.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					update();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUpdateAwareness.setEnabled(false);
		btnUpdateAwareness.setBounds(56, 222, 197, 23);
		add(btnUpdateAwareness);

	}
	
	public void run(Connect connection){
		
		this.connect = connection;
		btnEnableAwarenessComponents.setEnabled(true);
		
	}
	
	public void stop(){
		
		if(running){
		running = false;
		try {
			awareness.reset();
			awareness.setEngagementMode(EngagementModes.SemiEngaged);
			awareness.setTrackingMode(TrackingModes.WholeBody);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btnBreath.setText("Disable Breathing");
		rdbtnSemiEngaged.setSelected(true);
		rdbtnWholeBody.setSelected(true);
		chckbxTouch.setSelected(true);
		chckbxPeople.setSelected(true);
		chckbxSound.setSelected(true);
		chckbxMovement.setSelected(true);
		setState(false);
		btnEnableAwarenessComponents.setText("Enable Awareness");
		btnEnableAwarenessComponents.setEnabled(false);
		}
		else{
			
			setState(false);
			btnEnableAwarenessComponents.setText("Enable Awareness");
			btnEnableAwarenessComponents.setEnabled(false);
			
		}
		
	}
	
	void setBreath() throws Exception{
		if(awareness.getBreathEnabled(Appendage.Body)){
			btnBreath.setText("Enable Breathing");
			awareness.setAllBreathing(false);
		}
		else{
			btnBreath.setText("Disable Breathing");
			awareness.setAllBreathing(true);
		}
	}
	
	void enableDisable() throws Exception{
		
		if(running){
			
			running = false;
			awareness.reset();
			awareness.setEngagementMode(EngagementModes.SemiEngaged);
			awareness.setTrackingMode(TrackingModes.WholeBody);
			btnBreath.setText("Disable Breathing");
			rdbtnSemiEngaged.setSelected(true);
			rdbtnWholeBody.setSelected(true);
			chckbxTouch.setSelected(true);
			chckbxPeople.setSelected(true);
			chckbxSound.setSelected(true);
			chckbxMovement.setSelected(true);
			setState(false);
			btnEnableAwarenessComponents.setText("Enable Awareness");
			
			
			
		}
		else if(!running){
			
		awareness = new Awareness(connect.getSession());
		awareness.reset();
		awareness.setEngagementMode(EngagementModes.SemiEngaged);
		awareness.setTrackingMode(TrackingModes.WholeBody);
		awareness.setAllBreathing(true);
		rdbtnSemiEngaged.setSelected(true);
		rdbtnWholeBody.setSelected(true);
		chckbxTouch.setSelected(true);
		chckbxPeople.setSelected(true);
		chckbxSound.setSelected(true);
		chckbxMovement.setSelected(true);
		running = true;
		setState(true);
		btnEnableAwarenessComponents.setText("Disable Awareness");
		
		}
		
	}
	
	void update() throws Exception{
		/**
		private JRadioButton rdbtnUnengaged;
		private JRadioButton rdbtnSemiEngaged;
		private JRadioButton rdbtnFullyEngaged;
		private JRadioButton rdbtnHead;
		private JRadioButton rdbtnBodyRotation;
		private JRadioButton rdbtnWholeBody;
		private JRadioButton rdbtnMoveContextually;
		private JCheckBox chckbxTouch;
		private JCheckBox chckbxSound;
		private JCheckBox chckbxMovement;
		private JCheckBox chckbxPeople;
		*/
		//String mode, limb;
		EngagementModes mode;
		TrackingModes limb;
		if(rdbtnUnengaged.isSelected()){
			mode = EngagementModes.Unengaged;
		}
		else if(rdbtnSemiEngaged.isSelected()){
			mode = EngagementModes.SemiEngaged;
		}
		else if(rdbtnFullyEngaged.isSelected()){
			mode = EngagementModes.FullyEngaged;
		}
		else{
			mode = EngagementModes.SemiEngaged;
		}
		if(rdbtnHead.isSelected()){
			limb = TrackingModes.Head;
		}
		else if(rdbtnBodyRotation.isSelected()){
			limb = TrackingModes.BodyRotation;
		}
		else if(rdbtnWholeBody.isSelected()){
			limb = TrackingModes.WholeBody;
		}
		else if(rdbtnMoveContextually.isSelected()){
			limb = TrackingModes.MoveContextually;
		}
		else{
			limb = TrackingModes.WholeBody;
		}
		awareness.setEngagementMode(mode);
		awareness.setTrackingMode(limb);
		if(chckbxTouch.isSelected()){
			awareness.setTouchStimulus(true);
		}
		if(chckbxMovement.isSelected()){
			awareness.setMoveStimulus(true);
		}
		if(chckbxPeople.isSelected()){
			awareness.setPeopleStimulus(true);
		}
		if(chckbxSound.isSelected()){
			awareness.setSoundStimulus(true);
		}
	}
}
