package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.exception.NotFoundException;
import mg.fruive.service.ErrorService;
import mg.fruive.service.ProductService;

@Controller
@AllArgsConstructor
public class RestockController {
	
	private ProductService productService;
	private ErrorService errorService;
	
	@GetMapping("/bo/restock/{productId}")
	public ModelAndView restockByProductId(Model model, @PathVariable Integer productId) {
		
		try {
			
			productService.findUnique(model, productId);
			
		} catch (NotFoundException e) {
			
			errorService.defineError(model, 404, e.getMessage());
			
		} return new ModelAndView("restock/save/index");
		
	}

}
