package edu.sru.brian.tictactoegame.vision;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;

import com.aldebaran.demo.RobotIP;
import com.aldebaran.qi.Session;

import edu.sru.brian.gui.CommonServices;
import edu.sru.brian.gui.IPDialog;
import edu.sru.brian.nao.SeekRedBallModule;
import edu.sru.thangiah.nao.connection.Connection;

import java.awt.FlowLayout;
import java.util.List;

/**
 * NAOCameraStreamframe
 * @author bsa7332
 * Creates a Frame with the Nao camerea stream
 *
 */
public class NAOCameraStreamFrame extends JFrame {

	private JPanel contentPane;
	private StreamPanel topCamPane;
	private StreamPanel botCamPane;
	private StreamPanel topLinePane;
	private StreamPanel botLinePane;
	
	private BottomCameraStream botCameraStream;
	private TopCameraStream topCameraStream;
	
	private ImageStreamManager topCamManager;
	private ImageStreamManager botCamManager;
	
	private HoughLineStream topLineStream;
	private HoughLineStream botLineStream;
	
	
	private Connection connection;
	private String ipStr;	
	private IPDialog ipDialog;
	private TTTGCamera camera;
	
	public static final long DELAY_TIME = 500;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					NAOCameraStreamFrame frame = new NAOCameraStreamFrame();
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
	public NAOCameraStreamFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		topCamPane = new StreamPanel();
		contentPane.add(topCamPane);
		
		botCamPane = new StreamPanel();
		contentPane.add(botCamPane);
		
		topLinePane = new StreamPanel();
		contentPane.add(topLinePane);
		
		botLinePane = new StreamPanel();
		contentPane.add(botLinePane);
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
        	// Initialize Camera stuff
        	initializeNAOModules();
        }
	}
	
	/**
	 * Initialize the NAO modules
	 */
	public void initializeNAOModules()
	{
		Session session;
		
		session = connection.getSession();
		
		try {
			camera = new TTTGCamera(session);
			
			botCameraStream = new BottomCameraStream(camera);
			topCameraStream = new TopCameraStream(camera);
			
			botCamPane.setImageStream(botCameraStream);
			topCamPane.setImageStream(topCameraStream);
			
			topCamManager = new ImageStreamManager();
			botCamManager = new ImageStreamManager();
			
			topLineStream = new HoughLineStream();
			botLineStream = new HoughLineStream();
			
			topLinePane.setImageStream(topLineStream);
			botLinePane.setImageStream(botLineStream);
			
			topCamManager.setSource(topCamPane.getPanelImageStream());
			botCamManager.setSource(botCamPane.getPanelImageStream());
			
			topCamManager.enqueue(topLinePane.getPanelImageStream());
			botCamManager.enqueue(botLinePane.getPanelImageStream());
			
			topCamManager.startThreaded(DELAY_TIME);
			botCamManager.startThreaded(DELAY_TIME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Uninitialize NAO robot
	 */
	public void uninitializeNAO() {
		if(connection.isConnected())
		{
			try {
				topCamManager.stopThread();
				botCamManager.stopThread();
				camera.exit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
