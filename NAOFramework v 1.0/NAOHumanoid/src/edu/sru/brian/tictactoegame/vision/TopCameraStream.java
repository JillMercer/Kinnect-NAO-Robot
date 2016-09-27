package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;

/**
 * TopCameraStream
 * @author Brian Atwell
 * Description: Top Camera from Nao robot as an image stream
 */
public class TopCameraStream implements InterfaceImageStream{
	
	TTTGCamera camera;
	
	public TopCameraStream(TTTGCamera camera)
	{
		this.camera = camera;
	}

	/**
	 * Update the NAO robots top camera
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		camera.updateTopImage();
	}

	/**
	 * Get Image
	 */
	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return camera.getTopImage();
	}

	/**
	 * Set input stream
	 */
	@Override
	public void setInputStream(InterfaceImageStream stream) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Get input stream
	 */
	@Override
	public InterfaceImageStream getInputStream() {
		// TODO Auto-generated method stub
		return null;
	}

}
