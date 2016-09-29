package edu.sru.brian.tictactoegame.decisiontree;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.sru.brian.tictactoegame.decisiontree.narytree.NaryTree;

/**
 * File: TreeWriter.java
 * @author Brian Atwell
 * Description: Writes a NaryTree to XML file.
 *
 */
public class TreeWriter {
	
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private Element treeElement;
	private Element nodesConfig;
	private Element linkingPhase;
	
	private String fileName;
	
	public TreeWriter(String fileName)
	{
		if(fileName != null)
		{
		 try {
			 	this.fileName = fileName;

				docFactory = DocumentBuilderFactory.newInstance();
				docBuilder = docFactory.newDocumentBuilder();

				// root tree elements
				doc = docBuilder.newDocument();
				treeElement = doc.createElement(GameDTreeXMLConstants.TREE);
				doc.appendChild(treeElement);

				// nodesConfig elements
				nodesConfig = doc.createElement(GameDTreeXMLConstants.NODE_CONFIG);
				treeElement.appendChild(nodesConfig);
				
				
				linkingPhase = doc.createElement(GameDTreeXMLConstants.LINKING_PHASE);
				treeElement.appendChild(linkingPhase);
				

			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  }
		}
		System.out.println("Made it to filename Constructor");
	}
	
	public TreeWriter()
	{
		 try
		 {

			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();

			// root tree elements
			doc = docBuilder.newDocument();
			treeElement = doc.createElement(GameDTreeXMLConstants.TREE);
			doc.appendChild(treeElement);

			// nodesConfig elements
			nodesConfig = doc.createElement(GameDTreeXMLConstants.NODE_CONFIG);
			treeElement.appendChild(nodesConfig);
			
			linkingPhase = doc.createElement(GameDTreeXMLConstants.LINKING_PHASE);
			treeElement.appendChild(linkingPhase);

		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			System.exit(0);
		  }
		 System.out.println("Made it to base Constructor");
	}
	
	/**
	 * Adds a node to the DOM document builder
	 * @param data
	 */
	public void addNode(TTTGameData data)
	{
		Element curNode;
		Element nameNode;
		Element markerNode;
		Element posNode;
		Element statusNode;
		Element winsNode;
		Element lossesNode;
		Element tiesNode;
		Element childFileNode;
		Element displayNode;
		Element dispYNode;
		Element dispXNode;
		if(data==null)
		{
			return;
		}
		
		// nodesConfig elements
		curNode = doc.createElement(GameDTreeXMLConstants.NODE);
		
		// nameNode
		nameNode = doc.createElement(GameDTreeXMLConstants.NAME);
		nameNode.appendChild(doc.createTextNode(data.getName()));
		//System.out.println("Name:"+data.getName());
		curNode.appendChild(nameNode);
		
		/*
		// Display Node
		displayNode = doc.createElement(GameDTreeXMLConstants.DISPLAY);
		curNode.appendChild(displayNode);
		
		// Display X Node
		dispXNode = doc.createElement(GameDTreeXMLConstants.DISPLAY_X);
		dispXNode.appendChild(doc.createTextNode(""+0));
		displayNode.appendChild(dispXNode);
		
		// Display Y Node
		dispYNode = doc.createElement(GameDTreeXMLConstants.DISPLAY_Y);
		dispYNode.appendChild(doc.createTextNode(""+0));
		displayNode.appendChild(dispYNode);
		*/
		
		// markerNode
		markerNode = doc.createElement(GameDTreeXMLConstants.MARKER);
		markerNode.appendChild(doc.createTextNode(data.getMarker().toString()));
		//System.out.println("MARKER:"+data.getMarker().toString());
		curNode.appendChild(markerNode);
		
		// position Node
		posNode = doc.createElement(GameDTreeXMLConstants.POSITION);
		posNode.appendChild(doc.createTextNode(""+data.getPosition()));
		//System.out.println("POSITION:"+data.getPosition());
		curNode.appendChild(posNode);
		
		// Status Node
		statusNode = doc.createElement(GameDTreeXMLConstants.STATUS);
		statusNode.appendChild(doc.createTextNode(data.getStatus().toString()));
		//System.out.println("STATUS:"+data.getStatus().toString());
		curNode.appendChild(statusNode);
		
		// Number of Wins Node
		winsNode = doc.createElement(GameDTreeXMLConstants.NUMWINS);
		winsNode.appendChild(doc.createTextNode(""+data.getWins()));
		curNode.appendChild(winsNode);
		
		// Number of Losses Node
		lossesNode = doc.createElement(GameDTreeXMLConstants.NUMLOSSES);
		lossesNode.appendChild(doc.createTextNode(""+data.getLosses()));
		curNode.appendChild(lossesNode);
		
		// Number of Ties Node
		tiesNode = doc.createElement(GameDTreeXMLConstants.NUMTIES);
		tiesNode.appendChild(doc.createTextNode(""+data.getTies()));
		curNode.appendChild(tiesNode);
		
		//ChildFile
		if(data.canLoadChildren())
		{
			childFileNode = doc.createElement(GameDTreeXMLConstants.CHILD_FILE);
			childFileNode.appendChild(doc.createTextNode(data.getChildFile()));
			curNode.appendChild(childFileNode);
		}
		
		nodesConfig.appendChild(curNode);
	}
	
