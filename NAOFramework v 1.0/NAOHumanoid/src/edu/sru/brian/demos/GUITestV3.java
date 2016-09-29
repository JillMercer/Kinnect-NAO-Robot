package edu.sru.brian.demos;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

//import com.aldebaran.demo.QiApplicationThread;
import com.aldebaran.demo.RobotIP;
//import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Future;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALColorBlobDetection;
import com.aldebaran.qi.helper.proxies.ALFaceDetection;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALTracker;
import com.aldebaran.qi.helper.proxies.ALVideoDevice;
import edu.sru.brian.gui.NAOBlobDetectionPanel;
import edu.sru.brian.nao.BlobProperties;
import edu.sru.brian.nao.NAOMath;
import edu.sru.brian.timer.ResetableActionListener;
import edu.sru.brian.timer.ResetableTimer;
import edu.sru.thangiah.nao.connection.Connection;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextField;

/**
 * My original GUI for the red ball and various other examples I tried with the robot.
 * Obsolete, use RedBallGUI.java
 * RedBallGUI uses separate classes for the redball code and have been updated. 
 * 
 * RUN AT YOUR OWN RISK.
 * 
 * @author Brian Atwell
 *
 */
public class GUITestV3 {

	private JFrame frame;
	
	private Connection connection;
	private Session session;
	//private Application app;
	private ALMemory alMemory;
    private ALMotion alMotion;
    private ALTextToSpeech tts;
    private ALTracker alTracker;
    private ALColorBlobDetection alBlobDetet;
    private ALFaceDetection alFaceDet;
    private ALBasicAwareness alBasicAwareness;
    private ALRobotPosture alPosture;
    private ALVideoDevice alVidDev;
	private BlobProperties blobProps;
	
	private BlobProperties greenBallProps;
	
	
	private static String vidModuleNameProposed="GUITestV3";
	private String vidModuleName;
	
	private Session sessionVirt;
	private ALMotion alMotionVirt;
	
	private Timer trackLHandTimer;
	private Timer trackWaitRedBallTimer;
	private Timer trackRedBallTimer;
	
	private ResetableTimer seekRedBallTimer;
	
	public final int SEEK_REDBALL_SPEED = 250;
	
	//private List<Float> redBallList;
	
	//private List<Float> handDestList;
	
	private final static List<Float> initRArm;
	
	private final static List<Float> initRArmWait;
	
	private final static List<Float> rHandRdBl;
	
	private boolean isFaceEventEnabled = false;
	
	//private Session virtSession;
	
	private JTextArea textArea;
	
    
    //private QiApplicationThread qiThread;
    
    private boolean isRunning = false;
    private JTextField dataTextField;
    
    static {
		
		initRArm = new ArrayList<Float>();
		
		initRArmWait = new ArrayList<Float>();
		
		rHandRdBl = new ArrayList<Float>();
		
		initRArm.add(0.19547684f);
		initRArm.add(0.11170106f);
		initRArm.add(1.5711995f);
		initRArm.add(0.034906592f);
		initRArm.add(1.5707966f);
		initRArm.add(1.0f);
		
		/*
		initRArmWait.add(0.5232583f);
		initRArmWait.add( 0.13293631f);
		initRArmWait.add(1.5742933f);
		initRArmWait.add(0.68059057f);
		initRArmWait.add( 1.5707961f);
		initRArmWait.add(1.0f);
		*/
		
		initRArmWait.add(0.34557524f);
		initRArmWait.add(-0.18541902f);
		initRArmWait.add(1.5707961f);
		initRArmWait.add(0.41038793f);
		initRArmWait.add(1.5707961f);
		initRArmWait.add(1.0f);
		
		rHandRdBl.add(0.46320325f);
		rHandRdBl.add(-0.3230874f);
		rHandRdBl.add(-0.011997998f);
    }
    
    public class SeekRedBallActionlistener implements ResetableActionListener {
		private List<Float> rHandList;
		//private List<Float> rShoulderList;
		private List<Float> redBallList;
		private List<Float> initRHandPos=null;
		private int curState=0;
		private float distRdBlBody=0;
		private int waitCount=0;
		
		private Timer myTimer;
		

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			float distRdBlHand;
			int distRdBlHandInt;
			
