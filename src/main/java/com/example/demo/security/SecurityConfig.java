package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.MyAppUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyAppUserService appUserService;

    public SecurityConfig(MyAppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return appUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm -> {
                    httpForm
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/perform_login")  // Login işlemi için URL
                        .defaultSuccessUrl("/index", true)  // Login başarılı olduğunda yönlendirilecek sayfa
                        .failureUrl("/loginPage?error=true") // Başarısız giriş URL'i .permitAll();
                        .permitAll();         
                })
                .logout(logoutForm -> {
                    logoutForm
                        .logoutUrl("/perform_logout")
                        .deleteCookies("JSESSIONID"); // Logout işlemi için URL
                })
                .authorizeHttpRequests(registry -> {
                    registry
                        .requestMatchers("/req/signup", "/forget_password", "/css/**", "/js/**").permitAll();  
                    registry
                        .anyRequest().authenticated();  
                })
                .build();  
    }
}
