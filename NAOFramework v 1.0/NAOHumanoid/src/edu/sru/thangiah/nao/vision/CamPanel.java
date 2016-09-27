package edu.sru.thangiah.nao.vision;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CamPanel extends JPanel implements Runnable{

	private CameraImage cameraImage;
	private int frameRate;
	private CameraImageType type;
	
	public CamPanel(CameraImage cameraImage, CameraImageType type){
		this.cameraImage = cameraImage;
		this.type = type;
		this.frameRate = cameraImage.getFrameRate();
		Dimension size = new Dimension(cameraImage.getWidth(), cameraImage.getHeight());
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	}
	
	public void paintComponent(Graphics g){
			g.drawImage(cameraImage.getImage(type), 0, 0, null);
	}

	public void run() {
		while(cameraImage.isRunning()){
			//currTime = System.currentTimeMillis();
			this.repaint();
			//endTime = System.currentTimeMillis();
			try {
				Thread.sleep((1000/frameRate));
			} catch (InterruptedException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
