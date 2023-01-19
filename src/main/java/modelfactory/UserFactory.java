package modelfactory;

import model.GeneralModel;
import model.PatientModel;
import model.UserModel;

public abstract class UserFactory {

    public enum Role {
        GENERAL_PRACTITIONER, PATIENT
    };

    /**
     * This methods builds a user based on role
     *
     * @param role: role of the user
     * @param cf: fiscal code of the user
     * @param password: password of the user
     * @return user (PatientModel o GeneralModel)
     */
    public static UserModel make(Role role, String cf, String password) {
        UserFactory factory = null;
        if (role == Role.GENERAL_PRACTITIONER) {
            factory = new GeneralFactory();
        } else {
            factory = new PatientFactory();
        }
        return factory.build(role, cf, password);
    }
    public UserModel build(Role role, String cf, String password) {
        UserModel v = selectRole(role);
        v.setCf(cf);
        v.setPassword(password);
        System.out.println("v: " + v);
        return v;
    }

    /**
     * this is the factory method
     *
     * @param role
     * @return
     */
    protected abstract UserModel selectRole(Role role);
}
