package edu.sru.thangiah.nao.gui;

import javax.swing.JPanel;

import edu.sru.thangiah.nao.gui.NAODetailPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;
import java.net.*;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.demo.*;
import edu.sru.thangiah.nao.demo.old.GoodbyeWorld;
import edu.sru.thangiah.nao.demo.old.TimedWave;

/**
 * @author thangiah
 *
 */
public class NAOComDetailPanel extends JPanel {

	private JTextField tfIpAddress;
	private JTextField tfPort;
	JLabel lblNewLabel = new JLabel("IP Address");
	JButton btnConnectDisconnect = new JButton("Connect");
	JButton btnPing = new JButton("Ping");	
	JButton btnRunStop = new JButton("Run");
	JComboBox cBNaoPrograms = new JComboBox();
	
	//Track the connection
	Connect naoConnect;
	boolean isConnectStatus;
	
	/**
	 * Create the panel.
	 */
	public NAOComDetailPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);

		lblNewLabel.setBounds(10, 11, 62, 14);
		add(lblNewLabel);
		
		tfIpAddress = new JTextField();
		tfIpAddress.setText("192.168.1.101");
		tfIpAddress.setBounds(82, 8, 99, 20);
		add(tfIpAddress);
		tfIpAddress.setColumns(10);
		
		tfPort = new JTextField();
		tfPort.setText("9559");
		tfPort.setBounds(233, 8, 57, 20);
		add(tfPort);
		tfPort.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Port #");
		lblNewLabel_1.setBounds(191, 11, 46, 14);
		add(lblNewLabel_1);
				
		//Event and button to ping the robot
		btnPing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pingRobot();
			}
		});
		btnPing.setBounds(201, 39, 89, 23);
		add(btnPing);

	
		//Event and button to connect/disconnect from robot
		btnConnectDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectDisconnect();
			}
		});
		
		btnConnectDisconnect.setBounds(20, 39, 104, 23);
		add(btnConnectDisconnect);
		btnRunStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//program to run
				runProgram(cBNaoPrograms.getSelectedItem().toString());
			}
		});
		
		btnRunStop.setBounds(20, 138, 89, 23);
		add(btnRunStop);
		

		cBNaoPrograms.setModel(new DefaultComboBoxModel(new String[] {"Goodbye World", "Timed Wave", "Fist Bump"
				, "Hand Shake", "Walk With Me"}));
		cBNaoPrograms.setBounds(20, 89, 118, 20);
		add(cBNaoPrograms);

		
		//initially the connection status is false
		isConnectStatus = false;

	}
	
	
	/**
	 * Ping the robot
	 */
	void pingRobot()
	{
		 try {
			 	String ipAddress = tfIpAddress.getText();
			 	
			    InetAddress inet = InetAddress.getByName(tfIpAddress.getText());

			    System.out.println("Sending Ping Request to " + ipAddress);
			    System.out.println(inet.isReachable(5000) ? "Robot is reachable" : "Robot is NOT reachable");
		 }
		 catch (IOException e) 
		 {
		      e.printStackTrace();
		 }
	}
	
	
	public boolean connectDisconnect()
	{
		//Not connected
		if (!isConnectStatus)
		{
			//Get the ipaddress and port
			String ipAddress;
			String port;
			
			ipAddress = tfIpAddress.getText();
			port = tfPort.getText();
			
			//check if the ip address and port are valid
			
			naoConnect = new Connect(ipAddress, port);
			try {
				naoConnect.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			btnConnectDisconnect.setText("Disconnect");
			isConnectStatus= true;
			System.out.println("Connected to NAO");
		}
		else
		{
			if(naoConnect.getStatus())
			{
				btnConnectDisconnect.setText("Connect");
				isConnectStatus= false;
				System.out.println("Disconnected from NAO");
			}
			else
			{
				System.out.println("Could not disconnect from NAO");
			}
		}
		return isConnectStatus;
	
	}
	
	void runProgram(String program)
	{
		
		//check if the program is connected, then run the program
		if (isConnectStatus)
		{
			switch (program)
			{
				case "Goodbye World":
					GoodbyeWorld.goodbyeWorld(naoConnect.getSession());
					break;
				case "Timed Wave":
				try {
					TimedWave.timedWave(naoConnect.getSession());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					break;
				default:
					break;
			}
		}
		else
		{
			System.out.println("Not connected to the NAO robot");
		}
		
	}
}
