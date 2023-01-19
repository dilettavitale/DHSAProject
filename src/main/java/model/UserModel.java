package model;

import java.io.Serializable;

/**
 *
 * @author immacolata
 */
public abstract class UserModel implements Serializable {

    private String cf;
    private String password;

    /**
     * this class defines the user of the application
     *
     * @param cf: fiscal code of the user
     * @param password: password of the user
     */
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

    public void setCf(String cf) {
        this.cf = cf;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract UserModel findUser(String cf, String password) throws Exception;

    @Override
    public String toString() {
        return "cf=" + cf + ", password=" + password;
    }

}
