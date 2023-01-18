/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import model.FindSkypeDB;
import model.PatientPFromDocModel;
import model.ReadBloodCountPDF;
import model.ReadMedicationRequestPDF;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import view.PatientPage;

/**
 *
 * @author dilet
 */
public class PatientPFromDocController {

    private String cf;
    private PatientPFromDocModel ppfdm;
    private PatientPage page = new PatientPage();
    private String skypeid;
    private Patient patient;
    private Practitioner pr;
    /**
     * The controller for the manipulation of data on the patient page requested by the practitioner
     * @param cf the patient's fiscal code
     * @param pr the practitioner who is logged into the account
     */
    public PatientPFromDocController(String cf, Practitioner pr) {
        this.cf = cf;
        this.pr = pr;
        this.ppfdm = new PatientPFromDocModel(cf);
        this.skypeid = ppfdm.getSkypeid();

        ArrayList<String> l = this.ppfdm.inizializeView();
        this.page.setFiscalCode(cf);
        this.page.setPhone(l.get(0));
        this.page.setCity(l.get(1));
        this.page.setName(l.get(2));
        this.page.setAddress(l.get(3));
        this.patient = this.ppfdm.getPatient();
        this.page.setVisible(true);
        this.page.addCallListener(new CallListener());
        this.page.addBloodAnalysisListener(new BloodAnalysisListener(this.patient));
        this.page.addLoadMedicationRequestListener(new MedicalRequestListener());
    }

    public class CallListener implements ActionListener {

        @Override
        /**
         * The method makes the doctor call the patient
         * @param e : event in which the user clicked on the button to start the call
         */
        public void actionPerformed(ActionEvent e) {

            String link1 = "skype:" + skypeid + "?call";

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new java.net.URI(link1));

                } catch (IOException exio) {
                    exio.printStackTrace();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(PatientPage.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("Not Supported");
            }

        }
    }

    public class MedicalRequestListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                String filename = f.getAbsolutePath();
                //filePath.setText(filename);
                ReadMedicationRequestPDF medRequest = new ReadMedicationRequestPDF(f);
                medRequest.extractInformation(patient, pr);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                page.displayErrorMessage(ex.getMessage());
            }
        }
    }

    public class BloodAnalysisListener implements ActionListener {

        private Patient patient;

        public BloodAnalysisListener(Patient patient) {
            this.patient = patient;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            BloodAnalysisController bav = new BloodAnalysisController(this.patient);

        }
    }

}
