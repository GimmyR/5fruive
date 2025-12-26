package mg.fruive.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class ErrorService {
	
	public void defineError(Model model, Integer status, String message) {
		
		model.addAttribute("status", status);
		model.addAttribute("errorMessage", message);
		
	}
	
	public void throwExceptionIfErrorsExist(BindingResult bindingResult) throws Exception {
		
		if(bindingResult.hasErrors())
			throw new Exception(bindingResult.getAllErrors().getFirst().getDefaultMessage());
		
	}

}
