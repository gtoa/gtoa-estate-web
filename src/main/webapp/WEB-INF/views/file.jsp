<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<body>

		<table>
			<tr>
				<td>文件名</td>
				<td>操作</td>
			</tr>
			<c:forEach var="fileName" items="${fileList}" >
				<tr>
					<td>${fileName}</td>
					<td><a href="downloadfile?fileName=${fileName}">下载</a> ｜
						<a href="/deletefile?fileName=${fileName}">删除</a></td>
				</tr>
			</c:forEach>
		</table>


 <form action="/uploadfile" method="post" enctype="multipart/form-data">
      <tr><label>Upload File:</label></tr>
      <tr><input name="file" type="file"/></tr>
      <tr><input name="submit" type="submit"/></tr>
    </form>
	</body>
</html>