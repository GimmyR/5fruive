package mg.fruive.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mg.fruive.entity.Account;
import mg.fruive.entity.Role;
import mg.fruive.exception.NotFoundException;
import mg.fruive.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceUnitTest {
	
	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private AccountService accountService;
	
	@Test
	public void findUniqueByUsernameShouldReturnAccount() throws NotFoundException {
		
		List<Role> roles = Arrays.asList(new Role(1, "Client"));
		Account account = new Account(1, "John", "Doe", "johndoe", "mdpJohn", roles);
		String username = "johndoe";
		
		when(accountRepository.findByUsername(username)).thenReturn(Optional.of(account));
		
		Account acc = accountService.findUniqueByUsername(username);
		assertNotNull(acc);
		assertEquals("Doe", acc.getLastname());
		
	}
	
	@Test
	public void findUniqueByUsernameShouldThrowException() {
		
		String username = "johndoe";
		when(accountRepository.findByUsername(username)).thenReturn(Optional.empty());
		assertThrows(
				NotFoundException.class, 
				() -> accountService.findUniqueByUsername(username)
		);
		
	}

}
