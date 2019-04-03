<%-- 
    Document   : geburtstag_edit
    Created on : Mar 25, 2019, 8:50:44 PM
    Author     : belizbalim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Geburtstag bearbeiten
            </c:when>
            <c:otherwise>
                Geburtstag anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/geburtstag_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/home/"/>">Home</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/geburtstage/list/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <label for="geburtstag_title">Titel:</label>
                <div class="side-by-side">
                    <select name="geburtstag_title">
                        <option value="">Kein Titel</option>
                        <option value="Dr.">Dr.</option> 
                        <option value="Frau">Frau</option> 
                        <option value="Herr">Herr</option> 
                    </select> 
                </div> 
                <label for="geburtstag_name">Name:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="geburtstag_name" value="${geburtstag_form.values["geburtstag_name"][0]}" >
                </div>
                
                <label for="geburtstag_surname">Surname:</label>
                <div class="side-by-side">
                    <input type="text" name="geburtstag_surname" value="${geburtstag_form.values["geburtstag_surname"][0]}" >
                </div>
                
                <label for="geburtstag_notiz">Notiz:</label>
                <div class="side-by-side">
                    <input type="text" name="geburtstag_notiz" value="${geburtstag_form.values["geburtstag_notiz"][0]}" >
                </div>

                <label for="geburtstag_date">
                    Datum:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="geburtstag_date" value="${geburtstag_form.values["geburtstag_date"][0]}">
                </div>
              
                <label for="geburtstag_category">Kategorie:</label>
                <div class="side-by-side">
                    <select name="geburtstag_category">
                        <option value="">Keine Kategorie</option>
                            <c:if test="${!empty categories}">
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.id}" ${geburtstag_form.values["geburtstag_category"][0] == category.id.toString() ? 'selected' : ''}>
                                        <c:out value="${category.name}" />
                                    </option>
                                 </c:forEach>
                            </c:if>        
                    </select> 
                </div> 
                <br>
                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Speichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty geburtstag_form.errors}">
                <ul class="errors">
                    <c:forEach items="${geburtstag_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>