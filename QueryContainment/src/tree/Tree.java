package tree;

import data.Attribute;
import data.Query;
import data.Relation;
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
		this.nodes = new ArrayList<>();
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
	
	@Override
	public String toString(){
		return this.root.getSubtreeString();
	}
	
	/**
	 * Creates a tree out of the width 2 cyclic query. 
	 * Works with noise, but noise relations should have a name starting with n (others s).
	 * @param query query to create tree of
	 * @return A tree which breaks the cycle in the query
	 */
	public static Tree createFromCyclicQueryWidth2(Query query){
		Attribute firstAttribute = query.getRelations().get(0).getAttributes().get(0);
		Relation lastRelation = null;
		for(Relation r : query.getRelations()){
			if(r.getName().toCharArray()[0] == 's'){
				lastRelation = r;
			}
		}
		Node root = new Node();
		for(Attribute a : query.getRelations().get(0).getAttributes()){
			root.addAttribute(a);
		}
		root.setName(query.getRelations().get(0).getName());
		Tree result = new Tree(root);
		Node parent = root;
		for(int i = 1; i<query.getRelations().size(); i++){
			Node node = new Node(parent);
			for(Attribute a : query.getRelations().get(i).getAttributes()){
				node.addAttribute(a);
			}
			node.setName(query.getRelations().get(i).getName());
			if(query.getRelations().get(i) != lastRelation && query.getRelations().get(i).getName().toCharArray()[0] == 's'){
				node.addAttribute(firstAttribute);
			}
			result.addNode(node);
			parent = node;
		}
		return result;
	}
}
