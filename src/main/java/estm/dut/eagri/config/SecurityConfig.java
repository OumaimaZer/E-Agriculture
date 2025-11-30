package estm.dut.eagri.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import estm.dut.eagri.services.UserInfoUserDetailsService;


@Configuration
@EnableWebSecurity
// @EnableMethodSecurity
public class SecurityConfig {

    @Bean
    //authentication
    public UserDetailsService userDetailsService() { 
        return new UserInfoUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http.csrf((csrf)->csrf.disable())
        .authorizeHttpRequests((request) -> request
				.requestMatchers("/css/**,/js/**","/eagri/clients/auth/signup","/eagri/clients/auth/login","/eagri/clients/index").permitAll()
                .anyRequest().authenticated()
			) 
			.formLogin((form) -> form
				.loginPage("/eagri/clients/auth/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/eagri/clients/", true)
                .failureForwardUrl("/error")
				.permitAll()
			)
			.logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/eagri/clients/auth/login"))
            .exceptionHandling((handle)->handle
                  .accessDeniedPage("/error")
            );
                

		return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }
}