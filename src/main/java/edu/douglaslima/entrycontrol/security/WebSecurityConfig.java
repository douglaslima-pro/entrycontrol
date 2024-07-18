package edu.douglaslima.entrycontrol.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private AuthenticationEntryPointImpl authenticationEntryPoint;

	@Autowired
	private TokenExceptionHandlerFilter tokenExceptionHandlerFilter;
	
	@Autowired
	private TokenFilter tokenFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/swagger*/**", "/swagger-ui/**", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/configuration/**", "/webjars/**").permitAll()
					.requestMatchers("/auth/**").permitAll()
					.requestMatchers("/user/search/me").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/user/update/me").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/user/search/{id}").hasRole("ADMIN")
					.requestMatchers("/user/search/all").hasRole("ADMIN")
					.requestMatchers("/user/update/{id}").hasRole("ADMIN")
					.requestMatchers("/user/delete/{id}").hasRole("ADMIN")
					.anyRequest().authenticated()
					)
			.csrf(csrf -> csrf.disable())
			.cors(Customizer.withDefaults())
			.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(tokenExceptionHandlerFilter, TokenFilter.class);
		return http.build();
	}

}
