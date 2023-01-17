/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.mycompany.digitalhealth_project;

import controller.LogController;
import view.Login;

/**
 *
 * @author immacolata
 */
public class Digitalhealth_project {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Login login = new Login();
        LogController contr = new LogController(login);
    }
}
