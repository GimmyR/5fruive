package mg.fruive.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mg.fruive.entity.CartEntry;
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
	
	public void prepareCartView(Model model) throws NotFoundException {
		
		Map<Integer, Float> cart = this.getCart();
		
		if(cart != null) {
			
			List<Product> products = new ArrayList<Product>();
			List<Float> amounts = new ArrayList<Float>();
			Float totalPrice = (float) 0;
			
			for(Map.Entry<Integer, Float> entry : cart.entrySet()) {
				
				Product product = this.findProduct(entry.getKey());
				products.add(product);
				amounts.add(entry.getValue());
				totalPrice += (float)(product.getPrice() * entry.getValue());
				
			}
			
			model.addAttribute("products", products);
			model.addAttribute("amounts", amounts);
			model.addAttribute("totalPrice", totalPrice);
			
		}
		
	}
	
	public void resetCart() {
		
		httpSession.removeAttribute("cart");
		
	}
	
	public void removeProduct(Integer productId) throws NotFoundException {
		
		Map<Integer, Float> cart = this.getCart();
		
		if(cart != null) {
			
			if(cart.remove(productId) == null)
				throw new NotFoundException("Product not found in cart.");
			
			if(cart.size() > 0)
				httpSession.setAttribute("cart", cart);
			
			else this.resetCart();
			
		}
		
	}
	
	public void saveCart(List<CartEntry> entries) throws NotFoundException, OutOfStockException {
		
		this.validateCart(entries);
		this.saveCartEntries(entries);
		
	}
	
	private void validateCart(List<CartEntry> entries) throws NotFoundException, OutOfStockException {
		
		for(int i = 0; i < entries.size(); i++) {
			
			Integer productId = entries.get(i).getProductId();
			Float amount = entries.get(i).getAmount();
			this.validateInput(productId, amount);
			Product product = this.findProduct(productId);
			this.validateInStock(product, amount);
			
		}
		
	}
	
	private void saveCartEntries(List<CartEntry> entries) {
		
		Map<Integer, Float> cart = this.getCart();
		
		if(cart == null)
			cart = new HashMap<>();
		
		for(int i = 0; i < entries.size(); i++) {
			
			Integer productId = entries.get(i).getProductId();
			Float amount = entries.get(i).getAmount();
			cart.put(productId, amount);
			
		} httpSession.setAttribute("cart", cart);
		
	}

}
