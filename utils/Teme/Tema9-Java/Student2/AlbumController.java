/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;
import entity.Artists;
import entity.Albums;
import java.sql.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
/**
 *
 * @author Eduard
 */
public class AlbumController {

    AlbumController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void create(String name, String country) throws SQLException {
        Connection con = DataBase.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into artists (name, country) values (?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, country);
            pstmt.executeUpdate();
        }
    }
    /*
    public Integer findByName(String name) throws SQLException {
        Connection con = DataBase.getConnection();
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.getResultSet()) {
            stmt.executeQuery("select id from artists where name='" + name + "'");
            return rs.next() ? rs.getInt(1) : null;
        }
    }
    */
    
    private EntityManagerFactory emf;
    public AlbumController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public void create(Albums artist) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(artist);
        em.getTransaction().commit();
        em.close();
    }

    
    public Albums findByName(String albumName) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("select t from Albums t where t.name=:name");
        List<Albums> albums = query.setParameter("name", albumName).getResultList();
        em.close();
        return albums.isEmpty() ? null : albums.get(0);
    }
    
    
    
}
