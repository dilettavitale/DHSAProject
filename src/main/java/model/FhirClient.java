package model;

import connectionFHIR.FHIR;
import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Patient;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.MedicationRequest.MedicationRequestStatus;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;

public class FhirClient {

    private final HashMap<String, String> medreqinenglish = new HashMap<>();
    Patient patient = new Patient();
    MedicationRequest mr = new MedicationRequest();
    DiagnosticReport dr = new DiagnosticReport();
    HashMap<String, String> bloodcountsnomedID = new HashMap<>();
    HashMap<String, String> medreqsnomedID = new HashMap<>();
    List<String> commonUnit = Arrays.asList("%", "x10^3/µL", "x10^6/µL", "fL", "g/dL", "pg", "x10^6/µL", "dL", "mL", "pL");

    public FhirClient() {
        create_bloodcount_SnomedDictionary();     //INITIALIZE AND POPULATE USEFUL DICTIONARY
        create_medreq_SnomedDictionary();
        create_medreq_inenglish();
    }

    private void create_bloodcount_SnomedDictionary() {
        bloodcountsnomedID.put("Medico", "309341008");
        bloodcountsnomedID.put("Codice Laboratorio", "246370007");
        bloodcountsnomedID.put("Esame emocromocitometrico", "88308000");
        bloodcountsnomedID.put("RBC", " 14089001");
        bloodcountsnomedID.put("WBC", "767002");
        bloodcountsnomedID.put("HGB", " 38082009");
        bloodcountsnomedID.put("HCT", "28317006");
        bloodcountsnomedID.put("MCV", "104133003");
        bloodcountsnomedID.put("MCH", "735148007");
        bloodcountsnomedID.put("MCHC", " 37254006");
        bloodcountsnomedID.put("RDW", "66842004");
        bloodcountsnomedID.put("PLTS", "104086003");
        bloodcountsnomedID.put("GRNE", "271035003");
        bloodcountsnomedID.put("GREO", "310540006");
        bloodcountsnomedID.put("GRBA", "271038001");
        bloodcountsnomedID.put("LY", "271036002");
        bloodcountsnomedID.put("MO", "271037006");
    }

    private void create_medreq_SnomedDictionary() {
        medreqsnomedID.put("AMOXICILLINA", "27658006");
        medreqsnomedID.put("ACIDO FOLICO", "6247001");
        medreqsnomedID.put("KETOPROFENE", "10099000");
    }

    private void create_medreq_inenglish() {  //FASTEST WAY TO TRANSLATE ITALIAN DRUGS IN ENGLISH
        medreqinenglish.put("AMOXICILLINA", "AMOXICILLIN");
        medreqinenglish.put("ACIDO FOLICO", "FOLIC ACID");
        medreqinenglish.put("KETOPROFENE", "KETOPROFEN");
    }

    public void print_bloodcount_SnomedDictionary() {
        System.out.println(bloodcountsnomedID);
    }

    public void set_bloodcount_SnomedID(String key, String value) {
        bloodcountsnomedID.put(key, value);
    }

    public HashMap<String, String> get_bloodcount_SnomedID() {
        return bloodcountsnomedID;
    }

    public void set_medprescription_OnFhir(String drug, String dose, Date date, Patient p, Practitioner pr) {
        for (Map.Entry<String, String> entry : medreqsnomedID.entrySet()) {
            if (entry.getKey().equals(drug)) {
                String medEnglish = medreqinenglish.get(entry.getKey());
                mr.getMedicationCodeableConcept().addCoding().setCode(entry.getValue()).setSystem("http://snomed.info/sct").setDisplay(medEnglish);
                mr.setStatus(MedicationRequestStatus.ACTIVE);
                mr.setAuthoredOn(date);
                mr.getSubject().setResource(p);
                mr.getRequester().setResource(pr);
                Dosage dos = new Dosage();
                Quantity doseQ = new Quantity();
                System.out.println(dose);
                String doseN = dose.substring(0, dose.length() - 2);
                String unit = dose.substring(dose.length() - 2).toLowerCase();
                BigDecimal bd = new BigDecimal(doseN);
                doseQ.setValue(bd);
                doseQ.setUnit(unit);
                Dosage.DosageDoseAndRateComponent doserate = new Dosage.DosageDoseAndRateComponent();
                doserate.setDose(doseQ);
                List<Dosage.DosageDoseAndRateComponent> l = new ArrayList();
                l.add(doserate);
                dos.setDoseAndRate(l);
                mr.addDosageInstruction(dos);
                MethodOutcome outcomeMR = FHIR.client.create()
                        .resource(mr)
                        .prettyPrint()
                        .encodedJson()
                        .execute();

                this.mr = new MedicationRequest();
            }
        }

    }

    public void set_bloodCount_onFhir(HashMap<String, String> bloodcount, Patient p) throws ParseException {
        Organization lab = new Organization();
        lab.setId(bloodcount.get("CODICE LABORATORIO"));
        lab.setName("Lab" + bloodcount.get("CODICE LABORATORIO"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(bloodcount.get("DATA"));
        DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setValue(date);
        dr.setEffective(dateTimeType);
        Reference patient = new Reference(p);
        dr.setSubject(patient);
        for (Map.Entry<String, String> entryvalueofblood : bloodcount.entrySet()) {
            if (!isInvalidKey(entryvalueofblood.getKey())) {
                Observation obs = new Observation();
                String key = entryvalueofblood.getKey();
                String value = entryvalueofblood.getValue();
                String snomedCode = bloodcountsnomedID.get(key);

                Coding coding = obs.getCode().addCoding();
                coding.setCode(snomedCode);
                coding.setSystem("https://www.snomed.org/");
                coding.setDisplay(key);

                Quantity quantityValue = new Quantity();
                String unit = getUnitFromString(value, commonUnit);
                String valueNoSpace = value.replaceAll("\\s+", "");
                System.out.println(valueNoSpace);
                int index = 0;
                for (int i = 0; i < valueNoSpace.length(); i++) {
                    if (!Character.isDigit(valueNoSpace.charAt(i)) && valueNoSpace.charAt(i) != '.') {
                        index = i;
                        break;

                    }

                }
                quantityValue.setValue(new BigDecimal(valueNoSpace.substring(0, index)));
                quantityValue.setSystem("http://unitsofmeasure.org");

                quantityValue.setUnit(unit);
                obs.setValue(quantityValue);
                Reference observation = new Reference(obs);
                dr.addResult(observation);
            }
        }
        MethodOutcome outcomeVR = FHIR.client.create()  //CREATE A RESOURCE OUTCOME WITH DIAGNOSTIC REPORT INSIDE IT
                .resource(dr)
                .prettyPrint()
                .encodedJson()
                .execute();

    }

    private boolean isInvalidKey(String key) {        // IF KEYS ARE THIS STRING RETURN TRUE (THIS STRING IS NOT USEFUL IN THIS ISTANCE)
        return key.equals("CODICE FISCALE") || key.equals("MEDICO") || key.equals("CODICE LABORATORIO") || key.equals("EMOCROMOCITOMETRICO") || key.equals("DATA");
    }

    private String getUnitFromString(String string, List<String> commonUnit) { //COMPARE STRING TAKEN FROM PDF WITH COMMON UNITS OF MEASURMENT IN ORDER TO SAVE THE ONE READ
        for (String unit : commonUnit) {
            if (string.contains(unit)) {
                return unit;
            }
        }
        return null;
    }

}
