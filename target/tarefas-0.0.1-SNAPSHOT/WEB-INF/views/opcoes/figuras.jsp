<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


    <jsp:include page="tipo.jsp" />

    <div  class= "field">
            <label class="label">Classificação</label>
            <p class="control has-icons-left">
            <span class="select is-rounded">
              <select name="classificacao" id="classificacao_select" required="required" onchange="base.handleOutros(this)">
              </select>
            </span>
            <span class="icon is-small is-left">
              <i class="fas fa-award"></i>
            </span>
            </p>
        </div>

    <input type="hidden" value="change/tipo_select/classificacao_select" />
