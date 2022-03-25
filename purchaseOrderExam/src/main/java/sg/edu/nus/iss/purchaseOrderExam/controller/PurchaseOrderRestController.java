package sg.edu.nus.iss.purchaseOrderExam.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.purchaseOrderExam.model.Quotation;
import sg.edu.nus.iss.purchaseOrderExam.service.QuotationService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

@RestController
@RequestMapping(path="/api")
public class PurchaseOrderRestController {

    @Autowired
    private QuotationService quoteSvc;
    
    
    @PostMapping(path="/po")
    public ResponseEntity<String> postPO( @RequestBody String payload, Model model ) {
        
        System.out.println("........ TESTING OF payload : " + payload);
        


        JsonObjectBuilder builder;

        try (InputStream is = new ByteArrayInputStream(payload.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject req = r.readObject();
            

            String name = req.getString("name");
            String address = req.getString("address");
            String email = req.getString("email");
            JsonArray lineItems = req.getJsonArray("lineItems");

            builder = Json.createObjectBuilder();


            builder.add("name", name);
            builder.add("address", address);
            builder.add("email", email);
            builder.add("lineItems", lineItems);


        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw ex;

        } catch (Exception ex) {
            JsonObject result = Json.createObjectBuilder()
                .add("error", ex.getMessage())
                .build();
            return ResponseEntity.status(400).body(result.toString());

        }

        return ResponseEntity.ok(builder.build().toString());

        

        // Optional<Quotation> quote = quoteSvc.getQuotations(payload);

        // String name = form.getFirst("name");
        // String email = form.getFirst("email");
        // String phone = form.getFirst("phone");

        // System.out.println(">>> name: %s, email: %s\n".formatted(name, email));





        // System.out.println(">>>> form : ");
        // System.out.printf(">>>> payload: %s\n", payload);

        // JsonArrayBuilder arrBuilder = Json.createArrayBuilder(); // this is to build the json data
        // JsonObjectBuilder builder; 

        // String name = ((JsonArray) form).getString("name");
        // String email = form.getFirst("email");
        // String phone = form.getFirst("address");


        

        
        // System.out.println(">>> POST data: " );
        // try (InputStream is = new ByteArrayInputStream(form.getBytes())) {
        //         JsonReader r = Json.createReader(is);
        //         JsonObject request = r.readObject();
        //         builder = Json.createObjectBuilder();
        // } catch (Exception ex) {
        // return phone;

        // JsonArray quoteArray = arrBuilder.build();

        // return ResponseEntity
        //     .ok()            
        //     .body(quoteArray.toString());

        // }         
        // return null;
    }
}
