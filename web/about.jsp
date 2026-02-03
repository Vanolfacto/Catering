<%-- 
    Document   : about
    Created on : Jun 20, 2024, 10:26:33 AM
    Author     : Vanja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>O nama - Catering Service</title>
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
            margin: 20px auto;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1, h2 {
            color: #ff6347;
        }
    </style>
</head>
<body>
    <header>
        <h1 class='title'>O nama</h1>
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
        <h2>O našoj Ketering Službi</h2>
        <p>Ova Ketering Služba je jedna od najstarijih na celom Balkanu. Nastala je 1390. godinu dana nakon Kosovskog Boja. Osnivač ove Ketering Službe je jedan jedini Vuk Branković.</p>
    </div>
</body>
</html>