package mg.fruive.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetCart() throws Exception {
		
		mockMvc.perform(get("/cart"))
			.andExpect(status().isOk())
			.andExpect(view().name("cart/index"));
		
	}

}
