package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;

/**
 * Interface image stream 
 * @author Brian Atwell
 * Description: Interface for image stream.
 *
 */
public interface InterfaceImageStream extends InterfaceImageSource {
	
	/**
	 * Set the input stream
	 * @param stream
	 */
	public void setInputStream(InterfaceImageStream stream);
	
	/**
	 * Get the input stream
	 * @return
	 */
	public InterfaceImageStream getInputStream();

	/**
	 * Update the image stream
	 */
	public void update();
	
	/**
	 * Get current image
	 */
	public BufferedImage getImage();
}
