package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import edu.sru.thangiah.random.RandomGenerator;

/**
 * SkeletonLineImageProcessor
 * @author Brian Atwell
 * Description: Example of Morphological Skeleton line detection
 * Does not currently work. It was a test to see if this line detection
 * method is applicable.
 * Based off of http://felix.abecassis.me/2011/09/opencv-morphological-skeleton/
 *
 */
public class SkeletonLineImageProcessorV2 {

	private BufferedImage image;
	
	/**
	 * Run the example Morphological Skeleton line detection
	 */
	public void run()
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		String srcFilename = "resources/tttorig.jpg";
		Mat src = Imgcodecs.imread(srcFilename);
		//Mat cdst = new Mat(src.size(), CvType.CV_8UC1);
		
		ImageFrame.imshow("Skeleton", src);
		
		//Imgproc.threshold(src, src, 127, 255, Imgproc.THRESH_BINARY);
		Imgproc.cvtColor(src,src,Imgproc.COLOR_RGB2GRAY);
		
		ImageFrame.imshow("Skeleton", src);
		
		
		Mat skel = new Mat(src.size(), CvType.CV_8UC1, new Scalar(0));
		Mat temp = new Mat(src.size(), CvType.CV_8UC1);
		Mat eroded = new Mat(src.size(), CvType.CV_8UC1);
		
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3));
		
		boolean done;
		do
		{
		  Imgproc.erode(src, eroded, element);
		  Imgproc.dilate(eroded, temp, element); // temp = open(img)
		  Core.subtract(src, temp, temp);
		  Core.bitwise_or(skel, temp, skel);
		  eroded.copyTo(src);
		 /*
		  double max;
		  MinMaxLocResult mmlr;
		  mmlr = Core.minMaxLoc(src);
		  //Core.minMaxLoc(new Mat(src, 0, max));
		  
		  max = mmlr.maxVal;
		  done = (max == 0);
		  */
		  done = (Core.countNonZero(src) == 0);
		} while (!done);
		
		ImageFrame.imshow("Skeleton", skel);

		// Save the visualized detection.
	    String filename = "skeletonLine.png";
	    System.out.println(String.format("Writing %s", filename));
	    Imgcodecs.imwrite(filename, skel);
		
	}
	
	/**
	 * Main method to run the Morphological line detection
	 * @param args
	 */
	public static void main(String []args)
	{
		SkeletonLineImageProcessorV2 proc = new SkeletonLineImageProcessorV2();
		proc.run();
	}
}
