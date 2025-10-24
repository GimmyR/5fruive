package mg.fruive.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.exception.NotFoundException;
import mg.fruive.service.CartService;
import mg.fruive.service.ErrorService;

@Controller
@AllArgsConstructor
public class CartController {
	
	private CartService cartService;
	private ErrorService errorService;
	
	@GetMapping("/cart")
	public ModelAndView getCart(Principal auth, Model model) {
		
		model.addAttribute("auth", auth);
		
		try {
			
			cartService.prepareCartView(model);
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return new ModelAndView("cart/index");
		
	}
	
	@GetMapping("/cart/reset")
	public ModelAndView resetCart() {
		
		cartService.resetCart();
		return new ModelAndView("redirect:/cart");
		
	}

}
