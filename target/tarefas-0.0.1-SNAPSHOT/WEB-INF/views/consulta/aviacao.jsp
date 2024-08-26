
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
    prefix="fn" %>

<table class="table" id="tbl_exporttable_to_xls">
      <thead>
          <tr>
              <th><abbr title="Nome do trabalho">Nome</abbr></th>
  <th>Escala</th>
  <th><abbr title="Nível que trabalho foi feito">Nível</abbr></th>
  <th><abbr title="Fabricante do modelo">Fabricante</abbr></th>
  <th><abbr title="Se o kit possui base">Base</abbr></th>
  <th><abbr title="Se o kit possui Temática">Temática</abbr></th>
              <th><abbr title="A Utilização do modelo">Utilização</abbr></th>
              <th><abbr title="O Tipo do modelo">Tipo</abbr></th>
              <th><abbr title="A Propulsão e quantidade de motores do modelo">Propulsão</abbr></th>
              <th><abbr title="O Período que o modelo faz parte">Período</abbr></th>
              <th><abbr title="A Nacionalidade do modelo">Nacionalidade</abbr></th>
              <th><abbr title="A Arma que o modelo faz parte">Arma</abbr></th>
              <th><abbr title="A Família que o modelo faz parte">Família</abbr></th>
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

            <td>${fn:replace(kit.utilizacao,'avi','')}</td>
            <td>${fn:replace(fn:replace(kit.tipo,'Civil',''),'Militar','')}</td>
            <td>${fn:replace(fn:replace(kit.propulsao,'Civil',''),'Militar','')}
            <c:if test = "${kit.propulsao != ''}">
                (${kit.motores})
            </c:if>

            </td>
            <td>${kit.periodo}</td>
            <td>${kit.aerea}</td>
            <td>${kit.operacional}</td>
            <td>${kit.familia}</td>

            <jsp:include page="valueMelhorias.jsp" />
            <td>
                ${kit.nomeModelista}
            </td>

          </tr>
          </c:forEach>

      </tbody>
</table>
