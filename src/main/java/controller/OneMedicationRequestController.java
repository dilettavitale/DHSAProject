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

    /**
     * The controller for the manipulation of data for the view of the one
     * medication request
     *
     * @param oneMedicationView:the page that shows a medication request
     * @param nameMedRequest:prescription name
     * @param date:date of issue of the medical prescription
     * @param status:prescription status
     * @param link: link FHIR
     * @param practitioner: the name of the general practitioner
     */
    public OneMedicationRequestController(OneMedicationRequestView oneMedicationView, String nameMedRequest, String date, String status, String link, String practitioner) {
        this.oneMedicationView = oneMedicationView;
        this.nameMedRequest = nameMedRequest;
        this.date = date;
        this.status = status;
        this.link = link;
        this.practitioner = practitioner;
        this.oneMedicationView.setLabelname(nameMedRequest);
        this.oneMedicationView.setLabelDate(date);
        this.oneMedicationView.setLabelStatus(status);
        this.oneMedicationView.setLabelPractitioner(practitioner);
        this.oneMedicationView.addJSONListener(new OneMedicationRequestController.JSONListener());
    }

    /**
     * The method shows the JSON format of the medication request when the user
     * clicks on the button
     *
     * @param e: the click on the button
     */
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
