package edu.sru.kearney.controlgui;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import edu.sru.thangiah.nao.awareness.Awareness;
import edu.sru.thangiah.nao.awareness.enums.Appendage;
import edu.sru.thangiah.nao.awareness.enums.EngagementModes;
import edu.sru.thangiah.nao.awareness.enums.TrackingModes;

/**
 * Awareness Panel Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public class NaoAwarenessPanel extends NaoPanel implements NaoPanelInterface {

	/**
	 * Create the panel.
	 */
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
	private ButtonGroup engage;
	private ButtonGroup tracking;
	private JTextPane txtpnTrackingMode;
	private JTextPane txtpnEngagementMode;
	private JTextPane txtpnStimulus;

	public NaoAwarenessPanel() {

		setLayout(null);

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
		btnBreath.setBounds(99, 163, 197, 23);
		add(btnBreath);

		rdbtnUnengaged = new JRadioButton("Unengaged");
		rdbtnUnengaged.addActionListener(updateListener());
		rdbtnUnengaged.setBounds(10, 40, 109, 20);
		add(rdbtnUnengaged);
		rdbtnUnengaged.setSelected(true);

		rdbtnFullyEngaged = new JRadioButton("Fully Engaged");
		rdbtnFullyEngaged.addActionListener(updateListener());
		rdbtnFullyEngaged.setBounds(10, 100, 109, 20);
		add(rdbtnFullyEngaged);

		rdbtnSemiEngaged = new JRadioButton("Semi Engaged");
		rdbtnSemiEngaged.addActionListener(updateListener());
		rdbtnSemiEngaged.setBounds(10, 70, 109, 20);
		add(rdbtnSemiEngaged);

		rdbtnHead = new JRadioButton("Head");
		rdbtnHead.addActionListener(updateListener());
		rdbtnHead.setBounds(140, 40, 109, 20);
		add(rdbtnHead);
		rdbtnHead.setSelected(true);

		rdbtnBodyRotation = new JRadioButton("Body Rotation");
		rdbtnBodyRotation.addActionListener(updateListener());
		rdbtnBodyRotation.setBounds(140, 70, 109, 20);
		add(rdbtnBodyRotation);

		rdbtnWholeBody = new JRadioButton("Whole Body");
		rdbtnWholeBody.addActionListener(updateListener());
		rdbtnWholeBody.setBounds(140, 100, 109, 20);
		add(rdbtnWholeBody);

		rdbtnMoveContextually = new JRadioButton("Contextually");
		rdbtnMoveContextually.addActionListener(updateListener());
		rdbtnMoveContextually.setBounds(140, 130, 109, 20);
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
		chckbxSound.addActionListener(updateListener());
		chckbxSound.setBounds(270, 40, 109, 20);
		add(chckbxSound);
		chckbxSound.setSelected(true);

		chckbxMovement = new JCheckBox("Movement");
		chckbxMovement.addActionListener(updateListener());
		chckbxMovement.setBounds(270, 100, 109, 20);
		add(chckbxMovement);
		chckbxMovement.setSelected(true);

		chckbxTouch = new JCheckBox("Touch");
		chckbxTouch.addActionListener(updateListener());
		chckbxTouch.setBounds(270, 70, 109, 20);
		add(chckbxTouch);
		chckbxTouch.setSelected(true);

		chckbxPeople = new JCheckBox("People");
		chckbxPeople.addActionListener(updateListener());
		chckbxPeople.setBounds(270, 130, 109, 20);
		add(chckbxPeople);
		chckbxPeople.setSelected(true);

		txtpnTrackingMode = new JTextPane();
		txtpnTrackingMode.setEditable(false);
		txtpnTrackingMode.setBackground(SystemColor.menu);
		txtpnTrackingMode.setText("Engagement Mode");
		txtpnTrackingMode.setBounds(10, 10, 109, 20);
		add(txtpnTrackingMode);

		txtpnEngagementMode = new JTextPane();
		txtpnEngagementMode.setEditable(false);
		txtpnEngagementMode.setText("Tracking Mode");
		txtpnEngagementMode.setBackground(SystemColor.menu);
		txtpnEngagementMode.setBounds(140, 10, 109, 20);
		add(txtpnEngagementMode);

		txtpnStimulus = new JTextPane();
		txtpnStimulus.setEditable(false);
		txtpnStimulus.setText("Stimulus");
		txtpnStimulus.setBackground(SystemColor.menu);
		txtpnStimulus.setBounds(270, 10, 109, 20);
		add(txtpnStimulus);

	}

	public ActionListener updateListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					update();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
	}

	void setBreath() throws Exception {
		if (awareness.getBreathEnabled(Appendage.Body)) {
			btnBreath.setText("Enable Breathing");
			awareness.setAllBreathing(false);
		} else {
			btnBreath.setText("Disable Breathing");
			awareness.setAllBreathing(true);
		}
	}

	void update() throws Exception {

		EngagementModes mode;
		TrackingModes limb;
		if (rdbtnUnengaged.isSelected()) {
			mode = EngagementModes.Unengaged;
		} else if (rdbtnSemiEngaged.isSelected()) {
			mode = EngagementModes.SemiEngaged;
		} else if (rdbtnFullyEngaged.isSelected()) {
			mode = EngagementModes.FullyEngaged;
		} else {
			mode = EngagementModes.SemiEngaged;
		}
		if (rdbtnHead.isSelected()) {
			limb = TrackingModes.Head;
		} else if (rdbtnBodyRotation.isSelected()) {
			limb = TrackingModes.BodyRotation;
		} else if (rdbtnWholeBody.isSelected()) {
			limb = TrackingModes.WholeBody;
		} else if (rdbtnMoveContextually.isSelected()) {
			limb = TrackingModes.MoveContextually;
		} else {
			limb = TrackingModes.WholeBody;
		}
		awareness.setEngagementMode(mode);
		awareness.setTrackingMode(limb);
		if (chckbxTouch.isSelected()) {
			awareness.setTouchStimulus(true);
		}
		if (chckbxMovement.isSelected()) {
			awareness.setMoveStimulus(true);
		}
		if (chckbxPeople.isSelected()) {
			awareness.setPeopleStimulus(true);
		}
		if (chckbxSound.isSelected()) {
			awareness.setSoundStimulus(true);
		}
	}

	@Override
	public void run() throws Exception {
		awareness = new Awareness(sess);
		update();

	}
}
