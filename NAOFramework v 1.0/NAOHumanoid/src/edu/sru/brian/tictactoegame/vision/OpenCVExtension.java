package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * OpenCVExtension
 * @author Brian Atwell
 * Description: Provides static functions for converting 
 * opencv image files to BufferedImage and BufferedImages to opencv.
 *
 */
public class OpenCVExtension {
	
	/**
	 * Convert BufferedImage to Opencv Mat image
	 * @param bi
	 * @return
	 */
	public static Mat bufferedImageToMat(BufferedImage bi) {
	  Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
	  byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
	  mat.put(0, 0, data);
	  return mat;
	}
	
	/**
	 * Convert opencv Mat image to BufferedImage
	 * @param in
	 * @return
	 */
	public static BufferedImage matToBufferedImage(Mat in) {
		BufferedImage out;
		
		Mat dst= new Mat();
		if(in.type() == CvType.CV_8UC1)
		{
			//Imgproc.cvtColor(in, dst, Imgproc.COLOR_GRAY2BGR);
			dst = in;
		}else if(in.type() == CvType.CV_8UC3)
		{
			Imgproc.cvtColor(in, dst, Imgproc.COLOR_RGB2BGR);
		}
		else 
		{
			return null;
		}
        byte[] data = new byte[dst.height() * dst.width() * (int)dst.elemSize()];
        int type;
        dst.get(0, 0, data);

        if(dst.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(in.width(), in.height(), type);
        
        //System.out.println("Width: "+in.width()+" height: "+in.height()+" type: "+CvType.typeToString(in.type()));

        out.getRaster().setDataElements(0, 0, dst.width(),dst.height(), data);
        return out;
	}
	
	/**
	 * Convert opencv Mat image to BufferedImage
	 * @param in
	 * @return
	 */
	public static BufferedImage matGrayToBufferedImage(Mat in) {
		BufferedImage out;
		
		Mat dst= new Mat();
		if(in.type() == CvType.CV_8UC1)
		{
			Imgproc.cvtColor(in, dst, Imgproc.COLOR_GRAY2BGR);
			//dst=in;
			System.out.println("Grayscale");
		}else if(in.type() == CvType.CV_8UC3)
		{
			Imgproc.cvtColor(in, dst, Imgproc.COLOR_RGB2BGR);
		}
		else 
		{
			return null;
		}
        byte[] data = new byte[dst.height() * dst.width() * (int)dst.elemSize()];
        int type;
        dst.get(0, 0, data);

        if(dst.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        out = new BufferedImage(in.width(), in.height(), type);
        
        //System.out.println("Width: "+in.width()+" height: "+in.height()+" type: "+CvType.typeToString(in.type()));

        out.getRaster().setDataElements(0, 0, in.width(), in.height(), data);
        return out;
	}

}
