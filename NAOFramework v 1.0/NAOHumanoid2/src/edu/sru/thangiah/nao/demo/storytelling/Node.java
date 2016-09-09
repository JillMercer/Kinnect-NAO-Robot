package edu.sru.thangiah.nao.demo.storytelling;

import java.util.ArrayList;
import java.util.Iterator;

public class Node {
	
	protected ArrayList<Node> linkedNodes;
	private String nodeName;
	private int counter;
	
	public Node(){
		linkedNodes = new ArrayList<Node>();
		counter = 0;
	}
	
	/**
	 * Gets the name of the node.
	 * @return The name of the node.
	 */
	public String getNodeName(){
		return nodeName;
	}
	
	/**
	 * Sets the name of this node.
	 * @param name The new name for this node.
	 */
	public void setNodeName(String name){
		nodeName = name;
	}
	
	/**
	 * Links another node to this node.
	 * @param node The node to link
	 * @throws Exception If the node has already been linked.
	 */
	public void linkNode(Node node) throws Exception{
		if (!linkedNodes.contains(node)){
			linkedNodes.add(node);
			counter++;
		}
		else {
			throw new Exception("Node is already linked.");
		}
	}
	
	/**
	 * Links another node to this node. If the node is already linked, its position is changed to index.
	 * @param node The node to link.
	 * @param index The index where the node should reside.
	 */
	public void linkNode(Node node, int index){
		if (!linkedNodes.contains(node)){
			linkedNodes.add(index, node);
		}
		else {
			int currentLocation = linkedNodes.indexOf(node);
			linkedNodes.remove(currentLocation);
			linkedNodes.add(index, node);
		}
		
		counter++;
	}
	
	/**
	 * Removes a node that is linked to this node.
	 * @param node The linked node to remove.
	 */
	public void removeLinkedNode(Node node){
		linkedNodes.remove(node);
		counter--;
	}
	
	/**
	 * Removes a node that is linked to this node.
	 * @param index The index of the linked node to remove.
	 */
	public void removeLinkedNode(int index){
		linkedNodes.remove(index);
		counter--;
	}
	
	/**
	 * Gets a node linked to this one. If the index is out of bounds, null is returned.
	 * @param indexOfNode Position of the linked node.
	 * @return The linked node.
	 */
	public Node getLinkedNode(int indexOfNode){
		if (linkedNodes.size() > indexOfNode && indexOfNode > -1) {
			return linkedNodes.get(indexOfNode);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Gets a node linked to this one. If the named node is not linked, null is returned.
	 * @param nodeName The name of the linked node.
	 * @return The linked node.
	 */
	public Node getLinkedNode(String nodeName){
		Node temp = null;
		
		for (Node n : linkedNodes){
			if (n.getNodeName().equals(nodeName)){
				temp = n;
			}
		}
		
		return temp;
	}
	
	/**
	 * Iterator for this nodes connected nodes.
	 * @return An iterator of connected nodes.
	 */
	public Iterator<Node> getConnectedNodes(){
		return linkedNodes.iterator();
	}
	
	/**
	 * Gets the number of connected nodes.
	 * @return Number of connected nodes.
	 */
	public int getNumberOfNodes(){
		return counter;
	}
	
	/**
	 * Releases the resources used by this node.
	 */
	public void destroy(){
		this.linkedNodes.clear();
		this.linkedNodes = null;
		this.nodeName = null;
		this.counter = 0;
	}
}
