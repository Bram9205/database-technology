package main;

import data.Attribute;
import data.Query;
import data.Relation;
import java.util.HashMap;
import java.util.Map;
import tree.Node;
import tree.Tree;

/**
 *
 * @author Bram
 */
public class SQLGenerator {

	public static String generateTables(Tree tree) {
		return recursiveGenerateTables(tree.getRoot());
	}

	private static String recursiveGenerateTables(Node root) {
		String result = "CREATE TABLE " + root.getName() + "(";
		int size = root.getAttributes().size();
		for (int i = 0; i < size - 1; i++) {
			result += root.getAttributes().get(i).getType() + " varchar(20),";
		}
		result += root.getAttributes().get(size - 1).getType() + " varchar(20)";
		result += ");";

		for (Node n : root.getChildren()) {
			result += recursiveGenerateTables(n);
		}
		return result;
	}

	public static String generateQuery(Tree tree) {
		return recursiveGenerateQuery(tree.getRoot());
	}

	private static String recursiveGenerateQuery(Node node) {
		String sql = "SELECT * FROM " + node.getName() + " WHERE ";
		if (node.getChildren().isEmpty()) {
			sql += "( ";
		}
		for (Node child : node.getChildren()) {
			sql += "Exists( " + recursiveGenerateQuery(child);
		}
		for (Node child : node.getChildren()) {
			for (Attribute attr : node.getAttributes()) {
				for (Attribute chattr : child.getAttributes()) {
					if (attr.getType().equals(chattr.getType())) {
						if (!child.getChildren().isEmpty() || child.getAttributes().indexOf(attr) != 0) {
							sql += " AND ";
						}    //dont write AND if the child has no children and this is the first child. We write AND if its not the fire child in this iterator.
						sql += node.getName() + "." + attr.getType() + " = " + child.getName() + "." + attr.getType();
					}
				}
			}
			sql += ")";
		}
		if (!node.getChildren().isEmpty() && node.getChildren().get(0).getChildren().isEmpty()) {
			sql += ")";
		}
		return sql;
	}

	public static String fillTables(Tree regularTree, Tree noisedTree, Query regularQuery, Query noisedQuery) {
		Map head = new HashMap();
		for (int i = 0; i < regularQuery.getHead().getAttributes().size(); i++) {
			head.put(regularQuery.getHead().getAttributes().get(i), noisedQuery.getHead().getAttributes().get(i));
		}
		return recursiveFillTables(regularTree.getRoot(), noisedTree.getRoot(), head);
	}

	public static String recursiveFillTables(Node node, Node noiseRoot, Map head) {
		//Generate the "INSERT INTO {tablename}(a, b, c) VALUES " part
		String result = "INSERT INTO " + node.getName() + "(";
		String separator = "";
		for (Attribute a : node.getAttributes()) {
			result += separator + a.getType();
			separator = ", ";
		}
		result += ") VALUES ";

		//Generate the part containing the values
		result += getFillValues(node, noiseRoot, head);
		for (Node n : node.getChildren()) {
			result += "; " + recursiveFillTables(n, noiseRoot, head);
		}

		return result;
	}

	/**
	 * Returns all values that should be inserted in the table for node as SQL string
	 *
	 * @param node the table that we are inserting in
	 * @param noisenode along with its children, the values that could be inserted in the table (if valid)
	 * @param head the mapping from values that should match
	 * @return
	 */
	private static String getFillValues(Node node, Node noiseNode, Map head) {
		String result = "";
		Boolean valid = true;
		for (int i = 0; i < node.getAttributes().size(); i++) {
			Attribute compare = node.getAttributes().get(i);
			//If an attribute is in the head, and there is an attribute on that position in the noiseNode, 
			// then it has to be the matching attribute from the head, otherwise not valid
			if (head.containsKey(compare) && noiseNode.getAttributes().size() > i && noiseNode.getAttributes().get(i) != head.get(compare)) {
				valid = false;
			}
		}
		String thisResult = "";
		if (valid) { //if the node is a valid row for the table, make the values
			thisResult += "(";
			String separator = "";
			for (Attribute a : noiseNode.getAttributes()) {
				thisResult += separator + "'" + a.getType() + "'";
				separator = ", ";
			}
			//Attributes with no matching position just get their matching head values. This assumes they actually are in the head (so far no exceptions)
			if(noiseNode.getAttributes().size() < node.getAttributes().size()){ 
				for(int i = noiseNode.getAttributes().size(); i < node.getAttributes().size(); i++){
					thisResult += separator + "'" + ((Attribute) head.get(node.getAttributes().get(i))).getType() + "'";
				}
			}
			thisResult += ")";
		}
		result += thisResult;
		String separator = (result.isEmpty()) ? "" : ", ";
		if(noiseNode.getChildren().isEmpty()){ //no children, just return thisResult, which is empty when this node is not a valid row
			return result;
		} else { //along with thisResult, the children that match should be returned
			for (Node n : noiseNode.getChildren()) {
				String child = getFillValues(node, n, head);
				if(!child.isEmpty()){
					result += separator + child;
					separator = ", ";
				}
			}
		}
		return result;
	}
	
	public static String dropTables(Tree tree) {
		return recursiveDropTables(tree.getRoot());
	}

	private static String recursiveDropTables(Node root) {
		String result = "DROP TABLE " + root.getName() + ";";
		for (Node n : root.getChildren()) {
			result += recursiveDropTables(n);
		}
		return result;
	}
}
