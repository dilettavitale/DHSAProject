/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import connectionFHIR.FHIR;
import controller.PatientPFromDocController;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import model.UserModel;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import view.DoctorWelcomePage;
import view.PatientPage;

/**
 *
 * @author dilet
 */
public class WelcomeDoctorModel {

    private DoctorWelcomePage view;
    private UserModel gp;
    private Practitioner p;

    public WelcomeDoctorModel(DoctorWelcomePage view, UserModel gp) {
        this.view = view;
        this.gp = gp;

        

        //inizializeView();
    }

    public Practitioner getP() {
        return p;
    }
    
    
    public String inizilizeName(){
        FHIR conn = new FHIR();
        IGenericClient client = conn.FHIRConnection();
        Bundle response = client.search()
                .forResource(Practitioner.class)
                .where(Practitioner.IDENTIFIER.exactly().identifier(gp.getCf()))
                .returnBundle(Bundle.class)
                .execute();
        Practitioner pr = (Practitioner) response.getEntry().get(0).getResource();
        this.p = pr;
        System.out.println("sono in welcome model");
        return pr.getName().get(0).getNameAsSingleString();
        
    }


    public String[] inizializeJList() {
        FHIR conn = new FHIR();
        IGenericClient client = conn.FHIRConnection();
        Bundle response = client.search()
                .forResource(Patient.class)
                .where(Patient.GENERAL_PRACTITIONER.hasChainedProperty(Organization.IDENTIFIER.exactly().identifier(gp.getCf())))
                .returnBundle(Bundle.class)
                .execute();
        System.out.println("sto facendo la jlist");

        ArrayList names = new ArrayList<String>();

        for (Bundle.BundleEntryComponent c : response.getEntry()) {
            Patient p = (Patient) c.getResource();
            names.add(p.getName().get(0).getNameAsSingleString() + " " + p.getIdentifierFirstRep().getValue());
            System.out.println(p.getId());
            System.out.println("\n");

        }
        String[] array = (String[]) names.toArray(new String[0]);
        //view.start(array);
        return array;

    }

}
