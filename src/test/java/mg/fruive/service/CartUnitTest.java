package mg.fruive.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import mg.fruive.domain.Cart;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;

@ExtendWith(MockitoExtension.class)
public class CartUnitTest {
	
	@Test
	public void testGetAmountByProductIdShouldThrowException() {
		
		Cart cart = new Cart();
		assertThrows(NotFoundException.class, () -> cart.getAmountByProductId(1));
		
	}
	
	@Test
	public void testPut() throws OutOfStockException, NotFoundException {
		
		Cart cart = new Cart();cart.put(2, (float) 1, (float) 90);
		cart.put(3, (float) 0.2, (float) 80);
		cart.put(1, (float) 0.5, (float) 60);
		cart.put(4, (float) 0.4, (float) 30);
		assertEquals((float) 0.5, cart.getAmountByProductId(1));
		
	}
	
	@Test
	public void testPutShouldThrowException() {
		
		Cart cart = new Cart();
		assertThrows(OutOfStockException.class, () -> cart.put(1, (float) 2, (float) 1));
		
	}
	
	@Test
	public void testAdd() throws OutOfStockException, NotFoundException {
		
		Cart cart = new Cart();
		cart.add(2, (float) 1, (float) 90);
		cart.add(3, (float) 0.2, (float) 80);
		cart.add(1, (float) 0.5, (float) 60);
		cart.add(4, (float) 0.4, (float) 30);
		cart.add(1, (float) 0.4, (float) 60);
		assertEquals((float) 0.9, cart.getAmountByProductId(1));
		
	}
	
	@Test
	public void testRemove() throws OutOfStockException, NotFoundException {
		
		Cart cart = new Cart();
		cart.put(2, (float) 1, (float) 90);
		cart.put(3, (float) 0.2, (float) 80);
		cart.put(1, (float) 0.5, (float) 60);
		cart.put(4, (float) 0.4, (float) 30);
		assertEquals((float) 0.5, cart.getAmountByProductId(1));
		cart.remove(1);
		assertThrows(NotFoundException.class, () -> cart.getAmountByProductId(1));
		
	}

}
