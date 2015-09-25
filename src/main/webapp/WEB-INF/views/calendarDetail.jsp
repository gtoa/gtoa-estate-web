<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: xiaoxue.wang
  Date: 15/9/24
  Time: 下午2:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>联系人</title>
  <script type="text/javascript" src="http://s.xnimg.cn/ajax/jquery/jquery-1.11.0.min.js"></script>
  <script type="text/javascript" src="/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="/js/gtoa.js"></script>
  <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <a href="/calendar">返回</a>
  <br>
  <br>
  Id : ${event.getId()}<br>
  Name: ${event.getSummery()}<br>
  Creator: ${event.getCreator()}<br>
  CreateTime: ${event.getCreateTime()}<br>
  StartTime: ${event.getStartTime()}<br>
  EndTime: ${event.getEndTime()} <br>

</body>
</html>
