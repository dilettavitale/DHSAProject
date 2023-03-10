package model;

import connectionFHIR.FHIR;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Reference;
import view.DiagnosticReportView;


public class DiagnosticReportModel {

    private Patient patient;
    private String dtt;
    private HashMap<String, String> exams = new HashMap<>();
    private String drLink;
    /**
     * This model manipulates the data to show in the DiagnosticReportView
     * @param patient the selected patient
     * @param dtt the selected date
     */
    public DiagnosticReportModel(Patient patient, String dtt) {
        this.patient = patient;
        this.dtt = dtt;
        this.drLink = search();

    }

    public String getDrLink() {
        return drLink;
    }

    public HashMap<String, String> getExams() {
        return exams;
    }
    
    /**
     * This method searchs the DiagnosticReport made in the selected date and creates a hashmap saving this values
     * @return the JSON link to the DiagnosticReport
     */
    public String search() {
        Bundle response = FHIR.client.search()
                .forResource(DiagnosticReport.class)
                .where(DiagnosticReport.SUBJECT.hasChainedProperty(Patient.IDENTIFIER.exactly().identifier(patient.getIdentifierFirstRep().getValue())))
                .and(DiagnosticReport.DATE.exactly().day(dtt))
                .returnBundle(Bundle.class)
                .execute();

        Bundle.BundleEntryComponent c = response.getEntry().get(0);
        DiagnosticReport dr = (DiagnosticReport) c.getResource();
        for (Reference rs : dr.getResult()) {
            Observation o = (Observation) rs.getResource();
            String analysis = o.getCode().getCoding().get(0).getDisplay();
            BigDecimal bd;
            bd = o.getValueQuantity().getValue();
            String value = bd.toString();
            exams.put(analysis, value);

        }
        return dr.getId();

    }
}
