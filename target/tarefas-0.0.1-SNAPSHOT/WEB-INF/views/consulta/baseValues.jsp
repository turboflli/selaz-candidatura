    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="kit" value="${requestScope.attribute.kit}"/>
<c:set var="exibirCategoria" value="${requestScope.attribute.exibirCategoria}"/>

    <td>${kit.nome}</td>
    <td>${kit.escala}</td>
    <td>${kit.nivel.descricao}</td>
    <c:if test="${telaDoModelista}">
        <td>${kit.nomeCategoria}</td>
    </c:if>
    <td>${kit.fabricante}</td>
    <td>
        <c:if test = "${kit.temBase}">
            <span class="icon has-text-success">
                <i class="fas fa-check"></i>
            </span>
        </c:if>
        <c:if test = "${!kit.temBase}">
            <span class="icon has-text-danger">
                <i class="fas fa-times"></i>
            </span>
        </c:if>
    </td>
    <td>
        <c:if test = "${kit.tematica}">
            <span class="icon has-text-success">
                <i class="fas fa-check"></i>
            </span>
        </c:if>
        <c:if test = "${!kit.tematica}">
            <span class="icon has-text-danger">
                <i class="fas fa-times"></i>
            </span>
        </c:if>
    </td>