package edu.sru.thangiah.nao.sensors.infrared;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JTextArea;

import edu.sru.brian.gui.ShowablePanel;
import edu.sru.thangiah.nao.sensors.sonar.SonarNAOPanel;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class InfraredNAOPanelV2 extends JPanel {
	private JTextField txtFieldIPAddr;
	private JTextField txtFieldOneByte;
	private JTextField txtFieldFourBytes;
	private JTextField txtFieldRemoteName;
	private JTextField txtFieldRemoteButtonName;
	private JTextField txtFieldReceivedIP;
	private JTextField txtFieldReceivedOneByte;
	private JTextField txtFieldReceivedFourBytes;

	/**
	 * Create the panel.
	 */
	public InfraredNAOPanelV2() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		add(panel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel lblSendIp = new JLabel("Send IP:");
		panel_2.add(lblSendIp);
		
		txtFieldIPAddr = new JTextField();
		panel_2.add(txtFieldIPAddr);
		txtFieldIPAddr.setColumns(10);
		
		JButton btnSendIp = new JButton("Send IP");
		panel_2.add(btnSendIp);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblSendOneByte = new JLabel("Send One Byte:");
		panel_3.add(lblSendOneByte);
		
		txtFieldOneByte = new JTextField();
		panel_3.add(txtFieldOneByte);
		txtFieldOneByte.setColumns(10);
		
		JButton btnNewButton = new JButton("Send One Byte");
		panel_3.add(btnNewButton);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JLabel lblSendFourBytes = new JLabel("Send Four Bytes:");
		panel_4.add(lblSendFourBytes);
		
		txtFieldFourBytes = new JTextField();
		panel_4.add(txtFieldFourBytes);
		txtFieldFourBytes.setColumns(10);
		
		JButton btnSendFourBytes = new JButton("Send Four Bytes");
		panel_4.add(btnSendFourBytes);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		JLabel lblSendButtonRemote = new JLabel("Send Remote Control Button");
		panel_5.add(lblSendButtonRemote);
		
		JPanel panel_9 = new JPanel();
		panel_5.add(panel_9);
		
		JPanel panel_6 = new JPanel();
		panel_9.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		
		JLabel lblRemoteNam = new JLabel("Remote Name:");
		panel_7.add(lblRemoteNam);
		
		txtFieldRemoteName = new JTextField();
		panel_7.add(txtFieldRemoteName);
		txtFieldRemoteName.setColumns(10);
		
		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8);
		
		JLabel lblButtonName = new JLabel("Button Name:");
		panel_8.add(lblButtonName);
		
		txtFieldRemoteButtonName = new JTextField();
		panel_8.add(txtFieldRemoteButtonName);
		txtFieldRemoteButtonName.setColumns(10);
		
		JButton btnSendRemoteButton = new JButton("Send Remote Button");
		panel_9.add(btnSendRemoteButton);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{450, 0};
		gbl_panel_1.rowHeights = new int[] {1};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblReceivedData = new JLabel("Received Data");
		GridBagConstraints gbc_lblReceivedData = new GridBagConstraints();
		gbc_lblReceivedData.anchor = GridBagConstraints.WEST;
		gbc_lblReceivedData.insets = new Insets(0, 0, 5, 0);
		gbc_lblReceivedData.gridx = 0;
		gbc_lblReceivedData.gridy = 0;
		panel_1.add(lblReceivedData, gbc_lblReceivedData);
		
		JPanel panel_10 = new JPanel();
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.insets = new Insets(0, 0, 5, 0);
		gbc_panel_10.gridx = 0;
		gbc_panel_10.gridy = 1;
		panel_1.add(panel_10, gbc_panel_10);
		panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));
		
		JLabel lblReceivedIp = new JLabel("IP:");
		panel_10.add(lblReceivedIp);
		
		txtFieldReceivedIP = new JTextField();
		txtFieldReceivedIP.setEditable(false);
		panel_10.add(txtFieldReceivedIP);
		txtFieldReceivedIP.setColumns(10);
		
		JPanel panel_11 = new JPanel();
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.insets = new Insets(0, 0, 5, 0);
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 2;
		panel_1.add(panel_11, gbc_panel_11);
		
		JLabel lblReceivedSingleByte = new JLabel("One Byte:");
		panel_11.add(lblReceivedSingleByte);
		
		txtFieldReceivedOneByte = new JTextField();
		txtFieldReceivedOneByte.setEditable(false);
		panel_11.add(txtFieldReceivedOneByte);
		txtFieldReceivedOneByte.setColumns(10);
		
		JPanel panel_12 = new JPanel();
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.insets = new Insets(0, 0, 5, 0);
		gbc_panel_12.gridx = 0;
		gbc_panel_12.gridy = 3;
		panel_1.add(panel_12, gbc_panel_12);
		
		JLabel lblFourByte = new JLabel("Four Bytes:");
		panel_12.add(lblFourByte);
		
		txtFieldReceivedFourBytes = new JTextField();
		txtFieldReceivedFourBytes.setEditable(false);
		panel_12.add(txtFieldReceivedFourBytes);
		txtFieldReceivedFourBytes.setColumns(10);
		
		JPanel panel_13 = new JPanel();
		GridBagConstraints gbc_panel_13 = new GridBagConstraints();
		gbc_panel_13.gridx = 0;
		gbc_panel_13.gridy = 4;
		panel_1.add(panel_13, gbc_panel_13);
		
		JLabel lblRemoteControlButton = new JLabel("Remote Control Button");
		panel_13.add(lblRemoteControlButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setColumns(10);
		panel_13.add(textArea);

	}
	
	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfraredNAOPanelV2 panel = new InfraredNAOPanelV2();
					ShowablePanel.showDialog(panel, "InfraredNAOPanel");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
