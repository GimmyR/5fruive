package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.AllArgsConstructor;
import mg.fruive.entity.Purchase;
import mg.fruive.exception.NotFoundException;
import mg.fruive.service.ErrorService;
import mg.fruive.service.PurchaseService;

@Controller
@AllArgsConstructor
public class PurchaseDetailController {
	
	private PurchaseService purchaseService;
	private ErrorService errorService;
	
	@GetMapping("/bo/purchases/{purchaseId}")
	public String getPurchaseDetails(Model model, @PathVariable Integer purchaseId) {
		
		try {
			
			Purchase purchase = purchaseService.findPurchaseWithDetails(purchaseId);
			model.addAttribute("details", purchase.getDetails());
			
		} catch (NotFoundException  e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return "purchase-details/index";
		
	}

}
