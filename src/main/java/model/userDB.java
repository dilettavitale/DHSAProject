/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 *
 * @author immacolata
 */
public interface userDB {
    
    public abstract UserModel findUser(String cf, String password) throws Exception;
    
}
