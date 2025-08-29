package mg.fruive.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Product;
import mg.fruive.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductService {
	
	public static Integer ITEMS_PER_PAGE = 10;
	
	private ProductRepository productRepository;
	
	public List<Product> findAll(Model model, Integer page) {
		
		if(page == null)
			page = 0;
		
		Page<Product> products = productRepository.findAll(PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(Sort.Direction.ASC, "id")));
		
		if(model != null) {
			
			model.addAttribute("products", products.toList());
			model.addAttribute("numberOfPages", this.countPages());
			model.addAttribute("selectedPage", page);
			
		} return products.toList();
		
	}
	
	private Integer countPages() {
		
		List<Product> products = productRepository.findAll();
		Double ratio = ((double)products.size()) / ((double)ITEMS_PER_PAGE);
		Integer intRatio = ratio.intValue();
		
		if(ratio > intRatio)
			return intRatio + 1;
		else return intRatio;
		
	}

}
