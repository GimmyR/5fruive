package mg.fruive.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mg.fruive.domain.Cart;
import mg.fruive.domain.CartEntry;
import mg.fruive.entity.Account;
import mg.fruive.entity.MostPurchased;
import mg.fruive.entity.Product;
import mg.fruive.entity.Purchase;
import mg.fruive.entity.PurchaseDetail;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;
import mg.fruive.repository.AccountRepository;
import mg.fruive.repository.MostPurchasedRepository;
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
	private MostPurchasedRepository mostPurchasedRepository;

	@Transactional(rollbackFor = {NotFoundException.class, OutOfStockException.class})
	public Purchase buyProducts(Principal auth, String cardCode) throws NotFoundException, OutOfStockException {
		
		Purchase purchase = null;
		
		Account account = this.validateAccount(auth);
		purchase = new Purchase(null, LocalDateTime.now(), account, new ArrayList<>());
		purchase = purchaseRepository.save(purchase);
		Cart cart = this.getCart();
		this.saveDetails(purchase, cart);
		
		return purchase;
		
	}
	
	private Cart getCart() throws NotFoundException {
		
		Object cart = httpSession.getAttribute("cart");
		
		if(cart != null)
			return (Cart) cart;
		
		else throw new NotFoundException("Cart not found");
		
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
		
		purchase.replaceDetails(details);
		purchaseRepository.save(purchase);
		httpSession.removeAttribute("cart");
		
	}
	
	private Product findProduct(Integer productId) throws NotFoundException {
		
		Optional<Product> product = productRepository.findById(productId);
		
		if(product.isEmpty())
			throw new NotFoundException("Product not found");
		
		else return product.get();
		
	}
	
	private PurchaseDetail saveDetail(Product product, Purchase purchase, CartEntry entry) throws OutOfStockException {
		
		PurchaseDetail detail = new PurchaseDetail(purchase, product, entry.getAmount());
		return purchaseDetailRepository.save(detail);
		
	}
	
	private void saveProduct(Product product, CartEntry entry) {
		
		product.subtractToInStock(entry.getAmount());
		productRepository.save(product);
		
	}
	
	public Purchase findPurchase(Integer id) throws NotFoundException {
		
		Optional<Purchase> purchase = purchaseRepository.findById(id);
		
		if(purchase.isEmpty())
			throw new NotFoundException("Purchase not found");
		
		else return purchase.get();
		
	}
	
	public Purchase findPurchaseWithDetails(Integer id) throws NotFoundException {
		
		Optional<Purchase> purchase = purchaseRepository.findByIdWithDetails(id);
		
		if(purchase.isEmpty())
			throw new NotFoundException("Purchase not found");
		
		else return purchase.get();
		
	}
	
	public String provideAccountFullname(Principal auth) throws NotFoundException {
		
		Optional<Account> account = accountRepository.findByUsername(auth.getName());
		
		if(account.isPresent())
			return account.get().getFirstname() + " " + account.get().getLastname();
		
		else throw new NotFoundException("Account not found");
		
	}
	
	public List<Purchase> findAll() {
		
		return purchaseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
	}
	
	public Map<String, Object> findMostPurchased() {
		
		Map<String, Object> map = new HashMap<>();
		List<MostPurchased> result = mostPurchasedRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		List<String> labels = new ArrayList<String>();
		List<Float> values = new ArrayList<Float>();
		
		result.forEach(purchase -> {
			labels.add(purchase.getName());
			values.add(purchase.getAmount());
		});
		
		map.put("labels", labels);
		map.put("values", values);
			
		return map;
		
	}

}
