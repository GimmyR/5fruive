package mg.fruive.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Product;
import mg.fruive.exception.NotFoundException;
import mg.fruive.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductService {
	
	public static Integer ITEMS_PER_PAGE = 9;
	
	private ProductRepository productRepository;
	
	public List<Product> findAll(Model model, String search, Integer page) {
		
		Page<Product> products = null;
		
		if(page == null)
			page = 0;
		
		if(search != null)
			products = productRepository.findByNameLikeIgnoreCase("%" + search + "%", PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(Sort.Direction.ASC, "id")));
		
		else products = productRepository.findAll(PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(Sort.Direction.ASC, "id")));
		
		if(model != null) {
			
			model.addAttribute("products", products.toList());
			model.addAttribute("numberOfPages", products.getTotalPages());
			model.addAttribute("selectedPage", page);
			
			if(search != null)
				model.addAttribute("search", URLEncoder.encode(search, StandardCharsets.UTF_8));
			
		} return products.toList();
		
	}
	
	public Product findUnique(Model model, Integer id) throws NotFoundException {
		
		Optional<Product> opt = productRepository.findById(id);
		
		if(opt.isEmpty())
			throw new NotFoundException("Product not found");
		
		if(model != null)
			model.addAttribute("product", opt.get());
		
		return opt.get();
		
	}

}
