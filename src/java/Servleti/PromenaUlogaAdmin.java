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
@WebServlet(name = "PromenaUlogaAdmin", urlPatterns = {"/PromenaUlogaAdmin"})
public class PromenaUlogaAdmin extends HttpServlet {

 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdA = request.getParameter("userIdA");
        String novaUlogaA = request.getParameter("novaUlogaA");
        // Validacija parametara
        if (userIdA == null || novaUlogaA == null) {
            response.sendRedirect("PromenaUlogaAdmin.jsp?error=missingParams");
            return;
        }
        
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psDelete = null;
        
        try {
            connection = Database.getConnection(); // Implementirati prema vašoj konfiguraciji
            
            
            if(novaUlogaA.equals("korisnik")){
                 String insertSql = "INSERT INTO korisnik (ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona) " +
                                   "SELECT ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona " +
                                   "FROM admin WHERE ID = ?";
                psInsert = connection.prepareStatement(insertSql);
                psInsert.setInt(1, Integer.parseInt(userIdA));
                psInsert.executeUpdate();
                
                
                // Obriši korisnika iz korisnik ili manager tabele
                
                
                String deleteSql = "DELETE FROM admin WHERE ID = ?";
                psDelete = connection.prepareStatement(deleteSql);
                psDelete.setInt(1, Integer.parseInt(userIdA));
                psDelete.executeUpdate();
            }
            else if(novaUlogaA.equals("manager")){
                String insertSql = "INSERT INTO manager (ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona) " +
                                   "SELECT ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona " +
                                   "FROM admin WHERE ID = ?";
                psInsert = connection.prepareStatement(insertSql);
                psInsert.setInt(1, Integer.parseInt(userIdA));
                psInsert.executeUpdate();
                
                
                
                
                String deleteSql = "DELETE FROM admin WHERE ID = ?";
                psDelete = connection.prepareStatement(deleteSql);
                psDelete.setInt(1, Integer.parseInt(userIdA));
                psDelete.executeUpdate();
            }
            else {
                response.sendRedirect("PromenaUlogaAdmin.jsp?error=invalidRole");
                return;
            }
            
            
            response.sendRedirect("PromenaUlogaAdmin.jsp?success=true");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("PromenaUlogaAdmin.jsp?error=databaseError");
            
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
