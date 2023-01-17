/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.PatientModel;
import model.UserModel;
import modelfactory.GeneralFactory;
import modelfactory.PatientFactory;
import modelfactory.UserFactory;
import view.DoctorWelcomePage;
import view.Login;
import view.PatientView;

/**
 *
 * @author immacolata
 */
public class LogController {

    private final Login view;

    public LogController(Login view) {
        this.view = view;
        this.view.addLoginListener(new LoginListener());
        view.setVisible(true);
    }

    public class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cf = "";
            String password = "";
            String role = "";
            try {
                cf = view.getCf();

                password = view.getPassowrd();

                role = view.getRole();

                switch (role) {
                    case "General practitioner": {

                        UserFactory generalp = new GeneralFactory();
                        UserModel gp = generalp.build(UserFactory.Role.GENERAL_PRACTITIONER, cf, password);

                        gp = gp.findUser(gp.getCf(), gp.getPassword());
                        if (gp == null) {
                            view.displayErrorMessage("Username or password not matched");

                        } else {
                            /*RICORDATI CHE POI DEVI ISTANZIARE ANCHE IL CONTROLLER*/
                            DoctorWelcomePage general = new DoctorWelcomePage();
                            WelcomeDoctorController generalController = new WelcomeDoctorController(general, gp);
                            general.setVisible(true);
                            view.setVisible(false);
                        }
                        break;
                    }
                    case "Patient": {

                        UserFactory patientFactory = new PatientFactory();
                        UserModel patient = patientFactory.build(UserFactory.Role.PATIENT, cf, password);

                        patient = patient.findUser(patient.getCf(), patient.getPassword());
                        if (patient == null) {
                            view.displayErrorMessage("Username or password not matched");
                        } else {
                            PatientView pv = new PatientView();
                            PatientController controllerHome = new PatientController(pv, (PatientModel) patient);
                            pv.setVisible(true);
                            view.setVisible(false);
                        }
                        break;
                    }

                    default:
                        break;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                view.displayErrorMessage(ex.getMessage());
            }
        }
    }

}
