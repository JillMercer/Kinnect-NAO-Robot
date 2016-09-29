package edu.sru.brian.tictactoegame.decisiontree;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import common.structure.AbstractIterator;
import edu.sru.brian.tictactoegame.Game;
import edu.sru.brian.tictactoegame.GameChecker;
import edu.sru.brian.tictactoegame.Markers;
import edu.sru.brian.tictactoegame.WinStatus;
import edu.sru.brian.tictactoegame.WinnerRowID;
import edu.sru.brian.tictactoegame.decisiontree.narytree.NaryTree;


/**
 * File: DefaultGenerateDescisionTree.java
 * @author Brian Atwell
 * Description: This generates a descision tree for tic tac toe AI.
 *
 */
public class DefaultGenerateDescisionTree {
	
	protected ArrayList<Integer> unusedNums = new ArrayList<Integer>();
	protected ArrayList<Integer> numList = new ArrayList<Integer>();
	protected Stack<NaryTree> todo = new Stack<NaryTree>();
	private long nodeCount; 
	
	protected int numEndNodes=0;
	
	public static final int MAX_END_NODES = 5;
	
	protected GameChecker game;
	private Markers initMarker;
	
	protected NaryTree root;
	
	public static final int MAX_USED_NUMBERS = 9;
	
	private TreeWriter writer;
	private String fileNameBase=DEFAULT_FILENAME;
	
	public static String BASE_DIR = System.getProperty("user.dir") + "\\src\\edu\\sru\\brian\\tictactoegame\\decisiontree\\data\\";
	public static String DEFAULT_FILENAME = System.getProperty("user.dir") + "\\src\\edu\\sru\\brian\\tictactoegame\\decisiontree\\data\\testdata";
	
	
	
	public DefaultGenerateDescisionTree()
	{
		root = new NaryTree(TTTGameData.ROOT);
		game = new GameChecker();
		writer= new TreeWriter();
		initMarker=Markers.X;
	}
	
	/**
	 * Generate the tree
	 * @param Markers initMarker
	 * @param String fileName
	 * @return NaryTree
	 */
	public NaryTree generateTree(Markers initMarker , String fileName)
	{
		ArrayList<NaryTree> top;
		NaryTree curTree;
		TTTGameData curData;
		nodeCount=0;
		this.initMarker = initMarker;
		
		fileNameBase=fileName;
		
		for(int i=0;i<MAX_USED_NUMBERS; i++)
		{
			Integer num = new Integer(i);
			unusedNums.add(num);
			numList.add(num);
			curTree = new NaryTree(new TTTGameData(""+(nodeCount++),NodeStatus.UNDETERMINED, num, initMarker));
			root.addChild(curTree);
		}
		
		top = root.children();
		
		/*
		curTree = top.get(0);
		curData = ((TTTGameData)curTree.value());
		unusedNums.remove(curData.getPosition());
		//todo.push(curTree);
		todo.clear();
		
		//if(outFile.)
		writer.clear();
		writer.setFileName(fileNameBase+"_"+curData.getMarker().toString()+curData.getPosition()+".xml");
		writer = null;
		newNode(9, curTree);
		//writer.saveToFile();
		*/
		
		
		writer.clear();
		for(int i=0; i<top.size();i++)
		{
			System.out.println("Tree index:"+i);
			
			curTree = top.get(i);
			curData = ((TTTGameData)curTree.value());
			unusedNums.remove(new Integer(curData.getPosition()));
			
			writer.setFileName(fileNameBase+"_"+curData.getMarker().toString()+curData.getPosition()+".xml");
			//writer = null;
			newNode(9, curTree);
			writer.saveToFile();
			writer.clear();
			curTree.removeAllChildren();
			unusedNums.add(new Integer(curData.getPosition()));
		}
		
		if(writer!=null)
		{
			File fileObj;
			writer.setFileName(fileNameBase+"_"+initMarker.toString()+"top.xml");
			writer.addNodeAndLink(root);
			for(int i=0; i<top.size();i++)
			{
				curData=((TTTGameData)top.get(i).value());
				fileObj = new File(fileNameBase+"_"+curData.getMarker().toString()+curData.getPosition()+".xml");
				curData.setChildFile(fileObj.getName());
				writer.addNode(curData);
				
			}
			writer.saveToFile();
			writer.clear();
		}
		root.removeAllChildren();
		
		
		
		return root;
	}
	
