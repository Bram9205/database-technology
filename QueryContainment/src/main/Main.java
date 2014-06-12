package main;


import data.Query;
import tree.Tree;
import java.sql.*;
import java.util.ArrayList;

public class Main {	
	
	private static void test(){
		Query query = QueryGenerator.generateCyclicQueryWidth2(4);
		Query nquery = QueryGenerator.generateCyclicQueryWidth2(4,1);
		System.out.println("Regular query: " + query.toString());
		System.out.println("Noised query: " + nquery.toString());
		Tree tree = Tree.createFromCyclicQueryWidth2(query);
		Tree ntree = Tree.createFromCyclicQueryWidth2(nquery);
		System.out.println("Regular tree: " + tree.toString());
		System.out.println("Noised tree: " + ntree.toString());
		System.out.println("SQL:");
		System.out.println(SQLGenerator.generateTables(tree));
		System.out.println(SQLGenerator.fillTables(tree, ntree, query, nquery));
		System.out.println(SQLGenerator.generateQuery(tree));
	}

    public static void executeOnDB(String[] args){
        Database database = new Database();
        //Query query = QueryGenerator.generateCyclicQueryWidth2(4);
        //Query nquery = QueryGenerator.generateCyclicQueryWidth2(4,1);
        long time = System.currentTimeMillis();
        int inputA = Integer.parseInt(args[0]);
        int inputB = Integer.parseInt(args[1]);
        Query query = QueryGenerator.generateCyclicQueryWidth2(inputA);
        Query nquery = QueryGenerator.generateCyclicQueryWidth2(inputA,inputB);
        //System.out.println("Regular query: " + query.toString());
        //System.out.println("Noised query: " + nquery.toString());
        Tree tree = Tree.createFromCyclicQueryWidth2(query);
        Tree ntree = Tree.createFromCyclicQueryWidth2(nquery);
        System.out.println("Time generating data: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        //System.out.println("Regular tree: " + tree.toString());
        //System.out.println("Noised tree: " + ntree.toString());
        //System.out.println("SQL:");
        database.updateDB(SQLGenerator.generateTables(tree));
        System.out.println("Making Tables: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();


        database.updateDB(SQLGenerator.fillTables(tree, ntree, query, nquery));
        System.out.println("Filling tables: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();


        System.out.println(database.query(SQLGenerator.generateQuery(tree)));
        System.out.println("Executing query: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();

        database.cleanup();
        System.out.println("Cleaning up: " + (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
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
		//test();
        executeOnDB(args);
//		testDB();
	}
}
