
import data.Query;
import tree.Tree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	
	private static QueryGenerator generator;
	
	private static void treeToSQL(Query query){
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	
	private static void test(){
		Query query = generator.generateCyclicQueryWidth2(2500);
		Tree tree = Tree.createFromCyclicQueryWidth2(query);
		System.out.println(tree.toString());
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
