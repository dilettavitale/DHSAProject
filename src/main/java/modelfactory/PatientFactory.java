/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package modelfactory;

import model.GeneralModel;
import model.PatientModel;

/**
 *
 * @author immacolata
 */
public class PatientFactory extends UserFactory {

    @Override
    protected PatientModel selectRole(UserFactory.Role role) {
        if (role==null) return null; else switch (role) {
            case PATIENT:
                return new PatientModel("","");
            default:
                return null;
        }
    }
    
}
