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
package com.defilecture.controleur;

import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import com.defilecture.modele.DemandeEquipe;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.Equipe;
import com.defilecture.modele.EquipeDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

/** @author Joel */
public class EffectuerCreationEquipeAction
    implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender {
  private HttpSession session;
  private HttpServletResponse response;
  private HttpServletRequest request;
  private HashMap data;

  @Override
  public String execute() {
    if (session.getAttribute("connecte") != null) {
      if ((int) session.getAttribute("role") == Compte.CAPITAINE) {
        String nom = request.getParameter("nom");
        if (nom != null) {
          Equipe equipe = new Equipe();
          equipe.setNom(nom);
          // equipe.setIdCapitaine((int)session.getAttribute("id"));
          try {
            Connection cnx =
                Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
            EquipeDAO daoEquipe = new EquipeDAO(cnx);
            // idéalement créer le tout seulement si et seulement si toutes les conditions sont
            // vraies
            if (daoEquipe.create(equipe)) {
              equipe = daoEquipe.findByNom(equipe.getNom());
              if (equipe != null) {
                CompteDAO daoCompte = new CompteDAO(cnx);
                Compte compte = daoCompte.read((int) session.getAttribute("connecte"));

                compte.setIdEquipe(equipe.getIdEquipe());

                if (daoCompte.update(compte)) {
                  DemandeEquipeDAO daoDemandeEquipe = new DemandeEquipeDAO(cnx);
                  DemandeEquipe demande = new DemandeEquipe();
                  demande.setIdCompte(compte.getIdCompte());
                  demande.setIdEquipe(compte.getIdEquipe());

                  demande.setStatutDemande(1);
                  if (daoDemandeEquipe.create(demande))
                    return "creationEquipeCompletee.do?tache=afficherPageEquipe&idEquipe="
                        + equipe
                            .getIdEquipe(); // soit afficher le page avec utilisateur pour pouvoir
                                            // enoyer une demande
                }
              } else {
                data.put("erreurNom", "Ce nom est déjà utilisé par un équipage");
                return "creation.do?tache=afficherPageCreationEquipe";
              }
            }
          } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherPageCreationEquipeAction.class.getName())
                .log(Level.SEVERE, null, ex);
            return "creation.do?tache=afficherPageCreationEquipe";
          } catch (SQLException ex) {
            Logger.getLogger(EffectuerCreationEquipeAction.class.getName())
                .log(Level.SEVERE, null, ex);
            return "creation.do?tache=afficherPageCreationEquipe";

          } finally {
            Connexion.close();
          }
        }
        return "creation.do?tache=afficherPageCreationEquipe";
      }
      return "creation.do?tache=afficherPageAccueil";
    }
    return "connexion.do?tache=afficherPageConnexion";
  }

  @Override
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }

  @Override
  public void setSession(HttpSession session) {
    this.session = session;
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
