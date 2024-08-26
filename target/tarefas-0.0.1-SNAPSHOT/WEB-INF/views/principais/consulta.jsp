<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html" charset=UTF-8>
<script type="text/javascript" src="https://unpkg.com/xlsx@0.15.1/dist/xlsx.full.min.js"></script>


<head>
    <title>Cadastro</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>

    <div class="tile is-ancestor is-vertical">
        <div class="tile">
        <div class="logout">
            <div class="control">
                <input type="submit" value="Sair" class="input is-link is-light" onclick="base.logout()" />
            </div>
        </div>
        </div>
        <div class="tile is-ancestor is-vertical">
            <div class="tile is-horizontal is-18">
              <section class="hero is-primary">
                  <div class="hero-body">
                    <p class="title">
                      <i class="fas fa-user"></i>Bem-vindo ${modelista.nome}
                    </p>
                    <p class="subtitle">
                      Consulte os modelos cadastrados, e exporte relatórios.
                    </p>
                  </div>
              </section>
            </div>
            <div class="tile is-horizontal is-18">
              <section class="hero is-primary">
                  <div class="hero-body">
                    <jsp:include page="../consulta/resumo.jsp" />
                  </div>
              </section>
            </div>
        </div>
        <div class="tile is-vertical" >

            <section class="section">

                <div class="container">
                    <div class="card is-fullwidth">
                        <header class="card-header">
                            <p class="card-header-title card-toggle"><i class="fas fa-search"></i> Campos de Busca</p>
                            <a class="card-header-icon card-toggle">
                                <i class="fa fa-angle-down"></i>
                            </a>
                        </header>
                        <div class="card-content">
                            <div class="content">
                                <jsp:include page="modeloForm.jsp" >
                                     <jsp:param name="permiteTodasCategorias" value="true" />
                                </jsp:include>

                                <div  class= "field is-grouped">
                                    <div class="control has-icons-left">
                                        <input type="submit" value="Consultar" class="input is-success" onclick="base.buscar()" />
                                            <span class="icon is-small is-left">
                                              <i class="fas fa-search"></i>
                                            </span>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>

            <br/>

                <div class="container">
                    <div class="card is-fullwidth">
                        <header class="card-header">
                            <p class="card-header-title card-toggle"><i class="fas fa-eye"></i>  Visualizar Resultados de busca</p>
                            <a class="card-header-icon card-toggle">
                                <i class="fa fa-angle-down"></i>
                            </a>
                        </header>
                        <div class="card-content">

                            <div class="table-container" id="lista_modelos">
                            </div>
                            <div class="control has-icons-left">
                                <input type="submit" value="Exportar" class="input is-success" onclick="base.ExportToExcel('xlsx')" />
                                    <span class="icon is-small is-left">
                                      <i class="fas fa-file-download"></i>
                                    </span>
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