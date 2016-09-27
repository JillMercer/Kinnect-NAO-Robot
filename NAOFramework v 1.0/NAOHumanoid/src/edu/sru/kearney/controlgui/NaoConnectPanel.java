package edu.sru.kearney.controlgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.connection.Robot;
import edu.sru.thangiah.nao.connection.RobotList;
import edu.sru.thangiah.nao.system.NaoSystem;

/**
 * Connection Panel Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public class NaoConnectPanel extends NaoPanel implements NaoPanelInterface {

	/**
	 * Create the panel.
	 */

	public RobotList naoList;
	private JButton btnReboot, btnShutdown, btnDisconnect;
	private int index;
	private Robot robot;
	private ControlGui main;
	private JTextPane namePane, ipPane, batPane;
	private NaoSystem system;
	private Battery bat;

	public NaoConnectPanel(ControlGui main, Robot robot) {
		this.robot = robot;
		this.main = main;
		setSize(390, 104);
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);

		btnReboot = new JButton("Reboot");
		btnReboot.setBounds(140, 70, 109, 23);
		add(btnReboot);
		btnReboot.setEnabled(false);

		btnShutdown = new JButton("Shutdown");
		btnShutdown.setBounds(270, 70, 109, 23);
		add(btnShutdown);
		btnShutdown.setEnabled(false);

		btnDisconnect = new JButton("Disconnect");

		btnDisconnect.setEnabled(false);
		btnDisconnect.setBounds(10, 70, 109, 23);
		add(btnDisconnect);

		JTextPane txtpnConnectedTo = new JTextPane();
		txtpnConnectedTo.setEditable(false);
		txtpnConnectedTo.setText("Connected To");
		txtpnConnectedTo.setBounds(10, 10, 109, 20);
		add(txtpnConnectedTo);

		namePane = new JTextPane();
		namePane.setEditable(false);
		namePane.setBounds(10, 40, 109, 20);
		add(namePane);

		JTextPane txtpnIpAddress = new JTextPane();
		txtpnIpAddress.setText("Ip Address");
		txtpnIpAddress.setEditable(false);
		txtpnIpAddress.setBounds(140, 10, 109, 20);
		add(txtpnIpAddress);

		JTextPane txtpnBatteryPercent = new JTextPane();
		txtpnBatteryPercent.setText("Battery:");
		txtpnBatteryPercent.setEditable(false);
		txtpnBatteryPercent.setBounds(270, 10, 109, 20);
		add(txtpnBatteryPercent);

		batPane = new JTextPane();
		batPane.setEditable(false);
		batPane.setBounds(270, 40, 109, 20);
		add(batPane);

		ipPane = new JTextPane();
		ipPane.setEditable(false);
		ipPane.setBounds(140, 40, 109, 20);
		add(ipPane);
	}

	void updateBattery(int update) {
		batPane.setText(Integer.toString(update));
	}

	public void updateName(String name) {
		namePane.setText(name);
	}

	public void updateIp(String ip) {
		ipPane.setText(ip);
	}

	public class Battery extends Thread {

		public void run() {

			while (robot.isRunning()) {
				try {
					int update = (robot.batteryCharge());
					updateBattery(update);
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					stopThread();
				}
			}
		}

		public void stopThread() {
			this.interrupt();
		}
	}

	@Override
	public void run() throws Exception {
		updateName(robot.name());
		updateIp(robot.ip());
		bat = new Battery();
		bat.start();
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					main.disconnect();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnShutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					main.shutdown();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					main.reboot();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}
}