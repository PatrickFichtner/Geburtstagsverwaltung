
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Geburtstage
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/geburtstag_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/home/"/>">Home</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/geburtstage/geburtstag/new/"/>">Geburtstag anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/geburtstage/categories/"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Suchtext"/>

            <select name="search_category">
                <option value="">Alle Kategorien</option>

                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}" ${param.search_category == category.id ? 'selected' : ''}>
                        <c:out value="${category.name}" />
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Geburtstage --%>
        <c:choose>
            <c:when test="${empty geburtstage}">
                <p>Es wurden keine Geburtstage gefunden.</p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="administration.web.WebUtils"/>

                <table>
                    <thead>
                        <tr>
                            <th>Kategorie</th>
                            <th>Titel</th>
                            <th>Name</th>
                            <th>Vorname</th>
                            <th>Datum</th>
                            <th>Notiz</th>
                        </tr>
                    </thead>
                    <c:forEach items="${geburtstage}" var="geburtstag">
                        <tr>
                            <td>
                                <c:out value="${geburtstag.category.name}"/>
                            </td>
                            <td>
                                <c:out value="${geburtstag.title}"/>
                            </td>
                            <td>
                                <c:out value="${geburtstag.name}"/>
                            </td>
                            <td>
                                <a href="<c:url value="/app/geburtstage/geburtstag/${geburtstag.id}/"/>">
                                    <c:out value="${geburtstag.surname}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(geburtstag.date)}"/>
                            </td>
                            <td>
                                <c:out value="${geburtstag.notiz}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>