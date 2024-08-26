    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>


<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.inputmask/5.0.8/jquery.inputmask.min.js"></script>

    <div  class= "field">
        <label class="label">Nome</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="nomeModelista" type="text" placeholder="Nome" required="required">
            <span class="icon is-small is-left">
              <i class="fas fa-signature"></i>
            </span>
        </div>
    </div>
    <div  class= "field">
        <label class="label">E-mail</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="email" type="text" placeholder="nome@mail.com" required="required">
            <span class="icon is-small is-left">
              <i class="fas fa-envelope"></i>
            </span>
        </div>
    </div>
    <div  class= "field">
        <label class="label">Telefone</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="telefone" type="text" placeholder="(00)00000-0000"
            required="required" size="15" id="tel">
            <span class="icon is-small is-left">
              <i class="fas fa-mobile"></i>
            </span>
        </div>
    </div>

    <script>
    $('#tel').inputmask("(99) 99999-9999");
    </script>