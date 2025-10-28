package mg.fruive.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaymentController {
	
	@GetMapping("/payment")
	public ModelAndView providePaymentMethod(Principal auth, Model model) {
		
		model.addAttribute("auth", auth);
		return new ModelAndView("payment/index");
		
	}

}
