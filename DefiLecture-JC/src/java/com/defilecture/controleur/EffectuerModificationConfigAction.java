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

import com.defilecture.modele.ConfigSite;
import com.defilecture.modele.ConfigSiteDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerModificationConfigAction extends Action implements RequirePRGAction {
  private ConfigSite configUpdate;
  private ConfigSiteDAO configDAO;

  @Override
  public String execute() {
    if (userIsConnected() && (userIsAdmin() || userIsModerateur())) {
      try {
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        configDAO = new ConfigSiteDAO(cnx);
        configUpdate = new ConfigSite();
        String regDate = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}";

        if(request.getParameter("accesA") != null && request.getParameter("accesA").matches(regDate)) {
          configUpdate.getConfig().put("accesA", request.getParameter("accesA").replace('T',' '));
        }

        if(request.getParameter("accesDe") != null && request.getParameter("accesDe").matches(regDate)) {
          configUpdate.getConfig().put("accesDe", request.getParameter("accesDe").replace('T', ' ');
        }

        if(request.getParameter("dInscription") != null && request.getParameter("dInscription").matches(regDate)) {
          configUpdate.getConfig().put("dInscription", request.getParameter("dInscription").replace('T', ' ');
        }

        if(request.getParameter("dLecture") != null && request.getParameter("dLecture").matches(regDate)) {
          configUpdate.getConfig().put("dLecture", request.getParameter("dLecture").replace('T', ' ');
        }

        if(request.getParameter("fLecture") != null && request.getParameter("fLecture").matches(regDate)) {
          configUpdate.getConfig().put("fLecture", request.getParameter("fLecture").replace('T', ' ');
        }

        if(request.getParameter("nbMatelots") != null && request.getParameter("nbMatelots").matches(regDate)) {
          configUpdate.getConfig().put("nbMatelots", request.getParameter("nbMatelots").replace('T', ' ');
        }

        if(request.getParameter("limiteSoft") != null && request.getParameter("limiteSoft").matches(regDate)) {
          configUpdate.getConfig().put("limiteSoft", request.getParameter("limiteSoft").replace('T', ' ');
        }

        if(request.getParameter("limiteHard") != null && request.getParameter("limiteHard").matches(regDate)) {
          configUpdate.getConfig().put("limiteHard", request.getParameter("limiteHard").replace('T', ' ');
        }

        configDAO.update(configUpdate);

        for (Map.Entry<String, String> entry : configUpdate.getConfig().entrySet()) {
          session
              .getServletContext()
              .setAttribute("com.defilecture." + entry.getKey(), entry.getValue());
        }
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerModificationConfigAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } finally {
        Connexion.close();
      }
    }
    return "*.do?tache=afficherPageConfiguration";
  }
}
