package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.service.ProductService;

@Controller
@AllArgsConstructor
public class ProductController {
	
	private ProductService productService;
	
	@GetMapping("/")
	public ModelAndView getHome(Model model, @RequestParam(name = "p", required = false) Integer page) {
		
		productService.findAll(model, page);
		return new ModelAndView("home/index");
		
	}

}
