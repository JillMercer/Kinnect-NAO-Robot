package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;

/**
 * InterfaceImageSource
 * @author Brian Atwell
 * Description: An interface for image source
 *
 */
public interface InterfaceImageSource {
	
	/**
	 * Update the image, apply processing or pull image from source
	 * in this method
	 */
	public void update();
	
	/**
	 * Get the image
	 * @return
	 */
	public BufferedImage getImage();

}
