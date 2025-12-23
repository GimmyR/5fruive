package mg.fruive.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mg.fruive.domain.Cart;
import mg.fruive.domain.CartEntry;
import mg.fruive.entity.Product;
import mg.fruive.exception.InvalidValueException;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.repository.ProductRepository;

@Service
@AllArgsConstructor
public class CartService {
	
	private final HttpSession httpSession;
	private ProductRepository productRepository;
	
	public Cart getCart() {
		
		Object cart = httpSession.getAttribute("cart");
		
		if(cart != null)
			return (Cart) cart;
		
		else return null;
		
	}
	
	public Integer getCartSize() {
		
		Cart cart = this.getCart();
		
		if(cart != null)
			return cart.size();
		
		else return 0;
		
	}
	
	public Integer addToCart(Integer productId, Float amount) throws NotFoundException, OutOfStockException, InvalidValueException {
		
		this.validateInput(productId, amount);
		Cart cart = this.getCart();
		
		if(cart == null)
			cart = new Cart();
		
		return this.addOrPut(cart, productId, amount);
		
	}
	
	private void validateInput(Integer productId, Float amount) {
		
		if(productId == null)
			throw new NullPointerException("Product ID is missing");
		
		else if(amount == null)
			throw new NullPointerException("Amount of a product (ID: " + productId + ") is missing");
		
	}
	
	private Integer addOrPut(Cart cart, Integer productId, Float amount) throws NotFoundException, OutOfStockException, InvalidValueException {
		
		Product product = this.findProduct(productId);
		cart.add(productId, amount, product.getInStock());
		httpSession.setAttribute("cart", cart);
		return cart.size();
		
	}
	
	private Product findProduct(Integer productId) throws NotFoundException {
		
		Optional<Product> opt = productRepository.findById(productId);
		
		if(opt.isEmpty())
			throw new NotFoundException("Product not found");
		
		else return opt.get();
		
	}
	
	public Map<String, Object> prepareCartView() throws NotFoundException {
		
		Map<String, Object> result = new HashMap<>();
		Cart cart = this.getCart();
		
		if(cart != null) {
			
			List<Product> products = new ArrayList<Product>();
			List<Float> amounts = new ArrayList<Float>();
			Float totalPrice = (float) 0;
			
			for(CartEntry entry : cart.getEntries()) {
				
				Product product = this.findProduct(entry.getProductId());
				products.add(product);
				amounts.add(entry.getAmount());
				totalPrice += (float)(product.getPrice() * entry.getAmount());
				
			}
			
			result.put("products", products);
			result.put("amounts", amounts);
			result.put("totalPrice", totalPrice);
			
		} return result;
		
	}
	
	public void resetCart() {
		
		httpSession.removeAttribute("cart");
		
	}
	
	public void removeProduct(Integer productId) throws NotFoundException {
		
		Cart cart = this.getCart();
		
		if(cart != null) {
			
			if(cart.remove(productId) == null)
				throw new NotFoundException("Product not found in cart.");
			
			if(cart.size() > 0)
				httpSession.setAttribute("cart", cart);
			
			else this.resetCart();
			
		}
		
	}
	
	public void saveCart(List<CartEntry> entries) throws NotFoundException, OutOfStockException, InvalidValueException {
		
		Cart cart = this.getCart();
		
		if(cart == null)
			cart = new Cart();
		
		for(int i = 0; i < entries.size(); i++) {
			
			Integer productId = entries.get(i).getProductId();
			Float amount = entries.get(i).getAmount();
			this.validateInput(productId, amount);
			Product product = this.findProduct(productId);
			cart.add(productId, amount, product.getInStock());
			
		} httpSession.setAttribute("cart", cart);
		
	}

}
