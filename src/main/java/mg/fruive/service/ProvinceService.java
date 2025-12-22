package mg.fruive.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import mg.fruive.entity.Province;
import mg.fruive.repository.ProvinceRepository;

@Service
@AllArgsConstructor
public class ProvinceService {
	
	private ProvinceRepository provinceRepository;
	
	public List<Province> findAll() {
		
		return provinceRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
	}

}
