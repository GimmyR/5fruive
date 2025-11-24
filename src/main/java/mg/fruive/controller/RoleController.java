package mg.fruive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import mg.fruive.service.RoleService;

@Controller
@AllArgsConstructor
public class RoleController {
	
	private RoleService roleService;
	
	@GetMapping("/bo/roles")
	public ModelAndView getRoles(Model model) {
		
		model.addAttribute("active", "roles");
		roleService.findAll(model);
		return new ModelAndView("roles/index");
		
	}

}
