package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by max on 6/12/14.
 */
public class Database {
    Connection connection = null;
    public Database(){
        try {
            //System.out.println("Loading driver...");
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }
        String url = "jdbc:mysql://localhost/";
        String username = "root";
        String password = "fletcher";
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE DATABASE querydb;");
            stmt.executeUpdate("USE querydb;");
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("Did not connect to database");
            cleanup();
            System.exit(1);}
        }

    public void updateDB(String sql){
        try {
            Statement stmt = connection.createStatement();
            for(String str: sql.split(";")){
                System.out.println(str+";");
                stmt.addBatch(str+";");}
            stmt.executeBatch();
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("Problem executing statement: \n");
            System.out.println(sql);
            cleanup();
        }
    }
    public ResultSet queryForResultSet(String sql){
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        }
        catch(Exception e){
            System.out.println(e);

            System.out.println("Problem executing statement: \n");
            System.out.println(sql);
            cleanup();
            return null;

        }

    }
    public boolean query(String sql){
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next() == true;
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Problem executing statement: \n");
            System.out.println(sql);
            cleanup();
            return false;
        }

    }
    public void cleanup(){
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP DATABASE querydb;");

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Problem executing statement: \n");
            System.out.println("Cleanup...");
        }
    }
}
