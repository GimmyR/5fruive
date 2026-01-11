package mg.fruive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
	
	@Query("select p from Purchase p left join fetch p.details where p.id = :id")
	Optional<Purchase> findByIdWithDetails(@Param("id") Integer id);
	
	@Query("select p from Purchase p join fetch p.purchaser left join fetch p.details")
	List<Purchase> findAllWithPurchaserAndDetails(Sort sort);

}
