package edu.sru.brian.tictactoegame.decisiontree;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import edu.sru.brian.tictactoegame.Markers;
import edu.sru.brian.tictactoegame.decisiontree.narytree.NaryTree;
import edu.sru.thangiah.nao.demo.storytelling.Node;

// DecisionTreeFromXML.java
// Based off of StoryFromXML for consistency of framework
// Modified by: Brian Atwell
// Date: 2/28/2016

/**
 * File: DecisionTreeFromXML.java
 * @author Brian Atwell
 * Description: Loads decision tree from XML to NaryTree and TTTGameData.
 *
 */

 public class DecisionTreeFromXML {
	 private Hashtable<String, NaryTree> nodes;
	 private String xmlFile;
	 
	 /**
	 * @param xmlFile The XML to get the story from. The starting node of the story
	 * must be named "Start" in the XML file.
	 */
	public DecisionTreeFromXML(String xmlFile){
		 nodes = new Hashtable<String, NaryTree>();
		 this.xmlFile = xmlFile;
	 }
	 
	 /** Gets the first node (i.e. the starting or root node) of the story from the XML file.
	 * @return A NaryTree that represents the starting node of the XML story.
	 */
	public NaryTree getStartNode(){
		 this.execute();
		 
		 return nodes.get("Start");
	 }
	
	/** Gets the first node (i.e. the starting or root node) of the story from the XML file.
	 * @return
	 */
	public NaryTree getStartNode(String nodeName){
		 this.execute();
		 
		 return nodes.get(nodeName);
	 }
	 
	 /** Reads in the story XML file and constructs the story nodes and links them together.
	 * @return A Hashtable of the story nodes where each node can be accessed through the key 
	 * in the hashtable, which is the nodes name.
	 */
	private Hashtable<String, NaryTree> execute(){
		 try {
		      // First, create a new XMLInputFactory
		      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		      // Setup a new eventReader
		      InputStream in = new FileInputStream(xmlFile);
		      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		      NaryTree tempNode = null;
		      TTTGameData tempNodeData = null;
		      
		      while (eventReader.hasNext()) {
		          XMLEvent event = eventReader.nextEvent();

		          if (event.isStartElement()) {
		        	  StartElement startElement = event.asStartElement();
		        	  String temp = startElement.getName().getLocalPart();
		        	  
		        	  switch(temp){
		        	  case GameDTreeXMLConstants.NAME:
		        		  tempNodeData = new TTTGameData();
		        		  tempNode = new NaryTree(tempNodeData);	        		  
		        		  event = eventReader.nextEvent();
		        		  tempNodeData.setName(event.asCharacters().getData());
		        		  break;
		        	  case GameDTreeXMLConstants.MARKER:
		        		  event = eventReader.nextEvent();
		        		  tempNodeData.setMarker(Markers.getMarkerFromString(event.asCharacters().getData()));
		        		  nodes.put(tempNodeData.getName(), tempNode);
		        		  break;
		        	  case GameDTreeXMLConstants.POSITION:
		        		  event = eventReader.nextEvent();
		        		  tempNodeData.setPosition(Integer.parseInt(event.asCharacters().getData()));
		        		  break;
		        	  case GameDTreeXMLConstants.STATUS:
		        		  event = eventReader.nextEvent();
		        		  tempNodeData.setStatus(NodeStatus.nodeStatusFromString(event.asCharacters().getData()));
		        		  break;
		        	  case GameDTreeXMLConstants.NUMWINS:
		        		  event = eventReader.nextEvent();
		        		  tempNodeData.setWins(Integer.parseInt(event.asCharacters().getData()));
		        		  break;
		        	  case GameDTreeXMLConstants.NUMLOSSES:
		        		  event = eventReader.nextEvent();
		        		  tempNodeData.setLosses(Integer.parseInt(event.asCharacters().getData()));
		        		  break;
		        	  case GameDTreeXMLConstants.NUMTIES:
		        		  event = eventReader.nextEvent();
		        		  tempNodeData.setTies(Integer.parseInt(event.asCharacters().getData()));
		        		  break;
		        	  case GameDTreeXMLConstants.CHILD_FILE:
		        		  event = eventReader.nextEvent();
		        		  tempNodeData.setChildFile(event.asCharacters().getData());
		        		  break;
		        	  case GameDTreeXMLConstants.LINKER:
		        		  event = eventReader.nextEvent();
		        		  tempNode = nodes.get(event.asCharacters().getData());
		        		  break;
		        	  case GameDTreeXMLConstants.LINKED_NODE:
		        		  event = eventReader.nextEvent();
		        		  tempNode.addChild(nodes.get(event.asCharacters().getData()));
		        		  break;
		        		  default:
		        			  break;
		        	  }
		        	  
		          }
		      }
		      
		      in.close();      
		 }
		 catch (Exception ex){
			 ex.printStackTrace();
		 }
		 
		 return nodes;
	 }
	 
	 public static void main(String[] args){
		 DecisionTreeFromXML xml = new DecisionTreeFromXML(System.getProperty("user.dir") + "\\src\\edu\\sru\\brian\\tictactoegame\\decisiontree\\data\\sampletree.xml");
		 Hashtable<String, NaryTree> nodes = xml.execute();
		 
		 Enumeration<NaryTree> iterator = nodes.elements();
		 
		 while (iterator.hasMoreElements()){
			 NaryTree tempNode = iterator.nextElement();
			 System.out.println("NAME:: " + ((TTTGameData)tempNode.value()).getName());
			 System.out.println("MARKER:: " + ((TTTGameData)tempNode.value()).getMarker());
			 System.out.println("POSITION:: " + ((TTTGameData)tempNode.value()).getPosition());
			 Iterator<NaryTree> options = tempNode.getDirectChildrenIterator();
			 System.out.println("Connected nodes: ");
			 while (options.hasNext()){
				 System.out.println(((TTTGameData)options.next().value()).getName() + " ");
			 }
			 
			 System.out.println();
		 }
	 }
	 
}
