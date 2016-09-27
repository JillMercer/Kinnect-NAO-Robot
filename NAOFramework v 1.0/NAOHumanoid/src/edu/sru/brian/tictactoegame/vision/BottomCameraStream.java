package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;

/**
 * BottomCameraStream
 * @author Brian Atwell
 * Description: Bottom NAO robot camera stream 
 */
public class BottomCameraStream implements InterfaceImageStream{
	
	private TTTGCamera camera;
	
	public BottomCameraStream(TTTGCamera camera)
	{
		this.camera = camera;
	}

	/**
	 * Called by ImageStreamManager to update the image
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		camera.updateBottomImage();
	}

	/**
	 * Gets the current image as a BufferedImage
	 */
	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return camera.getBottomImage();
	}

	/**
	 * Set input image stream
	 * 
	 */
	@Override
	public void setInputStream(InterfaceImageStream stream) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Get input image stream
	 */
	@Override
	public InterfaceImageStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
