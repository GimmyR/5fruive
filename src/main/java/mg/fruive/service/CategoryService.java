package mg.fruive.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Category;
import mg.fruive.repository.CategoryRepository;

@Service
@AllArgsConstructor
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	
	public List<Category> findAll(Model model) {
		
		List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
		if(model != null)
			model.addAttribute("categories", categories);
		
		return categories;
		
	}

}
