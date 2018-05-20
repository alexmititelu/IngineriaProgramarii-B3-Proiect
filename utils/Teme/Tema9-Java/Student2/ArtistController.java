/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;
import entity.Artists;
import java.sql.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
/**
 *
 * @author Eduard
 */
public class ArtistController {
    
    public ArtistController() {
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
    public ArtistController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public void create(Artists artist) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(artist);
        em.getTransaction().commit();
        em.close();
    }

    
    public Artists findByName(String artistName) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("select t from Artist t where t.name=:name");
        List<Artists> artists = query.setParameter("name", artistName).getResultList();
        em.close();
        return artists.isEmpty() ? null : artists.get(0);
    }
}
