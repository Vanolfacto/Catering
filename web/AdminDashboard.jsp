<%-- 
    Document   : AdminDashboard
    Created on : Jun 22, 2024, 7:21:14 PM
    Author     : Vanja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Modeli.Proizvod" %>
<%@ page import="Database.Database" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<% HttpSession sesija = request.getSession(false);
    String userType = (String) sesija.getAttribute("userType");
    if (userType == null || !userType.equals("admin")) {
        response.sendRedirect("Login.jsp?error=unauthorized");
        return;
    }%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Dashboard</title>
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
    <nav>
        <a href="AdminDashboard.jsp">Home</a>
        <a href="IzmenaProizvodaAdmin.jsp">Izmena</a>
        <a href="PrikazNarudzbinaAdmin.jsp">Narudžbine</a>
        <a href="PrikazNaloga.jsp">Nalozi Korisnika</a>
        <a href="PrikazNalogaManager.jsp">Nalozi Menadžera</a>
        <a href="PromenaUloga.jsp">Promena Uloga Korisnika</a>
        <a href="PromenaUlogaManager.jsp">Promena Uloga Menadžera</a>
        <a href="PromenaUlogaAdmin.jsp">Promena Uloga Admina</a>
         <%
           
            if (sesija != null && sesija.getAttribute("username") != null) {
        %>
            <a href="Logout">Logout</a>
        <% } else { %>
            <a href="Login.jsp">Login</a>
            <a href="Registracija.jsp">Register</a>
        <% } %>
    </nav>
    <h2>Dobrodošli, Admin!</h2>
    <h3>Pregled proizvoda</h3>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Naziv</th>
            <th>Tip</th>
            <th>Cena</th>
            <th>Opis</th>
            <th>Slika</th>
        </tr>
        <%
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                connection = Database.getConnection();
                String sql = "SELECT * FROM proizvod";
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                int proizvodId = rs.getInt("id");
        %>
        <tr>
            <td><%= rs.getInt("ID") %></td>
            <td><%= rs.getString("Naziv") %></td>
            <td><%= rs.getString("Tip") %></td>
            <td><%= rs.getDouble("Cena") %></td>
            <td><%= rs.getString("Opis") %></td>
            <td><img src="Slika?id=<%= proizvodId %>" alt="Slika proizvoda" width="100" height="100"></td>
        </tr>
        <%
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (connection != null) connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        %>
    </table>
</body>
</html>
