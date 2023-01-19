/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *This class is used to create the DB connection once
 * @author immacolata
 */
public class ConnectionSingleton {
    
    private static Connection instance = null;

    public synchronized static Connection getInstance() {
        try {
            if (instance== null || instance.isClosed()) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    instance = DriverManager.getConnection("jdbc:mysql://localhost/digitalhealth", "root", ""); //database name,"db username","password"
                } catch (ClassNotFoundException | SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instance;
    }
    
    // private constructor
    private ConnectionSingleton() {}
    
}
