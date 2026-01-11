package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Account;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	@Query("select a from Account a left join fetch a.roles order by a.id asc")
	List<Account> findAllWithRoles();
	
	Optional<Account> findByUsername(String username);
	
	@Query("select a from Account a left join fetch a.roles where a.username = :username")
	Optional<Account> findByUsernameWithRoles(@Param("username") String username);

}
