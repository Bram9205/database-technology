
import data.Query;
import tree.Tree;

import java.sql.*;

public class Main {
	
	private static void treeToSQL(Query query){
		throw new UnsupportedOperationException("not yet implemented");
	}
	
	
	private static void test(){
		Query query = QueryGenerator.generateCyclicQueryWidth2(2500);
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

            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE ab \n" +
                    "\t(\n" +
                    "     a varchar(20), \n" +
                    "     b varchar(20)\n" +
                    "     \n" +
                    "    );\n" +
                    "\n" +
                    "CREATE TABLE bcd \n" +
                    "\t(\n" +
                    "     b varchar(20), \n" +
                    "     c varchar(20),\n" +
                    "     d varchar(20)\n" +
                    "     \n" +
                    "    );\n" +
                    "CREATE TABLE bc\n" +
                    "\t(\n" +
                    "     b varchar(20), \n" +
                    "     c varchar(20)\n" +
                    "     \n" +
                    "    );\n" +
                    "CREATE TABLE db\n" +
                    "\t(\n" +
                    "     d varchar(20), \n" +
                    "     b varchar(20)\n" +
                    "     \n" +
                    "    );\n" +
                    "\n" +
                    "\n" +
                    "INSERT INTO ab\n" +
                    "(a, b)\n" +
                    "VALUES\n" +
                    "('x', 'y');\n" +
                    "\n" +
                    "INSERT INTO bcd\n" +
                    "(b, c, d)\n" +
                    "VALUES\n" +
                    "('y', 'y', 'z'),\n" +
                    "('y', 'z', 'y'),\n" +
                    "('y', 'z', 'z');\n" +
                    "\n" +
                    "\n" +
                    "INSERT INTO bc\n" +
                    "(b, c)\n" +
                    "VALUES\n" +
                    "('y', 'z');\n" +
                    "\n" +
                    "INSERT INTO db\n" +
                    "(d, b)\n" +
                    "VALUES\n" +
                    "('z', 'y');\n" +
                    "\n";

            stmt.executeUpdate(sql);

            sql = "select *\n" +
                    "from ab\n" +
                    "where exists(\n" +
                    "    select * \n" +
                    "    from bcd\n" +
                    "    where exists(\n" +
                    "      select *\n" +
                    "      from db\n" +
                    "      where bcd.d = db.d AND bcd.b = db.b\n" +
                    "      ) and exists (\n" +
                    "      select *\n" +
                    "      from bc \n" +
                    "      where bcd.b = bc.b AND bcd.c = bc.c\n" +
                    "      ) and ab.b = bcd.b\n" +
                    "  )\n" +
                    "\n" +
                    "\n";

            stmt = connection.createStatement();
            ResultSet RS = stmt.executeQuery(sql);
            while(RS.next() != false)
            {
                System.out.println(RS.getString(1));
            }

            stmt.executeUpdate("DROP TABLE ab");
            stmt.executeUpdate("DROP TABLE bcd");
            stmt.executeUpdate("DROP TABLE bc");
            stmt.executeUpdate("DROP TABLE db");




        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        } finally {
            System.out.println("Closing the connection.");
            if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
        }
	}
}
