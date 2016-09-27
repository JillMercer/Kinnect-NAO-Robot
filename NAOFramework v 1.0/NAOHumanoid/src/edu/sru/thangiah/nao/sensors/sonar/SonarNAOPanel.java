package edu.sru.thangiah.nao.sensors.sonar;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.instrument.IllegalClassFormatException;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.aldebaran.demo.RobotIP;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;

import edu.sru.brian.gui.ShowablePanel;
import edu.sru.thangiah.nao.connection.Connection;

public class SonarNAOPanel extends JPanel {
	
	private JTextField[] txtLeftValueAry = new JTextField[10];
	private JTextField[] txtRightValueAry = new JTextField[10];
	
	private Session session;
	private SonarNAO sonar;

	/**
	 * Create the panel.
	 */
	public SonarNAOPanel() {
		initialize();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SonarNAOPanel panel = new SonarNAOPanel();
					showDialog(null, panel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SonarNAOPanel panel = new SonarNAOPanel();
					ShowablePanel.showDialog(panel, "SonarNAOPanel");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void showDialog(JFrame frame, SonarNAOPanel blobProps) {
		final SonarNAOPanel panel;
		JDialog dialog;
		
		panel = new SonarNAOPanel();
		
		if(frame != null && frame.getTitle() != "")
		{
			dialog = new JDialog(frame, true);
		}
		else
		{
			dialog = new JDialog(frame, "SonarNAOPanel", true);
		}
		
		
		dialog.setBounds(100, 100, 435, 405);
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setMinimumSize(new Dimension(240,275));
		
		
		//panel.setLayout(new FlowLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialog.getContentPane().add(panel, BorderLayout.CENTER);
		dialog.getContentPane().add(panel);
		dialog.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.out.println("window Closing!!!");
				panel.Uninitialize();
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				System.out.println("window Closed!!!");
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
		dialog.setVisible(true);
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		SonarNAOSettings settings;
		Connection connection;
		/*
		connection = new Connection();
		
		connection.connectToNao(RobotIP.ip);
		
		if(!connection.isConnected()) {
			JOptionPane.showMessageDialog (null, "Could not connect to Robot!!\r\nAt "+RobotIP.ip, "WalkTest", JOptionPane.WARNING_MESSAGE);
		}
		
		sonar = new SonarNAO(connection);
		
		settings = new SonarNAOSettings();
		settings.setReceivers(SonarNAODeviceID.BOTH);
		settings.setTransmitters(SonarNAODeviceID.BOTH);
		sonar.setSonarWaveSettings(settings);
		
		sonar.enable();
		
		sonar.addSonarDetectionListener(new SonarDetectionListener() {

			@Override
			public synchronized void sonarDetected(int loc, float distance) {
				// TODO Auto-generated method stub
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
		*/
		
		/*
		frmSonarNaoTester.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("Window Resized Width:"+e.getComponent().getWidth()+" Height:"+e.getComponent().getHeight());
			}
		});
		*/
		/*
		frmSonarNaoTester.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Uninitialize();
			}
		});
		*/
		
		this.setBounds(100, 100, 425, 405);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel panel = new JPanel();
		this.add(panel);
		//this.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		//frmSonarNaoTester.getContentPane().add(panel);
		
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
		this.add(panel_3);
		
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
				int loc;
				Float[] echoes;
				
				loc = SonarNAOLocation.LEFT.intValue();
				echoes = new Float[10];
				
				try {
					echoes = sonar.getSonarEchoes(loc);
				} catch (IllegalClassFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				for(int i=0; i < 10; i++) {
					if(echoes.length <= i) {
						txtLeftValueAry[i].setText("0");
					}
					else {
						txtLeftValueAry[i].setText(""+echoes[i]);
					}
				}
				loc = SonarNAOLocation.LEFT.intValue();
				echoes = new Float[10];
				
				try {
					echoes = sonar.getSonarEchoes(loc);
				} catch (IllegalClassFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int i=0; i < 10; i++) {
					if(echoes.length <= i) {
						txtRightValueAry[i].setText("0");
					}
					else {
						txtRightValueAry[i].setText(""+echoes[i]);
					}
				}
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
		}
		
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
	
	public void Uninitialize() {
		sonar.disable();
	}

}
