package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * FileImageStrea
 * @author Brian Atwell
 * Description: Stream an static image file. This is useful 
 * for image processing
 */
public class FileImageStream extends AbstractImageStream {
	
	private File file;
	private BufferedImage img;
	private boolean reload;
	
	public FileImageStream(File file, boolean reload)
	{
		this.reload=reload;
		this.file = file;
	}

	/**
	 * Updates the image
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(reload && img == null)
		{
			try {
					img = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the current image
	 */
	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return img;
	}

	/**
	 * Reload the image from the file
	 * @return
	 */
	public boolean isReload() {
		return reload;
	}

	/**
	 * Set whether the image will reload from file
	 * @param reload
	 */
	public void setReload(boolean reload) {
		this.reload = reload;
	}

}
