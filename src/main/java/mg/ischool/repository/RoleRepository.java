package mg.ischool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.ischool.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
