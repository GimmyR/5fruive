package mg.fruive.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mg.fruive.entity.Account;
import mg.fruive.entity.Cart;
import mg.fruive.entity.CartEntry;
import mg.fruive.entity.Product;
import mg.fruive.entity.Purchase;
import mg.fruive.entity.PurchaseDetail;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.repository.AccountRepository;
import mg.fruive.repository.ProductRepository;
import mg.fruive.repository.PurchaseDetailRepository;
import mg.fruive.repository.PurchaseRepository;

@Service
@AllArgsConstructor
public class PurchaseService {
	
	private final HttpSession httpSession;
	private AccountRepository accountRepository;
	private PurchaseRepository purchaseRepository;
	private PurchaseDetailRepository purchaseDetailRepository;
	private ProductRepository productRepository;
	
	public Purchase buyProducts(Principal auth, String cardCode) throws NotFoundException, OutOfStockException {
		
		Purchase result = null;
		
		this.validateCardCode(cardCode);
		Account account = this.validateAccount(auth);
		Cart cart = this.getCart();
		result = new Purchase();
		result.setPurchaseDate(LocalDateTime.now());
		result.setPurchaser(account);
		result = purchaseRepository.save(result);
		this.saveDetails(result, cart);
		
		return result;
		
	}
	
	private Cart getCart() throws NotFoundException {
		
		Object cart = httpSession.getAttribute("cart");
		
		if(cart != null)
			return (Cart) cart;
		
		else throw new NotFoundException("Cart not found");
		
	}
	
	private void validateCardCode(String cardCode) throws NotFoundException {
		
		if(cardCode == null || cardCode.isBlank())
			throw new NotFoundException("Card code not found");
		
	}
	
	private Account validateAccount(Principal auth) {
		
		Optional<Account> account = accountRepository.findByUsername(auth.getName());
		
		return account.get();
		
	}
	
	private void saveDetails(Purchase purchase, Cart cart) throws NotFoundException, OutOfStockException {
		
		List<PurchaseDetail> details = new ArrayList<PurchaseDetail>();
		List<CartEntry> entries = cart.getEntries();
		
		for(int i = 0; i < entries.size(); i++) {
			
			CartEntry entry = entries.get(i);
			Product product = this.findProduct(entry.getProductId());
			PurchaseDetail detail = this.saveDetail(product, purchase, entry);
			details.add(detail);
			this.saveProduct(product, entry);
			
		}
		
		purchase.setDetails(details);
		purchaseRepository.save(purchase);
		httpSession.removeAttribute("cart");
		
	}
	
	private Product findProduct(Integer productId) throws NotFoundException {
		
		Optional<Product> product = productRepository.findById(productId);
		
		if(product.isEmpty())
			throw new NotFoundException("Product not found");
		
		else return product.get();
		
	}
	
	private void validateAmount(Product product, Float amount) throws OutOfStockException {
		
		if(product.getInStock() < amount)
			throw new OutOfStockException(product.getName() + " in stock (" + product.getInStock() + " " + product.getUnit() + ") is lower than what you want to buy (" + amount + " " + product.getUnit() +")");
		
	}
	
	private PurchaseDetail saveDetail(Product product, Purchase purchase, CartEntry entry) throws OutOfStockException {
		
		PurchaseDetail detail = new PurchaseDetail();
		detail.setPurchase(purchase);
		detail.setProduct(product);
		this.validateAmount(product, entry.getAmount());
		detail.setAmount(entry.getAmount());
		detail.setPrice((float) (detail.getAmount() * product.getPrice()));
		return purchaseDetailRepository.save(detail);
		
	}
	
	private void saveProduct(Product product, CartEntry entry) {
		
		product.setInStock(product.getInStock() - entry.getAmount());
		productRepository.save(product);
		
	}
	
	public Purchase findPurchase(Integer id) throws NotFoundException {
		
		Optional<Purchase> purchase = purchaseRepository.findById(id);
		
		if(purchase.isEmpty())
			throw new NotFoundException("Purchase not found");
		
		else return purchase.get();
		
	}
	
	public void provideAccountFullname(Model model, Principal auth) throws NotFoundException {
		
		Optional<Account> account = accountRepository.findByUsername(auth.getName());
		
		if(account.isPresent())
			model.addAttribute("fullname", account.get().getFirstname() + " " + account.get().getLastname());
		
		else throw new NotFoundException("Account not found");
		
	}

}
