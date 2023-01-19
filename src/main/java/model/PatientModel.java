
package model;

import connectionFHIR.FHIR;
import ca.uhn.fhir.rest.client.api.IGenericClient;
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
    /**
     * the model for the maniultation of data of the patient
     * @param cf: fiscal code of the patient
     * @param password : password of the patient
     */

    public PatientModel(String cf, String password) {
        super(cf, password);
        pdb = new PatientDB();

    }
    
/**
 * This method returns the patient (application user) with this fiscal code and password.It uses the findUser method defined in the PatientDB class.
 * @param cf: fiscal code of the user
 * @param password: password of the user
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
     * @param cf: fiscal code of the patient
     * @return patient
     */
    public Patient getPatient(String cf) {
        Practitioner f = new Practitioner();
        Bundle response;
        response = FHIR.client.search()
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
    /**
     * this methods finds the name and the surname of the patient
     * @param p: patient 
     * @return 
     */
    public String FindNomePatient(Patient p) {
        namePatient = patient.getName().get(0).getNameAsSingleString();
    return namePatient ;
}

}
