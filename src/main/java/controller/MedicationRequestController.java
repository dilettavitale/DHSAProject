/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import model.MedicationRequestModel;
import model.PatientModel;
import org.hl7.fhir.r4.model.Patient;
import view.MedicationRequestView;
import view.OneMedicationRequestView;

/**
 *
 * @author immacolata
 */
public class MedicationRequestController {

    private Patient patient;
    private MedicationRequestModel medReqM;
    private PatientModel model;
    private MedicationRequestView medReqView = new MedicationRequestView();

    public MedicationRequestController(Patient patient, PatientModel model) {
        this.patient = patient;
        this.model = model;
        this.medReqM = new MedicationRequestModel(patient, medReqView);
        ArrayList<MedicationRequestModel.RowTable> list = this.medReqM.listMedRequest(model);
        //String [][] table =this.medReqM.initializeView();
        this.medReqView.setTable(list);
        this.medReqView.setVisible(true);
        this.medReqView.addSelectionMouseListener(new MedicationRequestListener());

    }

    public class MedicationRequestListener extends MouseAdapter {

        public MedicationRequestListener() {

        }

        public void mouseClicked(MouseEvent me) {
            String col1 = "";
            String col2 = "";
            String col3="";
            String col4 = "";
            String col5 = "";
            col1 = medReqView.getcolNameValue();
            col2 = medReqView.getcolDateValue();
            col3=medReqView.getcolStatusValue();
            col4= medReqView.getcolLinkValue();
            col5= medReqView.getcolRequesterValue();
            if ((col1.length() != 0) && (col2.length() != 0)) {
                JOptionPane.showMessageDialog(null, "Contenuto riga selezionata: " + col1 + " " + col2);
                OneMedicationRequestView oneMedicationView = new OneMedicationRequestView();
                OneMedicationRequestController oneMedication = new OneMedicationRequestController(oneMedicationView, col1, col2,col3,col4,col5, model);
                oneMedicationView.setVisible(true);
                medReqView.setVisible(false);
            }

        }

    }

}
