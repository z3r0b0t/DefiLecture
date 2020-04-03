<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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

          <%-- Sous-menu de gestion des lectures --%>
          <div id="menuLectures">
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">Lectures<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="*.do?tache=afficherPageGestionLectures">Gérer les lectures</a></li>
              </ul>
            </li>
          </div>

          <%-- Sous-menu de gestion des épreuves --%>
          <div id="menuDefis">
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">Épreuves<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="*.do?tache=afficherPageCreationDefi">Cr&eacute;er une épreuve</a></li>
                <li><a href="*.do?tache=afficherPageDefisReussis">Liste des épreuves réussies</a></li>
                <li><a href="*.do?tache=afficherPageParticipationDefi">Voir les épreuves</a></li>
              </ul>
            </li>
          </div>

          <li>
            <a href="*.do?tache=afficherPageConfiguration">Configuration du site</a>
          </li>
        </ul>


        <%-- 2ème menu à droite de déconnexion --%>
        <ul class="nav navbar-nav navbar-right">
          <li id='li-facebook'>
            <a id='facebook'  target="_blank" href='https://www.facebook.com/DefiLectureCollegeRosemont/'></a>
          </li>
          <li id='li-facebook'>
            <a id='instagram'  target="_blank" href='https://www.instagram.com/defilecture/'></a>
          </li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
              <% out.println(application.getAttribute("vocCode"));%><span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li><a href='*.do?tache=afficherPageMarcheASuivre'>Marche à suivre</a></li>
                <li><a href='*.do?tache=afficherPageCodeConduite'>Code de conduite</a></li>
                <li><a href='*.do?tache=afficherPageGlossaire'>Glossaire</a></li>
                <li><a href='*.do?tache=afficherPageContributeurs'>Contributeurs</a></li>
            </ul>
          </li>

          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
              <span class="glyphicon glyphicon-cog"></span>
              <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
              <li><a href='details.do?tache=afficherPageProfil&id=${sessionScope.connecte}'><span class="glyphicon glyphicon-user"></span> Mon Compte</a></li>
              <li><a href='*.do?tache=effectuerDeconnexion'><span class="glyphicon glyphicon-log-in"></span> Se d&eacute;connecter</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</div>
