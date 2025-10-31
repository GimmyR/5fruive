package mg.fruive.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Purchase;
import mg.fruive.exception.NotFoundException;
import mg.fruive.service.ErrorService;
import mg.fruive.service.PurchaseService;

@Controller
@AllArgsConstructor
public class PurchaseController {
	
	private PurchaseService purchaseService;
	private ErrorService errorService;
	
	@GetMapping("/payment")
	public ModelAndView providePaymentMethod(Principal auth, Model model) {
		
		model.addAttribute("auth", auth);
		return new ModelAndView("payment/index");
		
	}
	
	@PostMapping("/payment")
	public ModelAndView buyProducts(Principal auth, Model model, @RequestParam(name = "card-code", required = false) String cardCode) {
		
		model.addAttribute("auth", auth);
		String params = "";
		
		try {
		
			Purchase purchase = purchaseService.buyProducts(auth, cardCode);
			return new ModelAndView("redirect:/bill/" + purchase.getId());
		
		} catch (Exception e) {
			
			params = "?error=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
			
		} return new ModelAndView("redirect:/cart" + params);
		
	}
	
	@GetMapping("/bill/{purchaseId}")
	public ModelAndView getBill(Principal auth, Model model, @PathVariable Integer purchaseId) {
		
		model.addAttribute("auth", auth);
		
		try {
			
			Purchase purchase = purchaseService.findPurchase(purchaseId);
			model.addAttribute("purchase", purchase);
			purchaseService.provideAccountFullname(model, auth);
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return new ModelAndView("bill/index");
		
	}

}
