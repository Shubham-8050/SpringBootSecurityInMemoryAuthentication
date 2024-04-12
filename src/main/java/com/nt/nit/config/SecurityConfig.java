package com.nt.nit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Authentication
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("sam").password("{noop}sam").authorities("ADMIN");
        auth.inMemoryAuthentication().withUser("syed").password("{noop}syed").authorities("EMPLOYEE");
        auth.inMemoryAuthentication().withUser("jai").password("{noop}jai").authorities("STUDENT");
    }

    //Authorization
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/welcome").authenticated()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/emp").hasAuthority("EMPLOYEE")
                .antMatchers("/std").hasAuthority("STUDENT")

                //login form
                .and()
                .formLogin()
                .defaultSuccessUrl("/welcome",true)

                //logout details
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                //exception details
                .and()
                .exceptionHandling()
                .accessDeniedPage("/denied");



    }
}
