<%--
  Created by IntelliJ IDEA.
  User: LazovoyOV
  Date: 16.10.2019
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Goods</title>
</head>
<body>
<h1>Add goods to the database</h1>
<form method ="POST" action="/goods">

    <table>
        <tr><input type="text" name="name" required>* name<br></tr>
        <tr> <input type="text" name="cost"  required>*cost<br></tr>
        <tr><input type="submit" value="Save Good"></tr>
    </table><br>
</form>

<a href="/">Back to Main page</a>
</body>
</html>