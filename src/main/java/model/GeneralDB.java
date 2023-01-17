/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

import connectionDB.ConnectionSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author immacolata
 */
public class GeneralDB implements userDB {

       
    private Connection conn;
    private static PreparedStatement pst;
    private static ResultSet rs;
    private static Statement st;

    public GeneralDB() {
      
    }

    @Override
    public UserModel findUser(String cf, String password) {
        try {
            conn =ConnectionSingleton.getInstance();
            if (!cf.equals("") && !password.equals("")) {
                String query = "SELECT * FROM `users` WHERE cf=? and password=?";
                System.out.println("sono qui");
                pst = conn.prepareStatement(query);
                pst.setString(1, cf);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String codf = rs.getString("cf");
                    String pass = rs.getString("password");
                    GeneralModel gm = new GeneralModel(codf, pass);
                    return gm;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
                try { rs.close(); } catch (SQLException e) { }
                try { pst.close(); } catch (SQLException e) { }
              //  try { conn.close(); } catch (SQLException e) { }
            }
    }
}
