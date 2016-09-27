package edu.sru.brian.tictactoegame.decisiontree;

import edu.sru.brian.tictactoegame.Markers;
import edu.sru.brian.tictactoegame.decisiontree.narytree.NaryTree;

/**
 * File:TTTGameData
 * @author Brian Atwell
 * Description: Stores node data for Decision tree.
 *
 */
public class TTTGameData {
	
	private NodeStatus status;
	private int position;
	private Markers marker;
	private long numWins;
	private long numLosses;
	private long numTies;
	private String name;
	private String childFile;
	private boolean areChildrenLoaded=false;
	
	public final static int MAX_POSITION = 8;
	public final static int MIN_POSITION = 0;
	
	public final static TTTGameData ROOT;
	
	static {
		ROOT = new TTTGameData();
		ROOT.position=-1;
		ROOT.name="Start";
		ROOT.marker=Markers.EMPTY;
		ROOT.numLosses=0;
		ROOT.numWins=0;
		ROOT.numTies=0;
		ROOT.status=NodeStatus.UNDETERMINED;
	}
	
	/**
	 * Creates TTTGameData
	 * @param String name
	 * @param NodeStatus status
	 * @param int position
	 * @param Markers marker
	 */
	public TTTGameData(String name, NodeStatus status, int position, Markers marker)
	{
		this.status = status;
		setPosition(position);
		this.marker = marker;
		this.name=name;
		numWins=0;
		numTies=0;
		numLosses=0;
	}
	
	/**
	 * Creates TTTGameData
	 * @param String name
	 * @param NodeStatus status
	 * @param int position
	 * @param Markers marker
	 * @param String loader
	 */
	public TTTGameData(String name, NodeStatus status, int position, Markers marker, String loader)
	{
		this.status = status;
		setPosition(position);
		this.marker = marker;
		this.name=name;
		numWins=0;
		numTies=0;
		numLosses=0;
		childFile=loader;
	}
	
	public TTTGameData()
	{
		this("", NodeStatus.LOSE, 0, Markers.X);
	}
	
	/**
	 * Get child file
	 * @return String
	 */
	public String getChildFile() {
		return childFile;
	}

	/**
	 * Set Child file
	 * @param childFile
	 */
	public void setChildFile(String childFile) {
		this.childFile = childFile;
	}

	/**
	 * get marker
	 * @return
	 */
	public Markers getMarker()
	{
		return marker;
	}
	
	/**
	 * Get name
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Get losses
	 * @return
	 */
	public long getLosses()
	{
		return numLosses;
	}

	/**
	 * Get position
	 * @return
	 */
	public int getPosition()
	{
		return position;
	}
	
	/**
	 * Get NodeStatus
	 * @return
	 */
	public NodeStatus getStatus()
	{
		return status;
	}
	
	/**
	 * Get Ties
	 * @return
	 */
	public long getTies()
	{
		return numTies;
	}
	
	/**
	 * Get wins
	 * @return
	 */
	public long getWins()
	{
		return numWins;
	}
	
	/**
	 * Can the object load a file
	 * @return
	 */
	public boolean canLoadChildren()
	{
		return childFile!=null;
	}
	
	/**
	 * Are children loaded
	 * @return
	 */
	public boolean areChildrenLoaded()
	{
		return areChildrenLoaded;
	}
	
	/**
	 * Load children
	 * @param thisNode
	 * @param basePath
	 */
	public void loadChildren(NaryTree thisNode, String basePath)
	{
		if(canLoadChildren())
		{
			if(thisNode.children().isEmpty())
			{
				areChildrenLoaded=false;
			}
				
			if(!areChildrenLoaded())
			{
				NaryTree nodeCopy;
				NaryTree curChild;
				int size=0;
				DecisionTreeFromXML xml = new DecisionTreeFromXML(basePath+this.getChildFile());
				
				System.out.println("FILE LOAD: "+basePath+((TTTGameData)thisNode.value()).getChildFile());
				
				nodeCopy=xml.getStartNode(((TTTGameData)thisNode.value()).getName());
				
				if(nodeCopy != null)
				{
					size=nodeCopy.children().size();
					System.out.println("number");
					for(int i=0; i<size;i++)
					{
						curChild = nodeCopy.detachFirst();
						thisNode.addChild(curChild);
					}
				}
				
				areChildrenLoaded=true;
			}
		}
	}
	
	/**
	 * UnloadChildren
	 * @param node
	 */
	public void unLoadChildren(NaryTree node)
	{
		node.clear();
		areChildrenLoaded=false;
	}
	
	/**
	 * Set marker
	 * @param marker
	 */
	public void setMarker(Markers marker)
	{
		this.marker = marker;
	}
	
	/**
	 * Set Name
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Set  Losses
	 * @param losses
	 */
	public void setLosses(long losses)
	{
		this.numLosses=losses;
	}
	
	/**
	 * Set Position
	 * @param pos
	 */
	public void setPosition(int pos)
	{
		if(pos >= MIN_POSITION && pos <= MAX_POSITION)
			this.position = pos;
		else
			this.position = 0;
	}
	
	/**
	 * Set NodeStatus
	 * @param status
	 */
	public void setStatus(NodeStatus status)
	{
		this.status = status;
	}
	
	/**
	 * Set ties
	 * @param ties
	 */
	public void setTies(long ties)
	{
		this.numTies = ties;
	}
	
	/**
	 * Set Wins
	 * @param wins
	 */
	public void setWins(long wins)
	{
		this.numWins=wins;
	}
	
	/**
	 * Calculate Gain
	 * @return
	 */
	public long calculateGain()
	{
		return numWins-numLosses;
	}
	
	/**
	 * Convert to String
	 */
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		s.append("[TTTGameData ");
		s.append("Marker: ");
		s.append(marker);
		s.append(" Position: ");
		s.append(position);
		s.append(" Status: ");
		s.append(status);
		s.append(" ]");
		
		return s.toString();
	}
}
