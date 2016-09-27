package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.IntStream;

import com.aldebaran.qi.*;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALVideoDevice;

import edu.sru.thangiah.nao.module.Module;
import edu.sru.thangiah.nao.vision.VisionInterface;

/** Author: Zachary Kearney
 Last Edited, 2/24/2016
 * @author zrk1002
 * 
 * Edited by Brian Atwell for use with image streams
 *
 */

//https://github.com/lumenrobot/avatar-nao/tree/master/src/main/java/org/lskk/lumen/avatar/nao
//http://doc.aldebaran.com/2-1/naoqi/vision/alvideodevice.html#alvideodevice

public class TTTGCamera extends Module implements VisionInterface{

	 private String moduleFront;
	 private String moduleBottom;
	 private int height;
	 private int width;
	 private Integer cameraIndex;
	 private Integer resolution;
	 private Integer colorspace;
	 private Integer frameRate;
	 private boolean cameraRunning;
	 private ALVideoDevice video;
	 private Session session;
	 private ALMemory memory;
	 private String activeModule;
	 private BufferedImage topImage;
	 private BufferedImage botImage;
	 
		/** ALVideoDevice contains 4 parameters, 
		 * Name - Name of the subscribing module
		 * 
		 * CameraIndex -
		 * 	[0] - The camera on the Top of the head pointing straight
		 * 	[1] - The camera on the Bottom of the head pointing down
		 * 
		 * Resolution - The resolution of the Camera
		 * 	[8] - 40*30px
		 *  [7] - 80*60px
		 *  [0] - 160*120px
		 *  [1] - 320*240px
		 *  [2] - 640*480px
		 *  [3] - 1280*960px
		 *  
		 *  Colorspace - Ranges from 0-16;
		 *  Options are
		 *  [10] - YUV ColorSpace
		 *  [11] - RGB ColorSpace
		 *  YUV functions faster as less processing is performed on nao		 *  
		 *  
		 *  FrameRate - The number of frames the camera records per second
		 */
		 
	 public TTTGCamera(Session session) throws Exception{ // DEFAULT CONSTRUCTORS
		 
		 super(session);
		 this.session = session;
		 cameraIndex = new Integer(0); // Front Camera
		 resolution = new Integer(1); //320*240
		 colorspace = new Integer(11); //Default Colorspace, change is not recommended
		 frameRate= new Integer(30); //Default Framerate
		 cameraRunning = false;
	 } 
	 /**
	  * Runs the video application using the requested modifiers.
	  * ^^^NOTE^^^ The camera modifiers cannot be changed after run method is called.
	  */
	 
