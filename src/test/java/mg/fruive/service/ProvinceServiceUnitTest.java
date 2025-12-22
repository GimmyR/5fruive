package mg.fruive.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mg.fruive.entity.Province;
import mg.fruive.repository.ProvinceRepository;

@ExtendWith(MockitoExtension.class)
public class ProvinceServiceUnitTest {
	
	@Mock
	private ProvinceRepository provinceRepository;
	
	@InjectMocks
	private ProvinceService provinceService;
	
	@Test
	public void testFindAll() {
		
		List<Province> provinces = Arrays.asList(new Province(1, "Antananarivo"));
		
		when(provinceService.findAll()).thenReturn(provinces);
		
		List<Province> provs = provinceService.findAll();
		assertEquals(1, provs.size());
		assertEquals("Antananarivo", provs.getFirst().getName());
		
	}

}
