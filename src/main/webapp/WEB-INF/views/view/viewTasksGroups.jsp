<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html" charset=UTF-8>
<div class="tile is-vertical" >

    <section class="section">

        <div class="container">
            <div class="card is-fullwidth">
                <header class="card-header">
                    <p class="card-header-title card-toggle"><i class="fas fa-clipboard"></i>  Tarefas Pendentes</p>
                    <a class="card-header-icon card-toggle">
                        <i class="fa fa-angle-down"></i>
                    </a>
                </header>
                <div class="card-content">
                    <div class="content">
                        <table class="table">
                                <jsp:include page="../view/tableTaskHead.jsp" />

                              <tbody>
                                  <c:forEach items="${opentasks}" var="task">
                                  <tr>
                                    <td>${task.title}</td>
                                    <td>
                                    <c:if test = "${task.description.length() >= 50}">
                                        ${task.description.substring(0,50)} ...
                                    </c:if>
                                    <c:if test = "${task.description.length() < 50}">
                                        ${task.description}
                                    </c:if>
                                    </td>
                                    <td>${task.createdAtFormated}</td>
                                    <td>${task.dueDateFormated}</td>
                                    <td>${task.userUsername}</td>
                                    <td>
                                        <c:if test = "${task.statusDescription=='Pendente'}">
                                            <span class="icon has-text-warning">
                                                <i class="fas fa-exclamation-triangle"></i>
                                            </span>
                                        </c:if>
                                        <c:if test = "${task.statusDescription=='emAndamento'}">
                                            <span class="icon has-text">
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
                                        <strong><a href="./editTask/${task.id}" target="_blank">
                                            <i class="fas fa-pen "></i>
                                        </a></strong>
                                        <strong><a class="deleteIcon" onclick="base.deleteTask(${task.id})">
                                            <i class="fas fa-trash-alt is-danger"></i>
                                        </a></strong>
                                    </td>

                                  </tr>
                                  </c:forEach>

                              </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    <br/>

        <div class="container">
            <div class="card is-fullwidth">
                <header class="card-header">
                    <p class="card-header-title card-toggle"><i class="fas fa-clipboard"></i>  Tarefas Em Andamento</p>
                    <a class="card-header-icon card-toggle">
                        <i class="fa fa-angle-down"></i>
                    </a>
                </header>
                <div class="card-content">
                    <div class="content">
                        <table class="table">
                                <jsp:include page="../view/tableTaskHead.jsp" />

                              <tbody>
                                  <c:forEach items="${workingtasks}" var="task">
                                  <tr>
                                    <td>${task.title}</td>
                                    <td>
                                    <c:if test = "${task.description.length() >= 50}">
                                        ${task.description.substring(0,50)} ...
                                    </c:if>
                                    <c:if test = "${task.description.length() < 50}">
                                        ${task.description}
                                    </c:if>
                                    </td>
                                    <td>${task.createdAtFormated}</td>
                                    <td>${task.dueDateFormated}</td>
                                    <td>${task.userUsername}</td>
                                    <td>
                                        <c:if test = "${task.statusDescription=='Pendente'}">
                                            <span class="icon has-text-warning">
                                                <i class="fas fa-exclamation-triangle"></i>
                                            </span>
                                        </c:if>
                                        <c:if test = "${task.statusDescription=='emAndamento'}">
                                            <span class="icon has-text">
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
                                        <strong><a href="./editTask/${task.id}" target="_blank">
                                            <i class="fas fa-pen "></i>
                                        </a></strong>
                                        <strong><a class="deleteIcon" onclick="base.deleteTask(${task.id})">
                                            <i class="fas fa-trash-alt is-danger"></i>
                                        </a></strong>
                                    </td>

                                  </tr>
                                  </c:forEach>

                              </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    <br/>

        <div class="container">
            <div class="card is-fullwidth">
                <header class="card-header">
                    <p class="card-header-title card-toggle"><i class="fas fa-clipboard"></i>  Tarefas Concluídas</p>
                    <a class="card-header-icon card-toggle">
                        <i class="fa fa-angle-down"></i>
                    </a>
                </header>
                <div class="card-content">
                    <div class="content">
                        <table class="table">
                                <jsp:include page="../view/tableTaskHead.jsp" />

                              <tbody>
                                  <c:forEach items="${donetasks}" var="task">
                                  <tr>
                                    <td>${task.title}</td>
                                    <td>
                                    <c:if test = "${task.description.length() >= 50}">
                                        ${task.description.substring(0,50)} ...
                                    </c:if>
                                    <c:if test = "${task.description.length() < 50}">
                                        ${task.description}
                                    </c:if>
                                    </td>
                                    <td>${task.createdAtFormated}</td>
                                    <td>${task.dueDateFormated}</td>
                                    <td>${task.userUsername}</td>
                                    <td>
                                        <c:if test = "${task.statusDescription=='Pendente'}">
                                            <span class="icon has-text-warning">
                                                <i class="fas fa-exclamation-triangle"></i>
                                            </span>
                                        </c:if>
                                        <c:if test = "${task.statusDescription=='emAndamento'}">
                                            <span class="icon has-text">
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
                                        <strong><a href="./editTask/${task.id}" target="_blank">
                                            <i class="fas fa-pen "></i>
                                        </a></strong>
                                        <strong><a class="deleteIcon" onclick="base.deleteTask(${task.id})">
                                            <i class="fas fa-trash-alt is-danger"></i>
                                        </a></strong>
                                    </td>

                                  </tr>
                                  </c:forEach>

                              </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

<br/>

        <div class="container">
            <div class="card is-fullwidth">
                <header class="card-header">
                    <p class="card-header-title card-toggle"><i class="fas fa-clipboard"></i>  Minhas tarefas</p>
                    <a class="card-header-icon card-toggle">
                        <i class="fa fa-angle-down"></i>
                    </a>
                </header>
                <div class="card-content">
                    <div class="content">
                        <table class="table">
                                <jsp:include page="../view/tableTaskHead.jsp" />

                              <tbody>
                                  <c:forEach items="${mytasks}" var="task">
                                  <tr>
                                    <td>${task.title}</td>
                                    <td>
                                    <c:if test = "${task.description.length() >= 50}">
                                        ${task.description.substring(0,50)} ...
                                    </c:if>
                                    <c:if test = "${task.description.length() < 50}">
                                        ${task.description}
                                    </c:if>
                                    </td>
                                    <td>${task.createdAtFormated}</td>
                                    <td>${task.dueDateFormated}</td>
                                    <td>${task.userUsername}</td>
                                    <td>
                                        <c:if test = "${task.statusDescription=='Pendente'}">
                                            <span class="icon has-text-warning">
                                                <i class="fas fa-exclamation-triangle"></i>
                                            </span>
                                        </c:if>
                                        <c:if test = "${task.statusDescription=='emAndamento'}">
                                            <span class="icon has-text">
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
                                        <strong><a href="./editTask/${task.id}" target="_blank">
                                            <i class="fas fa-pen "></i>
                                        </a></strong>
                                        <strong><a class="deleteIcon" onclick="base.deleteTask(${task.id})">
                                            <i class="fas fa-trash-alt is-danger"></i>
                                        </a></strong>
                                    </td>

                                  </tr>
                                  </c:forEach>

                              </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    <br/>



    </section>

</div>