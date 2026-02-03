/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Vanja
 */
public class Database {
      
     
       public static Connection getConnection() throws SQLException {
           Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/ketering?zeroDateTimeBehavior=CONVERT_TO_NULL", "admin", "admin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Greška prilikom uspostavljanja veze sa bazom podataka.");
        }
        
        return connection;
    }
}
