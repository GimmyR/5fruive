package mg.fruive.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mg.fruive.entity.Purchase;
import mg.fruive.exception.NotFoundException;
import mg.fruive.record.CardForm;
import mg.fruive.service.ErrorService;
import mg.fruive.service.PurchaseService;

@Controller
@AllArgsConstructor
public class PurchaseController {
	
	private PurchaseService purchaseService;
	private ErrorService errorService;
	
	@GetMapping("/payment")
	public String providePaymentMethod(Principal auth, Model model) {
		
		model.addAttribute("auth", auth);
		model.addAttribute("cardForm", new CardForm(null));
		return "payment/index";
		
	}
	
	@PostMapping("/payment")
	public String buyProducts(Principal auth, Model model, @Valid @ModelAttribute CardForm cardForm, BindingResult bindingResult) {
		
		model.addAttribute("auth", auth);
		String view = "payment/index";
		
		try {
			
			errorService.throwExceptionIfErrorsExist(bindingResult, false);
			Purchase purchase = purchaseService.buyProducts(auth, cardForm.code());
			view = "redirect:/bill/" + purchase.getId();
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} catch (Exception e) {
			
			errorService.defineError(model, 400, e.getMessage());
			
		} return view;
		
	}
	
	@GetMapping("/bill/{purchaseId}")
	public String getBill(Principal auth, Model model, @PathVariable Integer purchaseId) {
		
		model.addAttribute("auth", auth);
		
		try {
			
			model.addAttribute("purchase", purchaseService.findPurchase(purchaseId));
			model.addAttribute("fullname", purchaseService.provideAccountFullname(auth));
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return "bill/index";
		
	}
	
	@GetMapping("/bo/dashboard")
	public String getDashboard(Principal auth, Model model) {
		
		model.addAttribute("auth", auth);
		model.addAttribute("active", "dashboard");
		model.addAllAttributes(purchaseService.findMostPurchased());
		return "dashboard/index";
		
	}
	
	@GetMapping("/bo/purchases")
	public String getPurchases(Model model) {
		
		model.addAttribute("active", "purchases");
		model.addAttribute("purchases", purchaseService.findAll());
		return "purchases/index";
		
	}

}
