package com.E_CommerceBackendSystem.ecom.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		 http
         .authorizeHttpRequests(auth -> auth
        	  .requestMatchers("/user/**").hasRole("USER")
        	  .requestMatchers("/admin/**").hasRole("ADMIN")
              .requestMatchers("/public/**").permitAll()
              .anyRequest().authenticated()
         )
         .formLogin(withDefaults());

		 http
		    .csrf(csrf -> csrf.disable())
		    .httpBasic(withDefaults());
     return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
