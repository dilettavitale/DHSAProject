
package modelfactory;

import model.GeneralModel;
import model.PatientModel;

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
