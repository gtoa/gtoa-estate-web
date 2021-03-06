<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: snowy
  Date: 15/10/09
  Time: 下午8:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <%--<link href="/css/bootstrap.min.css" rel="stylesheet">--%>
    <%--<script src="/js/jquery.min.js"></script>--%>

</head>
<body>
    <c:choose>
        <c:when test="${userId == 0}">
            <a href="/login">login</a>
            <a href="/registry">registry</a>
        </c:when>
        <c:otherwise>
            id: <c:out value="${userId}" /> ,&nbsp; &nbsp;
            account: <c:out value="${account}" /><br>
            <c:if test="${hasToken > 0}">
                <a href="/calendar"> 日历事件</a><br>
                <a href="/gmail/contacts">联系人</a><br>
                <a href="/showfiles" > 文件管理</a> <br>
            </c:if>
            <c:if test="${hasToken <= 0}">
                <a href="${authUrl}">授权</a><br>
            </c:if>
        </c:otherwise>
    </c:choose>
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
