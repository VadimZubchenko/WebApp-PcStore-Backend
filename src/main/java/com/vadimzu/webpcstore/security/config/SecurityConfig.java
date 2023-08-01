/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vadimzu.webpcstore.security.config;

import com.vadimzu.webpcstore.exception.ResourceNotFoundException;
import com.vadimzu.webpcstore.repository.StaffRepo;
import com.vadimzu.webpcstore.security.jwt.JwtConfigurer;
import com.vadimzu.webpcstore.security.jwt.JwtTokenProvider;
import com.vadimzu.webpcstore.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 *
 * @author vadimzubchenko
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtService jwtService;
    private LogoutHandler logoutHandler;

    @Autowired
    public SecurityConfig(LogoutHandler logoutHandler, JwtService jwtService) {
        this.logoutHandler = logoutHandler;
        this.jwtService = jwtService;

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                //.antMatchers("admin endpoint").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtService))
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
    }
}
