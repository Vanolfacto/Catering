<%-- 
    Document   : home
    Created on : Jun 19, 2024, 9:38:27 PM
    Author     : Vanja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home - Catering Service</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .title{
            color:white;
        }
        header {
            background-color: #ff6347;
            color: white;
            padding: 10px 0;
            text-align: center;
        }
         nav {
            margin: 0;
            padding: 10px 0;
            background-color: #333;
            overflow: hidden;
        }
        nav a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }
        nav a:hover {
            background-color: #ddd;
            color: black;
        }
        .content {
            padding: 20px;
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1, h2 {
            color: #ff6347;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        ul li {
            background-color: #f9f9f9;
            margin: 10px 0;
            padding: 10px;
            border: 1px solid #ddd;
        }
        
         .form-container {
            display: flex;
            justify-content: center;
            margin-top: 50px;
        }
        .form-container form {
            margin: 0 20px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f8f9fa;
        }
        .form-container input {
            display: block;
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        
    </style>
    </head>
    <body>
         <header>
        <h1 class="title">Dobrodošli u našu ketering službu!</h1>
    </header>
        <nav>
        <a href="home.jsp">Home</a>
        <a href="Meni.jsp">Meni</a>
        <a href="about.jsp">O nama</a>
        <a href="Korpa.jsp">Korpa</a>
         <%
            HttpSession sesija = request.getSession(false);
            if (session != null && sesija.getAttribute("username") != null) {
        %>
            <a href="Logout">Logout</a>
        <% } else { %>
            <a href="Login.jsp">Login</a>
            <a href="Registracija.jsp">Register</a>
        <% } %>
    </nav>
     <div class="content">
        <h2>Naš meni</h2>
        <p>Nudimo Vam razna ukusna jela koja će Vam stomake prepuniti. Ovde su jedan od naših omiljenih jela:</p>
        <ul>
            
            <li>
                <h3>Glavna Jela</h3>
                <p>Preporučujemo najviše Picu Kaprićozu.</p>
            </li>
            <li>
                <h3>Dezerti</h3>
                <p>Probajte slatka jela poput Braunija.</p>
            </li>
            
        </ul>
    </div>
    </body>
</html>


