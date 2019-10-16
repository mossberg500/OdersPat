<%--
  Created by IntelliJ IDEA.
  User: LazovoyOV
  Date: 16.10.2019
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goods database management</title>
</head>
<body>
<th><h3>Goods</h3></th>
<table border="0" frame="border" rules="all">
    <tr>
        <th>name</th>
        <th>cost</th>
    </tr>
    <c:forEach var="good" items="${lstGoods}">
        <tr>

            <form action="goods" method="get">
                <td><input type="text" name="name" value="<c:out value="${good.name}" />"></td>
                <td><input type="text" name="cost" value=${good.cost}></td>

            </form>
        </tr>
    </c:forEach>
</table>
<table>
    <tr>
        <td colspan="3"><h2>Commands</h2></td>
    </tr>
    <tr>
        <h3>
            <td><a href="/goods?command=All Goods">Refresh</a></td>
            <td><a href="/addgood.jsp">Add Goods</a></td>
            <td><a href="/index.jsp">Home</a></td>

        </h3>
    </tr>
</table>
</body>
</html>