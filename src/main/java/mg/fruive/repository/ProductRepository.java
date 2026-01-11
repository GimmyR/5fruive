package mg.fruive.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("select p from Product p join fetch p.category join fetch p.province where p.id = :id")
	Optional<Product> findByIdWithCategoryAndProvince(@Param("id") Integer id);
	
	Page<Product> findByNameLikeIgnoreCase(String name, Pageable pageable);
	
	@Query("select p from Product p join fetch p.category join fetch p.province where lower(p.name) like lower(:name)")
	Page<Product> findByNameWithCategoryAndProvince(@Param("name") String name, Pageable pageable);

}
