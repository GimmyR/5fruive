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
import mg.fruive.exception.IsMissingException;
import mg.fruive.exception.NegativeOrZeroException;

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
	
	public Restock(LocalDateTime restockDate, Account administrator, Product product, Float amount) throws NegativeOrZeroException, IsMissingException {
		
		if(amount == null)
			throw new IsMissingException("Amount value is missing");
		
		if(amount <= 0)
			throw new NegativeOrZeroException("Amount value should be positive");
		
		this.restockDate = restockDate;
		this.administrator = administrator;
		this.product = product;
		this.amount = amount;
		
	}

}
