package mg.fruive.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String getHome(Principal auth, Model model, @RequestParam(required = false) String search, @RequestParam(name = "p", required = false) Integer page) {
		
		model.addAllAttributes(productService.findAll(search, page));
		model.addAttribute("auth", auth);
		return "home/index";
		
	}
	
	@GetMapping("/product/{productId}")
	public String getUniqueProduct(Principal auth, Model model, @PathVariable Integer productId) {
		
		model.addAttribute("auth", auth);
		
		try {
		
			model.addAttribute("product", productService.findUnique(productId));
		
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return "product/index";
		
	}
	
	@GetMapping("/bo/products")
	public String getProducts(Model model, @RequestParam(required = false) String search, @RequestParam(name = "p", required = false) Integer page) {
		
		model.addAttribute("active", "products");
		model.addAttribute("url", "/bo/products");
		model.addAllAttributes(productService.findAll(search, page));
		return "products/index";
		
	}

}
