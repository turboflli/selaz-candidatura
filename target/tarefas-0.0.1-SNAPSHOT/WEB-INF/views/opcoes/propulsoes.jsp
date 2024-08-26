   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

   <div  class= "field is-hidden">
        <div class= "is-inline-block">
            <label class="label">Propulsão</label>
            <p class="control has-icons-left">
            <span class="select is-rounded">
              <select name="propulsao" id="propulsao_select" required="required">
                  <option selected disabled hidden value="">Escolha...</option>
                  <option value="Hélice ${utilizacao}">Hélice</option>
                  <option value="Reação ${utilizacao}">Reação</option>
              </select>
            </span>
            <span class="icon is-small is-left">
              <i class="fas fa-cogs"></i>
            </span>
            </p>
        </div>
        <div class= "is-inline-block">
            <label class="label">Motores</label>
            <p class="control has-icons-left">
            <span class="select is-rounded">
              <select name="motores" id="motores_select" required="required">
                <option selected disabled hidden value="">Escolha...</option>
                <option value="Monomotor">Monomotor</option>
                <option value="Multimotor">Multimotor</option>
              </select>
            </span>
            <span class="icon is-small is-left">
              <i class="fas fa-fan"></i>
            </span>
            </p>
        </div>
    </div>