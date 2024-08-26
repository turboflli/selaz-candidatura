<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html" charset=UTF-8>
<spring:url value="/required.css" var="requiredCss" />


<head>
    <title>Cadastro</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${requiredCss}" rel="stylesheet" />
</head>

<body>

    <div class="tile is-ancestor is-vertical">
        <div class="tile">
        <div class="logout">
            <div class="control">
            <span class="icon-text has-text-danger">
                <span class="icon" style="{width: 25px  !important;height: 25px !important;margin-top: 15px !important;}">
                    <a target="_blank" href="./novaSenha">
                        <i class="fas fa-cog" ></i>
                    </a>
                </span>
                <span>
                    <input type="submit" value="Sair" class="input is-link is-light" onclick="base.logout()" />
                </span>
            </span>
            </div>
        </div>
        </div>
        <div class="tile is-ancestor is-vertical">
            <div class="tile is-horizontal is-18">
              <section class="hero is-primary">
                  <div class="hero-body">
                    <p class="title">
                      <i class="fas fa-user"></i> ${modelista.nome}
                    </p>
                    <p class="subtitle">
                      Adicione ou consulte seus modelos
                    </p>
                  </div>
              </section>
            </div>
        </div>
        <div class="tile is-vertical" >

            <section class="section">

                <p class="title">Prazo encerrado</p>
                Prezado(a) modelista, o prazo para cadastro dos modelos está encerrado! Mas você ainda pode continuar consultando seus modelos já cadastrados
                <%--
                <div class="container">
                    <div class="card is-fullwidth">
                        <header class="card-header">
                            <p class="card-header-title card-toggle"><i class="fas fa-plus"></i> Cadastrar Novo kit</p>
                            <a class="card-header-icon card-toggle">
                                <i class="fa fa-angle-down"></i>
                            </a>
                        </header>
                        <div class="card-content is-hidden">
                            <div class="content">
                                <jsp:include page="modeloForm.jsp" />

                                <div  class= "field is-grouped">
                                    <div class="control has-icons-left">
                                        <input type="submit" value="Enviar" class="input is-success" onclick="base.save()" />
                                            <span class="icon is-small is-left">
                                              <i class="fas fa-check-square"></i>
                                            </span>
                                    </div>
                                    <div class="control ">
                                        <button onclick="base.limpar()" class="button is-link is-light">Limpar</button>
                                    </div>
                                </div>

                        </div>
                    </div>
                </div>
                --%>
            <br/>

                <div class="container">
                    <div class="card is-fullwidth">
                        <header class="card-header">
                            <p class="card-header-title card-toggle"><i class="fas fa-eye"></i>  Visualizar Kits cadastrados</p>
                            <a class="card-header-icon card-toggle">
                                <i class="fa fa-angle-down"></i>
                            </a>
                        </header>
                        <div class="card-content">
                            <div class="content" id="lista_modelos">
                                <jsp:include page="../consulta/modelos.jsp" >
                                    <jsp:param name="telaDoModelista" value="true" />
                                </jsp:include>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

        </div>
    </div>

</body>

<script>
document.addEventListener('DOMContentLoaded', function() {
	let cardToggles = document.getElementsByClassName('card-toggle');
	for (let i = 0; i < cardToggles.length; i++) {
		cardToggles[i].addEventListener('click', e => {
			e.currentTarget.parentElement.parentElement.childNodes[3].classList.toggle('is-hidden');
		});
	}
});
</script>

</html>