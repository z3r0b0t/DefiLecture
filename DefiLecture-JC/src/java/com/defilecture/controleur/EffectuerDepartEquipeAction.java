/**
    This file is part of DefiLecture.

    DefiLecture is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DefiLecture is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DefiLecture.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defilecture.controleur;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;
import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import com.defilecture.modele.DemandeEquipe;
import com.defilecture.modele.EquipeDAO;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.Equipe;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joel
 * @author Mikaël Nadeau
 */
public class EffectuerDepartEquipeAction extends Action implements RequirePRGAction, DataSender {
    private HashMap data;
    
    @Override
    public String execute() {
        String action = "echec.do?tache=afficherPageAccueil";
        if(session.getAttribute("connecte") == null
            || session.getAttribute("role") == null
            || request.getParameter("idEquipe") == null
            || request.getParameter("idCompte") == null){ }
        else if(!request.getParameter("idCompte")
            .equals(session.getAttribute("connecte")+"")
            && ((int)session.getAttribute("role") != Compte.CAPITAINE)
            && ((int)session.getAttribute("role") != Compte.ADMINISTRATEUR)){}
        else{
            try {
                String idCompte = request.getParameter("idCompte");
                String idEquipe = request.getParameter("idEquipe");
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                EquipeDAO equipeDao = new EquipeDAO(cnx);
                CompteDAO compteDao = new CompteDAO(cnx);
                Compte compte = compteDao.read(idCompte);
                Equipe equipe = equipeDao.read(idEquipe); 
                if(compte != null && equipe != null 
                    && equipe.getIdEquipe() == compte.getIdEquipe()){
                    
                    DemandeEquipeDAO demandeEqpDao = new DemandeEquipeDAO(cnx);
                    DemandeEquipe demandeEquipe = 
                            demandeEqpDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());
                    
                    if(demandeEquipe != null){
                        if(demandeEqpDao.delete(demandeEquipe)){
                            compte.setIdEquipe(-1);
                            compteDao.update(compte);
                            action="auRevoir.do?tache=afficherPageEquipe&idEquipe="+idEquipe;
                            data.put("succesRetrait", "Le matelot "+ compte.getPrenom()+ " " + compte.getNom() + " a été envoyé par-dessus bord");
                        }
                        else{
                            action="tuRestes.do?tache=afficherPageEquipe&idEquipe="+idEquipe;
                            data.put("erreurRetrait", "Le matelot "+ compte.getPrenom()+ " " + compte.getNom() + " n'a pas été envoyé par-dessus bord");

                       // demandeEquipe.setStatutDemande(0); //met à 0 si l'utilisateur est suspendu
                        //si l'un des enregistrements échouent alors on revient à l'état initial 
                     /*   if(!demandeEqpDao.update(demandeEquipe) || !compteDao.update(compte)){
                            demandeEquipe.setStatutDemande(1);
                            compte.setIdEquipe(equipe.getIdEquipe());
                            demandeEqpDao.update(demandeEquipe);
                            compteDao.update(compte);
                            action = "echec.do?tache=afficherPageEquipe&idEquipe="+idEquipe; */
                        }
                    }
                }
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EffectuerDepartEquipeAction.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            finally{
                Connexion.close();
            }

        }
        
        
        return action;
        
    }

    @Override
    public void setData(Map<String, Object> data) {
        this.data = (HashMap) data;
    }
    
    
}
