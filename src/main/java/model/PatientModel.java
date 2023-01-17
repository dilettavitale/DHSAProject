
package model;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import connectionFHIR.FHIR;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;

public class PatientModel extends UserModel {

    String namePatient = "";
    private PatientDB pdb;
    private Patient patient;

    public PatientModel(String cf, String password) {
        super(cf, password);
        pdb = new PatientDB();

    }
    
/**
 * This method returns the patient (application user) with this fiscal code and password.It uses the findUser method defined in the PatientDB class.
 * @param cf
 * @param password
 * @return pm
 * @throws Exception 
 */
    @Override
    public UserModel findUser(String cf, String password) throws Exception {
        PatientModel pm = (PatientModel) pdb.findUser(cf, password);
        return pm;
    }

    /**
     * This method searches the patient (in FHIR) with this fiscal code
     * @param cf
     * @return patient
     */
    public Patient getPatient(String cf) {
        FHIR conn = new FHIR();
        IGenericClient client = conn.FHIRConnection();
        Practitioner f = new Practitioner();
        Bundle response;
        response = client.search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.exactly().identifier(cf))
                .returnBundle(Bundle.class)
                .execute();

        for (Bundle.BundleEntryComponent c : response.getEntry()) {
            Patient p = (Patient) c.getResource();
            this.patient=p;
            
        }
      return patient;       

    }
    // fIND NAME AND SURNAME OF PATIENT THAT LOGGED IN
    public String FindNomePatient(Patient p) {
        namePatient = patient.getName().get(0).getNameAsSingleString();
    return namePatient ;
}

}
