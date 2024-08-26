<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html" charset=UTF-8>
<script type="text/javascript" src="https://unpkg.com/xlsx@0.15.1/dist/xlsx.full.min.js"></script>


<head>
    <title>Menu Usuário</title>
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
                      <i class="fas fa-user"></i>Bem-vindo ${user.username}
                    </p>
                    <p class="subtitle">
                      Consulte e Tarefas e administre as suas
                    </p>
                  </div>
                  <div class="hero-body">
                      <br/>
                      <strong><a href="./newTask" target="_blank">Cadastrar nova Tarefa</a></strong>
                  </div>
              </section>
            </div>

        </div>
        <jsp:include page="../view/viewTasksGroups.jsp" />
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