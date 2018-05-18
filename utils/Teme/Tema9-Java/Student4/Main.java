
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
/*
create table artists(
    id integer NOT NULL,
    name varchar(100) not null,
    country varchar(100),
    primary key (id)
);
create table albums(
    id integer,
    name varchar(100) not null,
    artist_id integer not null,
    release_year integer,
    primary key (id)
);
create table charts (
  ID INTEGER,
  AlbumID INTEGER,
  Primary KEY(ID)
)

select * from artists;
select * from albums;
select * from charts;
      truncate table artists;
      truncate table albums;
      truncate table charts;
*/
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
         try {
            int n,phoenixId,radioheadId;
            Charts charts = new Charts();
            HTMLify htmlify = new HTMLify();
            ArtistController artists = new ArtistController();
            AlbumController albums = new AlbumController();
            
            artists.create("Radiohead", "United Kingdom");
            artists.create("Phoenix", "Romania");
            Database.commit();
		
            phoenixId = artists.findByName("Phoenix");
            albums.create(phoenixId, "Mugur de fluier", 1974);
					
            radioheadId = artists.findByName("Radiohead");
            albums.create(radioheadId, "OK Computer", 1997);
            albums.create(radioheadId, "Kid A", 2000);
            albums.create(radioheadId, "In Rainbows", 2007);
            
            albums.list(radioheadId); 
            Database.commit();
            
            List params = new LinkedList();
            params.add("asdasd");
            params.add("Asdas");
            artists.insert(params);
            
            params.remove(1);
            params.add(3);
            params.add(4);
            albums.insert(params);
            
            params.clear();
            params.add("ID");
            params.add("Name");
            params = artists.select(params,"WHERE ID = 3 ");
            System.out.println("Am selectat: " + Arrays.toString(params.toArray()));
            
            params.clear();
            params.add("Name");
            params.add("ttest");
            artists.update(params, "WHERE ID = 1");
            
            n = charts.getRandom(150);
            while(n-- != 0) {
                String name = charts.getStringOfLength(20);
                String country = charts.getStringOfLength(5);
                params.clear();
                params.add(name);
                params.add(country);
                artists.insert(params);
            }
            n = charts.getRandom(150);
            while(n-- != 0) {
                String name = charts.getStringOfLength(20);
                params.clear();
                params.add(name);
                params.add(charts.getRandom(50));
                params.add(charts.getRandom(2018));
                albums.insert(params);
            }
            
            charts.populateChartsTable();
            charts.displayRanks();
            
            Ranking ranking = new Ranking();
            ranking.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ranking.populate();
            
            htmlify.initiate();
            htmlify.createTable("SELECT * FROM artists");
            htmlify.createTable("SELECT * FROM albums");
            htmlify.createTable("SELECT * FROM charts");
            htmlify.createRanksTable();
            htmlify.createFile();
            
            Database.closeConnection();
        } catch (SQLException e) {
            System.err.println(e);
            Database.rollback();
        }
    }
    
}
