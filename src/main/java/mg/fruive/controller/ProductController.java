package mg.fruive.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.exception.NotFoundException;
import mg.fruive.service.ErrorService;
import mg.fruive.service.ProductService;

@Controller
@AllArgsConstructor
public class ProductController {
	
	private ProductService productService;
	
	private ErrorService errorService;
	
	@GetMapping("/")
	public ModelAndView getHome(Principal auth, Model model, @RequestParam(required = false) String search, @RequestParam(name = "p", required = false) Integer page) {
		
		productService.findAll(model, search, page);
		model.addAttribute("auth", auth);
		return new ModelAndView("home/index");
		
	}
	
	@GetMapping("/product/{productId}")
	public ModelAndView getUniqueProduct(Principal auth, Model model, @PathVariable Integer productId) {
		
		model.addAttribute("auth", auth);
		
		try {
		
			productService.findUnique(model, productId);
		
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return new ModelAndView("product/index");
		
	}
	
	@GetMapping("/bo/products")
	public ModelAndView getProducts(Model model) {
		
		model.addAttribute("active", "products");
		productService.findAll(model);
		return new ModelAndView("products/index");
		
	}

}
