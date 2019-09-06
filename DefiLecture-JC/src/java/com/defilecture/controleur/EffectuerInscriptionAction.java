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
import com.util.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerInscriptionAction extends Action implements RequirePRGAction, DataSender {
  private HashMap data;

  @Override
  public String execute() {
    boolean erreur = false;
    String action = "echec.do?tache=afficherPageInscription";

    if (request.getParameter("pseudonyme") != null) {
      data.put("pseudonyme", Util.toUTF8(request.getParameter("pseudonyme")));
    }

    if (request.getParameter("programmeEtude") != null) {
      data.put("programmeEtude", request.getParameter("programmeEtude"));
    }

    if (request.getParameter("courriel") == null) {
      erreur = true;
      data.put("erreurCourriel", "Veuillez entrer votre courriel");
    } else {
      data.put("courriel", request.getParameter("courriel"));
    }

    if (request.getParameter("prenom") == null) {
      erreur = true;
      data.put("erreurPrenom", "Veuillez entrer votre prenom");
    } else {
      data.put("prenom", Util.toUTF8(request.getParameter("prenom")));
    }

    if (request.getParameter("nom") == null) {
      erreur = true;
      data.put("erreurNom", "Veuillez entrer votre nom");
    } else {
      data.put("nom", Util.toUTF8(request.getParameter("nom")));
    }

    if ((request.getParameter("motPasse") != null)
        && request.getParameter("confirmationMotPasse") != null
        && !Util.toUTF8(request.getParameter("motPasse"))
            .equals(Util.toUTF8(request.getParameter("confirmationMotPasse")))) {
      erreur = true;
      data.put(
          "erreurMotPasseIdentique",
          "Les deux champs concernant les mots de passe ne sont pas identiques");
    }

    if (!erreur) {
      try {
        String courriel = request.getParameter("courriel"),
            prenom = Util.toUTF8(request.getParameter("prenom")),
            nom = Util.toUTF8(request.getParameter("nom")),
            motPasse = Util.toUTF8(request.getParameter("motPasse")),
            programmeEtude = request.getParameter("programmeEtude"),
            pseudonyme = Util.toUTF8(request.getParameter("pseudonyme")),
            sel = Util.genererSel();

        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        CompteDAO dao = new CompteDAO(cnx);
        Compte compte = new Compte();
        compte.setCourriel(courriel);
        compte.setPrenom(prenom);
        compte.setNom(nom);
        compte.setMotPasse(Util.hasherAvecSel(motPasse, sel));
        compte.setSel(sel);
        compte.setPseudonyme(pseudonyme);
        compte.setProgrammeEtude(programmeEtude);

        if (request.getParameter("devenirCapitaine") != null) {
          compte.setDevenirCapitaine(Integer.parseInt(request.getParameter("devenirCapitaine")));
        }

        // faire vérification avec des findBy
        if (dao.findByCourriel(courriel) != null) {
          data.put("erreurCourriel", "Ce courriel est déjà utilisé par un moussaillon");

        } else if (dao.findByPseudonyme(pseudonyme) != null) {
          data.put("erreurPseudonyme", "Ce pseudonyme est déjà utilisé par un moussaillon");
        } else {
          if (dao.create(compte)) {
            data.put("succesInscription", "Un compte a été créé avec succès");
            action = "succes.do?tache=afficherPageConnexion";
          } else {
            data.put(
                "erreurInscription",
                "Problème de création du compte. Veuillez réessayer. Si le problème survient à répétition, contactez un administrateur.");
          }
        }
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerInscriptionAction.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return action;
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
