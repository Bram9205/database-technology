package main;


import data.Query;
import tree.Tree;
import java.sql.*;

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

            Statement stmt = connection.createStatement();
            String sql;

            stmt.executeUpdate("CREATE TABLE ab (a varchar(20), b varchar(20));");
            stmt.executeUpdate("CREATE TABLE bcd (b varchar(20), c varchar(20), d varchar(20));");
            stmt.executeUpdate("CREATE TABLE bc (b varchar(20), c varchar(20));");
            stmt.executeUpdate("CREATE TABLE db (d varchar(20), b varchar(20));");
            stmt.executeUpdate("INSERT INTO ab(a, b) VALUES ('x', 'y');");
            stmt.executeUpdate("INSERT INTO bcd(b, c ,d) VALUES ('y', 'y', 'z'), ('y', 'y', 'z'), ('y', 'y', 'z');");
            stmt.executeUpdate("INSERT INTO bc(b, c) VALUES ('y', 'z');");
            stmt.executeUpdate("INSERT INTO db(d, b) VALUES ('z', 'y');");


            sql = "SELECT *" +
                    "FROM querydb.ab" +
                    "WHERE EXISTS(" +
                    "    SELECT * " +
                    "    FROM querydb.bcd" +
                    "    WHERE EXISTS(" +
                    "      SELECT *" +
                    "      FROM querydb.db" +
                    "      WHERE querydb.bcd.d = querydb.db.d AND querydb.bcd.b = querydb.db.b" +
                    "      ) AND EXISTS (" +
                    "      SELECT *" +
                    "      FROM querydb.bc " +
                    "      WHERE querydb.bcd.b = querydb.bc.b AND querydb.bcd.c = querydb.bc.c" +
                    "      ) AND querydb.ab.b = querydb.bcd.b" +
                    "  );";
            stmt = connection.createStatement();
            ResultSet RS = stmt.executeQuery(sql);
            while(RS.next() != false)
            {
                System.out.println(RS.getString(1));
            }

            stmt.executeUpdate("DROP TABLE querydb.ab;");
            stmt.executeUpdate("DROP TABLE querydb.bcd;");
            stmt.executeUpdate("DROP TABLE querydb.bc;");
            stmt.executeUpdate("DROP TABLE querydb.db;");




        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        } finally {
            System.out.println("Closing the connection.");
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
	}
}
