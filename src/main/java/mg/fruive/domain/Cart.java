package mg.fruive.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mg.fruive.exception.InvalidValueException;
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
	
	public Float getAmountByProductId(Integer productId) {
		
		Float result = null;
		
		for(int i = 0; i < entries.size(); i++) {
			
			if(entries.get(i).getProductId() == productId) {
				
				result = entries.get(i).getAmount();
				break;
				
			}
			
		} return result;
		
	}
	
	public void add(Integer productId, Float amount, Float inStock) throws InvalidValueException, OutOfStockException {
		
		if(amount <= 0)
			throw new InvalidValueException("Amount to add to cart should be positive");
		
		Float number = this.getAmountByProductId(productId);
		
		if(number == null)
			number = (float) 0;
		
		this.put(productId, number + amount, inStock);
		
	}
	
	public void put(Integer productId, Float amount, Float inStock) throws InvalidValueException, OutOfStockException {
		
		if(amount > inStock)
			throw new OutOfStockException(String.format("Amount in stock (%.2f) is fewer than amount to buy : %.2f", inStock, amount));
		
		Integer index = null;
		
		for(int i = 0; i < entries.size(); i++) {
			
			if(entries.get(i).getProductId() == productId) {
				
				entries.get(i).setAmount(amount);
				index = i;
				break;
				
			}
			
		}
		
		if(index == null)
			entries.add(new CartEntry(productId, amount));
		
	}
	
	public Float remove(Integer productId) {
		
		Float result = null;
		
		for(int i = 0; i < entries.size(); i++) {
			
			if(entries.get(i).getProductId() == productId) {
				
				result = entries.get(i).getAmount();
				entries.remove(i);
				break;
				
			}
			
		} return result;
		
	}

}
