package mg.ischool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mg.ischool.entity.Product;
import mg.ischool.repository.ProductRepository;

@SpringBootTest
class MajudgeApplicationTests {
	
	@Autowired
	private ProductRepository productRepository;

	@Test
	void contextLoads() {
	}
	
	//@Test
	void saveProducts() {
		
		productRepository.save(new Product(null, "PC Portbale"));
		productRepository.save(new Product(null, "PC Bureau"));
		productRepository.save(new Product(null, "Smartphone"));
		
		productRepository.findAll().forEach(product -> System.out.println(product.getName()));
		
	}

}
