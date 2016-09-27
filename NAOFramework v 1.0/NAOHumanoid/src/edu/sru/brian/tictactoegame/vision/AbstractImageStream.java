package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;

/**
 * AbstractImageStream
 * @author Brian Atwell
 * Description: Implements getting and setting an input stream.
 */
public abstract class AbstractImageStream implements InterfaceImageStream {

	InterfaceImageStream stream;
	
	/**
	 * Set input stream
	 */
	@Override
	public void setInputStream(InterfaceImageStream stream) {
		// TODO Auto-generated method stub
		this.stream = stream;
	}
	
	/**
	 * Get input stream
	 */
	public InterfaceImageStream getInputStream() 
	{
		return this.stream;
	}

}
