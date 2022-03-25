package sg.edu.nus.iss.purchaseOrderExam;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.purchaseOrderExam.model.Quotation;
import sg.edu.nus.iss.purchaseOrderExam.service.QuotationService;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseOrderExamApplicationTests {


	@Autowired
	private QuotationService quoteSvc;

	@Autowired
	private MockMvc mvc;


	@Test
	void contextLoads() {

		List<String> items2 = new LinkedList<>();
		items2.add("durian");
		items2.add("plum");
		items2.add("pear");
		
		Optional<Quotation> quote = quoteSvc.getQuotations(items2);
		Assertions.assertTrue(quote.isPresent());
	}

	@Test
	void shouldReturnList() throws Exception {

		JsonObject payload = Json.createObjectBuilder()
			.add("name", "Michael Scott") 
			.add("address", "21 Bedok Road") 
			.add("email", "abcde@gmail.com") 
			.build();


		RequestBuilder req = MockMvcRequestBuilders.post("/api")
													.header("User-Agent", "junit")
													.accept(MediaType.APPLICATION_JSON)
													.contentType(MediaType.APPLICATION_JSON)
													.content(payload.toString());


		MvcResult result = mvc.perform(req).andReturn();
		int status = result.getResponse().getStatus();

		String response = result.getResponse().getContentAsString();
		Assertions.assertEquals(200, status);

		System.out.println("payload: " + response);

	}

}
