/**
 * @File NAOIPDialogV4.java
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.JScrollPane;

import com.aldebaran.qi.CallError;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JProgressBar;

/**
 * Displays a GUI Dialog that will find all the NAO robots on the network.
 * It returns a String that contains an IP address.
 * 
 * @author Brian Atwell
 * @since 11-05-2015
 *
 */
public class NAOIPDialogV4 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JTable table;
	private JScrollPane scrollPane;
	private JFormattedTextField formattedIPField;
	private JFormattedTextField formattedPortField;
	private JCheckBox chckbxCustomIPName;
	private JCheckBox chckbxCustomPort;
	//private List<String> serviceTypesList;
	//private List<JmDNS> jmdnsList;
	private MyServiceListener myServiceListener;
	private MySerivceTypeListener myServiceTypeListener;
	private String properIPPort=null;
	private String localHostName="";
	//private JmDNS jmDNSObj;
	
	//Thread checker timer
	private static final String dnsServices[] = {"_naoqi._tcp.local."};
	private FindServicesThread[] findServicesThrd;
	private ThreadCheckerListener thrdChecker;
	private Timer thrdChkTimer;
	private int THREAD_CHECKER_TIMER_SPEED = 500;
	
	public static final String panelTitle = "NAOIPDialog";
	
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
					ArrayList<HostData> list;
					Iterator<HostData> iter;
					HostData data;
					DefaultTableModel tableModel;
					
					tableModel = (DefaultTableModel) table.getModel();
					list = findServicesThrd[i].getData();
					iter = list.iterator();
					
					while(iter.hasNext())
					{
						data = iter.next();
						tableModel.addRow(new Object[]{data.getType(), data.getName(),data.getPort(), data.getFirstIPv4Address()});
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
	 * Class to hold Host data.
	 * Such as IPv4 addresses, IPv6 addresses, Name, type, and port.
	 * 
	 * @author Brian Atwell
	 *
	 */
	class HostData
	{
		private String Type;
		private String Name;
		private int Port;
		private Inet4Address[] IPv4;
		private Inet6Address[] IPv6;
		
		public HostData(String Type, String Name, int Port, Inet4Address[] IPv4, Inet6Address[] IPv6)
		{
			this.Type = Type;
			this.Name = Name;
			this.Port = Port;
			this.IPv4 = IPv4;
			this.IPv6 = IPv6;
		}

		public String getType() {
			return Type;
		}

		public String getName() {
			return Name;
		}

		public int getPort() {
			return Port;
		}

		public Inet4Address[] getIPv4Addresses() {
			return IPv4;
		}
		
		public String getFirstIPv4Address() {
			if(IPv4.length > 0)
			{
				return IPv4[0].getHostAddress();
			}
			
			
			return null;
		}
		
		public Inet6Address[] getIPv6Addresses() {
			return IPv6;
		}
		
		public String getFirstIPv6Address() {
			
			if(IPv4.length > 0)
			{
				return IPv6[0].getHostAddress();
			}
			
			return null;
		}
		
		
	}
	
	/**
	 * Use JmDNS to find services in the lServices string.
	 * 
	 * @author Brian Atwell
	 *
	 */
	class FindServicesThread extends Thread {
		private ArrayList<HostData> list = new ArrayList<HostData>();
		private int curState=0;
		private JmDNS jmDNSObj;
		private String services;
		private boolean processed=false;
		
		private int missCount=0;
		
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
			
			//serviceTypesList = new ArrayList<String>();
			//myServiceListener = new MyServiceListener();
			//myServiceTypeListener = new MySerivceTypeListener();
			//int subStrIndex;
			ServiceInfo[] serviceInfos;
			
			System.out.println("Loopback HostName: "+InetAddress.getLoopbackAddress().getHostName());
			
			//DefaultTableModel tableModel;
			//jmdnsList.add(jmDNSTmp);
			//localHostName = jmDNSTmp.getHostName();
			//jmDNSTmp.addServiceTypeListener(myServiceTypeListener);
			
			//jmDNSTmp.addServiceListener("_naoqi._tcp.local.", myServiceListener);
			//jmDNSTmp.addServiceListener("_nao._tcp.local.", myServiceListener);
			
			serviceInfos = jmDNSObj.list(services);
			//tableModel = (DefaultTableModel) table.getModel();
			for (ServiceInfo info : serviceInfos) {
				//tableModel.addRow(new Object[]{info.getType(), info.getName(),info.getPort(), info.getInet4Addresses()[0].getHostAddress()});
				list.add(new HostData(info.getType(), info.getName(),info.getPort(), info.getInet4Addresses(), info.getInet6Addresses()));
			}
		
			/*
			subStrIndex = localHostName.lastIndexOf(".local.");
			if( subStrIndex != -1)
			{
				localHostName = localHostName.substring(0, subStrIndex);
			}
			*/
			super.run();
			
			System.out.println("Thread Stopped!");
			
		}
		
		public ArrayList<HostData> getData()
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
	
	class MySerivceTypeListener implements ServiceTypeListener {

        @Override
        public void serviceTypeAdded(ServiceEvent event) {
            System.out.println("Service type Found: " + event.getType());
            
            //DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            if(event.getType().toLowerCase().indexOf("nao") != -1)
            //if(true)
            {
            	System.out.println("NAO service found!");
            	System.out.println("Service type added: " + event.getType());
	            //serviceTypesList.add(event.getType());
	            event.getDNS().addServiceListener(event.getType(), myServiceListener);
	            
            }
        }

        /*
         * (non-Javadoc)
         * @see javax.jmdns.ServiceTypeListener#subTypeForServiceTypeAdded(javax.jmdns.ServiceEvent)
         */
        @Override
        public void subTypeForServiceTypeAdded(ServiceEvent event) {
            System.out.println("SubType for service type added: " + event.getType());
        }
    }
	
	/**
	 * A service listener for JmDNS. Will print the services found.
	 * 
	 * @author Brian Atwell
	 *
	 */
	class MyServiceListener implements ServiceListener {
		private DefaultTableModel tableModel;
		
		public MyServiceListener()
		{
			tableModel = (DefaultTableModel) table.getModel();
		}
		
        @Override
        public void serviceAdded(ServiceEvent event) {
        	Inet4Address[] inet4;
        	String hostAddress="127.0.0.1";
        	
            System.out.println("Service added   : " + event.getName() + "." + event.getType());
            //System.out.println("IP:"+event.getInfo().getInet4Addresses()[0]);
            
            
            ServiceInfo infos = event.getDNS().getServiceInfo(event.getType(), event.getName());
            
            inet4 = infos.getInet4Addresses();
          	
        	if(inet4.length > 0)
        	{
        		hostAddress = inet4[0].getHostAddress();
        		for(int i=0; i < inet4.length; i++)
        		{
        			System.out.println("inet["+i+"] = "+inet4[i].getHostAddress());
        			
        		}
        	}
            
            if(!localHostName.equals(event.getName()))
            {
            	inet4 = infos.getInet4Addresses();
            	if(inet4.length > 0)
            	{
            		hostAddress = inet4[0].getHostAddress();
            		for(int i=0; i < inet4.length; i++)
            		{
            			System.out.println("inet["+i+"] = "+inet4[i].getHostAddress());
            		}
            	}
            	/*else
            	{
            		InetAddress[] inet;
            		inet = infos.getInetAddresses();
            		if(inet.length > 0)
                	{
                		hostAddress = inet[0].getHostAddress();
                	}
            	}
            	*/
            }
            
            tableModel.addRow(new Object[]{infos.getType(), infos.getName(),infos.getPort(), hostAddress});
            System.out.println("Service added to table   : " + event.getName() + "." + event.getType());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed : " + event.getName() + "." + event.getType());
            /*
            DefaultTableModel tableModel;
        	Inet4Address[] inet4;
        	String hostAddress="127.0.0.1";
        	
			tableModel = (DefaultTableModel) table.getModel();
			
			 ServiceInfo infos = event.getDNS().getServiceInfo(event.getType(), event.getName());
			 
			 if(!localHostName.equals(event.getName()))
			 {
			 	inet4 = infos.getInet4Addresses();
			 	if(inet4.length > 0)
			 	{
			 		hostAddress = inet4[0].getHostAddress();
			 	}
			 }
			 
			 //TODO Finish this
             
        	//tableModel.getValueAt(row, 2);
			//tableModel.getValueAt(row, 3);
			 */
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
        }
    }
	
	/**
	 * Display IP dialog and return ip when finished
	 * @return
	 */
	public static String ShowJMDNSIPDialog() {
		NAOIPDialogV4 dialog = new NAOIPDialogV4();
		dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		return dialog.getProperIPPort();
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
	public static String ShowJMDNSIPDialog(Dialog dialog, String title) {
		NAOIPDialogV4 ipDialog = new NAOIPDialogV4(dialog, title);
		ipDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		ipDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		if(dialog.getHeight() < ipDialog.getHeight())
		{
			ipDialog.setLocationRelativeTo(null);
			ipDialog.setLocation(dialog.getLocationOnScreen().x, dialog.getLocationOnScreen().y+20);
		}
		ipDialog.setVisible(true);
		return ipDialog.getProperIPPort();
	}
	
	/**
	 * Display IP dialog and return ip when finished.
	 * Set frame and title.
	 * 
	 * @param frame
	 * @param title
	 * @return
	 */
	public static String ShowJMDNSIPDialog(Frame frame, String title) {
		NAOIPDialogV4 ipDialog = new NAOIPDialogV4(frame, title);
		ipDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		ipDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		if(frame.getHeight() < ipDialog.getHeight())
		{
			ipDialog.setLocationRelativeTo(null);
			ipDialog.setLocation(frame.getLocationOnScreen().x, frame.getLocationOnScreen().y+30);
		}
		ipDialog.setVisible(true);
		return ipDialog.getProperIPPort();
	}
	
	/**
	 * Display IP dialog and return ip when finished.
	 * Set window and title.
	 * 
	 * @param window
	 * @param title
	 * @return
	 */
	public static String ShowJMDNSIPDialog(Window window, String title) {
		NAOIPDialogV4 ipDialog = new NAOIPDialogV4(window, title);
		ipDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		ipDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		if(window.getHeight() < ipDialog.getHeight())
		{
			ipDialog.setLocationRelativeTo(null);
			ipDialog.setLocation(window.getLocationOnScreen().x, window.getLocationOnScreen().y+30);
		}
		ipDialog.setVisible(true);
		return ipDialog.getProperIPPort();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NAOIPDialogV4 dialog = new NAOIPDialogV4();
			dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			System.out.println("IP: "+dialog.getProperIPPort());
			System.exit(0);
			//dialog.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getProperIPPort() {
		// TODO Auto-generated method stub
		return properIPPort;
	}

	public void initializeJMDNS() {
		//serviceTypesList = new ArrayList<String>();
		//jmdnsList = new ArrayList<JmDNS>();
		myServiceListener = new MyServiceListener();
		myServiceTypeListener = new MySerivceTypeListener();
		int subStrIndex;
		
		//System.out.println("Loopback HostName: "+InetAddress.getLoopbackAddress().getHostName());
		
		findServicesThrd = new FindServicesThread[dnsServices.length];
		//findServicesThrd[0] = new FindServicesThread("_naoqi._tcp.local.");
		//findServicesThrd[1] = new FindServicesThread("_nao._tcp.local.");
		
		thrdChecker = new ThreadCheckerListener();
		
		thrdChkTimer = new Timer(THREAD_CHECKER_TIMER_SPEED,thrdChecker);
		
		thrdChkTimer.setInitialDelay(THREAD_CHECKER_TIMER_SPEED);
		
		thrdChkTimer.setCoalesce(true);
		
		startLookingForIPs();
		
		
		//JmDNS jmDNSObj;
		//DefaultTableModel tableModel;
			//jmDNSObj = JmDNS.create();
			//jmdnsList.add(jmDNSTmp);
			//localHostName = jmDNSTmp.getHostName();
			//jmDNSTmp.addServiceTypeListener(myServiceTypeListener);
			
			//jmDNSTmp.addServiceListener("_naoqi._tcp.local.", myServiceListener);
			//jmDNSTmp.addServiceListener("_nao._tcp.local.", myServiceListener);
			/*
			ServiceInfo[] serviceInfos = jmDNSTmp.list("_naoqi._tcp.local.");
			tableModel = (DefaultTableModel) table.getModel();
			for (ServiceInfo info : serviceInfos) {
				tableModel.addRow(new Object[]{info.getType(), info.getName(),info.getPort(), info.getInet4Addresses()[0].getHostAddress()});
			}
			
			serviceInfos = jmDNSTmp.list("_nao._tcp.local.");
			tableModel = (DefaultTableModel) table.getModel();
			for (ServiceInfo info : serviceInfos) {
				tableModel.addRow(new Object[]{info.getType(), info.getName(),info.getPort(), info.getInet4Addresses()[0].getHostAddress()});
			}
			*/
		
		
		/*
		try {
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			JmDNS jmDNSTmp;
			while(e.hasMoreElements())
			{
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration ee = n.getInetAddresses();
			    while (ee.hasMoreElements())
			    {
			        InetAddress i = (InetAddress) ee.nextElement();
			        if(!i.isLoopbackAddress())
			        {
			        	if(i.getClass() == Inet4Address.class)
			        	{
			        		System.out.println(""+i.getHostAddress());
			        		jmDNSTmp = JmDNS.create(i.getHostAddress());
			        		jmdnsList.add(jmDNSTmp);
			        		localHostName = jmDNSTmp.getHostName();
			        		//jmDNSTmp.addServiceTypeListener(myServiceTypeListener);
			        		jmDNSTmp.addServiceListener("_naoqi._tcp.local.", myServiceListener);
			        		jmDNSTmp.addServiceListener("_nao._tcp.local.", myServiceListener);
			        	}
			        }
			    }
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
		subStrIndex = localHostName.lastIndexOf(".local.");
		if( subStrIndex != -1)
		{
			localHostName = localHostName.substring(0, subStrIndex);
		}
	}

	/**
	 * Start of the main GUI code
	 * 
	 */
	
	/**
	 * Create the dialog.
	 */
	public NAOIPDialogV4() {
		super();
		initializeGUI(null);
	}
	
	public NAOIPDialogV4(Dialog dialog, String title) {
		super(dialog, title);
		initializeGUI(title);
	}
	
	public NAOIPDialogV4(Frame frame, String title) {
		super(frame, title);
		initializeGUI(title);
	}
	
	public NAOIPDialogV4(Window window, String title) {
		super(window, title);
		initializeGUI(title);
	}
	
	public void initializeGUI(String title)
	{
		if(title == null || title.isEmpty())
		{
			this.setTitle("NAO IP getter");
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
		setBounds(100, 100, 480, 349);
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
					int selRow;
					properIPPort="";
					
					if(chckbxCustomIPName.isSelected())
					{
						//properIPPort="tcp://";
						properIPPort=formattedIPField.getText();
						//properIPPort+=":";
						//properIPPort+=formattedPortField.getText();
					}
					else
					{
						selRow = table.getSelectedRow();
						if(selRow != -1) {
							TableModel model;
							model = table.getModel();
							//properIPPort="tcp://";
							properIPPort=""+model.getValueAt(selRow, 3);
							//properIPPort+=":";
							/*
							if(chckbxCustomPort.isSelected())
							{
								properIPPort+=formattedPortField.getText();
							}
							else
							{
								properIPPort+=model.getValueAt(selRow, 2);
							}
							*/
						}
					}
					setVisible(false);

				}
			});
			
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
			
			JButton cancelButton = new JButton("Cancel");
			buttonPanel.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
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
					mouseButtonLeftClick(e);
				}
			});
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setModel( new DefaultTableModel(
				new Object[][] {
					//{"Test", "Localhost", "63", "127.0.0.1"},
				},
				new String[] {
					"Type", "Name", "Port", "IP"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setPreferredWidth(110);
			table.getColumnModel().getColumn(1).setPreferredWidth(145);
			table.getColumnModel().getColumn(1).setMinWidth(12);
			table.getColumnModel().getColumn(3).setPreferredWidth(105);
			scrollPane.setViewportView(table);
			
			JPanel customIPPanel = new JPanel();
			panel.add(customIPPanel, BorderLayout.SOUTH);
			
			chckbxCustomIPName.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED)
					{
						formattedIPField.setEnabled(true);
						chckbxCustomPort.setSelected(true);
					}
					else
					{
						formattedIPField.setEnabled(false);
					}
				}
			});
			customIPPanel.add(chckbxCustomIPName);
			
			formattedIPField.setColumns(10);
			formattedIPField.setEnabled(false);
			customIPPanel.add(formattedIPField);
			
			
			chckbxCustomPort.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED)
					{
						formattedPortField.setEnabled(true);
					}
					else
					{
						formattedPortField.setEnabled(false);
					}
				}
			});
			customIPPanel.add(chckbxCustomPort);
			
			formattedPortField.setColumns(10);
			formattedPortField.setEnabled(false);
			customIPPanel.add(formattedPortField);
			
			
			//pack();
			initializeJMDNS();
	}
	
	public void mouseButtonLeftClick(MouseEvent e)
	{
		if(e.getClickCount()>= 2)
		{
			okButton.doClick();
		}
	}
	
	public void startLookingForIPs()
	{
		for(int i=0; i < dnsServices.length; i++)
		{
			findServicesThrd[0] = new FindServicesThread(dnsServices[i]);
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

}
