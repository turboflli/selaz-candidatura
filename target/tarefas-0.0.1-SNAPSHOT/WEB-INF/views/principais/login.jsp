<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>

<div class="container">
    <input type="hidden" value="${chave}" id="chave_input" />
    <input type="hidden" value="${iv}" id="iv_input" />
    <div  class= "field">
        <label class="label">Nome ou email</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="login" type="text" placeholder="login"
                id="login_input" onkeydown="base.sendLogin()">
            <span class="icon is-small is-left">
              <i class="fas fa-signature"></i>
            </span>
        </div>
    </div>
    <div  class= "field">
        <label class="label">Senha</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="senha" type="password" placeholder="***"
                id="senha_input"  onkeydown="base.sendLogin()">
            <span class="icon is-small is-left">
              <i class="fas fa-lock"></i>
            </span>
        </div>
    </div>
    <div class="control ">
        <button onclick="base.login()" class="button is-link is-light">Login</button>
    </div>
    <strong><a href="./novoUsuario">Não tem conta? cadastre-se</a></strong>
</div>
