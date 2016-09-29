/**
 * Author: Brian Atwell
 * Date Modified: 10-22-2015
 * 
 * SonarNAOTester test the SonarNAO framework gets the sonar data.
 */

package edu.sru.thangiah.nao.sensors.sonar;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.FlowLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.border.LineBorder;

import com.aldebaran.demo.RobotIP;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;

import edu.sru.brian.gui.CommonServices;
import edu.sru.brian.gui.IPDialog;
import edu.sru.brian.nao.UnitsConversion;
import edu.sru.thangiah.nao.connection.Connection;
import edu.sru.thangiah.nao.system.NAOVersion;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.instrument.IllegalClassFormatException;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JTextArea;

public class SonarNAOTester {

	private JFrame frmSonarNaoTester;
	private JTextField[] txtLeftValueAry = new JTextField[10];
	private JTextField[] txtRightValueAry = new JTextField[10];
	private JComboBox unitsCmboBox;
	
	private static final String[] UNIT_TYPES = {"Metric (meters)", "Imperial (feet)"};
	private String lastUnitsCmboBox = UNIT_TYPES[0];
	
	private SonarNAO sonar;
	private SonarNAODetectionSettings detectSettings;
	
	private static boolean isDebugGUIOnly =false;
	private JTextField txtMinDist;
	private JTextField txtMaxDist;
	private JTextField txtBodyVersion;
	private JTextField txtRecMinDist;
	private JTextField txtRecMaxDist;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SonarNAOTester window = new SonarNAOTester();
					window.frmSonarNaoTester.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SonarNAOTester() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmSonarNaoTester = new JFrame();
		
		frmSonarNaoTester.setTitle("Sonar NAO tester");
		frmSonarNaoTester.setBounds(100, 100, 490, 570);
		frmSonarNaoTester.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSonarNaoTester.getContentPane().setLayout(new BoxLayout(frmSonarNaoTester.getContentPane(), BoxLayout.Y_AXIS));
		frmSonarNaoTester.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				InitializeNAO();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		JPanel panel_4 = new JPanel();
		frmSonarNaoTester.getContentPane().add(panel_4);
		
		JLabel lblUnits = new JLabel("Units:");
		panel_4.add(lblUnits);
		
