package mg.fruive.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import mg.fruive.entity.Account;
import mg.fruive.entity.Product;
import mg.fruive.entity.Restock;
import mg.fruive.exception.NegativeException;
import mg.fruive.repository.RestockRepository;

@Service
@AllArgsConstructor
public class RestockService {
	
	private RestockRepository restockRepository;
	
	public Restock saveRestock(Account administrator, Product product, Float amount) throws NegativeException {
		
		Restock restock = new Restock();
		restock.setRestockDate(LocalDateTime.now());
		restock.setAdministrator(administrator);
		restock.setProduct(product);
		restock.setAmount(amount);
		
		return restockRepository.save(restock);
		
	}
	
	public List<Restock> findAll() {
		
		return restockRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
	}

}
