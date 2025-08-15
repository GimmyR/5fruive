package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {

}
