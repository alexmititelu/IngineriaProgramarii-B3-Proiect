
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlbumController extends EntityController {
    static int uniqueID = 1;
    public void create(int artistID,String name, int release_year) throws SQLException, ClassNotFoundException {
        Connection con = Database.getConnection();
        String query = "INSERT INTO albums(ID, Name,Artist_ID,Release_Year) VALUES('" + uniqueID++ +"', '" + name + "', '" + artistID + "', '" + release_year + "')";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.executeUpdate();
        }
    }
    public int findByName(String name) throws SQLException, ClassNotFoundException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT ID FROM albums WHERE Name = '" + name + "'");
        return rs.next()?rs.getInt(1):null;
    }    
    public void list(int artistID) throws SQLException, ClassNotFoundException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT Name FROM albums WHERE Artist_ID = '" + artistID + "'");
        System.out.println("Albums with artist_id " + artistID + ": ");
        while (rs.next())
            System.out.println(rs.getString("Name"));
    }

    public void insert(List params) { 
        try {
            Connection con = Database.getConnection();
            String query = "INSERT INTO albums (ID,Name,Artist_ID,Release_Year) VALUES (" + uniqueID++ + ", '" + params.get(0) + "', " + params.get(1) + ", " + params.get(2) + ")";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List select(List<String> items, String condition) {
        String query = "SELECT ";
        for(String item : items) {
            query += item;
            if(item != items.get(items.size()-1))
              query += ", ";
        }
        query += " FROM album " + condition;
        try {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery(query);
            List result = new LinkedList();
            while(rs.next())
                for(String item : items)
                    result.add(rs.getObject(item));
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void update(List items,String condition) {
        String query = "UPDATE albums SET ";
        for(int i=0;i<items.size();i+=2) {
            query += items.get(i).toString();
            query += " = '" + items.get(i+1).toString() + "'";
            if(i < items.size()-2)
                query += ", ";
        }
        query += " " + condition;
        System.out.println(query);
        try {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void delete(String condition) {
        String query = "DELETE FROM albums " + condition;
        try {
            Connection con = Database.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ArtistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
