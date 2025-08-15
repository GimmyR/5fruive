package mg.fruive.controller;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Product;
import mg.fruive.repository.ProductRepository;

@Controller
@AllArgsConstructor
public class ProductController {
	
	private ProductRepository productRepository;
	
	@GetMapping("/")
	public ModelAndView getHome(Model model) {
		
		List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("products", products);
		
		return new ModelAndView("home/index");
		
	}

}
