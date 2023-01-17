
package model;

public interface userDB {
    
    public abstract UserModel findUser(String cf, String password) throws Exception;
    
}
