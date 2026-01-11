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
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.record.CartEntryForm;
import mg.fruive.repository.ProductRepository;

@Service
@AllArgsConstructor
public class CartService {
	
	private final HttpSession httpSession;
	private ProductRepository productRepository;
	
	private Cart getCart() {
		
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
	
	public Integer addToCart(Integer productId, Float amount) throws NotFoundException, OutOfStockException {
		
		Cart cart = this.getCart();
		
		if(cart == null)
			cart = new Cart();
		
		return this.addOrPut(cart, productId, amount);
		
	}
	
	private Integer addOrPut(Cart cart, Integer productId, Float amount) throws NotFoundException, OutOfStockException {
		
		Product product = this.findProduct(productId, false);
		cart.add(productId, amount, product.getInStock());
		httpSession.setAttribute("cart", cart);
		return cart.size();
		
	}
	
	private Product findProduct(Integer productId, Boolean withCategoryAndProvince) throws NotFoundException {
		
		Optional<Product> opt = null;
		
		if(withCategoryAndProvince)
			opt = productRepository.findByIdWithCategoryAndProvince(productId);
		
		else opt = productRepository.findById(productId);
		
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
				
				Product product = this.findProduct(entry.getProductId(), true);
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
			
			cart.remove(productId);
			
			if(cart.size() > 0)
				httpSession.setAttribute("cart", cart);
			
			else this.resetCart();
			
		} else throw new NotFoundException("Cart not found");
		
	}
	
	public void saveCart(List<CartEntryForm> entries) throws NotFoundException, OutOfStockException {
		
		Cart cart = this.getCart();
		
		if(cart == null)
			cart = new Cart();
		
		for(int i = 0; i < entries.size(); i++) {
			
			Integer productId = entries.get(i).productId();
			Float amount = entries.get(i).amount();
			Product product = this.findProduct(productId, false);
			cart.put(productId, amount, product.getInStock());
			
		} httpSession.setAttribute("cart", cart);
		
	}

}
