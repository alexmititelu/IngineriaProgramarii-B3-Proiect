
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Charts {
    int uniqueID;
    char[] chars;
    Random random;
    
    public Charts() {
        uniqueID = 1;
        chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        random = new Random();
    }
    public int getRandom(int n) {
        return random.nextInt(n)+1;
    }
    public String getStringOfLength(int n) {
        String string = "";
        for (int i = 0; i < n; i++)
            string += chars[random.nextInt(chars.length)];
        return string;
    }
    public void populateChartsTable() throws SQLException, ClassNotFoundException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM albums");
        rs.next();
        int max = rs.getInt(1);
        int n = max/2;
        while(n-- != 0)
            rs = stmt.executeQuery("INSERT INTO charts(ID,AlbumID) VALUES(" + uniqueID++ + ", " + getRandom(max) + ")");
    }
    public void displayRanks() throws SQLException, ClassNotFoundException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT artists.Name FROM artists,albums,charts WHERE artists.ID = albums.Artist_ID AND albums.ID = charts.AlbumID ORDER BY charts.ID");
        int id = 1;
        System.out.println("\n----Display ranks-----");
        while(rs.next()) 
            System.out.println(id++ + ". " + rs.getString("Name"));
    }
}
