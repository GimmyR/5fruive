package mg.fruive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.fruive.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