	 public void run() throws Exception{
		 
		determineRes();
		video = new ALVideoDevice(this.session);
		reset();
		moduleFront = video.subscribeCamera("VideoStream " + 0 + " #", 0, resolution, colorspace, frameRate);
		moduleBottom = video.subscribeCamera("VideoStream " + 1 + " #", 1, resolution, colorspace, frameRate);
		cameraRunning = true;
		System.out.printf("subscribed with id: %s", moduleFront);
		System.out.printf("subscribed with id: %s", moduleBottom);
		memory = new ALMemory(session);
		memory.subscribeToEvent("MiddleTactilTouched", (value) -> {
			if (((Float) value) > 0.00) {
				try {
					exit();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}});
	 }

	 public void reset() throws Exception {
		List<String> instances = (List<String>) video.getSubscribers();
		for(int i = 0; i < instances.size(); i++){
			if(instances.get(i).substring(0, 11).equals("VideoStream")){
			video.unsubscribe(instances.get(i));
			System.out.printf("\nunsubscribed from id: %s", instances.get(i));
			}
		}
		cameraRunning = false;
	 }
	 
	 /**
	  * Unsubscribes from the Camera module and closes the connection;
	  */
	 
	 public void exit() throws Exception{
		 
		 cameraRunning = false;
		 video.unsubscribe(moduleFront);
		 video.unsubscribe(moduleBottom);
		 System.out.printf("\nunsubscribed from id: %s", moduleFront);
		 
	 }
	 
	 /** 
	  * Switches from Top and Bottom camera. Must be done before run method.
	 * @throws Exception 
	  */
	 
	 public void setCameraIndex(Integer index) throws Exception{
		 if(cameraRunning == false){
		 if(index.equals(0)){
			 cameraIndex = 0;
		 }
		 else if(index.equals(1)){
			 cameraIndex = 1;
		 }
		 else{
			 System.out.println("Error, please enter one of the following values.");
			 System.out.println("[0] - Front Camera");
			 System.out.println("[1] - Rear Camera");
		 }
		 }
		 else{
			 throw new Exception("Camera is running");
		 }
	 }
	 
	 /**
	  *  Changes to the desired resolution. Must be done before run method.
	 * @throws Exception 
	  */
	 
	 public void setResolution(Integer res) throws Exception{
		 if(cameraRunning == false){
		 if(res.equals(0)){
			 width = 160;
			 height = 120;
			 resolution = 0;
		 }
		 else if(res.equals(1)){
			 width = 320;
			 height = 240;
			 resolution = 1;
		 }
		 else if(res.equals(2)){
			 width = 640;
			 height = 480;
			 resolution = 2;
		 }
		 else if(res.equals(3)){
			 width = 1280;
			 height = 960;
			 resolution = 3;
		 }
		 else if(res.equals(7)){
			 width = 80;
			 height = 60;
			 resolution = 7;
		 }
		 else if(res.equals(8)){
			 width = 40;
			 height = 30;
			 resolution = 8;
		 }
		 else{
			System.out.println("Error, please enter one of the following values");
			System.out.println("[8] - 40*30px");
			System.out.println("[7] - 80*60px");
			System.out.println("[0] - 160*120px");
			System.out.println("[1] - 320*240px");
			System.out.println("[2] - 650*480px");
			System.out.println("[3] - 1280*960px");
		 }
		 }
		 else{
			 throw new Exception("Camera is running");
		 }
		 
	 }
	 
	 private void determineRes() {
		 switch(resolution){
		 case 0:
			 height = 120;
			 width = 160;
			 break;
		 case 1:
			 height = 240;
			 width = 320;
			 break;
		 case 2:
			 height = 480;
			 width = 640;
			 break;
		 case 3:
			 height = 960;
			 width = 1280;
			 break;
		 case 8:
			 height = 30;
			 width = 40;
			 break;
		 case 7:
			 height = 60;
			 width = 80;
			 break;
		 }
	 }
	 /**
	  * Returns Camera Index.
	  */

	 public Integer getCameraIndex(){
		 return cameraIndex;
	 }
	 
	 /**
	  * Returns Camera Resolution.
	  */
	 
	 public Integer getResolution(){
		 return resolution;
	 }
	 
	 /**
	  * Returns Frame Rate.
	  */
	 
	 public Integer getFrameRate(){
		 return frameRate;
	 }
	 
	 /**
	  * Returns ColorSpace.
	  */
	 
	 public Integer getColorspace(){
		 return colorspace;
	 }
	 
	 /**
	  * Returns Height of Video.
	  */
	 
	 public int getHeight(){
		 return height;
	 }
	 
	 /**
	  * Returns Width of Video.
	  */
	 
	 public int getWidth(){
		 return width;
	 }
	 
	 /**
	  *  Returns subscribing Module Name.
	  */


	 public byte[] getByteArray(String module) throws Exception{
		 List<Object> imageList = (List<Object>) video.getImageRemote(module);
		 	ByteBuffer buffer = (ByteBuffer)imageList.get(6);
		 	byte[] rawData = buffer.array();
		 	return rawData;
	 }

	 /**
	  * Is the thread running
	  * @return
	  */
	public boolean isRunning(){
		return cameraRunning;
	}

	/**
	 * Get module name
	 */
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return activeModule;
	}
	
	/**
	 * Get top image
	 * @return
	 */
	public BufferedImage getTopImage(){
		return topImage;
	}
	
	/**
	 * Get bottom image
	 * @return
	 */
	public BufferedImage getBottomImage(){
		return botImage;
	}
	
	/**
	 * Update image
	 * @param module
	 * @return
	 */
	public BufferedImage updateImage(String module)
	{
		byte[] rawData;
		int[] pixels;
		BufferedImage image = null;
		try {
			rawData = getByteArray(module);
			pixels = new int[height * width];
	        
	        IntStream.range(0, pixels.length).parallel().forEach(i -> pixels[i] =
	        		((255 & 0xFF) << 24) |
	               ((int)(rawData[(i * 3)] & 0xFF) << 16) | // red
	               ((int)(rawData[i * 3 + 1] & 0xFF) << 8) | // green
	               ((int)(rawData[i * 3 + 2] & 0xFF))); //blue);
	        //credit to justin cather
	        //Looping through the data and converting from hexadecimal into raw pixel data.
	        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR); //RED GREEN BLUE Format
	        image.setRGB(0,0,width,height,pixels,0,width);
	        //Converts BufferedImage into proper format.
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return image;
	}
	
	/**
	 * Update bottom image
	 */
	public void updateBottomImage()
	{
		botImage = updateImage(moduleFront);
	}
	
	/**
	 * Update top image	
	 */
	public void updateTopImage()
	{
		topImage = updateImage(moduleFront);
	}
	
	}
