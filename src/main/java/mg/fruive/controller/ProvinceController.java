package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.service.ProvinceService;

@Controller
@AllArgsConstructor
public class ProvinceController {
	
	private ProvinceService provinceService;
	
	@GetMapping("/bo/provinces")
	public ModelAndView getProvinces(Model model) {
		
		model.addAttribute("active", "provinces");
		provinceService.findAll(model);
		return new ModelAndView("provinces/index");
		
	}

}
