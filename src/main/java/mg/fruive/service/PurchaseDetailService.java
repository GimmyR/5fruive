package mg.fruive.service;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import mg.fruive.repository.PurchaseDetailRepository;

@Service
@AllArgsConstructor
public class PurchaseDetailService {
	
	private PurchaseDetailRepository purchaseDetailRepository;

}
