
package modelfactory;

import model.GeneralModel;
import model.UserModel;

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
