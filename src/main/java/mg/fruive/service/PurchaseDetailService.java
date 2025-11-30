package mg.fruive.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import mg.fruive.entity.PurchaseDetail;
import mg.fruive.repository.PurchaseDetailRepository;

@Service
@AllArgsConstructor
public class PurchaseDetailService {
	
	private PurchaseDetailRepository purchaseDetailRepository;
	
	public List<PurchaseDetail> findAll(Model model) {
		
		List<PurchaseDetail> details = purchaseDetailRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
		if(model != null)
			model.addAttribute("details", details);
		
		return details;
		
	}

}
