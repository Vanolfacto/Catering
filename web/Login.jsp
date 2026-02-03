<%-- 
    Document   : Login
    Created on : Jun 20, 2024, 2:01:24 PM
    Author     : Vanja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login - Catering Service</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #4CAF50;
            color: white;
            padding: 20px;
            text-align: center;
        }
        .form-container {
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .form-container form {
            display: flex;
            flex-direction: column;
        }
        .form-container form h2 {
            margin-bottom: 20px;
            text-align: center;
            color: #333;
        }
        .form-container input[type="text"], .form-container input[type="password"] {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .form-container input[type="submit"] {
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .form-container input[type="submit"]:hover {
            background-color: #45a049;
        }
        .form-container p {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>
    <header>
        <h1>Login</h1>
    </header>
    
    <div class="form-container">
        <form action="Login" method="post">
            <h2>Login</h2>
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="lozinka" placeholder="Password" required>
            <input type="submit" value="Login">
            <%
        if ("true".equals(request.getParameter("error"))) {
            out.println("<p style='color:red;'>Neispravno korisničko ime ili lozinka</p>");
        }
    %>
        </form>
    </div>
</body>
</html>
