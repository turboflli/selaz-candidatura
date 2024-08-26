<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="propulsoes.jsp" />

<div  class= "field is-hidden">
        <label class="label">Período</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="periodo" id="periodo_select" required="required">
          </select>
        </span>
        <span class="icon is-small is-left">
          <i class="fas fa-calendar"></i>
        </span>
        </p>
    </div>

    <div  class= "field">
        <label class="label">Nacionalidade</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="aerea" id="aerea_select" required="required" onchange="base.handleOutros(this)">
          </select>
        </span>
        <span class="icon is-small is-left">
          <i class="fas fa-flag"></i>
        </span>
        </p>
    </div>

    <div  class= "field">
        <label class="label">Arma</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="operacional" id="operacional_select" required="required">
          </select>
        </span>
        <span class="icon is-small is-left">
          <i class="fas fa-crosshairs"></i>
        </span>
        </p>
    </div>

<input type="hidden" value="change/propulsao_select/periodo_select" />
    <input type="hidden" value="add/aerea/aerea_select" />
    <input type="hidden" value="add/operacional/operacional_select" />