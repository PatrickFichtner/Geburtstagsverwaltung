<%-- 
    Document   : home
    Created on : Mar 25, 2019, 9:13:50 PM
    Author     : belizbalim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Home
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/home.css"/>" />
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
        
    </jsp:attribute>
</template:base>
