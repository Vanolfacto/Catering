/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servleti;

import java.io.IOException;
import java.sql.Connection;
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
@WebServlet(name = "PromenaUloga", urlPatterns = {"/PromenaUloga"})
public class PromenaUloga extends HttpServlet {

    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String userId = request.getParameter("userId");
        String novaUloga = request.getParameter("novaUloga");
       
        // Validacija parametara
        if (userId == null || novaUloga == null) {
            response.sendRedirect("PromenaUloga.jsp?error=missingParams");
            return;
        }
        
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psDelete = null;
        
        try {
            connection = Database.getConnection(); // Implementirati prema vašoj konfiguraciji
            
            if (novaUloga.equals("manager")) {
                // Premesti korisnika iz korisnik tabele u manager tabelu
                String insertSql = "INSERT INTO manager (ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona) " +
                                   "SELECT ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona " +
                                   "FROM korisnik WHERE ID = ?";
                psInsert = connection.prepareStatement(insertSql);
                psInsert.setInt(1, Integer.parseInt(userId));
                psInsert.executeUpdate();
               
                
                // Obriši korisnika iz korisnik tabele
                String deleteSql = "DELETE FROM korisnik WHERE ID = ?";
                psDelete = connection.prepareStatement(deleteSql);
                psDelete.setInt(1, Integer.parseInt(userId));
                psDelete.executeUpdate();
                
                // Obriši korisnika iz admin tabele (ako postoji)
                deleteSql = "DELETE FROM admin WHERE ID = ?";
                psDelete = connection.prepareStatement(deleteSql);
                psDelete.setInt(1, Integer.parseInt(userId));
                psDelete.executeUpdate();
                
            } else if (novaUloga.equals("admin")) {
                // Premesti korisnika iz korisnik ili manager tabele u admin tabelu
                String insertSql = "INSERT INTO admin (ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona) " +
                                   "SELECT ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona " +
                                   "FROM korisnik WHERE ID = ?";
                psInsert = connection.prepareStatement(insertSql);
                psInsert.setInt(1, Integer.parseInt(userId));
                psInsert.executeUpdate();
                
                
                // Obriši korisnika iz korisnik ili manager tabele
                String deleteSql = "DELETE FROM korisnik WHERE ID = ?";
                psDelete = connection.prepareStatement(deleteSql);
                psDelete.setInt(1, Integer.parseInt(userId));
                psDelete.executeUpdate();
                
                deleteSql = "DELETE FROM manager WHERE ID = ?";
                psDelete = connection.prepareStatement(deleteSql);
                psDelete.setInt(1, Integer.parseInt(userId));
                psDelete.executeUpdate();
                
            }  else {
                response.sendRedirect("PromenaUloga.jsp?error=invalidRole");
                return;
            }
            
            
            
            response.sendRedirect("PromenaUloga.jsp?success=true");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("PromenaUloga.jsp?error=databaseError");
            
        } finally {
            try {
                if (psInsert != null) psInsert.close();
                if (psDelete != null) psDelete.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
