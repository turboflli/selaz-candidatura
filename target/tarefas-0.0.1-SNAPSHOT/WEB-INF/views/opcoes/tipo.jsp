<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


    <div  class= "field">
        <label class="label">Tipo</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="tipo" id="tipo_select" required="required" onchange="base.handleOutros(this)">
             <option selected disabled hidden value="" > Escolha o tipo que o modelo se enquadra</option>
              <c:forEach items="${listaTipos}" var="tipo" >
                <option value='${tipo.value}'>${tipo.label}</option>
              </c:forEach>
          </select>
        </span>
        <span class="icon is-small is-left">
          <i class="fas fa-info"></i>
        </span>
        </p>
    </div>
