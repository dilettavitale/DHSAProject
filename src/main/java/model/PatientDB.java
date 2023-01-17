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
public class PatientDB implements userDB {

       
    private Connection conn;
    private static PreparedStatement pst;
    private static ResultSet rs;
    private static Statement st;

    public PatientDB() {
      
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
                    PatientModel pm = new PatientModel(codf, pass);
                    return pm;
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

        /*
        try {
            String query = "SELECT * FROM `user` WHERE username=? and password=? and usertype=?";
            con = DriverManager.getConnection("jdbc:mysql://localhost/multiuserlogin", "root", "");
            pst = con.prepareStatement(query);
            pst.setString(1, txtuser.getText());
            pst.setString(2, txtpass.getText());
            pst.setString(3, String.valueOf(jComboBox1.getSelectedItem()));
            rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "username and password matched and you are logined as " + rs.getString("userType"));
                if (jComboBox1.getSelectedIndex() == 0) {
                    generalpractitioner gp = new generalpractitioner();
                    gp.setVisible(true);
                    this.setVisible(false);
                } else {
                    patient p = new patient();
                    p.setVisible(true);
                    this.setVisible(false);
                }
            } //username e password non corrispondono
            else {
                JOptionPane.showMessageDialog(this, "username and password do not matched ");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }*/
