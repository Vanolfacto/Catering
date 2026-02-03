/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servleti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Database.Database;
/**
 *
 * @author Vanja
 */
@WebServlet(name = "Registracija", urlPatterns = {"/Registracija"})
public class Registracija extends HttpServlet {

   
   private static final String url = "jdbc:mysql://localhost:3306/ketering";
     private static final String user = "admin"; 
    private static final String pass = "admin";
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    String ime= request.getParameter("ime");
    String prezime= request.getParameter("prezime");
    String username= request.getParameter("username");
    String lozinka= request.getParameter("lozinka");
    String email= request.getParameter("email");
    String adresa= request.getParameter("adresa");
    String brojTelefonaStr=request.getParameter("brojTelefona");
    
     int brojTelefona;
        try {
            brojTelefona = Integer.parseInt(brojTelefonaStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessageR", "Pogresan format");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = Database.getConnection();
        String sql="INSERT INTO korisnik (Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, ime);
            statement.setString(2, prezime);
            statement.setString(3, username);
            statement.setString(4, lozinka);
            statement.setString(5, email);
            statement.setString(6, adresa);
            statement.setInt(7, brojTelefona);
            statement.executeUpdate();
            connection.close();
    }
    catch(ClassNotFoundException | SQLException e){
        e.printStackTrace();
        request.setAttribute("errorMessageR", "Greška prilikom upisa u bazu.");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
    }
    response.sendRedirect("home.jsp");
    }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
