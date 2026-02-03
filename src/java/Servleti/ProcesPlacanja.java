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
import java.sql.Statement;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Database.Database;
import Modeli.Proizvod;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Vanja
 */
@WebServlet(name = "ProcesPlacanja", urlPatterns = {"/ProcesPlacanja"})
public class ProcesPlacanja extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Do nothing or redirect to the payment page
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("korisnikID") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        int korisnikId = (int) session.getAttribute("korisnikID");
        String ime = request.getParameter("ime");
        String prezime = request.getParameter("prezime");
        String adresa = request.getParameter("adresa");
        String grad = request.getParameter("grad");
        String postanskiBroj = request.getParameter("postanskiBroj");
        String email = request.getParameter("email");
        String telefon = request.getParameter("telefon");
        String nacinPlacanja = request.getParameter("nacinPlacanja");

        // Inicijalizacija korpe proizvoda i količina
        List<Integer> proizvodi = (List<Integer>) session.getAttribute("korpaProizvodi");
        List<Integer> kolicine = (List<Integer>) session.getAttribute("korpaKolicine");

        System.out.println("Proizvodi u korpi: " + proizvodi);
        System.out.println("Kolicine u korpi: " + kolicine);

        if (proizvodi == null || proizvodi.isEmpty() || kolicine == null || kolicine.isEmpty()) {
            response.sendRedirect("GreskaPlacanja.jsp");
            return;
        }

        Connection connection = null;
        PreparedStatement psNarudzbina = null;
        PreparedStatement psStavkaNarudzbine = null;
        PreparedStatement psBrisanjeKorpe = null;
        try {
            connection = Database.getConnection();
            connection.setAutoCommit(false);
            String sqlNarudzbina = "INSERT INTO narudzbina (Korisnik_ID, Ime, Prezime, Adresa, Grad, PostanskiBroj, Email, Telefon, NacinPlacanja) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            psNarudzbina = connection.prepareStatement(sqlNarudzbina, Statement.RETURN_GENERATED_KEYS);
            psNarudzbina.setInt(1, korisnikId);
            psNarudzbina.setString(2, ime);
            psNarudzbina.setString(3, prezime);
            psNarudzbina.setString(4, adresa);
            psNarudzbina.setString(5, grad);
            psNarudzbina.setString(6, postanskiBroj);
            psNarudzbina.setString(7, email);
            psNarudzbina.setString(8, telefon);
            psNarudzbina.setString(9, nacinPlacanja);
            psNarudzbina.executeUpdate();

            ResultSet generatedKeys = psNarudzbina.getGeneratedKeys();
            int narudzbinaId = 0;
            if (generatedKeys.next()) {
                narudzbinaId = generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }

            String sqlStavkaNarudzbine = "INSERT INTO stavka_narudzbine (Narudzbina_ID, Proizvod_ID, NazivProizvoda, Kolicina, Cena) VALUES (?, ?, ?, ?, ?)";
            psStavkaNarudzbine = connection.prepareStatement(sqlStavkaNarudzbine);

            for (int i = 0; i < proizvodi.size(); i++) {
                int proizvodId = proizvodi.get(i);
                int kolicina = kolicine.get(i);

                Proizvod proizvod = getProductInfo(proizvodId);
                
                if (proizvod == null) {
                    throw new SQLException("Product not found for ID: " + proizvodId);
                }
                
                psStavkaNarudzbine.setInt(1, narudzbinaId);
                psStavkaNarudzbine.setInt(2, proizvodId);
                psStavkaNarudzbine.setString(3, proizvod.getNaziv());
                psStavkaNarudzbine.setInt(4, kolicina);
                psStavkaNarudzbine.setDouble(5, proizvod.getCena());
                psStavkaNarudzbine.addBatch();
            }

            psStavkaNarudzbine.executeBatch();

            String sqlBrisanjeKorpe = "DELETE FROM korpa WHERE ID_Korisnika = ?";
            psBrisanjeKorpe = connection.prepareStatement(sqlBrisanjeKorpe);
            psBrisanjeKorpe.setInt(1, korisnikId);
            psBrisanjeKorpe.executeUpdate();
            
            connection.commit();

            session.removeAttribute("korpaProizvodi");
            session.removeAttribute("korpaKolicine");

            response.sendRedirect("Potvrda.jsp");

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            response.sendRedirect("GreskaPlacanja.jsp");
        } finally {
            try {
                if (psNarudzbina != null) psNarudzbina.close();
                if (psStavkaNarudzbine != null) psStavkaNarudzbine.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Proizvod getProductInfo(int proizvodId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Proizvod proizvod = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT ID, Naziv, Tip, Cena, Opis, Slika FROM proizvod WHERE ID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, proizvodId);
            rs = ps.executeQuery();

            if (rs.next()) {
                proizvod = new Proizvod(
                        rs.getInt("id"),
                        rs.getString("naziv"),
                        rs.getString("tip"),
                        rs.getDouble("cena"),
                        rs.getString("opis"),
                        rs.getString("slika")
                );
            }
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

        return proizvod;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
