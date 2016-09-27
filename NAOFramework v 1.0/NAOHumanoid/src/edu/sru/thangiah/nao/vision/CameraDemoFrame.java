package edu.sru.thangiah.nao.vision;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.connection.SynchronizedConnect;

import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

public class CameraDemoFrame extends JFrame {

	private JPanel contentPane;
	private CamPanel camTopDef;
	private CamPanel camTopEdge;
	private CamPanel camTopDual;
	private CamPanel camBotDef;
	private CamPanel camBotEdge;
	private CamPanel camBotDual;
	private Camera camera;
	private CameraImage topImage;
	private CameraImage botImage;
	private Session session;
	private SynchronizedConnect sync;

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	
	public CameraDemoFrame(SynchronizedConnect sync, String name) throws Exception{
		session = sync.getSession(name);
		this.sync = sync;
		camera = new Camera(session);
		camera.run();
		topImage = camera.getTopImage();
		botImage= camera.getBottomImage();

		setTitle("Nao Camera Feed");
		setBounds(100, 100, 1023, 558);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmInitEdgeDetection = new JMenuItem("Init Edge Detection");
		mntmInitEdgeDetection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		mnFile.add(mntmInitEdgeDetection);
		
		JMenuItem mntmSaveTopImages = new JMenuItem("Save Top Images");
		mnFile.add(mntmSaveTopImages);
		mntmSaveTopImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				try {
					topImage.saveImages();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		

		JMenuItem mntmSaveBotImages = new JMenuItem("Save Bot Images");
		mnFile.add(mntmSaveBotImages);
		mntmSaveBotImages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				try {
					botImage.saveImages();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmFrontCamOptions = new JMenuItem("Top Cam Options");
		mntmFrontCamOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CameraFrameOptions camOptions = new CameraFrameOptions(topImage, "Top");
				camOptions.setVisible(true);
			}
		});
		mnView.add(mntmFrontCamOptions);
		
		JMenuItem mntmBottomCamOptions = new JMenuItem("Bottom Cam Options");
		mntmBottomCamOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CameraFrameOptions camOptions = new CameraFrameOptions(botImage, "Bottom");
				camOptions.setVisible(true);
			}
		});
		
		mnView.add(mntmBottomCamOptions);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new CamFrameListener());
		run();
	}
	
	
	public void run() {
		camTopDef = new CamPanel(topImage, CameraImageType.DEFAULT);
		camTopDef.setBounds(10, 11, 320, 240);
		contentPane.add(camTopDef);
		
		camTopEdge = new CamPanel(topImage, CameraImageType.EDGE);
		camTopEdge.setBounds(340, 11, 320, 240);
		contentPane.add(camTopEdge);
		
		camTopDual = new CamPanel(topImage, CameraImageType.DUAL);
		camTopDual.setBounds(670, 11, 320, 240);
		contentPane.add(camTopDual);
		
		camBotDef = new CamPanel(botImage, CameraImageType.DEFAULT);
		camBotDef.setBounds(10, 262, 320, 240);
		contentPane.add(camBotDef);
		
		camBotEdge = new CamPanel(botImage, CameraImageType.EDGE);
		camBotEdge.setBounds(340, 262, 320, 240);
		contentPane.add(camBotEdge);
		
		camBotDual = new CamPanel(botImage, CameraImageType.DUAL);
		camBotDual.setBounds(670, 262, 320, 240);
		contentPane.add(camBotDual);
		
		DisposeThread dispose = new DisposeThread();
		new Thread(dispose).start();
		new Thread(camTopDef).start();
		new Thread(camTopEdge).start();
		new Thread(camTopDual).start();
		new Thread(camBotDef).start();
		new Thread(camBotEdge).start();
		new Thread(camBotDual).start();
	}
	
	public void exit(){
		
		try{
			camera.exit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		this.dispose();
	}
	
	private class DisposeThread implements Runnable{

		@Override
		public void run() {
			while(camera.isRunning()){
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
	
	private class CamFrameListener implements WindowListener{
		public void windowClosing(WindowEvent e) {
			try {
				camera.exit();
				dispose();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
		public void windowActivated(WindowEvent e) {}
		public void windowClosed(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
	}				
}
