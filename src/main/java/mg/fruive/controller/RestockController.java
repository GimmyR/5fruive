package mg.fruive.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mg.fruive.entity.Account;
import mg.fruive.entity.Product;
import mg.fruive.exception.NotFoundException;
import mg.fruive.record.AmountForm;
import mg.fruive.service.AccountService;
import mg.fruive.service.ErrorService;
import mg.fruive.service.ProductService;
import mg.fruive.service.RestockService;

@Controller
@AllArgsConstructor
public class RestockController {
	
	private ProductService productService;
	private AccountService accountService;
	private RestockService restockService;
	private ErrorService errorService;
	
	@GetMapping("/bo/restock/{productId}")
	public String restockByProductId(Model model, @PathVariable Integer productId) {
		
		try {
			
			model.addAttribute("product", productService.findUnique(productId));
			model.addAttribute("amountForm", new AmountForm(null));
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return "restock/save/index";
		
	}
	
	@PostMapping("/bo/restock/{productId}")
	public String restockingByProductId(Model model, Principal auth, @PathVariable Integer productId, @Valid AmountForm amountForm, BindingResult bindingResult) {
		
		try {
			
			Product product = productService.findUnique(productId);
			model.addAttribute("product", product);
			errorService.throwExceptionIfErrorsExist(bindingResult, false);
			Account account = accountService.findUniqueByUsername(auth.getName());
			restockService.saveRestock(account, product, amountForm.amount());
			product.addToInStock(amountForm.amount());
			productService.update(product);
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} catch (Exception e) {
			
			errorService.defineError(model, 400, e.getMessage());
			
		} return "restock/save/index";
		
	}
	
	@GetMapping("/bo/restocks")
	public String getRestocks(Model model) {
		
		model.addAttribute("restocks", restockService.findAll());
		return "restock/index";
		
	}

}
