<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: snowy
  Date: 15/10/14
  Time: 下午7:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>third User Info</title>
</head>
<body>
  <c:choose>
    <c:when test="${user.thirdType == 1}">
      facebook用户
    </c:when>
    <c:when test="${user.thirdType == 2}">
      google用户
    </c:when>
    <c:when test="${user.thirdType == 3}">
      twitter用户
    </c:when>
    <c:otherwise>

    </c:otherwise>
  </c:choose>
<br>
  用户名： ${user.userName}<br>
  邮箱： ${user.email} <br>
  第三方ID: ${user.thirdId}

</body>
</html>
