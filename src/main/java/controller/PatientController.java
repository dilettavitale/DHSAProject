/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controller;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import connectionFHIR.FHIR;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import model.CallPatientModel;
import model.FindSkypeDB;
import model.PatientModel;
import model.ReadBloodCountPDF;
import model.UserModel;
import modelfactory.GeneralFactory;
import modelfactory.PatientFactory;
import modelfactory.UserFactory;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import view.PatientView;

/**
 *
 * @author immacolata
 */
public class PatientController {

    private final PatientView view;
    private final PatientModel model;
    private Patient pt;

    public PatientController(PatientView view, PatientModel model) {
        this.view = view;
        this.model = model;

        this.view.addCallListener(new CallListener());
        this.view.addLoadBloodAnalysisListener(new BloodAnalysisListener());
        this.view.setLabelcf(model.getCf());
        this.pt = model.getPatient(model.getCf());
        this.view.setLabelname(model.FindNomePatient(pt));
        this.view.addMedicationRequestListener(new MedicationRequestListener(pt));
        view.setVisible(true);

    }

    public class CallListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String linkGP = "";

            String cf = "";
            String cfGeneral = "";
            String nameSkype = "";
            try {
                cf = model.getCf();
                CallPatientModel call = new CallPatientModel();
                cfGeneral = call.findSkypeGeneral(cf);
                linkGP = call.constructSkypeLink(nameSkype);

                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new java.net.URI(linkGP));
                    } catch (IOException exio) {
                        exio.printStackTrace();
                    } catch (java.net.URISyntaxException exuri) {
                        exuri.printStackTrace();
                    }

                } else {
                    System.out.println("Not Supported");
                }

            } catch (Exception ex) {

                view.displayErrorMessage(ex.getMessage());
            }
        }
    }

    public class BloodAnalysisListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                String filename = f.getAbsolutePath();

                ReadBloodCountPDF bloodCount = new ReadBloodCountPDF(f);
                bloodCount.extractInformation(pt);

            } catch (Exception ex) {

                view.displayErrorMessage(ex.getMessage());
            }
        }
    }

    public class MedicationRequestListener implements ActionListener {

        private Patient patient;

        public MedicationRequestListener(Patient patient) {
            this.patient = patient;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                MedicationRequestController bav = new MedicationRequestController(this.patient, model);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                view.displayErrorMessage(ex.getMessage());
            }
        }
    }

}
