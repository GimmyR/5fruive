package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.MostPurchased;

@Repository
public interface MostPurchasedRepository extends JpaRepository<MostPurchased, Integer> {

}
