package mg.fruive.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ErrorService {
	
	public void defineError(Model model, Integer status, String message) {
		
		model.addAttribute("status", status);
		model.addAttribute("errorMessage", message);
		
	}

}
