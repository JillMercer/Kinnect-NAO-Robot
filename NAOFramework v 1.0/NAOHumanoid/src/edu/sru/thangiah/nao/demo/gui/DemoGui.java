package edu.sru.thangiah.nao.demo.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.sru.thangiah.nao.connection.Robot;
import edu.sru.thangiah.nao.connection.SynchronizedConnect;
import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;

import edu.sru.thangiah.nao.demo.AutomatedMotion; // Demos that can be executed
import edu.sru.thangiah.nao.demo.BarcodeScanner;
import edu.sru.thangiah.nao.demo.Demo;
import edu.sru.thangiah.nao.demo.DemoEnum;
import edu.sru.thangiah.nao.demo.HelloWorldTutorial;
import edu.sru.thangiah.nao.demo.KinectTracking;
import edu.sru.thangiah.nao.demo.HelloEarth;
import edu.sru.thangiah.nao.demo.FistBumpDemo;
import edu.sru.thangiah.nao.demo.Posture;
import edu.sru.thangiah.nao.demo.SynchronizedDance;
import edu.sru.thangiah.nao.demo.Track;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

/**
 * Author: Sam Thangiah, last edited 8/5/2016
 * 
 * Main class running all the demos
 * 
 * TO DO: When connect is clicked, indicate with a busy icon, searching for robots
 *        Reduce the number of popups
 *
 */
public class DemoGui extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private RobotPanel robotPanel;
	private ArrayList<Demo> demos;				//for loading all the demos
	private SynchronizedConnectDemo connect;
	private JMenu mnRun;
	private JMenu mnOptions;
	private JMenuItem mntmShutdownAll;
	private JMenuItem mntmRebootAll;
	private JMenuItem mntmDisconnectAll;
	private JMenuItem mntmEndAllApplications;
	private JMenuItem mntmRunCamera;
	private JMenuItem mntmRunDiagnostics;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DemoGui frame = new DemoGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DemoGui() {
		setResizable(false);
		demos = new ArrayList<Demo>();
		connect = null;
		setTitle("Nao Demo Gui");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 599, 452);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		//Connect and find all the robots
		JMenuItem mntmConnect = new JMenuItem("Connect");
		mntmConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect();
			}
		});
		mnFile.add(mntmConnect);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
		});
		mnFile.add(mntmExit);
		
		mnRun = new JMenu("Run");
		mnRun.setEnabled(false);
		menuBar.add(mnRun);
		
		mnOptions = new JMenu("Options");
		mnOptions.setEnabled(false);
		menuBar.add(mnOptions);
		
		//menu option - Shutdown all
		mntmShutdownAll = new JMenuItem("Shutdown All");
		mntmShutdownAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect.shutdownAll();
				robotPanel.createNodes(connect);
			}
		});
		
		//menu option - Run Camera
		mntmRunCamera = new JMenuItem("Run Camera");
		mntmRunCamera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new CameraDemoFrame(connect).setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//menu option - Run Diagnostics
		mntmRunDiagnostics = new JMenuItem("Run Diagnostics");
		mntmRunDiagnostics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new DiagnosticGUI(connect);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//menu option - Reboot all
		mntmRebootAll = new JMenuItem("Reboot All");
		mntmRebootAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect.rebootAll();
				robotPanel.createNodes(connect);
			}
		});

		//menu option - Disconnect All
		mntmDisconnectAll = new JMenuItem("Disconnect All");
		mntmDisconnectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect.stopAll();
				robotPanel.createNodes(connect);
			}
		});
		
		//menu option -End All Applications
		mntmEndAllApplications = new JMenuItem("End All Applications");
		mntmEndAllApplications.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect.stopAllDemos();
			}
		});

		//Add to menu options
		mnOptions.add(mntmRunDiagnostics);
		mnOptions.add(mntmRunCamera);
		mnOptions.add(mntmShutdownAll);
		mnOptions.add(mntmRebootAll);
		mnOptions.add(mntmDisconnectAll);
		mnOptions.add(mntmEndAllApplications);
		
		//Options for the Run are added
		for(DemoEnum enumVal : DemoEnum.values()){
			JMenuItem mntmNewMenuItem = new JMenuItem(enumVal.name());
			mnRun.add(mntmNewMenuItem);
			mntmNewMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					runDemo(enumVal);
			}});
		}
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(215,330));
		contentPane.add(tabbedPane, BorderLayout.EAST);
		
		robotPanel = new RobotPanel();
		contentPane.add(robotPanel, BorderLayout.WEST);
		
		WindowListener exitListener = new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		    	exit();
		    }
		};
		addWindowListener(exitListener);
	}
	
	private void addDemo(Demo d){
		tabbedPane.addTab(d.getDemoName(), new ApplicationPanel(d));
	}
	
	private void exit(){
		int totalTabs = tabbedPane.getTabCount();
		for(int i = 0; i < totalTabs; i++)
		{
		   Component c = tabbedPane.getTabComponentAt(i);
		   if(c instanceof ApplicationPanel){
			   ((ApplicationPanel) c).stopDemo();
		   }
		}
		if(connect != null && connect.isRunning()){
			connect.stopAll();
		}
		dispose();
    }
	
	private void connect(){
		SynchronizedConnectDialog dialog = null;
		try {
			dialog = new SynchronizedConnectDialog();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> ips = dialog.getIps();
		if(ips != null){
			if(connect ==null){
				connect = new SynchronizedConnectDemo(ips);
				mnRun.setEnabled(true);
				mnOptions.setEnabled(true);
				robotPanel.createNodes(connect);
				new Update().start();
			}
			else{
				if(!connect.isRunning()){
					for(String ip : ips){
						connect.addRobot(ip);
					}
					robotPanel.createNodes(connect);
					new Update().start();
				}
				else{
					for(String ip : ips){
						connect.addRobot(ip);
					}
					robotPanel.createNodes(connect);
				}

			}
		}
	}

	private class Update extends Thread{
		public void run(){
			while(connect.isRunning()){
				refresh();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void refresh(){
		robotPanel.update();
	}
	
	/* Execution of the demos 
	 * 
	 * The case values for the Demos have to match the order in which the
	 * demo programs are listed on DemoEnum.java
	 * 
	 */
	private void runDemo(DemoEnum enumVal){
		Demo demo = null;
		if(connect.getAllInactiveNames().size() <=0 ){
			JOptionPane.showMessageDialog(this, "There are no inactive robots.");
		}
		else{
		switch(enumVal){
		case HelloWorld:
			try{
				demo = new HelloWorldTutorial(connect);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			break;
		case HelloEarth:
			try{
				demo = new HelloEarth(connect);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			break;
		case FistBump:
			try{
				demo = new FistBumpDemo(connect);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			break;
		case BarcodeScanner:
			try {
				demo = new BarcodeScanner(connect);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Track:
			try {
				demo = new Track(connect);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case AutomatedMotion:
			try {
				demo = new AutomatedMotion(connect);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case Posture:
			try {
				demo = new Posture(connect);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case SynchronizedDance:
			try {
				demo = new SynchronizedDance(connect);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case KinectTracking:
			try {
				demo = new KinectTracking(connect);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;	
		}
		addDemo(demo);
	
	}
	}
}
