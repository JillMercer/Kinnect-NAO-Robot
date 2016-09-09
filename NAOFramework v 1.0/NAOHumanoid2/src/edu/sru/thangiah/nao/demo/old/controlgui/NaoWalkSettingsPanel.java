package edu.sru.thangiah.nao.demo.old.controlgui;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextPane;

import com.aldebaran.qi.helper.proxies.ALMotion;

public class NaoWalkSettingsPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	
	private JSlider SForward, SHori, SHeight, SRotation, SFreq, STorWX, STorWY;
	private JButton btnNewButton;
	private float maxX, maxY, maxZ, maxH, freq, torWX, torWY;
	private ALMotion motion;
	private Object[] alObj;
	private ArrayList al;
	private Object alvalue;
	private JButton btnRestoreDefault;
	private JButton btnPrintConfig;
	
	public NaoWalkSettingsPanel() {
		setLayout(null);
		al = new ArrayList();
		
		JTextPane txtpnStepForward = new JTextPane();
		txtpnStepForward.setEditable(false);
		txtpnStepForward.setBackground(SystemColor.menu);
		txtpnStepForward.setText("Step Forward");
		txtpnStepForward.setBounds(10, 11, 82, 20);
		add(txtpnStepForward);
		
		JTextPane txtpnStepBackwards = new JTextPane();
		txtpnStepBackwards.setEditable(false);
		txtpnStepBackwards.setBackground(SystemColor.menu);
		txtpnStepBackwards.setText("Step Horizontal");
		txtpnStepBackwards.setBounds(10, 42, 82, 20);
		add(txtpnStepBackwards);
		
		JTextPane txtpnRotation = new JTextPane();
		txtpnRotation.setEditable(false);
		txtpnRotation.setBackground(SystemColor.menu);
		txtpnRotation.setText("Rotation");
		txtpnRotation.setBounds(10, 73, 82, 20);
		add(txtpnRotation);
		
		JTextPane txtpnStepHeight = new JTextPane();
		txtpnStepHeight.setEditable(false);
		txtpnStepHeight.setBackground(SystemColor.menu);
		txtpnStepHeight.setText("Step Height");
		txtpnStepHeight.setBounds(10, 141, 82, 20);
		add(txtpnStepHeight);
		
		SForward = new JSlider();
		SForward.setValue(40);
		SForward.setMaximum(80);
		SForward.setMinimum(1);
		SForward.setBounds(102, 11, 147, 23);
		add(SForward);
		
		SHori = new JSlider();
		SHori.setValue(40);
		SHori.setMaximum(60);
		SHori.setMinimum(1);
		SHori.setBounds(102, 39, 147, 23);
		add(SHori);
		
		SRotation = new JSlider();
		SRotation.setValue(35);
		SRotation.setMaximum(52);
		SRotation.setMinimum(1);
		SRotation.setBounds(102, 70, 147, 23);
		add(SRotation);
		
		SHeight = new JSlider();
		SHeight.setMaximum(40);
		SHeight.setValue(20);
		SHeight.setMinimum(5);
		SHeight.setBounds(102, 138, 147, 23);
		add(SHeight);
		
		btnNewButton = new JButton("Set Config");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					convertSlider();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(55, 298, 161, 23);
		add(btnNewButton);
		
		btnRestoreDefault = new JButton("Restore Default");
		btnRestoreDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					restoreDefault();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRestoreDefault.setBounds(55, 264, 161, 23);
		add(btnRestoreDefault);
		
		btnPrintConfig = new JButton("Print Config");
		btnPrintConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getMoveConfig();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPrintConfig.setBounds(55, 332, 161, 23);
		add(btnPrintConfig);
		
		SFreq = new JSlider();
		SFreq.setSnapToTicks(true);
		SFreq.setValue(1);
		SFreq.setMaximum(1);
		SFreq.setBounds(102, 104, 147, 23);
		add(SFreq);
		
		JTextPane txtpnFrequency = new JTextPane();
		txtpnFrequency.setText("Frequency");
		txtpnFrequency.setEditable(false);
		txtpnFrequency.setBackground(SystemColor.menu);
		txtpnFrequency.setBounds(10, 107, 82, 20);
		add(txtpnFrequency);
		
		JTextPane txtpnTorsoWx = new JTextPane();
		txtpnTorsoWx.setText("Torso WX");
		txtpnTorsoWx.setEditable(false);
		txtpnTorsoWx.setBackground(SystemColor.menu);
		txtpnTorsoWx.setBounds(10, 175, 82, 20);
		add(txtpnTorsoWx);
		
		STorWX = new JSlider();
		STorWX.setValue(12);
		STorWX.setMinimum(0);
		STorWX.setMaximum(24);
		STorWX.setBounds(102, 172, 147, 23);
		add(STorWX);
		
		JTextPane txtpnTorsoWy = new JTextPane();
		txtpnTorsoWy.setText("Torso WY");
		txtpnTorsoWy.setEditable(false);
		txtpnTorsoWy.setBackground(SystemColor.menu);
		txtpnTorsoWy.setBounds(10, 209, 82, 20);
		add(txtpnTorsoWy);
		
		STorWY = new JSlider();
		STorWY.setValue(12);
		STorWY.setMinimum(0);
		STorWY.setMaximum(24);
		STorWY.setBounds(102, 206, 147, 23);
		add(STorWY);
	}
	
	public void run(ALMotion motion) throws Exception{
		this.motion = motion;
		convertSlider();
	}
	
	void convertSlider() throws Exception{
		
		maxX = SForward.getValue();
		maxX = maxX/1000f;
		maxY = SHori.getValue();
		maxY = (maxY/1000f) + .1f;
		maxZ = SRotation.getValue();
		maxZ = maxZ/100f;
		freq = SFreq.getValue();
		maxH = SHeight.getValue();
		maxH = maxH/1000f;
		torWX = STorWX.getValue();
		torWX = torWX/100f;
		torWX = torWX - 0.12f;
		torWY = STorWY.getValue();
		torWY = torWY/100f;
		torWY = torWY - 0.12f;
		
		Object[] valList = {maxX, maxY, maxZ, freq, maxH, torWX, torWY};
		al = (ArrayList)motion.getMoveConfig("Default");
		Object whatIs = al.get(0);
		System.out.println("Pause");
		for(int i = 0; i <= 6; i++){
			Object var = al.get(i);
			ArrayList newList = (ArrayList)var;
			newList.set(1, valList[i]);
			al.set(i, newList);
		}
		System.out.println(al);

	}
	
	public ArrayList getSliderVal() throws Exception{
		return al;
	}
	
	public void getMoveConfig() throws Exception{
		
		Object config = motion.getMoveConfig("Default");
		System.out.println(config);
		
	}
	void restoreDefault() throws Exception{
		
		SForward.setValue(40);
		SHori.setValue(40);
		SRotation.setValue(35);
		SHeight.setValue(20);
		STorWX.setValue(12);
		STorWY.setValue(12);
		SFreq.setValue(1);
		convertSlider();
		
	}
}
