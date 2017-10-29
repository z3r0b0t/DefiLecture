<%--
    Document   : pageListeDemandesEquipe
    Created on : 2017-10-28, 08:15:58
    Author     : Joel
--%>
<%@page import="modele.EquipeDAO"%>
<%@page import="modele.Compte"%>
<%@page import="modele.CompteDAO"%>
<%@page import="modele.DemandeEquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.connecte or (!(sessionScope.role eq 2) and (requestScope.ordre eq 'recu'))}">
    <jsp:forward page="*.do?tache=afficherPageAccueil"></jsp:forward>
</c:if>


<%  Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    DemandeEquipeDAO demandeEqDao = new DemandeEquipeDAO(cnx);

    if("recu".equals(request.getParameter("ordre"))){
        CompteDAO cptDao = new CompteDAO(cnx);
        Compte compte = new Compte();
        if(request.getParameter("idEquipe")==null)
            compte = cptDao.read((int)session.getAttribute("connecte"));
        pageContext.setAttribute("cptDao", cptDao);
        pageContext.setAttribute("listeDemandes", demandeEqDao.findByIdEquipeStatutDemande(compte.getIdEquipe(),-1));
    }
    else{
        EquipeDAO eqpDao = new EquipeDAO(cnx);
        pageContext.setAttribute("demandeEqDao", eqpDao);
        pageContext.setAttribute("listeDemandes", demandeEqDao.findByIdCompte((int)session.getAttribute("connecte")));
    }
%>


<h2>Liste des demandes</h2>

    <table class="table">

      <thead>
        <tr>
          <th>Demandes d'adh&eacute;sion</th>
          <th>&Eacute;tat de la demande</th>
          <th></th>
        </tr>
      </thead>

      <tbody>
      <c:choose>
          
      <c:when test="${requestScope.ordre eq 'recu'}">
       <c:forEach items="${listeDemandes}" var="demande">
        <c:if test="${sessionScope.role eq 2}">
         <c:set var="auteur" value="${cptDao.read(demande.idCompte)}"></c:set>
         <tr>
            <td>Demande envoy&eacute;e par ${auteur.prenom} ${auteur.nom}</td>
            <td>
                <a href="accepter.do?tache=accepterDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}">Accepter</a>
                <a href="refuser.do?tache=refuserDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}">Refuser</a>
            </td>
         </tr>
        </c:if>
       </c:forEach>
      </c:when>
         
      <c:otherwise>
       <c:forEach items="${listeDemandes}" var="demande">
        <c:set var="equipe" value="${eqpDao.read(demande.idEquipe)}"></c:set>
        <tr>
            <td>Demande envoy&eacute;e l'équipe <a href="equipe.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></td>
            <td>État</td>
        </tr>
       </c:forEach>
      </c:otherwise>
         
      </c:choose>
      </tbody>

    </table>

