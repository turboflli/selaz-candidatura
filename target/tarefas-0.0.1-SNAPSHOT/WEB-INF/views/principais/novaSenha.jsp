<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html" charset=UTF-8>
<spring:url value="/modelista.js" var="modelistaJs" />
<spring:url value="/base.css" var="baseCss" />
<spring:url value="/required.css" var="requiredCss" />
<spring:url value="/base.js" var="baseJs" />
<spring:url value="/all.min.js" var="fontawesome" />
<spring:url value="/bulma.min.css" var="bulmaCss" />



<head>
    <title>Cadastro</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <link href="${bulmaCss}" rel="stylesheet" />
    <script src="${fontawesome}"></script>
    <link href="${baseCss}" rel="stylesheet" />
    <link href="${requiredCss}" rel="stylesheet" />
    <script src="${modelistaJs}"></script>
    <script src="${baseJs}"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
</head>

<body>
        <div class="container">

            <input type="hidden" value="${chave}" id="chave_input" />
            <input type="hidden" value="${iv}" id="iv_input" />
            <div  class= "field">
                <label class="label">Senha Atual</label>
                <div class="control has-icons-left">
                    <input class="input is-rounded" name="senhaAtual" type="password" placeholder="***" required="required">
                    <span class="icon is-small is-left">
                      <i class="fas fa-lock"></i>
                    </span>
                </div>
            </div>
            <div  class= "field">
                <label class="label">Nova Senha</label>
                <div class="control has-icons-left">
                    <input class="input is-rounded" name="senha" type="password" placeholder="***"
                     id="senha_input" required="required" onchange="modelista.validarSenha(this, 'senha_input2')">
                    <span class="icon is-small is-left">
                      <i class="fas fa-lock"></i>
                    </span>
                </div>
            </div>
            <div  class= "field">
                <label class="label">Confirme a nova Senha</label>
                <div class="control has-icons-left">
                    <input class="input is-rounded" type="password" placeholder="***"
                    id="senha_input2" required="required"  onchange="modelista.validarSenha(this, 'senha_input')">
                    <span class="icon is-small is-left">
                      <i class="fas fa-lock"></i>
                    </span>
                </div>
            </div>

            <div  class= "field is-grouped">
                <div class="control has-icons-left">
                    <input type="submit" value="Enviar" class="input is-success" onclick="modelista.alterarSenha()" />
                        <span class="icon is-small is-left">
                          <i class="fas fa-check-square"></i>
                        </span>
                </div>
            </div>


        </div>
</body>
</html>