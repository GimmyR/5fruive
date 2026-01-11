package mg.fruive.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import mg.fruive.entity.Category;
import mg.fruive.entity.Product;
import mg.fruive.entity.Province;
import mg.fruive.exception.NotFoundException;
import mg.fruive.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productService;
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindByNameLikeIgnoreCase() {
		
		String search = "Banana";
		Category category = new Category(1, "Fruit");
		Province province = new Province(1, "Antananarivo");
		List<Product> products = Arrays.asList(new Product(1, category, province, search, (float) 1000.0, 2000.0, "kg", "banana.jpg"));
		Page<Product> page = new PageImpl<Product>(products);
		
		when(productRepository.findByNameLikeIgnoreCase("%" + search + "%", PageRequest.of(0, 9, Sort.by(Sort.Direction.ASC, "id")))).thenReturn(page);
		
		Map<String, Object> result = productService.findAll(search, null, false);
		List<Product> prods = ((List<Product>)result.get("products"));
		assertEquals(1, prods.size());
		assertEquals("Banana", prods.getFirst().getName());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAll() {
		
		String search = "Banana";
		Category category = new Category(1, "Fruit");
		Province province = new Province(1, "Antananarivo");
		List<Product> products = Arrays.asList(new Product(1, category, province, search, (float) 1000.0, 2000.0, "kg", "banana.jpg"));
		Page<Product> page = new PageImpl<Product>(products);
		
		when(productRepository.findAll(PageRequest.of(0, 9, Sort.by(Sort.Direction.ASC, "id")))).thenReturn(page);
		
		Map<String, Object> result = productService.findAll(null, null, false);
		List<Product> prods = ((List<Product>)result.get("products"));
		assertEquals(1, prods.size());
		assertEquals("Banana", prods.getFirst().getName());
		
	}
	
	@Test
	public void testFindUnique() throws NotFoundException {
		
		Category category = new Category(1, "Fruit");
		Province province = new Province(1, "Antananarivo");
		Product product = new Product(1, category, province, "Banana", (float) 1000.0, 2000.0, "kg", "banana.jpg");
		
		when(productRepository.findById(1)).thenReturn(Optional.of(product));
		
		Product prod = productService.findUnique(1);
		assertNotNull(prod);
		assertEquals("banana.jpg", prod.getImage());
		
	}
	
	@Test
	public void testFindUniqueThrowingNotFoundException() {
		
		when(productRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> productService.findUnique(1));
		
	}

}