			try {
				rHandList = alMotion.getPosition("RHand", 0, true);
				redBallList=alTracker.getTargetPosition(0);
				
				
				
				if(redBallList != null && !redBallList.isEmpty())
				{
					distRdBlBody = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), 0.0f,0.0f,0.0f);
					
					switch(curState) 
					{
					case 0:
						if(distRdBlBody <= .4f)
						{
							this.initRHandPos = alMotion.getAngles("RArm", true);
							
							alTracker.setMode("Head");
							
							curState=1;
							waitCount=0;
						}
						break;
					case 1:
						++waitCount;
						
						if(waitCount == 4)
						{
							curState = 2;
							waitCount = 0;
							alMotion.setBreathEnabled("RArm", false);
							//alMotion.setIdlePostureEnabled("RArm", false);
							//alMotion.setStiffnesses("RArm", 1.0f);
							alMotion.setStiffnesses("RWristYaw", 1.0f);
							alMotion.setStiffnesses("RElbowYaw", 1.0f);
							
							alMotion.setAngles("RArm", initRArmWait, 0.3f);
							
							tts.say("Give me the red ball please");
						}
						
						if(distRdBlBody >= .6f)
						{
							//alMotion.setIdlePostureEnabled("RArm", false);
							alMotion.setAngles("RArm", this.initRHandPos, 0.25f);
							//alMotion.setStiffnesses("RArm", 0.0f);
							alMotion.setBreathEnabled("RArm", true);
							alTracker.setMode("Move");
							
							tts.say("I want the red ball");
							curState=0;
						}
						break;
					case 2:
						
						
						//rShoulderList = alMotion.getPosition("RShoulderRoll", 0, true);
						//distRdBlHand = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rShoulderList.get(0), rShoulderList.get(1), rShoulderList.get(2));
						//distRdBlHand = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rHandList.get(0), rHandList.get(1), rHandList.get(2));
						distRdBlHand = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rHandRdBl.get(0), rHandRdBl.get(1), rHandRdBl.get(2));
						//distRdBlHandInt = (int) (distRdBlHand*100);
						
						//dataTextField.setText(""+distRdBlHand);
						//dataTextField.setText(""+NAOMath.getDistance3D(rShoulderList.get(0), rShoulderList.get(1), rShoulderList.get(2), rHandList.get(0), rHandList.get(1), rHandList.get(2)));
						
						//if(distRdBlHandInt <= 10){
						if(distRdBlHand <= 0.1) {
							curState=3;
							textArea.append("Red Ball in Range!\n\r");
							tts.say("Red ball is in my hand");
						}
						
						if(distRdBlBody >= .7f)
						{
							//alMotion.setIdlePostureEnabled("RArm", false);
							alMotion.setAngles("RArm", this.initRHandPos, 0.25f);
							//alMotion.setStiffnesses("RArm", 0.0f);
							alMotion.setBreathEnabled("RArm", true);
							alTracker.setMode("Move");
							
							tts.say("I want the red ball");
							curState=0;
						}
						break;
						
					case 3:
						//distRdBlHand = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rHandList.get(0), rHandList.get(1), rHandList.get(2));
						distRdBlHand = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rHandRdBl.get(0), rHandRdBl.get(1), rHandRdBl.get(2));
						
						//dataTextField.setText(""+distRdBlHand);
						
						if(distRdBlHand > 0.2f){
							curState=2;
							textArea.append("Red Ball is not in my hand!\n\r");
							tts.say("Red Ball is not in my hand");
						}
						
