/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PatientModel;
import view.OneMedicationRequestView;
import view.PatientPage;

/**
 *
 * @author immacolata
 */
public class OneMedicationRequestController {

    private final OneMedicationRequestView oneMedicationView;
    private final String nameMedRequest;
    private final String date;
    private final String status;
    private final String link;
    private final String practitioner;
    private final PatientModel p;

    public OneMedicationRequestController(OneMedicationRequestView oneMedicationView, String nameMedRequest, String date, String status, String link, String practitioner, PatientModel p) {
        this.oneMedicationView = oneMedicationView;
        this.nameMedRequest = nameMedRequest;
        this.date = date;
        this.status = status;
        this.link = link;
        this.practitioner = practitioner;
        this.p = p;
        this.oneMedicationView.setLabelname(nameMedRequest);
        this.oneMedicationView.setLabelDate(date);
        this.oneMedicationView.setLabelStatus(status);
        this.oneMedicationView.setLabelPractitioner(practitioner);
        this.oneMedicationView.addJSONListener(new OneMedicationRequestController.JSONListener());
    }

    public class JSONListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new java.net.URI(link));
                } catch (IOException exio) {
                    exio.printStackTrace();
                } catch (URISyntaxException ex) {
                    Logger.getLogger(OneMedicationRequestController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("Not Supported");
            }

        }
    }
}
