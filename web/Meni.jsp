<%-- 
    Document   : Meni
    Created on : Jun 20, 2024, 2:21:41 PM
    Author     : Vanja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="Database.Database"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Menu - Catering Service</title>
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
            max-width: 1200px;
            margin: 20px auto;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
        }
        .card {
            width: 300px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin: 10px;
            padding: 10px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .card h3 {
            color: #ff6347;
        }
        .card p {
            margin: 5px 0;
        }
        .card button {
            background-color: #ff6347;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .card button:hover {
            background-color: #ff7f50;
        }
        .card img {
            width: 100%;
            height: auto;
            border-bottom: 1px solid #ddd;
            margin-bottom: 10px;
        }
        .italic {
            margin-bottom: 5%;
        }
        .spacer {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <header>
        <h1>Naš meni</h1>
    </header>
    <nav>
        <a href="home.jsp">Home</a>
        <a href="Meni.jsp">Meni</a>
        <a href="about.jsp">O nama</a>
        <a href="Korpa.jsp">Korpa</a>
        <%
            HttpSession sesija = request.getSession(false);
            if (sesija != null && sesija.getAttribute("username") != null) {
        %>
            <a href="Logout">Logout</a>
        <% } else { %>
            <a href="Login.jsp">Login</a>
            <a href="Registracija.jsp">Register</a>
        <% } %>
    </nav>
    <div class="container">
          <%
            // Provera poruke o uspehu
            String message = (String) sesija.getAttribute("message");
            if (message != null) {
                sesija.removeAttribute("message");
        %>
            <div class="message">
                <%= message %>
            </div>
        <%
            }
        %>
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
                    int id = rs.getInt("id");
                    String naziv = rs.getString("naziv");
                    String opis = rs.getString("opis");
                    double cena = rs.getDouble("cena");
        %>
        <div class="card">
             <img src="Slika?id=<%= id %>" alt="<%= naziv %>">
            <h3><%= naziv %></h3>
            <p>Cena: <%= cena %> din</p>
            <i class="italic">Opis: <%= opis%></i>
            
            <!-- Additional product details can be added here -->
             <%
                if (sesija != null && sesija.getAttribute("username") != null) {
            %>
            <form action="UbaciUKorpu" method="post">
                <input type="hidden" name="id" value="<%= id %>">
                <label for="kolicina<%= id %>" class="spacer">Kolicina:</label>
                <input type="number" id="kolicina<%= id %>" name="kolicina" value="1" min="1">
                <button type="submit"  value="add">Dodaj u korpu</button>
            </form>
                  <%
                } else {
            %>
                <p>Morate biti ulogovani da biste dodali proizvode u korpu. <a href="Login.jsp">Ulogujte se</a></p>
            <%
                }
            %>
        </div>
        <%
                }
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
