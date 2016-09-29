package edu.sru.thangiah.nao.demo.old.controlgui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.demo.old.DemoEnum;
import edu.sru.thangiah.nao.demo.old.controlgui.NaoConnectPanel;
import edu.sru.thangiah.nao.diagnostic.gui.DiagnosticGUI;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import java.awt.SystemColor;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainGui extends JFrame {

	private JPanel contentPane;
	private NaoConnectPanel naoConnectPanel;
	JPanel Connection, Applications;
	private NaoWalkPanel naoWalkPanel;
	private NaoAwarenessPanel Awareness;
	private JTabbedPane tabbedPane;
	private Connect connect;
	private NaoPosturePanel naoPosturePanel;
	private NaoTTSPanel naoTTSPanel;
	private JTextPane txtpnConnectedTo, txtpnBatteryLevel;
	private Battery bat;
	private JMenuItem mntmRunDiagnostics;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGui frame = new MainGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	
	public MainGui() throws Exception {
		setResizable(false);
		
		setTitle("Nao Gui v2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmRunDiagnostics = new JMenuItem("Run Diagnostics");
		mntmRunDiagnostics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				diagnostics();
			}
		});
		mnFile.add(mntmRunDiagnostics);
		
		JMenuItem mntmRunApplication = new JMenuItem("Run Application");
		mntmRunApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if(connect.getStatus()){
					Object[] possibilities = DemoEnum.values();
					Object selectedApp = JOptionPane.showInputDialog(
					                    null,
					                    "Choose an Application Demo:\n",
					                    "Run Application",
					                    JOptionPane.PLAIN_MESSAGE,
					                    null,
					                    possibilities, DemoEnum.Boredom);
					
					switch((String)selectedApp){
					case "FistBump":
						break;
					case "GoodbyeWorld":
						break;
					case "SensorTestGame":
						break;
					case "TimedWave":
						break;
					case "WalkWithMe":
						break;
					case "Boredom":
						break;
					default:
						break;
					}
					}
				//}
			});
		
		mnFile.add(mntmRunApplication);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 594, 451);
		contentPane.add(tabbedPane);
		
		Connection = new JPanel();
		tabbedPane.addTab("Connection", null, Connection, null);
		Connection.setLayout(null);
		
		JButton btnPingAll = new JButton("Ping All");
		btnPingAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					naoConnectPanel.pingAll();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnPingAll.setBounds(10, 11, 89, 23);
		Connection.add(btnPingAll);
		
		naoConnectPanel = new NaoConnectPanel(this);
		naoConnectPanel.setBounds(10, 45, 287, 213);
		Connection.add(naoConnectPanel);
		naoConnectPanel.run();
		
		naoPosturePanel = new NaoPosturePanel();
		naoPosturePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		naoPosturePanel.setBounds(10, 269, 287, 143);
		Connection.add(naoPosturePanel);
		
		naoTTSPanel = new NaoTTSPanel();
		naoTTSPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		naoTTSPanel.setBounds(307, 45, 272, 367);
		Connection.add(naoTTSPanel);
		
		txtpnConnectedTo = new JTextPane();
		txtpnConnectedTo.setBackground(SystemColor.menu);
		txtpnConnectedTo.setEditable(false);
		txtpnConnectedTo.setText("Connected To:");
		txtpnConnectedTo.setBounds(109, 14, 144, 20);
		Connection.add(txtpnConnectedTo);
		
		txtpnBatteryLevel = new JTextPane();
		txtpnBatteryLevel.setBackground(SystemColor.menu);
		txtpnBatteryLevel.setEditable(false);
		txtpnBatteryLevel.setText("Battery Level:");
		txtpnBatteryLevel.setBounds(258, 14, 170, 20);
		Connection.add(txtpnBatteryLevel);

	}
	
	public void enableMethods(Connect connect) throws Exception{
	
		this.connect = connect;
		naoWalkPanel = new NaoWalkPanel();
		tabbedPane.addTab("Movement", null, naoWalkPanel, null);
		naoWalkPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		naoWalkPanel.run(connect);
		
		naoTTSPanel.run(connect);
		naoPosturePanel.run(connect);
		
		Awareness = new NaoAwarenessPanel();
		tabbedPane.addTab("Awareness", null, Awareness, null);
		Awareness.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Awareness.run(connect);
		
		txtpnConnectedTo.setText("Connected To: " + connect.getName());
		bat = new Battery(this, connect);
		bat.start();
		
	}
	
	public void updateBattery(String update) throws Exception{
		txtpnBatteryLevel.setText(update);
	}
	
	public void disableMethods() throws Exception{
		
		bat.stopThread();
		naoWalkPanel = null;
		Awareness = null;
		Applications = null;
		naoTTSPanel.enableDisable(false);
		naoPosturePanel.enableDisable(false);
		tabbedPane.remove(1);
		tabbedPane.remove(1);
		tabbedPane.remove(1);
		txtpnConnectedTo.setText("Connected To: ");
		txtpnBatteryLevel.setText("Battery Level: ");
		
	}

	public void diagnostics(){
		if(connect.getStatus()){
			SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	try {
						new DiagnosticGUI(connect.getSession());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }       
	});
	}
	}
	
	public void receiveConnect(Connect connect){
		this.connect = connect;
	}
	
	public void stopConnect(){
	}
	
	public class Battery extends Thread{
		
		private MainGui gui;
		private Connect connect;
		
		public Battery(MainGui gui, Connect connect){
			this.gui = gui;
			this.connect = connect;
		}
		
		public void run(){
			
			while(connect.getStatus()){
				try {				
					String update = ("Battery Level: " + connect.getBatteryCharge() + "%");
					gui.updateBattery(update);
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					stopThread();
				}
			}
			}
		
		public void stopThread(){
			this.interrupt();
		}
	}
}