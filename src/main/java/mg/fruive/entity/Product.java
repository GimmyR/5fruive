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
	
	private Float inStock;
	
	private Double price;
	
	private String unit;
	
	private String image;
	
	// METHODS :
	
	public void addToInStock(Float amount) {
		
		this.inStock += amount;
		
	}
	
	public void subtractToInStock(Float amount) {
		
		this.inStock -= amount;
		
	}

}
