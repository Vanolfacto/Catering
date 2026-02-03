/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeli;


import Modeli.Proizvod;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.Database;
/**
 *
 * @author Vanja
 */
public class ProductUtil {
     public static Proizvod getProductInfo(int proizvodId) {
        Proizvod proizvod = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = Database.getConnection();
            String sql = "SELECT * FROM proizvod WHERE ID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, proizvodId);
            rs = ps.executeQuery();

            if (rs.next()) {
                proizvod = new Proizvod();
                proizvod.setId(rs.getInt("ID"));
                proizvod.setNaziv(rs.getString("Naziv"));
                proizvod.setCena(rs.getDouble("Cena"));
                // Dodajte ostale atribute proizvoda po potrebi
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
}
