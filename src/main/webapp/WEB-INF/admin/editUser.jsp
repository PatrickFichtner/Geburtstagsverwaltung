<%--
    Document   : editUser
    Created on : 03.04.2019, 19:54:33
    Author     : Patrick Fichtner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Benutzerdaten ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
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
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="signup_username">
                        Benutzername:
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="username" value="${edit_form.values["username"][0]}" required="required" autofocus="autofocus" disabled>
                    </div>


                    <label for="firstname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="firstname" value="${edit_form.values["firstname"][0]}">
                    </div>

                    <label for="lastname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="lastname" value="${edit_form.values["lastname"][0]}">
                    </div>

                    <label for="newPassword">
                        Neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="newPassword" value="${signup_form.values["newPassword"][0]}">
                    </div>

                    <label for="newPasswordConfirm">
                        Passwort (wdh.):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="newPasswordConfirm" value="${signup_form.values["newPasswordConfirm"][0]}">
                    </div>
                    <br>
                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <%--<input class="btn btn-primary btn-block" type="submit" value="Speichern"> --%>
                        <button class="icon-pencil" type="submit">
                            Speichern
                        </button>
                    </div>
                </div>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty edit_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${edit_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>
