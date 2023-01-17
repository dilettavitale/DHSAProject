/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import connectionFHIR.FHIR;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;

/**
 *
 * @author immacolata
 */
public class CallPatientModel {

    private FindSkypeDB skypeDB;
    String cfGeneral = "";
    String nameSkype = "";
    String linkSkype="";
    String link = "skype:";


    public CallPatientModel() {
        skypeDB = new FindSkypeDB();


    }

    public String findSkypeGeneral(String cfPatient) {
        FHIR conn = new FHIR();
        IGenericClient client = conn.FHIRConnection();
        Practitioner f = new Practitioner();
        // cerco il paziente con questo codice fiscale 
        Bundle response;
        response = client.search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.exactly().identifier(cfPatient))
                .returnBundle(Bundle.class)
                .execute();

        System.out.println("Found " + response.getEntry().size() + " with this cf'");
        for (Bundle.BundleEntryComponent c : response.getEntry()) {
            Patient p = (Patient) c.getResource();
            System.out.println("Name= " + p.getName().get(0).getNameAsSingleString());
            f = (Practitioner) p.getGeneralPractitionerFirstRep().getResource();
            cfGeneral = f.getIdentifierFirstRep().getValue();
            System.out.println("codice fiscale medico " + cfGeneral);
            FindSkypeDB skype = new FindSkypeDB();
            nameSkype = skype.SkypeName(cfGeneral);
            System.out.println("Skype del mio medico " + nameSkype);

        }
        return nameSkype;

    }
    public String constructSkypeLink(String namSkype){
        linkSkype=link+nameSkype+"?call"; 
        
        return linkSkype;
        
    }

}
