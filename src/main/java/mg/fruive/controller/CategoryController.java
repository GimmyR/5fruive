package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.service.CategoryService;

@Controller
@AllArgsConstructor
public class CategoryController {
	
	private CategoryService categoryService;
	
	@GetMapping("/bo/categories")
	public ModelAndView getCategories(Model model) {
		
		model.addAttribute("active", "categories");
		categoryService.findAll(model);
		return new ModelAndView("categories/index");
		
	}

}
