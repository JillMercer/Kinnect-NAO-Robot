package edu.sru.thangiah.demo.redball;

import java.awt.Color;

public interface CircleBlobPropertiesInterface {

	/**
	 * Gets color of blob to find.
	 */
	public Color getColor();
	
	/**
	 * Gets color threshold from 0 to 255
	 */
	public int getColorThreshold();
	
	/**
	 * Gets the minimum size in pixels
	 */
	public int getMinSize();
	
	/**
	 * Gets shape
	 * 
	 *  "Circle" if object is circular
	 *  or "Unknown" if object is a generic blob.
	 */
	public String getShape();
	
	/**
	 * Gets span of the object in meters
	 * 
	 * (For a ball it is the diameter)
	 */
	public float getSpan();
	
	/**
	 * Set the color of the blob to find
	 */
	public void setColor(Color color);
	
	/**
	 * Set the threshold
	 * 
	 * Can be from 0 to 255
	 */
	public void setColorThreshold(int threshold);
	
	/**
	 * Set the size in pixels
	 */
	public void setMinSize(int sizePixels);
	
	/**
	 * Set the span of the object
	 * 
	 * (For a ball it is the diameter)
	 */
	public void setSpan(float span);
}