		unitsCmboBox = new JComboBox();
		unitsCmboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onUnitsComboChanged();
			}
		});
		unitsCmboBox.setModel(new DefaultComboBoxModel(UNIT_TYPES));
		panel_4.add(unitsCmboBox);
		
		JPanel panel_6 = new JPanel();
		frmSonarNaoTester.getContentPane().add(panel_6);
		
		JLabel lblNaoBodyVersion = new JLabel("NAO Body Version:");
		panel_6.add(lblNaoBodyVersion);
		
		txtBodyVersion = new JTextField();
		txtBodyVersion.setEditable(false);
		panel_6.add(txtBodyVersion);
		txtBodyVersion.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		frmSonarNaoTester.getContentPane().add(panel_5);
		
		JLabel lblMinDist = new JLabel("Min Distance: ");
		panel_5.add(lblMinDist);
		
		txtMinDist = new JTextField();
		txtMinDist.setEditable(false);
		panel_5.add(txtMinDist);
		txtMinDist.setColumns(5);
		
		JLabel lblMaxDist = new JLabel("Max Distance:");
		panel_5.add(lblMaxDist);
		
		txtMaxDist = new JTextField();
		txtMaxDist.setEditable(false);
		panel_5.add(txtMaxDist);
		txtMaxDist.setColumns(5);
		
		JPanel panel_8 = new JPanel();
		frmSonarNaoTester.getContentPane().add(panel_8);
		
		JLabel lblMinimumRecommendedDistance = new JLabel("Min Recommended Distance:");
		panel_8.add(lblMinimumRecommendedDistance);
		
		txtRecMinDist = new JTextField();
		txtRecMinDist.setEditable(false);
		panel_8.add(txtRecMinDist);
		txtRecMinDist.setColumns(5);
		
		JLabel lblMaximumRecommendedDistance = new JLabel("Max Recommneded Distance:");
		panel_8.add(lblMaximumRecommendedDistance);
		
		txtRecMaxDist = new JTextField();
		txtRecMaxDist.setEditable(false);
		panel_8.add(txtRecMaxDist);
		txtRecMaxDist.setColumns(5);
		
		JPanel panel_7 = new JPanel();
		frmSonarNaoTester.getContentPane().add(panel_7);
		
		JButton btnShowDistanceInfo = new JButton("Distance Info");
		btnShowDistanceInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmSonarNaoTester, "<html><body><p style='width: 200px;'>"+
			"A distance less than the minimum simply tells you that an object is there. Any that is " + 
			"outside the recommended is an estimation." +
			"</p></body></html>", "Sonar Distance Info", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel_7.add(btnShowDistanceInfo);
		
		JPanel panel = new JPanel();
		frmSonarNaoTester.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JLabel lblLeftSonarSensor = new JLabel("Left Sonar Sensor");
		lblLeftSonarSensor.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblLeftSonarSensor);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JLabel lblRightSonarSensor = new JLabel("Right Sonar Sensor");
		panel_2.add(lblRightSonarSensor);
		
		JPanel panel_3 = new JPanel();
		frmSonarNaoTester.getContentPane().add(panel_3);
		
		JButton btnSendWave = new JButton("Send Wave");
		btnSendWave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sonar.sendWave();
			}
		});
		panel_3.add(btnSendWave);
		
		JButton btnSendWavesPeriodic = new JButton("Send Waves Peroidic");
		btnSendWavesPeriodic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sonar.sendWavesPeriodically();
			}
		});
		panel_3.add(btnSendWavesPeriodic);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sonar.stopWaves();
			}
		});
		panel_3.add(btnStop);
		
		JButton btnGetValues = new JButton("Get Values");
		btnGetValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSonar(SonarNAOLocation.LEFT.intValue());
				updateSonar(SonarNAOLocation.RIGHT.intValue());
			}
		});
		panel_3.add(btnGetValues);
		
		JPanel panelLp;
		JLabel labelLp;
		
		for(int i=0; i < 10; i++) {
			panelLp = new JPanel();
			panel_1.add(panelLp);
			panelLp.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			labelLp = new JLabel("Value "+(i+1));
			panelLp.add(labelLp);
			
			txtLeftValueAry[i] = new JTextField();
			panelLp.add(txtLeftValueAry[i]);
			txtLeftValueAry[i].setColumns(10);
			txtLeftValueAry[i].setEditable(false);
			txtLeftValueAry[i].setText("0");
		}
		
		for(int i=0; i < 10; i++) {
			panelLp = new JPanel();
			panel_2.add(panelLp);
			panelLp.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			labelLp = new JLabel("Value "+(i+1));
			panelLp.add(labelLp);
			
			txtRightValueAry[i] = new JTextField();
			panelLp.add(txtRightValueAry[i]);
			txtRightValueAry[i].setColumns(10);
			txtRightValueAry[i].setEditable(false);
			txtRightValueAry[i].setText("0");
		}
		
		//txtBodyVersion.setText("Assumed V5");
		//txtMaxDist.setText(""+SonarNAODetectionConstants.NAOV5MAX);
		//txtMinDist.setText(""+SonarNAODetectionConstants.NAOV5MIN);
		
		/*
		sonar.addSonarPeriodicListener(new SonarPeriodicListener() {

			@Override
			public void sonarChanged(int loc, Float[] echoes) {
				if(SonarNAOLocation.LEFT.intValue() == loc) {
					for(int i=0; i < 10; i++) {
						if(echoes.length <= i) {
							txtLeftValueAry[i].setText("0");
						}
						else {
							txtLeftValueAry[i].setText(""+echoes[i]);
						}
					}
				}
				else if(SonarNAOLocation.RIGHT.intValue() == loc) {
					for(int i=0; i < 10; i++) {
						if(echoes.length <= i) {
							txtRightValueAry[i].setText("0");
						}
						else {
							txtRightValueAry[i].setText(""+echoes[i]);
						}
					}
				}
				
			}
			
		});
		*/
		
		
		//sonar.sendWavesPeriodically();
	}
	
	private void InitializeNAO()
	{
		if(!isDebugGUIOnly)
		{
			String ipStr = "";
			SonarNAOSettings settings;
			Connection connection;
			IPDialog ipDialog;
			
			String[] services= {CommonServices.NAOQI}; 
			ipDialog = IPDialog.SetupJMDNSIPDialog(services, frmSonarNaoTester, false, 9559, "");
			
			connection = new Connection();
			
			ipDialog.setVisible(true);
			///ipStr = "192.168.0.108";
			
			if(ipDialog.getSelectedHostData().size() > 0)
			{
				ipStr = ipDialog.getSelectedHostData().get(0).getFirstIPv4Address();

			}
			
			if(ipStr == null || ipStr.isEmpty())
	    	{
	    		
	    		ipStr = RobotIP.ip;
	    	}
			
			connection.connectToNao(ipStr);
			
			if(connection.isConnected())
			{
				try {
					sonar = new SonarNAO(connection.getSession());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				settings = new SonarNAOSettings();
				settings.setReceivers(SonarNAODeviceID.BOTH);
				settings.setTransmitters(SonarNAODeviceID.BOTH);
				sonar.setSonarWaveSettings(settings);
				
				sonar.enable();
				
				sonar.addSonarDetectionListener(new SonarDetectionListener() {
		
					@Override
					public synchronized void sonarDetected(int loc, float distance) {
						// TODO Auto-generated method stub
						updateSonar(loc);
					}
		
					@Override
					public synchronized void sonarNothingDetected(int loc, float distance) {
						// TODO Auto-generated method stub
						if(SonarNAOLocation.LEFT.intValue() == loc) {
							for(int i=0; i < 10; i++) {
									txtLeftValueAry[i].setText("0");
		
							}
						}
						else if(SonarNAOLocation.RIGHT.intValue() == loc) {
							for(int i=0; i < 10; i++) {
									txtRightValueAry[i].setText("0");
							}
						}
					}
		
					
				});
				
				frmSonarNaoTester.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						Uninitialize();
					}
				});
				
				detectSettings = new SonarNAODetectionSettings(connection.getSession());
				
				updateSonarDistanceLimits();
				
				txtBodyVersion.setText(NAOVersion.getNAOBodyVersion(connection.getSession()));
				
				/*
				frmSonarNaoTester.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						System.out.println("Window Resized Width:"+e.getComponent().getWidth()+" Height:"+e.getComponent().getHeight());
					}
				});
				*/
			}//End Connected successfully
			else // Connection Failed
			{
				JOptionPane.showMessageDialog (null, "Could not connect to Robot!!\r\nAt "+RobotIP.ip, "WalkTest", JOptionPane.WARNING_MESSAGE);
			}
		}//End if DebugOnly
	}
	
	private void Uninitialize() {
		sonar.disable();
	}
	
	private void updateSonar(int loc)
	{
		if(!isDebugGUIOnly)
		{
			Float[] echoes;
			
			echoes = new Float[10];
			
			try {
				echoes = sonar.getSonarEchoes(loc);
			} catch (IllegalClassFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(SonarNAOLocation.LEFT.intValue() == loc) {
				for(int i=0; i < 10; i++) {
					if(echoes.length <= i) {
						txtLeftValueAry[i].setText("0");
					}
					else {
						if(lastUnitsCmboBox.equals(UNIT_TYPES[0]))
						{
							txtLeftValueAry[i].setText(""+echoes[i]);
						}
						else if(lastUnitsCmboBox.equals(UNIT_TYPES[1]))
						{
							txtLeftValueAry[i].setText(""+echoes[i]*UnitsConversion.METERS_TO_FEET);
						}
					}
				}
			}
			else if(SonarNAOLocation.RIGHT.intValue() == loc) {
				for(int i=0; i < 10; i++) {
					if(echoes.length <= i) {
						txtRightValueAry[i].setText("0");
					}
					else {
						if(lastUnitsCmboBox.equals(UNIT_TYPES[0]))
						{
							txtRightValueAry[i].setText(""+echoes[i]);
						}
						else if(lastUnitsCmboBox.equals(UNIT_TYPES[1]))
						{
							txtRightValueAry[i].setText(""+echoes[i]*UnitsConversion.METERS_TO_FEET);
						}
					}
				}
			}
		}
	}
	
	private void updateSonarDistanceLimits()
	{
		if(lastUnitsCmboBox.equals(UNIT_TYPES[0]))
		{
			// Since left and right sonars are the same, on the NAO, we can use either one
			// Lets use the left one.
			txtMinDist.setText(""+detectSettings.getMinimumDistance(SonarNAOLocation.LEFT.intValue()));
			txtMaxDist.setText(""+detectSettings.getMaximumDistance(SonarNAOLocation.LEFT.intValue()));
			txtRecMinDist.setText(""+detectSettings.getRecommendedMinimumDistance(SonarNAOLocation.LEFT.intValue()));
			txtRecMaxDist.setText(""+detectSettings.getRecommendedMaximumDistance(SonarNAOLocation.LEFT.intValue()));
		}
		else if(lastUnitsCmboBox.equals(UNIT_TYPES[1]))
		{
			// Since left and right sonars are the same, on the NAO, we can use either one
			// Lets use the left one.
			txtMinDist.setText(""+detectSettings.getMinimumDistance(SonarNAOLocation.LEFT.intValue())*UnitsConversion.METERS_TO_FEET);
			txtMaxDist.setText(""+detectSettings.getMaximumDistance(SonarNAOLocation.LEFT.intValue())*UnitsConversion.METERS_TO_FEET);
			txtRecMinDist.setText(""+detectSettings.getRecommendedMinimumDistance(SonarNAOLocation.LEFT.intValue())*UnitsConversion.METERS_TO_FEET);
			txtRecMaxDist.setText(""+detectSettings.getRecommendedMaximumDistance(SonarNAOLocation.LEFT.intValue())*UnitsConversion.METERS_TO_FEET);
		}
	}
	
	private void onUnitsComboChanged()
	{
		//Do combobox stuff here
		String nextUnit;
		nextUnit = (String)unitsCmboBox.getSelectedItem();
		
		// If combobox actually changes value.
		if(nextUnit != lastUnitsCmboBox)
		{
			lastUnitsCmboBox = nextUnit;
			updateSonarDistanceLimits();
			updateSonar(SonarNAOLocation.LEFT.intValue());
			updateSonar(SonarNAOLocation.RIGHT.intValue());
		}
	}

}
