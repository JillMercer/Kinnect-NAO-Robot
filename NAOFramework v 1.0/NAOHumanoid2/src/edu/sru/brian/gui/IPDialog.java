/**
 * @File IPDialog.java
 * 
 * Provides a GUI to find NAO robots on the network.
 * It will return the IP of the robot name/IP the user selects
 * 
 * @author Brian Atwell
 * @date 2015
 * @lastModified 10/27/2015
 */

package edu.sru.brian.gui;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.JScrollPane;

import com.aldebaran.qi.CallError;

import edu.sru.brian.net.HostData;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JProgressBar;

/**
 * Displays a GUI Dialog that will find all the NAO robots on the network.
 * It returns a String that contains an IP address.
 * 
 * @author Brian Atwell
 * @since 11-05-2015
 *
 */
public class IPDialog extends JDialog {

	private JButton okButton;
	private JTable table;
	private JScrollPane scrollPane;
	private JFormattedTextField formattedIPField;
	private JFormattedTextField formattedPortField;
	private JCheckBox chckbxCustomIPName;
	private JCheckBox chckbxCustomPort;
	
	private List<HostData> hostList = new LinkedList<HostData>();

	private String selHostName=null;
	private List<HostData> selHostData=new LinkedList<HostData>();
	//private JmDNS jmDNSObj;
	
	//Thread checker timer
	private String dnsServices[] = null;
	private FindServicesThread[] findServicesThrd;
	private ThreadCheckerListener thrdChecker;
	private Timer thrdChkTimer;
	private int THREAD_CHECKER_TIMER_SPEED = 500;
	
	public static final String defaultTitle = "IP Dialog";
	public final int defaultPort;
	
	private JProgressBar progressBar;
	private JButton btnRefresh;
	
	private boolean isThreadLookingForIPs=false;
	
	/**
	 * Action Listener that is called by a swing timer to get data from the work threads.
	 * 
	 * @author Brian Atwell
	 *
	 */
	class ThreadCheckerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			boolean isDead = true;
			
			//System.out.println("findServicesThrd.isPaused():"+findServicesThrd.isAlive());
			for(int i=0; i < findServicesThrd.length; i++)
			{
				if(!findServicesThrd[i].isAlive() && !findServicesThrd[i].isProcessed())
				{
					System.out.println("Thread break!");
					List<HostData> list;
					Iterator<HostData> iter;
					HostData data;
					DefaultTableModel tableModel;
					
					tableModel = (DefaultTableModel) table.getModel();
					list = findServicesThrd[i].getData();
					iter = list.iterator();
					
					while(iter.hasNext())
					{
						data = iter.next();
						tableModel.addRow(new Object[]{data.getType(), data.getName(),data.getPort(), data.getFirstIPv4Address(), data.getFirstIPv6Address(), data});
						hostList.add(data);
					}
					
					System.out.println("findServicesThread["+i+"]:"+findServicesThrd[i].getServices()+": Stopped");
					
				}
				else
				{
					isDead=false;
				}
			}
			
