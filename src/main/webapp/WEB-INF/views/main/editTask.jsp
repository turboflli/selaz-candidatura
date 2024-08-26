<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
                <label class="label">Título</label>
                <div class="control has-icons-left">
                    <input class="input is-rounded" name="title" type="text" placeholder="task for project 1" required="required" value="${task.title}">
                    <span class="icon is-small is-left">
                      <i class="fas fa-signature"></i>
                    </span>
                </div>
            </div>
            <div  class= "field">
                <label class="label">Descrição</label>
                <div class="control">
                    <textarea class="textarea" name="description" placeholder="Detalhe a tarefa..." >${task.description}</textarea>
                </div>
            </div>

            <div  class= "field">
                <label class="label">Estimativa</label>
                <div class="control has-icons-left">
                    <input class="input is-rounded dataInput" name="dueDate" type="date" required="required" value="${dateFormated}">
                    <span class="icon is-small is-left">
                      <i class="fas fa-calendar-day"></i>
                    </span>
                </div>
            </div>

            <div  class= "field">
                <label class="label">Status</label>
                <p class="control has-icons-left">
                <span class="select is-rounded">
                  <select name="status" required="required" >
                      <option value="Pendente"  ${task.statusDescription == 'Pendente' ? 'selected' : ''} >Pendente</option>
                      <option value="emAndamento" ${task.statusDescription == 'emAndamento' ? 'selected' : ''} >Em Andamento</option>
                      <option value="Concluida" ${task.statusDescription == 'Concluida' ? 'selected' : ''} >Concluída</option>
                  </select>
                </span>
                    <span class="icon is-small is-left">
                  <i class="fas fa-stopwatch"></i>
                </span>
                </p>
            </div>

            <div  class= "field">
                <label class="label">Usuário</label>
                <p class="control has-icons-left">
                <span class="select is-rounded">
                  <select name="userId" >
                       <option selected disabled hidden value="">Escolha usuário responsável</option>
                     <c:forEach items="${avaliableusers}" var="user">
                     <option value="${user.id}" ${task.user.id == user.id ? 'selected' : ''} >${user.username}</option>
                     </c:forEach>
                  </select>
                </span>
                    <span class="icon is-small is-left">
                  <i class="fas fa-user"></i>
                </span>
                </p>
            </div>

            <div  class= "field is-grouped">
                <div class="control has-icons-left">
                    <input type="submit" value="Enviar" class="input is-success" onclick="base.editTask(${task.id})" />
                        <span class="icon is-small is-left">
                          <i class="fas fa-check-square"></i>
                        </span>
                </div>
            </div>


        </div>
</body>
</html>
