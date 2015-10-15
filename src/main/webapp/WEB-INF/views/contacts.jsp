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
 <a href="/">首页</a>&nbsp; &nbsp; &nbsp; &nbsp;
  <a href="/gmail/contact/add">添加联系人</a>
  <br>

  <table>

    <th>
      <tr>
        <td>用户名</td>
        <td>邮箱</td>
        <td>电话</td>
        <td>操作</td>
      </tr>
    </th>
    <tbody>
  <c:forEach var="contact" items="${contactList }">

      <tr>
        <td>${contact.getUserName()}</td>
        <td>${contact.getEmailList()}</td>
        <td>${contact.getPhoneList()}</td>
        <td><a href="/gmail/contact/delete?contactId=${contact.getContactId()}">删除</a> &nbsp;
          <a href="/gmail/contact/detail?contactId=${contact.getContactId()}">详情</a></td>
      </tr>

  </c:forEach>
    </tbody>

  </table>

  <br>
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
