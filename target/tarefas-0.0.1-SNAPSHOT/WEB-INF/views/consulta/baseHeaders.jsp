  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

  <th><abbr title="Nome do trabalho">Nome</abbr></th>
  <th>Escala</th>
  <th><abbr title="Nível que trabalho foi feito">Nível</abbr></th>
  <c:if test="${telaDoModelista}">
    <th><abbr title="Gategoria que modelo se enquadra">Categoria</abbr></th>
  </c:if>
  <th><abbr title="Fabricante do modelo">Fabricante</abbr></th>
  <th><abbr title="Se o kit possui base">Base</abbr></th>
  <th><abbr title="Se o kit possui Temática">Temática</abbr></th>