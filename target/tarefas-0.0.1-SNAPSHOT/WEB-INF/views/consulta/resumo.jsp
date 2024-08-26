<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<table class="table">
      <thead>
          <tr>
            <th>Categoria</th>
            <th>total</th>
          </tr>
      </thead>

      <tbody>
        <tr>
          <td>Diorama</td>
          <td>${totaldiorama}</td>
        </tr>
        <tr>
          <td>Astronautica</td>
          <td>${totalastronautica}</td>
        </tr>
        <tr>
          <td>Autos</td>
          <td>${totalautos}</td>
        </tr>
        <tr>
          <td>Aviação</td>
          <td>${totalaviacao}</td>
        </tr>
        <tr>
          <td>Ficção</td>
          <td>${totalficcao}</td>
        </tr>
        <tr>
          <td>Figuras</td>
          <td>${totalfiguras}</td>
        </tr>
        <tr>
          <td>Militaria</td>
          <td>${totalmilitaria}</td>
        </tr>
        <tr>
          <td>Naval</td>
          <td>${totalnaval}</td>
        </tr>
      </tbody>
</table>