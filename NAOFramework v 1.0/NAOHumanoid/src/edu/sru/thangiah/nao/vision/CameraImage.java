package edu.sru.thangiah.nao.vision;

/** Author: Zachary Kearney
Last Edited, 4/26/2016
* @author zrk1002
* Container for a camera image from the NaoRobot.
* Contains 4 separate images, the current image, the image of just the edges, the alpha image of the edges, and the combined image.
* Edge detection is done in real time via threads.
*
*/
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class CameraImage {
	
	private CannyEdgeDetector detector;
	private BufferedImage currImage;
	private BufferedImage edgeImage;
	private BufferedImage alphaImage;
	private BufferedImage dualImage;
	private Camera camera;
	private int width = 0;
	private int height = 0;
	private int frameRate = 30;
	private String module;	
	private boolean isRunning = false;
	private CurrThread curr;
	private EdgeThread edge;
	private DualThread dual;
	private boolean detectorResize = false;
	private boolean edgeRunning = false;
	
	public CameraImage(Camera camera, String module){
		this.camera = camera;
		width = camera.getWidth();
		height = camera.getHeight();
		frameRate = camera.getFrameRate();
		this.module = module;
		detector = new CannyEdgeDetector();
		setLowThreshold(.1f); //default is 2.5
		setHighThreshold(4.5f); //default is 7.5
		//detector.setGaussianKernelWidth(16); // default is 16
		//detector.setGaussianKernelRadius(.2f); // default is 16
	}
	
	public void run(){
		if(camera.isRunning() && !isRunning){
		isRunning = true;
		curr = new CurrThread(); 
		edge = new EdgeThread();
		dual = new DualThread();
		new Thread(curr).start();
		new Thread(edge).start();
		new Thread(dual).start();	
		}
	}
	
	public void stop(){
		isRunning = false;
	}
	
	private void updateEdgeImage(){
		try{
		if(!edgeRunning){
		edgeRunning = true;
			detector.setSourceImage(currImage, detectorResize);
			detector.process();
			edgeImage = detector.getEdgesImage();
			alphaImage = detector.getAlphaImage();
			edgeRunning = false;
		}
		}catch(NullPointerException e){}
	}
	
	private void updateCurrImage(byte[] rawData){	 		 	
	        int[] pixels = new int[height * width];
	        
	        IntStream.range(0, pixels.length).parallel().forEach(i -> pixels[i] =
	        		((255 & 0xFF) << 24) |
                   ((int)(rawData[(i * 3)] & 0xFF) << 16) | // red
                   ((int)(rawData[i * 3 + 1] & 0xFF) << 8) | // green
                   ((int)(rawData[i * 3 + 2] & 0xFF))); //blue);
	        //credit to justin cather
	        //Looping through the data and converting from hexadecimal into raw pixel data.
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR); //RED GREEN BLUE Format
	        image.setRGB(0,0,width,height,pixels,0,width);
	        //Converts BufferedImage into proper format.
	       currImage = image;
	 }
	
	/*
	private void updateEdgeImageResize(){
	
		try{
			if(!edgeRunning){
		edgeRunning = true;
		fastDetector.setSourceImage(scaleDown(currImage));
		fastDetector.process();
		edgeImage = scaleUp(fastDetector.getEdgesImage());
		alphaImage = scaleUp(fastDetector.getAlphaImage());
		edgeRunning = false;
			}
		}catch(NullPointerException e){}
	}
	
	 private void updateEdgeImageNoResize(){
		try{		 	
		if(!edgeRunning){
		edgeRunning = true;
		fullDetector.setSourceImage(currImage);
		fullDetector.process();
		edgeImage = fullDetector.getEdgesImage();
		alphaImage = fullDetector.getAlphaImage();
		edgeRunning = false;
		 }
		 }
		 catch(NullPointerException e){
		 }
	 }
	 */
	
	 private void updateDualImage(){
		try{
		BufferedImage combined = new BufferedImage(currImage.getWidth(), currImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = combined.getGraphics();
	    g.drawImage(currImage, 0, 0, null);
	    g.drawImage(alphaImage, 0, 0, null);
		dualImage = combined;
		}
		catch(NullPointerException e){
		}
	 }
	 
	 private class CurrThread implements Runnable{

		public void run() {
			
			while(camera.isRunning() && isRunning){
				long start = System.currentTimeMillis();		
		    	try {
					updateCurrImage(camera.getByteArray(module));
				} catch (Exception e1) {
					e1.printStackTrace();
				}  	
		    	
		    	long fin = System.currentTimeMillis();	    	
		    	try {
		    		long sleepTime = (1000/frameRate - (fin - start));
		    		if (sleepTime > 0)
		    			Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}}}
		}
	 
	 private class EdgeThread implements Runnable{
 
		 public void run() {
				 while(camera.isRunning() && isRunning){
						long start = System.currentTimeMillis();
						if(currImage != null){
							updateEdgeImage();
						}
				    	long fin = System.currentTimeMillis();	    	
				    	try {
				    		long sleepTime = (1000/frameRate - (fin - start));
				    		if (sleepTime > 0)
				    			Thread.sleep(sleepTime);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}}}		 
					}
	 
	 private class DualThread implements Runnable{

		 public void run() {
				while(camera.isRunning() && isRunning){
					long start = System.currentTimeMillis();
					if(edgeImage != null){
			    	updateDualImage();
					}
			    	long fin = System.currentTimeMillis();	    	
			    	try {
			    		long sleepTime = (1000/frameRate - (fin - start));
			    		if (sleepTime > 0)
			    			Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
						}}}}
	 
	 public BufferedImage getImage(CameraImageType type){
		 switch (type){
		 case DEFAULT:
			 return currImage;
		 case EDGE:
			 return edgeImage;
		 case DUAL:
			 return dualImage;
		 }
		 return currImage;	 
	 }
	 
	 public int getWidth(){
		 return width;
	 }
	 
	 public int getHeight(){
		 return height;
	 }
	 
	 public int getFrameRate(){
		 return frameRate;
	 }
	 
	 public boolean isRunning(){
		 return isRunning;
	 }
	 
	 public static BufferedImage scaleDown(BufferedImage img) { 
		    Image tmp = img.getScaledInstance(160, 120, Image.SCALE_SMOOTH);
		    BufferedImage dimg = new BufferedImage(160, 120, BufferedImage.TYPE_3BYTE_BGR);
		    Graphics2D g2d = dimg.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();
		    return dimg;
		}
	 
	 public static BufferedImage scaleUp(BufferedImage img){
		    Image tmp = img.getScaledInstance(320, 240, Image.SCALE_SMOOTH);
		    BufferedImage dimg = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2d = dimg.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();
		    return dimg;
	 }
		
		public void setLowThreshold(float val){
			detector.setLowThreshold(val);
		}
		
		public float getLowThreshold(){
			return detector.getLowThreshold();
		}
		
		public void setHighThreshold(float val){
			detector.setHighThreshold(val);
		}
		
		public float getHighThreshold(){
			return detector.getHighThreshold();
		}
		
		public void setGaussianKernelWidth(int val){
			detector.setGaussianKernelWidth(val);
		}
		
		public int getGaussianKernelWidth(){
			return detector.getGaussianKernelWidth();
		}
		
		public void setGaussianKernelRadius(float val){
			detector.setGaussianKernelRadius(val);
		}
		
		public float getGaussianKernelRadius(){
			return detector.getGaussianKernelRadius();
		}
		
		public void setContrastNormalized(boolean val){
			detector.setContrastNormalized(val);
		}
		
		public boolean isContrastNormalized(){
			return detector.isContrastNormalized();
		}
		
		public void resetDetector() throws InterruptedException{
			setLowThreshold(.1f); //default is 2.5
			setHighThreshold(4.5f); //default is 7.5
			setGaussianKernelWidth(16); // default is 16
			setGaussianKernelRadius(1f);
			detectorResize = true; // default is 16
		}
		
		public void setDetectorResize(boolean val){
			if(detectorResize == val) return;
			detectorResize = val;	
		}
		
		public boolean isDetectorResize(){
			return detectorResize;
		}
		
		public void saveImages() throws IOException{
			File currFileOut = new File("curr.jpg");
			File edgeFileOut = new File("edge.jpg");
			File dualFileOut = new File("dual.jpg");
			ImageIO.write(currImage, "jpg", currFileOut);
			ImageIO.write(edgeImage, "jpg", edgeFileOut);
			ImageIO.write(dualImage, "jpg", dualFileOut);
		}
}
