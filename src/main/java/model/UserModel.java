/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author immacolata
 */
public abstract class UserModel implements Serializable {
    
    private String cf;
    private String password;

    public UserModel(String cf, String password) {
        this.cf = cf;
        this.password = password;
    }

    public String getCf() {
        return cf;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.cf = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract UserModel findUser(String username, String password) throws Exception;

    @Override
    public String toString() {
        return "cf=" + cf + ", password=" + password ;
    }
    
}
