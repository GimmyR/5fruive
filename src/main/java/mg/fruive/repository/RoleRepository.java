package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
