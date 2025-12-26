package mg.fruive.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import mg.fruive.exception.InvalidValueException;

@Getter
@NoArgsConstructor
public class CartEntry {
	
	private Integer productId;
	private Float amount;
	
	public CartEntry(Integer productId, Float amount) throws InvalidValueException {
		
		this.productId = productId;
		this.setAmount(amount);
		
	}
	
	public void setAmount(Float amount) throws InvalidValueException {
		
		if(amount <= 0)
			throw new InvalidValueException("Amount to put into cart should be positive");
		
		this.amount = amount;
		
	}

}
