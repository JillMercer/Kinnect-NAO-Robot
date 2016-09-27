package edu.sru.thangiah.nao.vision;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class CameraFrameOptions extends JFrame {

	private JPanel contentPane;
	private JSlider lowThreshold;
	private JSlider highThreshold;
	private JSlider gaussKernelWidth;
	private JSlider gaussKernelRadius;
	private JButton btnUpdateDetector;
	private CameraImage camImage;
	private float low, high, radius;
	private int width;
	private JButton btnReset;
	private JToggleButton btnResize;

	/**
	 * Create the frame.
	 */

	private void init(){
		
		lowThreshold = new JSlider();
		lowThreshold.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
			}
		});
		
		lowThreshold.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Low Threshold", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		lowThreshold.setValue(80);
		lowThreshold.setMaximum(160);
		lowThreshold.setBounds(10, 11, 200, 48);
		contentPane.add(lowThreshold);
		
		highThreshold = new JSlider();
		highThreshold.setValue(80);
		highThreshold.setMaximum(160);
		highThreshold.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "High Threshold", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		highThreshold.setBounds(10, 70, 200, 48);
		contentPane.add(highThreshold);
		
		gaussKernelWidth = new JSlider();
		gaussKernelWidth.setMinimum(1);
		gaussKernelWidth.setValue(16);
		gaussKernelWidth.setMaximum(32);
		gaussKernelWidth.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Gaussian Kernel Width", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		gaussKernelWidth.setBounds(10, 129, 200, 48);
		contentPane.add(gaussKernelWidth);
		
		gaussKernelRadius = new JSlider();
		gaussKernelRadius.setMinimum(1);
		gaussKernelRadius.setValue(160);
		gaussKernelRadius.setMaximum(320);
		gaussKernelRadius.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Gaussian Kernel Radius", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		gaussKernelRadius.setBounds(10, 188, 200, 48);
		contentPane.add(gaussKernelRadius);
		
		btnResize = new JToggleButton("Resize Edge");
		btnResize.setSelected(true);
		btnResize.setBounds(42, 247, 136, 23);
		contentPane.add(btnResize);
		
		btnUpdateDetector = new JButton("Update");
		btnUpdateDetector.setBounds(42, 281, 136, 23);
		contentPane.add(btnUpdateDetector);
		
		btnReset = new JButton("Reset");
		btnReset.setBounds(42, 315, 136, 23);
		contentPane.add(btnReset);
		
		btnReset.addActionListener(new ResetListener());
		btnUpdateDetector.addActionListener(new UpdateListener());
		
		//lowThreshold.addChangeListener(new lowThreshListener());
		//highThreshold.addChangeListener(new highThreshListener());
		//gaussKernelWidth.addChangeListener(new widthListener());
		//gaussKernelRadius.addChangeListener(new radiusListener());
		
	}
	
	 class UpdateListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			updateDetector();	
		}	 
	 }
	 
	 class ResetListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			try {
				reset();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		 
	 }
	 
	private void updateDetector(){
		radius = ((float)gaussKernelRadius.getValue())/10;
		width = gaussKernelWidth.getValue();
		high = ((float)highThreshold.getValue())/10;
		low = ((float)lowThreshold.getValue())/10;
		
		if(low > high){
			high = low;
			highThreshold.setValue((int)high*10);
			high = ((float)highThreshold.getValue())/10 + .1f;
		}
		if(high < low){
			low = high;
			lowThreshold.setValue((int)low*10);
			low = ((float)lowThreshold.getValue())/10 - .1f;
		}
		camImage.setDetectorResize(btnResize.isSelected());
		camImage.setLowThreshold(low);
		camImage.setHighThreshold(high);
		camImage.setGaussianKernelRadius(radius);
		camImage.setGaussianKernelWidth(width);
				
		}
	
	private void initDetector(){
		low = camImage.getLowThreshold();		
		high = camImage.getHighThreshold();
		width = camImage.getGaussianKernelWidth();
		radius = camImage.getGaussianKernelRadius();
		highThreshold.setValue((int)high*10);
		lowThreshold.setValue((int)low*10);
		gaussKernelRadius.setValue((int)radius*10);
		gaussKernelWidth.setValue(width);
		btnResize.setSelected(camImage.isDetectorResize());
	}
	
	private void reset() throws InterruptedException{
		camImage.resetDetector();
		initDetector();	
	}
	
	public CameraFrameOptions(CameraImage camImage, String camera) {
		this.camImage = camImage;
		setTitle("Edge Detection Settings: " + camera);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 235, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);	
		init();
		initDetector();
	}
}
