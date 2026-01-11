package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Account;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Optional<Account> findByUsername(String username);
	
	@Query("select a from Account c left join fetch c.roles where c.username = :username")
	Optional<Account> findByUsernameWithRoles(@Param("username") String username);

}
