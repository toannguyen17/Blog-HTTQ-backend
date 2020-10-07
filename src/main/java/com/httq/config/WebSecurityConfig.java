package com.httq.config;

import com.httq.services.user.UserDetailsServiceImpl;
import com.httq.system.properties.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(StorageProperties.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Bean
	@Qualifier("userDetailsService")
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	//
	//    @Bean
	//    public FilterRegistrationBean<CorsFilter> corsFilter() {
	//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	//        CorsConfiguration config = new CorsConfiguration();
	//        config.setAllowCredentials(true);
	//        config.addAllowedOrigin("*");
	//        config.addAllowedHeader("*");
	//        config.addAllowedMethod("*");
	//        source.registerCorsConfiguration("/**", config);
	//        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
	//        bean.setOrder(0);
	//        return bean;
	//    }


	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
		accessDeniedHandler.setErrorPage("/error");
		return accessDeniedHandler;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new CustomDaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	//    @Override
	//    protected void configure(HttpSecurity http) throws Exception {
	//        http
	//                .authorizeRequests()
	//                .antMatchers("/admin/**", "/api/admin/**")
	//                .hasAnyRole("ADMIN")
	//                .and()
	//            .authorizeRequests()
	//                .antMatchers("/profile/**", "/api/profile/**")
	//                .hasAnyRole("ADMIN", "USER")
	//                .and()
	//            .authorizeRequests()
	//                .antMatchers("/**").permitAll()
	//                .and()
	//            .formLogin()
	//                .loginPage("/login").usernameParameter("email").passwordParameter("password").permitAll()
	//                .successForwardUrl("/o")
	//                .failureForwardUrl("/login")
	//                .successHandler(authenticationSuccessHandler())
	//                .and()
	//            .logout()
	//                .invalidateHttpSession(true)
	//                .logoutUrl("/logout")
	//                .logoutSuccessUrl("/")
	//                .permitAll()
	//                .and()
	//            .rememberMe().key("uniqueAndSecret")
	//                .and()
	//            .headers()
	//                .defaultsDisabled()
	//                .frameOptions()
	//                .sameOrigin()
	//                .cacheControl().disable();
	//        http.csrf();
	//    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable CSRF (cross site request forgery)
		http.csrf()
		    .disable();

		// No session will be created or used by spring security
		http.sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Entry points
		http.authorizeRequests()//
		    .antMatchers("/api/v1/signin")
		    .permitAll()
		    .antMatchers("/api/v1/signup")
		    .permitAll()
		    // Disallow everything else..
		    .anyRequest()
		    .authenticated();

		// If a user try to access a resource without having enough permissions
		http.exceptionHandling()
		    .accessDeniedPage("/login");

		// Apply JWT
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

		// Optional, if you want to test the API from a browser
		// http.httpBasic();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Allow swagger to be accessed without authentication
		web.ignoring()
		   .antMatchers("/v2/api-docs")//
		   .antMatchers("/swagger-resources/**")//
		   .antMatchers("/swagger-ui.html")//
		   .antMatchers("/configuration/**")//
		   .antMatchers("/webjars/**")//
		   .antMatchers("/public");

		// Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
		//		   .and()
		//		   .ignoring()
		//		   .antMatchers("/h2-console/**/**");
	}
}
