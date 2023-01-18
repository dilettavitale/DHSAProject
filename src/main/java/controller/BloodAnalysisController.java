/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.DiagnosticReportController;
import controller.DiagnosticReportController;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.BloodAnalysisModel;
import org.hl7.fhir.r4.model.Patient;
import view.BloodAnalysisView;

/**
 *
 * @author dilet
 */
public class BloodAnalysisController {

    private Patient patient;
    private BloodAnalysisModel bam;
    private BloodAnalysisView bav = new BloodAnalysisView();
    /**
     * The controller for the manipulation of data for the view of the dates of the several analysis made by the chosen patient
     * @param patient: the patient of whom analysis dates will be shown
     */
    public BloodAnalysisController(Patient patient) {
        this.patient = patient;
        this.bam = new BloodAnalysisModel(patient);
        ArrayList<String> table = this.bam.listBloodAnalysis();
        this.bav.setTable(table);
        this.bav.addTableListener(new JMouseTableListener());
        this.bav.setVisible(true);

    }

    public class JMouseTableListener implements MouseListener {
        
        @Override
        /**
         * The method shows the data of the diagnostic report made on the selected day
         * @param e : event in which the user clicked on a row of the table and has chosen one analysis to be shown
         */
        public void mouseClicked(MouseEvent e) {
            String value = bav.getSelectedText();
            try {
                JOptionPane.showMessageDialog(null, "Contenuto riga selezionata: " + value);
                DiagnosticReportController drc = new DiagnosticReportController(patient, value);
                bav.setVisible(false);

            } catch (ParseException ex) {
                Logger.getLogger(BloodAnalysisController.class.getName()).log(Level.SEVERE, null, ex);
            }

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
