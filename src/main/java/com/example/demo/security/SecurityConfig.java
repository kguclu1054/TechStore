package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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

    private final MyAppUserService myAppUserService;

    public SecurityConfig(MyAppUserService myAppUserService) {
        this.myAppUserService = myAppUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myAppUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    @Lazy
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm -> {
                    httpForm
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/loginPage?error=true")  // Hatalı girişte bu URL'ye yönlendirme
                        .permitAll();
                })
                .logout(logoutForm -> {
                    logoutForm
                        .logoutUrl("/perform_logout")
                        .logoutSuccessUrl("/loginPage?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll();
                })
                .authorizeHttpRequests(registry -> {
                    registry
                        .requestMatchers("/req/signup", "/forget_password", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/search", "/search-results").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/checkout/remove-all/**").authenticated() 
                        .anyRequest().authenticated();
                })
                .rememberMe(rememberMe -> rememberMe.disable())
                .build();
    }
}

