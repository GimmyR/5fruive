package mg.fruive.entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Immutable
@Table(name = "most_purchased")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MostPurchased {
	
	@Id
	private Integer id;
	
	private String name;
	
	private Float amount;

}
