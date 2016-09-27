package edu.sru.brian.tictactoegame.decisiontree.narytree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import common.orderedstructure.NaturalComparator;
import common.orderedstructure.OrderedStructure;
import common.structure.AbstractIterator;
import common.structure.AbstractStructure;

/**
 * NaryTree
 * @author Brian Atwell
 * Description: Implements an Nary Tree
 */
public class NaryTree {

	protected Object val; // value associated with node
	
	protected NaryTree parent;
	
	protected ArrayList<NaryTree> children = new ArrayList<NaryTree>();
	
	public static final NaryTree EMPTY = new NaryTree();
	
	/**
	 * Default Constructor
	 */
	public NaryTree()
	{
		this(null);
	}
	
	/**
	 * Constructor set the data part of the tree node
	 * @param value
	 */
	public NaryTree(Object value)
	{
		val=value;
		parent=null;
	}
	
	/**
	 * Constructor set the data part of the tree node.
	 * And set the parent to par.
	 * @param par
	 * @param value
	 */
	public NaryTree(NaryTree par, Object value)
	{
		val=value;
		parent=par;
	}
	
	/**
	 * Constructor set the data part of the tree node.
	 * Set the parent to par and add list to children
	 * @param par
	 * @param value
	 * @param list
	 */
	public NaryTree(NaryTree par, Object value, ArrayList<NaryTree> list)
	{
		val=value;
		children = list;
		parent=par;
	}
	
	/**
	 * Constructor set the data part of the tree node.
	 * Add children from list
	 * @param value
	 * @param list
	 */
	public NaryTree(Object value, ArrayList<NaryTree> list)
	{
		val=value;
		children = list;
	}
	
	/**
	 * Get children
	 * @return
	 */
	public ArrayList<NaryTree> children()
	{
		return children;
	}
	
	/**
	 * Clear tree
	 */
	public void clear()
	{
		removeAllChildren(this);
	}
	
	/**
	 * Get parent
	 * @return
	 */
	public NaryTree parent()
	{
		return parent;
	}
	
	/**
	 * Add children
	 * @param list
	 */
	public void addChildren(ArrayList<NaryTree> list)
	{
		for(NaryTree i: list)
		{
			children.add(i);
			i.setParent(this);
		}
	}
	
	/**
	 * Add child
	 * @param childTree
	 */
	public void addChild(NaryTree childTree)
	{
		children.add(childTree);
		childTree.setParent(this);
	}
	
	/**
	 * Get nth child
	 * @param index
	 * @return
	 */
	public NaryTree nthChild(int index)
	{
		if(children.size() > index)
		{
			return children.get(index);
		}
		
		return NaryTree.EMPTY;
	}
	
	/**
	 * Get first child
	 * @return
	 */
	public NaryTree firstChild()
	{
		if(children.size() >= 1)
		{	
			return children.get(0);
		}
		
		return NaryTree.EMPTY;
	}
	
	/**
	 * Detach child without removing its children
	 * @param pos
	 * @return
	 */
	public NaryTree detachChild(int pos)
	{
		NaryTree child = null;
		if(pos < children.size())
		{
			child=children.remove(pos);
		}
		
		return child;
	}
	
	/**
	 * Detach first
	 * @return
	 */
	public NaryTree detachFirst()
	{
		NaryTree child = null;
		if(!children.isEmpty())
		{
			child=children.remove(0);
		}
		
		return child;
	}
	
	/**
	 * Get last child
	 * @return
	 */
	public NaryTree lastChild()
	{
		if(children.size() > 1)
		{
			return children.get(children.size()-1);
		}
		
		return NaryTree.EMPTY;
	}
	
	/**
	 * Get number of direct children
	 * @return
	 */
	public int getDirectChildrenSize()
	{
		return children.size();
	}
	
	/**
	 * Get direct children Iterator
	 * @return
	 */
	public Iterator<NaryTree> getDirectChildrenIterator()
	{
		return children.iterator();
	}
	
	/**
	 * Remove all children
	 */
	public void removeAllChildren()
	{
		removeAllChildren(this);
	}
	
