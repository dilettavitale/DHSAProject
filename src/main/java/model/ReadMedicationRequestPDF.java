package model;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;

public class ReadMedicationRequestPDF extends ReadGenericPDF {

    FhirClient client;

    public ReadMedicationRequestPDF(File file) {
        super(file);
        this.client = new FhirClient();
    }

    public void extractInformation(Patient p,Practitioner pr) throws ParseException {
        try {
            String elaboratedText = super.extractText(file);
            extractInformationMedicalPrescription(elaboratedText,p,pr);
        } catch (IOException ex) {
            Logger.getLogger(ReadBloodCountPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void extractInformationMedicalPrescription(String text,Patient p,Practitioner pr) throws ParseException {
        List<String> commonDrugs = Arrays.asList("PARACETAMOLO", "IBUPROFENE", "ASPIRINA", "AMOXICILLINA", "LOSARTAN",
                "ATORVASTATINA", "ENALAPRIL", "RAMIPRIL", "BISOPROLOLO", "OMEPRAZOLO",
                "CLOPIDOGREL", "LEVOTIROXINA", "METFORMINA", "ACIDO ACETILSALICILICO",
                "LERCANIDIPINA", "METOTRESSATO", "MELOXICAM", "CANDESARTAN", "KETOPROFENE", "ACIDO FOLICO", "FOSFOMICINA", "INTERFOS");

        SimpleDateFormat dataformat = new SimpleDateFormat("dd/M/yyyy");
        Date dataprescrizione = null;
        Pattern pattern = Pattern.compile("CODICE FISCALE MEDICO:\\s*(\\w+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String codiceFiscale = matcher.group(1);
        }
        pattern = Pattern.compile("[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String codiceFiscale = matcher.group();
        }
        pattern = Pattern.compile("DATA:\\s*(\\d{2}/\\d{2}/\\d{4})");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String data = matcher.group(1);
            dataprescrizione = dataformat.parse(data);
        }

        for (String i : commonDrugs) {
            pattern = Pattern.compile(i + ".*(\r?\n)");
            matcher = pattern.matcher(text);
            if (matcher.find()) {
                // IF A DRUG IS FIND SEND IT TO FHIR SERVER ENCAPSULATING IT IN A MEDICAL PRESCRIPTION
                String drugs = matcher.group();
                pattern = Pattern.compile("(\\d+[.,]?\\d*\\s*(mg|g))", Pattern.CASE_INSENSITIVE);
                Matcher m = pattern.matcher(drugs);//SEARCH FOR DOSE IN MG OR G
                if (m.find()) {
                    String dose = m.group();
                    client.set_medprescription_OnFhir(i,dose , dataprescrizione,p,pr);
                }
            }
        }
 
    }
}
