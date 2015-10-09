<%--
  Created by IntelliJ IDEA.
  User: xiaoxue.wang
  Date: 15/9/25
  Time: 下午5:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>添加日历项</title>
</head>

<body>
<form action="/gmail/contact/add" method="post">
  <table>
    <tr>
      <td>Family Name：</td>
      <td><input type="text"  name="familyName" id="familyName"/></td>
    </tr>
    <tr>
      <td>Given Name：</td>
      <td><input type="text" name="givenName" id="givenName"/> </td>
    </tr>
    <tr>
      <td>Email Address：</td>
      <td><input type="text" name="emailAddress" id="emailAddress"/> </td>
    </tr>
    <tr>
      <td>Phone Number：</td>
      <td><input type="text" name="phoneNumber" id="phoneNumber"/> </td>
    </tr>

    <tr>
      <td><input type="submit" value="提交"/> </td>
      <td></td>
    </tr>
  </table>
</form>

</body>
</html>
