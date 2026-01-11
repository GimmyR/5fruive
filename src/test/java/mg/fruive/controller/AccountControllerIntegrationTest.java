package mg.fruive.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class AccountControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testSignIn() throws Exception {
			
		mockMvc.perform(get("/sign-in"))
			.andExpect(status().isOk())
			.andExpect(view().name("sign-in/index"));
		
	}
	
	@Test
	@WithMockUser(username = "johndoe", roles = {"Client", "Admin"})
	public void testGetAccounts() throws Exception {
			
		mockMvc.perform(get("/bo/accounts"))
			.andExpect(status().isOk())
			.andExpect(view().name("accounts/index"));
		
	}
	
}
