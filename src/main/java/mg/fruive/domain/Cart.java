package mg.fruive.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mg.fruive.exception.NotFoundException;
import mg.fruive.exception.OutOfStockException;

@Getter
@AllArgsConstructor
public class Cart {
	
	// ATTRIBUTES
	
	private List<CartEntry> entries;
	
	// CONSTRUCTORS
	
	public Cart() {
		
		this.entries = new ArrayList<CartEntry>();
		
	}
	
	// METHODS
	
	public Integer size() {
		
		return entries.size();
		
	}
	
	private int findIndexByProductId(Integer productId) throws NotFoundException {
		
		int index = entries.indexOf(new CartEntry(productId, null));
		
		if(index < 0)
			throw new NotFoundException("Product ID not found");
		
		return index;
		
	}
	
	public Float getAmountByProductId(Integer productId) throws NotFoundException {
		
		int index = this.findIndexByProductId(productId);
		return entries.get(index).getAmount();
		
	}
	
	public void add(Integer productId, Float amount, Float inStock) throws OutOfStockException {
		
		Float number = null;
		
		try {
			
			number = this.getAmountByProductId(productId);
			
		} catch (NotFoundException e) {
			
			number = (float) 0;
			
		} this.put(productId, number + amount, inStock);
		
	}
	
	public void put(Integer productId, Float amount, Float inStock) throws OutOfStockException {
		
		if(amount > inStock)
			throw new OutOfStockException(String.format("Amount in stock (%.2f) is fewer than amount to buy : %.2f", inStock, amount));
		
		try {
			
			Integer index = this.findIndexByProductId(productId);
			this.entries.get(index).setAmount(amount);
			
		} catch (NotFoundException e) {
			
			this.entries.add(new CartEntry(productId, amount));
			
		}
		
	}
	
	public void remove(Integer productId) throws NotFoundException {
		
		if(!this.entries.remove(new CartEntry(productId, null)))
			throw new NotFoundException("Product ID not found");
		
	}

}
