package mg.fruive.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Account;
import mg.fruive.entity.Product;
import mg.fruive.exception.NegativeException;
import mg.fruive.exception.NotFoundException;
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
	public ModelAndView restockByProductId(Model model, @PathVariable Integer productId) {
		
		try {
			
			productService.findUnique(model, productId);
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return new ModelAndView("restock/save/index");
		
	}
	
	@PostMapping("/bo/restock/{productId}")
	public ModelAndView restockingByProductId(Model model, Principal auth, @PathVariable Integer productId, @RequestParam Float amount) {
		
		try {
			
			Product product = productService.findUnique(model, productId);
			Account account = accountService.findUniqueByUsername(auth.getName());
			restockService.saveRestock(account, product, amount);
			product.setInStock(product.getInStock() + amount);
			productService.update(product);
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} catch (NegativeException e) {
			
			errorService.defineError(model, 402, e.getMessage());
			
		} return new ModelAndView("restock/save/index");
		
	}

}
