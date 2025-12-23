package mg.fruive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mg.fruive.exception.InvalidValueException;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn
	private Category category;
	
	@ManyToOne
	@JoinColumn
	private Province province;
	
	private String name;
	
	private Double inStock;
	
	private Double price;
	
	private String unit;
	
	private String image;
	
	// METHODS :
	
	public void addToInStock(Float amount) throws InvalidValueException {
		
		this.amountShouldBeStrictlyPositive(amount, "Amount to add is invalid (negative or equals to 0)");
		this.inStock += amount;
		
	}
	
	public void subtractToInStock(Float amount) throws InvalidValueException {
		
		this.amountShouldBeStrictlyPositive(amount, "Amount to subtract is invalid (negative or equals to 0)");
		this.inStock -= amount;
		
	}
	
	private void amountShouldBeStrictlyPositive(Float amount, String message) throws InvalidValueException {
		
		if(amount <= 0)
			throw new InvalidValueException("amount", message);
		
	}

}
