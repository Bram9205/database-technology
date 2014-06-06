package tree;

import java.util.ArrayList;

/**
 *
 * @author Bram
 */
public class Tree {
	private Node root;
	private ArrayList<Node> nodes;
	
	public Tree(){
		
	}
	
	public Tree(Node root){
		this.root = root;
	}
	
	public Tree(Node root, ArrayList<Node> nodes){
		this.root = root;
		this.nodes = nodes;
	}
	
	public void addNode(Node node){
		this.nodes.add(node);
	}
	
	public Node getRoot(){
		return this.root;
	}
}
