/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.lab9;

import java.sql.Connection;

/**
 *
 * @author Aurelia
 */
public class Database {
    private static final String URL = "";
    private static final String USER = "dba3";
    private static final String PASSWORD = "sql";
    private static final Connection connection = null;

    private Database() { }
    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }
    
    //Implement the method createConnection()
    private static void createConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //Implement the method closeConnection()
    private static void closeConnection(){
    }
    //Implement the method commit()
    private static void comit(){
    }
    //Implement the method rollback() 
    private static void rollback(){
    }
}
