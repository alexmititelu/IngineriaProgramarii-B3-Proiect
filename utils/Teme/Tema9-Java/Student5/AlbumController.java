import java.sql.*;

public class AlbumController {
    public void create(Integer id, String name,Integer year) throws SQLException {
        Connection con = Database.getConnection();

        try (PreparedStatement pstmt = con.prepareStatement("insert into albums ( name,artist_id,release_year) values (?, ?,?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.setInt(3,year);
            pstmt.executeUpdate();
        }
    }
    public Integer list(Integer id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id from albums where id='" + Integer.toString(id) + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }
}
