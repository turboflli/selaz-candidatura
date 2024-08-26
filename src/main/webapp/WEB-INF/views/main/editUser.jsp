<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html" charset=UTF-8>
<spring:url value="/base.css" var="baseCss" />
<spring:url value="/required.css" var="requiredCss" />
<spring:url value="/base.js" var="baseJs" />
<spring:url value="/all.min.js" var="fontawesome" />
<spring:url value="/bulma.min.css" var="bulmaCss" />



<head>
    <title>Edição</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <link href="${bulmaCss}" rel="stylesheet" />
    <script src="${fontawesome}"></script>
    <link href="${baseCss}" rel="stylesheet" />
    <link href="${requiredCss}" rel="stylesheet" />
    <script src="${baseJs}"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
</head>

<body>

        <div class="container">

            <div  class= "field">
                <label class="label">Username</label>
                <div class="control has-icons-left">
                    <input class="input is-rounded" name="username" type="text" placeholder="username" required="required" value="${user.username}">
                    <span class="icon is-small is-left">
                      <i class="fas fa-signature"></i>
                    </span>
                </div>
            </div>
            <div  class= "field">
                <label class="label">Nível</label>
                <div class="control has-icons-left">
                    <input class="input is-rounded" name="nivel" type="text" placeholder="" value="${user.nivel}">
                    <span class="icon is-small is-left">
                      <i class="fas fa-user-tag"></i>
                    </span>
                </div>
            </div>

            <div   class= "field is-rounded">
                <label class="label">É administrador?</label>
                <div class="control">
                    <label class="radio">
                        <input type="radio" name="admin" value="true" ${user.admin ? 'checked' : ''} >
                        Sim
                    </label>
                    <label class="radio">
                        <input type="radio" name="admin" value="false" ${!user.admin ? 'checked' : ''} >
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

            <input type="hidden" value="${chave}" id="chave_input" />
            <input type="hidden" value="${iv}" id="iv_input" />

            <div  class= "field is-grouped">
                <div class="control has-icons-left">
                    <input type="submit" value="Enviar" class="input is-success" onclick="base.editUser(${user.id})" />
                        <span class="icon is-small is-left">
                          <i class="fas fa-check-square"></i>
                        </span>
                </div>
            </div>


        </div>
</body>
</html>