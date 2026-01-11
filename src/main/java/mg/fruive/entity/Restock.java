package mg.fruive.entity;

import java.time.LocalDateTime;

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
public class Restock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDateTime restockDate;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Account administrator;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Product product;
	
	private Float amount;

}
