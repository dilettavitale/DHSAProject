package model;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hl7.fhir.r4.model.Patient;

public class ReadBloodCountPDF extends ReadGenericPDF {

    HashMap<String, String> bloodCountExam;
    FhirClient client;
    public ReadBloodCountPDF(File file) {
        super(file);
        this.bloodCountExam = new HashMap();
        this.client=new FhirClient();
    }

    public void extractInformation(Patient p) throws ParseException  {
        try {
            String elaboratedText = super.extractText(file);       //FIRST EXTRACT TEXT CALLING METHOD OF CLASS EXTENDED
            extractInformationBloodExams(elaboratedText,p);        //THEN COLLECT INFORMATION NEEDED
        } catch (IOException ex) {
            Logger.getLogger(ReadBloodCountPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void extractInformationBloodExams(String text, Patient p) throws ParseException {     //THERE ARE SEVERAL REG EX TO SEARCH WHAT IS NEEDED
        Pattern pattern = Pattern.compile("Codice Lab:\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String codiceLab1 = matcher.group(1);
            String codiceLab2 = matcher.group(2);
            String codiceLab3 = matcher.group(3);
            String codiceLab = codiceLab1 + " " + codiceLab2 + " " + codiceLab3; //246370007  struttura per analisi cliniche
            bloodCountExam.put("CODICE LABORATORIO", codiceLab);
        }
        pattern = Pattern.compile("Medico :\\s*(\\w+)\\s+(\\w+)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String nomeMedico = matcher.group(1);        //309341008
            String cognomeMedico = matcher.group(2);
            bloodCountExam.put("MEDICO", nomeMedico + "" + cognomeMedico);
        }
        pattern = Pattern.compile("C.F.:\\s*(\\w+)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String codiceFiscalePaziente = matcher.group(1);
            bloodCountExam.put("CODICE FISCALE", codiceFiscalePaziente);
        }
        pattern = Pattern.compile("EMOCROMOCITOMETRICO");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String esame = matcher.group();     // 88308000
            bloodCountExam.put("EMOCROMOCITOMETRICO", esame);
        }
        pattern = Pattern.compile("\\(RBC\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String rbc = matcher.group(1); // 14089001
            bloodCountExam.put("RBC", rbc);
        } else {
            System.out.println("Red Blood Cells(RBC) missing");
        }
        pattern = Pattern.compile("\\(WBC\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String wbc = matcher.group(1);    //767002
            bloodCountExam.put("WBC", wbc);
        } else {
            System.out.println("White Blood Cells(WBC) missing");
        }
        pattern = Pattern.compile("\\(Hgb\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String hemoglobin = matcher.group(1);     // 38082009
            bloodCountExam.put("HGB", hemoglobin);
        } else {
            System.out.println("Hemoglobin(Hgb) missing");
        }
        pattern = Pattern.compile("\\(Hct\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String hematocrit = matcher.group(1);    //28317006
            bloodCountExam.put("HCT", hematocrit);
        } else {
            System.out.println("Hematocrit(Hct) missing");
        }
        pattern = Pattern.compile("\\(MCV\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String mcv = matcher.group(1);      //104133003
            bloodCountExam.put("MCV", mcv);
        } else {
            System.out.println("Mean corpuscular volume(MCV) missing");
        }
        pattern = Pattern.compile("\\(MCH\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String mch = matcher.group(1);         //735148007
            bloodCountExam.put("MCH", mch);
        } else {
            System.out.println("Mean corpuscular volume(MCH) missing");
        }
        pattern = Pattern.compile("\\(MCHC\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String mchc = matcher.group(1);        // 37254006
            bloodCountExam.put("MCHC", mchc);
        } else {
            System.out.println("Mean corpuscular hemoglobin concentration(MCHC) missing");
        }
        pattern = Pattern.compile("\\(RDW\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String rdw = matcher.group(1);       //66842004
            bloodCountExam.put("RDW", rdw);
        } else {
            System.out.println("Red cell distribution width(RDW) missing");
        }
        pattern = Pattern.compile("\\(PLTS\\)[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String plts = matcher.group(1);     //104086003
            bloodCountExam.put("PLTS", plts);
        } else {
            System.out.println("Platelets(PLTS) missing");
        }
        pattern = Pattern.compile("Granulociti Neutrofili[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String grne = matcher.group(1);        //271035003
            bloodCountExam.put("GRNE", grne);
        } else {
            System.out.println("Neutrophil granulocytes missing");
        }
        pattern = Pattern.compile("Granulociti Eosinofili[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String greo = matcher.group(1);          //CODICE SNOMED 310540006
            bloodCountExam.put("GREO", greo);
        } else {
            System.out.println("Eosinophilic granulocytes missing");
        }
        pattern = Pattern.compile("Granulociti Basofili[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String grba = matcher.group(1);   // 271038001
            System.out.println("Basophil granulocytes :" + grba);
            bloodCountExam.put("GRBA", grba);
        } else {
            System.out.println("Basophil granulocytes missing");
        }
        pattern = Pattern.compile("Linfociti[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String ly = matcher.group(1);       //CODICE SNOMED 271036002
            bloodCountExam.put("LY", ly);
        } else {
            System.out.println("lymphocytes missing");
        }
        pattern = Pattern.compile("Monociti[\\s]*(.*)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String mo = matcher.group(1);           //CODICE SNOMED  271037006
            bloodCountExam.put("MO", mo);
        } else {
            System.out.println("monocytes missing");
        }
        pattern = Pattern.compile("WBC\\s+([\\d.]+)\\s+");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String wbc = matcher.group(1);
            bloodCountExam.put("WBC", wbc);
        }
        pattern = Pattern.compile("RBC\\s+[A-Z]+\\s+([\\d.]+)\\s+");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String rbc = matcher.group(1);
            bloodCountExam.put("RBC", rbc);
        }
        pattern = Pattern.compile("Hemoglobin\\s+([\\d.]+)\\s+");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String hemoglobin = matcher.group(1);
            bloodCountExam.put("HGB", hemoglobin);
        }
        pattern = Pattern.compile("Hematocrit\\s+([\\d.]+)\\s+");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String hematocrit = matcher.group(1);
            bloodCountExam.put("HCT", hematocrit);
        }
        pattern = Pattern.compile("del\\s(\\d{2}-\\d{2}-\\d{4})");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            String data = matcher.group(1);      
            bloodCountExam.put("DATA", data);
        }
        client.set_bloodCount_onFhir(bloodCountExam,p);       //CALL METHOD TO SEND THIS INFORMATION ON FHIR
    }

}
