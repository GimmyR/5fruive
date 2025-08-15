package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
