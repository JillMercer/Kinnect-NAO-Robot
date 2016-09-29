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
 * HoughLineStream
 * @author Brian Atwell
 * Description: Hough line detection stream. Get image
 * convert it opencv and detect lines. Then draw the 
 * detected lines to the image.
 *
 */
public class HoughLineStream extends AbstractImageStream {
	
	private List<Line> listLines;

	
	private BufferedImage image;
	
	public HoughLineStream()
	{
		listLines= new ArrayList<Line>();
	}

	/**
	 * update run the hough line detection algorithm
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		listLines.clear();
		Line curLine;
		if(getInputStream()==null || getInputStream().getImage()==null)
			return;
		Mat src = OpenCVExtension.bufferedImageToMat(getInputStream().getImage());

		
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

				//System.out.println("Lines: col["+c+"] row["+r+"]");

				Imgproc.line(cdst, start, end, new Scalar(RandomGenerator.pRand(245, 10),RandomGenerator.pRand(245, 10),RandomGenerator.pRand(245, 10)), 3);
			}
		}

		// Save the visualized detection.
		image = OpenCVExtension.matToBufferedImage(cdst);
	    //String filename = "lineDetection.png";
	    //System.out.println(String.format("Writing %s", filename));
	    //Imgcodecs.imwrite(filename, cdst);
	}

	/**
	 * Get image
	 */
	@Override
	public BufferedImage getImage() {
		// TODO Auto-generated method stub
		return image;
	}
	
	/**
	 * Get the lines detected
	 * @return
	 */
	public List<Line> getLines()
	{
		return listLines;
	}

}
