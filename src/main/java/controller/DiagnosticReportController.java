/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DiagnosticReportModel;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Patient;
import view.DiagnosticReportView;
import view.PatientPage;

/**
 *
 * @author dilet
 */
class DiagnosticReportController {

    private Patient patient;
    private String dateS;
    private DateTimeType dtt;
    private DiagnosticReportModel drm;
    private DiagnosticReportView view= new DiagnosticReportView();
    private HashMap<String, String> exams;
    private String drLink;

    public DiagnosticReportController(Patient patient, String value) throws ParseException {
        this.patient = patient;
        this.dateS = value;
        this.dtt = createDateTimeType(value);
        this.drm = new DiagnosticReportModel(patient,value);
        this.exams = this.drm.getExams();
        setValues();
        String date = dateS.substring(0, 10);
        this.view.setDate(date);
        this.view.addJSONListener(new JSONListener());
        this.drLink = this.drm.getDrLink();
        this.view.setVisible(true);
        
        
        

    }
    
        public class JSONListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            

            String link1 = drLink;

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new java.net.URI(link1));
                    //desktop.browse(new java.net.URI(link2));
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

    private DateTimeType createDateTimeType(String value) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(this.dateS);
        DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setValue(date);
        return dateTimeType;

    }

    private void setValues() {
        this.view.setRBC(this.exams.get("RBC"));
        this.view.setWBC(this.exams.get("WBC"));
        this.view.setHGB(this.exams.get("HGB"));
        this.view.setHCT(this.exams.get("HCT"));
        this.view.setMCH(this.exams.get("MCH"));
        this.view.setMCHC(this.exams.get("MCHC"));
        this.view.setRDW(this.exams.get("RDW"));
        this.view.setPLTS(this.exams.get("PLTS"));
        this.view.setGRNE(this.exams.get("GRNE"));
        this.view.setGRBA(this.exams.get("GRBA"));
        this.view.setMCV(this.exams.get("MCV"));
        this.view.setLY(this.exams.get("LY"));
        this.view.setGREO(this.exams.get("GREO"));
        this.view.setMO(this.exams.get("MO"));
        
        
    }

}
