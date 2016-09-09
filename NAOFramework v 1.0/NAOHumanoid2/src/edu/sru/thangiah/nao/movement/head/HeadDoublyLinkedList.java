package edu.sru.thangiah.nao.movement.head;

public class HeadDoublyLinkedList 
{
	private static HeadNode headNode;
	private static HeadNode tailNode;
	private static HeadNode temp;
	
	public void add(int xPercent, float x, float yMin, float yMax)
	{
		HeadNode node = new HeadNode(xPercent, x, yMin, yMax);
		
		if(headNode == null)
		{
			headNode = node;
			headNode.setPrevious(null);
			headNode.setNext(null);
		}
		else
		{
			temp = headNode;
			while(temp.getNext() != null)
			{
				temp = temp.getNext();
			}
			
			temp.setNext(node);
			node.setNext(null);
			node.setPrevious(temp);
			tailNode = node;
		}
		
	}
	
	public String toString()
	{
		String text = new String();
		
		temp = headNode;
		
		do
		{
			System.out.println(temp.xPercent + "% " + temp.x + " " + temp.yMin + " " + temp.yMax);
			temp = temp.getNext();
		}
		while(temp != null);
		
		return text;
	}
	
	public String backString()
	{
		String text = new String();
		
		temp = tailNode;
		
		do
		{
			System.out.println(temp.xPercent + "% " + temp.x + " " + temp.yMin + " " + temp.yMax);
			temp = temp.getPrevious();
		}
		while(temp != null);
		
		return text;
	}
	
	public int getXPercent()
	{
		int xPercent = temp.xPercent;
		
		return xPercent;
	}
	
	public HeadNode getHead()
	{
		return headNode;
	}
}
