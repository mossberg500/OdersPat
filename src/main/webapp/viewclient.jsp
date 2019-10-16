<%--
  Created by IntelliJ IDEA.
  User: LazovoyOV
  Date: 14.10.2019
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Client database management</title>
</head>
<body>
<th><h3>Clients</h3></th>
<table border="0" frame="border" rules="all">
    <tr>
        <th>Full name</th>
        <th>Phone</th>
        <th>Commands</th>
    </tr>
    <c:forEach var="client" items="${lstClients}">
        <tr>

            <form action="clients" method="get">
                <td><input type="text" name="fullName" value="<c:out value="${client.fullName}" />"></td>
                <td><input type="text" name="phone" value=${client.phone}></td>


                <td><input type="hidden" name="id" value="${client.id}">
                    <button type="submit" name="command" value="Apply">Edit</button>
                    <button type="submit" name="command" value="Delete">delete</button>

                </td>
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
                <td><a href="/clients?command=All Clients">Refresh</a></td>
                <td><a href="/addclient.jsp">Add client</a></td>
                <td><a href="/index.jsp">Home</a></td>

        </h3>
    </tr>
</table>
</body>
</html>