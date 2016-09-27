package edu.sru.brian.tictactoegame.vision;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;

import com.aldebaran.demo.RobotIP;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.brian.gui.CommonServices;
import edu.sru.brian.gui.IPDialog;
import edu.sru.thangiah.nao.connection.Connection;
import edu.sru.thangiah.nao.vision.Camera;
import edu.sru.thangiah.nao.vision.CameraImage;

/**
 * TTTGCamera Frame
 * Based off of Zach Kearney's Camera Frame
 * Edited Brian Atwell
 * Description: Creates a frame and shows the Nao's bottom camera
 * and Hough Line detection.
 *
 */
public class TTTGCameraFrame extends JFrame {

	private JPanel contentPane;
	private TTTGCamera camera;
	private StreamPanel camPane;
	private StreamPanel linePane;
	private BottomCameraStream camStream;
	private ImageStreamManager camManager;
	private HoughLineStream lineStream;
	private ALMotion motion;
	private Session session;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					Session session;
					IPDialog ipDialog;
					Connection connection;
					String ipStr="";
					
					String[] services= {CommonServices.NAOQI}; 
					ipDialog = IPDialog.SetupJMDNSIPDialog(services, false, 9559);
					
					connection = new Connection();
					
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
			        	System.out.println("Failed to run GUI");
			        }
			        else
			        {
			        	TTTGCameraFrame frame = new TTTGCameraFrame(session);
						frame.setVisible(true);
			        }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TTTGCameraFrame(Session sess) {
		session = sess;
		try {
			camera = new TTTGCamera(session);
			camera.setResolution(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		camStream = new BottomCameraStream(camera);
		camManager = new ImageStreamManager();
		
		lineStream = new HoughLineStream();
		contentPane.setLayout(null);
		
		try {
			camera.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		camPane = new StreamPanel();
		contentPane.add(camPane);
		linePane = new StreamPanel();
		contentPane.add(linePane);
		
		System.out.println("width: "+camera.getWidth()+" height: "+camera.getHeight());
		
		camPane.setBounds(0, 0, camera.getWidth(), camera.getHeight());
		
		this.setBounds(0, 0, camera.getWidth()*2, camera.getHeight()+50);
		linePane.setBounds(camera.getWidth(), 0, camera.getWidth(), camera.getHeight());
		
		camPane.setImageStream(camStream);
		linePane.setImageStream(lineStream);
		
		camManager.setSource(camPane.getPanelImageStream());
		camManager.enqueue(linePane.getPanelImageStream());
		
		camManager.startThreaded(250);
		
	}

}
