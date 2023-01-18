
package model;

import connectionFHIR.FHIR;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import connectionDB.ConnectionSingleton;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FindSkypeDB;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;
import view.PatientPage;

public class PatientPFromDocModel {

    private String cf;
    private String skypeid;
    private Patient patient;

    public PatientPFromDocModel(String cf) {
        this.cf = cf;
        FindSkypeDB fsdb = new FindSkypeDB();
        this.skypeid = fsdb.SkypeName(cf);

    }

    public String getSkypeid() {
        return skypeid;
    }

    public ArrayList<String> inizializeView() {
        Bundle responsePatient = FHIR.client.search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.exactly().identifier(cf))
                .returnBundle(Bundle.class)
                .execute();

        Patient p = (Patient) responsePatient.getEntry().get(0).getResource();
        this.patient = p;
        String telecom = p.getTelecomFirstRep().getValue();
        String city = p.getAddressFirstRep().getCity();
        String address;
        address = p.getAddressFirstRep().getLine().get(0).toString();
        String name = p.getName().get(0).getNameAsSingleString();
        ArrayList<String> l;
        l = new ArrayList<String>();
        l.add(telecom);
        l.add(city);
        l.add(name);
        l.add(address);
        return l;

    }

    public Patient getPatient() {
        return patient;
    }
}
