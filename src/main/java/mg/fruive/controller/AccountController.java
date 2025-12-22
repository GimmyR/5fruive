package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;
import mg.fruive.service.AccountService;

@Controller
@AllArgsConstructor
public class AccountController {
	
	private AccountService accountService;
	
	@GetMapping("/sign-in")
	public String signIn() {
		
		return "sign-in/index";
		
	}
	
	@GetMapping("/bo/accounts")
	public String getAccounts(Model model) {
		
		model.addAttribute("active", "accounts");
		model.addAttribute("accounts", accountService.findAll());
		return "accounts/index";
		
	}

}
