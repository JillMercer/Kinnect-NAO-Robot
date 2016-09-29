package edu.sru.brian.tictactoegame.vision;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import edu.sru.thangiah.random.RandomGenerator;

/**
 * HoughLineImageProcessor
 * @author Brian Atwell
 * Description: Example application of Hough Line detection.
 * It converts the image to grey scale. Then it detects 
 * lines and draws the lines to the image and saves it to file.
 *
 */
public class HoughLineImageProcessor {

	private BufferedImage image;
	
	/**
	 * Run the Hough Line detection example
	 */
	public void run()
	{
		int linCmbDistThreshold=2;
		int linCmbMinLenOvrLap=6;
		double linSlopeThreshold=0.1d;
		List<Line> listLines = new ArrayList<Line>();
		Line curLine;
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		String srcFilename = "resources/tttorig.jpg";
		Mat src = Imgcodecs.imread(srcFilename);
		
		Mat dst, cdst;
		dst= new Mat();
		cdst = new Mat();
		
		Imgproc.Canny(src, dst, 50d, 200d, 3, true);
		Imgproc.cvtColor(dst, cdst, Imgproc.COLOR_GRAY2BGR);
		
		Mat lines;
		lines = new Mat();
		
		int threshold = 65;
	    int minLineSize = 30;
	    int lineGap = 25;
		
		Imgproc.HoughLinesP(dst, lines, 1.27d, Math.PI/180, threshold, minLineSize, lineGap);
		
		double highSlope;
		double lowSlope;
		double[] vec;
		for(int c = 0; c < lines.cols(); c++ )
		{
			for(int r=0; r < lines.rows(); r++)
			{
				vec = lines.get(r, c);
				// x1, y1
				Point start = new Point(vec[0], vec[1]);
				// x2, y2
				Point end = new Point(vec[2], vec[3]);
				
				/*
				curLine = new Line(start, end);
				highSlope=curLine.getSlope()+linSlopeThreshold;
				lowSlope=curLine.getSlope()-linSlopeThreshold; 
				
				for(Line lpln:listLines)
				{
					if(lpln.getSlope() > lowSlope && lpln.getSlope() < highSlope)
					{
					}
				}
				*/

				System.out.println("Lines: col["+c+"] row["+r+"]");

				Imgproc.line(cdst, start, end, new Scalar(RandomGenerator.pRand(245, 10),RandomGenerator.pRand(245, 10),RandomGenerator.pRand(245, 10)), 3);
			}
		}

		// Save the visualized detection.
	    String filename = "lineDetection.png";
	    System.out.println(String.format("Writing %s", filename));
	    Imgcodecs.imwrite(filename, cdst);
		
	}
	
	/**
	 * Main method to run the hough line example
	 * @param args
	 */
	public static void main(String []args)
	{
		HoughLineImageProcessor proc = new HoughLineImageProcessor();
		proc.run();
	}
}
