package mg.ischool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.ischool.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
