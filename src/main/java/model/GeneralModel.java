/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author immacolata
 */
public class GeneralModel extends UserModel {

   
    private GeneralDB mdb;

    public GeneralModel(String cf, String password) {
        super(cf, password);
        mdb = new GeneralDB();
    }

    @Override
    public UserModel findUser(String cf, String password) throws Exception {
        GeneralModel gm = (GeneralModel) mdb.findUser(cf, password);
        System.out.println("HO CERCATO"+ gm);
        return gm;
    }
}
