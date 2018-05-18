/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Eduard
 */
public class DataBase {
    
    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
    private static final String USER = "myuser1";
    private static final String PASSWORD = "mypassword";
    private static Connection connection = null;
    private DataBase() { }
    public static Connection getConnection() throws SQLException {
        try{
        if (connection == null) {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("succesfully connected to database");
            
            
        }
        } catch(SQLException e){
                System.out.println("could not connect to database");
                }
        connection.setAutoCommit(false);
        return connection;
    }
    
    public static void commit(){
        try {
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void rollback(){
        try {
            connection.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
