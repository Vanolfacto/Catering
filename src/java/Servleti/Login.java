/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servleti;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import Modeli.Admin;
import Modeli.Korisnik;
import Modeli.Manager;
/**
 *
 * @author Vanja
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          String username = request.getParameter("username");
        String lozinka = request.getParameter("lozinka");

        Admin admin = authenticateAdmin(username, lozinka);
        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userType", "admin");
            session.setAttribute("korisnikID", admin.getId());
            session.setAttribute("username", admin);
            response.sendRedirect("AdminDashboard.jsp");
            return;
        }
        
        // Prvo pokušaj autentifikaciju kao menadžer
        Manager manager = authenticateMenadzer(username, lozinka);
        if (manager != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userType", "manager");
            session.setAttribute("korisnikID", manager.getId());
            session.setAttribute("username", manager);
            response.sendRedirect("ManagerDashboard.jsp");
            return;
        }

        // Ako nije menadžer, pokušaj autentifikaciju kao korisnik
        Korisnik korisnik = authenticateKorisnik(username, lozinka);
        if (korisnik != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userType", "korisnik");
            session.setAttribute("korisnikID", korisnik.getId());
            session.setAttribute("username", korisnik);
            response.sendRedirect("home.jsp");
            return;
        } else {
            response.sendRedirect("Login.jsp?error=true");
        }
        
    }

    private Manager authenticateMenadzer(String username, String lozinka) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Manager manager = null;

        try {
            connection = Database.getConnection(); // Implementirati getConnection() prema vašim potrebama
            String sql = "SELECT * FROM manager WHERE UserName = ? AND Lozinka = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, lozinka);
            rs = ps.executeQuery();

            if (rs.next()) {
                manager = new Manager();
                manager.setId(rs.getInt("ID"));
                manager.setIme(rs.getString("Ime"));
                manager.setPrezime(rs.getString("Prezime"));
                manager.setUsername(rs.getString("UserName"));
                manager.setLozinka(rs.getString("Lozinka"));
                manager.setEmail(rs.getString("Email"));
                manager.setAdresa(rs.getString("Adresa"));
                manager.setBrojTelefona(rs.getInt("Broj_telefona"));
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

        return manager;
    }

    private Korisnik authenticateKorisnik(String username, String lozinka) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Korisnik korisnik = null;

        try {
            connection = Database.getConnection(); // Implementirati getConnection() prema vašim potrebama
            String sql = "SELECT * FROM korisnik WHERE UserName = ? AND Lozinka = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, lozinka);
            rs = ps.executeQuery();

            if (rs.next()) {
                korisnik = new Korisnik();
                korisnik.setId(rs.getInt("ID"));
                korisnik.setIme(rs.getString("Ime"));
                korisnik.setPrezime(rs.getString("Prezime"));
                korisnik.setUsername(rs.getString("UserName"));
                korisnik.setLozinka(rs.getString("Lozinka"));
                korisnik.setEmail(rs.getString("Email"));
                korisnik.setAdresa(rs.getString("Adresa"));
                korisnik.setBrojTelefona(rs.getInt("Broj_telefona"));
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

        return korisnik;

    }
    private Admin authenticateAdmin(String username, String lozinka) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Admin admin = null;

        try {
            connection = Database.getConnection(); // Implementirati getConnection() prema vašim potrebama
            String sql = "SELECT * FROM admin WHERE UserName = ? AND Lozinka = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, lozinka);
            rs = ps.executeQuery();

            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("ID"));
                admin.setIme(rs.getString("Ime"));
                admin.setPrezime(rs.getString("Prezime"));
                admin.setUsername(rs.getString("UserName"));
                admin.setLozinka(rs.getString("Lozinka"));
                admin.setEmail(rs.getString("Email"));
                admin.setAdresa(rs.getString("Adresa"));
                admin.setBrojTelefona(rs.getInt("Broj_telefona"));
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

        return admin;

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
