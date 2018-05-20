
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HTMLify {
    String html;
    public void initiate() {
        html = "";
        html += "<!DOCTYPE html>" +
                "<html lang=\"en-US\" class=\"indexPage\">\n" +
                "  <head>\n" +
                "    <title>Lab 9 - Java</title>\n" +
                "<style>\n" +
                "h1 {\n" +
                "    font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
                "    color: #4CAF50;\n" +
                "}" +
                "table {\n" +
                "    font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
                "    border-collapse: collapse;\n" +
                "    width: 60%;\n" +
                "}\n" +
                "\n" +
                "table td, table th {\n" +
                "    border: 1px solid #ddd;\n" +
                "    padding: 8px;\n" +
                "}\n" +
                "\n" +
                "table tr:nth-child(even){background-color: #f2f2f2;}\n" +
                "\n" +
                "table tr:hover {background-color: #ddd;}\n" +
                "\n" +
                "table th {\n" +
                "    padding-top: 12px;\n" +
                "    padding-bottom: 12px;\n" +
                "    text-align: left;\n" +
                "    background-color: #4CAF50;\n" +
                "    color: white;\n" +
                "}\n" +
                "</style>" +
                "  </head>\n" +
                "  <body>\n";
    }
    public void createTable(String query) throws SQLException, ClassNotFoundException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        html += "<h1>" + query.split("FROM")[1].toUpperCase() + "</h1><br>\n";
        html += "<table>\n";
        int columns = rs.getMetaData().getColumnCount();
        
        for(int i=0;i<columns;i++)
            html +=  "<th>" + rs.getMetaData().getColumnName(i+1) + "</th>";
        
        while(rs.next()) {
            html += "\n<tr>";
            for(int i=0;i<columns;i++)
                html += "<td>" + rs.getString(i+1) + "</td>\n";
            html += "</tr>";
        }
        html += "\n</table>";
    }
    public void createRanksTable() throws SQLException, ClassNotFoundException {
        Connection con = Database.getConnection();
        Statement stmt = con.createStatement();
        String query = "SELECT artists.Name FROM artists,albums,charts WHERE artists.ID = albums.Artist_ID AND albums.ID = charts.AlbumID ORDER BY charts.ID";
        ResultSet rs = stmt.executeQuery(query);
        html += "<h1>RANKS</h1><br>\n";
        html += "<table>\n";
        int columns = rs.getMetaData().getColumnCount();
        
        html += "<th>ID</th>";
        for(int i=0;i<columns;i++)
            html +=  "<th>" + rs.getMetaData().getColumnName(i+1) + "</th>";
        while(rs.next()) {
            html += "\n<tr>";
            for(int i=0;i<columns;i++) {
                html += "<td>" + rs.getRow() + "</td>";
                html += "<td>" + rs.getString(i+1) + "</td>\n";
            }
            html += "</tr>";
        }
        html += "\n</table>";
    }
    public void createFile() throws FileNotFoundException, IOException {
        html += "  </body>\n" +
                "</html>";
        PrintWriter writer = new PrintWriter(new File("htmlify.html"));
        writer.println(html);
        writer.close();
    }
}
