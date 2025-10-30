package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.PurchaseDetail;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Integer> {

}
