<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html" charset=UTF-8>
<spring:url value="/base.js" var="baseJs" />
<spring:url value="/all.min.js" var="fontawesome" />
<spring:url value="/bulma.min.css" var="bulmaCss" />
<spring:url value="/base.css" var="baseCss" />


<head>
    <title>Bem-vindo</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <link href="${bulmaCss}" rel="stylesheet" />
    <script src="${fontawesome}"></script>
    <script src="${baseJs}"></script>
    <link href="${baseCss}" rel="stylesheet" />
</head>

<style>

.conteudo {
    margin-top: 3%;
}

</style>

<body>
    <div class="conteudo">
    <c:if test = "${!hasUser}">
        <jsp:include page="main/login.jsp" />
    </c:if>
    <c:if test = "${hasUser}">
        <c:if test = "${user.admin}">
            <jsp:include page="main/menuAdmin.jsp" />
        </c:if>
        <c:if test = "${!user.admin}">
            <jsp:include page="main/menuUser.jsp" />
        </c:if>

    </c:if>
    </div>

</body>
</html>