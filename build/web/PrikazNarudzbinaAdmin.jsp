<%-- 
    Document   : PrikazNarudzbinaAdmin
    Created on : Jun 22, 2024, 7:26:58 PM
    Author     : Vanja
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Modeli.Narudzbina" %>
<%@ page import="Modeli.StavkaNarudzbine" %>
<%@ page import="Database.Database" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
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
    List<Map<String, Object>> narudzbine = new ArrayList<>();

    try {
        connection = Database.getConnection();
        String sql = "SELECT * FROM narudzbina";
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, Object> narudzbina = new HashMap<>();
            narudzbina.put("id", rs.getInt("id"));
            narudzbina.put("datum", rs.getTimestamp("datum"));
            narudzbina.put("ime", rs.getString("ime"));
            narudzbina.put("prezime", rs.getString("prezime"));
            narudzbina.put("adresa", rs.getString("adresa"));
            narudzbina.put("grad", rs.getString("grad"));
            narudzbina.put("postanskiBroj", rs.getInt("postanskiBroj"));
            narudzbina.put("email", rs.getString("email"));
            narudzbina.put("telefon", rs.getInt("telefon"));
            narudzbina.put("nacinPlacanja", rs.getString("nacinPlacanja"));

            // Dohvatanje stavki narudžbine
            String stavkeSql = "SELECT * FROM stavka_narudzbine WHERE Narudzbina_ID = ?";
            PreparedStatement stavkePs = connection.prepareStatement(stavkeSql);
            stavkePs.setInt(1, rs.getInt("id"));
            ResultSet stavkeRs = stavkePs.executeQuery();
            List<Map<String, Object>> stavke = new ArrayList<>();
            while (stavkeRs.next()) {
                Map<String, Object> stavka = new HashMap<>();
                stavka.put("nazivProizvoda", stavkeRs.getString("nazivProizvoda"));
                stavka.put("kolicina", stavkeRs.getInt("kolicina"));
                stavka.put("cena", stavkeRs.getDouble("cena"));
                stavke.add(stavka);
            }
            narudzbina.put("stavke", stavke);
            narudzbine.add(narudzbina);
            stavkeRs.close();
            stavkePs.close();
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
        <title>Prikaz Narudzbina</title>
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
           
        }
        h1, h2 {
            color: #ff6347;
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
        <h1 class="title">Prikaz Narudžbina</h1>
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
        <h2>Lista Narudžbina</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Datum</th>
                <th>Ime i Prezime</th>
                <th>Adresa</th>
                <th>Grad</th>
                <th>Poštanski Broj</th>
                <th>Email</th>
                <th>Telefon</th>
                <th>Način Plaćanja</th>
                <th>Stavke</th>
                <th>Akcije</th>
            </tr>
            <% for (Map<String, Object> narudzbina : narudzbine) { %>
                <tr>
                    <td><%= narudzbina.get("id") %></td>
                    <td><%= narudzbina.get("datum") %></td>
                    <td><%= narudzbina.get("ime") %> <%= narudzbina.get("prezime") %></td>
                    <td><%= narudzbina.get("adresa") %></td>
                    <td><%= narudzbina.get("grad") %></td>
                    <td><%= narudzbina.get("postanskiBroj") %></td>
                    <td><%= narudzbina.get("email") %></td>
                    <td><%= narudzbina.get("telefon") %></td>
                    <td><%= narudzbina.get("nacinPlacanja") %></td>
                    <td>
                        <ul>
                            <% List<Map<String, Object>> stavke = (List<Map<String, Object>>) narudzbina.get("stavke"); %>
                            <% for (Map<String, Object> stavka : stavke) { %>
                                <li><%= stavka.get("nazivProizvoda") %> - Količina: <%= stavka.get("kolicina") %> - Cena: <%= stavka.get("cena") %></li>
                            <% } %>
                        </ul>
                    </td>
                    <td>
                        <form action="BrisanjeNarudzbine" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= narudzbina.get("id") %>">
                            <button type="submit">Obriši</button>
                        </form>
                    </td>
                </tr>
            <% } %>
        </table>
    </div>
</body>
</html>
