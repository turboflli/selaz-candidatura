    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <c:set var="melhorias" value="${param.melhorias}"/>
    <td>
    <c:if test = "${kit.melhorias.length() >= 50}">
        ${kit.melhorias.substring(0,50)} ...
    </c:if>
    <c:if test = "${kitmelhorias.length() < 50}">
        ${kit.melhorias}
    </c:if>
    </td>