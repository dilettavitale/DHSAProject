package model;

/**
 *
 * @author immacolata
 */
public class GeneralModel extends UserModel {

    private GeneralDB mdb;

    /**
     * the model for the maniultation of data of the general practitioner
     *
     * @param cf: fiscal code of the patient
     * @param password : password of the patient
     */
    public GeneralModel(String cf, String password) {
        super(cf, password);
        mdb = new GeneralDB();
    }

    /**
     * This method returns the general practitioner (application user) with this fiscal code
     * and password.It uses the findUser method defined in the GeneralDB class.
     *
     * @param cf: fiscal code of the user
     * @param password: password of the user
     * @return pm
     * @throws Exception
     */
    @Override
    public UserModel findUser(String cf, String password) throws Exception {
        GeneralModel gm = (GeneralModel) mdb.findUser(cf, password);
        return gm;
    }
}
