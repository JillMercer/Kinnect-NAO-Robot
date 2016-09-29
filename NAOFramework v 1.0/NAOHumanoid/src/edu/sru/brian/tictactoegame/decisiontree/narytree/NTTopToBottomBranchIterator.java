package edu.sru.brian.tictactoegame.decisiontree.narytree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import common.structure.AbstractIterator;
import edu.sru.brian.tictactoegame.Markers;
import edu.sru.brian.tictactoegame.decisiontree.NodeStatus;
import edu.sru.brian.tictactoegame.decisiontree.TTTGameData;

/**
 * Branch iterator
 * @author Brian Atwell
 * Description: Leaf level iterator returns an array of data of nodes from
 *  root to leaf.
 *
 */
public class NTTopToBottomBranchIterator  extends AbstractIterator {

	protected NaryTree root;
	protected Stack todo;
	protected Stack data = new Stack();
	
	public NTTopToBottomBranchIterator(NaryTree root)
	{
		todo = new Stack();
		this.root = root;
		reset();
	}
	
	/**
	 * Reset iterator
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		todo.clear();
		data.clear();
		NaryTree current = root;
		while(current != NaryTree.EMPTY)
		{
			todo.push(new Integer(0));
			todo.push(current);
			if(current != root)
			data.add(current.value());
			current = current.firstChild();
		}
	}

	/**
	 * Has another node
	 */
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return !todo.isEmpty();
	}

	/**
	 * Get current node
	 */
	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return ((NaryTree)todo.peek()).value();
	}

	/**
	 * Get next node
	 */
	@Override
	public Object next() {
		// TODO Auto-generated method stub
		if(todo.isEmpty())
		{
			return null;
		}
		NaryTree old = (NaryTree) todo.pop();
		NaryTree curTree;
		Integer curPos = ((Integer) todo.pop())+1;
		
		Object[] result = data.toArray();
		data.pop();
		
		while(!todo.isEmpty() && curPos.intValue() >= old.getDirectChildrenSize())
		{
			//System.out.println("curInt"+curPos);
			if(old != root && !data.isEmpty())
			{
				data.pop();
			}
			old = (NaryTree) todo.pop();
			curPos = ((Integer) todo.pop())+1;
		}
		
		if(curPos < old.getDirectChildrenSize())
		{
			curTree = old.children().get(curPos);
			todo.push(curPos);
			todo.push(old);
			if(old != root)
			{
				data.push(old.value());
			}
			//data.
			
			todo.push(new Integer(0));
			todo.push(curTree);
			data.push(curTree.value());
			
			while(curTree.getDirectChildrenSize() > 0)
			{
				curTree = curTree.firstChild();
				todo.push(new Integer(0));
				todo.push(curTree);
				data.push(curTree.value());
			}
		}
		
		return result;
	}
	
	/**
	 * Test this class
	 * @param args
	 */
	public static void main(String[] args)
	{
		NaryTree curNode;
		NaryTree seclvl;
		NaryTree thirdlvl;
		NaryTree locRoot = new NaryTree(null);
		
		//Level 1
		seclvl = new NaryTree(new TTTGameData(""+1,NodeStatus.UNDETERMINED, 0, Markers.X));
		locRoot.addChild(seclvl);
		
			// Level 2
			thirdlvl = new NaryTree(new TTTGameData(""+2,NodeStatus.UNDETERMINED, 1, Markers.O));
			seclvl.addChild(thirdlvl);
		
				// Level 3
				curNode = new NaryTree(new TTTGameData(""+3,NodeStatus.UNDETERMINED, 2, Markers.X));
				thirdlvl.addChild(curNode);
			
			// Level 2
			thirdlvl = new NaryTree(new TTTGameData(""+4,NodeStatus.UNDETERMINED, 2, Markers.O));
			seclvl.addChild(thirdlvl);
		
		
				// Level 3
				curNode = new NaryTree(new TTTGameData(""+5,NodeStatus.UNDETERMINED, 1, Markers.X));
				thirdlvl.addChild(curNode);
		
		// Level 1
		seclvl = new NaryTree(new TTTGameData(""+6,NodeStatus.UNDETERMINED, 1, Markers.X));
		locRoot.addChild(seclvl);
		
			// Level 2
			thirdlvl = new NaryTree(new TTTGameData(""+7,NodeStatus.UNDETERMINED, 0, Markers.O));
			seclvl.addChild(thirdlvl);
				
				// Level 3
				curNode = new NaryTree(new TTTGameData(""+8,NodeStatus.UNDETERMINED, 2, Markers.X));
				thirdlvl.addChild(curNode);
					
			// Level 2
			thirdlvl = new NaryTree(new TTTGameData(""+9,NodeStatus.UNDETERMINED, 2, Markers.O));
			seclvl.addChild(thirdlvl);
				
				
				// Level 3
				curNode = new NaryTree(new TTTGameData(""+10,NodeStatus.UNDETERMINED, 0, Markers.X));
				thirdlvl.addChild(curNode);
		
		// Level 1
		seclvl = new NaryTree(new TTTGameData(""+11,NodeStatus.UNDETERMINED, 2, Markers.X));
		locRoot.addChild(seclvl);
		
			// Level 2
			thirdlvl = new NaryTree(new TTTGameData(""+12,NodeStatus.UNDETERMINED, 0, Markers.O));
			seclvl.addChild(thirdlvl);
				
				// Level 3
				curNode = new NaryTree(new TTTGameData(""+13,NodeStatus.UNDETERMINED, 1, Markers.O));
				thirdlvl.addChild(curNode);
					
			// Level 2
			thirdlvl = new NaryTree(new TTTGameData(""+14,NodeStatus.UNDETERMINED, 1, Markers.O));
			seclvl.addChild(thirdlvl);
				
				
				// Level 3
				curNode = new NaryTree(new TTTGameData(""+15,NodeStatus.UNDETERMINED, 0, Markers.O));
				thirdlvl.addChild(curNode);
				
		Object[] dataAry;
		TTTGameData sigData;
		AbstractIterator iter = locRoot.topToBottomBranchIterator();
		
		System.out.println("made it here!!");
		
		while(iter.hasNext())
		{
			dataAry = (Object[]) iter.next();
			
			if(dataAry != null)
			{
			
				for(int i=0;i<dataAry.length; i++)
				{
					sigData = (TTTGameData)dataAry[i];
					if(sigData != null)
					{
						System.out.print(sigData.getPosition()+" ");
					}
				}
				System.out.println("");
			}
			
		}
	}

}
