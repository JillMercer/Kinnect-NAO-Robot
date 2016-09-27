package edu.sru.brian.tictactoegame.decisiontree;

/**
 * File:DragListener.java
 * @author Brian
 *	Description: Provides a drag event
 */
public interface DragListener {
	
	/**
	 * Called on drag
	 * @param NodePanel curNode
	 * @param double x
	 * @param double y
	 */
	public void onDrag(NodePanel curNode, double x, double y);

}
