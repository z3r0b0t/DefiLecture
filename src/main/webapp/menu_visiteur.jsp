<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.dInscription']}" var="datedebut" type="both"/>
<fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.fLecture']}" var="datefin" type="both"/>

<div class='container-fluid'  style="margin-bottom: 90px" >
  <nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand logo-navigation" href='*.do?tache=afficherPageMarcheASuivre'></a>
        <button id="btn-burger" type="button" class="navbar-toggle" data-toggle="collapse" data-target="#optionsNavigation">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
      </div>

      <div class="collapse navbar-collapse" id="optionsNavigation">
        <ul class="nav navbar-nav">
          <li>
            <a href="scoreboard.do?tache=afficherPageTableauScores">
                <% out.println(application.getAttribute("vocBanque"));%>
            </a>
          </li>
          <c:if test="${ now ge datedebut && now lt datefin }">
            <div id="menuInscription">
              <li>
                <a href='*.do?tache=afficherPageInscription'>S'inscrire</a>
              </li>
            </div>
          </c:if>
        </ul>

        <%-- Menu de connexion et des règles --%>
        <ul class="nav navbar-nav navbar-right">
          <li id='li-facebook'>
            <a id='facebook'  target="_blank" href='https://www.facebook.com/DefiLectureCollegeRosemont/'></a>
          </li>
          <li id='li-facebook'>
            <a id='instagram'  target="_blank" href='https://www.instagram.com/defilecture/'></a>
          </li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><% out.println(application.getAttribute("vocCode"));%><span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href='*.do?tache=afficherPageMarcheASuivre'>Marche à suivre</a></li>
              <li><a href='*.do?tache=afficherPageCodeConduite'>Code de conduite</a></li>
              <li><a href='*.do?tache=afficherPageGlossaire'>Glossaire</a></li>
              <li><a href='*.do?tache=afficherPageContributeurs'>Contributeurs</a></li>
            </ul>
          </li>
          <c:if test="${ now ge datedebut && now lt datefin }">
            <div id="menuConnexion">
              <li><a href='*.do?tache=afficherPageConnexion'><span class="glyphicon glyphicon-log-in"></span> Se connecter</a></li>
            </div>
          </c:if>
        </ul>
      </div>
    </div>
  </nav>
</div>
