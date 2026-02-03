<%-- 
    Document   : IzmenaProizvoda
    Created on : Jun 21, 2024, 6:37:13 PM
    Author     : Vanja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Modeli.Proizvod" %>
<%@ page import="Database.Database" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%
    HttpSession sesija = request.getSession(false);
    String userType = (String) sesija.getAttribute("userType");
    if (userType == null || !userType.equals("manager")) {
        response.sendRedirect("Login.jsp?error=unauthorized");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upravljanje Proizvodima</title>
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
    <h1>Upravljanje Proizvodima</h1>
    
    <!-- Forma za dodavanje proizvoda -->
     <nav>
        <a href="ManagerDashboard.jsp">Home</a>
        <a href="IzmenaProizvoda.jsp">Izmena</a>
        <a href="PrikazNarudzbina.jsp">Narudžbine</a>
         <%
            
            if (sesija != null && sesija.getAttribute("username") != null) {
        %>
            <a href="Logout">Logout</a>
        <% } else { %>
            <a href="Login.jsp">Login</a>
            <a href="Registracija.jsp">Register</a>
        <% } %>
    </nav>
     <h2>Dodaj Proizvod</h2>
    <form action="IzmenaProizvoda" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="add">
        <label for="naziv">Naziv:</label>
        <input type="text" id="naziv" name="naziv" required>
        <label for="tip">Tip:</label>
        <input type="text" id="tip" name="tip" required>
        <label for="cena">Cena:</label>
        <input type="number" id="cena" name="cena" step="0.01" required>
        <label for="opis">Opis:</label>
        <textarea id="opis" name="opis" required></textarea>
        <label for="slika">Slika:</label>
        <input type="file" id="slika" name="slika" required>
        <button type="submit">Dodaj</button>
    </form>

    <!-- Tabela proizvoda -->
    <h2>Lista Proizvoda</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Naziv</th>
            <th>Tip</th>
            <th>Cena</th>
            <th>Opis</th>
            <th>Slika</th>
            <th>Akcije</th>
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
            <td>
                <form action="IzmenaProizvoda" method="post" style="display:inline;"  enctype="multipart/form-data">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="id" value="<%= rs.getInt("ID") %>">
                        <button type="submit">Izmeni</button>
                    </form>
                    <!-- Forma za brisanje proizvoda -->
                    <form action="IzmenaProizvoda" method="post" style="display:inline;"  enctype="multipart/form-data">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= rs.getInt("ID") %>">
                        <button type="submit">Obriši</button>
                    </form>
            </td>
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