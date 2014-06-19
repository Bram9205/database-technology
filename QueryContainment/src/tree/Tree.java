package tree;

import data.Attribute;
import data.Query;
import data.Relation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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
	
	public void print(){
		this.root.print();
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


    /**
     *
     * @param query
     * @return
     */
    public static Tree createFromCyclicQueryWidthN(Query query){
        Node root = new Node(query.getRelation(0));

        Tree result = new Tree(root);
        Node parent = root;
        Node previous = root;
        int size = query.getRelations().size();
        //Build tree structure matching nodes to compatible parents i.e. parents with common variables. Will only match one variable, not both.
        for(Relation relation: query.getRelations().subList(1,query.getRelations().size())){
            Node newNode = new Node(relation);
            try{
                parent = findCompatiblePair(newNode,previous);
            }
            catch (NullPointerException e){
                Attribute temp = newNode.getAttribute(0);
                newNode.getAttributes().set(0,newNode.getAttributes().get(1));
                newNode.getAttributes().set(1,temp);
                parent = findCompatiblePair(newNode, previous);
            }
            newNode.parent = parent;
            parent.addChild(newNode);
            previous = newNode;
        }
        LinkedList<Node> processQueue = new LinkedList<>();
        Node currNode;
        for(Node child: root.getChildren()){processQueue.add(child);}
        //Add extra attributes to nodes to make acyclic.
        while(!processQueue.isEmpty()){
            currNode = processQueue.remove();
            for(Node child: currNode.getChildren()){processQueue.add(child);}
            assert currNode.parent.getAttributes().contains(currNode.getAttribute(0));

            for(Attribute attribute : currNode.getAttributes().subList(1, currNode.getAttributes().size())) {//assume first attribute is ok, need to check all other ones.
                boolean attributeMatching = false;
                if(attribute == currNode.getAttribute(1)){   //only check the second attribute for matching with children.
                    for (Node child : currNode.getChildren()) {
                        if (child.getAttributes().contains(attribute)){attributeMatching = true;}
                    }
                }

                if(currNode.parent.getAttributes().contains(attribute)){attributeMatching = true;}

                if(!attributeMatching && matchingAttribute(attribute, currNode, root)){//after checking children and parent, this attribute is not found. Add it to parent to make acyclic graph.
                    //but only if there is another attribute it is matching too, and this is not a unique attribute.
                    currNode.parent.addAttribute(attribute);
                    processQueue.add(currNode.parent);//necessary to propagate added attribute up tree.
                }
            }
        }
        return result;
    }

    /**
     * Given a node and the most recently added node in the tree, backtrace through the tree and return the
     * first node which contains the first attribute of the new node.
     * @param newNode
     * @param previous
     * @return
     */
    public static Node findCompatiblePair(Node newNode, Node previous){
         if(previous.getAttributes().contains(newNode.getAttribute(0))){
             return previous;
         }
        for(Node child : previous.getChildren()){
            if(child.getAttributes().contains(newNode.getAttribute(0))){
                return child;
            }
        }

        return findCompatiblePair(newNode, previous.parent);
    }

    public static boolean matchingAttribute(Attribute attribute, Node originalNode, Node node){
        if(node != originalNode){
            if(node.getAttributes().contains(attribute)){return true;}
        }
        for(Node child : node.getChildren()) {
            if(matchingAttribute(attribute, originalNode, child)){return true;}
        }
        return false;

    }

}
