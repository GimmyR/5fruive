package mg.fruive.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import jakarta.servlet.http.HttpSession;
import mg.fruive.domain.Cart;
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

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceUnitTest {
	
	@Mock
	private HttpSession httpSession;
	
	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private PurchaseRepository purchaseRepository;
	
	@Mock
	private PurchaseDetailRepository purchaseDetailRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private MostPurchasedRepository mostPurchasedRepository;
	
	@InjectMocks
	private PurchaseService purchaseService;
	
	@Test
	public void testFindPurchase() throws NotFoundException {
		
		Purchase purchase = new Purchase(1, LocalDateTime.now(), null, null);
		when(purchaseRepository.findById(1)).thenReturn(Optional.of(purchase));
		Purchase purchase2 = purchaseService.findPurchase(1);
		assertEquals(true, purchase.equals(purchase2));
		
	}
	
	@Test
	public void testFindPurchaseShouldThrowException() throws NotFoundException {
		
		when(purchaseRepository.findById(1)).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> purchaseService.findPurchase(1));
		
	}
	
	@Test
	public void testProvideAccountFullname() throws NotFoundException {
		
		Principal auth = new Principal() {
			@Override
			public String getName() { return "johndoe"; }
		};
		
		Account account = new Account(1, "John", "Doe", "johndoe", "mdpJohn", new ArrayList<>());
		when(accountRepository.findByUsername(auth.getName())).thenReturn(Optional.of(account));
		String name = purchaseService.provideAccountFullname(auth);
		assertEquals(true, name.equals(account.getFirstname() + " " + account.getLastname()));
		
	}
	
	@Test
	public void testProvideAccountFullnameShouldThrowException() throws NotFoundException {
		
		Principal auth = new Principal() {
			@Override
			public String getName() { return "johndoe"; }
		};
		
		when(accountRepository.findByUsername(auth.getName())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> purchaseService.provideAccountFullname(auth));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindMostPurchased() {
		
		List<MostPurchased> mostPurchased = Arrays.asList(new MostPurchased(1, "Banana", (float) 10));
		when(mostPurchasedRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(mostPurchased);
		Map<String, Object> map = purchaseService.findMostPurchased();
		List<String> labels = (List<String>) map.get("labels");
		assertEquals("Banana", labels.getFirst());
		
	}
	
	@Test
	public void testBuyProducts() throws NotFoundException, OutOfStockException {
		
		Principal auth = new Principal() {
			@Override
			public String getName() { return "johndoe"; }
		};
		
		Account account = new Account(1, "John", "Doe", "johndoe", "mdpJohn", new ArrayList<>());
		Product product = new Product(1, null, null, "Banana", (float) 1000.0, 2000.0, "kg", "banana.jpg");
		Cart cart = new Cart();
		cart.add(product.getId(), (float) 1, product.getInStock());
		Purchase pur = new Purchase(1, null, account, new ArrayList<>());
		
		when(accountRepository.findByUsername(auth.getName())).thenReturn(Optional.of(account));
		when(this.httpSession.getAttribute("cart")).thenReturn(cart);
		when(productRepository.findById(1)).thenReturn(Optional.of(product));
		when(purchaseRepository.save(any(Purchase.class))).thenReturn(pur);
		when(purchaseDetailRepository.save(any(PurchaseDetail.class))).thenReturn(new PurchaseDetail(1, pur, product, (float) 1, (float) 2000));
		
		Purchase purchase = purchaseService.buyProducts(auth, "65376");
		assertEquals("Banana", purchase.getDetails().getFirst().getId()); // A CHANGER LA VALEUR A COMPARER
		
	}

}
