package edu.sru.thangiah.nao.demo.storytelling.XML;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.aldebaran.qi.Session;

import edu.sru.thangiah.nao.demo.storytelling.Node;
import edu.sru.thangiah.nao.demo.storytelling.StoryElementNode;

 public class StoryFromXML {
	 static final String NAME = "Name";
	 static final String TEXT = "Text";
	 static final String OPTION = "Option";
	 static final String LINKED_NODE = "LinkedNode";
	 static final String LINKER = "Linker";
	 
	 private Hashtable<String, StoryElementNode> nodes;
	 private String xmlFile;
	 
	 /**
	 * @param xmlFile The XML to get the story from. The starting node of the story
	 * must be named "Start" in the XML file.
	 */
	public StoryFromXML(String xmlFile){
		 nodes = new Hashtable<String, StoryElementNode>();
		 this.xmlFile = xmlFile;
	 }
	 
	 /** Gets the first node (i.e. the starting or root node) of the story from the XML file.
	 * @return A StoryElementNode that represents the starting node of the XML story.
	 */
	public StoryElementNode getStartNode(){
		 this.execute();
		 
		 return nodes.get("Start");
	 }
	 
	 /** Reads in the story XML file and constructs the story nodes and links them together.
	 * @return A Hashtable of the story nodes where each node can be accessed through the key 
	 * in the hashtable, which is the nodes name.
	 */
	private Hashtable<String, StoryElementNode> execute(){
		 try {
		      // First, create a new XMLInputFactory
		      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		      // Setup a new eventReader
		      InputStream in = new FileInputStream(xmlFile);
		      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		      StoryElementNode tempNode = null;
		      
		      while (eventReader.hasNext()) {
		          XMLEvent event = eventReader.nextEvent();

		          if (event.isStartElement()) {
		        	  StartElement startElement = event.asStartElement();
		        	  String temp = startElement.getName().getLocalPart();
		        	  
		        	  switch(temp){
		        	  case NAME:
		        		  tempNode = new StoryElementNode();	        		  
		        		  event = eventReader.nextEvent();
		        		  tempNode.setNodeName(event.asCharacters().getData());
		        		  break;
		        	  case TEXT:
		        		  event = eventReader.nextEvent();
		        		  tempNode.setStoryText(event.asCharacters().getData());
		        		  nodes.put(tempNode.getNodeName(), tempNode);
		        		  break;
		        	  case OPTION:
		        		  event = eventReader.nextEvent();
		        		  tempNode.addOption(event.asCharacters().getData());
		        		  break;
		        	  case LINKER:
		        		  event = eventReader.nextEvent();
		        		  tempNode = nodes.get(event.asCharacters().getData());
		        		  break;
		        	  case LINKED_NODE:
		        		  event = eventReader.nextEvent();
		        		  tempNode.linkNode(nodes.get(event.asCharacters().getData()));
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
		 StoryFromXML xml = new StoryFromXML(System.getProperty("user.dir") + "\\src\\storytelling\\XML\\Stories\\pirates.xml");
		 Hashtable<String, StoryElementNode> nodes = xml.execute();
		 
		 Enumeration<StoryElementNode> iterator = nodes.elements();
		 
		 while (iterator.hasMoreElements()){
			 StoryElementNode tempNode = iterator.nextElement();
			 System.out.println("NAME:: " + tempNode.getNodeName());
			 System.out.println("TEXT:: " + tempNode.getStoryText());
			 Iterator<Node> options = tempNode.getConnectedNodes();
			 System.out.println("Connected nodes: ");
			 while (options.hasNext()){
				 System.out.println(options.next().getNodeName() + " ");
			 }
			 
			 System.out.println();
		 }
	 }
	 
}
