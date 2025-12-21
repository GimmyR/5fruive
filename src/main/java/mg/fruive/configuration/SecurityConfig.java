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
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // Disable CSRF for API
		httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

        // Use custom login page to sign in
		httpSecurity.formLogin(login -> login.loginPage("/sign-in").permitAll());
		
		// Transaction on Front-Office and all Back-Office need authentication
		httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/payment/**", "/bill/**").authenticated());
		httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/bo/**").hasRole("Admin"));
		httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
		httpSecurity.userDetailsService(userDetailService);
		return httpSecurity.build();
		
	}

}
