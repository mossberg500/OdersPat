<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit order</title>
</head>

<body>

<c:choose>
    <c:when test="${id==null}">
        <h4>New order</h4>
    </c:when>
    <c:otherwise>
        <h4>Edit order # ${id}</h4>
    </c:otherwise>
</c:choose>

<form action="orders" method="get">
    <input type="hidden" name="id" value="${id}">
    <table border="0" frame="border">
        <col width="100" valign="top">
        <col width="150" valign="top">
        <td><b>Date </b></td>
        <td>
            <input type="date" name="date" value="${date}">
        </td>
        <td><b>Client</b></td>
        <td>
            <select name="client">
                <c:forEach var="client" items="${lstClients}">
                    <option value="${client.id}" ${client.selected}> ${client.fullName}</option>
                </c:forEach>
            </select>
        </td>
    </table>

    <table border="0" frame="border">
        <col width="100" valign="left">
        <col width="20" valign="top">
        <col width="20" valign="top">
        <tr>
            <th>Good</th>
            <th>Count</th>
            <th>Cost</th>
        </tr>

        <c:forEach var="good" items="${lstGoods}">
            <tr>
                <td>
                    <input type="checkbox" name="goodsCheckId"
                           value="<c:out value="${good.id}"/>" ${good.checked} >${good.name}
                </td>
                <input type="hidden" name="goodsId" value="${good.id}">
                <td><input type="text" name="goodsCount" value="${(good.count==0) ? 1 : good.count}"></td>
                <td><input type="text" name="goodsCost" value=${good.cost}></td>
            </tr>
        </c:forEach>
    </table>

    <input type="submit" name="command" value="Ok">
    <input type="submit" name="command" value="Cancel">

</form>
</body>
</html>