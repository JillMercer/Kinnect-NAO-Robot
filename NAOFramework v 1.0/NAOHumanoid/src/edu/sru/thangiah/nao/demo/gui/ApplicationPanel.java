package edu.sru.thangiah.nao.demo.gui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import edu.sru.thangiah.nao.demo.Demo;

public class ApplicationPanel extends JPanel {

	private Demo demo;
	private ArrayList<String> robotNames;
	private String title;
	
	public ApplicationPanel(Demo demo) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.demo = demo;
		this.title = demo.getDemoName();
		try {
			demo.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "ConnectedRobots", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 195, 244);
		add(panel);
		for(String str : demo.getRobotNames()){
			JTextPane robot = new JTextPane();
			robot.setBackground(SystemColor.menu);
			robot.setText(str);
			robot.setEditable(false);
			panel.add(robot);
		}
		
		JButton btnStopApplication = new JButton("Stop Application");
		btnStopApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					demo.exit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStopApplication.setBounds(10, 296, 195, 23);
		add(btnStopApplication);
		setPreferredSize(new Dimension(215,330));
		setVisible(true);
		new CheckThread().start();
	}
	
	public void stopDemo(){
		try {
			demo.exit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(demo.isRunning()){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		getParent().remove(this);
	}
	private void exit(){
		getParent().remove(this);
	}
	
	private class CheckThread extends Thread{
		public void run(){
			while(demo.isRunning()){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			exit();
		}
	}
	public boolean isActive(){
		return demo.isRunning();
	}
}
