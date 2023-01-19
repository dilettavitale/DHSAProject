
package model;

import connectionFHIR.FHIR;
import ca.uhn.fhir.rest.client.api.IGenericClient;
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


public class WelcomeDoctorModel {
    private UserModel gp;
    private Practitioner p;
    /**
     * This class manipulates the data to show in the WelcomeDoctorView
     * @param gp 
     */
    public WelcomeDoctorModel(UserModel gp) {
        this.gp = gp;
    }

    public Practitioner getP() {
        return p;
    }
    
    /**
     * This method searchs for the name of the doctor who logged in 
     * @return the name of the doctor
     */
    public String inizilizeName(){
        Bundle response = FHIR.client.search()
                .forResource(Practitioner.class)
                .where(Practitioner.IDENTIFIER.exactly().identifier(gp.getCf()))
                .returnBundle(Bundle.class)
                .execute();
        Practitioner pr = (Practitioner) response.getEntry().get(0).getResource();
        this.p = pr;
        return pr.getName().get(0).getNameAsSingleString();
        
    }

    /**
     * This method searchs for the names and the fiscal codes of the doctor's patients
     * @return the list with names and fiscal codes
     */
    public String[] inizializeJList() {
        Bundle response = FHIR.client.search()
                .forResource(Patient.class)
                .where(Patient.GENERAL_PRACTITIONER.hasChainedProperty(Organization.IDENTIFIER.exactly().identifier(gp.getCf())))
                .returnBundle(Bundle.class)
                .execute();

        ArrayList names = new ArrayList<String>();

        for (Bundle.BundleEntryComponent c : response.getEntry()) {
            Patient p = (Patient) c.getResource();
            names.add(p.getName().get(0).getNameAsSingleString() + " " + p.getIdentifierFirstRep().getValue());

        }
        String[] array = (String[]) names.toArray(new String[0]);
        return array;
    }
}
