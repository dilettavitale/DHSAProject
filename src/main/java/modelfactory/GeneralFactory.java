/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package modelfactory;

import model.GeneralModel;
import model.UserModel;

/**
 *
 * @author immacolata
 */
public class GeneralFactory extends UserFactory {
    @Override
    protected GeneralModel selectRole(Role role) {
        if (role==null) return null; else switch (role) {
            case GENERAL_PRACTITIONER:
                return new GeneralModel("","");
            default:
                return null;
        }
    }


}
