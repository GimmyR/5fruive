package mg.fruive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Category category;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
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
