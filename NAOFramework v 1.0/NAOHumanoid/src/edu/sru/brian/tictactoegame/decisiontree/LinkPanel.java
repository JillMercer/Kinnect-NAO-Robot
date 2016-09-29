package edu.sru.brian.tictactoegame.decisiontree;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

/**
 * File:LinkPanel.java
 * @author Brian Atwell
 * Description: Link two nodes
 */
public class LinkPanel extends JPanel {
	
	private Hashtable<NodePanel, NodePanel> links;
	private NodePanel startNode=null;
	private Point curMouse=null;

	public LinkPanel(Hashtable<NodePanel, NodePanel> links) {
		// TODO Auto-generated constructor stub
		this.links = links;
	}

	public LinkPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public LinkPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public LinkPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		
		Iterator<Map.Entry<NodePanel, NodePanel>> iter;
		Entry<NodePanel, NodePanel> elem;
		NodePanel par;
		NodePanel child;
		
		iter = links.entrySet().iterator();
		
		while(iter.hasNext())
		{
			elem = iter.next();
			
			par = elem.getValue();
			child = elem.getKey();
			
			g.drawLine(par.getX()+(par.getWidth()/2), par.getY()+(par.getHeight()/2), child.getX()+(child.getWidth()/2), child.getY()+(child.getHeight()/2));
		}
		
		if(startNode != null && curMouse != null)
		{
			g.drawLine(startNode.getX()+(startNode.getWidth()/2), startNode.getY()+(startNode.getHeight()/2), curMouse.x, curMouse.y);
		}
	}
	
	public void setStartNode(NodePanel startNode)
	{
		this.repaint();
		this.startNode = startNode;
	}
	
	public NodePanel getStartNode()
	{
		return this.startNode;
	}
	
	public void setMousePosition()
	{
		this.curMouse = this.getMousePosition();
		this.repaint();
	}

	
}
