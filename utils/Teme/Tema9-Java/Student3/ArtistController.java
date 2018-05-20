/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.company.lab9.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Aurelia
 */
public class ArtistController {
    public void create(String name, String country) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into artists (name, country) values (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, country);
            pstmt.executeUpdate();
        }
    }
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.getResultSet()) {
            stmt.executeQuery("select id from artists where name='" + name + "'");
            return rs.next() ? rs.getInt(1) : null;
        }
    }    
}
