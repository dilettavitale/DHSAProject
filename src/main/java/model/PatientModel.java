/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class PatientModel extends UserModel {

    String namePatient = "";
    private PatientDB pdb;
    private Patient patient;

    public PatientModel(String cf, String password) {
        super(cf, password);
        pdb = new PatientDB();

    }

    @Override
    public UserModel findUser(String cf, String password) throws Exception {
        PatientModel pm = (PatientModel) pdb.findUser(cf, password);
        System.out.println("HO CERCATO" + pm);
        return pm;
    }

    public Patient getPatient(String cf) {
        FHIR conn = new FHIR();
        IGenericClient client = conn.FHIRConnection();
        Practitioner f = new Practitioner();
        // cerco il paziente con questo codice fiscale 
        Bundle response;
        response = client.search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.exactly().identifier(cf))
                .returnBundle(Bundle.class)
                .execute();

        System.out.println("Found " + response.getEntry().size() + " with this cf'");
        for (Bundle.BundleEntryComponent c : response.getEntry()) {
            Patient p = (Patient) c.getResource();
            this.patient=p;
            
        }
      return patient;       

    }
    // prendo il cf del paziente che ha fatto accesso e trovo il suo nome e cognome in FHIR

    public String FindNomePatient(Patient p) {
        namePatient = patient.getName().get(0).getNameAsSingleString();
    return namePatient ;
}

}
