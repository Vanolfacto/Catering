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
@WebServlet(name = "BrisanjeNarudzbine", urlPatterns = {"/BrisanjeNarudzbine"})
public class BrisanjeNarudzbine extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          int id = Integer.parseInt(request.getParameter("id"));

        Connection connection = null;
        PreparedStatement psStavke = null;
        PreparedStatement psNarudzbina = null;

        try {
            connection = Database.getConnection();
            connection.setAutoCommit(false); // Počinjemo transakciju

            // Prvo brišemo stavke narudžbine
            String sqlStavke = "DELETE FROM stavka_narudzbine WHERE Narudzbina_ID = ?";
            psStavke = connection.prepareStatement(sqlStavke);
            psStavke.setInt(1, id);
            psStavke.executeUpdate();

            // Zatim brišemo narudžbinu
            String sqlNarudzbina = "DELETE FROM narudzbina WHERE ID = ?";
            psNarudzbina = connection.prepareStatement(sqlNarudzbina);
            psNarudzbina.setInt(1, id);
            psNarudzbina.executeUpdate();

            connection.commit(); // Ako su oba brisanja prošla, potvrđujemo transakciju

            response.sendRedirect("PrikazNarudzbina.jsp?success=true");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // U slučaju greške, poništavamo transakciju
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            response.sendRedirect("PrikazNarudzbina.jsp?error=true&message=" + e.getMessage());
        } finally {
            try {
                if (psStavke != null) psStavke.close();
                if (psNarudzbina != null) psNarudzbina.close();
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
