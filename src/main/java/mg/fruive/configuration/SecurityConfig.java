package mg.fruive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;
import mg.fruive.service.UserDetailService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	
	private UserDetailService userDetailService;
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		// Manage correctly what default URL to use on success for login or logout
		httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));
		httpSecurity.formLogin().loginPage("/sign-in").permitAll();
		
		// Transaction on Front-Office and all Back-Office need authentication
		httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
		httpSecurity.userDetailsService(userDetailService);
		return httpSecurity.build();
		
	}

}
