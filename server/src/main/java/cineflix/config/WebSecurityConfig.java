package cineflix.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cineflix.security.JWTAuthEntryPoint;
import cineflix.security.JWTAuthenticationFilter;
import cineflix.services.TokenAuthenticationService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired private JWTAuthEntryPoint unauthorizedHandler;

	@Autowired private TokenAuthenticationService authService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JWTAuthenticationFilter authFilter = new JWTAuthenticationFilter();
		authFilter.setAuthService(authService);
		http.csrf()
		.disable()
		.exceptionHandling()
		.authenticationEntryPoint(unauthorizedHandler)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS)
		.permitAll()
		.antMatchers("/movie/**")
		.permitAll()
		.antMatchers("/star/**")
		.permitAll()
		.antMatchers("/")
		.permitAll()
		.antMatchers("/**/*.{js,html,css}")
		.permitAll()
		.antMatchers("/user/search")
		.permitAll()
		.antMatchers("/user/login")
		.permitAll()
		.antMatchers("/user/register")
		.permitAll()
		.antMatchers("/user/make-admin")
		.permitAll()
		.antMatchers("/user/subscribe")
		.permitAll()
		.antMatchers("/user/")
		.authenticated()
		.anyRequest()
		.authenticated()
		.and()
		.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
