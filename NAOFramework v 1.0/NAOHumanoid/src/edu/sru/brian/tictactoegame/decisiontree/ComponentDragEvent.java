package edu.sru.brian.tictactoegame.decisiontree;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

/**
 * File: ComponentDragEvent.java
 * @author Brian Atwell
 * Description: This provides Dragging for components. But was abandoned.
 * @deprecated
 *
 */
public class ComponentDragEvent extends ComponentEvent {
	
	Point delta;

	/*
	public ComponentDragEvent(Component source, int id) {
		super(source, id);
		// TODO Auto-generated constructor stub
	}
	*/
	
	public ComponentDragEvent(ComponentEvent source, Point delta) {
		super(source.getComponent(), source.getID());
		// TODO Auto-generated constructor stub
		this.delta = delta;
	}
	
	public ComponentDragEvent(ComponentEvent source, double x, double y) {
		super(source.getComponent(), source.getID());
		// TODO Auto-generated constructor stub
		this.delta = new Point();
		delta.setLocation(x, y);
	}
	
	public ComponentDragEvent(MouseEvent source, Point delta) {
		super(source.getComponent(), source.getID());
		// TODO Auto-generated constructor stub
		this.delta = delta;
	}
	
	public ComponentDragEvent(MouseEvent source, double x, double y) {
		super(source.getComponent(), source.getID());
		// TODO Auto-generated constructor stub
		this.delta = new Point();
		delta.setLocation(x, y);
	}
	
	public double getDeltaX()
	{
		return delta.getX();
	}
	
	public double getDeltaY()
	{
		return delta.getY();
	}
	
	public Point getDeltaPoint()
	{
		return delta;
	}

}
