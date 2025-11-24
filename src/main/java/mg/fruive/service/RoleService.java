package mg.fruive.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.AllArgsConstructor;
import mg.fruive.entity.Role;
import mg.fruive.repository.RoleRepository;

@Service
@AllArgsConstructor
public class RoleService {
	
	private RoleRepository roleRepository;
	
	public List<Role> findAll(Model model) {
		
		List<Role> roles = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		
		if(model != null)
			model.addAttribute("roles", roles);
		
		return roles;
		
	}

}
