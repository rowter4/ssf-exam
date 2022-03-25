package sg.edu.nus.iss.purchaseOrderExam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.purchaseOrderExam.model.Quotation;

@Service
public class QuotationService {

    private static final String BASE_URL = "https://quotation.chuklee.com";
    private static final String QUOTE = "/quotation";

    public Optional<Quotation> getQuotations(String payload) {
        
        String quotationUrl = UriComponentsBuilder.fromUriString(BASE_URL)
                                                    // .queryParam("lineItems", string)
                                                    .toUriString();

        System.out.println(">>>>> url " + quotationUrl);
        
        RequestEntity<Void> req = RequestEntity.post(quotationUrl)
                                                .accept(MediaType.APPLICATION_JSON)
                                                .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;
        
        try {
            resp = template.exchange(req, String.class);
            String json = resp.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }   

        return Optional.empty();
        
        
    }

    
}
