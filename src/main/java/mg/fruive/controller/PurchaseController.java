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
import mg.fruive.service.PurchaseDetailService;
import mg.fruive.service.PurchaseService;

@Controller
@AllArgsConstructor
public class PurchaseController {
	
	private PurchaseService purchaseService;
	private PurchaseDetailService purchaseDetailService;
	private ErrorService errorService;
	
	@GetMapping("/payment")
	public ModelAndView providePaymentMethod(Principal auth, Model model) {
		
		model.addAttribute("auth", auth);
		return new ModelAndView("payment/index");
		
	}
	
	@PostMapping("/payment")
	public ModelAndView buyProducts(Principal auth, Model model, @RequestParam(name = "card-code", required = false) String cardCode) {
		
		model.addAttribute("auth", auth);
		Object result = purchaseService.buyProducts(auth, cardCode);
		
		if(result instanceof Purchase) {
			
			Purchase purchase = (Purchase) result;
			return new ModelAndView("redirect:/bill/" + purchase.getId());
			
		} else {
			
			String params = "?error=" + URLEncoder.encode((String) result, StandardCharsets.UTF_8);
			return new ModelAndView("redirect:/cart" + params);
			
		}
		
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
	
	@GetMapping("/bo/dashboard")
	public ModelAndView getDashboard(Principal auth, Model model) {
		
		model.addAttribute("auth", auth);
		model.addAttribute("active", "dashboard");
		return new ModelAndView("dashboard/index");
		
	}
	
	@GetMapping("/bo/purchases")
	public ModelAndView getPurchases(Model model) {
		
		model.addAttribute("active", "purchases");
		purchaseDetailService.findAll(model);
		return new ModelAndView("purchases/index");
		
	}

}
