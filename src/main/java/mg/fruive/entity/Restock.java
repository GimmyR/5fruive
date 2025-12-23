package mg.fruive.entity;

import java.time.LocalDateTime;

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
public class Restock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDateTime restockDate;
	
	@ManyToOne
	@JoinColumn
	private Account administrator;
	
	@ManyToOne
	@JoinColumn
	private Product product;
	
	private Float amount;
	
	public Restock(LocalDateTime restockDate, Account administrator, Product product, Float amount) throws InvalidValueException {
		
		if(amount <= 0)
			throw new InvalidValueException("amount", "Amount to set is invalid (negative or equals to 0)");
		
		this.restockDate = restockDate;
		this.administrator = administrator;
		this.product = product;
		this.amount = amount;
		
	}

}
