<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html" charset=UTF-8>

<div  class= "field is-grouped">
    <p class="control has-icons-left">
    <span class="select is-rounded">
      <select id="user_select" >
         <option selected disabled hidden value="">Escolha um usuário para gerenciar</option>
         <c:forEach items="${avaliableusers}" var="user">
         <option value="${user.id}">${user.username}</option>
         </c:forEach>
      </select>
    </span>
        <span class="icon is-small is-left">
      <i class="fas fa-user"></i>
    </span>
    </p>

    <div class="control ">
        <button onclick="base.openUserEditWindow()" class="button is-link is-light"><i class="fas fa-pen "></i></button>
    </div>
    <div class="control ">
        <button onclick="base.deleteUser()" class="button is-link is-light"><i class="fas fa-trash-alt is-danger deleteIcon"></i></button>
    </div>
</div>

