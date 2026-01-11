package mg.fruive.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Restock;

@Repository
public interface RestockRepository extends JpaRepository<Restock, Integer> {
	
	@Query("select r from Restock r join fetch r.administrator join fetch r.product")
	List<Restock> findAllWithAdministratorAndProduct(Sort sort);

}
