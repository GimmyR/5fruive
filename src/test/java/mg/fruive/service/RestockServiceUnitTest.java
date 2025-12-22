package mg.fruive.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mg.fruive.exception.NegativeException;
import mg.fruive.repository.RestockRepository;

@ExtendWith(MockitoExtension.class)
public class RestockServiceUnitTest {
	
	@Mock
	private RestockRepository restockRepository;
	
	@InjectMocks
	private RestockService restockService;
	
	@Test
	public void testSaveRestockThrowingException() {
		
		assertThrows(
				NegativeException.class, 
				() -> restockService.saveRestock(null, null, (float)-10)
		);
		
	}

}
