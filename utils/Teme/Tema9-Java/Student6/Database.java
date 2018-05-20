package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;
    private static final String URL = "jdbc:oracle://localhost:152";
    private static final String USERNAME = "JAVA";
    private static final String PASSWORD = "JAVA";
    private Database(){}

    public static Connection getConnection(){
        if(connection == null){
            try{
                System.out.println("Loading driver...");

                Class.forName("oracle.jdbc.driver.OracleDriver");
                System.out.println("Driver loaded...");

                System.out.println("Initializing connection...");

                connection = DriverManager.getConnection(URL, USERNAME,PASSWORD);
                System.out.println("Connection initialized!");
            }catch (ClassNotFoundException e){
                throw new IllegalStateException("Cannot find the driver in the classpath!", e);
            }catch (SQLException e){
                throw new IllegalStateException("Cannot connect the database!", e);
            }
        }

        return connection;
    }

    //Implement the method closeConnection()
    public static void closeConnection(){
        try{
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //Implement the method commit()
    public static void commit(){
        try {
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //Implement the method rollback()
    public static void rollback(){
        try {
            connection.rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
