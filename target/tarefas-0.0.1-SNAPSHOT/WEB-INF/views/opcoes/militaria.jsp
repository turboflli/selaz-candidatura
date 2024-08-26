<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


    <jsp:include page="tipo.jsp" />

    <div  class= "field">
        <label class="label">Propulsão</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="propulsao" id="propulsao_select" required="required">
          </select>
        </span>
        <span class="icon is-small is-left">
          <i class="fas fa-cogs"></i>
        </span>
        </p>
    </div>

    <div  class= "field">
        <label class="label">Nacionalidade Operacional</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="nacionalidade" id="nacionalidade_select" required="required" onchange="base.handleOutros(this)">
          </select>
        </span>
        <span class="icon is-small is-left">
          <i class="fas fa-flag"></i>
        </span>
        </p>
    </div>

    <input type="hidden" value="add/propulsao/propulsao_select" />
    <input type="hidden" value="add/nacionalidade/nacionalidade_select" />
