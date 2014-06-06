/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree;

import data.Attribute;
import java.util.ArrayList;

/**
 *
 * @author Bram
 */
public class Node {
	private ArrayList<Attribute> attributes;
	private Node parent;
	private ArrayList<Node> children;
	
	
	/**
	 * 
	 * @param attributes 
	 */
	public Node(ArrayList<Attribute> attributes, Node parent){
		this.attributes = attributes;
		this.parent = parent;
		this.parent.addChild(this);
	}
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	public void addChild(Node child){
		this.children.add(child);
	}
	
	public Node getParent(){
		return parent;
	}
	
	/**
	 * 
	 * @return attributes
	 */
	public ArrayList<Attribute> getAttributes(){
		return attributes;
	}
}
