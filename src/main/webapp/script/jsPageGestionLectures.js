/* 
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
<%-- 
    Document   : jsPageGestionLectures
    Created on : 2018-12-16, 20:52:35
    Author     : Roodney Aladin
--%>
 */

function arrayUniqueValuesOnly(array) {
  let unique = {};
  array.forEach(function(i) {
    if(!unique[i]) {
      unique[i] = true;
    }
  });
  return Object.keys(unique);
}

// Returns a date object from 'yyyy-MM-dd' format
function stringToDate(dateString) {
  if(dateString == "") {
    return null;
  }

  var dateValues = dateString.split('-');
  var y = dateValues[0];
  var m = dateValues[1];
  var d = dateValues[2];
  return new Date(y,m,d);
}

function filterTitre(item, titre) {
  return titre == "" ? true : item.values().titre.includes(titre);
}

function filterDateInscriptionTo(item, dateTo) {
  var date = stringToDate(item.values().dateInscription);
  if(dateTo) {
    return date <= dateTo;
  }
  return true;
}

function filterDateInscriptionFrom(item, dateFrom) {
  var date = stringToDate(item.values().dateInscription);
  if(dateFrom) {
    return date >= dateFrom;
  }
  return true;
}

function filterEquipe(item, equipe) {
  return equipe == "" ? true : item.values().equipe == equipe;
}

function filterObligatoire(item, obligatoire) {
  if(obligatoire == "") {
    return true;
  }

  return item.values().obligatoire == obligatoire;
}

function filterCourriel(item, courriel) {
  if(courriel == "") {
    return true;
  }

  return item.values().courriel == courriel;
}

function updateFiltering(f) {
  lecturesList.filter(function(i) {
    return filterTitre(i, f.titre) && filterDateInscriptionFrom(i, f.dateFrom) && filterDateInscriptionTo(i, f.dateTo) && filterEquipe(i, f.equipe) && filterObligatoire(i, f.obligatoire) && filterCourriel(i, f.courriel);
  });
}

$(function() {
  var options = {
    valueNames: columns,
    item: itemTemplate,
    page: 10,
    pagination: true,
  };
 
  var filters = {
    titre : "",
    dateFrom: null,
    dateTo: null,
    equipe: "",
    obligatoire: "",
    courriel: "",
  }; 

  // Init table
  lecturesList = new List('lectures', options, data);
  
  // Init Equipe options
  var equipeSelect = $("#filterEquipe");
  arrayUniqueValuesOnly(equipes).forEach(function(item, index) {
    var newOption = new Option(item, item);
    equipeSelect.append($(newOption).html(item));
  });

  // Init Courriel options
  var courrielSelect = $("#filterCourriel");
  arrayUniqueValuesOnly(courriels).forEach(function(item, index) {
    var newOption = new Option(item, item);
    courrielSelect.append($(newOption).html(item));
  });

  // Events
  $('#filterTitre').on('keyup', function() {
    var searchString = $(this).val();
    filters.titre = searchString;
    updateFiltering(filters);
  });

  $('#filterDateInscriptionFrom').change(function() {
    var date = stringToDate($(this).val());
    filters.dateFrom = date;
    updateFiltering(filters);
  });

  $('#filterDateInscriptionTo').change(function() {
    var date = stringToDate($(this).val());
    filters.dateTo = date;
    updateFiltering(filters);
  });

  $('#filterEquipe').change(function() {
    var equipe = $(this).children("option:selected").val();
    filters.equipe = equipe;
    updateFiltering(filters);
  });

  $('#filterObligatoire').change(function() {
    var obligatoire = $(this).children("option:selected").val();
    filters.obligatoire = obligatoire;
    updateFiltering(filters);
  });

  $('#filterCourriel').change(function() {
    var courriel = $(this).children("option:selected").val();
    filters.courriel = courriel;
    updateFiltering(filters);
  });
});

