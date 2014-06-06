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
	private String name;
	
	
	/**
	 * 
	 * @param attributes 
	 */
	public Node(ArrayList<Attribute> attributes, Node parent){
		this.attributes = attributes;
		this.parent = parent;
		this.parent.addChild(this);
		this.children = new ArrayList<Node>();
	}
	
	/**
	 * Add attributes later. Auto adds itself to parents children.
	 * @param parent 
	 */
	public Node(Node parent){
		this.parent = parent;
		this.parent.addChild(this);
		this.children = new ArrayList<Node>();
		this.attributes = new ArrayList<Attribute>();
	}
	
	/**
	 * Create the root node
	 */
	public Node(){
		this.parent = null;
		this.attributes = new ArrayList<Attribute>();
		this.children = new ArrayList<Node>();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void addAttribute(Attribute a){
		this.attributes.add(a);
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
	
	public String getSubtreeString(){
		String result = this.name+"(";
		for(int i = 0; i< this.attributes.size(); i++){
			result += this.attributes.get(i).getType() + ",";
		}
		result += ")";
		if(!children.isEmpty()){
			result += " -> ";
			result += children.get(0).getSubtreeString();
		}
		return result;
	}
}
