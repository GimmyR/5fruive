package mg.ischool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mg.ischool.service.ProductService;

@Controller
public class ProductController {
	
	private ProductService productService;
	
	public ProductController(ProductService productService) {
		
		this.productService = productService;
		
	}
	
	@GetMapping("/products")
	public String getAllProducts(Model model) {
		
		productService.findAll(model);
		return "products";
		
	}

}
