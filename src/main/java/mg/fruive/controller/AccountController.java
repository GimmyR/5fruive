package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
	
	@GetMapping("/sign-in")
	public String signIn() {
		
		return "sign-in/index";
		
	}

}
