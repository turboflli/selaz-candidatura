<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:include page="propulsoes.jsp" />

    <div  class= "field is-hidden">
        <label class="label">Família</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="familia" id="familia_select" required="required" onchange="base.handleOutros(this)">
           <option selected disabled hidden value="">Escolha...</option>
            <option value="Boeing">Boeing</option>
            <option value="Airbus">Airbus</option>
            <option value="Embraer">Embraer</option>
            <option value="Bombardier">Bombardier</option>
            <option value="Outros">Outros</option>
          </select>
        </span>
        <span class="icon is-small is-left">
          <i class="fas fa-crosshairs"></i>
        </span>
        </p>
    </div>

<input type="hidden" value="hide/propulsao_select/periodo_select" />