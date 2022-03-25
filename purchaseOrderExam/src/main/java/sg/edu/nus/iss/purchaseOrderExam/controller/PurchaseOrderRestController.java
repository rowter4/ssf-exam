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
import jakarta.json.JsonValue;
import sg.edu.nus.iss.purchaseOrderExam.model.Quotation;
import sg.edu.nus.iss.purchaseOrderExam.service.QuotationService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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


        InputStream is = new ByteArrayInputStream(payload.getBytes());
        JsonReader r = Json.createReader(is);
        JsonObject o = r.readObject();

        System.out.println("........ TESTING OF object : " + o);
        
        JsonArray itemsArr = o.getJsonArray("lineItems");
        List<String> newItems3 = new LinkedList<>(); 


        for (int i = 0; i < itemsArr.size(); i++)
            newItems3.add(Quotation.create(itemsArr.getJsonObject(i)));
            
        Map<String, Integer> itemMap = new HashMap<>();
        Optional<Quotation> quote = quoteSvc.getQuotations(new ArrayList<>(itemMap.keySet()));
    
        try {

            String name = o.getString("name");

            JsonObjectBuilder toReturn = Json.createObjectBuilder();        
            // toReturn.add("invoiceId", quote.getQuoteId())
            //         .add("name", name)
            //         .add("total", total)


            return ResponseEntity.ok().body(toReturn.build().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.status(400).body("{}");

        
    }
}
