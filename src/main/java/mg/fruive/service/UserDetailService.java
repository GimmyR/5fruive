package mg.fruive.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mg.fruive.entity.Account;
import mg.fruive.repository.AccountRepository;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Account> account = accountRepository.findByUsername(username);
		
		if(account.isEmpty())
			throw new UsernameNotFoundException(String.format("User %s not found", username));
		
		return User
				.withUsername(account.get().getUsername())
				.password(String.format("{noop}%s", account.get().getPassword()))
				.roles(account.get().getRoles().stream().map(role -> role.getName()).toArray(String[]::new))
				.build();
		
	}

}