	/**
	 * Acutal code to remove all children recursively
	 * @param node
	 */
	protected void removeAllChildren(NaryTree node)
	{
		int size=0;
		size = node.children().size();
		for(int i=0;i<size;i++)
		{
			if(!node.children().get(0).isEmpty())
			{
				removeAllChildren(node.children().get(0));
				node.children().remove(0);
			}
			else
			{
				node.children().remove(0);
			}
		}
	}
	
	/**
	 * Remove child
	 * @param child
	 */
	public void removeChild(NaryTree child)
	{
		removeAllChildren(child);
		children.remove(child);
	}
	
	/**
	 * Remove child from post
	 * @param pos
	 */
	public void removeChild(int pos)
	{
		NaryTree child;
		if(pos < children.size())
		{
			child=children.get(pos);
			removeAllChildren(child);
			children.remove(child);
		}
	}
	
	/**
	 * Remove children
	 * @param list
	 */
	public void removeChildren(ArrayList<NaryTree> list)
	{
		for(NaryTree i: list)
		{
			removeAllChildren(i);
			children.remove(i);
		}
	}
	
	/**
	 * Set children
	 * @param list
	 */
	public void setChildren(ArrayList<NaryTree> list)
	{
		removeAllChildren();
		children= list;
	}
	
	/**
	 * Set parent
	 * @param par
	 */
	public void setParent(NaryTree par)
	{
		parent=par;
	}
	
	/**
	 * Get size
	 * @return
	 */
	public int size()
	{
		int total=0;
		
		if(children.size()==0)
		{
			return 1;
		}
		
		for(NaryTree i: children)
		{
			total+=i.size();
		}
		
		return total;
	}
	
	/**
	 * get root
	 * @return
	 */
	public NaryTree root()
	{
		if(parent() == null)
		{
			return this;
		}
		else
		{
			return parent.root();
		}
	}
	
	/**
	 * get height
	 * @return
	 */
	public int height()
	{
		int height=-1;
		if(isEmpty())
		{
			return -1;
		}
		
		for(NaryTree i: children)
		{
			height = 1 + Math.max(i.height(), height);
		}
		
		return height;
	}
	
	/**
	 * Get depth
	 * @return
	 */
	public int depth()
	{
		if(parent() == null)
		{
			return 0;
		}
		
		return 1+parent.depth();
	}
	
	/**
	 * Is this equal to an empty tree
	 * @return
	 */
	public boolean isEmpty()
	{
		return this == EMPTY;
	}
	
	/**
	 * iterator
	 * @return
	 */
	public Iterator iterator()
	{
		return inorderIterator();
	}
	
	/**
	 * preorder iterator
	 * @return
	 */
	public AbstractIterator preorderIterator()
	{
		return new NTPreorderIterator(this);
	}
	
	/**
	 * Inorder iterator
	 * @return
	 */
	public AbstractIterator inorderIterator()
	{
		return new NTInorderIterator(this);
	}
	
	/**
	 * Postorder Iterator
	 * @return
	 */
	public AbstractIterator postorderIterator()
	{
		return new NTPostorderIterator(this);
	}
	
	/**
	 * Levelorder iterator
	 * @return
	 */
	public AbstractIterator levelorderIterator()
	{
		return new NTLevelorderIterator(this);
	}
	
	/**
	 * Goes from root to leaves returns an array representation of the data
	 * @return
	 */
	public AbstractIterator topToBottomBranchIterator()
	{
		return new NTTopToBottomBranchIterator(this);
	}
	
	/**
	 * Get Data
	 * @return
	 */
	public Object value()
	{
		return val;
	}
	
	/**
	 * Set data
	 * @param value
	 */
	public void setValue(Object value)
	{
		this.val = value;
	}
	
	/**
	 * Tree to string
	 */
	public String toString()
	{
		if(isEmpty())
		{
			return "<N-aryTree: empty>";
		}
		StringBuffer s = new StringBuffer();
		s.append("<BinaryTree "+value());
		
		for(NaryTree i: children)
		{
			if(!i.isEmpty())
			{
				s.append(" "+i);
			}
			else
			{
				s.append(" -");
			}
		}
		return s.toString();
	}
}
