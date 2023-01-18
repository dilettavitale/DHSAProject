package model;

import connectionFHIR.FHIR;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Reference;
import view.MedicationRequestView;

public class MedicationRequestModel {

    private Patient patient;
    private List<MedicationRequest> med = new ArrayList();

    public MedicationRequestModel(Patient patient) {
        this.patient = patient;
    }

    public class RowTable {

        public String name;
        public String data;
        public String link;
        public String status;
        public String requester;

        public RowTable(String name, String data, String status, String link, String requester) {
            this.name = name;
            this.data = data;
            this.status = status;
            this.link = link;
            this.requester = requester;
        }

        public String getRequester() {
            return requester;
        }

        public void setRequester(String requester) {
            this.requester = requester;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String name) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public ArrayList listMedRequest(PatientModel model) {
        String nameGP = "";
        ArrayList<RowTable> list = new ArrayList();

        Practitioner f = new Practitioner();

        Bundle response = FHIR.client.search()
                .forResource(MedicationRequest.class)
                .where(MedicationRequest.SUBJECT.hasChainedProperty(Patient.IDENTIFIER.exactly().identifier(model.getCf())))
                .returnBundle(Bundle.class)
                .execute();

        for (BundleEntryComponent c : response.getEntry()) {
            MedicationRequest m = (MedicationRequest) c.getResource();
            String ref = m.getRequester().getReference();
            System.out.println(ref);
            String doctorID = ref.substring(13);
            Practitioner foundDoctor = FHIR.client.read()
                    .resource(Practitioner.class)
                    .withId(doctorID)
                    .execute();
            nameGP = foundDoctor.getNameFirstRep().getNameAsSingleString();

            RowTable r = new RowTable(m.getMedicationCodeableConcept().getCoding().get(0).getDisplay().toString(), m.getAuthoredOn().toString(), m.getStatus().toString(), m.getIdBase(), nameGP);

            list.add(r);

        }

        return list;
    }

}
