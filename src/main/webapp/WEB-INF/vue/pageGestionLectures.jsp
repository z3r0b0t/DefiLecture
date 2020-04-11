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

<%-- Documentation de la libraire JS : https://table-sortable.now.sh/story-latest.html --%>
<link rel="stylesheet" href="css/table-sortable.css" />
<script src="script/table-sortable.js"></script>

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
  var data = [
  <c:forEach items="${listeLectures}" var="lecture">
    <c:set var="compte" value="${ daoCompte.read(lecture.idCompte) }"/>
    <c:set var="equipe" value="${ daoEquipe.read(compte.idEquipe) }"/>
      {
        <c:choose>
          <c:when test="${ sessionScope.role >= 3 }">
                titre:"${lecture.titre}",
                dateInscription:"${lecture.dateInscription}",
                courriel:"${compte.courriel}",
                equipe:"${equipe.nom}",
                obligatoire:"${lecture.estObligatoire eq 0 ? "NON" : "OUI"}",
                actions: '<a href="*.do?tache=effectuerSuppressionLecture&idLecture=${lecture.idLecture}&idCompte=${compte.idCompte}" onclick="return confirm(\"Êtes vous sûr de vouloir supprimer cette lecture?\")">Supprimer</a><a href="*.do?tache=afficherPageModificationLecture&idLecture=${lecture.idLecture}">Modifier</a>',
          </c:when>
          <c:otherwise>
              titre:"${lecture.titre}",
              dateInscription:"${lecture.dateInscription}",
              obligatoire:"${lecture.estObligatoire eq 0 ? "NON" : "OUI"}",
          </c:otherwise>
        </c:choose>
      },
  </c:forEach>
  ];

  var columns = {
    titre: 'Titre',
    dateInscription: "Date d'inscription",
    obligatoire: 'Obligatoire',
    <c:if test="${ sessionScope.role >= 3 }">
      equipe: 'Équipe',
      courriel: 'Courriel',
      actions: 'Actions',
    </c:if>
  };

  var options = {
    data: data,
    columns: columns,
    rowsPerPage: 10,
    pagination: true,
    searchField: '#searchField',
  };

  $(function() {
    var table = $("#table-sortable").tableSortable(options);
  });
</script>

<div class="row listeCompte-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 listeCompte-col">
        <h2>Liste des lectures</h2>
        <input type="text" placeholder="Chercher..." id="searchField">
        <div id="table-sortable"></div>
    </div>
</div>
