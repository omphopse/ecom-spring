package com.E_CommerceBackendSystem.ecom.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//HttpSecurity is used to configure web security rules.
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// Request → Authentication Filter → Authorization Filter → Controller
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		 http
         .authorizeHttpRequests(auth -> auth
//        		 auth represents:
//        			 AuthorizationManagerRequestMatcherRegistry
//        		 Which basically means:
//        			 object used to define URL access rules
        		 
        	  .requestMatchers("/user/**").hasRole("USER")
        	  .requestMatchers("/admin/**").hasRole("ADMIN")
              .requestMatchers("/public/**","/h2-console/**").permitAll()
              .anyRequest().authenticated()
         )
         .formLogin(withDefaults());

		 http
		    .csrf(csrf -> csrf.disable())
		    .headers(headers -> headers.frameOptions(frame -> frame.disable()))
		    .httpBasic(withDefaults());
		 
		 return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
