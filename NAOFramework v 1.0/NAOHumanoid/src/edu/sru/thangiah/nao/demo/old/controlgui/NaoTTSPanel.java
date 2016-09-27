package edu.sru.thangiah.nao.demo.old.controlgui;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;

import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.speech.texttospeech.TextToSpeech;

import java.awt.Component;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NaoTTSPanel extends JPanel {
	private JTextField txtTextToSpeech;

	/**
	 * Create the panel.
	 */
	JComboBox comboBox;
	JSlider sPitch, dVoice, dLevel, dTime;
	JButton btnTalk;
	private Connect connect;
	private TextToSpeech tts;
	private JTextPane txtpnVolume;
	private JSlider SVolume;
	private JSlider SSpeed;
	private JTextPane txtpnSpeed;
	
	public NaoTTSPanel() {
		setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Set Language"}));
		comboBox.setBounds(129, 11, 119, 20);
		add(comboBox);
		
		JTextPane txtpnSetLanguage = new JTextPane();
		txtpnSetLanguage.setEditable(false);
		txtpnSetLanguage.setBackground(SystemColor.menu);
		txtpnSetLanguage.setText("Set Voice");
		txtpnSetLanguage.setBounds(10, 10, 110, 20);
		add(txtpnSetLanguage);
		
		JTextPane txtpnPitchShift = new JTextPane();
		txtpnPitchShift.setEditable(false);
		txtpnPitchShift.setText("Pitch Shift");
		txtpnPitchShift.setBackground(SystemColor.menu);
		txtpnPitchShift.setBounds(10, 80, 110, 20);
		add(txtpnPitchShift);
		
		JTextPane txtpnDoubleVoice = new JTextPane();
		txtpnDoubleVoice.setEditable(false);
		txtpnDoubleVoice.setText("Double Voice");
		txtpnDoubleVoice.setBackground(SystemColor.menu);
		txtpnDoubleVoice.setBounds(10, 150, 110, 20);
		add(txtpnDoubleVoice);
		
		sPitch = new JSlider();
		sPitch.setMinimum(4);

		sPitch.setValue(0);
		sPitch.setMaximum(40);
		sPitch.setBounds(129, 80, 119, 23);
		add(sPitch);
		
		dVoice = new JSlider();

		dVoice.setValue(0);
		dVoice.setMaximum(40);
		dVoice.setBounds(129, 150, 119, 23);
		add(dVoice);
		
		dLevel = new JSlider();

		dLevel.setValue(0);
		dLevel.setMaximum(40);
		dLevel.setBounds(129, 185, 119, 23);
		add(dLevel);
		
		dTime = new JSlider();

		
		dTime.setValue(0);
		dTime.setMaximum(50);
		dTime.setBounds(129, 220, 119, 23);
		add(dTime);
		
		JTextPane txtpnDoubleVolume = new JTextPane();
		txtpnDoubleVolume.setEditable(false);
		txtpnDoubleVolume.setText("Double Volume");
		txtpnDoubleVolume.setBackground(SystemColor.menu);
		txtpnDoubleVolume.setBounds(10, 185, 110, 20);
		add(txtpnDoubleVolume);
		
		JTextPane txtpnDoubleTimeShift = new JTextPane();
		txtpnDoubleTimeShift.setEditable(false);
		txtpnDoubleTimeShift.setText("Double Time Shift");
		txtpnDoubleTimeShift.setBackground(SystemColor.menu);
		txtpnDoubleTimeShift.setBounds(10, 220, 110, 20);
		add(txtpnDoubleTimeShift);
		
		txtTextToSpeech = new JTextField();
		txtTextToSpeech.setText("Text To Speech");
		txtTextToSpeech.setBounds(10, 302, 165, 20);
		add(txtTextToSpeech);
		txtTextToSpeech.setColumns(10);
		
		btnTalk = new JButton("Talk");

		btnTalk.setBounds(10, 333, 110, 23);
		add(btnTalk);
		
		txtpnVolume = new JTextPane();
		txtpnVolume.setEditable(false);
		txtpnVolume.setText("Volume");
		txtpnVolume.setBackground(SystemColor.menu);
		txtpnVolume.setBounds(10, 45, 110, 20);
		add(txtpnVolume);
		
		SVolume = new JSlider();
		SVolume.setMaximum(175);

		SVolume.setValue(100);
		SVolume.setBounds(129, 45, 119, 23);
		add(SVolume);
		
		SSpeed = new JSlider();

		SSpeed.setMinimum(5);
		SSpeed.setValue(10);
		SSpeed.setMaximum(40);
		SSpeed.setBounds(129, 115, 119, 23);
		add(SSpeed);
		
		txtpnSpeed = new JTextPane();
		txtpnSpeed.setEditable(false);
		txtpnSpeed.setText("Speed");
		txtpnSpeed.setBackground(SystemColor.menu);
		txtpnSpeed.setBounds(10, 115, 110, 20);
		add(txtpnSpeed);

		enableDisable(false);
	}
	
	public void enableDisable(boolean enabled){
		for(Component c : this.getComponents()){
			c.setEnabled(enabled);
		}
	}
	
	public void run(Connect connect) throws Exception{
		
		this.connect = connect;
		tts = new TextToSpeech(connect.getSession());
		comboBox.removeAllItems();
		ArrayList voiceList = (ArrayList)tts.getAvailableVoices();
		for(String s:(ArrayList<String>)voiceList){
			comboBox.addItem(s);
		}
		int vol = (int) (tts.getVolume()*100);
		SVolume.setValue(vol);
		listeners();
		enableDisable(true);
	}
	
	void Volume() throws Exception{
		float vol = SVolume.getValue();
		vol = vol/100f;
		tts.setVolume(vol);
	}
	
	void Pitch() throws Exception{
		float pitch = sPitch.getValue();
		pitch = pitch/10f;
		if(pitch < 0.5f){
			pitch = 0;
		}
		tts.setPitch(pitch);
	}
	
	void Voice() throws Exception{
		
		tts.setVoice((String)comboBox.getSelectedItem());
	}
	
	void DLevel() throws Exception{
		
		float level = dLevel.getValue();
		level = level/10f;
		tts.setDLevel(level);
	}
	
	void DVoice() throws Exception{
		
		float voice = dVoice.getValue();
		voice = voice/10f;
		tts.setDVoice(voice);
	}
	
	void DTime() throws Exception{
		
		float time = dTime.getValue();
		time = time/100f;
		tts.setDTime(time);
		
	}
	
	void Speed() throws Exception{
		
		float speed = SSpeed.getValue();
		speed = speed/10f;
		tts.setSpeed(speed);
		
	}
	
	void Say() throws Exception{
		
		tts.say(txtTextToSpeech.getText());
		
	}
	
	void listeners() throws Exception{
		SSpeed.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					Speed();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		SVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				try {
					Volume();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnTalk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Say();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		dTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					DTime();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		dLevel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					DLevel();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		sPitch.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					Pitch();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		dVoice.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					DVoice();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
}
