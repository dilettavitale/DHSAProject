/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import model.UserModel;
import model.WelcomeDoctorModel;
import modelfactory.GeneralFactory;
import modelfactory.PatientFactory;
import modelfactory.UserFactory;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import view.DoctorWelcomePage;
import view.PatientPage;
import view.PatientView;

/**
 *
 * @author dilet
 */
public class WelcomeDoctorController {

    private DoctorWelcomePage view;
    private UserModel doctor;
    private IGenericClient client;
    private WelcomeDoctorModel wdm;
    private Practitioner practitioner;
    
    /**
     * The controller for the manipulation of data to show the list of patient of the practioner who logged into the account
     * @param view the page seen by the practitioner as soon as they log in
     * @param gp the GBModel of the practitioner who logged in
     */
    public WelcomeDoctorController(DoctorWelcomePage view, UserModel gp) {
        this.view = view;
        this.doctor = gp;
        this.wdm = new WelcomeDoctorModel(gp);
        String name = this.wdm.inizilizeName();
        this.view.setName(name);
        String array[] = this.wdm.inizializeJList();
        JListPatients jlp = new JListPatients(array);
        this.view.setJList(jlp);
        this.view.setVisible(true);
        this.practitioner = wdm.getP();

        this.view.addJListListener(new JMouseListListener());

    }

    public WelcomeDoctorModel getWdm() {
        return wdm;
    }

    public class JListPatients extends AbstractListModel {

        String[] strings;
        /**
         * This class is used to updates values in the view's JList
         * @param strings 
         */
        public JListPatients(String[] strings) {
            this.strings = strings;
        }

        @Override
        public int getSize() {
            return strings.length;
        }

        @Override
        public String getElementAt(int i) {
            return strings[i];
        }
    }

    public class JMouseListListener implements MouseListener {

        @Override
        /**
         * The method shows the data of the selected patient
         * @param e : event in which the user clicked on the name of the selected patient
         */
        public void mouseClicked(MouseEvent e) {
            String value = view.getListSelected();
            String splits[] = value.split("\\s+");
            int len = splits.length - 1;
            PatientPFromDocController pp = new PatientPFromDocController(splits[len], practitioner);

        }

        @Override
        public void mousePressed(MouseEvent e) {
            //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //To change body of generated methods, choose Tools | Templates.
        }

    }

}
