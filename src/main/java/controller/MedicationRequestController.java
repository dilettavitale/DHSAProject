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
    private MedicationRequestView medReqView = new MedicationRequestView();

    /**
     * The controller for the manipulation of data for the view of medication
     * requests made by the general practitioner
     *
     * @param patient: the patient who wants to see the medication requests
     */
    public MedicationRequestController(Patient patient) {
        this.patient = patient;
        this.medReqM = new MedicationRequestModel(patient);
        ArrayList<MedicationRequestModel.RowTable> list = this.medReqM.listMedRequest();
        this.medReqView.setTable(list);
        this.medReqView.setVisible(true);
        this.medReqView.addSelectionMouseListener(new MedicationRequestListener());

    }

    public class MedicationRequestListener extends MouseAdapter {

        public MedicationRequestListener() {

        }

        /**
         * The method shows the data of the selected prescription
         *
         * @param e : event in which the user clicked on a row of the table and
         * has chosen one medication request to be shown
         */

        public void mouseClicked(MouseEvent me) {
            String col1 = "";
            String col2 = "";
            String col3 = "";
            String col4 = "";
            String col5 = "";
            col1 = medReqView.getcolNameValue();
            col2 = medReqView.getcolDateValue();
            col3 = medReqView.getcolStatusValue();
            col4 = medReqView.getcolLinkValue();
            col5 = medReqView.getcolRequesterValue();
            if ((col1.length() != 0) && (col2.length() != 0)) {
                JOptionPane.showMessageDialog(null, "Contenuto riga selezionata: " + col1 + " " + col2);
                OneMedicationRequestView oneMedicationView = new OneMedicationRequestView();
                OneMedicationRequestController oneMedication = new OneMedicationRequestController(oneMedicationView, col1, col2, col3, col4, col5);
                oneMedicationView.setVisible(true);
                medReqView.setVisible(false);
            }

        }

    }

}
