package edu.sru.brian.tictactoegame.decisiontree.narytree;

import java.util.Stack;

import common.structure.AbstractIterator;

/**
 * Nary Tree Preorder iterator
 * @author Brian Atwell
 * Description: Preorder iterator
 *
 */
public class NTPreorderIterator extends AbstractIterator {

	protected NaryTree root;
	protected Stack todo;

	/**
	 * Constructor
	 * @param root
	 */
	public NTPreorderIterator(NaryTree root)
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
		NaryTree current = root;
		while(current != NaryTree.EMPTY)
		{
			todo.push(current);
			current = current.firstChild();
		}
	}

	/**
	 * Has another node
	 */
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Get current node
	 */
	@Override
	public Object get() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get Next node
	 */
	@Override
	public Object next() {
		// TODO Auto-generated method stub
		return null;
	}

}
