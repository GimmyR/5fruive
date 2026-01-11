package mg.fruive.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import mg.fruive.entity.Product;
import mg.fruive.exception.NotFoundException;
import mg.fruive.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductService {
	
	public static Integer ITEMS_PER_PAGE = 9;
	
	private ProductRepository productRepository;
	
	public Map<String, Object> findAll(String search, Integer page, boolean withCategoryAndProvince) {
		
		Map<String, Object> result = new HashMap<>();
		Page<Product> products = null;
		
		if(page == null)
			page = 0;
		
		if(search == null)
			search = "";
		
		search = "%" + search + "%";
		PageRequest request = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(Sort.Direction.ASC, "id"));
		
		if(!withCategoryAndProvince)
			products = productRepository.findByNameLikeIgnoreCase(search, request);
		
		else products = productRepository.findByNameWithCategoryAndProvince(search, request);
		
		result.put("products", products.toList());
		result.put("numberOfPages", products.getTotalPages());
		result.put("selectedPage", page);
		
		if(search != null)
			result.put("search", URLEncoder.encode(search, StandardCharsets.UTF_8));
			
		return result;
		
	}
	
	public Product findUnique(Integer id) throws NotFoundException {
		
		Optional<Product> opt = productRepository.findById(id);
		
		if(opt.isEmpty())
			throw new NotFoundException("Product not found");
		
		return opt.get();
		
	}
	
	public Product findUniqueWithCategoryAndProvince(Integer id) throws NotFoundException {
		
		Optional<Product> opt = productRepository.findByIdWithCategoryAndProvince(id);
		
		if(opt.isEmpty())
			throw new NotFoundException("Product not found");
		
		return opt.get();
		
	}
	
	public Product update(Product product) {
		
		return productRepository.save(product);
		
	}

}
