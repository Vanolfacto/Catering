<%-- 
    Document   : PromenaUloga
    Created on : Jun 22, 2024, 8:39:11 PM
    Author     : Vanja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="Database.Database" %>
<%
    HttpSession sesija = request.getSession(false);
    String userType = (String) sesija.getAttribute("userType");
    if (userType == null || !userType.equals("admin")) {
        response.sendRedirect("Login.jsp?error=unauthorized");
        return;
    }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promena Uloge</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
            }
            form {
                max-width: 400px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            label {
                display: block;
                margin-bottom: 10px;
            }
            select, button {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 3px;
            }
            button {
                background-color: #4CAF50;
                color: white;
                border: none;
                cursor: pointer;
            }
            button:hover {
                background-color: #45a049;
            }
            nav {
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
        </style>
    </head>
    <body>
        <nav>
        <a href="AdminDashboard.jsp">Home</a>
        <a href="IzmenaProizvodaAdmin.jsp">Izmena</a>
        <a href="PrikazNarudzbinaAdmin.jsp">Narudžbine</a>
        <a href="PrikazNaloga.jsp">Nalozi Korisnika</a>
        <a href="PrikazNalogaManager.jsp">Nalozi Menadžera</a>
        <a href="PromenaUloga.jsp">Promena Uloga Korisnika</a>
        <a href="PromenaUlogaManager.jsp">Promena Uloga Menadžera</a>
        <a href="PromenaUlogaAdmin.jsp">Promena Uloga Admina</a>
        <% if (sesija != null && sesija.getAttribute("username") != null) { %>
            <a href="Logout">Logout</a>
        <% } else { %>
            <a href="Login.jsp">Login</a>
            <a href="Registracija.jsp">Register</a>
        <% } %>
    </nav>
        <form action="PromenaUloga" method="post">
    <h2>Promena Uloge Korisnika</h2>
    <label for="userId">Korisnik ID:</label>
    <input type="text" id="userId" name="userId" required>

    <label for="novaUloga">Nova Uloga:</label>
    <select id="novaUloga" name="novaUloga" required>
        <option value="korisnik">Korisnik</option>
        <option value="manager">Menadžer</option>
        <option value="admin">Administrator</option>
    </select>

    <button type="submit">Promeni Ulogu</button>
</form>


    </body>
</html>
