package edu.sru.thangiah.nao.demo.old.controlgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.connection.RobotList;

public class NaoConnectPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	public RobotList naoList;
	JButton btnConnectList;
	JButton btnReboot;
	JButton btnShutdown;
	JComboBox<String> comboBox;
	private int index;
	private Connect connect;
	private JTextField IP;
	private JTextField PORT;
	private JButton btnConnectManually;
	private JButton btnDisconnect;
	public boolean isConnected;
	MainGui main;
	public NaoConnectPanel(MainGui main) {
		
		this.main = main;
		setSize(282,217);
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		isConnected = false;
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(10, 11, 143, 23);
		add(comboBox);
		comboBox.setEnabled(false);
		
		btnConnectList = new JButton("Connect");
		btnConnectList.setBounds(163, 11, 109, 23);
		add(btnConnectList);
		btnConnectList.setEnabled(false);
		
		btnReboot = new JButton("Reboot");
		btnReboot.setBounds(163, 106, 109, 23);
		add(btnReboot);
		btnReboot.setEnabled(false);
		
		btnShutdown = new JButton("Shutdown");
		btnShutdown.setBounds(163, 140, 109, 23);
		add(btnShutdown);
		btnShutdown.setEnabled(false);
		
		IP = new JTextField();
		IP.setText("192.168.0.1");
		IP.setColumns(10);
		IP.setBounds(10, 45, 106, 20);
		add(IP);
		
		PORT = new JTextField();
		PORT.setText("9559");
		PORT.setColumns(10);
		PORT.setBounds(10, 72, 106, 20);
		add(PORT);
		
		btnConnectManually = new JButton("Manual Connect");
		btnConnectManually.setBounds(163, 44, 109, 23);
		add(btnConnectManually);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					disconnect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnDisconnect.setEnabled(false);
		btnDisconnect.setBounds(163, 174, 109, 23);
		add(btnDisconnect);
		
	}	

	public void pingAll() throws Exception{
		
		naoList = new RobotList();
		naoList.run();
		setLayout(null);
		comboBox.removeAllItems();
		for(String s:naoList.getAllNames()){
			comboBox.addItem(s);
		}
		
		btnConnectList.setEnabled(true);
		comboBox.setEnabled(true);

		btnConnectList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			try {
				connectList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		
	}
	public void run() throws Exception{
		
		btnConnectManually.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try{
					connectManual();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
		btnShutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				try {
					shutDown();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

		btnReboot.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0){
				try {
					reboot();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});		
	}
	
	void reboot() throws Exception{
		if(connect.getStatus()){
			
			connect.reboot();
			main.stopConnect();
			enableButtons(false);
			main.disableMethods();
			
		}
		else{
			
			System.out.println("Robot not Connected");
		}
	}
	
	void enableButtons(boolean enable) throws Exception{
		
		comboBox.setEnabled(!enable);
		IP.setEnabled(!enable);
		PORT.setEnabled(!enable);
		btnConnectList.setEnabled(!enable);
		btnConnectManually.setEnabled(!enable);
		btnShutdown.setEnabled(enable);
		btnReboot.setEnabled(enable);
		btnDisconnect.setEnabled(enable);
		isConnected = enable;
		
	}
	
	void shutDown() throws Exception{
		
		if(connect.getStatus()){
			
			connect.shutdown();
			main.stopConnect();
			main.disableMethods();
			enableButtons(false);
			
		}
		else {
			
			System.out.println("Robot not Connected");

		}
	}

	void connectList() throws Exception{
		
		index = comboBox.getSelectedIndex();
		connect = new Connect(naoList.getIp(index));
		connect.run();
		main.enableMethods(connect);
		enableButtons(true);
		
	}

	void connectManual() throws Exception{
		connect = new Connect(IP.getText(), PORT.getText());
		connect.run();
		main.enableMethods(connect);
		enableButtons(true);
		
	}
	
	void disconnect() throws Exception{
		
		connect.stop();
		connect = null;
		enableButtons(false);
		main.disableMethods();

	}
	
	public void setMainGui(MainGui gui){
		this.main = gui;
	}
	
	public Connect getConnect(){
		return connect;
	}

}
