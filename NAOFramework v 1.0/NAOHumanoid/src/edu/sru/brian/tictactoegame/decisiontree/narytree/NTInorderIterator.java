package edu.sru.brian.tictactoegame.decisiontree.narytree;

import java.util.Stack;

import common.structure.AbstractIterator;

/**
 * File: NTInorderIterator.java
 * @author Brian Atwell
 * Description: inorder Nary tree iterator
 *
 */
public class NTInorderIterator extends AbstractIterator {
	
	protected NaryTree root;
	protected Stack todo;

	public NTInorderIterator(NaryTree root)
	{
		todo = new Stack();
		this.root = root;
		reset();
	}
	
	/**
	 * Resets the iterator to the starting position.
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		todo.clear();
		NaryTree current = root;
		while(current != NaryTree.EMPTY)
		{
			todo.push(current);
			current = current.firstChild();
		}
	}

	/**
	 * Is there another node to get
	 */
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return todo.isEmpty();
	}

	/**
	 * Gets the current node data
	 */
	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return ((NaryTree)todo.peek()).value();
	}

	/**
	 * Get the next node data
	 */
	@Override
	public Object next() {
		// TODO Auto-generated method stub
		NaryTree old = (NaryTree) todo.pop();
		
		Object result = old.value();
		
		//if(!old)
		
		return null;
	}

}
