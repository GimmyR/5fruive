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
import mg.fruive.exception.OutOfStockException;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn
	private Purchase purchase;
	
	@ManyToOne
	@JoinColumn
	private Product product;
	
	private Float amount;
	private Float price;
	
	public PurchaseDetail(Purchase purchase, Product product, Float amount) throws InvalidValueException, OutOfStockException {
		
		if(amount <= 0)
			throw new InvalidValueException(String.format("Amount of %s should be positive", product.getName()));
		
		if(product.getInStock() < amount)
			throw new OutOfStockException(product.getName() + " in stock (" + product.getInStock() + " " + product.getUnit() + ") is lower than what you want to buy (" + amount + " " + product.getUnit() +")");
		
		this.purchase = purchase;
		this.product = product;
		this.amount = amount;
		this.calculatePrice();
		
	}
	
	// METHODS :
	
	private void calculatePrice() {
		
		this.price = (float) (this.amount * this.product.getPrice());
		
	}

}
