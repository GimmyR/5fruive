package mg.ischool;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import mg.ischool.entity.Account;
import mg.ischool.entity.Category;
import mg.ischool.entity.Product;
import mg.ischool.entity.Province;
import mg.ischool.entity.Role;
import mg.ischool.repository.AccountRepository;
import mg.ischool.repository.CategoryRepository;
import mg.ischool.repository.ProductRepository;
import mg.ischool.repository.ProvinceRepository;
import mg.ischool.repository.RoleRepository;

@SpringBootTest
class FruiveApplicationTests {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	void contextLoads() {
	}
	
	/*@Test
	void testRole() {
		
		//roleRepository.save(new Role(null, "Client"));
		//roleRepository.save(new Role(null, "Admin"));
		
		roleRepository.findAll().forEach(role -> { System.out.println(role.getName()); });
		
	}*/
	
	/*@Test
	void testAccount() {
		
		//Account account = new Account(null, "Muriel", "Ramanantoanina", "murielram", "mdpMuriel", null);
		//accountRepository.save(account);
		
		accountRepository.findAll().forEach(acc -> {
			System.out.println(acc.getId() + " - " + acc.getFirstname());
		});
		
	}*/
	
	/*@Test
	void testAccountRoles() {
		
		//List<Role> roles = roleRepository.findAll();
		//Account account = accountRepository.findById(3).get();
		//account.setRoles(roles.subList(0, 1));
		//accountRepository.save(account);
		
		accountRepository.findAll().forEach(acc -> {
			System.out.println(acc.getFirstname() + " :");
			acc.getRoles().forEach(role -> System.out.println(role.getName()));
		});
		
	}*/
	
	/*@Test
	void testCategory() {
		
		//categoryRepository.save(new Category(null, "Fruit"));
		//categoryRepository.save(new Category(null, "Vegetable"));
		
		categoryRepository.findAll().forEach(category -> System.out.println(category.getName()));
		
	}*/
	
	/*@Test
	void testProvince() {
		
		//provinceRepository.save(new Province(null, "Antananarivo"));
		//provinceRepository.save(new Province(null, "Antsiranana"));
		//provinceRepository.save(new Province(null, "Fianarantsoa"));
		//provinceRepository.save(new Province(null, "Mahajanga"));
		//provinceRepository.save(new Province(null, "Toamasina"));
		//provinceRepository.save(new Province(null, "Toliara"));
		
		provinceRepository.findAll().forEach(province -> System.out.println(province.getName()));
		
	}*/
	
	/*@Test
	void testProduct() {
		
		//Category category = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).getFirst();
		//Province province = provinceRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).getFirst();
		//Product p = new Product(null, category, province, "Banana", 10000.0, 2000.0, "kg");
		//productRepository.save(p);
		
		productRepository.findAll().forEach(product -> System.out.println(product.getName()));
		
	}*/

}
