package com.victoriaKlein.UserSignUpSignIn.securityAuth;

import com.victoriaKlein.UserSignUpSignIn.securityAuth.authenticationHandler.CustomLoginFailureHandler;
import com.victoriaKlein.UserSignUpSignIn.securityAuth.authenticationHandler.CustomLoginSuccessHandler;
import com.victoriaKlein.UserSignUpSignIn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    @Autowired
    private final UserService userService;
    @Autowired
    private final CustomLoginFailureHandler loginFailureHandler;

    @Autowired
    private final CustomLoginSuccessHandler loginSuccessHandler;

    public ResourceServerConfig(UserService userService, CustomLoginFailureHandler loginFailureHandler, CustomLoginSuccessHandler loginSuccessHandler) {
        this.userService = userService;
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/users/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
             //   .loginPage("/signUp")
                .usernameParameter("userName")
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
}