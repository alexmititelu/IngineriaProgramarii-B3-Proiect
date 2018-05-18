import java.sql.*;

public class ArtistController {
    public void create(String name, String country) throws SQLException {

        Connection con = Database.getConnection();
        /*Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select * FROM ( select id from ARTISTS ORDER BY ID DESC ) WHERE rownum<=1");
        String result=null;
        java.lang.Object[] atribute;
        while (rs.next()) {
            result = rs.getString(1);
        }
        Integer newId;
        if (!result.equals("")) {
            newId = Integer.parseInt(result);
            newId = newId + 1;
        } else newId=0;
        result = Integer.toString(newId);*/
        try (PreparedStatement pstmt = con.prepareStatement("insert into artists (name, country) values (?, ?)")) {
            //pstmt.setString(1, result);
            pstmt.setString(1, name);
            pstmt.setString(2, country);
            pstmt.executeUpdate();
        }
    }
    public Integer findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select ID from artists where name='" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
    }
}