						if(distRdBlBody >= .7f)
						{
							alMotion.setAngles("RArm", this.initRHandPos, 0.25f);
							alMotion.setBreathEnabled("RArm", true);
							alTracker.setMode("Move");
							
							tts.say("I want the red ball");
							curState=0;
						}
						break;
					}
				}
			} catch (CallError | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		public void setTimer(Timer newTimer) {
			myTimer = newTimer;
		}
		
		public void reset() {
			curState=0;
		}
    }
    
    public class LearnBallListener implements ResetableActionListener {
		
    	// Current State of Learn Ball detection
    	private int curState=0;
		private Timer myTimer;
		

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				
				switch(curState) {
				
				// Start state
				case 0:
					List<Object> image;
					Integer width;
					Integer height;
					List<Float> rHandList;
					
					// Check for red ball
					image = (List<Object>) alVidDev.getImagesRemote(vidModuleName);
					
					width = (Integer) image.get(0);
					height = (Integer) image.get(0);
					
					rHandList = alMotion.getPosition("RHand", 0, true);
					
					
					break;
				
				}
			} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		public void setTimer(Timer newTimer) {
			myTimer = newTimer;
		}
		
		public void reset() {
			curState=0;
		}
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITestV3 window = new GUITestV3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUITestV3() {
		initialize();
		initializeNAO();
	}
	
	private void initializeNAO() {
		
		//blobProps = new BlobProperties();
		greenBallProps = new BlobProperties(new Color(0,51,0), 27, 5, 0.05f, "Circle");
		blobProps = greenBallProps;
		
		connection = new Connection();
		//session = new Session();
        
		String ipStr=null;
		
		ipStr = JOptionPane.showInputDialog(null, "Enter IP and port address: ", "Enter IP", JOptionPane.INFORMATION_MESSAGE);
		//ipStr = NAOIPDialog.ShowJMDNSIPDialog();
		
		Future<Void> futureVirt = null;
        try 
        {
	        //future = session.connect("tcp://"+RobotIP.ip+":"+RobotIP.port);
        	//future = session.connect(ipStr);
        	
        	if(ipStr == null || ipStr.isEmpty())
        	{
        		
        		ipStr = RobotIP.ip;
        	}
	        
        	connection.connectToNao(ipStr);
	        
	        session = connection.getSession();
	        
	        sessionVirt = session;
			//sessionVirt = new Session();
	        
	        if(session != sessionVirt)
        	{
        		futureVirt = sessionVirt.connect("tcp://127.0.0.1:55175");
        		
        		System.out.println("connected to virtual robot");
	        
		        synchronized (futureVirt)
	            {
	                futureVirt.wait(1000);
	            }
        	}
	        
	        if(!session.isConnected() || !sessionVirt.isConnected()) {
	        	JOptionPane.showMessageDialog (null, "Could not connect to Robot!!\r\nAt "+ipStr, "WalkTest", JOptionPane.WARNING_MESSAGE);
	            
	        }
	        
	        alMemory = new ALMemory(session);
            alMotion = new ALMotion(session);
            tts = new ALTextToSpeech(session);
            alTracker = new ALTracker(session);
            alBasicAwareness = new ALBasicAwareness(session);
        	alPosture = new ALRobotPosture(session);
        	alFaceDet = new ALFaceDetection(session);
        	
        	// New framework
        	alBlobDetet = new ALColorBlobDetection(session);
        	alVidDev = new ALVideoDevice(session);
        	
        	if(session != sessionVirt)
        	{
        		alMotionVirt = new ALMotion(sessionVirt);
        	}
        	else
        	{
        		alMotionVirt = alMotion;
        	}
        	
        	//alMemory.subscribeToEvent("ALTracker/ColorBlobDetected", "onColorBlobDetected::(m)", this);
        	alMemory.subscribeToEvent("FrontTactilTouched" , "onTouchFront::(f)", this);
        	alMemory.subscribeToEvent("ActiveDiagnosisErrorChanged", "onActiveDiagnosisErrorChanged::(m)", this);
        	alMemory.subscribeToEvent("PassiveDiagnosisErrorChanged", "onPassiveDiagnosisErrorChanged::(m)", this);
        	
        	alBlobDetet.subscribe("GUITest");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        //qiThread = new QiApplicationThread(app);
        //qiThread.start();
        /*
        try {
			alMotion.wakeUp();
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		SeekRedBallActionlistener seekActionListener;
		
		trackRedBallTimer = new Timer(500, new ActionListener() {
			
			private List<Float> rHandList;
			private List<Float> redBallList;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					redBallList=alTracker.getTargetPosition(0);
					rHandList = alMotionVirt.getPosition("RHand", 0, true);
					float y;
					float x;
					double ang;
					
					//y=rHandList.get(2)-redBallList.get(2);
					//x=rHandList.get(1)-redBallList.get(1);
					
					y = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), rHandList.get(2), redBallList.get(0), redBallList.get(1), redBallList.get(2));
					x = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), rHandList.get(2), rHandList.get(0), rHandList.get(1), rHandList.get(2));
					
					ang = Math.atan2(y,x);
					ang +=Math.PI/2.0f;
					
					alMotionVirt.setStiffnesses("RWristYaw", 1.0f);
					alMotionVirt.setStiffnesses("RElbowYaw", 1.0f);
					if(ang>1.8238)
					{
						ang = ang-1.8238f;
						alMotionVirt.setAngles("RWristYaw", 1.8238f, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", ang, 0.5f);
					}
					else if(ang < -1.8238f)
					{
						ang = ang+1.8238f;
						alMotionVirt.setAngles("RWristYaw", -1.8238f, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", ang, 0.5f);
					}
					else 
					{
						alMotionVirt.setAngles("RWristYaw", ang, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", 0, 0.5f);
					}
					alMotionVirt.setStiffnesses("RWristYaw", 0.0f);
					alMotionVirt.setStiffnesses("RElbowYaw", 0.0f);
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		trackLHandTimer = new Timer(500, new ActionListener() {
			
			private List<Float> rHandList;
			private List<Float> redBallList;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					redBallList=alMotionVirt.getPosition("LHand", 0, true);
					rHandList = alMotionVirt.getPosition("RHand", 0, true);
					float y;
					float x;
					double ang;
					
					//y=rHandList.get(2)-redBallList.get(2);
					//x=rHandList.get(1)-redBallList.get(1);
					
					y = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), rHandList.get(2), redBallList.get(0), redBallList.get(1), redBallList.get(2));
					x = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), rHandList.get(2), rHandList.get(0), rHandList.get(1), rHandList.get(2));
					
					ang = Math.atan2(y,x);
					ang +=Math.PI/2.0f;
					if(ang>1.8238)
					{
						ang = ang-1.8238f;
						alMotionVirt.setAngles("RWristYaw", 1.8238f, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", ang, 0.5f);
					}
					else if(ang < -1.8238f)
					{
						ang = ang+1.8238f;
						alMotionVirt.setAngles("RWristYaw", -1.8238f, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", ang, 0.5f);
					}
					else 
					{
						alMotionVirt.setAngles("RWristYaw", ang, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", 0, 0.5f);
					}
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		trackWaitRedBallTimer = new Timer(500, new ActionListener() {
			
			private List<Float> rHandList;
			private List<Float> redBallList;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					rHandList = alMotion.getPosition("RHand", 0, true);
					
					redBallList=alTracker.getTargetPosition(0);
					//handDestList = alMotion.getPosition("RHand", 0, false);
					//handDestList
					float dist;
					
					dist = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), rHandList.get(0), rHandList.get(1), rHandList.get(2));
					
					if(dist < 0.1f){
						textArea.append("Red Ball in Range!\n\r");
						tts.say("Red ball is in my hand");
					}
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		seekActionListener = new SeekRedBallActionlistener();
		
		seekRedBallTimer = new ResetableTimer(SEEK_REDBALL_SPEED, seekActionListener);
		
		seekActionListener.setTimer(seekRedBallTimer);
		
		seekRedBallTimer.setCoalesce(true);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				uninitialize();
			}
		});
		frame.setBounds(100, 100, 490, 299);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane);
		splitPane.setDividerLocation(0.5);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanel btnPanel = new JPanel();
		splitPane.setTopComponent(btnPanel);
		btnPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton btnWalkForward = new JButton("Walk Forward");
		btnWalkForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tts.say("Moving forward.");
					if(!alMotion.robotIsWakeUp()) {
						alMotion.wakeUp();
					}
					alMotion.moveToward(0.6f, 0f, 0f);
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnWalkForward.setBounds(10, 11, 111, 23);
		btnPanel.add(btnWalkForward);
		
		JButton btnWalkBackward = new JButton("Walk Backward");
		btnWalkBackward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tts.say("Moving backward.");
					if(!alMotion.robotIsWakeUp()) {
						alMotion.wakeUp();
					}
					alMotion.moveToward(-0.6f, 0f, 0f);
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnWalkBackward.setBounds(10, 45, 111, 23);
		btnPanel.add(btnWalkBackward);
		
		JButton btnStopWalking = new JButton("Stop Walking");
		btnStopWalking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tts.say("Stop Moving.");
					alMotion.moveToward(0f, 0f, 0f);
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnStopWalking.setBounds(10, 79, 111, 23);
		btnPanel.add(btnStopWalking);
		
		JButton btnStartRedBall = new JButton("Start Red Ball");
		btnStartRedBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startRedball();
			}
		});
		btnStartRedBall.setBounds(10, 113, 111, 23);
		btnPanel.add(btnStartRedBall);
		
		JButton btnStopRedBall = new JButton("Stop Red Ball");
		btnStopRedBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopRedball();
			}
		});
		btnStopRedBall.setBounds(10, 147, 111, 23);
		btnPanel.add(btnStopRedBall);
		
		/*
		JButton btnConfigRedBall = new JButton("Config Red Ball");
		btnConfigRedBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConfigRedBall.setBounds(131, 113, 105, 23);
		btnPanel.add(btnConfigRedBall);
		*/
		
		JButton btnSetEffectorRarm = new JButton("Set Effector RArm");
		btnSetEffectorRarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alTracker.setEffector("RArm");
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPanel.add(btnSetEffectorRarm);
		
		JButton btnArmToRed = new JButton("Arm To Red Ball");
		btnArmToRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Float> list;
				try {
					list = alTracker.getTargetPosition(1);
					list.add(0f);
					list.add(0f);
					list.add(0f);
					alMotion.setPositions("RArm", 1, list, 0.5f, 7);
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPanel.add(btnArmToRed);
		
		JButton btnGetRedBall = new JButton("Get Red Ball");
		btnGetRedBall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Subscribe to RedBallDetected Event
				//Get distance from red ball
				//check if Red ball is close
				//if not move to red ball pos - threshold
				//if red ball is in reach, open hand extend hand
				// when red ball is in position close hand
				
				List<Float> redBallList;
				
				try {
					redBallList=alTracker.getTargetPosition(0);
					System.out.println("Redball Post: "+redBallList);
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPanel.add(btnGetRedBall);
		
		JButton btnCloseHand = new JButton("Close Hand");
		btnCloseHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alMotion.closeHand("RHand");
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnOpenHand = new JButton("Open Hand");
		btnOpenHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					alMotion.openHand("RHand");
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnSetHandAngle = new JButton("Set hand Angle");
		btnSetHandAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//[0.40491632, -0.034906592, -2.8E-45, 0.034906592, 0.15264936, 0.25]
				
				/*
				try {
					alMotion.setBreathEnabled("RArm", false);
					alMotion.setIdlePostureEnabled("RArm", false);
				} catch (CallError | InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				*/
				
				List<Float> rHandList;
				List<Float> redBallList;
				try {
					alMotion.setStiffnesses("RArm", 1.0f);
					alMotion.setAngles("RArm", initRArm, 0.5f);
					if(session != sessionVirt)
					{
						alMotionVirt.setAngles("RArm", initRArm, 0.5f);
					}
					alMotion.setStiffnesses("RArm", 0.0f);
					redBallList = alTracker.getTargetPosition(0);
					//redBallList = new ArrayList<Float>();
					//2.4697332, 0.36351782, 1.142264
					//redBallList.add(2.4697332f);
					//redBallList.add(0.36351782f);
					//redBallList.add(1.142264f);
					rHandList = alMotion.getPosition("RHand", 0, false);
					float y;
					float x;
					double ang;
					
					y=rHandList.get(2)-redBallList.get(2);
					x=rHandList.get(1)-redBallList.get(1);
					ang = Math.atan2(y,x);
					ang -=Math.PI/2.0f;
					
					alMotionVirt.setStiffnesses("RWristYaw", 1.0f);
					alMotionVirt.setStiffnesses("RElbowYaw", 1.0f);
					if(ang>1.8238)
					{
						ang = ang-1.8238f;
						alMotionVirt.setAngles("RWristYaw", 1.8238f, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", ang, 0.5f);
					}
					else if(ang < -1.8238f)
					{
						ang = ang+1.8238f;
						alMotionVirt.setAngles("RWristYaw", -1.8238f, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", ang, 0.5f);
					}
					else 
					{
						alMotionVirt.setAngles("RWristYaw", ang, 0.5f);
						alMotionVirt.setAngles("RElbowYaw", 0, 0.5f);
					}
					alMotionVirt.setStiffnesses("RWristYaw", 0.0f);
					alMotionVirt.setStiffnesses("RElbowYaw", 0.0f);
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnSetHandAs = new JButton("Set LHand as ball");
		btnSetHandAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//redBallList=alMotionVirt.getPosition("LHand", 1, false);
					
					alMotionVirt.setAngles("RArm", initRArm, 0.5f);
					Thread.sleep(1000);
					
					//trackLHandTimer.setInitialDelay(0);
					//trackLHandTimer.start();
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPanel.add(btnSetHandAs);
		btnPanel.add(btnSetHandAngle);
		
		JButton btnWaitNGrab = new JButton("Wait N Grab");
		btnWaitNGrab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					//redBallList=alMotionVirt.getPosition("LHand", 1, false);
					
					
					if(session != sessionVirt)
					{
						alMotionVirt.setAngles("RArm", initRArmWait, 0.5f);
					}
					
					alMotion.setBreathEnabled("RArm", false);
					//alMotion.setIdlePostureEnabled("RArm", false);
					alMotion.setStiffnesses("RWristYaw", 1.0f);
					alMotion.setStiffnesses("RElbowYaw", 1.0f);
					
					alMotion.setAngles("RArm", initRArmWait, 0.3f);
					
					//alMotion.setStiffnesses("RWristYaw", 0.0f);
					//alMotion.setStiffnesses("RElbowYaw", 0.0f);
					

					
					
					
					///trackWaitRedBallTimer.setInitialDelay(0);
					//trackWaitRedBallTimer.start();
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnPanel.add(btnWaitNGrab);
		
		JButton btnRedBallRange = new JButton("stop seek redball");
		btnRedBallRange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					seekRedBallTimer.stop();
					seekRedBallTimer.reset();
					stopRedball();
					
					
					/*
					handDestList = alMotion.getPosition("RHand", 0, true);
					
					redBallList=alTracker.getTargetPosition(0);
					//handDestList = alMotion.getPosition("RHand", 0, false);
					//handDestList
					float dist;
					
					dist = NAOMath.getDistance3D(redBallList.get(0), redBallList.get(1), redBallList.get(2), handDestList.get(0), handDestList.get(1), handDestList.get(2));
					
					if(dist < 0.1f){
						textArea.append("Red Ball in Range!\n\r");
					}
					*/
					tts.say("timer stopped");
				} catch (CallError | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnStartSeekRedball = new JButton("Start seek redball");
		btnStartSeekRedball.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startRedball();
				
				seekRedBallTimer.setInitialDelay(SEEK_REDBALL_SPEED);
				seekRedBallTimer.start();
			}
		});
		btnPanel.add(btnStartSeekRedball);
		btnPanel.add(btnRedBallRange);
		
		btnPanel.add(btnOpenHand);
		btnPanel.add(btnCloseHand);
		
		JButton btnLearnFace = new JButton("Learn Face");
		btnLearnFace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPanel.add(btnLearnFace);
		
		JButton btnRelearnFace = new JButton("ReLearn Face");
		btnRelearnFace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPanel.add(btnRelearnFace);
		
		JButton btnToggleFaceEvent = new JButton("Toggle Face Event");
		btnToggleFaceEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleFaceEvent(2);
			}
		});
		btnPanel.add(btnToggleFaceEvent);
		
		JButton btnCameraConfig = new JButton("Camera Config");
		btnPanel.add(btnCameraConfig);
		
		JButton btnColorblobConfig = new JButton("ColorBlob Config");
		btnColorblobConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onColorBlobConfigClicked();
			}
		});
		btnPanel.add(btnColorblobConfig);
		
		JButton btnStartBlobDetection = new JButton("Start Blob Detection");
		btnStartBlobDetection.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				onColorBlobStartClicked();
			}
			
		});
		btnPanel.add(btnStartBlobDetection);
		
		JButton btnStopBlobDetection = new JButton("Stop Blob Detection");
		btnStopBlobDetection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onColorBlobStopClicked();
			}
		});
		btnPanel.add(btnStopBlobDetection);
		
		dataTextField = new JTextField();
		btnPanel.add(dataTextField);
		dataTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setBottomComponent(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		/*
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText("sfdsadfsafsdfsdfsa");
		scrollPane.setViewportView(textArea);
		*/
		
	}
	
	private void uninitialize() {
		try {
			//tts.say("Application is stopping");
			//tcp://127.0.0.1:55546
				/*
				//tts.say("Application is stopping");
				alMotion.setBreathEnabled("RArm", true);
				alMotion.setIdlePostureEnabled("RArm", true);
				System.out.println("uninitialize called");
				alTracker.stopTracker();
		    	alTracker.unregisterAllTargets();
		    	alPosture.goToPosture("Stand", 0.8f);

		    	if(alBasicAwareness != null && !alBasicAwareness.isAwarenessRunning())
		    	{
		    		alBasicAwareness.startAwareness();
		    	}
		        //alMotion.moveToward(0f, 0f, 0f);
		        app.stop();
				qiThread.join(1000);
				*/
			//alMotion.setBreathEnabled("RArm", true);
			//alMotion.setIdlePostureEnabled("RArm", true);
			//alMotion.setBreathEnabled("Body", false);
			
			toggleFaceEvent(0);
			alMotion.setBreathEnabled("Body", true);
			//alMotion.setIdlePostureEnabled("Body", false);
			//alMotion.setIdlePostureEnabled("Body", true);
			System.out.println("uninitialize called");
			if(session.isConnected())
			{
				alPosture.goToPosture("Standinit", 0.8f);
				alTracker.stopTracker();
				alTracker.unregisterAllTargets();
				//alMemory.unsubscribeToEvent("ALTracker/ColorBlobDetected", "onColorBlobDetected::(m)");
				alBlobDetet.unsubscribe("GUITest");
	        	
	        	//alTracker..untrackEvent("ALTracker/ColorBlobDetected");
			}

			//app.stop();
			//qiThread.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void startRedball() {
		// Wake up robot
        try {
			alMotion.wakeUp();
        
	        //Go to posture stand
	        alPosture.goToPosture("Stand", 0.8f);
	        
	        alBasicAwareness.stopAwareness();
	        
	        
	        // choregraphe example [-self.distanceX, self.distanceY, self.angleWz,
	        //self.thresholdX, self.thresholdY, self.thresholdWz]
	        Float[] relPos = {new Float(-0.3f),new Float(0),new Float(0),
	        		new Float(0.1f),new Float(0.1f),new Float(0.1f)};
	        ArrayList<Float> relPosAry = new ArrayList<Float>(Arrays.asList(relPos));
	        
	        // Initialize movement Used for Move and WholeBody
	        alMotion.moveInit();
	        alTracker.setRelativePosition(relPosAry);
	        alTracker.registerTarget("RedBall", 0.10f);
	        alTracker.setMode("Move");
	        alTracker.track("RedBall");
        } catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopRedball() {
		try {
			alTracker.setEffector("None");
			alTracker.stopTracker();
	    	alTracker.unregisterAllTargets();
	    	alPosture.goToPosture("Stand", 0.3f);
	        alBasicAwareness.startAwareness();
		} catch (CallError | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void startBlobTracker() {
		// Wake up robot
        try {
        	if(!alMotion.robotIsWakeUp()) {
        		alMotion.wakeUp();
        	}
        
	        //Go to posture stand
	        alPosture.goToPosture("Stand", 0.8f);
	        
	        alBasicAwareness.stopAwareness();
	        
	        
	        // choregraphe example [-self.distanceX, self.distanceY, self.angleWz,
	        //self.thresholdX, self.thresholdY, self.thresholdWz]
	        Float[] relPos = {new Float(-0.3f),new Float(0),new Float(0),
	        		new Float(0.1f),new Float(0.1f),new Float(0.1f)};
	        ArrayList<Float> relPosAry = new ArrayList<Float>(Arrays.asList(relPos));
	        
	        // Initialize movement Used for Move and WholeBody
	        alMotion.moveInit();
	        alTracker.setRelativePosition(relPosAry);
	        alTracker.setMode("Head");
	        Color color;
			color = blobProps.getColor();
			try {
				alBlobDetet.setColor(color.getRed(), color.getGreen(), color.getBlue(), blobProps.getColorThreshold());
				alBlobDetet.setObjectProperties(blobProps.getMinSize(), blobProps.getSpan(), blobProps.getShape());
				alTracker.trackEvent("ALTracker/ColorBlobDetected");
				
				//alBlobDetet.setAutoExposure(false);
			} catch (CallError | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopBlobTracker() {
		// Wake up robot
        try {
			alTracker.stopTracker();
			alTracker.trackEvent("");
			alTracker.unregisterAllTargets();
        } catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * type = 0; disable Face Detection
	 * type = 1; enable Face Detection
	 * type = 2; toggle Face Detection
	 */
	public void toggleFaceEvent(int type) {
		if(type == 0)
		{
			if(isFaceEventEnabled)
			{
				textArea.append("Face Detection Event unsubscribed\n");
				try {
					alMemory.unsubscribeToEvent("FaceDetected", "onFaceDetected::(m)");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isFaceEventEnabled=false;
			}
		}
		else if(type == 1) {
			if(!isFaceEventEnabled)
			{
				textArea.append("Face Detection Event subscribed\n");
				try {
					alMemory.subscribeToEvent("FaceDetected", "onFaceDetected::(m)", this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				isFaceEventEnabled=true;
			}
		}
		else if(type == 2) {
			if(!isFaceEventEnabled)
			{
				textArea.append("Face Detection Event subscribed\n");
				try {
					alMemory.subscribeToEvent("FaceDetected", "onFaceDetected::(m)", this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isFaceEventEnabled=true;
			}
			else
			{
				textArea.append("Face Detection Event unsubscribed\n");
				try {
					alMemory.unsubscribeToEvent("FaceDetected", "onFaceDetected::(m)");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isFaceEventEnabled=false;
			}
		}
	}
	
	public void onFaceDetected(Object param) {
		textArea.append("Face Detected\n");
		System.out.println(""+param);
	}
	
	public void onColorBlobConfigClicked() {
		BlobProperties lblobProps;
		lblobProps= NAOBlobDetectionPanel.showDialog(frame, this.blobProps);
		System.out.println("Blob Props: "+lblobProps);
		if(lblobProps != null)
		{
			this.blobProps = lblobProps;
			 Color color;
			 color = blobProps.getColor();
			
			try {
				alBlobDetet.setColor(color.getRed(), color.getGreen(), color.getBlue(), blobProps.getColorThreshold());
				alBlobDetet.setObjectProperties(blobProps.getMinSize(), blobProps.getSpan(), blobProps.getShape());
			} catch (CallError | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//JOptionPane.showInputDialog(this, panel);
	}
	
	public void onColorBlobStartClicked() {
		startBlobTracker();
	}
	
	public void onColorBlobStopClicked() {
		stopBlobTracker();
	}
	
	public void onColorBlobDetected(Object param) {
		//List<Object> list;
		//textArea.append("Color Blob object detected!\n");
		System.out.println("Color Blob Detected: ");
		//alVidDev.getActiveCamera().
		//alVidDev.getImageRemote(name);
		//alBlobDetet.getActiveCamera()
		//System.out.println(alBlobDetet.getCircle());
		/*
		list = (List<Object>) param;
		try {
			alTracker.lookAt((List<Float>)list.get(0), 0, 0.5f, false);
		} catch (CallError | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	public void onLearnColorBall() {
		try {
			startBlobTracker();
			vidModuleName = alVidDev.subscribeCamera(vidModuleNameProposed,0, 1, 11, 5);
			
			//trackWaitRedBallTimer.setInitialDelay(0);
			//trackWaitRedBallTimer.start();
		} catch (CallError | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void onTouchFront(float f) {
		if(f == 1.0f) {
			stopRedball();
		}
	}
	
	public void onActiveDiagnosisErrorChanged(Object param) {
		System.out.println("Active Diagnosis "+param+"");
	}

	public void onPassiveDiagnosisErrorChanged(Object param) {
		System.out.println("PassiveDiagnosis "+param+"");
	}
}
