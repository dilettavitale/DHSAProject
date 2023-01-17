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

    public BloodAnalysisController(Patient patient) {
        this.patient = patient;
        this.bam = new BloodAnalysisModel(patient, bav);
        ArrayList<String> table = this.bam.listBloodAnalysis();
        this.bav.setTable(table);
        this.bav.addTableListener(new JMouseTableListener());
        this.bav.setVisible(true);

    }

    public class JMouseTableListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            String value = bav.getSelectedText();
            try {
                DiagnosticReportController drc = new DiagnosticReportController(patient, value);
                //PatientPFromDocController pp = new PatientPFromDocController(splits[len],practitioner);
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
