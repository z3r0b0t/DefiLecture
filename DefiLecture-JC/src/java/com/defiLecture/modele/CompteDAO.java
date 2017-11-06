/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.modele;

import com.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class CompteDAO extends DAO<Compte>{

    public CompteDAO(Connection cnx) {
        super(cnx);
    }

    @Override
    public boolean create(Compte x) {
        String req = "INSERT INTO compte (`COURRIEL` , `MOT_PASSE` , `NOM`, "
                + "`PRENOM`, `PSEUDONYME`, `AVATAR`, `PROGRAMME_ETUDE`) VALUES "
                +    "(?,?,?,?,?,?,?)";

        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);

              if(x.getCourriel() != null && !"".equals(x.getCourriel().trim())
              && x.getMotPasse() != null && !"".equals(x.getMotPasse().trim())     
              && x.getNom()      != null && !"".equals(x.getNom().trim())
              && x.getPrenom()   != null && !"".equals(x.getPrenom().trim())){

                paramStm.setString(1, Util.toUTF8(x.getCourriel()));
                paramStm.setString(2, Util.toUTF8(x.getMotPasse()));
                paramStm.setString(3, Util.toUTF8(x.getNom()));
                paramStm.setString(4, Util.toUTF8(x.getPrenom()));
                if(x.getPseudonyme() != null && !"".equals(x.getPseudonyme().trim()))
                    paramStm.setString(5, Util.toUTF8(x.getPseudonyme()));
                else
                    paramStm.setString(5, null);
                if(x.getAvatar() != null && !"".equals(x.getAvatar().trim()))
                    paramStm.setString(6, Util.toUTF8(x.getAvatar()));
                else
                    paramStm.setString(6, null);
                if(x.getProgrammeEtude() != null && !"".equals(x.getProgrammeEtude().trim()))
                    paramStm.setString(7, Util.toUTF8(x.getProgrammeEtude()));
                else
                    paramStm.setString(7, null);
                
                int nbLignesAffectees= paramStm.executeUpdate();
                
                if (nbLignesAffectees>0) {
                        paramStm.close();
                        return true;
                }
              }
            return false;
        }
        catch (SQLException exp) {
        }
        finally {
                try {
                    if (paramStm!=null)
                        paramStm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CompteDAO.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
        }
        return false;
    }

    @Override
    public Compte read(int id) {
        String req = "SELECT * FROM compte WHERE `ID_COMPTE` = ?";
        
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, id);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if(resultat.next()){

                Compte c = new Compte();
                c.setIdCompte(resultat.getInt("ID_COMPTE"));
                if(resultat.getInt("ID_EQUIPE") == 0)
                    c.setIdEquipe(-1);
                else
                    c.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                c.setCourriel(resultat.getString("COURRIEL"));
                c.setPrenom(resultat.getString("PRENOM"));             
                c.setNom(resultat.getString("NOM"));
                c.setMotPasse(resultat.getString("MOT_PASSE"));
                c.setPseudonyme(resultat.getString("PSEUDONYME"));             
                c.setAvatar(resultat.getString("AVATAR"));             
                c.setProgrammeEtude(resultat.getString("PROGRAMME_ETUDE"));
                c.setMinutesRestantes(resultat.getInt("MINUTES_RESTANTES"));
                c.setPoint(resultat.getInt("POINT"));
                c.setRole(resultat.getInt("ROLE"));

                resultat.close();
                paramStm.close();
                    return c;
            }
            
            resultat.close();
            paramStm.close();
            return null;
                
        }
        catch (SQLException exp) {
        }
        finally {
            try{
                if (paramStm!=null)
                    paramStm.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
            }
        }        
        
        return null;

    }

    @Override
    public Compte read(String id) {
        try{
            return this.read(Integer.parseInt(id));
        }
        catch(NumberFormatException e){
            return null;
        }        
    }
    
    @Override
    public boolean update(Compte x) {
                String req = "UPDATE compte SET COURRIEL = ?, MOT_PASSE = ?,"
                           + "NOM = ?, PRENOM = ?, PSEUDONYME = ?, AVATAR = ?,"
                           + "PROGRAMME_ETUDE = ?, ID_EQUIPE = ?, MINUTES_RESTANTES = ?,"
                           + "POINT = ?, ROLE = ? WHERE ID_COMPTE = ?";

        PreparedStatement paramStm = null;
        try {
            paramStm = cnx.prepareStatement(req);

            if(x.getCourriel()     != null && !"".equals(x.getCourriel().trim()) 
                && x.getMotPasse() != null && !"".equals(x.getMotPasse().trim())
                && x.getNom()      != null && !"".equals(x.getNom().trim())     
                && x.getPrenom()   != null && !"".equals(x.getPrenom().trim()))
            {
                paramStm.setString(1, Util.toUTF8(x.getCourriel()));
                paramStm.setString(2, Util.toUTF8(x.getMotPasse()));
                paramStm.setString(3, Util.toUTF8(x.getNom()));
                paramStm.setString(4, Util.toUTF8(x.getPrenom()));

                if(x.getPseudonyme() == null || "".equals(x.getPseudonyme().trim()))
                    paramStm.setString(5, null);
                else
                    paramStm.setString(5, Util.toUTF8(x.getPseudonyme()));

                if(x.getAvatar() == null || "".equals(x.getAvatar().trim()))
                    paramStm.setString(6, null);
                else
                    paramStm.setString(6, Util.toUTF8(x.getAvatar()));

                if(x.getProgrammeEtude() == null || "".equals(x.getProgrammeEtude().trim()))
                    paramStm.setString(7, null);
                else
                    paramStm.setString(7, Util.toUTF8(x.getProgrammeEtude()));

                if(x.getIdEquipe() == -1){
                    paramStm.setNull(8, java.sql.Types.INTEGER);
                }
                else
                    paramStm.setInt(8, x.getIdEquipe());
                paramStm.setInt(9, x.getMinutesRestantes());
                paramStm.setInt(10, x.getPoint());
                paramStm.setInt(11, x.getRole());

                paramStm.setInt(12, x.getIdCompte());


                int nbLignesAffectees= paramStm.executeUpdate();

                if (nbLignesAffectees>0) {
                    paramStm.close();
                    return true;
                }
            }                
        return false;
        }
        catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }
        finally {
                try {
                    if (paramStm!=null)
                        paramStm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CompteDAO.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                
        }
        return false;
    }

    @Override
    public boolean delete(Compte x) {
        String req = "DELETE FROM compte WHERE `ID_COMPTE` = ?";
        
        PreparedStatement paramStm = null;

        try {
                paramStm.setInt(1, x.getIdCompte());
                paramStm = cnx.prepareStatement(req);

                int nbLignesAffectees= paramStm.executeUpdate();
                
                if (nbLignesAffectees>0) {
                    paramStm.close();
                    return true;
                }
                
            return false;
        }
        catch (SQLException exp) {
        }
        catch (Exception exp) {
        }
        finally {
                try {
                    if (paramStm!=null)
                        paramStm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CompteDAO.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
        }
        return false;
    }
    
    @Override
    public List<Compte> findAll() {
                        
        List<Compte> liste = new LinkedList<>();
        try {
            Statement stm = cnx.createStatement(); 
            ResultSet r = stm.executeQuery("SELECT * FROM compte");
            while (r.next()) {
                Compte c = new Compte();
                c.setIdCompte(r.getInt("ID_COMPTE"));
                if(r.getInt("ID_EQUIPE")==0)
                    c.setIdEquipe(-1);
                else    
                    c.setIdEquipe(r.getInt("ID_EQUIPE"));
                c.setCourriel(r.getString("COURRIEL"));
                c.setMotPasse(r.getString("MOT_PASSE"));
                c.setNom(r.getString("NOM"));
                c.setPrenom(r.getString("PRENOM"));
                c.setPoint(r.getInt("POINT"));
                c.setMinutesRestantes(r.getInt("MINUTES_RESTANTES"));
                c.setProgrammeEtude(r.getString("PROGRAMME_ETUDE"));
                c.setAvatar(r.getString("AVATAR"));
                c.setPseudonyme(r.getString("PSEUDONYME")); 
                c.setRole(r.getInt("ROLE"));
                
                liste.add(c);
            }
            r.close();
            stm.close();
        }
        catch (SQLException exp){
        }
        return liste;

    }
    
    public List<Compte> findByIdEquipe(int idEquipe){
        List<Compte> liste = new LinkedList<>();
        String req = "SELECT * FROM compte WHERE `ID_EQUIPE` = ?";
        
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, idEquipe);

            ResultSet resultat = paramStm.executeQuery();
            while (resultat.next()) {
                Compte c = new Compte();
                c.setIdCompte(resultat.getInt("ID_COMPTE"));
                if(resultat.getInt("ID_EQUIPE") == 0)
                    c.setIdEquipe(-1);
                else
                    c.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                c.setCourriel(resultat.getString("COURRIEL"));
                c.setPrenom(resultat.getString("PRENOM"));             
                c.setNom(resultat.getString("NOM"));
                c.setMotPasse(resultat.getString("MOT_PASSE"));
                c.setPseudonyme(resultat.getString("PSEUDONYME"));             
                c.setAvatar(resultat.getString("AVATAR"));             
                c.setProgrammeEtude(resultat.getString("PROGRAMME_ETUDE"));
                c.setMinutesRestantes(resultat.getInt("MINUTES_RESTANTES"));
                c.setPoint(resultat.getInt("POINT"));
                c.setRole(resultat.getInt("ROLE"));
                liste.add(c);
            }
            resultat.close();
            paramStm.close();
        }
        catch (SQLException exp){
        }
        return liste;    
    
    }

    public Compte findByIdentifiantMotPasse(String identifiant, String motPasse){
        
        String req = "SELECT * FROM compte WHERE (`COURRIEL` = ? or "
                   + "`PSEUDONYME` = ?) and `MOT_PASSE` = ?";
        ResultSet resultat;
        
        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);

                paramStm.setString(1, Util.toUTF8(identifiant));
                paramStm.setString(2, Util.toUTF8(identifiant));
                paramStm.setString(3, Util.toUTF8(motPasse));

                resultat = paramStm.executeQuery();
                
                // On vérifie s'il y a un résultat    
                if(resultat.next()){
                    if(!motPasse.equals(resultat.getString("MOT_PASSE")))
                        return null;
                    if(!identifiant.equals(resultat.getString("PSEUDONYME")) &&
                       !identifiant.equals(resultat.getString("COURRIEL")) )
                        return null;
                    
                    Compte c = new Compte();
                    c.setIdCompte(resultat.getInt("ID_COMPTE"));
                    if(resultat.getInt("ID_EQUIPE") == 0)
                        c.setIdEquipe(-1);
                    else
                        c.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                    c.setCourriel(resultat.getString("COURRIEL"));
                    c.setPrenom(resultat.getString("PRENOM"));             
                    c.setNom(resultat.getString("NOM"));
                    c.setMotPasse(resultat.getString("MOT_PASSE"));
                    c.setPseudonyme(resultat.getString("PSEUDONYME"));             
                    c.setAvatar(resultat.getString("AVATAR"));             
                    c.setProgrammeEtude(resultat.getString("PROGRAMME_ETUDE"));
                    c.setMinutesRestantes(resultat.getInt("MINUTES_RESTANTES"));
                    c.setPoint(resultat.getInt("POINT"));
                    c.setRole(resultat.getInt("ROLE"));
                    
                    resultat.close();
                    paramStm.close();
                        return c;
                }
            resultat.close();
            paramStm.close();
            return null;
                
        }
        catch (SQLException exp) {
        }
        finally {
            try{
                if (paramStm!=null)
                    paramStm.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
            }
        }        
        
        return null;
    }

    public Compte findByPseudonyme(String pseudo) {
        String req = "SELECT * FROM compte WHERE `PSEUDONYME` = ?";
        
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setString(1, Util.toUTF8(pseudo));

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if(resultat.next()){

                Compte c = new Compte();
                c.setIdCompte(resultat.getInt("ID_COMPTE"));
                c.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                c.setCourriel(resultat.getString("COURRIEL"));
                c.setPrenom(resultat.getString("PRENOM"));             
                c.setNom(resultat.getString("NOM"));
                c.setPseudonyme(resultat.getString("PSEUDONYME"));             
                c.setAvatar(resultat.getString("AVATAR"));             
                c.setProgrammeEtude(resultat.getString("PROGRAMME_ETUDE"));
                c.setMinutesRestantes(resultat.getInt("MINUTES_RESTANTES"));
                c.setPoint(resultat.getInt("POINT"));
                c.setRole(resultat.getInt("ROLE"));

                resultat.close();
                paramStm.close();
                    return c;
            }
            
            resultat.close();
            paramStm.close();
            return null;
        }
        catch (SQLException exp) {}
        finally {
            try{
                if (paramStm!=null)
                    paramStm.close();
            }
            catch (SQLException exp) {}
        }        
        
        return null;

    }
    
    public int countCompteByIdEquipe(int idEquipe){
    
//        String req = "SELECT COUNT(ID_COMPTE), idEquipe FROM `compte` WHERE ID_EQUIPE = ? GROUP BY COUNT(ID_COMPTE)";
        String req = "SELECT COUNT(ID_DEMANDE_EQUIPE) FROM `demande_equipe` WHERE ID_EQUIPE = ? and STATUT_DEMANDE = 1";
       int nbMembre = 0;
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);
            paramStm.setInt(1, idEquipe);
            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if(resultat.next()){
                //nbMembre = resultat.getInt("COUNT(ID_COMPTE)");
                nbMembre = resultat.getInt("COUNT(ID_DEMANDE_EQUIPE)");
            }
            
            resultat.close();
            paramStm.close();
            return nbMembre;
        }
        catch (SQLException exp) {}
        finally {
            try{
                if (paramStm!=null)
                    paramStm.close();
            }
            catch (SQLException exp) {}
        }         
        return nbMembre;
    }
    
    
}