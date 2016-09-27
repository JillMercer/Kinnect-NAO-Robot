package edu.sru.brian.tictactoegame.vision;

import org.opencv.core.Point;

/**
 * Line
 * @author Brian Atwell
 * Description: Stores a start Point and end point of a line.
 */
public class Line {
	private Point start;
	private Point end;
	private double slope;
	
	public Line(Point start, Point end)
	{
		slope = (end.y-start.y)/(end.x -start.x);
	}
	
	/**
	 * Calculate the slope
	 */
	private void calcSlope()
	{
		slope = (end.y-start.y)/(end.x -start.x);
	}
	
	/**
	 * Get the end point
	 * @return Point
	 */
	public Point getEnd()
	{
		return end;
	}
	
	/**
	 * Get Slope
	 * @return Point
	 */
	public double getSlope()
	{
		return slope;
	}
	
	/**
	 * Get Start point
	 * @return Point
	 */
	public Point getStart()
	{
		return start;
	}
	
	/**
	 * Set End point
	 * @param newEnd
	 */
	public void setEnd(Point newEnd)
	{
		end=newEnd;
		calcSlope();
	}
	
	/**
	 * Set the start point
	 * @param newStart
	 */
	public void setStart(Point newStart)
	{
		start=newStart;
		calcSlope();
	}

}
