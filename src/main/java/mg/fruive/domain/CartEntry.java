package mg.fruive.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.fruive.exception.InvalidValueException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartEntry {
	
	private Integer productId;
	private Float amount;
	
	public void setAmount(Float amount) throws InvalidValueException {
		
		if(amount <= 0)
			throw new InvalidValueException("amount", "Amount to add to cart is invalid (negative or equals to 0)");
		
		this.amount = amount;
		
	}

}
