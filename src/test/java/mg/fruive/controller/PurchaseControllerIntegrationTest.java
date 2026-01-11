package mg.fruive.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.fruive.record.CartEntryForm;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PurchaseControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@WithMockUser(username = "johndoe")
	public void testGetBill() throws Exception {
		
		mockMvc.perform(get("/bill/1"))
					.andExpect(status().isOk())
					.andExpect(view().name("bill/index"));
		
	}
	
	@Test
	@WithMockUser(username = "johndoe", roles = "Admin")
	public void testGetDashboard() throws Exception {
		
		mockMvc.perform(get("/bo/dashboard"))
					.andExpect(status().isOk())
					.andExpect(view().name("dashboard/index"));
		
	}
	
	@Test
	@WithMockUser(username = "adawong", roles = "Client")
	public void testBuyProducts() throws Exception {
		
		List<CartEntryForm> entries = Arrays.asList(
				new CartEntryForm(10, (float) 1), 
				new CartEntryForm(1, (float) 2),
				new CartEntryForm(2, (float) 0.5)
		);
		
		MvcResult result = mockMvc.perform(post("/api/cart/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(entries))
		).andReturn();
		
		mockMvc.perform(
				post("/payment")
					.with(csrf())
					.session((MockHttpSession) result.getRequest().getSession())
					.param("code", "12345")
		).andExpect(status().is(302));
		
	}

}
