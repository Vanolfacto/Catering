<%-- 
    Document   : Placanje
    Created on : Jun 20, 2024, 10:06:31 PM
    Author     : Vanja
--%>

<%@page import="Modeli.Proizvod"%>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="Modeli.ProductUtil"%>
<%@ page language="java" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%
    HttpSession sesija = request.getSession(false);
    if (sesija == null || sesija.getAttribute("korisnikID") == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
    
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Plaćanje</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 5px;
        }
        h1 {
            color: #ff6347;
            text-align: center;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-top: 10px;
        }
        input, select {
            padding: 10px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .hidden {
            display: none;
        }
        .disabled {
            color: #aaa;
            background-color: #f4f4f4;
        }
        .submit-btn {
            padding: 10px;
            background-color: #ff6347;
            color: white;
            border: none;
            border-radius: 5px;
            margin-top: 20px;
            cursor: pointer;
        }
        .submit-btn:hover {
            background-color: #ff7f50;
        }
        
    </style>
    <script>
        function toggleCardInfo() {
            var nacinPlacanja = document.querySelector('input[name="nacinPlacanja"]:checked');
            var cardInfo = document.getElementById('cardInfo');
            var cardFields = document.querySelectorAll('#cardInfo input');
            
            if (nacinPlacanja && nacinPlacanja.value === 'karticom') {
                cardInfo.classList.remove('hidden');
                cardFields.forEach(function(field) {
                    field.disabled = false;
                    field.classList.remove('disabled');
                });
            } else {
                cardInfo.classList.add('hidden');
                cardFields.forEach(function(field) {
                    field.disabled = true;
                    field.classList.add('disabled');
                });
            }
        }

        function validateForm(event) {
            var nacinPlacanja = document.querySelector('input[name="nacinPlacanja"]:checked');
            if (!nacinPlacanja) {
                alert('Molimo izaberite način plaćanja.');
                event.preventDefault();
            }
        }

        window.onload = function() {
            document.querySelector('form').addEventListener('submit', validateForm);
            toggleCardInfo();  // Initial call to set the state of card fields based on the selected payment method
        };
    </script>
</head>
<body>
    <div class="container">
        <h1>Plaćanje</h1>
        <%
            
            
        List<Integer> proizvodi = (List<Integer>) session.getAttribute("korpaProizvodi");
        List<Integer> kolicine = (List<Integer>) session.getAttribute("korpaKolicine");

        if (proizvodi != null && kolicine != null && !proizvodi.isEmpty() && !kolicine.isEmpty()) {
            for (int i = 0; i < proizvodi.size(); i++) {
                int proizvodId = proizvodi.get(i);
                int kolicina = kolicine.get(i);
                // Pretpostavimo da imate metodu za dobavljanje informacija o proizvodu po ID-u
                Proizvod proizvod = ProductUtil.getProductInfo(proizvodId);
                if (proizvod != null) {
                    out.println("<p>" + proizvod.getNaziv() + " - " + kolicina + " komada</p>");
                }
            }
        } else {
            out.println("<p>Korpa je prazna.</p>");
        }
    %>
        <form action="ProcesPlacanja" method="post">
            <label for="ime">Ime:</label>
            <input type="text" id="ime" name="ime" required>

            <label for="prezime">Prezime:</label>
            <input type="text" id="prezime" name="prezime" required>

            <label for="adresa">Adresa:</label>
            <input type="text" id="adresa" name="adresa" required>

            <label for="grad">Grad:</label>
            <input type="text" id="grad" name="grad" required>

            <label for="postanskiBroj">Poštanski broj:</label>
            <input type="text" id="postanskiBroj" name="postanskiBroj" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <label for="telefon">Telefon:</label>
            <input type="tel" id="telefon" name="telefon" required>

            <label>Način plaćanja:</label>
            <label>
                <input type="radio" name="nacinPlacanja" value="pouzecem" onclick="toggleCardInfo()" required> Pouzećem
            </label>
            

            <button type="submit" class="submit-btn">Potvrdi</button>
        </form>
    </div>
</body>
</html>