package br.com.abusei.Abusei;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
			.authorizeRequests()
			.antMatchers("/dashboard/**").hasRole("USER")
			.antMatchers(HttpMethod.POST, "/cadastrar").hasRole("ADMIN")
			.antMatchers("/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
		.formLogin(login -> login
				.loginPage("/entrar")
				.defaultSuccessUrl("/dashboard/relatorio", true)
				.permitAll()
		).logout(logout -> logout
				.logoutUrl("/sair")
				.logoutSuccessUrl("/inicio"))
		
		.csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
//		UserDetails user = User
//				.builder()
//				.username("celio")
//				.password(encoder.encode("celio"))
//				.roles("USER")
//				.build();
//		
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(encoder);
//		.withUser(user);
		

	}
	
}


