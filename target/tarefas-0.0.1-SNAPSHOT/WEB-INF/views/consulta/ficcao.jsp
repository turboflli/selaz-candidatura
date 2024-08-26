<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<table class="table" id="tbl_exporttable_to_xls">
      <thead>
          <tr>
              <th><abbr title="Nome do trabalho">Nome</abbr></th>
  <th>Escala</th>
  <th><abbr title="Nível que trabalho foi feito">Nível</abbr></th>
  <th><abbr title="Fabricante do modelo">Fabricante</abbr></th>
  <th><abbr title="Se o kit possui base">Base</abbr></th>
  <th><abbr title="Se o kit possui Temática">Temática</abbr></th>
              <th><abbr title="O tipo do modelo">Tipo</abbr></th>
              <th><abbr title="A Especificação do modelo">Especificação</abbr></th>
              <th>Melhorias</th>
              <th>Modelista</th>
          </tr>
      </thead>

      <tbody>
          <c:forEach items="${kits}" var="kit">
          <tr>
            <td>${kit.nome}</td>
    <td>${kit.escala}</td>
    <td>${kit.nivel.descricao}</td>
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

            <td>${kit.tipo}</td>
            <td>${kit.especificacao}</td>

            <jsp:include page="valueMelhorias.jsp" />
            <td>
                ${kit.nomeModelista}
            </td>

          </tr>
          </c:forEach>

      </tbody>
</table>
