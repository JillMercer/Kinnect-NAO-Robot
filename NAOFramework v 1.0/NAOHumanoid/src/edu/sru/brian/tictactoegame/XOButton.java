package edu.sru.brian.tictactoegame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JButton;

/**
 * File: XOButton.java
 * @author Brian Atwell
 * Description: Provides X or O buttons for the {@link TicTacToeGUI} 
 *
 */
public class XOButton extends JButton {
	
	private Markers state;
	private int pos;

	public XOButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	public XOButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}
	
	public XOButton(String text, int pos) {
		super(text);
		// TODO Auto-generated constructor stub
		this.pos = pos;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke(10));
		
	    if(state == Markers.X)
	    {
	    	g.setColor(Color.RED);
	    	g.drawLine(0, 0, this.getWidth(), this.getHeight());
	    	g.drawLine(this.getWidth(), 0, 0, this.getHeight());
	    	//System.out.println("X Marker painted");
	    }
	    else if(state == Markers.O)
	    {
	    	g.setColor(Color.BLACK);
	    	g.drawOval(15, 15, this.getWidth()-30, this.getHeight()-30);
	    	//System.out.println("O Marker painted");
	    }
	    else
	    {
	    	//System.out.println("NO Marker painted");
	    }
	}
	
	public void setDisplayState(Markers state)
	{
		this.state = state;
	}
	
	public Markers getDisplayState()
	{
		return this.state;
	}
	
	public int getPosOnBoard()
	{
		return this.pos;
	}

}
