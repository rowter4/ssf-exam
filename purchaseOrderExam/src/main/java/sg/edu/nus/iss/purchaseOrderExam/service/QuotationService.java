package sg.edu.nus.iss.purchaseOrderExam.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.purchaseOrderExam.model.Quotation;

@Service
public class QuotationService {

    private static final String BASE_URL = "https://quotation.chuklee.com/quotation";


    public Optional<Quotation> getQuotations(List<String> items) {

        JsonArrayBuilder newArray = Json.createArrayBuilder();
        for (String i : items )
            newArray.add(i);        
        JsonArray payload = newArray.build();
        
        String quotationUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                                    .toUriString();

        System.out.println(">>>>> url " + quotationUrl);
        
        RequestEntity<String> req = RequestEntity.post(quotationUrl)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(payload.toString(), String.class);
                                            

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        
        try {
            resp = template.exchange(req, String.class);
            String json = resp.getBody();

            Quotation newResponse = new Quotation();

            if (resp.getStatusCodeValue() == 200) {
                try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
                    JsonReader reader = Json.createReader(is);
                    JsonObject o = reader.readObject();
        
                    newResponse.setQuoteId(o.getString("quoteId"));
                    

                    if (o.containsKey("quotations")) {
                        JsonArray quotationArray = o.getJsonArray("quotations");

                        for(int i = 0; i < quotationArray.size(); i++) {
                            JsonObject item = quotationArray.getJsonObject(i);
                            String itemName = item.getString("item");
                            Double unitPrice = item.getJsonNumber("unitPrice").doubleValue();
                            newResponse.addQuotation(itemName, unitPrice.floatValue());
                        }
                    }
        
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }   

        return Optional.empty();
        
        
    }

    
}
