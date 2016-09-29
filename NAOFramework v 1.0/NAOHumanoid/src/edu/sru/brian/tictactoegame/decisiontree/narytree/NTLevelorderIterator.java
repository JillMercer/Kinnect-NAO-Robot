package edu.sru.brian.tictactoegame.decisiontree.narytree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import common.structure.AbstractIterator;

/**
 * File: NTLevelorderIterator
 * @author Brian Atwell
 * Description: NaryTree level order iterator implementation
 *
 */
public class NTLevelorderIterator extends AbstractIterator {
	
	protected NaryTree root;
	protected List<NaryTreeData> todo;
	
	/**
	 * Constructor
	 * @param root
	 */
	public NTLevelorderIterator(NaryTree root)
	{
		todo = new LinkedList<NaryTreeData>();
		this.root = root;
		reset();
	}
	
	/**
	 * Reset the iterator to the start node
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		todo.clear();
		NaryTree current = root;
		if(current != NaryTree.EMPTY)
		{
			todo.add(new NaryTreeData(current,0));
		}
	}

	/**
	 * is there another node
	 */
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return !todo.isEmpty();
	}

	/**
	 * Get the current node data
	 */
	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get next node data
	 */
	@Override
	public Object next() {
		// TODO Auto-generated method stub
		
		NaryTreeData curNode;
		NaryTreeData headNode;
		
		if(todo.isEmpty())
		{
			return null;
		}
		
		curNode = todo.get(todo.size()-1);
		
		headNode = todo.get(0);
		
		while(!todo.isEmpty() &&
			headNode.getChildPos() >= headNode.getNode().children().size())
		{
			todo.remove(0);
			if(!todo.isEmpty())
			{
				headNode = todo.get(0);
			}
			else
				
			{
				headNode = null;
			}
		}
		
		if(!todo.isEmpty() && headNode.getChildPos() < headNode.getNode().children.size())
		{
			todo.add(new NaryTreeData(headNode.getNode().children.get(headNode.getChildPos())));
			headNode.incChildPos();
		}		
		
		return curNode.getNode();
	}

}

/**
 * NaryTreeData
 * @author Brian Atwell
 *
 */
class NaryTreeData {
	private NaryTree node;
	private int childPos;
	
	public NaryTreeData(NaryTree node, int initPos)
	{
		this.node=node;
		childPos = initPos;			
	}
	
	public NaryTreeData(NaryTree node)
	{
		this.node=node;
		childPos = 0;			
	}
	
	/**
	 * get Node
	 * @return
	 */
	public NaryTree getNode() {
		return node;
	}
	
	/**
	 * Set Node
	 * @param newNode
	 */
	public void setNode(NaryTree newNode)
	{
		node=newNode;
	}
	
	/**
	 * Get Child Position
	 * @return
	 */
	public int getChildPos() {
		return childPos;
	}
	
	/**
	 * Set Child Pos
	 * @param newPos
	 */
	public void setChildPos(int newPos)
	{
		childPos = newPos;
	}
	
	/**
	 * Increase Child position
	 */
	public void incChildPos() {
		childPos++;
	}
}
