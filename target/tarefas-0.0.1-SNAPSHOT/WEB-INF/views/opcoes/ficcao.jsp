<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


    <jsp:include page="tipo.jsp" />

    <div  class= "field is-hidden">
            <label class="label">Especificação</label>
            <p class="control has-icons-left">
            <span class="select is-rounded">
              <select name="especificacao" id="especificacao_select" required="required">
              </select>
            </span>
            <span class="icon is-small is-left">
              <i class="fas fa-certificate"></i>
            </span>
            </p>
        </div>

    <input type="hidden" value="change/tipo_select/especificacao_select" />
    <input type="hidden" value="hide/tipo_select" />
