<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Search</title>
</head>
<body>
<h2>Find files:</h2>
<form method="post">
    <input type="text" name="name">
    <input type="submit" value="Go">
</form>
<table>
    <c:forEach items="${foundFiles}" var="file">
        <tr>
            <td>
                    ${file.getCatPath()}
            </td>
            <td>
                    ${file.getFileName()}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
