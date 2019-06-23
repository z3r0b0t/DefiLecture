/**
 * This file is part of DefiLecture.
 *
 * <p>DefiLecture is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>DefiLecture is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with DefiLecture. If
 * not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defilecture.controleur;

import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import com.defilecture.modele.Equipe;
import com.defilecture.modele.EquipeDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

/**
 * @author Joel
 * @author Mikaël Nadeau
 */
public class EffectuerModificationEquipeAction extends Action
    implements RequirePRGAction, DataSender {
  HashMap data;

  @Override
  public String execute() {
    String action = "*.do?tache=afficherPageAccueil";
    if (request.getParameter("idEquipe") != null) {
      action = "*.do?tache=afficherPageEquipe&idEquipe=" + request.getParameter("idEquipe");
      if (request.getParameter("modifier") != null) {
        if (userIsConnected()
            && (int) session.getAttribute("role") == Compte.CAPITAINE
            && request.getParameter("nom") != null) {
          try {
            Connection cnx =
                Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
            Compte compte = new CompteDAO(cnx).read((int) session.getAttribute("connecte"));
            EquipeDAO equipeDao = new EquipeDAO(cnx);
            Equipe equipe = equipeDao.findByNom(request.getParameter("nom"));
            if (compte != null && equipe == null && compte.getIdEquipe() != -1) {
              equipe = equipeDao.read(compte.getIdEquipe());
              equipe.setNom(request.getParameter("nom"));

              if (equipeDao.update(equipe)) {
                action =
                    "*.do?tache=afficherPageModificationEquipe&idEquipe="
                        + request.getParameter("idEquipe");
                data.put("succesNom", "L'enregistrement du nouveau nom s'est fait avec succès");
              } else {
                data.put(
                    "erreurModification",
                    "Un problème est survenu lors de la modification des informations");
              }
            } else {
              data.put(
                  "erreurNom",
                  "Le nom "
                      + request.getParameter("nom")
                      + " est déjà utilisé par un autre équipage");
              action =
                  "*.do?tache=afficherPageModificationEquipe&idEquipe="
                      + request.getParameter("idEquipe");
            }
          } catch (SQLException ex) {
            Logger.getLogger(EffectuerModificationEquipeAction.class.getName())
                .log(Level.SEVERE, null, ex);
          }
        }
      }
    }
    return action;
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
