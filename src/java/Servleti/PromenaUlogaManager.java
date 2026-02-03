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
@WebServlet(name = "PromenaUlogaManager", urlPatterns = {"/PromenaUlogaManager"})
public class PromenaUlogaManager extends HttpServlet {

  

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        String userIdM = request.getParameter("userIdM");
        String novaUlogaM = request.getParameter("novaUlogaM");
        
        // Validacija parametara
        if (userIdM == null || novaUlogaM == null) {
            response.sendRedirect("PromenaUlogaManager.jsp?error=missingParams");
            return;
        }
        
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psDelete = null;
        
        try {
            connection = Database.getConnection(); // Implementirati prema vašoj konfiguraciji
            
            
            if(novaUlogaM.equals("korisnik")){
                 String insertSql = "INSERT INTO korisnik (ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona) " +
                                   "SELECT ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona " +
                                   "FROM manager WHERE ID = ?";
                psInsert = connection.prepareStatement(insertSql);
                psInsert.setInt(1, Integer.parseInt(userIdM));
                psInsert.executeUpdate();
                
                
                // Obriši korisnika iz korisnik ili manager tabele
                String deleteSql = "DELETE FROM manager WHERE ID = ?";
                psDelete = connection.prepareStatement(deleteSql);
                psDelete.setInt(1, Integer.parseInt(userIdM));
                psDelete.executeUpdate();
                
                
            }
            else if(novaUlogaM.equals("admin")){
                String insertSql = "INSERT INTO admin (ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona) " +
                                   "SELECT ID, Ime, Prezime, UserName, Lozinka, Email, Adresa, Broj_telefona " +
                                   "FROM manager WHERE ID = ?";
                psInsert = connection.prepareStatement(insertSql);
                psInsert.setInt(1, Integer.parseInt(userIdM));
                psInsert.executeUpdate();
                
            
                
                String deleteSql = "DELETE FROM manager WHERE ID = ?";
                psDelete = connection.prepareStatement(deleteSql);
                psDelete.setInt(1, Integer.parseInt(userIdM));
                psDelete.executeUpdate();
            }
            else {
                response.sendRedirect("PromenaUlogaManager.jsp?error=invalidRole");
                return;
            }
            
            
            
            response.sendRedirect("PromenaUlogaManager.jsp?success=true");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("PromenaUlogaManager.jsp?error=databaseError");
            
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
