package mg.fruive.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
	
	public void saveRestock(Account administrator, Product product, Float amount) throws NegativeException {
		
		if(amount <= 0)
			throw new NegativeException("Amount value must be a positive value");
		
		Restock restock = new Restock();
		restock.setRestockDate(LocalDateTime.now());
		restock.setAdministrator(administrator);
		restock.setProduct(product);
		restock.setAmount(amount);
		
		restockRepository.save(restock);
		
	}
	
	public List<Restock> findAll(Model model) {
		
		List<Restock> restocks = restockRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
		if(model != null)
			model.addAttribute("restocks", restocks);
		
		return restocks;
		
	}

}
