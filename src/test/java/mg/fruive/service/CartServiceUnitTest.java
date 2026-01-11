package mg.fruive.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpSession;
import mg.fruive.domain.Cart;
import mg.fruive.entity.Product;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceUnitTest {
	
	@Mock
	private HttpSession httpSession;
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private CartService cartService;
	
	@Test
	public void testGetCartSizeShouldBeZero() {
		
		assertEquals(0, this.cartService.getCartSize());
		
	}
	
	@Test
	public void testAdd() throws NotFoundException, OutOfStockException {
		
		Product product = new Product(1, null, null, "Banana", (float) 100, 1000.0, "kg", null);
		when(this.productRepository.findById(1)).thenReturn(Optional.of(product));
		assertEquals(1, this.cartService.addToCart(product.getId(), (float) 0.5));
		
	}
	
	@Test
	public void testAddShouldThrowNotFoundException() throws OutOfStockException {
		
		when(this.productRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> this.cartService.addToCart(1, (float) 0.5));
		
	}
	
	@Test
	public void testAddShouldThrowOutOfStockException() throws NotFoundException {
		
		Product product = new Product(1, null, null, "Banana", (float) 1, 1000.0, "kg", null);
		when(this.productRepository.findById(1)).thenReturn(Optional.of(product));
		assertThrows(OutOfStockException.class, () -> this.cartService.addToCart(1, (float) 2));
		
	}
	
	@Test
	public void testRemoveProduct() throws OutOfStockException, NotFoundException {
		
		Cart cart = new Cart();
		cart.add(1, (float) 1, (float) 100);
		when(this.httpSession.getAttribute("cart")).thenReturn(cart);
		this.cartService.removeProduct(1);
		assertEquals(0, this.cartService.getCartSize());
		
	}
	
	@Test
	public void testRemoveProductShouldThrowException() {
		
		Cart cart = new Cart();
		when(this.httpSession.getAttribute("cart")).thenReturn(cart);
		assertThrows(NotFoundException.class, () -> this.cartService.removeProduct(1));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPrepareCartView() throws OutOfStockException, NotFoundException {
		
		Product product = new Product(1, null, null, "Banana", (float) 100, 1000.0, "kg", null);
		Cart cart = new Cart();
		cart.add(product.getId(), (float) 1, product.getInStock());
		when(this.httpSession.getAttribute("cart")).thenReturn(cart);
		when(this.productRepository.findByIdWithCategoryAndProvince(1)).thenReturn(Optional.of(product));
		
		Map<String, Object> map = this.cartService.prepareCartView();
		List<Product> products = (List<Product>) map.get("products");
		assertEquals(product.getName(), products.getFirst().getName());
		
	}

}
