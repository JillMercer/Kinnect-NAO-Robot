package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

/**
 * WebCameraStream
 * @author Brian Atwell
 * Description: Implements the web camera as an image stream
 *
 */
public class WebCameraStream extends AbstractImageStream {
	
	private VideoCapture cap;
	private BufferedImage buffImage;
	private Mat matImage;
	
	/**
	 * Create a basic web camera stream
	 */
	public WebCameraStream()
	{
		cap = new VideoCapture(0);
	}
	
	/**
	 * Create a web camera stream with width and height
	 * @param width
	 * @param height
	 */
	public WebCameraStream(int width, int height)
	{
		cap = new VideoCapture(0);
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * Create a web camera 
	 * @param vidID
	 * @param width
	 * @param height
	 */
	public WebCameraStream(int vidID, int width, int height)
	{
		cap = new VideoCapture(vidID);
		setWidth(width);
		setHeight(height);
	}

	/**
	 * Updates the image from the web camera
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(cap.isOpened())
		{
			matImage = new Mat();
			//image = new Mat(640, 480, CvType.CV_32S);
			cap.read(matImage);
			buffImage = OpenCVExtension.matToBufferedImage(matImage);
		}
		
	}

	/**
	 * Get the current image stream with the width and height
	 */
	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return buffImage;
	}
	
	/**
	 * Get the current image width of the web camera
	 * @return
	 */
	public int getWidth()
	{
		return (int)cap.get(Videoio.CV_CAP_PROP_FRAME_WIDTH);
	}
	
	/**
	 * Get the current image height of the web camera
	 * @return
	 */
	public int getHeight()
	{
		return (int) cap.get(Videoio.CV_CAP_PROP_FRAME_HEIGHT);
	}
	
	/**
	 * Set the width of the web camera image
	 * @param width
	 */
	public void setWidth(int width)
	{
		cap.set(Videoio.CV_CAP_PROP_FRAME_WIDTH,width);
	}
	
	/**
	 * Set the height of the web camera image
	 * @param height
	 */
	public void setHeight(int height)
	{
		cap.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT,height);
	}
	
	/**
	 * Save the web camera image to file.
	 * @param filename
	 */
	public void saveImage(String filename)
	{
		Imgcodecs.imwrite(filename, matImage);
	}
	
	/**
	 * stops the web camera from capturing images
	 */
	public void release()
	{
		cap.release();
	}

}
