package com.company;

import java.sql.*;

public class AlbumController {
    public void create(int artistId, String name, int releaseYear) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into albums (artist_id, name, release_year) values (?, ?,?)")) {
            pstmt.setInt(1, artistId);
            pstmt.setString(2, name);
            pstmt.setInt(3,releaseYear);
            pstmt.executeUpdate();
        }
    }

    public void list(int artistId) throws SQLException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        stmt.executeQuery("select name from albums where artist_id='" + artistId + "'");
        ResultSet rs = stmt.getResultSet();

        while (rs.next()){
            System.out.println(rs.getString("name"));

        }
    }
}
