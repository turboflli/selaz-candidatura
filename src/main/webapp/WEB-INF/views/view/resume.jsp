<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<table class="table">
      <thead>
          <tr>
            <th>Status</th>
            <th>total</th>
          </tr>
      </thead>

      <tbody>
        <tr>
          <td>Pendente</td>
          <td>${totalpendente}</td>
        </tr>
        <tr>
          <td>Em Andamento</td>
          <td>${totalemandamento}</td>
        </tr>
        <tr>
          <td>Concluida</td>
          <td>${totalconcluida}</td>
        </tr>

      </tbody>
</table>