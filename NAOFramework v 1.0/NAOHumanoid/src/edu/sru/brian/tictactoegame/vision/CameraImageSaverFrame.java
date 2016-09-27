package edu.sru.brian.tictactoegame.vision;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CameraImageSaverFrame extends JFrame {

	private JPanel contentPane;
	private StreamPanel camPane;
	private WebCameraStream camStream;
	private ImageStreamManager camManager;
	
	private int posPicCount=0;
	private int negPicCount=0;
	
	private String posPicFileNameBase;
	private String negPicFileNameBase;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					CameraImageSaverFrame frame = new CameraImageSaverFrame();
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
	public CameraImageSaverFrame() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(Character.toLowerCase(arg0.getKeyChar())=='P')
				{
					// Positive Image
					camStream.saveImage(posPicFileNameBase);
				}
				else if(Character.toLowerCase(arg0.getKeyChar())=='N')
				{
					// Negative Image
				}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				try {
					camManager.stopThread();
				} catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				camStream.release();
				//System.exit(0);
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		camStream = new WebCameraStream(640,480);
		camManager = new ImageStreamManager();
		
		contentPane.setLayout(null);
		
		camPane = new StreamPanel();
		contentPane.add(camPane);
		
		System.out.println("width: "+camStream.getWidth()+" height: "+camStream.getHeight());
		
		camPane.setBounds(0, 0, camStream.getWidth(), camStream.getHeight());
		
		this.setBounds(0, 0, camStream.getWidth(), camStream.getHeight()+50);
		
		camPane.setImageStream(camStream);
		
		camManager.setSource(camPane.getPanelImageStream());
		
		camManager.startThreaded(50);
		
	}
}
