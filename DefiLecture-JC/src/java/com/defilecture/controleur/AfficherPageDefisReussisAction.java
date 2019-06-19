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

import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

/**
 *
 * @author Charles
 * @author Mikaël Nadeau
 * @author Mikaël
 * @author Mikaël Nadeau
 */
public class AfficherPageDefisReussisAction extends Action {
    @Override
    public String execute() {
        if( session.getAttribute("connecte") != null && session.getAttribute("role") != null) {
            try {
                if( (int)session.getAttribute("role") == Compte.MODERATEUR
                    || (int)session.getAttribute("role") == Compte.ADMINISTRATEUR) {  

                    Connexion.setUrl(Config.URL);
                    Connexion.setUser(Config.DB_USER);
                    Connexion.setPassword(Config.DB_PWD);
                    Connection cnx = Connexion.getInstance();
                    CompteDAO dao = new CompteDAO(cnx);

                    if(dao.read((int)session.getAttribute("connecte"))!=null)
                        request.setAttribute("vue", "pageDefisReussis.jsp");

                    return "/index.jsp";
                }
            }
            catch (SQLException ex) {
                Logger.getLogger(AfficherPageGestionListeComptesAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                Connexion.close();
            }
        }
        return "/index.jsp";
    }
}
