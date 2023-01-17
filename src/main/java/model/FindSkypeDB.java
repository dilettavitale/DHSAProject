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
public class FindSkypeDB{
    
   
    private Connection conn;
    private static PreparedStatement pst;
    private static ResultSet rs;
    private static Statement st;

    public FindSkypeDB (){
      
    }
    
    
    public String SkypeName(String cf){
                try {
            conn =ConnectionSingleton.getInstance();
            if (!cf.equals("")) {
                String query = "SELECT NomeSkype FROM `users` WHERE cf=?";
                pst = conn.prepareStatement(query);
                pst.setString(1, cf);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String NomeSkype = rs.getString("NomeSkype");
                    return NomeSkype;
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
