package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;
import mg.fruive.service.ProvinceService;

@Controller
@AllArgsConstructor
public class ProvinceController {
	
	private ProvinceService provinceService;
	
	@GetMapping("/bo/provinces")
	public String getProvinces(Model model) {
		
		model.addAttribute("active", "provinces");
		provinceService.findAll(model);
		return "provinces/index";
		
	}

}
