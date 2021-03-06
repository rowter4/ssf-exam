package sg.edu.nus.iss.purchaseOrderExam.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

// Important. You cannot modify this file

public class Quotation {

    private String quoteId;
    private Map<String, Float> quotations = new HashMap<>();

    public String getQuoteId() { return quoteId; }
    public void setQuoteId(String quoteId) { this.quoteId = quoteId; }

    public Map<String, Float> getQuotations() { return quotations; }
    public void setQuotations(Map<String, Float> quotations) { this.quotations = quotations; }

    public void addQuotation(String item, Float unitPrice) { this.quotations.put(item, unitPrice); }
    public Float getQuotation(String item) { return this.quotations.getOrDefault((Object)item, -1000000f); }


    public static String create(JsonObject o) {
        String quoteId = new String();
        quoteId = o.getString("quoteId");
        // quote.quotations = o.getString("quotations");
        return quoteId;
    }
}
