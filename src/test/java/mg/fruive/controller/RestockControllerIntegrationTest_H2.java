package mg.fruive.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RestockControllerIntegrationTest_H2 {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username = "johndoe", roles = "Admin")
	public void testRestockingByProductId() throws Exception {
		
		mockMvc.perform(
					post("/bo/restock/1")
						.with(csrf())
						.param("amount", "100")
		).andExpect(status().isOk())
			.andExpect(view().name("restock/save/index"));
		
	}

}
