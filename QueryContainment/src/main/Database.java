package main;

import tree.Node;
import tree.Tree;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by max on 6/12/14.
 */
public class Database {
    Connection connection;
    String url = "jdbc:postgresql://localhost/querydb";
    String urltmp = "jdbc:postgresql://localhost/temp";
    String username = "postgres";
    String password = "fletcher";
    public Database(){
       // try {
            //System.out.println("Loading driver...");
            //Class.forName("com.postgresql.jdbc.Driver");
            //System.out.println("Driver loaded!");
       // } catch (ClassNotFoundException e) {
       //     throw new RuntimeException("Cannot find the driver in the classpath!", e);
       // }


        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement stmt = connection.createStatement();
            //stmt.executeUpdate("DROP DATABASE IF EXISTS querydb;");
            //stmt.executeUpdate("CREATE DATABASE querydb;");
            //stmt.executeUpdate("USE querydb;");
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("Did not connect to database");
            System.exit(1);}
        }

    public void updateDB(String sql){
        try {
            Statement stmt = connection.createStatement();
            for(String str: sql.split(";")){
                stmt.addBatch(str+";");}
            stmt.executeBatch();
        }
        catch (Exception e){
            System.out.println(e);
            //System.out.println("Problem executing statement: \n");
            //System.out.println(sql);
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
            //System.out.println("Problem executing statement: \n");
            //System.out.println(sql);
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
            //System.out.println("Problem executing statement: \n");
            //System.out.println(sql);
            return false;
        }

    }
    public int totalSize(Tree tree){
        return recursiveSize(tree.getRoot());


    }
    private int recursiveSize(Node node){
        int count = 0;
        for(Node child: node.getChildren()){
            count += recursiveSize(child);
        }
        ResultSet rs = this.queryForResultSet("SELECT COUNT(*) AS count FROM " + node.getName() + ";");
        try {
            System.out.println(rs.getInt("count"));
        }catch(Exception e){}
        return 0;

    }
    public void cleanup(){
        try {
            connection.close();
            connection = DriverManager.getConnection(urltmp, username, password);
            Statement stmt = connection.createStatement();
            //stmt.executeUpdate("drop schema public cascade;");
            //stmt.executeUpdate("create schema public;;");
            stmt.executeUpdate("DROP DATABASE querydb;");
            stmt.executeUpdate("CREATE DATABASE querydb;");


        }
        catch(Exception e){
            System.out.println(e);
            //System.out.println("Problem executing statement: \n");
            //System.out.println("Cleanup...");
        }
    }
}
