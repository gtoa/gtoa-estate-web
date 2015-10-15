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
  <a href="/gmail/contacts">返回</a>
  <br>
  <br>
  Id : ${contact.getContactId()}<br>
  Name: ${contact.getUserName()}<br>
  <c:forEach var="email" items="${contact.getEmailList()}">
    Email: ${email}<br>
  </c:forEach>
  <c:forEach var="phone" items="${contact.getPhoneList()}">
    Phone: ${phone}<br>
  </c:forEach>
  <script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
              (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-68652551-1', 'auto');
    ga('send', 'pageview');

  </script>
</body>
</html>
