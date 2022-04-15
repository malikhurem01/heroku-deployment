package com.internship.AuctionApp.security;

import com.internship.AuctionApp.configuration.AuthWhitelistConfig;
import com.internship.AuctionApp.configuration.JWTConfig;
import com.internship.AuctionApp.filters.JWTAuthenticationFilter;
import com.internship.AuctionApp.filters.JWTAuthorizationFilter;
import com.internship.AuctionApp.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userServiceImpl;
    private final PasswordConfig passwordConfig;
    private final JWTConfig jwtConfig;
    private final AuthWhitelistConfig authWhitelistConfig;

    @Autowired
    public SecurityConfig(UserServiceImpl userServiceImpl, PasswordConfig passwordConfig, JWTConfig jwtConfig, AuthWhitelistConfig authWhitelistConfig) {
        this.userServiceImpl = userServiceImpl;
        this.passwordConfig = passwordConfig;
        this.jwtConfig = jwtConfig;
        this.authWhitelistConfig = authWhitelistConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter customAuthenticationFilter = new JWTAuthenticationFilter(authenticationManagerBean(), null);
        http.cors().and().csrf().disable();
        http.authorizeRequests().antMatchers(authWhitelistConfig.getRoute() + "/auth/users/current").authenticated();
        http.authorizeRequests().antMatchers(authWhitelistConfig.getRoute() + "/authenticate").permitAll();
        http.authorizeRequests().antMatchers(authWhitelistConfig.getRoute() + "/get/products").permitAll();
        http.authorizeRequests().antMatchers(authWhitelistConfig.getRoute() + "/get/product").permitAll();
        http.addFilter(customAuthenticationFilter);
        authWhitelistConfig.initializeRoutes();
        http.addFilterBefore(new JWTAuthorizationFilter(jwtConfig, authWhitelistConfig), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordConfig.passwordEncoder());
        provider.setUserDetailsService(userServiceImpl);
        return provider;
    }

}
