/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import jdbc.Connexion;

/**
 *
 * @author Charles
 */
public class LectureDAO extends DAO<Lecture> {
    
    public LectureDAO(Connection c)
    {
        super(c);
    }

    @Override
    public boolean create(Lecture x) {
               
        String req = "INSERT INTO lecture (`ID_COMPTE` , `TITRE` , `DATE_INSCRIPTION` , `DUREE_MINUTES`) VALUES "+
			     "(?,?,?,?)";

        PreparedStatement paramStm = null;
        try 
        {

                paramStm = cnx.prepareStatement(req);

                
                paramStm.setInt(1, x.getIdCompte());
                paramStm.setString(2, x.getTitre());
                paramStm.setString(3, x.getDateInscription());
                paramStm.setInt(4, x.getDureeMinutes());

                int n= paramStm.executeUpdate();
                
                if (n>0)
                {
                        paramStm.close();
                        //stm.close();
                        return true;
                }
        }
        catch (SQLException exp)
        {
        }
        finally
        {
                if (paramStm!=null)
                    Connexion.close();
                  /*if(stm != null)
                      Connexion.close();*/
                  
        }
        return false;
    }

    @Override
    public Lecture read(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lecture read(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Lecture x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Lecture x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Lecture> findAll() {
        List<Lecture> liste = new LinkedList<>();
		try 
		{
			Statement stm = cnx.createStatement(); 
			ResultSet r = stm.executeQuery("SELECT * FROM lecture");
			while (r.next())
			{
				Lecture l = new Lecture();
                                l.setIdLecture(r.getInt("ID_LECTURE"));
				l.setIdCompte(r.getInt("ID_COMPTE"));
				l.setTitre(r.getString("TITRE"));
                                l.setDateInscription(r.getString("DATE_INSCRIPTION"));
				l.setDureeMinutes(r.getInt("DUREE_MINUTES"));
				l.setObligatoire(r.getInt("EST_OBLIGATOIRE"));
				liste.add(l);
			}
			r.close();
			stm.close();
		}
		catch (SQLException exp)
		{
		}
		return liste;
    }
    
}
