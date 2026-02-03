<%-- 
    Document   : PrikazNaloga
    Created on : Jun 22, 2024, 7:36:31 PM
    Author     : Vanja
--%>

<%@page import="Modeli.Korisnik"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
Connection connection = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Map<String, Object>> korisnici = new ArrayList<>();
    
try {
        connection = Database.getConnection();
        String sql = "SELECT * FROM korisnik";
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, Object> korisnik = new HashMap<>();
            korisnik.put("id", rs.getInt("id"));
            korisnik.put("ime", rs.getString("ime"));
            korisnik.put("prezime", rs.getString("prezime"));
            korisnik.put("username", rs.getString("username"));
            korisnik.put("email", rs.getString("email"));

           
            korisnici.add(korisnik);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Prikaz Naloga Korisnika</title>
         <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        header {
            background-color: #ff6347;
            color: white;
            padding: 10px 0;
            text-align: center;
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
        .content {
            padding: 20px;
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
    </style>
    </head>
    <body>
         <header>
        <h1>Prikaz Naloga Korisnika</h1>
    </header>
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
     <div class="content">
    <h2>Lista Korisnika</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Ime</th>
            <th>Prezime</th>
            <th>Korisničko Ime</th>
            <th>Email</th>
            <th>Akcije</th>
        </tr>
       <% for (Map<String, Object> korisnik : korisnici) { %>
            <tr>
                <td><%= korisnik.get("id") %></td>
                <td><%= korisnik.get("ime") %></td>
                <td><%= korisnik.get("prezime") %></td>
                <td><%= korisnik.get("username") %></td>
                <td><%= korisnik.get("email") %></td>             
                <td>
                    <form action="BrisanjeNaloga" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= korisnik.get("id") %>">
                        <button type="submit">Obriši</button>
                    </form>
                </td>
            </tr>
             <% } %>
    </table>
</div>
    </body>
</html>
