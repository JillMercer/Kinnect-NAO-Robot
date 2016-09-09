package edu.sru.thangiah.nao.sensors.infrared;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.aldebaran.demo.RobotIP;
import com.aldebaran.qi.Application;
import com.aldebaran.qi.RawApplication;
import com.aldebaran.qi.Session;

import edu.sru.brian.gui.ShowablePanel;
import edu.sru.brian.newapi.demo.RobotConfig;
import edu.sru.thangiah.nao.connection.Connection;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestFrameV2 extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldIPSend;
	private JTextField txtFieldOneByteSend;
	private JTextField txtFieldFourBytesSend;
	private JTextField txtFieldRemoteNameSend;
	private JTextField txtFieldButtonNameSend;
	private JTextField txtFieldIPReceived;
	private JTextField txtFieldOneByteReceived;
	private JTextField txtFieldFourBytesReceived;
	private JTextField txtFieldRemoteNameReceived;
	private JTextField txtFieldButtonNameReceived;
	private InfraredNAO infrared;
	
	private static RawApplication app = null;
	private static boolean isAppRunning = false;
	
	
	private void initApplication() {
		if(!isAppRunning)
		{
			isAppRunning=true;
			String args[];
			args = new String[1];
			args[0] = new String();
			app = new RawApplication(args);
			
			//app.start();
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrameV2 frame = new TestFrameV2(RobotConfig.getConnectionString());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		/*
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame("tcp://" + "192.168.0.101" + ":" + RobotConfig.PORT);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	}

	/**
	 * Create the frame.
	 */
	public TestFrameV2(String connectStr) {
		initializeNAO(connectStr);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 447);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_11 = new JPanel();
		panel.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.Y_AXIS));
		
		JLabel lblSendData = new JLabel("Send Data");
		panel_11.add(lblSendData);
		
		JPanel panel_3 = new JPanel();
		panel_11.add(panel_3);
		
		JLabel lblIpAddress = new JLabel("IP Address:");
		panel_3.add(lblIpAddress);
		
		txtFieldIPSend = new JTextField();
		panel_3.add(txtFieldIPSend);
		txtFieldIPSend.setColumns(10);
		
		JButton btnSendIp = new JButton("Send IP");
		btnSendIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infrared.sendIPAddress(txtFieldIPSend.getText());
			}
		});
		panel_3.add(btnSendIp);
		
		JPanel panel_4 = new JPanel();
		panel_11.add(panel_4);
		
		JLabel lblOneByte = new JLabel("One Byte:");
		panel_4.add(lblOneByte);
		
		txtFieldOneByteSend = new JTextField();
		txtFieldOneByteSend.setEditable(false);
		panel_4.add(txtFieldOneByteSend);
		txtFieldOneByteSend.setColumns(10);
		
		JButton btnSendOneByte = new JButton("Send One Byte");
		btnSendOneByte.setEnabled(false);
		btnSendOneByte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infrared.send8Bit(Integer.valueOf(txtFieldOneByteSend.getText()));
			}
		});
		panel_4.add(btnSendOneByte);
		
		JPanel panel_5 = new JPanel();
		panel_11.add(panel_5);
		
		JLabel lblFourBytes = new JLabel("Four Bytes:");
		panel_5.add(lblFourBytes);
		
		txtFieldFourBytesSend = new JTextField();
		txtFieldFourBytesSend.setEditable(false);
		panel_5.add(txtFieldFourBytesSend);
		txtFieldFourBytesSend.setColumns(10);
		
		JButton btnSendFourBytes = new JButton("Send Four Bytes");
		btnSendFourBytes.setEnabled(false);
		btnSendFourBytes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infrared.send32Bit(txtFieldFourBytesSend.getText());
			}
		});
		panel_5.add(btnSendFourBytes);
		
		JPanel panel_6 = new JPanel();
		panel_11.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		
		JLabel lblSendRemoteButton = new JLabel("Send Remote Button");
		panel_6.add(lblSendRemoteButton);
		
		JPanel panel_10 = new JPanel();
		panel_6.add(panel_10);
		
		JPanel panel_9 = new JPanel();
		panel_10.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));
		
		JPanel panel_7 = new JPanel();
		panel_9.add(panel_7);
		
		JLabel lblRemoteName = new JLabel("Remote Name:");
		panel_7.add(lblRemoteName);
		
		txtFieldRemoteNameSend = new JTextField();
		txtFieldRemoteNameSend.setEditable(false);
		panel_7.add(txtFieldRemoteNameSend);
		txtFieldRemoteNameSend.setColumns(10);
		
		JPanel panel_8 = new JPanel();
		panel_9.add(panel_8);
		
		JLabel lblRemoteButtonName = new JLabel("Remote Button Name:");
		panel_8.add(lblRemoteButtonName);
		
		txtFieldButtonNameSend = new JTextField();
		txtFieldButtonNameSend.setEditable(false);
		panel_8.add(txtFieldButtonNameSend);
		txtFieldButtonNameSend.setColumns(10);
		
		JButton btnSendRemoteButton = new JButton("Send Remote Button");
		btnSendRemoteButton.setEnabled(false);
		btnSendRemoteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infrared.sendRemoteButton(txtFieldRemoteNameSend.getText(), txtFieldButtonNameSend.getText());
			}
		});
		panel_10.add(btnSendRemoteButton);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JLabel lblReceivedData = new JLabel("Received Data");
		panel_2.add(lblReceivedData);
		
		JPanel panel_12 = new JPanel();
		panel_2.add(panel_12);
		
		JLabel lblLastIp = new JLabel("Last IP:");
		panel_12.add(lblLastIp);
		
		txtFieldIPReceived = new JTextField();
		txtFieldIPReceived.setEditable(false);
		panel_12.add(txtFieldIPReceived);
		txtFieldIPReceived.setColumns(10);
		
		JPanel panel_13 = new JPanel();
		panel_2.add(panel_13);
		
		JLabel lblLastOneByte = new JLabel("Last One Byte:");
		panel_13.add(lblLastOneByte);
		
		txtFieldOneByteReceived = new JTextField();
		txtFieldOneByteReceived.setEditable(false);
		panel_13.add(txtFieldOneByteReceived);
		txtFieldOneByteReceived.setColumns(10);
		
		JPanel panel_14 = new JPanel();
		panel_2.add(panel_14);
		
		JLabel lblLastFourBytes = new JLabel("Last Four Bytes:");
		panel_14.add(lblLastFourBytes);
		
		txtFieldFourBytesReceived = new JTextField();
		txtFieldFourBytesReceived.setEditable(false);
		panel_14.add(txtFieldFourBytesReceived);
		txtFieldFourBytesReceived.setColumns(10);
		
		JPanel panel_15 = new JPanel();
		panel_2.add(panel_15);
		
		JLabel lblLastRemoteReceived = new JLabel("Last Remote Received:");
		panel_15.add(lblLastRemoteReceived);
		
		JLabel lblName = new JLabel("Name:");
		panel_15.add(lblName);
		
		txtFieldRemoteNameReceived = new JTextField();
		txtFieldRemoteNameReceived.setEditable(false);
		panel_15.add(txtFieldRemoteNameReceived);
		txtFieldRemoteNameReceived.setColumns(10);
		
		JLabel lblButtonName = new JLabel("Button Name:");
		panel_15.add(lblButtonName);
		
		txtFieldButtonNameReceived = new JTextField();
		txtFieldButtonNameReceived.setEditable(false);
		panel_15.add(txtFieldButtonNameReceived);
		txtFieldButtonNameReceived.setColumns(10);
		
		JPanel panel_16 = new JPanel();
		panel_2.add(panel_16);
		
		JButton btnGetData = new JButton("Get Data");
		btnGetData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//txtFieldRemoteNameReceived.setText(infrared.getRemoteNameReceived());
				//txtFieldButtonNameReceived.setText(infrared.getRemoteButtonNameReceived());
				//txtFieldFourBytesReceived.setText(""+infrared.getFourthByteOf32bitReceived());
				//txtFieldOneByteReceived.setText(""+infrared.get8BitReceived());
				txtFieldIPReceived.setText(infrared.getIPAddressReceived());
			}
		});
		panel_16.add(btnGetData);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTextArea textAreaOutput = new JTextArea();
		textAreaOutput.setEditable(false);
		//panel_1.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(textAreaOutput);
	}
	
	public void initializeNAO(String connectStr)
	{
		initApplication();
		Session session;
		
		Connection connection;
		
		connection = new Connection();
		
		connection.connectToNao("192.168.1.108");
		
		session = connection.getSession();
		
		
		if(!session.isConnected()) {
			JOptionPane.showMessageDialog (null, "Could not connect to Robot!!\r\nAt "+RobotIP.ip, "WalkTest", JOptionPane.WARNING_MESSAGE);
		}
		
		infrared = new InfraredNAO(session);
		infrared.enable();
		
	}

}
