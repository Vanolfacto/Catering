/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servleti;

import Database.Database;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vanja
 */
@WebServlet(name = "Uklanjanje", urlPatterns = {"/Uklanjanje"})
public class Uklanjanje extends HttpServlet {


   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
          HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("korisnikID") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        int korisnikId = (int) session.getAttribute("korisnikID");
        int proizvodId = Integer.parseInt(request.getParameter("id"));

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            String sql = "DELETE FROM korpa WHERE ID_Korisnika = ? AND ID_Proizvoda = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, korisnikId);
            ps.setInt(2, proizvodId);
            ps.executeUpdate();

            response.sendRedirect("Korpa.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while removing item from cart.");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
