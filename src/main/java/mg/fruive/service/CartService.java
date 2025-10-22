package mg.fruive.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mg.fruive.entity.Product;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.repository.ProductRepository;

@Service
@AllArgsConstructor
public class CartService {
	
	private HttpSession httpSession;
	private ProductRepository productRepository;
	
	@SuppressWarnings("unchecked")
	public Map<Integer, Float> getCart() {
		
		Object cart = httpSession.getAttribute("cart");
		
		if(cart != null)
			return (Map<Integer, Float>) cart;
		
		else return null;
		
	}
	
	public Integer addToCart(Integer productId, Float amount) throws NotFoundException, OutOfStockException {
		
		this.validateInput(productId, amount);
		Map<Integer, Float> cart = this.getCart();
		
		if(cart == null)
			cart = new HashMap<>();
		
		return this.addOrPut(cart, productId, amount);
		
	}
	
	private void validateInput(Integer productId, Float amount) {
		
		if(productId == null)
			throw new NullPointerException("Product ID is missing");
		
		else if(amount == null)
			throw new NullPointerException("Amount of a product (ID: " + productId + ") is missing");
		
	}
	
	private Integer addOrPut(Map<Integer, Float> cart, Integer productId, Float amount) throws NotFoundException, OutOfStockException {
		
		Product product = this.findProduct(productId);
		Float number = cart.get(productId);
		
		if(number == null)
			number = (float) 0;
		
		Float total = number + amount;
		this.validateInStock(product, total);
		cart.put(productId, total);
		httpSession.setAttribute("cart", cart);
		return cart.size();
		
	}
	
	private Product findProduct(Integer productId) throws NotFoundException {
		
		Optional<Product> opt = productRepository.findById(productId);
		
		if(opt.isEmpty())
			throw new NotFoundException("Product not found");
		
		else return opt.get();
		
	}
	
	private void validateInStock(Product product, Float total) throws OutOfStockException {
		
		if(total > product.getInStock())
			throw new OutOfStockException("Out of stock : " + total + " > " + product.getInStock());
		
	}

}
