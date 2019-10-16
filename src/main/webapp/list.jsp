<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goods, clients, orders</title>
</head>

<body>

<h2> Orders data base </h2>

<h3>Goods</h3>
<table border="0" frame="border" rules="all">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>management</th>
    </tr>
    <c:forEach var="good" items="${lstGoods}">
        <tr>
            <form action="goods" method="get">

                <td><input type="text" name="name" value="<c:out value="${good.name}" />"></td>
                <td><input type="text" name="cost" value=${good.cost}></td>
                <td>
                    <input type="hidden" name="id" value="${good.id}">
                    <input type="submit" name="command" value="Apply">
                    <input type="submit" name="command" value="Delete">
                </td>
            </form>
        </tr>
    </c:forEach>
    <td>
        <form action="goods" method="get">
                <input type="submit" name="command" value="All Goods">
        </form>
    </td>
</table>

<h1>Client list</h1>
<table border="0" frame="border" rules="all">

        <tr><th>Client ID</th><th>Full name</th><th>Phone number</th></tr>

        <c:forEach var="client" items="${lstClients}">
            <tr>
                <form action="clients" method="get">
                    <td><c:out value="${client.id}"/></td>
                    <td><c:out value="${client.fullName}"/> </td>
                    <td><c:out value="${client.phone}"/> </td>
                </form>

            </tr>
        </c:forEach>


    <td>
        <form action="clients" method="get">
                <input type="submit" name="command" value="All Clients" >
        </form>
    </td>
</table>

<th><h3>Orders</h3></th>
<table border="0" frame="border" rules="all">
    <col width="100" valign="top">
    <col width="100" valign="top">
    <col width="150" valign="top">
    <col width="50" valign="top">
    <tr>
        <th>Number</th>
        <th>Date</th>
        <th>Client</th>
        <th>Sum</th>
        <th>management</th>
    </tr>
    <c:forEach var="order" items="${lstOrders}">
        <tr>
            <form action="orders" method="get">
                <td align="middle">${order.id}</td>
                <td align="middle">${order.date}</td>
                <td>${order.client}</td>
                <td align="right">${order.sum}</td>

                <td>
                    <input type="hidden" name="id" value="${order.id}">
                    <input type="submit" name="command" value="Edit">
                    <input type="submit" name="command" value="Delete">
                </td>
            </form>
        </tr>
    </c:forEach>
    <tr>
        <form action="orders" method="get">
            <td colspan="4"> </td>
            <td colspan="2" align="bottom">
                <input type="submit" name="command" value="New order">
            </td>
        </form>
    </tr>

</table>


</body>
</html>