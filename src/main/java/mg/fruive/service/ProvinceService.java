package mg.fruive.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Province;
import mg.fruive.repository.ProvinceRepository;

@Service
@AllArgsConstructor
public class ProvinceService {
	
	private ProvinceRepository provinceRepository;
	
	public List<Province> findAll(Model model) {
		
		List<Province> provinces = provinceRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
		if(model != null)
			model.addAttribute("provinces", provinces);
		
		return provinces;
		
	}

}
