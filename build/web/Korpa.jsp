<%-- 
    Document   : Korpa
    Created on : Jun 20, 2024, 8:59:12 PM
    Author     : Vanja
--%>
<%@ page import="java.sql.*" %>
<%@ page import="Database.Database" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page language="java" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    HttpSession sesija = request.getSession(false);
    if (session == null || sesija.getAttribute("korisnikID") == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
    int korisnikId = (int) session.getAttribute("korisnikID");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
         body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
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
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }
        h1 {
            color: #ff6347;
            text-align: center;
        }
        .total {
            text-align: right;
            margin-top: 20px;
            font-size: 1.2em;
        }
        .checkout-btn {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #ff6347;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 20px;
            cursor: pointer;
        }
        .checkout-btn:hover {
            background-color: #ff7f50;
        }
        .title{
            color: white;
        }
        
    </style>
    <body>
    <header>
        <h1 class='title'>Vaša Korpa</h1>
    </header>
    <nav>
        <a href="home.jsp">Home</a>
        <a href="Meni.jsp">Meni</a>
        <a href="about.jsp">O nama</a>
        <a href="Korpa.jsp">Korpa</a>
        <%
            if (sesija != null && sesija.getAttribute("username") != null) {
        %>
            <a href="Logout">Logout</a>
        <%
            } else {
        %>
            <a href="Login.jsp">Login</a>
            <a href="Registracija.jsp">Register</a>
        <%
            }
        %>
    </nav>
    <div class="container">
        <%
            double sveUkupno=0.0;
            
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                connection = Database.getConnection();
                String sql = "SELECT p.ID, p.Naziv, p.Opis, p.Cena, p.Slika, SUM(k.Kolicina) as UkupnaKolicina FROM korpa k INNER JOIN proizvod p ON k.ID_Proizvoda = p.ID WHERE k.ID_Korisnika = ?  GROUP BY k.ID_Proizvoda, p.Naziv, p.Opis, p.Cena, p.Slika";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, korisnikId);
                rs = ps.executeQuery();
                
                boolean hasItems = false;
                while (rs.next()) {
                    int proizvodId = rs.getInt("id");
                    String naziv = rs.getString("naziv");
                    String opis = rs.getString("opis");
                    double cena = rs.getDouble("cena");                   
                    int kolicina = rs.getInt("UkupnaKolicina");
                    double ukupnaCena = cena * kolicina;
                    sveUkupno+=ukupnaCena;
                    hasItems = true;
        %>
        <div class="cart-item">
            <div class="item-img">
              <img src="Slika?id=<%= proizvodId %>" alt="<%= naziv %>" style="max-width: 150px;">
            </div>
            <div class="item-details">
                <h3><%= naziv %></h3>
                <p>Opis: <%= opis %></p>
                <p>Cena: <%= cena %> din</p>
                <p>Količina: <%= kolicina %></p>
                <p>Ukupna Cena: <%= ukupnaCena%> din</p>
            </div>
            <div>
                <a href="Uklanjanje?id=<%= proizvodId %>" class="remove-btn">Ukloni</a>
            </div>
        </div>
             <%
                }

                if (!hasItems) {
        %>
        <div class="empty-cart">
            <p>Vaša korpa je prazna.</p>
        </div>  
        <%}%>
        <% if (hasItems) { %>
        <div class="total">
            <h3>Sve Ukupno: <%= sveUkupno %> din</h3>
        </div>
        <a href="Placanje.jsp" class="checkout-btn">Nastavi na Plaćanje</a>  
        
    <% } %>
     <%
                
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            }
        %>
    </div>
</body>
</html>
