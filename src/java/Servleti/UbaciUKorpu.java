/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servleti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Database.Database;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vanja
 */
@WebServlet(name = "UbaciUKorpu", urlPatterns = {"/UbaciUKorpu"})
public class UbaciUKorpu extends HttpServlet {



   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession(false);
        

        int korisnikId = (int) session.getAttribute("korisnikID");
        int proizvodId = Integer.parseInt(request.getParameter("id"));
        int kolicina = Integer.parseInt(request.getParameter("kolicina"));

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

         try {
            connection = Database.getConnection();

            // Dobijanje cene proizvoda
            String sql = "SELECT Cena FROM proizvod WHERE ID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, proizvodId);
            rs = ps.executeQuery();

            if (rs.next()) {
                double cenaProizvoda = rs.getDouble("Cena"); // Promenjeno na "Cena" umesto "cena"
                double ukupnaCena = cenaProizvoda * kolicina;

                // Ubacivanje proizvoda u korpu u bazi podataka
                sql = "INSERT INTO korpa (ID_Korisnika, ID_Proizvoda, kolicina, Cena_Proizvoda, Ukupna_Cena) VALUES (?, ?, ?, ?, ?)";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, korisnikId);
                ps.setInt(2, proizvodId);
                ps.setInt(3, kolicina);
                ps.setDouble(4, cenaProizvoda);
                ps.setDouble(5, ukupnaCena);
                ps.executeUpdate();

                // Postavljanje poruke o uspehu
                session.setAttribute("message", "Proizvod uspešno dodat u korpu.");

                // Upravljanje korpom u sesiji
                List<Integer> proizvodi = (List<Integer>) session.getAttribute("korpaProizvodi");
                List<Integer> kolicine = (List<Integer>) session.getAttribute("korpaKolicine");

                if (proizvodi == null) {
                    proizvodi = new ArrayList<>();
                    session.setAttribute("korpaProizvodi", proizvodi);
                }

                if (kolicine == null) {
                    kolicine = new ArrayList<>();
                    session.setAttribute("korpaKolicine", kolicine);
                }

                if (proizvodi.contains(proizvodId)) {
                    int index = proizvodi.indexOf(proizvodId);
                    kolicine.set(index, kolicine.get(index) + kolicina); // Ako je već u korpi, dodaj količinu
                } else {
                    proizvodi.add(proizvodId);
                    kolicine.add(kolicina);
                }

                // Ispisivanje za debugging
                System.out.println("Proizvodi: " + proizvodi);
                System.out.println("Kolicine: " + kolicine);
            }

            response.sendRedirect("Meni.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
