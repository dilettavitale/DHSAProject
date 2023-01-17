/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package modelfactory;

import model.GeneralModel;
import model.PatientModel;
import model.UserModel;

/**
 *
 * @author immacolata
 */
public abstract class UserFactory {
    
   
    public enum Role {GENERAL_PRACTITIONER,PATIENT};
    
    public static UserModel make( Role role, String cf, String password){
        UserFactory factory = null;
        if(role == Role.GENERAL_PRACTITIONER){
            factory = new GeneralFactory();
        }
        else{
            factory = new PatientFactory();
        }
        return factory.build(role, cf, password);
    }
    
    public UserModel build(Role role, String cf,String password) {
        UserModel v = selectRole(role);
        v.setUsername(cf);
        v.setPassword(password);
        System.out.println("v: " +v);
        return v;
    }
    
    // This is the "factory method"
    protected abstract UserModel selectRole(Role role);
    
    
}
