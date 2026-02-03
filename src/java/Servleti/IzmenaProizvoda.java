/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servleti;

import Database.Database;
import Modeli.Proizvod;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
/**
 *
 * @author Vanja
 */
@WebServlet(name = "IzmenaProizvoda", urlPatterns = {"/IzmenaProizvoda"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2 MB
                 maxFileSize = 1024 * 1024 * 10,      // 10 MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50 MB
public class IzmenaProizvoda extends HttpServlet {

 
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userType = (String) session.getAttribute("userType");
        if (userType == null || !userType.equals("manager") || !userType.equals("admin")) {
            response.sendRedirect("Login.jsp?error=unauthorized");
            return;
        }

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM proizvod");
             ResultSet rs = ps.executeQuery()) {

            request.setAttribute("proizvodi", rs);
            request.getRequestDispatcher("IzmenaProizvoda.jsp").forward(request, response);

        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendRedirect("IzmenaProizvoda.jsp?error=dberror");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                dodajProizvod(request, response);
                break;
            case "edit":
                editProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            default:
                response.sendRedirect("IzmenaProizvoda.jsp");
                break;
        }
    }

    private void dodajProizvod(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String naziv = request.getParameter("naziv");
        String tip = request.getParameter("tip");
        double cena = Double.parseDouble(request.getParameter("cena"));
        String opis = request.getParameter("opis");
        InputStream slikaInputStream = request.getPart("slika").getInputStream();

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO proizvod (Naziv, Tip, Cena, Opis, Slika) VALUES (?, ?, ?, ?, ?)")) {

            ps.setString(1, naziv);
            ps.setString(2, tip);
            ps.setDouble(3, cena);
            ps.setString(4, opis);
            ps.setBinaryStream(5, slikaInputStream);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.sendRedirect("IzmenaProizvoda");
            } else {
                response.sendRedirect("IzmenaProizvoda.jsp?error=adderror");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendRedirect("IzmenaProizvoda.jsp?error=dberror");
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    int id = Integer.parseInt(request.getParameter("id"));
    String naziv = request.getParameter("naziv");
    String tip = request.getParameter("tip");
    String cenaStr = request.getParameter("cena");
    String opis = request.getParameter("opis");
    
   
    
    double cena;
    try {
        cena = Double.parseDouble(cenaStr.trim());
    } catch (NumberFormatException e) {
        response.sendRedirect("IzmenaProizvoda.jsp?error=true");
        return;
    }
    // Parsiranje cena kao double
    
    
    // Dobavljanje slike ako je dostupna
    Part filePart = request.getPart("slika");
    InputStream inputStream = null;
    if (filePart != null && filePart.getSize() > 0) {
        inputStream = filePart.getInputStream();
    }
    
    Connection connection = null;
    PreparedStatement ps = null;
    
    try {
        connection = Database.getConnection();
        String sql;
        if (inputStream != null) {
            // Ako je dostupna nova slika, ažuriraj i sliku
            sql = "UPDATE proizvod SET Naziv=?, Tip=?, Cena=?, Opis=?, Slika=? WHERE ID=?";
            ps = connection.prepareStatement(sql);
            ps.setBlob(5, inputStream);
        } else {
            // Ako nije dostupna nova slika, ažuriraj bez slike
            sql = "UPDATE proizvod SET Naziv=?, Tip=?, Cena=?, Opis=? WHERE ID=?";
            ps = connection.prepareStatement(sql);
        }
        
        // Postavljanje parametara za upit
        ps.setString(1, naziv);
        ps.setString(2, tip);
        ps.setDouble(3, cena);
        ps.setString(4, opis);
        ps.setInt(5, id);
        
        // Izvršavanje upita za ažuriranje
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            response.sendRedirect("IzmenaProizvoda.jsp");
        } else {
            response.sendRedirect("IzmenaProizvoda.jsp?error=true");
        }
        
    } catch (SQLException ex) {
        ex.printStackTrace();
        response.sendRedirect("IzmenaProizvoda.jsp?error=true");
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM proizvod WHERE ID=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
            response.sendRedirect("IzmenaProizvoda.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("IzmenaProizvoda.jsp?error=true");
        }
    }

  

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
