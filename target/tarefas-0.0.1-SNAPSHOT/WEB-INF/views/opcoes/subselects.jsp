<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <option selected disabled hidden value="">Escolha...</option>
<c:forEach items="${itens}" var="item">
    <option value='${item.value}'>${item.label}</option>
  </c:forEach>