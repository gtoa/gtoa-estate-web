<%--
  Created by IntelliJ IDEA.
  User: snowy
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
  <form action="/calendar/add" method="post">
    <table>
      <tr>
        <td>主题：</td>
        <td><input type="text"  name="summary" id="summary"/></td>
      </tr>
      <tr>
        <td>描述：</td>
        <td><input type="text" name="desc" id="desc"/> </td>
      </tr>
      <tr>
        <td>位置：</td>
        <td><input type="text" name="location" id="location"/> </td>
      </tr>
      <tr>
        <td>开始时间(格式为2015-09-23 12:23:00)：</td>
        <td><input type="text"  name="startTime" id="startTime"/></td>
      </tr>
      <tr>
        <td>结束时间(格式为2015-09-23 12:23:00)：</td>
         <td><input type="text" name="endTime" id="endTime"/></td>
      </tr>
      <tr>
        <td><input type="submit" value="提交"/> </td>
        <td></td>
      </tr>
    </table>
  </form>

</body>
</html>
