<!--
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
-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="com.defilecture.modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>

<script src="script/list.min.js"></script>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="daoLecture" scope="page" class="com.defilecture.modele.LectureDAO">
    <jsp:setProperty name="daoLecture" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoCompte" scope="page" class="com.defilecture.modele.CompteDAO">
    <jsp:setProperty name="daoCompte" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoEquipe" scope="page" class="com.defilecture.modele.EquipeDAO">
    <jsp:setProperty name="daoEquipe" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>

<c:set var="maxItemParPage" value="10" scope="page"/>
<c:choose>
  <c:when test="${ sessionScope.role >= 3 }">
    <c:set var="listeLectures" value="${ daoLecture.findAll() }"/>
  </c:when>
  <c:otherwise>
    <c:set var="listeLectures" value="${ daoLecture.findByIdCompte(sessionScope.currentId) }"/>
  </c:otherwise>
</c:choose>

<script type="application/javascript">
  var data = [];
  var courriels = [];
  var equipes = [];

  <c:forEach items="${listeLectures}" var="lecture">
    <c:set var="compte" value="${ daoCompte.read(lecture.idCompte) }"/>
    <c:set var="equipe" value="${ daoEquipe.read(compte.idEquipe) }"/>
        <c:choose>
          <c:when test="${ sessionScope.role >= 3 }">
            courriels.push("${compte.courriel}");
            equipes.push("${equipe.nom}");
            data.push({
                titre:"${lecture.titre}",
                dateInscription:"${lecture.dateInscription}",
                courriel:"${compte.courriel}",
                equipe:"${equipe.nom}",
                obligatoire:"${lecture.estObligatoire eq 0 ? "NON" : "OUI"}",
                actions: '<a href="*.do?tache=effectuerSuppressionLecture&idLecture=${lecture.idLecture}&idCompte=${compte.idCompte}" onclick="return confirm(\'Êtes vous sûr de vouloir supprimer cette lecture?\');">Supprimer</a><a href="*.do?tache=afficherPageModificationLecture&idLecture=${lecture.idLecture}">Modifier</a>',
            });
          </c:when>
          <c:otherwise>
            data.push({
              titre:"${lecture.titre}",
              dateInscription:"${lecture.dateInscription}",
              obligatoire:"${lecture.estObligatoire eq 0 ? "NON" : "OUI"}",
            });
          </c:otherwise>
        </c:choose>
  </c:forEach>

  var columns = [
    'titre',
    'dateInscription',
    'obligatoire',
    <c:if test="${ sessionScope.role >= 3 }">
      'equipe',
      'courriel',
      'actions',
    </c:if>
  ];

  var itemTemplate = "<tr>";
  itemTemplate += "<td class='titre'></td>";
  itemTemplate += "<td class='dateInscription'></td>";
  itemTemplate += "<td class='obligatoire'></td>";
  <c:if test="${ sessionScope.role >= 3 }">
    itemTemplate += "<td class='equipe'></td>";
    itemTemplate += "<td class='courriel'></td>";
    itemTemplate += "<td class='actions' colspan='2'></td>";
  </c:if>
  itemTemplate += "</tr>";

  var lecturesList;
</script>
<script language="javascript" src="./script/jsPageGestionLectures.js"></script>

<div class="row listeCompte-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 listeCompte-col">
      
      <%-- Section de filtrage des lectures pour les admins --%>
      <c:if test="${ sessionScope.role >= 3 }">
        <h2>Filtrer les lectures</h2>
        <div class="filter-section">
          <div class="filter">Titre : <input type="text" id="filterTitre" placeholder="Recherche un titre..." /></div>
          <div class="filter">Date d'inscription : <input type="date" id="filterDateInscriptionFrom"/> à <input type="date" id="filterDateInscriptionTo"/></div>
          <br>
          <div class="filter">Équipe : 
            <select id="filterEquipe">
              <option value="">*</option>
            </select>
          </div>
          <div class="filter">Obligatoire : 
            <select id="filterObligatoire">
              <option value="">*</option>
              <option value="OUI">OUI</option>
              <option value="NON">NON</option>
            </select>
          </div>
          <div class="filter">Courriel : 
            <select id="filterCourriel">
              <option value="">*</option>
            </select>
          </div>
        </div>
      </c:if>

      <h2>Liste des lectures</h2>
      <div id="lectures">
        <c:if test="${ sessionScope.role < 3 }">
          <div class="searchArea">
            <input type="text" class="search" name="recherche" style="width:45%;height:30px;font-size:125%;position:relative;left:15px;" maxlength="60" value="">
            <button title="Explorer!" style="padding:0px;margin-top:15px;height:33px;background:none;border:none;position:relative;top:-3px;left:20px"><img src="./images/victorian_search_icon2.png" height="100%"></button>
          </div>
        </c:if>
        <table class="table">
          <thead>
            <tr>
              <th class="sort" data-sort="titre">Titre</th>
              <th class="sort" data-sort="dateInscription">Date d'inscription</th>
              <th class="sort" data-sort="obligatoire">Obligatoire</th>
              <th class="sort" data-sort="equipe">Équipe</th>
              <th class="sort" data-sort="courriel">Courriel</th>
              <th class="sort" data-sort="actions"></th>
            </tr>
          </thead>
          <tbody class="list">
          </tbody>
        </table>
        <ul class="pagination"></ul>
      </div>
    </div>
</div>
