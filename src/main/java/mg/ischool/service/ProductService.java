package mg.ischool.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import mg.ischool.entity.Product;
import mg.ischool.repository.ProductRepository;

@Service
public class ProductService {
	
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		
		this.productRepository = productRepository;
		
	}
	
	public void findAll(Model model) {
		
		List<Product> products = productRepository.findAll();
		model.addAttribute("products", products);
		
	}

}
