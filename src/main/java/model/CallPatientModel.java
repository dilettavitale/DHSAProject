package model;

import connectionFHIR.FHIR;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;

public class CallPatientModel {

    private FindSkypeDB skypeDB;
    String cfGeneral = "";
    String nameSkype = "";
    String linkSkype = "";
    String link = "skype:";

    public CallPatientModel() {
        skypeDB = new FindSkypeDB();

    }

    public String findSkypeGeneral(String cfPatient) {
        Practitioner f = new Practitioner();
        // cerco il paziente con questo codice fiscale 
        Bundle response;
        response = FHIR.client.search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.exactly().identifier(cfPatient))
                .returnBundle(Bundle.class)
                .execute();

        for (Bundle.BundleEntryComponent c : response.getEntry()) {
            Patient p = (Patient) c.getResource();

            f = (Practitioner) p.getGeneralPractitionerFirstRep().getResource();
            cfGeneral = f.getIdentifierFirstRep().getValue();

            FindSkypeDB skype = new FindSkypeDB();
            nameSkype = skype.SkypeName(cfGeneral);

        }
        return nameSkype;

    }

    public String constructSkypeLink(String namSkype) {
        linkSkype = link + nameSkype + "?call";

        return linkSkype;

    }

}
