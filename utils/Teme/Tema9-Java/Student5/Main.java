import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            ArtistController artists = new ArtistController();

            artists.create("Radiohead", "United Kingdom");
            artists.create("Phoenix", "Romania");
            Database.commit();

            int phoenixId = artists.findByName("Phoenix");

            AlbumController albums = new AlbumController();
            albums.create(phoenixId, "Mugur de fluier", 1974);

            //Add the Radiohead's albums ("OK Computer", 1997), ("Kid A", 2000), ("In Rainbows", 2007)

            int radioheadId = artists.findByName("Radiohead");
            albums.create(radioheadId,"OK Computer",1997);
            albums.create(radioheadId, "Kid A", 2000);
            albums.create(radioheadId,"In Rainbows",2007);
            albums.list(radioheadId); //displays all the albums from the specified artist
            Database.commit();

            Database.closeConnection();
        } catch (SQLException e) {
            System.err.println(e);
            Database.rollback();
        }
    }
}
