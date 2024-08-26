    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>

    <meta http-equiv="Content-Type" content="text/html" charset=UTF-8>
<spring:url value="/required.css" var="requiredCss" />

    <div  class= "field">
        <label class="label">Username</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="username" type="text" placeholder="username" required="required">
            <span class="icon is-small is-left">
              <i class="fas fa-signature"></i>
            </span>
        </div>
    </div>
    <div  class= "field">
        <label class="label">Nível</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="nivel" type="text" placeholder="">
            <span class="icon is-small is-left">
              <i class="fas fa-user-tag"></i>
            </span>
        </div>
    </div>

    <div   class= "field is-rounded">
        <label class="label">É administrador?</label>
        <div class="control">
            <label class="radio">
                <input type="radio" name="admin" value="true">
                Sim
            </label>
            <label class="radio">
                <input type="radio" name="admin" value="false" checked>
                Não
            </label>
        </div>
    </div>

     <div  class= "field">
        <label class="label">Senha</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="senha" type="password" placeholder="***"
             id="senha_input" required="required" onchange="base.checkPassword(this, 'senha_input2')">
            <span class="icon is-small is-left">
              <i class="fas fa-lock"></i>
            </span>
        </div>
    </div>
    <div  class= "field">
        <label class="label">Confirme a Senha</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" type="password" placeholder="***"
            id="senha_input2" required="required"  onchange="base.checkPassword(this, 'senha_input')">
            <span class="icon is-small is-left">
              <i class="fas fa-lock"></i>
            </span>
        </div>
    </div>
