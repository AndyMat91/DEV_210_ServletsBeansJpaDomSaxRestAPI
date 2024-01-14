<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<form method=post action=Authorization style="text-align: center">
    <h3>Авторизация</h3>
    <h3> ${Error_auth} </h3>
    Login: <input name="login" /> <br><br>
    Password: <input type="password" name="password" /> <br><br>
    <input type="submit" name="OK" value="OK" <br><br>
</form>
</body>
</html>