package sg.edu.nus.iss.purchaseOrderExam;

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
		// Optional<Quotation> quote = quoteSvc.getQuotations("");
		// Assertions.assertTrue(quote.isPresent());
	}

	@Test
	void shouldReturnList() throws Exception {

		RequestBuilder req = MockMvcRequestBuilders.get("/invoice")
				.queryParam("lineItems")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(req).andReturn();
		int status = result.getResponse().getStatus();

		String payload = result.getResponse().getContentAsString();
		Assertions.assertEquals(200, status);

		System.out.println("payload: " + payload);

	}

}
