package edu.sru.brian.tictactoegame.vision;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * StreamPanel
 * @author Brian Atwell
 * Description: A panel that implements a ImageStream to update the panel
 * and show a user set ImageStream.
 *
 */
public class StreamPanel extends JPanel {
	
	private InterfaceImageStream updateStream;
	private InterfaceImageStream userStream;
	private Dimension size;
	

	/**
	 * Create the panel.
	 */
	public StreamPanel() {
		size = new Dimension();
		updateStream = new InterfaceImageStream() {
			@Override
			public void update() {
				// TODO Auto-generated method stub
				if(userStream != null)
				{
					BufferedImage buffImage;
					userStream.update();
					buffImage = userStream.getImage();
					/*
					if(buffImage != null)
					{
						if(size.height != buffImage.getHeight() ||
							size.width != buffImage.getWidth())
						{
							Point point = getLocation();
							size.height=buffImage.getHeight();
							size.width=buffImage.getWidth();
							setMinimumSize(size);
							setSize(size);
							setBounds(point.x, point.y, size.width, size.height);
						}
					}
					*/
					repaint();
				}
			}

			/**
			 * Get Image
			 */
			@Override
			public BufferedImage getImage() {
				// TODO Auto-generated method stub
				if(userStream != null)
				{
					return userStream.getImage();
				}
				
				return null;
			}

			/**
			 * Set Input Stream
			 */
			@Override
			public void setInputStream(InterfaceImageStream stream) {
				// TODO Auto-generated method stub
				userStream.setInputStream(stream);
			}

			/**
			 * Get Input Stream
			 */
			@Override
			public InterfaceImageStream getInputStream() {
				// TODO Auto-generated method stub
				return userStream;
			}
			
		};
	}
	
	/**
	 * Set Image Stream
	 * @param stream
	 */
	public void setImageStream(InterfaceImageStream stream)
	{
		userStream=stream;
	}
	
	/**
	 * Get User Image Stream
	 */
	public InterfaceImageSource getUserImageStream()
	{
		return userStream;
	}
	
	/**
	 * Get Panel Image stream
	 * @return
	 */
	public InterfaceImageStream getPanelImageStream()
	{
		return updateStream;
	}
	
	/**
	 * Paint the image on the panel
	 */
	public void paint(Graphics g)
	{
		super.paint(g);
		BufferedImage image;
		image = updateStream.getImage();
		if(image != null)
		{
			Point point = getLocation();
			g.drawImage(image, 0, 0, null);
		}
		else
		{
			System.out.println("image is null");
		}
	}

}
