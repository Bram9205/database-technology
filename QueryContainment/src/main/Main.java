package main;


import data.Query;
import tree.Tree;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {	
	
	private static void test(){
		Query query = QueryGenerator.generateCyclicQueryWidthN(3,5,0);
		Query nquery = QueryGenerator.generateCyclicQueryWidthN(3,5,1);
		System.out.println("Regular query: " + query.toString());
		System.out.println("Noised query: " + nquery.toString());
		Tree tree = Tree.createFromCyclicQueryWidthN(query);
		Tree ntree = Tree.createFromCyclicQueryWidthN(nquery);
        //Tree ntree = QueryGenerator.generateUncontainedTree(0,0);
		System.out.println("Regular tree: ");
		tree.print();
		System.out.println("Noised tree: ");
		ntree.print();
		System.out.println("SQL:");
		System.out.println(SQLGenerator.generateTables(tree));
		Map head = new HashMap();
		head.put(query.getHead().getAttributes().get(0), ntree.getRoot().getAttributes().get(0));
		head.put(query.getHead().getAttributes().get(1), ntree.getRoot().getAttributes().get(1));
		System.out.println(SQLGenerator.recursiveFillTables(tree.getRoot(), ntree.getRoot(), head));
		System.out.println(SQLGenerator.generateQuery(tree));
	}
	
	public static String getSQL(int n, int k, int noise){
		String result = "";
		Query query = QueryGenerator.generateCyclicQueryWidthN(k, n, 0);
		Query query2 = QueryGenerator.generateCyclicQueryWidthN(k, n, noise);
		Tree tree = Tree.createFromCyclicQueryWidthN(query);
		Tree tree2 = Tree.createFromCyclicQueryWidthN(query2);
		result += SQLGenerator.generateTables(tree);
		result += "\n\n" + SQLGenerator.fillTables(tree, tree2, query, query2);
		result += "\n\n" + SQLGenerator.generateQuery(tree);
		return result;
	}

    public static boolean executeOnDB(String[] args) throws SQLException{
        Database database = new Database();
        //Query query = QueryGenerator.generateCyclicQueryWidth2(4);
        //Query nquery = QueryGenerator.generateCyclicQueryWidth2(4,1);
        long time = System.currentTimeMillis();
		int k = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[0]);
        int noise = Integer.parseInt(args[1]);
        Query query = QueryGenerator.generateCyclicQueryWidthN(k,n,0);
        Query nquery = QueryGenerator.generateCyclicQueryWidthN(k,n,noise);
        //System.out.println("Regular query: " + query.toString());
        //System.out.println("Noised query: " + nquery.toString());
        Tree tree = Tree.createFromCyclicQueryWidthN(query);
        Tree ntree = Tree.createFromCyclicQueryWidthN(nquery);
        System.out.print("Time generating data: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        //System.out.println("Regular tree: " + tree.toString());
        //System.out.println("Noised tree: " + ntree.toString());
        //System.out.println("SQL:");
        database.updateDB(SQLGenerator.generateTables(tree));
        System.out.print(" | Making Tables: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();


        database.updateDB(SQLGenerator.fillTables(tree, ntree, query, nquery));
        System.out.print(" | Filling tables: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();


        boolean result = database.query(SQLGenerator.generateQuery(tree));
        System.out.print(" | Executing query: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();


        System.out.println(" | Database size(records total): " + database.totalSize(tree));
        //database.totalSize(tree);

        database.cleanup();
        System.out.print(" | Cleaning up: " + (System.currentTimeMillis()-time) + "\n");
        time = System.currentTimeMillis();

		return result;
    }

	private static void testDB(){
		/*for(int i = 0; i<10; i++){
			System.out.println(Relation.generateRandomName());
		} */
        //From http://stackoverflow.com/questions/2839321/java-connectivity-with-mysql
        try {
            //System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }
        String url = "jdbc:mysql://localhost:/querydb";
        String username = "root";
        String password = "fletcher";
        Connection connection = null;
        try {
            //System.out.println("Connecting database...");
            connection = DriverManager.getConnection(url, username, password);
            //System.out.println("Database connected!");

            Statement stmt = connection.createStatement();
            String sql;

            stmt.executeUpdate("CREATE TABLE ab (a varchar(20), b varchar(20));");
            stmt.executeUpdate("CREATE TABLE bcd (b varchar(20), c varchar(20), d varchar(20));");
            stmt.executeUpdate("CREATE TABLE bc (b varchar(20), c varchar(20));");
            stmt.executeUpdate("CREATE TABLE db (d varchar(20), b varchar(20));");
            stmt.executeUpdate("INSERT INTO ab(a, b) VALUES ('x', 'y');");
            stmt.executeUpdate("INSERT INTO bcd(b, c ,d) VALUES ('y', 'y', 'z'), ('y', 'z', 'y'), ('y', 'z', 'z');");
            stmt.executeUpdate("INSERT INTO bc(b, c) VALUES ('y', 'z');");
            stmt.executeUpdate("INSERT INTO db(d, b) VALUES ('z', 'y');");


            sql = "SELECT * FROM querydb.ab WHERE EXISTS( SELECT * FROM querydb.bcd WHERE EXISTS(SELECT * FROM querydb.db WHERE querydb.bcd.d = querydb.db.d AND querydb.bcd.b = querydb.db.b) AND EXISTS (SELECT * FROM querydb.bc WHERE querydb.bcd.b = querydb.bc.b AND querydb.bcd.c = querydb.bc.c) AND querydb.ab.b = querydb.bcd.b);";
            stmt = connection.createStatement();



            ResultSet RS = stmt.executeQuery(sql);
            ResultSetMetaData metadata = RS.getMetaData();
            int numberOfColumns = metadata.getColumnCount();
            ArrayList<String> arrayList = new ArrayList<String>();

            while(RS.next() != false)
            {
                int i = 1;
                while(i <= numberOfColumns) {
                    System.out.print(RS.getString(i++));
                }
                System.out.println();
            }


            stmt.executeUpdate("DROP TABLE querydb.ab;");
            stmt.executeUpdate("DROP TABLE querydb.bcd;");
            stmt.executeUpdate("DROP TABLE querydb.bc;");
            stmt.executeUpdate("DROP TABLE querydb.db;");




        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        } finally {
            //System.out.println("Closing the connection.");
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
	}
	
	public static void main(String[] args){
		test();
//        executeOnDB(args);
        //executeOnDB(args);
//		testDB();
	}
}
