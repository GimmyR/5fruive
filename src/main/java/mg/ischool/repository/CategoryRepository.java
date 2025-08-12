package mg.ischool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.ischool.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