			/**
			 * If the threads are all done stop the timer.
			 */
			if(isDead)
			{
				isThreadLookingForIPs=false;
				System.out.println("Timer is stopped!");
				onFinishedLoadIPs();
				thrdChkTimer.stop();
			}
		}
		
	}
	
	/**
	 * Use JmDNS to find services in the lServices string.
	 *
	 */
	class FindServicesThread extends Thread {
		private List<HostData> list = new LinkedList<HostData>();
		private JmDNS jmDNSObj;
		private String services;
		private boolean processed=false;
		
		public static final int MAX_MISS_COUNT=4;
		public static final int DELAY_SPEED=250;
		
		
		public FindServicesThread(String lServices)
		{
			services = lServices;
			try {
				jmDNSObj = JmDNS.create();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			
			ServiceInfo[] serviceInfos;
			
			System.out.println("Loopback HostName: "+InetAddress.getLoopbackAddress().getHostName());
			
			serviceInfos = jmDNSObj.list(this.services);
			
			for (ServiceInfo info : serviceInfos) {
				//tableModel.addRow(new Object[]{info.getType(), info.getName(),info.getPort(), info.getInet4Addresses()[0].getHostAddress()});
				this.list.add(new HostData(info.getType(), info.getName(),info.getPort(), info.getInet4Addresses(), info.getInet6Addresses()));
			}
			
			super.run();
			
			System.out.println("Thread "+services+" Stopped!");
			
		}
		
		public List<HostData> getData()
		{
			processed=true;
			return list;
		}
		
		public boolean isProcessed()
		{
			return processed;
		}
		
		public String getServices()
		{
			return services;
		}
		
	}
	
	// Selects multi selection
	// Was going to implement a custom multiselect algorithm
	public void setQuickSelect(boolean isMultiSelect)
	{		
		if(isMultiSelect)
		{
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}
		else
		{
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}
	
	/**
	 * Validate the Title passed in and return title
	 * @param title
	 * @return
	 */
	public static String validateTitle(String title)
	{
		if(title == null || title.isEmpty())
		{
			title = defaultTitle;
		}
		
		return title;
	}
	
	/**
	 * Creates an IP Dialog box and returns the dialog box object.
	 * This is advantage over ShowJMDNSIPDialog because it runs threads
	 * in the background until you show the dialog.
	 * @param services
	 * @param isMultiSelect
	 * @param lDefaultPort
	 * @return
	 */
	public static IPDialog SetupJMDNSIPDialog(String[] services, boolean isMultiSelect, int lDefaultPort)
	{
		IPDialog ipDialog = new IPDialog(services,lDefaultPort);
		ipDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		ipDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ipDialog.setQuickSelect(isMultiSelect);
		return ipDialog;
	}
	
	/**
	 * Creates an IP Dialog box and returns the dialog box object.
	 * This is advantage over ShowJMDNSIPDialog because it runs threads
	 * in the background until you show the dialog.
	 * @param services
	 * @param dialog
	 * @param isMultiSelect
	 * @param title
	 * @return
	 */
	public static IPDialog SetupJMDNSIPDialog(String[] services, Dialog dialog, boolean isMultiSelect, int lDefaultPort, String title)
	{
		title = validateTitle(title);
		IPDialog ipDialog = new IPDialog(services, dialog, lDefaultPort, title);
		ipDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		ipDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ipDialog.setQuickSelect(isMultiSelect);
		if(dialog.getHeight() < ipDialog.getHeight())
		{
			ipDialog.setLocationRelativeTo(null);
			ipDialog.setLocation(dialog.getLocationOnScreen().x, dialog.getLocationOnScreen().y+20);
		}
		return ipDialog;
	}
	
	/**
	 * Creates an IP Dialog box and returns the dialog box object.
	 * This is advantage over ShowJMDNSIPDialog because it runs threads
	 * in the background until you show the dialog.
	 * @param services
	 * @param frame
	 * @param isMultiSelect
	 * @param title
	 * @return
	 */
	public static IPDialog SetupJMDNSIPDialog(String[] services, Frame frame, boolean isMultiSelect, int lDefaultPort, String title)
	{
		title = validateTitle(title);
		IPDialog ipDialog = new IPDialog(services, frame, lDefaultPort, title);
		ipDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		ipDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ipDialog.setQuickSelect(isMultiSelect);
		if(frame.getHeight() < ipDialog.getHeight())
		{
			ipDialog.setLocationRelativeTo(null);
			ipDialog.setLocation(frame.getLocationOnScreen().x, frame.getLocationOnScreen().y+30);
		}
		return ipDialog;
	}
	
	/**
	 * Creates an IP Dialog box and returns the dialog box object.
	 * This is advantage over ShowJMDNSIPDialog because it runs threads
	 * in the background until you show the dialog.
	 * @param services
	 * @param window
	 * @param isMultiSelect
	 * @param lDefaultPort
	 * @param title
	 * @return
	 */
	public static IPDialog SetupJMDNSIPDialog(String[] services, Window window, boolean isMultiSelect, int lDefaultPort, String title)
	{
		title = validateTitle(title);
		IPDialog ipDialog = new IPDialog(services, window, lDefaultPort, title);
		ipDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		ipDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ipDialog.setQuickSelect(isMultiSelect);
		if(window.getHeight() < ipDialog.getHeight())
		{
			ipDialog.setLocationRelativeTo(null);
			ipDialog.setLocation(window.getLocationOnScreen().x, window.getLocationOnScreen().y+30);
		}
		return ipDialog;
	}
	
	
	/**
	 * Display IP dialog and return ip when finished
	 * @return
	 */
	public static List<HostData> ShowJMDNSIPDialog(String[] services, boolean isMultiSelect, int lDefaultPort) {
		
		IPDialog dialog = SetupJMDNSIPDialog(services, isMultiSelect, lDefaultPort);
		dialog.setVisible(true);
		return dialog.getSelectedHostData();
	}
	
	/**
	 * Display IP dialog and return ip when finished.
	 * Set dialog and title.
	 * 
	 * 
	 * @param dialog
	 * @param title
	 * @return
	 */
	public static List<HostData> ShowJMDNSIPDialog(String[] services, Dialog dialog, boolean isMultiSelect, int lDefaultPort, String title) {
		IPDialog ipDialog;
		ipDialog = SetupJMDNSIPDialog(services, dialog, isMultiSelect, lDefaultPort, title);
		ipDialog.setVisible(true);
		return ipDialog.getSelectedHostData();
	}
	
	/**
	 * Display IP dialog and return ip when finished.
	 * Set frame and title.
	 * 
	 * @param frame
	 * @param title
	 * @return
	 */
	public static List<HostData> ShowJMDNSIPDialog(String[] services, Frame frame, boolean isMultiSelect, int lDefaultPort, String title) {
		IPDialog ipDialog;
		ipDialog = SetupJMDNSIPDialog(services, frame, isMultiSelect, lDefaultPort, title);
		ipDialog.setVisible(true);
		return ipDialog.getSelectedHostData();
	}
	
	/**
	 * Display IP dialog and return ip when finished.
	 * Set window and title.
	 * 
	 * @param window
	 * @param title
	 * @return
	 */
	public static List<HostData> ShowJMDNSIPDialog(String[] services, Window window, boolean isMultiSelect, int lDefaultPort, String title) {
		IPDialog ipDialog;
		ipDialog = SetupJMDNSIPDialog(services, window, isMultiSelect, lDefaultPort, title);
		ipDialog.setVisible(true);
		return ipDialog.getSelectedHostData();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String[] services= {CommonServices.NAOQI, "_nvstream._tcp.local."}; 
		try {
			JFrame testFrame = new JFrame();
			testFrame.setVisible(true);
			testFrame.setBounds(0, 0, 500,500);
			IPDialog dialog = SetupJMDNSIPDialog(services, testFrame, true, 9559, "");
			
			//dialog.addTestDataToTable();
			
			dialog.setVisible(true);
			System.out.println("Host Data: "+dialog.getSelectedHostData());
			System.exit(0);
			//dialog.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<HostData> getSelectedHostData() {
		return selHostData;
	}

	public void initializeJMDNS() {

		int subStrIndex;
		
		
		findServicesThrd = new FindServicesThread[dnsServices.length];
		
		thrdChecker = new ThreadCheckerListener();
		
		thrdChkTimer = new Timer(THREAD_CHECKER_TIMER_SPEED,thrdChecker);
		
		thrdChkTimer.setInitialDelay(THREAD_CHECKER_TIMER_SPEED);
		
		thrdChkTimer.setCoalesce(true);
		
		startLookingForIPs();
	}

	/**
	 * Start of the main GUI code
	 * 
	 */
	
	/**
	 * Create the dialog.
	 * @wbp.parser.constructor
	 */
	public IPDialog(String[] services, int lDefaultPort) {
		super();
		this.defaultPort = lDefaultPort;
		this.dnsServices = services;
		initializeGUI(null);
	}
	
	public IPDialog(String[] services, Dialog dialog, int lDefaultPort, String title) {
		super(dialog, title);
		this.defaultPort = lDefaultPort;
		this.dnsServices = services;
		initializeGUI(title);
	}
	
	public IPDialog(String[] services, Frame frame, int lDefaultPort, String title) {
		super(frame, title);
		this.defaultPort = lDefaultPort;
		this.dnsServices = services;
		initializeGUI(title);
	}
	
	public IPDialog(String[] services, Window window, int lDefaultPort, String title) {
		super(window, title);
		this.defaultPort = lDefaultPort;
		this.dnsServices = services;
		initializeGUI(title);
	}
	
	public void initializeGUI(String title)
	{
		if(title == null || title.isEmpty())
		{
			this.setTitle("DNS IP getter");
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
					System.out.println("window Closed!");
					/*
					try {
						int length;
						length = jmdnsList.size();
						for(int i=0; i<length; i++)
						{
							jmdnsList.get(i).close();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					*/
					thrdChkTimer.stop();
					System.out.println("Timer and thread Stopped!");
					setVisible(false);

			}
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("window Closing!");
				
			}
		});
		setBounds(100, 100, 650, 349);
		this.setMinimumSize(new Dimension(480, 302));
		getContentPane().setLayout(new BorderLayout());
			//contentPanel.add(table);
		formattedIPField = new JFormattedTextField();
		chckbxCustomPort = new JCheckBox("Custom Port");
		chckbxCustomIPName = new JCheckBox("Custom IP");
		formattedPortField = new JFormattedTextField();
			
			JPanel buttonPanel = new JPanel();
			getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			
			progressBar = new JProgressBar();
			buttonPanel.add(progressBar);
			
			progressBar.setIndeterminate(true);
			progressBar.setToolTipText("loading...");
			
			btnRefresh = new JButton("Refresh");
			btnRefresh.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(!isThreadLookingForIPs)
					{
						startLookingForIPs();
					}
				}
			});
			buttonPanel.add(btnRefresh);
			
			okButton = new JButton("OK");
			buttonPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					onOkBtnClicked();

				}
			});
			
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
			
			JButton cancelButton = new JButton("Cancel");
			buttonPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					onCancelBtnClicked();
				}
			});
			cancelButton.setActionCommand("Cancel");
			
			//Button Groups
			
			ButtonGroup group = new ButtonGroup();
			
			
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			scrollPane = new JScrollPane();
			panel.add(scrollPane);
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					jtableMouseButtonLeftClick(e);
				}
			});
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setModel( new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Type", "Name", "Port", "IPv4", "IPv6"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setPreferredWidth(110);
			table.getColumnModel().getColumn(1).setPreferredWidth(120);
			table.getColumnModel().getColumn(1).setMinWidth(12);
			table.getColumnModel().getColumn(2).setPreferredWidth(44);
			table.getColumnModel().getColumn(2).setMinWidth(40);
			table.getColumnModel().getColumn(2).setMaxWidth(45);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			table.getColumnModel().getColumn(3).setMinWidth(100);
			table.getColumnModel().getColumn(3).setMaxWidth(105);
			table.getColumnModel().getColumn(4).setPreferredWidth(255);
			table.getColumnModel().getColumn(4).setMaxWidth(295);
			
			//table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			
			scrollPane.setViewportView(table);
			
			JPanel customIPPanel = new JPanel();
			panel.add(customIPPanel, BorderLayout.SOUTH);
			
			chckbxCustomIPName.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					onCustomIPStateChanged(e.getStateChange()==ItemEvent.SELECTED);
				}
			});
			customIPPanel.add(chckbxCustomIPName);
			
			formattedIPField.setColumns(10);
			formattedIPField.setEnabled(false);
			customIPPanel.add(formattedIPField);
			
			
			chckbxCustomPort.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					onCustomPortStateChanged(e.getStateChange()==ItemEvent.SELECTED);
				}
			});
			customIPPanel.add(chckbxCustomPort);
			
			formattedPortField.setColumns(10);
			formattedPortField.setEnabled(false);
			customIPPanel.add(formattedPortField);
			
			
			//pack();
			initializeJMDNS();
	}
	
	public void jtableMouseButtonLeftClick(MouseEvent e)
	{
		if(e.getClickCount()>= 2)
		{
			onOkBtnClicked();
		}
	}
	
	public void startLookingForIPs()
	{
		hostList.clear();
		
		for(int i=0; i < dnsServices.length; i++)
		{
			findServicesThrd[i] = new FindServicesThread(dnsServices[i]);
		}
		
		//table
		((DefaultTableModel)table.getModel()).getDataVector().clear();
		
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		int rowCount = dm.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
		//table.updateUI();
		
		isThreadLookingForIPs=true;
		
		for(int i=0; i < findServicesThrd.length; i++)
		{
			findServicesThrd[i].start();
		}
		
		thrdChkTimer.start();
		onLoadIPs();
	}
	
	public void onLoadIPs()
	{
		progressBar.setIndeterminate(true);
		progressBar.setVisible(true);
		btnRefresh.setEnabled(false);
	}
	
	public void onFinishedLoadIPs()
	{
		progressBar.setVisible(false);
		progressBar.setIndeterminate(false);
		btnRefresh.setEnabled(true);
	}
	
	private void addTestDataToTable()
	{
		HostData data;
		DefaultTableModel tableModel;
		
		tableModel = (DefaultTableModel) table.getModel();
		
		byte[] bytev4IP = {(byte)255,(byte)255,(byte)255,(byte)255};
		byte[] bytev6IP = new byte[16];
		
		for(int i=0; i<16; i++)
		{
			bytev6IP[i]=119;
		}
		
		Inet4Address[] ipv4 = new Inet4Address[1];
		Inet6Address[] ipv6 = new Inet6Address[1];
		try {
			ipv4[0] = (Inet4Address) InetAddress.getByAddress(bytev4IP);
			ipv6[0] = (Inet6Address) InetAddress.getByAddress(bytev6IP);
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
		
		
		data= new HostData("Test.tcp.local.", "TestClient", 65535, ipv4, ipv6);
		
		tableModel.addRow(new Object[]{data.getType(), data.getName(),data.getPort(), data.getFirstIPv4Address(), data.getFirstIPv6Address(), data});
	}
	
	public void onCustomPortStateChanged(boolean isSelected) {
		if(isSelected)
		{
			formattedPortField.setEnabled(true);
		}
		else
		{
			formattedPortField.setEnabled(false);
		}
	}
	
	public void onCustomIPStateChanged(boolean isSelected) {
		if(isSelected)
		{
			formattedIPField.setEnabled(true);
			chckbxCustomPort.setSelected(true);
		}
		else
		{
			formattedIPField.setEnabled(false);
		}
	}
	
	public void onOkBtnClicked() {
		int selRow[];
		selHostName="";
		HostData tempData;
		
		if(chckbxCustomIPName.isSelected())
		{
			String byteStrs[];
			byte byteAry[];
			Inet4Address[] ip4addr = new Inet4Address[1];
			Inet6Address[] ip6addr = null;
			
			byteAry = new byte[4];
			
			//properIPPort="tcp://";
			//selHostName=formattedIPField.getText();
			//properIPPort+=":";
			//properIPPort+=formattedPortField.getText();
			//Inet4Address.
			
			byteStrs = formattedIPField.getText().split(",");
			
			for(int i=0; i<byteStrs.length; i++)
			{
				byteAry[i] = Byte.parseByte(byteStrs[i]);
			}
			
			try {
				ip4addr[0]=(Inet4Address)Inet4Address.getByAddress(byteAry);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			selHostData.clear();
			
			selHostData.add(new HostData("", "", Integer.parseUnsignedInt(formattedPortField.getText()), ip4addr, ip6addr));
		}
		else
		{
			//selRow = table.getSelectedRow();
			selRow = table.getSelectedRows();
			
			System.out.println(selRow);
			
			if(selRow != null && selRow.length > 0)
			{
				TableModel model;
				model = table.getModel();
				//properIPPort="tcp://";
				
				selHostData.clear();
				
				for(int i=0; i<selRow.length; i++)
				{
					selHostName=""+model.getValueAt(selRow[i], 1);
					
					//Get HostData
					if(chckbxCustomPort.isSelected())
					{
						tempData = HostData.findByName(hostList.iterator(), selHostName);
						selHostData.add(new HostData(tempData.getType(), tempData.getName(), Integer.parseInt(formattedPortField.getText()), tempData.getIPv4Addresses(), tempData.getIPv6Addresses()));
						
					}
					else
					{
						selHostData.add( HostData.findByName(hostList.iterator(), selHostName));
					}
				}
			}
			
		}
		setVisible(false);

	}
	
	public void onCancelBtnClicked() {
		selHostData.clear();
		setVisible(false);
	}

}
