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

    public static String generateQuery(Tree tree) {return recursiveGenerateQuery(tree.getRoot());}

    private static String recursiveGenerateQuery(Node node){
        String sql = "SELECT * FROM " + node.getName() + " WHERE ";
        if(node.getChildren().isEmpty()){ sql += "( ";}
        for(Node child: node.getChildren()){
            sql += "Exists( " + recursiveGenerateQuery(child);
        }
        for(Node child: node.getChildren()){
            for(Attribute attr: node.getAttributes()){
                for(Attribute chattr: child.getAttributes()){
                    if(attr.getType().equals(chattr.getType())){
                        if(!child.getChildren().isEmpty() && node.getChildren().indexOf(child) != 0){sql += " AND ";}    //dont write AND if the child has no children and this is the first child. We write AND if its not the fire child in this iterator.
                        sql += node.getName() + "." + attr.getType() + " = " + child.getName() + "." + attr.getType();
                    }
                }
            }
            sql += ")";
        }
        if(!node.getChildren().isEmpty() && node.getChildren().get(0).getChildren().isEmpty()){sql += ")";}
        return sql;
    }
	
	public static String fillTables(Tree tree){
		throw new UnsupportedOperationException("not yet implemented");
	}
}
