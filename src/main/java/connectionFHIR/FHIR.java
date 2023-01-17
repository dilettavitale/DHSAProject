/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package connectionFHIR;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author immacolata
 */
public class FHIR {

    FhirContext ctx = FhirContext.forR4();
    String serverBase = "https://hapi.fhir.org/baseR4";
    private IGenericClient client = ctx.newRestfulGenericClient(serverBase);
     

    // private constructor
    public FHIR() {
    }
    
    public IGenericClient FHIRConnection(){
        IGenericClient client = ctx.newRestfulGenericClient(serverBase);
        return client;    
    }
}