	/**
	 * Gives you the used numbers of the board used.
	 * @param tree
	 * @return
	 */
	public ArrayList<Integer> usedNumbers(NaryTree tree)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		Iterator iter = todo.iterator();
		
		while(iter.hasNext())
		{
			list.add((Integer)iter.next());
		}
		
		return list;
	}
	
	/**
	 * Reset the stack
	 */
	public void reset()
	{
		todo.clear();
	}
	
	/**
	 * Recursive function to create new nodes.
	 * recursion will stop when level == 0
	 * @param int level
	 * @param NaryTree node
	 */
	public void newNode(int level, NaryTree node)
	{
		Integer curInt;
		NaryTree curNode;
		int size=unusedNums.size();
		NodeStatus curNodeStat;
		NodeStatus parStatus=null;
		TTTGameData curData;
		TTTGameData nodeData;

		todo.push(node);
		
		nodeData = (TTTGameData)node.value();
		game.takeTurn(nodeData.getPosition());
		
		//if(numEndNodes<MAX_END_NODES)
		//{
		if(level>0 && size>0)
		{
			int wins=0;
			int losses=0;
			int ties=0;
			//System.out.println("Startlevel: "+level);
			//System.out.println("Size: "+size);
			for(int i=0;i < size; i++)
			{
				curInt = unusedNums.remove(0);
				curNode = new NaryTree(node,new TTTGameData(""+(nodeCount++),NodeStatus.UNDETERMINED, curInt,Markers.getOpponent(nodeData.getMarker())));
				//System.out.println(curNode.toString());
				node.addChild(curNode);
				newNode(level-1,curNode);
				unusedNums.add(curInt);
			}
			
			Iterator<NaryTree> iter = node.children().iterator();
			parStatus = ((TTTGameData)node.value()).getStatus();
			
			if(iter.hasNext() && parStatus == NodeStatus.UNDETERMINED)
			{
				curNode = iter.next();
				curData = (TTTGameData) curNode.value();
				if(curNode != root)
				{
					parStatus=curData.getStatus();
				}
			
			
				while(iter.hasNext())
				{
					curNode = iter.next();
					curData = (TTTGameData) curNode.value();
					curNodeStat=curData.getStatus();
					
					if(curNode != root && curNodeStat!=parStatus)
					{
						if((curNodeStat==NodeStatus.LOSE && parStatus==NodeStatus.TIE) ||
								(curNodeStat==NodeStatus.TIE && parStatus==NodeStatus.LOSE))
						{
							parStatus=NodeStatus.LOSE;
						}
						else if((curNodeStat==NodeStatus.WIN && parStatus==NodeStatus.TIE) ||
								(curNodeStat==NodeStatus.TIE && parStatus==NodeStatus.WIN))
						{
							parStatus = NodeStatus.WINORTIE;
						}
						else if((curNodeStat==NodeStatus.LOSE && parStatus==NodeStatus.WIN) ||
								(curNodeStat==NodeStatus.WIN && parStatus==NodeStatus.LOSE))
						{
							parStatus = NodeStatus.UNDETERMINED;
						}
						else if((curNodeStat==NodeStatus.LOSE && parStatus==NodeStatus.WINORTIE) ||
								(curNodeStat==NodeStatus.WINORTIE && parStatus==NodeStatus.LOSE))
						{
							parStatus = NodeStatus.UNDETERMINED;
						}
						else if((curNodeStat==NodeStatus.WIN && parStatus==NodeStatus.WINORTIE) ||
								(curNodeStat==NodeStatus.WINORTIE && parStatus==NodeStatus.WIN))
						{
							parStatus = NodeStatus.WINORTIE;
						}
						else if((curNodeStat==NodeStatus.TIE && parStatus==NodeStatus.WINORTIE) ||
								(curNodeStat==NodeStatus.WINORTIE && parStatus==NodeStatus.TIE))
						{
							parStatus = NodeStatus.WINORTIE;
						}
					}
				}
				((TTTGameData)node.value()).setStatus(parStatus);
			}
			
			for(NaryTree child: node.children())
			{
			curData = (TTTGameData)child.value();
			wins+=curData.getWins();
			losses+=curData.getLosses();
			ties+=curData.getTies();
			
			}
			
			curData = (TTTGameData)node.value();
			
			curData.setWins(wins);
			curData.setLosses(losses);
			curData.setTies(ties);
			
			// Add node to writer
			if(writer!=null)
			{
				writer.addNodeAndLink(node);
			}
			
			//System.out.println("End level: "+level);
			//curInt = unusedNums.remove(0);
			//unusedNums.add(curInt);
		}
		else
		{
			numEndNodes++;
			
			Iterator<WinnerRowID> iter;
			WinnerRowID row;
			int[] markerCount = new int[2];
			
			for(int i=0;i<markerCount.length;i++)
			{
				markerCount[i]=0;
			}
			
			iter = game.getWinningRowIDs().iterator();
			
			while(iter.hasNext())
			{
				row = iter.next();
				if(row.getMarker()==Markers.X)
				{
					markerCount[Markers.X.getInt()]++;
				}
				else if(row.getMarker()==Markers.O)
				{
					markerCount[Markers.O.getInt()]++;
				}
			}
			
			if(initMarker==Markers.X)
			{
				nodeData.setWins(3*markerCount[Markers.X.getInt()]);
				nodeData.setLosses(3*markerCount[Markers.O.getInt()]);
			}
			else if(initMarker==Markers.O)
			{
				nodeData.setWins(3*markerCount[Markers.O.getInt()]);
				nodeData.setLosses(3*markerCount[Markers.X.getInt()]);
			}
			
			/*
			if(game.getWinStatus() == WinStatus.X)
			{
				//System.out.println("Win");
				//curData.setStatus(NodeStatus.WIN);
				//setChildrenStatus(node, NodeStatus.WIN);
				nodeData.setStatus(NodeStatus.WIN);
				nodeData.setWins(1);
			}
			else if(game.getWinStatus() == WinStatus.O)
			{
				//System.out.println("Lose");
				//curData.setStatus(NodeStatus.LOSE);
				//setChildrenStatus(node, NodeStatus.LOSE);
				nodeData.setStatus(NodeStatus.LOSE);
				nodeData.setLosses(1);
			}
			else if(game.getWinStatus() == WinStatus.TIE)
			{
				//System.out.println("Tie");
				//curData.setStatus(NodeStatus.TIE);
				//setChildrenStatus(node, NodeStatus.TIE);
				nodeData.setStatus(NodeStatus.TIE);
				nodeData.setTies(1);
			}
			*/
			
			if(writer!=null)
			{
				writer.addNode(nodeData);
			}
		}
		//}
		//else if(writer!=null)
		//{
		//	writer.addNode((TTTGameData)node.value());
		//}
		game.removeTurn(nodeData.getPosition());
		todo.pop();
	}
	
	/**
	 * Set children NodeStatus
	 * @param NaryTree node
	 * @param NodeStatus status
	 */
	public void setChildrenStatus(NaryTree node, NodeStatus status)
	{
		((TTTGameData)node.value()).setStatus(status);
		for(NaryTree n: node.children())
		{
			setChildrenStatus(n, status);
		}
	}
	
	/**
	 * Get the File name base used to generate multiple XML files 
	 * @return
	 */
	public String getFileNameBase()
	{
		return fileNameBase;
	}
	
	/**
	 * Set the file name base use to generate multiple files
	 * @param fileName
	 */
	public void setFileNameBase(String fileName)
	{
		fileNameBase=fileName;
	}
	
	/**
	 * Test win propagation
	 */
	private static void testWinPropagation()
	{
		NaryTree locRoot;
		int numErrors=0;
		int numEndNodes=0;
		DefaultGenerateDescisionTree treeGen = new DefaultGenerateDescisionTree();
		

		locRoot = treeGen.generateTree(Markers.X, BASE_DIR+"dataTree");
		 
		System.out.println("Tree generated");
		
		System.out.println("output branches");
		
		Object[] dataAry;
		TTTGameData sigData;
		AbstractIterator iter = locRoot.topToBottomBranchIterator();
		
		System.out.println("made it here!!");

		
		while(iter.hasNext())
		{
			dataAry = (Object[]) iter.next();
			
			if(dataAry != null)
			{
				treeGen.game.reset(Markers.X);
				treeGen.game.start();
				for(int i=0;i<dataAry.length; i++)
				{
					sigData = (TTTGameData)dataAry[i];
					if(sigData != null)
					{
						treeGen.game.takeTurn(sigData.getPosition());
						System.out.print(sigData.getStatus().toChar()+" ");
					}
				}
				
				sigData = (TTTGameData)dataAry[dataAry.length-1];
				if((treeGen.game.getWinStatus()== WinStatus.X && sigData.getStatus() != NodeStatus.WIN) ||
					(treeGen.game.getWinStatus()== WinStatus.O && sigData.getStatus() != NodeStatus.LOSE) ||
					(treeGen.game.getWinStatus()== WinStatus.TIE && sigData.getStatus() != NodeStatus.TIE))
				{
					System.out.println("Error Win statuses don't match WinStatus: "+
								treeGen.game.getWinStatus().toString()+" NodeStatus: "+
								sigData.getStatus().toChar());
					numErrors++;
				}
				numEndNodes++;
				
				System.out.println("");
			}
		}
		System.out.println("Number of total end Nodes: "+numEndNodes);
		System.out.println("Number of Matching Win Errors: "+numErrors);
	}
	
	/** Level order test
	 * 
	 */
	public static void levelOrderTest()
	{
		NaryTree locRoot;
		NaryTree curNode;
		DefaultGenerateDescisionTree treeGen = new DefaultGenerateDescisionTree();
		

		locRoot = treeGen.generateTree(Markers.X,BASE_DIR+"xDataTree");
		 
		System.out.println("Tree generated");
		
		System.out.println("output branches");
		
		TTTGameData sigData;
		AbstractIterator iter = locRoot.levelorderIterator();
		
		System.out.println("made it here!!");

		long gains = 0;
		
		int selLevel =2;

		int nodeCount =0;
		int lastDepth=-1;
		int curDepth=0;
		int countPerDepth=0;
		
		while(iter.hasNext())
		{
			curNode= (NaryTree)iter.next();
			sigData = (TTTGameData) curNode.value();
			
			curDepth=curNode.depth();
			
			nodeCount++;
			
			if(lastDepth!=curDepth)
			{
				System.out.println("New Depth: "+curDepth+" previouse nodes "+countPerDepth+" previous depth");
				countPerDepth=0;
				lastDepth=curDepth;
			}
			
			if(sigData != null)
			{
				if(lastDepth==curDepth)
				{
					countPerDepth++;
				}
				gains = sigData.getWins()-sigData.getLosses();

				System.out.print("Wins:"+sigData.getWins()+" Losses:"+sigData.getLosses()+" gains:"+gains+" ");
			}
			
			System.out.println("cur Depth: "+curDepth);
			
			if(curDepth > selLevel)
			{
				break;
			}
		}
		
		System.out.println("Node Count: "+nodeCount);
		System.out.println("Tree iteration finished!");
	}
	
	/**
	 * Generate decision tree
	 * @param args
	 */
	public static void main(String []args)
	{
		NaryTree locRoot;
		NaryTree curNode;
		DefaultGenerateDescisionTree treeGen = new DefaultGenerateDescisionTree();
		

		locRoot = treeGen.generateTree(Markers.X,BASE_DIR+"xDataTree");
		 
		System.out.println("Tree generated");
		
		System.out.println("output branches");
		
		TTTGameData sigData;
		AbstractIterator iter = locRoot.levelorderIterator();
		
		System.out.println("made it here!!");

		long gains = 0;
		
		int selLevel =2;

		int nodeCount =0;
		int lastDepth=-1;
		int curDepth=0;
		int countPerDepth=0;
		
		while(iter.hasNext())
		{
			curNode= (NaryTree)iter.next();
			sigData = (TTTGameData) curNode.value();
			
			curDepth=curNode.depth();
			
			nodeCount++;
			
			if(lastDepth!=curDepth)
			{
				System.out.println("New Depth: "+curDepth+" previouse nodes "+countPerDepth+" previous depth");
				countPerDepth=0;
				lastDepth=curDepth;
			}
			
			if(sigData != null)
			{
				if(lastDepth==curDepth)
				{
					countPerDepth++;
				}
				gains = sigData.getWins()-sigData.getLosses();

				System.out.print("Wins:"+sigData.getWins()+" Losses:"+sigData.getLosses()+" gains:"+gains+" ");
			}
			
			System.out.println("cur Depth: "+curDepth);
			
			if(curDepth > selLevel)
			{
				break;
			}
		}
		
		System.out.println("Node Count: "+nodeCount);
		System.out.println("Tree iteration finished!");
		
	}

}
