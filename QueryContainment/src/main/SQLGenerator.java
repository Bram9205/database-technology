package main;

import data.Attribute;
import tree.Node;
import tree.Tree;

/**
 *
 * @author Bram
 */
public class SQLGenerator {
	public static String generateTables(Tree tree){
		return recursiveGenerateTables(tree.getRoot());
	}
	
	private static String recursiveGenerateTables(Node root){
		String result = "CREATE TABLE " + root.getName() + "(";
		int size =  root.getAttributes().size();
		for(int i = 0; i < size-1; i++){
			result += root.getAttributes().get(i).getType() + " varchar(20),";
		}
		result += root.getAttributes().get(size-1).getType() + " varchar(20)";
		result += ");";
		
		for(Node n : root.getChildren()){
			result += recursiveGenerateTables(n);
		}
		return result;
	}
	
	public static String fillTables(Tree tree){
		throw new UnsupportedOperationException("not yet implemented");
	}
}