	/**
	 * removes all nodes
	 */
	public void clear()
	{		
		removeAll(nodesConfig);
		removeAll(linkingPhase);
	}
	
	/**
	 * Removes all subnodes
	 * @param node
	 */
	private static void removeAll(Node node) 
    {
		int size;
		NodeList nl = node.getChildNodes();
		Node curNode;
		
		size=nl.getLength();
        for(int i=0;i<size;i++)
        {
        	curNode = nl.item(0);
        	if(curNode.hasChildNodes())
        	{
        		removeAll(curNode);
        		node.removeChild(curNode);
        	}
        	else
        	{
        		node.removeChild(curNode);
        	}
        }
    }


	/**
	 * Adds all children as links to the treeNode to the document
	 * @param treeNode
	 */
	public void linkNode(NaryTree treeNode)
	{
		Element linker;
		Element linkedNode;
		Element curNode;
		TTTGameData curData;
		
		curData = (TTTGameData)treeNode.value();
		if(curData == null)
		{
			return;
		}

		if(treeNode.children().size()>0)
		{
			curNode = doc.createElement(GameDTreeXMLConstants.NODE);
			linkingPhase.appendChild(curNode);
			
			linker = doc.createElement(GameDTreeXMLConstants.LINKER);
			linker.appendChild(doc.createTextNode(curData.getName()));
			curNode.appendChild(linker);
			
			for(NaryTree curTree: treeNode.children())
			{
				curData = (TTTGameData)curTree.value();
				if(curData != null)
				{
					linkedNode = doc.createElement(GameDTreeXMLConstants.LINKED_NODE);
					linkedNode.appendChild(doc.createTextNode(((TTTGameData)curTree.value()).getName()));
					curNode.appendChild(linkedNode);
				}
			}
		}
	}
	
	/**
	 * Add node and link children
	 * @param treeNode
	 */
	public void addNodeAndLink(NaryTree treeNode)
	{
		TTTGameData curData;
		curData = (TTTGameData)treeNode.value();
		if(curData==null)
		{
			return;
		}
		addNode(curData);
		linkNode(treeNode);
	}
	
	/**
	 * Save document to XML file
	 */
	public void saveToFile()
	{
		try{
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(fileName);
	
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	
			transformer.transform(source, result);
	
			System.out.println("File saved!");
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Set filename
	 * @param fileName
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	/**
	 * Get filename
	 * @return
	 */
	public String getFileName()
	{
		return fileName;
	}
}
