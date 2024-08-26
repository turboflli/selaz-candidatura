    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


    <div  class= "field">
        <label class="label">Título</label>
        <div class="control has-icons-left">
            <input class="input is-rounded" name="title" type="text" placeholder="task for project 1" required="required">
            <span class="icon is-small is-left">
              <i class="fas fa-signature"></i>
            </span>
        </div>
    </div>
    <div  class= "field">
        <label class="label">Descrição</label>
        <div class="control">
            <textarea class="textarea" name="description" placeholder="Detalhe a tarefa..."></textarea>
        </div>
    </div>

    <div  class= "field">
        <label class="label">Estimativa</label>
        <div class="control has-icons-left">
            <input class="input is-rounded dataInput" name="dueDate" type="date" required="required">
            <span class="icon is-small is-left">
              <i class="fas fa-calendar-day"></i>
            </span>
        </div>
    </div>

    <div  class= "field">
        <label class="label">Status</label>
        <p class="control has-icons-left">
        <span class="select is-rounded">
          <select name="status" required="required">
               <option selected disabled hidden value="">Escolha o status da tarefa</option>
              <option value="Pendente">Pendente</option>
              <option value="emAndamento">Em Andamento</option>
              <option value="Concluida">Concluída</option>
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
             <option value="${user.id}">${user.username}</option>
             </c:forEach>
          </select>
        </span>
            <span class="icon is-small is-left">
          <i class="fas fa-user"></i>
        </span>
        </p>
    </div>
