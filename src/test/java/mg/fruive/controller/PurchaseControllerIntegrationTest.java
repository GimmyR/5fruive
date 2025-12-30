package mg.fruive.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
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

}
