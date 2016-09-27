package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** 
 * ImageStreamManager
 * @author Brian Atwell
 * Description: Updates a list of ImageStream
 *
 */
public class ImageStreamManager {
	
	private InterfaceImageStream source;
	private Queue<InterfaceImageStream> imageList;
	private long delay;
	private LocalThread privateThread;
	private boolean isAlive;
	
	/**
	 * Thread for updating image streams
	 * @author Brian Atwell
	 * Description: Update images
	 */
	private class LocalThread extends Thread {
		
		public void run()
		{
			while(isAlive)
			{
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				privateUpdate();
			}
		}
	}
	
	public ImageStreamManager()
	{
		isAlive=true;
		imageList = new LinkedList<InterfaceImageStream>();
		privateThread = new LocalThread();
	}

	/**
	 * Enqueues (adds) input stream to list
	 * @param stream
	 */
	public void enqueue(InterfaceImageStream stream)
	{
		if(imageList.isEmpty())
		{
			stream.setInputStream(source);
		}
		else
		{
			stream.setInputStream(imageList.peek());
		}
		
		imageList.add(stream);
	}
	
	/**
	 * dequeue removes the last element from list
	 * @return
	 */
	public InterfaceImageStream dequeue()
	{
		InterfaceImageStream imageStream=null;
		
		if(!imageList.isEmpty())
		{
			imageStream = imageList.remove();
		}
		
		return imageStream;
	}
	
	/**
	 * returns iterator of list
	 * @return
	 */
	public Iterator<InterfaceImageStream> iterator() {
		return imageList.iterator();
	}
	
	/**
	 * peek at the end of the list
	 * @return
	 */
	public InterfaceImageStream peek()
	{
		InterfaceImageStream imageStream=null;
		
		if(!imageList.isEmpty())
		{
			imageStream = imageList.peek();
		}
		
		return imageStream;
	}
	
	/**
	 * set source image
	 * @param source
	 */
	public void setSource(InterfaceImageStream source)
	{
		this.source = source;
	}
	
	/**
	 * get source image
	 * @return
	 */
	public InterfaceImageStream getSource()
	{
		return source;
	}
	
	/**
	 * get number of elements in the list
	 * @return
	 */
	public int size()
	{
		return imageList.size();
	}
	
	/**
	 * Allows users of ImageStreamManager to update the stream manually
	 */
	public void update()
	{
		if(!privateThread.isAlive())
		{
			privateUpdate();
		}
	}
	
	/**
	 * Update the stream called by localThread
	 */
	private void privateUpdate()
	{
		source.update();
		for(InterfaceImageStream is: imageList)
		{
			is.update();
		}
	}
	
	/**
	 * Start thread to update stream
	 * @param delay
	 */
	public void startThreaded(long delay)
	{
		this.delay=delay;
		privateThread.start();
	}
	
	/**
	 * Stop thread that updates the stream
	 * @throws InterruptedException
	 */
	public synchronized void stopThread() throws InterruptedException
	{
		isAlive=false;
		//privateThread.interrupt();
		privateThread.join();
	}
}
