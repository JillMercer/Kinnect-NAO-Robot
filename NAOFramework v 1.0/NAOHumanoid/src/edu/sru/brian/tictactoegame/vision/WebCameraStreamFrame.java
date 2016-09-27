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

/**
 * WebCameraStreamFrame
 * @author Brian Atwell
 * Description: Creates a frame for web camera stream.
 * It shows the web camera image and the Hough Line detection stream.
 *
 */
public class WebCameraStreamFrame extends JFrame {

	private JPanel contentPane;
	private StreamPanel camPane;
	private StreamPanel linePane;
	private WebCameraStream camStream;
	private ImageStreamManager camManager;
	private HoughLineStream lineStream;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
					WebCameraStreamFrame frame = new WebCameraStreamFrame();
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
	public WebCameraStreamFrame() {
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
		
		camStream = new WebCameraStream(320,240);
		camManager = new ImageStreamManager();
		
		lineStream = new HoughLineStream();
		contentPane.setLayout(null);
		
		camPane = new StreamPanel();
		contentPane.add(camPane);
		linePane = new StreamPanel();
		contentPane.add(linePane);
		
		System.out.println("width: "+camStream.getWidth()+" height: "+camStream.getHeight());
		
		camPane.setBounds(0, 0, camStream.getWidth(), camStream.getHeight());
		
		this.setBounds(0, 0, camStream.getWidth()*2, camStream.getHeight()+50);
		linePane.setBounds(camStream.getWidth(), 0, camStream.getWidth(), camStream.getHeight());
		
		camPane.setImageStream(camStream);
		linePane.setImageStream(lineStream);
		
		camManager.setSource(camPane.getPanelImageStream());
		camManager.enqueue(linePane.getPanelImageStream());
		
		camManager.startThreaded(250);
		
	}

}
