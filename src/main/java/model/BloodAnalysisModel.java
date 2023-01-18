package model;

import connectionFHIR.FHIR;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.util.ArrayList;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import view.BloodAnalysisView;

public class BloodAnalysisModel {

    private Patient patient;
    
    /**
     * This model manipulates data to show in BloodAnalysisView
     * @param patient selected patient
     */
    public BloodAnalysisModel(Patient patient) {
        this.patient = patient;
    }

    /**
     * This function searchs the dates of the diagnostic reports of the selected patient
     * @return the list of the dates
     */
    public ArrayList listBloodAnalysis() {
        ArrayList<String> list = new ArrayList();
        
        Practitioner f = new Practitioner();
        Bundle response = FHIR.client.search()
                .forResource(DiagnosticReport.class)
                .where(DiagnosticReport.SUBJECT.hasChainedProperty(Patient.IDENTIFIER.exactly().identifier(patient.getIdentifierFirstRep().getValue())))
                .returnBundle(Bundle.class)
                .execute();

        for (Bundle.BundleEntryComponent c : response.getEntry()) {
            DiagnosticReport dr = (DiagnosticReport) c.getResource();
            String date = null;
            DateTimeType dt = dr.getEffectiveDateTimeType();
            if (dr.getEffectiveDateTimeType() != null) {
                date = dr.getEffectiveDateTimeType().asStringValue();
            } else {
                date = "Date not found";
            }

            list.add(date);
        }

        return list;

    }
}
