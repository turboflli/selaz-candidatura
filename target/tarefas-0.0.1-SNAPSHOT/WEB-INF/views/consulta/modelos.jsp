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

                <th><abbr title="Gategoria que modelo se enquadra">Categoria</abbr></th>

                <th><abbr title="Fabricante do modelo">Fabricante</abbr></th>
                <th><abbr title="Se o kit possui base">Base</abbr></th>
                <th><abbr title="Se o kit possui Temática">Temática</abbr></th>

              <th>Melhorias</th>
              <c:if test="${!param.telaDoModelista}">
                 <th>Modelista</th>
              </c:if>
              <th>Informações</th>
              <c:if test="${param.telaDoModelista}">
                 <th>Deletar</th>
              </c:if>
          </tr>
      </thead>

      <tbody>
          <c:forEach items="${kits}" var="kit">
          <tr>
            <td>${kit.nome}</td>
    <td>${kit.escala}</td>
    <td>${kit.nivel.descricao}</td>
    <td>${kit.nomeCategoria}</td>

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
            <td>
            <c:if test = "${kit.melhorias.length() >= 50}">
                ${kit.melhorias.substring(0,50)} ...
            </c:if>
            <c:if test = "${kit.melhorias.length() < 50}">
                ${kit.melhorias}
            </c:if>
            </td>
            <c:if test="${!param.telaDoModelista}">
                <td>
                    ${kit.nomeModelista}
                </td>
            </c:if>
            <td>
                <div class="tooltip"><i class="fas fa-info-circle"></i>
                  <span class="tooltiptext">${kit.extras}</span>
                </div>
            </td>

            <c:if test="${param.telaDoModelista}">
                <td>
                    <button onclick="base.deletar(${kit.id})" class="button has-text-danger">
                        <i class="fas fa-trash-alt is-danger"></i>
                    </button>
                </td>
            </c:if>

          </tr>
          </c:forEach>

      </tbody>
</table>
