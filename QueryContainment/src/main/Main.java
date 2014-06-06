package main;


import data.Query;
import tree.Tree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import tree.Node;

public class Main {
	
	private static String treeToSQL(Tree tree){
		//Add the table creation
		return subtreeToSQL("", tree.getRoot());
	}
	
	private static String subtreeToSQL(String partialQuery, Node rootNode){
		String additive = "SELECT * FROM ";
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	private static void test(){
		Query query = QueryGenerator.generateCyclicQueryWidth2(3);
		Tree tree = Tree.createFromCyclicQueryWidth2(query);
		System.out.println(SQLGenerator.generateTables(tree));
	}
	
	public static void main(String[] args){
//		test();
		/*for(int i = 0; i<10; i++){
			System.out.println(Relation.generateRandomName());
		} */
        //From http://stackoverflow.com/questions/2839321/java-connectivity-with-mysql
        try {
            System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }


        String url = "jdbc:mysql://localhost:3306/querydb";
        String username = "root";
        String password = "fletcher";
        Connection connection = null;
        try {
            System.out.println("Connecting database...");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        } finally {
            System.out.println("Closing the connection.");
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
	}
}
