package edu.sru.thangiah.nao.demo.gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import edu.sru.thangiah.nao.connection.Robot;
import edu.sru.thangiah.nao.connection.SynchronizedConnect;
import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;

import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RobotPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private JTree robotTree;
	private DefaultTreeModel model;
	private SynchronizedConnectDemo connect;
	private DefaultMutableTreeNode top;
	private ArrayList<RobotNode> nodes;
	JButton endApp, reboot, disconnect, shutdown;
	private Robot selected;
	
	public RobotPanel() {
		selected = null;
		nodes = new ArrayList<RobotNode>();
		top = new DefaultMutableTreeNode("Robots");
		robotTree = new JTree(top);
		robotTree.setShowsRootHandles(true);
		model = (DefaultTreeModel) robotTree.getModel();
		top = (DefaultMutableTreeNode)model.getRoot();
		JScrollPane treeView = new JScrollPane(robotTree);
		treeView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		treeView.setMinimumSize(new Dimension(335,315));
		treeView.setPreferredSize(new Dimension(335,315));
		setMinimumSize(new Dimension(350,330));
		setPreferredSize(new Dimension(350, 406));
		add(treeView);
		
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(335, 75));
		panel.setPreferredSize(new Dimension(335, 75));
		add(panel);
		
		endApp = new JButton("End Application");
		endApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				endApp();
			}
		});
		panel.add(endApp);
		
		disconnect = new JButton("Disconnect");
		disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disconnect();
				createNodes(connect);
			}
		});
		panel.add(disconnect);
		
		reboot = new JButton("Reboot");
		reboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reboot();
				createNodes(connect);
			}
		});
		panel.add(reboot);
		
		shutdown = new JButton("Shutdown");
		shutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shutdown();
				createNodes(connect);
			}
		});
		panel.add(shutdown);
		
		endApp.setEnabled(false);
	   	disconnect.setEnabled(false);
    	reboot.setEnabled(false);
    	shutdown.setEnabled(false);
		robotTree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		    	treeListen();
		    }
		});
	}
	
	private void shutdown(){
		if(selected.demoRunning()){
			connect.stopDemo(selected.name());		
		}
		connect.shutdown(selected.name());
    	endApp.setEnabled(false);
    	disconnect.setEnabled(false);
    	reboot.setEnabled(false);
    	shutdown.setEnabled(false);
	}
	
	private void reboot(){
		if(selected.demoRunning()){
			connect.stopDemo(selected.name());
		}
		connect.reboot(selected.name());
    	endApp.setEnabled(false);
    	disconnect.setEnabled(false);
    	reboot.setEnabled(false);
    	shutdown.setEnabled(false);
	}
	
	private void disconnect(){
		if(selected.demoRunning()){
			connect.stopDemo(selected.name());
		}
		connect.stop(selected.name());
    	endApp.setEnabled(false);
    	disconnect.setEnabled(false);
    	reboot.setEnabled(false);
    	shutdown.setEnabled(false);
	}
	
	private void endApp(){
		if(selected.demoRunning()){
			connect.stopDemo(selected.name());
		}
		endApp.setEnabled(false);
	}
	
	public void createNodes(SynchronizedConnectDemo connect){
		this.connect = connect;
		top.removeAllChildren();
		nodes.clear();
		while(connect.getAllRobots() == null){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(Robot r : connect.getAllRobots()){
			RobotNode node = new RobotNode(r);
			nodes.add(node);
			top.add(node);
		}
		model.reload(top);
		robotTree.setShowsRootHandles(true);
		robotTree.expandRow(0);
		robotTree.expandRow(1);	
	}
	
	private class RobotNode extends DefaultMutableTreeNode{
		DefaultMutableTreeNode ip, battery, demoRunning, demoName, demoId;
		private boolean runDemo = false;
		Robot r;
		private RobotNode(Robot r){
			super(r.name());
			this.r = r;
			ip = new DefaultMutableTreeNode("IP: " + r.ip());
			battery = new DefaultMutableTreeNode("BATTERY: " + r.batteryCharge() + "%");
			runDemo = r.demoRunning();
			demoRunning = new DefaultMutableTreeNode("DEMO RUNNING: " + r.demoRunning());
			demoName = new DefaultMutableTreeNode("DEMO NAME: " + r.demoName() + "                                                                                       ");
			demoId = new DefaultMutableTreeNode("DEMO ID: " + r.demoId());
			add(ip);
			add(battery);
			add(demoRunning);
			add(demoName);
			add(demoId);
		}
		
		private Robot getRobot(){
			return r;
		}
		
		private void update(){
			if(r.isRunning()){
			battery.setUserObject("BATTERY: " + r.batteryCharge() + "%");
			demoRunning.setUserObject("DEMO RUNNING: " + r.demoRunning());
			demoName.setUserObject("DEMO NAME: " + r.demoName());
			demoId.setUserObject("DEMO ID: " + r.demoId());
			if(runDemo != r.demoRunning()){
				runDemo = r.demoRunning();
				model.reload(this);
			}
			}
			else{
				this.removeAllChildren();
				this.removeFromParent();
				model.reload(top);
			}
		}
		
		private boolean isRunning(){
			return r.isRunning();
		}

	}
	
	private void treeListen(){
		 DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                 robotTree.getLastSelectedPathComponent();

		 	if (node == null || node.isRoot()){
		 		selected = null;
		 		endApp.setEnabled(false);
		 		disconnect.setEnabled(false);
		 		reboot.setEnabled(false);
		 		shutdown.setEnabled(false);
		 	}
		 	else{
		 		if(!(node instanceof RobotNode)){
		 			node = (DefaultMutableTreeNode) node.getParent();
		 		}
		 		selected = ((RobotNode) node).getRobot();
		 		if(selected.demoRunning()){
		 			endApp.setEnabled(true);
		 			disconnect.setEnabled(true);
		 			reboot.setEnabled(true);
		 			shutdown.setEnabled(true);
		 		}
		 		else{
		 			endApp.setEnabled(false);
		 			disconnect.setEnabled(true);
		 			reboot.setEnabled(true);
		 			shutdown.setEnabled(true);
		 		}
		 	}
	}
	public void update(){
		for(int i = 0; i < nodes.size(); i++){
			RobotNode node = nodes.get(i);
			if(node.isRunning()){
				node.update();
			}
			else{
				top.remove(node);
				nodes.remove(node);
			}
		}
		treeListen();
	}
	
}
		