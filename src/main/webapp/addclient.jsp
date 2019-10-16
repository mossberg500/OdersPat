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
    <title>Add client</title>
</head>
<body>
<h1>Add client to the database</h1>
<form method ="POST" action="/clients">

    <table>
        <tr><input type="text" name="fullName" required>* Full name<br></tr>
        <tr> <input type="tel" name="phone" pattern="[0-9]{2}[0-9]{3}[0-9]{7}" required>*Phone number. Format: 380000000000<br></tr>
        <tr><input type="submit" value="Save Client"></tr>
    </table><br>
</form>

<a href="/">Back to Main page</a>
</body>
</html>