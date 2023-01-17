
package model;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import connectionFHIR.FHIR;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;

public class OneMedicationRequestModel {

    public OneMedicationRequestModel() {
    }

    //TAKE NAMES OF PRESCRIPTION, SEARCH IT AND RETURN IT
    public String findMedRequest(String nameMedRequest, String cf) {
        String nameGP = "";
        FHIR conn = new FHIR();
        IGenericClient client = conn.FHIRConnection();
        Practitioner f = new Practitioner();
        Bundle response = client.search()
                .forResource(MedicationRequest.class)
                .where(MedicationRequest.SUBJECT.hasChainedProperty(Patient.IDENTIFIER.exactly().identifier(cf)))
                .returnBundle(Bundle.class)
                .execute();

        System.out.println(
                "Found " + response.getEntry().size() + " Ricette");

        return nameGP;
    }

}
