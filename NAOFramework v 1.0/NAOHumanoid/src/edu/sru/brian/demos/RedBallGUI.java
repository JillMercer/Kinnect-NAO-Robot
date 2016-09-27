package edu.sru.brian.demos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

import com.aldebaran.demo.RobotIP;
import com.aldebaran.qi.Session;

import edu.sru.brian.gui.CommonServices;
import edu.sru.brian.gui.IPDialog;
import edu.sru.brian.nao.SeekRedBallModule;
import edu.sru.brian.nao.StopListener;
import edu.sru.thangiah.nao.connection.Connection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Contains a GUI with a single button to run a red ball demo.
 * The actual red ball code is contained in SeekRedBallModule.java and RedBallTrackerModule.java.
 * @see edu.sru.brian.nao.SeekRedBallModule.java for a better description.
 * 
 * 
 * @author Brian Atwell
 * @date 10-2015
 *
 */
public class RedBallGUI extends JFrame implements StopListener {

	private JPanel contentPane;
	private SeekRedBallModule redBallMod;
	private Connection connection;
	private String ipStr;
	
	// GUI variables
	final JButton btnRedBallSeek;
	
	private IPDialog ipDialog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RedBallGUI frame = new RedBallGUI();
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
	public RedBallGUI() {
		
		addWindowListener(new WindowListener() {
			@Override
			
			/**
			 * Catch Initialization of window
			 * 
			 * Initialize Robot
			 */
			public void windowOpened(WindowEvent e) {
				initializeNAO();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			/**
			 * Catch Uninitialization of window
			 * 
			 * Uninitialize Robot
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				uninitializeNAO();
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * Setup GUI
		 */
		setTitle("Red Ball Demo");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		btnRedBallSeek = new JButton("Start Red Ball Seeker");
		btnRedBallSeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRunBtnClick();
			}
		});
		contentPane.add(btnRedBallSeek, BorderLayout.CENTER);
		
		//initializeNAO();
	}
	
	/**
	 * Initialize NAO Robot
	 */
	public void initializeNAO() {
		Session session;
		
		String[] services= {CommonServices.NAOQI}; 
		ipDialog = IPDialog.SetupJMDNSIPDialog(services, this, false, 9559, "");
		
		if(connection == null)
		{
			connection = new Connection();
		}
		
		//ipStr = JOptionPane.showInputDialog(null, "Enter IP and port address: ", "Enter IP", JOptionPane.INFORMATION_MESSAGE);
		
		// Display Dialog to show NAO robots and get IP.
		ipDialog.setVisible(true);
		
		if(ipDialog.getSelectedHostData().size() > 0)
		{
			ipStr = ipDialog.getSelectedHostData().get(0).getFirstIPv4Address();
			System.out.println("IP: "+ipStr);
		}
		
		if(ipStr == null || ipStr.isEmpty())
    	{
    		
    		ipStr = RobotIP.ip;
    	}
        
    	connection.connectToNao(ipStr);
        
        session = connection.getSession();
		//sessionVirt = new Session();d
        
        if(session == null || !session.isConnected()) {
        	int result;
        	result = JOptionPane.showConfirmDialog(null, "Could not connect to Robot!!\r\nAt "+ipStr+
        			"\n Would you like to continue?", "Red Ball Seek GUI", JOptionPane.YES_NO_OPTION);
        	
        	if(result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION || result == JOptionPane.CANCEL_OPTION)
        	{
        		System.out.println("result: "+result);
        		System.exit(0);
        		// End program
        	}
        	
        	// Else continue in GUI test mode
        	// No connection to the robot simply display the GUI
        	String origTitle;
        	origTitle = getTitle();
        	this.setTitle(origTitle+" GUI Test Mode");
        	
        	
        	
            
        }
        else
        {
        	redBallMod = new SeekRedBallModule(session);
        	redBallMod.addStopListener(this);
        }
		
		
	}
	
	/**
	 * Uninitialize NAO robot
	 */
	public void uninitializeNAO() {
		if(connection.isConnected())
		{
			redBallMod.exit();
		}
	}
	
	/**
	 * Called when the Run button is clicked
	 * Will start/stop seek red ball module demo
	 */
	public void onRunBtnClick() {
		if(redBallMod.isRunning())
		{
			btnRedBallSeek.setText("Start Red Ball Seeker");
			redBallMod.stop();
		}
		else
		{
			btnRedBallSeek.setText("stop");
			redBallMod.run();
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		btnRedBallSeek.setText("Start Red Ball Seeker");
		if(redBallMod.isRunning())
		{
			redBallMod.stop();
		}
	}

	
}
