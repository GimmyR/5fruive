package mg.fruive.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.fruive.record.CartEntryForm;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartRestControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testGetCartSize() throws Exception {
		
		mockMvc.perform(get("/api/cart-size").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(0));
		
	}
	
	@Test
	public void testAddToCart() throws Exception {
		
		mockMvc.perform(post("/api/cart/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new CartEntryForm(10, (float) 1)))
		).andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").value(1));
		
	}
	
	@Test
	public void testRemoveProduct() throws Exception {
		
		MvcResult result = mockMvc.perform(post("/api/cart/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(new CartEntryForm(10, (float) 1)))
		).andReturn();
		
		mockMvc.perform(post("/api/cart/remove/10")
				.session((MockHttpSession) result.getRequest().getSession())
				.accept(MediaType.APPLICATION_JSON)
		).andExpect(status().isCreated());
		
	}
	
	@Test
	public void testSaveCart() throws Exception {
		
		List<CartEntryForm> entries = Arrays.asList(
				new CartEntryForm(10, (float) 1), 
				new CartEntryForm(1, (float) 2),
				new CartEntryForm(2, (float) 0.5)
		);
		
		mockMvc.perform(post("/api/cart/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(entries))
		).andExpect(status().isCreated());
		
	}

}
