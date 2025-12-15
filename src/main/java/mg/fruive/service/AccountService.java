package mg.fruive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Account;
import mg.fruive.exception.NotFoundException;
import mg.fruive.repository.AccountRepository;

@Service
@AllArgsConstructor
public class AccountService {
	
	private AccountRepository accountRepository;
	
	public List<Account> findAll(Model model) {
		
		List<Account> accounts = accountRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
		if(model != null)
			model.addAttribute("accounts", accounts);
		
		return accounts;
		
	}
	
	public Account findUniqueByUsername(String username) throws NotFoundException {
		
		Optional<Account> opt = accountRepository.findByUsername(username);
		
		if(opt.isEmpty())
			throw new NotFoundException("Account not found");
		
		return opt.get();
		
	}

}
