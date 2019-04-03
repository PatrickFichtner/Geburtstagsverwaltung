<%-- 
    Copyright © 2019 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Dashboard
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/dashboard.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/geburtstage/list/"/>">Liste</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/geburtstage/geburtstag/new/"/>">Geburtstag anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/geburtstage/categories/"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.jtodo.common.web.WebUtils"/>
            <c:forEach items="${sections}" var="section">
                <h2>
                    <c:out value="${section.label}"/>
                </h2>
                <c:choose>
                    <c:when test="${empty section.tiles}">
                        <p>Heute hat keiner Geburtstag</p> 
                    </c:when>   
                    <c:otherwise>
                        <c:forEach items="${section.tiles}" var="tile">
                            <div class="tile">
                                <a href="<c:url value="${tile.href}"/>">
                                <div class="content">
                                        <div class="label">
                                            <c:out value="${tile.name}"/>
                                        </div>
                                        <div class="category">
                                            <c:out value="${tile.category}"/>
                                        </div>
                                        <div class="icon icon-${tile.icon}"></div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>        
            </c:forEach>
    </jsp:attribute>
</template:base>