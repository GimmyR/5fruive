package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Restock;

@Repository
public interface RestockRepository extends JpaRepository<Restock, Integer> {

}
