package edu.sru.thangiah.nao.movement.head;

public class HeadNode 
{
	int xPercent;
	float x;
	float yMax;
	float yMin;
	
	HeadNode previous;
	HeadNode next;
	
	//Creates a new node with the slider value, the actual x value, minimum y value, and maximum y value as determined by Aldebaran's NAO documentation
	HeadNode(int xPercent, float x, float yMax, float yMin)
	{
		this.xPercent = xPercent;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	HeadNode(int xPercent, float x, float yMax, float yMin, HeadNode prev, HeadNode next)
	{
		this.xPercent = xPercent;
		this.yMin = yMin;
		this.yMax = yMax;
		this.setPrevious(prev);
		this.setNext(next);
	}

	public void setNext(HeadNode node) 
	{
		this.next = node;
	}
	
	public void setPrevious(HeadNode node)
	{
		this.previous = node;
	}
	
	public HeadNode getNext()
	{
		return next;
	}
	
	public HeadNode getPrevious()
	{
		return previous;
	}

	public int getXPercent() 
	{
		return this.xPercent;
	}
	
	public String toString()
	{
		String text = new String();
		
		System.out.println(this.xPercent + "% " + this.x + " " + this.yMin + " " + this.yMax);
		
		return text;
	}

	public float getX()
	{
		return this.x;
	}
	
	public float getYMin()
	{
		return this.yMin;
	}
	
	public float getYMax()
	{
		return this.yMax;
	}
}
