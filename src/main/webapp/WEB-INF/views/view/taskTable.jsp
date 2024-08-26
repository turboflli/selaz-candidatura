<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<table class="table">
      <thead>
          <tr>
              <th><abbr title="Titulo da Tarefa">Título</abbr></th>
                <th>Escala</th>
                <th><abbr title="Descrição da Tarefa">Descrição</abbr></th>

                <th><abbr title="Data de crição">Criada em</abbr></th>

                <th><abbr title="Data esperada para conclusão">estimativa</abbr></th>
                <th><abbr title="Username do usuário associado">User</abbr></th>
                <th><abbr title="Status em que a tarefa se encontra">Status</abbr></th>

              <th>Ações</th>
          </tr>
      </thead>

      <tbody>
          <c:forEach items="${param.tasks}" var="task">
          <tr>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <c:if test = "${task.description.length() >= 50}">
                ${task.description.substring(0,50)} ...
            </c:if>
            <c:if test = "${task.description.length() < 50}">
                ${task.description}
            </c:if>
            <td>${task.createdAtFormated}</td>
            <td>${task.dueDateFormated}</td>
            <td>${task.userUsername}</td>
            <td>
                <c:if test = "${task.statusDescription=='Pendente'}">
                    <span class="icon has-text-success">
                        <i class="fas fa-exclamation-triangle"></i>
                    </span>
                </c:if>
                <c:if test = "${task.statusDescription=='emAndamento'}">
                    <span class="icon has-text-success">
                        <i class="fas fa-play-circle"></i>
                    </span>
                </c:if>
                <c:if test = "${task.statusDescription=='Concluida'}">
                    <span class="icon has-text-success">
                        <i class="fas fa-check"></i>
                    </span>
                </c:if>
            </td>

            <td>
                <button onclick="base.alterar(${task.id})" class="button has-text-danger">
                    <i class="fas fa-pen is-danger"></i>
                </button>
            </td>
            <td>
                <button onclick="base.deletar(${task.id})" class="button has-text-danger">
                    <i class="fas fa-trash-alt is-danger"></i>
                </button>
            </td>

          </tr>
          </c:forEach>

      </tbody>
</table>
